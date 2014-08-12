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

package com.liferay.portlet.rolesadmin.lar;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.lar.ExportImportPathUtil;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.ResourceTypePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.security.permission.PermissionConversionFilter;
import com.liferay.portal.security.permission.PermissionConverterUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceBlockLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionServiceUtil;
import com.liferay.portal.service.ResourceTypePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;

import java.util.List;

/**
 * @author David Mendez Gonzalez
 * @author Michael C. Han
 */
public class RoleStagedModelDataHandler
	extends BaseStagedModelDataHandler<Role> {

	public static final String[] CLASS_NAMES = {Role.class.getName()};

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Role role = fetchStagedModelByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (role != null) {
			RoleLocalServiceUtil.deleteRole(role);
		}
	}

	@Override
	public Role fetchStagedModelByUuidAndCompanyId(
		String uuid, long companyId) {

		return RoleLocalServiceUtil.fetchRoleByUuidAndCompanyId(
			uuid, companyId);
	}

	@Override
	public String[] getClassNames() {
		return CLASS_NAMES;
	}

	@Override
	public String getDisplayName(Role role) {
		return role.getName();
	}

	protected void deleteRolePermissions(
		PortletDataContext portletDataContext, Role importedRole) {

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				importedRole.getRoleId(),
				new int[] {
					ResourceConstants.SCOPE_COMPANY,
					ResourceConstants.SCOPE_GROUP_TEMPLATE
				},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermissionLocalServiceUtil.deleteResourcePermission(
				resourcePermission);
		}

		List<ResourcePermission> groupResourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				importedRole.getRoleId(),
				new int[] {ResourceConstants.SCOPE_GROUP},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ResourcePermission groupResourcePermission :
				groupResourcePermissions) {

			long groupId = GetterUtil.getLong(
				groupResourcePermission.getPrimKey());

			if ((groupId == portletDataContext.getCompanyGroupId()) ||
				(groupId == portletDataContext.getUserPersonalSiteGroupId())) {

				ResourcePermissionLocalServiceUtil.deleteResourcePermission(
					groupResourcePermission);
			}
		}

		List<ResourceTypePermission> resourceTypePermissions =
			getResourceTypePermissions(portletDataContext, importedRole);

		for (ResourceTypePermission resourceTypePermission :
				resourceTypePermissions) {

			ResourceTypePermissionLocalServiceUtil.deleteResourceTypePermission(
				resourceTypePermission);
		}
	}

	@Override
	protected void doExportStagedModel(
			PortletDataContext portletDataContext, Role role)
		throws Exception {

		String permissionsPath = ExportImportPathUtil.getModelPath(
			role, "permissions.xml");

		List<Permission> permissions =
			PermissionConverterUtil.convertPermissions(
				role, _permissionConversionFilter);

		String xml = portletDataContext.toXML(permissions);

		portletDataContext.addZipEntry(permissionsPath, xml);

		Element roleElement = portletDataContext.getExportDataElement(role);

		portletDataContext.addClassedModel(
			roleElement, ExportImportPathUtil.getModelPath(role), role);
	}

	@Override
	protected void doImportStagedModel(
			PortletDataContext portletDataContext, Role role)
		throws Exception {

		long userId = portletDataContext.getUserId(role.getUserUuid());

		ServiceContext serviceContext = portletDataContext.createServiceContext(
			role);

		Role existingRole = fetchStagedModelByUuidAndCompanyId(
			role.getUuid(), portletDataContext.getCompanyId());

		if (existingRole == null) {
			existingRole = RoleLocalServiceUtil.fetchRole(
				portletDataContext.getCompanyId(), role.getName());
		}

		Role importedRole = null;

		if (existingRole == null) {
			serviceContext.setUuid(role.getUuid());

			importedRole = RoleLocalServiceUtil.addRole(
				userId, null, 0, role.getName(), role.getTitleMap(),
				role.getDescriptionMap(), role.getType(), role.getSubtype(),
				serviceContext);
		}
		else {
			importedRole = RoleLocalServiceUtil.updateRole(
				existingRole.getRoleId(), role.getName(), role.getTitleMap(),
				role.getDescriptionMap(), role.getSubtype(), serviceContext);

			deleteRolePermissions(portletDataContext, importedRole);
		}

		String permissionsPath = ExportImportPathUtil.getModelPath(
			role, "permissions.xml");

		List<Permission> permissions =
			(List<Permission>)portletDataContext.getZipEntryAsObject(
				permissionsPath);

		for (Permission permission : permissions) {
			if (ResourceBlockLocalServiceUtil.isSupported(
					permission.getName())) {

				importResourceBlock(
					portletDataContext, importedRole, permission);
			}
			else {
				importResourcePermissions(
					portletDataContext, importedRole, permission);
			}
		}

		portletDataContext.importClassedModel(role, importedRole);
	}

	protected List<ResourceTypePermission> getResourceTypePermissions(
		PortletDataContext portletDataContext, Role importedRole) {

		DynamicQuery dynamicQuery =
			ResourceTypePermissionLocalServiceUtil.dynamicQuery();

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(
			companyIdProperty.eq(portletDataContext.getCompanyId()));

		Junction junction = RestrictionsFactoryUtil.disjunction();

		long[] permissibleGroupIds = {
			GroupConstants.DEFAULT_PARENT_GROUP_ID,
			portletDataContext.getCompanyId(),
			portletDataContext.getCompanyGroupId(),
			portletDataContext.getUserPersonalSiteGroupId()
		};

		for (long permissibleGroupId : permissibleGroupIds) {
			Property property = PropertyFactoryUtil.forName("groupId");

			junction.add(property.eq(permissibleGroupId));
		}

		dynamicQuery.add(junction);

		Property roleIdProperty = PropertyFactoryUtil.forName("roleId");

		dynamicQuery.add(roleIdProperty.eq(importedRole.getRoleId()));

		return ResourceTypePermissionLocalServiceUtil.dynamicQuery(
			dynamicQuery);
	}

	protected void importResourceBlock(
			PortletDataContext portletDataContext, Role importedRole,
			Permission permission)
		throws PortalException {

		int scope = permission.getScope();

		if (scope == ResourceConstants.SCOPE_COMPANY) {
			ResourceBlockLocalServiceUtil.addCompanyScopePermission(
				portletDataContext.getCompanyId(), permission.getName(),
				importedRole.getRoleId(), permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {
			long groupId = portletDataContext.getCompanyGroupId();

			long sourceGroupId = GetterUtil.getLong(permission.getPrimKey());

			if (sourceGroupId ==
					portletDataContext.getSourceUserPersonalSiteGroupId()) {

				groupId = portletDataContext.getUserPersonalSiteGroupId();
			}

			ResourceBlockLocalServiceUtil.addGroupScopePermission(
				portletDataContext.getCompanyId(), groupId,
				permission.getName(), importedRole.getRoleId(),
				permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
			ResourceBlockLocalServiceUtil.addGroupScopePermission(
				portletDataContext.getCompanyId(),
				GroupConstants.DEFAULT_PARENT_GROUP_ID, permission.getName(),
				importedRole.getRoleId(), permission.getActionId());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Individually scoped permissions are not exported");
			}
		}
	}

	protected void importResourcePermissions(
			PortletDataContext portletDataContext, Role importedRole,
			Permission permission)
		throws PortalException {

		int scope = permission.getScope();

		if (scope == ResourceConstants.SCOPE_COMPANY) {
			ResourcePermissionServiceUtil.addResourcePermission(
				portletDataContext.getCompanyGroupId(),
				portletDataContext.getCompanyId(), permission.getName(), scope,
				String.valueOf(portletDataContext.getCompanyId()),
				importedRole.getRoleId(), permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP) {
			long groupId = portletDataContext.getCompanyGroupId();

			long sourceGroupId = GetterUtil.getLong(permission.getPrimKey());

			if (sourceGroupId ==
					portletDataContext.getSourceUserPersonalSiteGroupId()) {

				groupId = portletDataContext.getUserPersonalSiteGroupId();
			}

			ResourcePermissionServiceUtil.addResourcePermission(
				groupId, portletDataContext.getCompanyId(),
				permission.getName(), ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), importedRole.getRoleId(),
				permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
			ResourcePermissionServiceUtil.addResourcePermission(
				GroupConstants.DEFAULT_PARENT_GROUP_ID,
				portletDataContext.getCompanyId(), permission.getName(),
				ResourceConstants.SCOPE_GROUP_TEMPLATE,
				String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID),
				importedRole.getRoleId(), permission.getActionId());
		}
		else {
			if (_log.isDebugEnabled()) {
				_log.debug("Individually scoped permissions are not imported");
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleStagedModelDataHandler.class);

	private final PermissionConversionFilter _permissionConversionFilter =
		new ImportExportPermissionConversionFilter();

}