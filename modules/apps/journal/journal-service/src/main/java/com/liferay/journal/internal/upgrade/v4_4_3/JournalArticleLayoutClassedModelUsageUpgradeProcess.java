/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.journal.internal.upgrade.v4_4_3;

import com.liferay.asset.publisher.constants.AssetPublisherPortletKeys;
import com.liferay.journal.model.JournalArticle;
import com.liferay.layout.page.template.constants.LayoutPageTemplateEntryTypeConstants;
import com.liferay.layout.util.constants.LayoutClassedModelUsageConstants;
import com.liferay.petra.lang.SafeCloseable;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.dao.orm.common.SQLTransformer;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortletKeys;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Lourdes Fernández Besada
 */
public class JournalArticleLayoutClassedModelUsageUpgradeProcess
	extends UpgradeProcess {

	public JournalArticleLayoutClassedModelUsageUpgradeProcess(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		long journalArticleClassNameId = _classNameLocalService.getClassNameId(
			JournalArticle.class.getName());
		long portletClassNameId = _classNameLocalService.getClassNameId(
			Portlet.class.getName());

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			Map<Long, Integer> layoutClassedModelUsageTypes =
				new ConcurrentHashMap<>();
			Map<Long, Map<Long, Long>> resourcePrimKeysMap =
				new ConcurrentHashMap<>();

			_addJournalContentSearchLayoutClassedModelUsages(
				journalArticleClassNameId, layoutClassedModelUsageTypes,
				portletClassNameId, resourcePrimKeysMap);

			_addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
				journalArticleClassNameId, layoutClassedModelUsageTypes,
				portletClassNameId, resourcePrimKeysMap);

			_addDefaultLayoutClassedModelUsages(
				journalArticleClassNameId, resourcePrimKeysMap);
		}
	}

	private void _addAssetPublisherPortletPreferencesLayoutClassedModelUsages(
			long journalArticleClassNameId,
			Map<Long, Integer> layoutClassedModelUsageTypes,
			long portletClassNameId,
			Map<Long, Map<Long, Long>> resourcePrimKeysMap)
		throws Exception {

		String sql = StringBundler.concat(
			"select distinct AssetEntry.groupId, AssetEntry.companyId, ",
			"AssetEntry.classPK, PortletPreferences.plid, ",
			"PortletPreferences.portletId from PortletPreferences inner join ",
			"(select SUBSTR(value, INSTR(value, '<asset-entry-uuid>') + ",
			"LENGTH('<asset-entry-uuid>'), INSTR(value, ",
			"'</asset-entry-uuid>') - (INSTR(value, '<asset-entry-uuid>') + ",
			"LENGTH('<asset-entry-uuid>'))) uuid, portletPreferencesId from ",
			"(select COALESCE(NULLIF(CAST_TEXT(largeValue), ''), smallValue) ",
			"as value, portletPreferencesId from PortletPreferenceValue where ",
			"name = 'assetEntryXml') innerTemp where value like ",
			"'%<asset-entry-type>com.liferay.journal.model.JournalArticle%' ",
			"or value like '%<asset-entry-type></asset-entry-type>%' or value ",
			"like '%<asset-entry>%$NEW_LINE$%<asset-entry-type/>%') temp on ",
			"PortletPreferences.ownerId = ", PortletKeys.PREFS_OWNER_ID_DEFAULT,
			" and PortletPreferences.ownerType = ",
			PortletKeys.PREFS_OWNER_TYPE_LAYOUT,
			" and PortletPreferences.portletId like '",
			AssetPublisherPortletKeys.ASSET_PUBLISHER,
			"%' and PortletPreferences.portletPreferencesId = ",
			"temp.portletPreferencesId and ",
			"PortletPreferences.portletPreferencesId in (select ",
			"portletPreferencesId from PortletPreferenceValue where name = ",
			"'selectionStyle' and smallValue = 'manual') inner join ",
			"AssetEntry on AssetEntry.classUuid = temp.uuid and ",
			"AssetEntry.classNameId = ", journalArticleClassNameId,
			" and AssetEntry.visible = [$TRUE$] where not exists (select 1 ",
			"from LayoutClassedModelUsage where ",
			"LayoutClassedModelUsage.classPK = AssetEntry.classPK and ",
			"LayoutClassedModelUsage.classNameId = ", journalArticleClassNameId,
			" and LayoutClassedModelUsage.containerKey = ",
			"PortletPreferences.portletId and ",
			"LayoutClassedModelUsage.containerType = ", portletClassNameId,
			" and LayoutClassedModelUsage.plid = PortletPreferences.plid) and ",
			"not exists (select 1 from LayoutClassedModelUsage where ",
			"LayoutClassedModelUsage.classPK = AssetEntry.classPK and ",
			"LayoutClassedModelUsage.classNameId = ", journalArticleClassNameId,
			" and LayoutClassedModelUsage.containerKey is null and ",
			"LayoutClassedModelUsage.containerType = 0 and ",
			"LayoutClassedModelUsage.plid = 0 )");

		try (LoggingTimer loggingTimer = new LoggingTimer();
			SafeCloseable safeCloseable = addTemporaryIndex(
				"AssetEntry", false, "classUuid", "classNameId", "visible")) {

			processConcurrently(
				SQLTransformer.transform(sql),
				StringBundler.concat(
					"insert into LayoutClassedModelUsage (uuid_, ",
					"layoutClassedModelUsageId, groupId, companyId, ",
					"createDate, modifiedDate, classNameId, classPK, ",
					"containerKey, containerType, plid, type_ ) values (?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
				resultSet -> new Object[] {
					resultSet.getLong("groupId"),
					resultSet.getLong("companyId"),
					resultSet.getLong("classPK"), resultSet.getLong("plid"),
					GetterUtil.getString(resultSet.getString("portletId"))
				},
				(values, preparedStatement) -> {
					long groupId = (Long)values[0];
					long companyId = (Long)values[1];
					long classPK = (Long)values[2];
					long plid = (Long)values[3];
					String portletId = (String)values[4];

					_addLayoutClassedModelUsage(
						groupId, companyId, journalArticleClassNameId, classPK,
						portletId, portletClassNameId, plid,
						layoutClassedModelUsageTypes, preparedStatement,
						resourcePrimKeysMap);

					Map<Long, Long> companyResourcePrimKeysMap =
						resourcePrimKeysMap.computeIfAbsent(
							companyId, key -> new ConcurrentHashMap<>());

					companyResourcePrimKeysMap.computeIfAbsent(
						classPK, key -> groupId);
				},
				"Unable to create manual selection asset publisher layout " +
					"classed model usages");
		}
	}

	private void _addDefaultLayoutClassedModelUsages(
			long journalArticleClassNameId,
			Map<Long, Map<Long, Long>> resourcePrimKeysMap)
		throws SQLException {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement preparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into LayoutClassedModelUsage (uuid_, ",
						"layoutClassedModelUsageId, groupId, companyId, ",
						"createDate, modifiedDate, classNameId, classPK, ",
						"containerKey, containerType, plid, type_ ) values ",
						"(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))) {

			for (Map.Entry<Long, Map<Long, Long>> companyResourcePrimKeysEntry :
					resourcePrimKeysMap.entrySet()) {

				long companyId = companyResourcePrimKeysEntry.getKey();
				Map<Long, Long> groupResourcePrimKeysMap =
					companyResourcePrimKeysEntry.getValue();

				for (Map.Entry<Long, Long> resourcePrimKeysEntry :
						groupResourcePrimKeysMap.entrySet()) {

					preparedStatement.setString(1, PortalUUIDUtil.generate());
					preparedStatement.setLong(2, increment());
					preparedStatement.setLong(
						3, resourcePrimKeysEntry.getValue());
					preparedStatement.setLong(4, companyId);

					Timestamp timestamp = new Timestamp(
						System.currentTimeMillis());

					preparedStatement.setTimestamp(5, timestamp);
					preparedStatement.setTimestamp(6, timestamp);

					preparedStatement.setLong(7, journalArticleClassNameId);
					preparedStatement.setLong(
						8, resourcePrimKeysEntry.getKey());
					preparedStatement.setString(9, null);
					preparedStatement.setLong(10, 0);
					preparedStatement.setLong(11, 0);
					preparedStatement.setInt(
						12, LayoutClassedModelUsageConstants.TYPE_DEFAULT);

					preparedStatement.addBatch();
				}
			}

			preparedStatement.executeBatch();
		}
	}

	private void _addJournalContentSearchLayoutClassedModelUsages(
			long journalArticleClassNameId,
			Map<Long, Integer> layoutClassedModelUsageTypes,
			long portletClassNameId,
			Map<Long, Map<Long, Long>> resourcePrimKeysMap)
		throws Exception {

		String sql = StringBundler.concat(
			"select distinct JournalArticle.resourcePrimKey, ",
			"JournalArticle.groupId, JournalArticle.companyId, ",
			"JournalContentSearch.portletId, Layout.plid from JournalArticle ",
			"inner join JournalContentSearch on JournalContentSearch.groupId ",
			"= JournalArticle.groupId and JournalContentSearch.articleId = ",
			"JournalArticle.articleId inner join Layout on ",
			"Layout.privateLayout = JournalContentSearch.privateLayout and ",
			"Layout.layoutId = JournalContentSearch.layoutId and ",
			"Layout.groupId = JournalArticle.groupId and not exists (select 1 ",
			"from LayoutClassedModelUsage where ",
			"LayoutClassedModelUsage.classPK = JournalArticle.resourcePrimKey ",
			"and LayoutClassedModelUsage.classNameId = ",
			journalArticleClassNameId,
			" and LayoutClassedModelUsage.containerKey = ",
			"JournalContentSearch.portletId and ",
			"LayoutClassedModelUsage.containerType = ", portletClassNameId,
			" and LayoutClassedModelUsage.plid = Layout.plid) where not ",
			"exists (select 1 from LayoutClassedModelUsage where ",
			"LayoutClassedModelUsage.classPK = JournalArticle.resourcePrimKey ",
			"and LayoutClassedModelUsage.classNameId = ",
			journalArticleClassNameId,
			" and LayoutClassedModelUsage.containerKey is null and ",
			"LayoutClassedModelUsage.containerType = 0 and ",
			"LayoutClassedModelUsage.plid = 0 )");

		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			processConcurrently(
				sql,
				StringBundler.concat(
					"insert into LayoutClassedModelUsage (uuid_, ",
					"layoutClassedModelUsageId, groupId, companyId, ",
					"createDate, modifiedDate, classNameId, classPK, ",
					"containerKey, containerType, plid, type_ ) values (?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"),
				resultSet -> new Object[] {
					resultSet.getLong("resourcePrimKey"),
					resultSet.getLong("groupId"),
					resultSet.getLong("companyId"),
					GetterUtil.getString(resultSet.getString("portletId")),
					resultSet.getLong("plid")
				},
				(values, preparedStatement) -> {
					long resourcePrimKey = (Long)values[0];
					long groupId = (Long)values[1];
					long companyId = (Long)values[2];
					String portletId = (String)values[3];
					long plid = (Long)values[4];

					_addLayoutClassedModelUsage(
						groupId, companyId, journalArticleClassNameId,
						resourcePrimKey, portletId, portletClassNameId, plid,
						layoutClassedModelUsageTypes, preparedStatement,
						resourcePrimKeysMap);
				},
				"Unable to create journal articles search layout classed " +
					"model usages");
		}
	}

	private void _addLayoutClassedModelUsage(
			long groupId, long companyId, long classNameId, long classPK,
			String containerKey, long containerType, long plid,
			Map<Long, Integer> layoutClassedModelUsageTypes,
			PreparedStatement preparedStatement,
			Map<Long, Map<Long, Long>> resourcePrimKeysMap)
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
		preparedStatement.setString(9, containerKey);
		preparedStatement.setLong(10, containerType);
		preparedStatement.setLong(11, plid);

		Integer type = layoutClassedModelUsageTypes.get(plid);

		if (type == null) {
			type = _getLayoutClassedModelUsageType(plid);

			layoutClassedModelUsageTypes.put(plid, type);
		}

		preparedStatement.setInt(12, type);

		preparedStatement.addBatch();

		Map<Long, Long> companyResourcePrimKeysMap =
			resourcePrimKeysMap.computeIfAbsent(
				companyId, key -> new ConcurrentHashMap<>());

		companyResourcePrimKeysMap.computeIfAbsent(classPK, key -> groupId);
	}

	private int _getLayoutClassedModelUsageType(long plid) throws Exception {
		if (plid <= 0) {
			return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"select Layout.plid, LayoutPageTemplateEntry.type_ from ",
					"Layout left join LayoutPageTemplateEntry on ",
					"(Layout.classPK = 0 and LayoutPageTemplateEntry.plid = ",
					plid,
					") or (LayoutPageTemplateEntry.plid = Layout.classPK) ",
					"where Layout.plid = ", plid))) {

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int layoutPageTemplateEntryType = resultSet.getInt("type_");

					if (layoutPageTemplateEntryType == 0) {
						return LayoutClassedModelUsageConstants.TYPE_LAYOUT;
					}

					if (layoutPageTemplateEntryType ==
							LayoutPageTemplateEntryTypeConstants.
								TYPE_DISPLAY_PAGE) {

						return LayoutClassedModelUsageConstants.
							TYPE_DISPLAY_PAGE_TEMPLATE;
					}

					return LayoutClassedModelUsageConstants.TYPE_PAGE_TEMPLATE;
				}

				return LayoutClassedModelUsageConstants.TYPE_DEFAULT;
			}
		}
	}

	private final ClassNameLocalService _classNameLocalService;

}