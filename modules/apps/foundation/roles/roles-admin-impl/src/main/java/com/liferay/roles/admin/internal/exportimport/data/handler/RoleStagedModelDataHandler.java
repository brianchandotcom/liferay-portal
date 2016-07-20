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

package com.liferay.roles.admin.internal.exportimport.data.handler;

import com.liferay.exportimport.kernel.lar.ExportImportPathUtil;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandler;
import com.liferay.exportimport.lar.BaseStagedModelDataHandler;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.NoSuchResourceActionException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.GroupConstants;
import com.liferay.portal.kernel.model.Permission;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.ResourceTypePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.security.permission.PermissionConversionFilter;
import com.liferay.portal.kernel.security.permission.PermissionConverterUtil;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.ResourceBlockLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionService;
import com.liferay.portal.kernel.service.ResourceTypePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.xml.Element;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author David Mendez Gonzalez
 * @author Michael C. Han
 */
@Component(immediate = true, service = StagedModelDataHandler.class)
public class RoleStagedModelDataHandler
	extends BaseStagedModelDataHandler<Role> {

	public static final String[] CLASS_NAMES = {Role.class.getName()};

	@Override
	public void deleteStagedModel(Role role) throws PortalException {
		_roleLocalService.deleteRole(role);
	}

	@Override
	public void deleteStagedModel(
			String uuid, long groupId, String className, String extraData)
		throws PortalException {

		Group group = _groupLocalService.getGroup(groupId);

		Role role = _roleLocalService.fetchRoleByUuidAndCompanyId(
			uuid, group.getCompanyId());

		if (role != null) {
			deleteStagedModel(role);
		}
	}

	@Override
	public List<Role> fetchStagedModelsByUuidAndCompanyId(
		String uuid, long companyId) {

		List<Role> roles = new ArrayList<>();

		roles.add(
			_roleLocalService.fetchRoleByUuidAndCompanyId(uuid, companyId));

		return roles;
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
			_resourcePermissionLocalService.getRoleResourcePermissions(
				importedRole.getRoleId(),
				new int[] {
					ResourceConstants.SCOPE_COMPANY,
					ResourceConstants.SCOPE_GROUP_TEMPLATE
				},
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			_resourcePermissionLocalService.deleteResourcePermission(
				resourcePermission);
		}

		List<ResourcePermission> groupResourcePermissions =
			_resourcePermissionLocalService.getRoleResourcePermissions(
				importedRole.getRoleId(),
				new int[] {ResourceConstants.SCOPE_GROUP}, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ResourcePermission groupResourcePermission :
				groupResourcePermissions) {

			long groupId = GetterUtil.getLong(
				groupResourcePermission.getPrimKey());

			if ((groupId == portletDataContext.getCompanyGroupId()) ||
				(groupId == portletDataContext.getUserPersonalSiteGroupId())) {

				_resourcePermissionLocalService.deleteResourcePermission(
					groupResourcePermission);
			}
		}

		List<ResourceTypePermission> resourceTypePermissions =
			getResourceTypePermissions(portletDataContext, importedRole);

		for (ResourceTypePermission resourceTypePermission :
				resourceTypePermissions) {

			_resourceTypePermissionLocalService.deleteResourceTypePermission(
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

		Role existingRole = _roleLocalService.fetchRoleByUuidAndCompanyId(
			role.getUuid(), portletDataContext.getCompanyId());

		if (existingRole == null) {
			existingRole = _roleLocalService.fetchRole(
				portletDataContext.getCompanyId(), role.getName());
		}

		Role importedRole = null;

		if (existingRole == null) {
			serviceContext.setUuid(role.getUuid());

			importedRole = _roleLocalService.addRole(
				userId, null, 0, role.getName(), role.getTitleMap(),
				role.getDescriptionMap(), role.getType(), role.getSubtype(),
				serviceContext);
		}
		else {
			importedRole = _roleLocalService.updateRole(
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
			try {
				if (_resourceBlockLocalService.isSupported(
						permission.getName())) {

					importResourceBlock(
						portletDataContext, importedRole, permission);
				}
				else {
					importResourcePermissions(
						portletDataContext, importedRole, permission);
				}
			}
			catch (NoSuchResourceActionException nsrae) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Skip importing individually scoped permissions");
				}
			}
		}

		portletDataContext.importClassedModel(role, importedRole);
	}

	protected List<ResourceTypePermission> getResourceTypePermissions(
		PortletDataContext portletDataContext, Role importedRole) {

		DynamicQuery dynamicQuery =
			_resourceTypePermissionLocalService.dynamicQuery();

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

		return _resourceTypePermissionLocalService.dynamicQuery(dynamicQuery);
	}

	protected void importResourceBlock(
			PortletDataContext portletDataContext, Role importedRole,
			Permission permission)
		throws PortalException {

		int scope = permission.getScope();

		if (scope == ResourceConstants.SCOPE_COMPANY) {
			_resourceBlockLocalService.addCompanyScopePermission(
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

			_resourceBlockLocalService.addGroupScopePermission(
				portletDataContext.getCompanyId(), groupId,
				permission.getName(), importedRole.getRoleId(),
				permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
			_resourceBlockLocalService.addGroupScopePermission(
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
			_resourcePermissionService.addResourcePermission(
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

			_resourcePermissionService.addResourcePermission(
				groupId, portletDataContext.getCompanyId(),
				permission.getName(), ResourceConstants.SCOPE_GROUP,
				String.valueOf(groupId), importedRole.getRoleId(),
				permission.getActionId());
		}
		else if (scope == ResourceConstants.SCOPE_GROUP_TEMPLATE) {
			_resourcePermissionService.addResourcePermission(
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

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourceBlockLocalService(
		ResourceBlockLocalService resourceBlockLocalService) {

		_resourceBlockLocalService = resourceBlockLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionLocalService(
		ResourcePermissionLocalService resourcePermissionLocalService) {

		_resourcePermissionLocalService = resourcePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setResourcePermissionService(
		ResourcePermissionService resourcePermissionService) {

		_resourcePermissionService = resourcePermissionService;
	}

	@Reference(unbind = "-")
	protected void setResourceTypePermissionLocalService(
		ResourceTypePermissionLocalService resourceTypePermissionLocalService) {

		_resourceTypePermissionLocalService =
			resourceTypePermissionLocalService;
	}

	@Reference(unbind = "-")
	protected void setRoleLocalService(RoleLocalService roleLocalService) {
		_roleLocalService = roleLocalService;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		RoleStagedModelDataHandler.class);

	private GroupLocalService _groupLocalService;
	private final PermissionConversionFilter _permissionConversionFilter =
		new ImportExportPermissionConversionFilter();
	private ResourceBlockLocalService _resourceBlockLocalService;
	private ResourcePermissionLocalService _resourcePermissionLocalService;
	private ResourcePermissionService _resourcePermissionService;
	private ResourceTypePermissionLocalService
		_resourceTypePermissionLocalService;
	private RoleLocalService _roleLocalService;

}