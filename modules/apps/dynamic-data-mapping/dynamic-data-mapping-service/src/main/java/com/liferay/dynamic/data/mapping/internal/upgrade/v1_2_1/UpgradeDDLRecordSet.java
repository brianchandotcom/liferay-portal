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

package com.liferay.dynamic.data.mapping.internal.upgrade.v1_2_1;

import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceConstants;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.PortletPreferencesLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Objects;

/**
 * @author Leonardo Barros
 */
public class UpgradeDDLRecordSet extends UpgradeProcess {

	public UpgradeDDLRecordSet(
		ClassNameLocalService classNameLocalService,
		CounterLocalService counterLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_classNameLocalService = classNameLocalService;
		_counterLocalService = counterLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	public long getLatestStructureVersionId(long ddmStructureId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"select structureVersionId from DDMStructureVersion where " +
					"structureId = ? order by createDate desc")) {

			ps.setLong(1, ddmStructureId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					return rs.getLong("structureVersionId");
				}

				return 0;
			}
		}
	}

	protected void deleteDDLRecordSet(long ddmStructureId, long recordSetId)
		throws SQLException {

		deleteStructureStructureLinks(ddmStructureId);

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecordSet where recordSetId = ?")) {

			ps.setLong(1, recordSetId);

			ps.executeUpdate();
		}
	}

	protected void deleteStructureStructureLinks(long ddmStructureId)
		throws SQLException {

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDMStructureLink where structureId = ?")) {

			ps.setLong(1, ddmStructureId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(5);

		sb.append("insert into DDMFormInstance(uuid_, formInstanceId, ");
		sb.append("groupId, companyId, userId, userName, versionUserId, ");
		sb.append("versionUserName, createDate, modifiedDate, structureId, ");
		sb.append("version, name, description, settings_, lastPublishDate) ");
		sb.append("values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps1 = connection.prepareStatement(
				"select DDLRecordSet.* from DDLRecordSet where " +
					"DDLRecordSet.scope = " + _SCOPE_FORMS);
			ResultSet rs = ps1.executeQuery();
			PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			while (rs.next()) {
				long recordSetId = rs.getLong("recordSetId");
				long structureId = rs.getLong("DDMStructureId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				String name = rs.getString("name");
				String description = rs.getString("description");
				String settings = rs.getString("settings_");
				Timestamp lastPublishDate = rs.getTimestamp("lastPublishDate");

				ps2.setString(1, PortalUUIDUtil.generate());
				ps2.setLong(2, recordSetId);
				ps2.setLong(3, groupId);
				ps2.setLong(4, companyId);
				ps2.setLong(5, userId);
				ps2.setString(6, userName);
				ps2.setLong(7, userId);
				ps2.setString(8, userName);
				ps2.setTimestamp(9, createDate);
				ps2.setTimestamp(10, rs.getTimestamp("modifiedDate"));
				ps2.setLong(11, structureId);
				ps2.setString(12, DDMFormInstanceConstants.VERSION_DEFAULT);
				ps2.setString(13, name);
				ps2.setString(14, description);
				ps2.setString(15, settings);
				ps2.setTimestamp(16, lastPublishDate);

				updateDDMStructure(structureId);
				updateDDMStructureLink(structureId);

				upgradeDDMFormInstanceVersion(
					groupId, companyId, userId, userName, createDate,
					recordSetId, structureId, name, description, settings,
					lastPublishDate);

				upgradeResourcePermission(
					recordSetId,
					"com.liferay.dynamic.data.mapping.model.DDMFormInstance",
					true);

				upgradeResourcePermission(
					structureId,
					"com.liferay.dynamic.data.mapping.model." +
						"DDMFormInstance-com.liferay.dynamic.data.mapping." +
							"model.DDMStructure",
					false);

				upgradeResourcePermission(
					"com_liferay_dynamic_data_lists_form_web_portlet_" +
						"DDLFormAdminPortlet",
					"com_liferay_dynamic_data_mapping_form_web_portlet_" +
						"DDMFormAdminPortlet");

				updateInstanceablePortletPreferences(
					recordSetId,
					"com_liferay_dynamic_data_lists_form_web_portlet_" +
						"DDLFormPortlet",
					"com_liferay_dynamic_data_mapping_form_web_portlet_" +
						"DDMFormPortlet");

				deleteDDLRecordSet(structureId, recordSetId);

				ps2.addBatch();
			}

			ps2.executeBatch();
		}
	}

	protected long getNewActionIds(long oldActionIds) {
		long bit4 = (oldActionIds >> 3) & 1;
		long bit5 = (oldActionIds >> 4) & 1;

		if (bit4 == bit5) {
			return oldActionIds;
		}

		int mask = (1 << 3) | (1 << 4);

		return oldActionIds ^ mask;
	}

	protected void updateDDMStructure(long ddmStructureId) throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("update DDMStructure set classNameId = ? where structureId ");
		sb.append("= ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			ps.setLong(2, ddmStructureId);

			ps.executeUpdate();
		}
	}

	protected void updateDDMStructureLink(long ddmStructureId)
		throws Exception {

		StringBundler sb = new StringBundler(2);

		sb.append("update DDMStructureLink set classNameId = ? where ");
		sb.append("structureId = ?");

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			ps.setLong(
				1,
				_classNameLocalService.getClassNameId(
					DDMFormInstance.class.getName()));
			ps.setLong(2, ddmStructureId);

			ps.executeUpdate();
		}
	}

	protected void updateInstanceablePortletPreferences(
			long recordSetId, String oldPortletId, String newPortletId)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_portletPreferencesLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Junction junction = RestrictionsFactoryUtil.disjunction();

				Property property = PropertyFactoryUtil.forName("portletId");

				junction.add(property.eq(oldPortletId));
				junction.add(property.like(oldPortletId + "_INSTANCE_%"));
				junction.add(
					property.like(oldPortletId + "_USER_%_INSTANCE_%"));

				dynamicQuery.add(junction);
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<PortletPreferences>)
				portletPreference -> updatePortletPreferences(
					recordSetId, recordSetId, oldPortletId, newPortletId,
					portletPreference));

		actionableDynamicQuery.performActions();
	}

	protected void updatePortletPreferences(
		long ddmFormInstanceId, long recordSetId, String oldPortletId,
		String newPortletId, PortletPreferences portletPreferences) {

		portletPreferences.setPortletId(
			StringUtil.replace(
				portletPreferences.getPortletId(), oldPortletId, newPortletId));

		String preferences = portletPreferences.getPreferences();

		preferences = StringUtil.replace(
			preferences, String.valueOf(recordSetId),
			String.valueOf(ddmFormInstanceId));

		portletPreferences.setPreferences(preferences);

		_portletPreferencesLocalService.updatePortletPreferences(
			portletPreferences);
	}

	protected void upgradeDDMFormInstanceVersion(
			long groupId, long companyId, long userId, String userName,
			Timestamp createDate, long formInstanceId, long structureId,
			String name, String description, String settings,
			Timestamp statusDate)
		throws SQLException {

		StringBundler sb = new StringBundler(6);

		sb.append("insert into DDMFormInstanceVersion(formInstanceVersionId, ");
		sb.append("groupId, companyId, userId, userName, createDate, ");
		sb.append("formInstanceId, structureVersionId, name, description, ");
		sb.append("settings_, version, status, statusByUserId, ");
		sb.append("statusByUserName, statusDate) values(?, ?, ?, ?, ?, ?, ?, ");
		sb.append("?, ?, ?, ?, ?, ?, ?, ?, ?)");

		try (PreparedStatement ps2 =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection, sb.toString())) {

			ps2.setLong(1, _counterLocalService.increment());
			ps2.setLong(2, groupId);
			ps2.setLong(3, companyId);
			ps2.setLong(4, userId);
			ps2.setString(5, userName);
			ps2.setTimestamp(6, createDate);
			ps2.setLong(7, formInstanceId);
			ps2.setLong(8, getLatestStructureVersionId(structureId));
			ps2.setString(9, name);
			ps2.setString(10, description);
			ps2.setString(11, settings);
			ps2.setString(12, DDMFormInstanceConstants.VERSION_DEFAULT);
			ps2.setInt(13, WorkflowConstants.STATUS_APPROVED);
			ps2.setLong(14, userId);
			ps2.setString(15, userName);
			ps2.setTimestamp(16, statusDate);

			ps2.execute();
		}
	}

	protected void upgradeResourcePermission(
			long primKeyId, String name, boolean updateActionIds)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("primKey");

				dynamicQuery.add(nameProperty.eq(String.valueOf(primKeyId)));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ResourcePermission>)
				resourcePermission -> {
					resourcePermission.setName(name);

					if (updateActionIds) {
						resourcePermission.setActionIds(
							getNewActionIds(resourcePermission.getActionIds()));
					}

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	protected void upgradeResourcePermission(String oldName, String newName)
		throws Exception {

		ActionableDynamicQuery actionableDynamicQuery =
			_resourcePermissionLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property nameProperty = PropertyFactoryUtil.forName("name");

				dynamicQuery.add(nameProperty.eq(oldName));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(ActionableDynamicQuery.PerformActionMethod<ResourcePermission>)
				resourcePermission -> {
					resourcePermission.setName(newName);

					if (Objects.equals(
							resourcePermission.getPrimKey(), oldName)) {

						resourcePermission.setPrimKey(newName);
					}

					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	private static final int _SCOPE_FORMS = 2;

	private final ClassNameLocalService _classNameLocalService;
	private final CounterLocalService _counterLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}