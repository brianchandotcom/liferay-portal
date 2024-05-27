/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.upgrade.v1_4_1;

import com.liferay.fragment.entry.processor.constants.FragmentEntryProcessorConstants;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.util.constants.LayoutClassedModelUsageConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lourdes Fernández Besada
 */
public class LayoutClassedModelUsageUpgradeProcess extends UpgradeProcess {

	public LayoutClassedModelUsageUpgradeProcess(
		ClassNameLocalService classNameLocalService, JSONFactory jsonFactory) {

		_classNameLocalService = classNameLocalService;
		_jsonFactory = jsonFactory;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Map<Long, Integer> layoutClassedModelUsageTypes =
				new ConcurrentHashMap<>();

			String sql = StringBundler.concat(
				"select FragmentEntryLink.groupId, ",
				"FragmentEntryLink.companyId, ",
				"FragmentEntryLink.fragmentEntryLinkId, ",
				"FragmentEntryLink.plid, FragmentEntryLink.editableValues ",
				"FROM FragmentEntryLink WHERE editableValues LIKE '%",
				FragmentEntryProcessorConstants.
					KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR,
				"%\"classNameId\":\"%'");

			processConcurrently(
				SQLTransformer.transform(sql),
				StringBundler.concat(
					"insert into LayoutClassedModelUsage (uuid_, ",
					"layoutClassedModelUsageId, groupId, companyId, ",
					"createDate, modifiedDate, classNameId, classPK, ",
					"cmExternalReferenceCode, containerKey, containerType, ",
					"plid, type_ ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ",
					"?, ?)"),
				resultSet -> new Object[] {
					resultSet.getLong("groupId"),
					resultSet.getLong("companyId"),
					resultSet.getLong("fragmentEntryLinkId"),
					resultSet.getLong("plid"),
					GetterUtil.getString(resultSet.getString("editableValues"))
				},
				(values, preparedStatement) -> {
					String editableValues = (String)values[4];

					JSONObject editableValuesJSONObject =
						_jsonFactory.createJSONObject(editableValues);

					JSONObject editableFragmentEntryProcessorJSONObject =
						editableValuesJSONObject.getJSONObject(
							FragmentEntryProcessorConstants.
								KEY_EDITABLE_FRAGMENT_ENTRY_PROCESSOR);

					if (editableFragmentEntryProcessorJSONObject == null) {
						return;
					}

					Map<Long, List<Long>> classNameIdClassPKsMap =
						new ConcurrentHashMap<>();

					for (String key :
							editableFragmentEntryProcessorJSONObject.keySet()) {

						JSONObject editableJSONObject =
							editableFragmentEntryProcessorJSONObject.
								getJSONObject(key);

						if ((editableJSONObject == null) ||
							!editableJSONObject.has("classNameId") ||
							!editableJSONObject.has("classPK")) {

							continue;
						}

						long classNameId = editableJSONObject.getLong(
							"classNameId");

						List<Long> classPKs =
							classNameIdClassPKsMap.computeIfAbsent(
								classNameId, key1 -> new ArrayList<>());

						long classPK = editableJSONObject.getLong("classPK");

						if (classPKs.contains(classPK)) {
							continue;
						}

						classPKs.add(classPK);

						long fragmentEntryLinkId = (Long)values[2];
						long plid = (Long)values[3];

						if (!_hasVisibleAssetEntry(classNameId, classPK) ||
							_hasFragmentEntryLinkLayoutClassedModelUsages(
								classNameId, classPK, fragmentEntryLinkId,
								plid)) {

							continue;
						}

						long groupId = (Long)values[0];
						long companyId = (Long)values[1];
						String externalReferenceCode =
							editableJSONObject.getString(
								"externalReferenceCode");

						_addLayoutClassedModelUsage(
							groupId, companyId, classNameId, classPK,
							externalReferenceCode,
							String.valueOf(fragmentEntryLinkId),
							_classNameLocalService.getClassNameId(
								FragmentEntryLink.class.getName()),
							plid, layoutClassedModelUsageTypes,
							preparedStatement);
					}
				},
				"Unable to create layout classed model usages for fragment " +
					"entry links");
		}
	}

	private void _addLayoutClassedModelUsage(
			long groupId, long companyId, long classNameId, long classPK,
			String classedModelExternalReferenceCode, String containerKey,
			long containerType, long plid,
			Map<Long, Integer> layoutClassedModelUsageTypes,
			PreparedStatement preparedStatement)
		throws Exception {

		preparedStatement.setString(1, PortalUUIDUtil.generate());
		preparedStatement.setLong(2, increment());
		preparedStatement.setLong(3, groupId);
		preparedStatement.setLong(4, companyId);

		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		preparedStatement.setTimestamp(5, timestamp);
		preparedStatement.setTimestamp(6, timestamp);

		preparedStatement.setLong(7, classNameId);
		preparedStatement.setLong(8, classPK);
		preparedStatement.setString(9, classedModelExternalReferenceCode);
		preparedStatement.setString(10, containerKey);
		preparedStatement.setLong(11, containerType);
		preparedStatement.setLong(12, plid);

		Integer type = layoutClassedModelUsageTypes.get(plid);

		if (type == null) {
			type = _getLayoutClassedModelUsageType(plid);

			layoutClassedModelUsageTypes.put(plid, type);
		}

		preparedStatement.setInt(13, type);

		preparedStatement.addBatch();
	}

	private int _getLayoutClassedModelUsageType(long plid) throws Exception {
		if (plid <= 0) {
			return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select Layout.plid, LayoutPageTemplateEntry.type_ from ",
					"Layout left join LayoutPageTemplateEntry on ",
					"(Layout.classPK = ? and LayoutPageTemplateEntry.plid = ? ",
					") or (LayoutPageTemplateEntry.plid = Layout.classPK) ",
					"where Layout.plid = ?"))) {

			preparedStatement.setLong(1, 0);
			preparedStatement.setLong(2, plid);
			preparedStatement.setLong(3, plid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int layoutPageTemplateEntryType = resultSet.getInt("type_");

					if (layoutPageTemplateEntryType == 0) {
						return LayoutClassedModelUsageConstants.TYPE_LAYOUT;
					}

					if (layoutPageTemplateEntryType ==
							LayoutPageTemplateEntryTypeConstants.DISPLAY_PAGE) {

						return LayoutClassedModelUsageConstants.
							TYPE_DISPLAY_PAGE_TEMPLATE;
					}

					return LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE;
				}

				return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
			}
		}
	}

	private boolean _hasFragmentEntryLinkLayoutClassedModelUsages(
			long classNameId, long classPK, long fragmentEntryLinkId, long plid)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select 1 from LayoutClassedModelUsage where classNameId ",
					"= ? and classPK = ? and containerKey = ? and ",
					"containerType = ? and plid = ?"))) {

			preparedStatement.setLong(1, classNameId);
			preparedStatement.setLong(2, classPK);
			preparedStatement.setString(3, String.valueOf(fragmentEntryLinkId));
			preparedStatement.setLong(
				4,
				_classNameLocalService.getClassNameId(
					FragmentEntryLink.class.getName()));
			preparedStatement.setLong(5, plid);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return true;
				}

				return false;
			}
		}
	}

	private boolean _hasVisibleAssetEntry(long classNameId, long classPK)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select 1 from AssetEntry where classNameId = ? and classPK " +
					"= ? and visible = ?")) {

			preparedStatement.setLong(1, classNameId);
			preparedStatement.setLong(2, classPK);
			preparedStatement.setBoolean(3, true);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				if (resultSet.next()) {
					return true;
				}

				return false;
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;
	private final JSONFactory _jsonFactory;

}