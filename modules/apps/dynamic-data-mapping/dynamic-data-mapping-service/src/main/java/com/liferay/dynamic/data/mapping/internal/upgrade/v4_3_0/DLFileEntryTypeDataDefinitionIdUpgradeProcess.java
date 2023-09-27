/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.dynamic.data.mapping.internal.upgrade.v4_3_0;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.model.DLFileEntryType;
import com.liferay.document.library.kernel.service.DLFileEntryTypeLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.ResourceActionsUtil;
import com.liferay.portal.kernel.service.ResourceLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;

/**
 * @author Alejandro Tardín
 */
public class DLFileEntryTypeDataDefinitionIdUpgradeProcess
	extends UpgradeProcess {

	public DLFileEntryTypeDataDefinitionIdUpgradeProcess(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		ActionableDynamicQuery actionableDynamicQuery =
			_dlFileEntryTypeLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				dynamicQuery.add(
					RestrictionsFactoryUtil.or(
						RestrictionsFactoryUtil.eq("dataDefinitionId", 0L),
						RestrictionsFactoryUtil.isNull("dataDefinitionId")));
				dynamicQuery.add(
					RestrictionsFactoryUtil.ne(
						"fileEntryTypeKey", "BASIC-DOCUMENT"));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(DLFileEntryType dlFileEntryType) -> {
				try {
					long ddmStructureId = _addDDMStructure(
						dlFileEntryType.getGroupId(),
						dlFileEntryType.getCompanyId(),
						dlFileEntryType.getUserId(),
						dlFileEntryType.getUserName(),
						dlFileEntryType.getName());

					long ddmStructureVersionId = _addDDMStructureVersion(
						dlFileEntryType.getUserId(),
						dlFileEntryType.getUserName(),
						dlFileEntryType.getCompanyId(),
						dlFileEntryType.getGroupId(), ddmStructureId,
						dlFileEntryType.getName());

					_addDDMStructureLayout(
						dlFileEntryType.getUserId(),
						dlFileEntryType.getUserName(),
						dlFileEntryType.getGroupId(),
						dlFileEntryType.getCompanyId(), ddmStructureId,
						dlFileEntryType.getName(), ddmStructureVersionId);

					ResourceLocalServiceUtil.addResources(
						dlFileEntryType.getCompanyId(),
						dlFileEntryType.getGroupId(),
						dlFileEntryType.getUserId(),
						ResourceActionsUtil.getCompositeModelName(
							DLFileEntryMetadata.class.getName(),
							DDMStructure.class.getName()),
						ddmStructureId, false, false, false);

					dlFileEntryType.setDataDefinitionId(ddmStructureId);

					_dlFileEntryTypeLocalService.updateDLFileEntryType(
						dlFileEntryType);
				}
				catch (Exception exception) {
					_log.error(exception);
				}
			});

		actionableDynamicQuery.performActions();

		if (!hasIndex("DLFileEntryType", "IX_B6F21286")) {
			runSQLTemplateString(
				"create unique index IX_B6F21286 on DLFileEntryType (" +
					"groupId, dataDefinitionId, ctCollectionId);",
				false);
		}
	}

	private long _addDDMStructure(
			long groupId, long companyId, long userId, String userName,
			String name)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into DDMStructure (mvccVersion, ctCollectionId, ",
					"uuid_, structureId, groupId, companyId, userId, ",
					"userName, versionUserId, versionUserName, createDate, ",
					"modifiedDate, parentStructureId, classNameId, ",
					"structureKey, version, name, description, definition, ",
					"storageType, type_, lastPublishDate) values (0, 0, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, 0, ?, ?, '1.0', ?, '', ?, ",
					"'default', 1, null)"))) {

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			preparedStatement.setString(1, PortalUUIDUtil.generate());

			long ddmStructureId = increment();

			preparedStatement.setLong(2, ddmStructureId);

			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, userId);
			preparedStatement.setString(6, userName);
			preparedStatement.setLong(7, userId);
			preparedStatement.setString(8, userName);

			Date date = new Date(System.currentTimeMillis());

			preparedStatement.setDate(9, date);
			preparedStatement.setDate(10, date);

			preparedStatement.setLong(11, classNameId);
			preparedStatement.setString(12, String.valueOf(ddmStructureId));
			preparedStatement.setString(13, name);
			preparedStatement.setString(14, _DEFINITION_DDM_STRUCTURE);

			preparedStatement.executeUpdate();

			return ddmStructureId;
		}
	}

	private void _addDDMStructureLayout(
			long userId, String userName, long groupId, long companyId,
			long ddmStructureId, String name, long ddmStructureVersionId)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into DDMStructureLayout (mvccVersion, ",
					"ctCollectionId, uuid_, structureLayoutId, groupId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"classNameId, structureLayoutKey, structureVersionId, ",
					"name, description, definition) values (0, 0, ?, ?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, '', ?)"))) {

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			preparedStatement.setString(1, PortalUUIDUtil.generate());
			preparedStatement.setLong(2, increment());
			preparedStatement.setLong(3, groupId);
			preparedStatement.setLong(4, companyId);
			preparedStatement.setLong(5, userId);
			preparedStatement.setString(6, userName);

			Date date = new Date(System.currentTimeMillis());

			preparedStatement.setDate(7, date);
			preparedStatement.setDate(8, date);

			preparedStatement.setLong(9, classNameId);
			preparedStatement.setString(10, String.valueOf(ddmStructureId));
			preparedStatement.setLong(11, ddmStructureVersionId);
			preparedStatement.setString(12, name);
			preparedStatement.setString(13, _DEFINITION_DDM_STRUCTURE_LAYOUT);

			preparedStatement.executeUpdate();
		}
	}

	private long _addDDMStructureVersion(
			long userId, String userName, long companyId, long groupId,
			long ddmStructureId, String name)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				StringBundler.concat(
					"insert into DDMStructureVersion (mvccVersion, ",
					"ctCollectionId, structureVersionId, groupId, companyId, ",
					"userId, userName, createDate, structureId, version, ",
					"parentStructureId, name, description, definition, ",
					"storageType, type_, status, statusByUserId, ",
					"statusByUserName, statusDate) values (0, 0, ?, ?, ?, ?, ",
					"?, ?, ?, '1.0', 0, ?, '', ? , 'default', 0, 0, ?, ?, ",
					"?)"))) {

			long structureVersionId = increment();

			preparedStatement.setLong(1, structureVersionId);

			preparedStatement.setLong(2, groupId);
			preparedStatement.setLong(3, companyId);
			preparedStatement.setLong(4, userId);
			preparedStatement.setString(5, userName);

			Date date = new Date(System.currentTimeMillis());

			preparedStatement.setDate(6, date);

			preparedStatement.setLong(7, ddmStructureId);
			preparedStatement.setString(8, name);
			preparedStatement.setString(9, _DEFINITION_DDM_STRUCTURE);
			preparedStatement.setLong(10, userId);
			preparedStatement.setString(11, userName);
			preparedStatement.setDate(12, date);

			preparedStatement.executeUpdate();

			return structureVersionId;
		}
	}

	private static final String _DEFINITION_DDM_STRUCTURE = JSONUtil.put(
		"availableLanguageIds", JSONUtil.putAll("en_US")
	).put(
		"defaultLanguageId", "en_US"
	).put(
		"definitionSchemaVersion", "2.0"
	).put(
		"fields", JSONFactoryUtil.createJSONArray()
	).put(
		"successPage",
		JSONUtil.put(
			"body", JSONFactoryUtil.createJSONObject()
		).put(
			"enabled", false
		).put(
			"title", JSONFactoryUtil.createJSONObject()
		)
	).toString();

	private static final String _DEFINITION_DDM_STRUCTURE_LAYOUT = JSONUtil.put(
		"defaultLanguageId", "en_US"
	).put(
		"definitionSchemaVersion", "2.0"
	).put(
		"fields", JSONFactoryUtil.createJSONArray()
	).put(
		"pages",
		JSONUtil.putAll(
			JSONUtil.put(
				"description", JSONUtil.put("en_US", StringPool.BLANK)
			).put(
				"enabled", false
			).put(
				"rows", JSONFactoryUtil.createJSONArray()
			).put(
				"title", JSONUtil.put("en_US", StringPool.BLANK)
			))
	).put(
		"paginationMode", "single-page"
	).toString();

	private static final Log _log = LogFactoryUtil.getLog(
		DLFileEntryTypeDataDefinitionIdUpgradeProcess.class);

	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}