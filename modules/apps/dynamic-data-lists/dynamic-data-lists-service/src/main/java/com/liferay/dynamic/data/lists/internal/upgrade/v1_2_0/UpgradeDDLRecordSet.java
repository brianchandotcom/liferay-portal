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

package com.liferay.dynamic.data.lists.internal.upgrade.v1_2_0;

import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.mapping.model.DDMFormInstance;
import com.liferay.dynamic.data.mapping.service.DDMFormInstanceLocalService;
import com.liferay.dynamic.data.mapping.service.DDMStructureLinkLocalService;
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

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Leonardo Barros
 */
public class UpgradeDDLRecordSet extends UpgradeProcess {

	public UpgradeDDLRecordSet(
		ClassNameLocalService classNameLocalService,
		DDMFormInstanceLocalService ddmFormInstanceLocalService,
		DDMStructureLinkLocalService ddmStructureLinkLocalService,
		PortletPreferencesLocalService portletPreferencesLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_classNameLocalService = classNameLocalService;
		_ddmFormInstanceLocalService = ddmFormInstanceLocalService;
		_ddmStructureLinkLocalService = ddmStructureLinkLocalService;
		_portletPreferencesLocalService = portletPreferencesLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	protected void deleteDDLRecordSet(long ddmStructureId, long recordSetId)
		throws Exception {

		_ddmStructureLinkLocalService.deleteStructureStructureLinks(
			ddmStructureId);

		try (PreparedStatement ps = connection.prepareStatement(
				"delete from DDLRecordSet where recordSetId = ?")) {

			ps.setLong(1, recordSetId);

			ps.executeUpdate();
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(2);

		sb.append("select DDLRecordSet.* from DDLRecordSet where ");
		sb.append("DDLRecordSet.scope = ?");

		try (PreparedStatement ps1 =
				connection.prepareStatement(sb.toString())) {

			ps1.setInt(1, DDLRecordSetConstants.SCOPE_FORMS);

			try (ResultSet rs = ps1.executeQuery()) {
				while (rs.next()) {
					long recordSetId = rs.getLong("recordSetId");

					DDMFormInstance ddmFormInstance =
						_ddmFormInstanceLocalService.createDDMFormInstance(
							recordSetId);

					long ddmStructureId = rs.getLong("DDMStructureId");

					ddmFormInstance.setCompanyId(rs.getLong("companyId"));

					ddmFormInstance.setGroupId(rs.getLong("groupId"));
					ddmFormInstance.setUserId(rs.getLong("userId"));
					ddmFormInstance.setUserName(rs.getString("userName"));
					ddmFormInstance.setVersionUserId(
						rs.getLong("versionUserId"));
					ddmFormInstance.setVersionUserName(
						rs.getString("versionUserName"));
					ddmFormInstance.setCreateDate(
						rs.getTimestamp("createDate"));
					ddmFormInstance.setModifiedDate(
						rs.getTimestamp("modifiedDate"));
					ddmFormInstance.setStructureId(ddmStructureId);
					ddmFormInstance.setVersion(rs.getString("version"));
					ddmFormInstance.setName(rs.getString("name"));
					ddmFormInstance.setDescription(rs.getString("description"));
					ddmFormInstance.setSettings(rs.getString("settings_"));
					ddmFormInstance.setLastPublishDate(
						rs.getTimestamp("lastPublishDate"));

					updateDDMStructure(ddmStructureId);
					updateDDMStructureLink(ddmStructureId);

					upgradeResourcePermission(
						recordSetId,
						"com.liferay.dynamic.data.mapping.model." +
							"DDMFormInstance",
						true);

					upgradeResourcePermission(
						ddmStructureId,
						"com.liferay.dynamic.data.mapping.model." +
							"DDMFormInstance-com.liferay.dynamic.data." +
								"mapping.model.DDMStructure",
						false);

					updateInstanceablePortletPreferences(
						ddmFormInstance.getFormInstanceId(), recordSetId,
						"com_liferay_dynamic_data_lists_form_web_portlet_" +
							"DDLFormPortlet",
						"com_liferay_dynamic_data_mapping_form_web_portlet_" +
							"DDMFormPortlet");

					deleteDDLRecordSet(ddmStructureId, recordSetId);

					_ddmFormInstanceLocalService.addDDMFormInstance(
						ddmFormInstance);
				}
			}
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
			long ddmFormInstanceId, long recordSetId, String oldPortletId,
			String newPortletId)
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
					ddmFormInstanceId, recordSetId, oldPortletId, newPortletId,
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


					_resourcePermissionLocalService.updateResourcePermission(
						resourcePermission);
				});

		actionableDynamicQuery.performActions();
	}

	private final ClassNameLocalService _classNameLocalService;
	private final DDMFormInstanceLocalService _ddmFormInstanceLocalService;
	private final DDMStructureLinkLocalService _ddmStructureLinkLocalService;
	private final PortletPreferencesLocalService
		_portletPreferencesLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;

}