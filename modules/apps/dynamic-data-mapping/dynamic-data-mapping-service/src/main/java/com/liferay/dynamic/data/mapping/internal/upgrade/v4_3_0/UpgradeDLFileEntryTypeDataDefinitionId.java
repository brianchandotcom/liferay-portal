/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
public class UpgradeDLFileEntryTypeDataDefinitionId extends UpgradeProcess {

	public UpgradeDLFileEntryTypeDataDefinitionId(
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
					exception.printStackTrace();
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

		try (PreparedStatement ps = connection.prepareStatement(
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

			ps.setString(1, PortalUUIDUtil.generate());

			long ddmStructureId = increment();

			ps.setLong(2, ddmStructureId);

			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);
			ps.setLong(7, userId);
			ps.setString(8, userName);

			Date now = new Date(System.currentTimeMillis());

			ps.setDate(9, now);
			ps.setDate(10, now);

			ps.setLong(11, classNameId);

			ps.setString(12, String.valueOf(ddmStructureId));
			ps.setString(13, name);
			ps.setString(14, _DDM_STRUCTURE_DEFINITION);

			ps.executeUpdate();

			return ddmStructureId;
		}
	}

	private void _addDDMStructureLayout(
			long userId, String userName, long groupId, long companyId,
			long ddmStructureId, String name, long ddmStructureVersionId)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
				StringBundler.concat(
					"insert into DDMStructureLayout (mvccVersion, ",
					"ctCollectionId, uuid_, structureLayoutId, groupId, ",
					"companyId, userId, userName, createDate, modifiedDate, ",
					"classNameId, structureLayoutKey, structureVersionId, ",
					"name, description, definition) values (0, 0, ?, ?, ?, ?, ",
					"?, ?, ?, ?, ?, ?, ?, ?, '', ?)"))) {

			long classNameId = PortalUtil.getClassNameId(
				DLFileEntryMetadata.class);

			ps.setString(1, PortalUUIDUtil.generate());
			ps.setLong(2, increment());
			ps.setLong(3, groupId);
			ps.setLong(4, companyId);
			ps.setLong(5, userId);
			ps.setString(6, userName);

			Date now = new Date(System.currentTimeMillis());

			ps.setDate(7, now);
			ps.setDate(8, now);

			ps.setLong(9, classNameId);

			ps.setString(10, String.valueOf(ddmStructureId));
			ps.setLong(11, ddmStructureVersionId);
			ps.setString(12, name);
			ps.setString(13, _DDM_STRUCTURE_LAYOUT_DEFINITION);

			ps.executeUpdate();
		}
	}

	private long _addDDMStructureVersion(
			long userId, String userName, long companyId, long groupId,
			long ddmStructureId, String name)
		throws Exception {

		try (PreparedStatement ps = connection.prepareStatement(
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

			ps.setLong(1, structureVersionId);

			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);

			Date now = new Date(System.currentTimeMillis());

			ps.setDate(6, now);

			ps.setLong(7, ddmStructureId);
			ps.setString(8, name);
			ps.setString(9, _DDM_STRUCTURE_DEFINITION);

			ps.setLong(10, userId);
			ps.setString(11, userName);
			ps.setDate(12, now);

			ps.executeUpdate();

			return structureVersionId;
		}
	}

	private static final String _DDM_STRUCTURE_DEFINITION = JSONUtil.put(
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

	private static final String _DDM_STRUCTURE_LAYOUT_DEFINITION = JSONUtil.put(
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

	private final DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

}