/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.NoSuchResourceException;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Tobias Kaefer
 * @author Douglas Wong
 * @author Matthew Kong
 * @author Raymond Augé
 */
public class VerifyPermission extends VerifyProcess {

	protected void checkPermissions() throws Exception {
		List<String> modelNames = ResourceActionsUtil.getModelNames();

		for (String modelName : modelNames) {
			List<String> actionIds =
				ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, actionIds, true);
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			try {
				deleteDefaultPrivateLayoutPermissions_6(companyId);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_1to4(long companyId)
		throws Exception {

		long defaultUserId = UserLocalServiceUtil.getDefaultUserId(companyId);

		List<Permission> permissions =
			PermissionLocalServiceUtil.getUserPermissions(defaultUserId);

		for (Permission permission : permissions) {
			Resource resource = ResourceLocalServiceUtil.getResource(
				permission.getResourceId());

			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(
					resource.getCodeId());

			if (isPrivateLayout(
					resourceCode.getName(), resource.getPrimKey())) {

				String[] actionIds = new String[] {permission.getActionId()};

				PermissionLocalServiceUtil.unsetUserPermissions(
					defaultUserId, actionIds, permission.getResourceId());
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_5(long companyId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		List<Permission> permissions =
			PermissionLocalServiceUtil.getRolePermissions(role.getRoleId());

		for (Permission permission : permissions) {
			Resource resource = ResourceLocalServiceUtil.getResource(
				permission.getResourceId());

			ResourceCode resourceCode =
				ResourceCodeLocalServiceUtil.getResourceCode(
					resource.getCodeId());

			if (isPrivateLayout(
					resourceCode.getName(), resource.getPrimKey())) {

				PermissionLocalServiceUtil.unsetRolePermission(
					role.getRoleId(), permission.getPermissionId());
			}
		}
	}

	protected void deleteDefaultPrivateLayoutPermissions_6(long companyId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			companyId, RoleConstants.GUEST);

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.getRoleResourcePermissions(
				role.getRoleId());

		for (ResourcePermission resourcePermission : resourcePermissions) {
			if (isPrivateLayout(
					resourcePermission.getName(),
					resourcePermission.getPrimKey())) {

				ResourcePermissionLocalServiceUtil.deleteResourcePermission(
					resourcePermission.getResourcePermissionId());
			}
		}
	}

	@Override
	protected void doVerify() throws Exception {
		deleteDefaultPrivateLayoutPermissions();

		checkPermissions();
		fixOrganizationRolePermissions();
	}

	protected void fixOrganizationRolePermissions() throws Exception {
		fixOrganizationRolePermissions_6();
		
		PermissionCacheUtil.clearCache();
	}

	protected void fixOrganizationRolePermissions_5() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ResourceCode.class);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", Organization.class.getName()));

		List<ResourceCode> resouceCodes =
			ResourceCodeLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (ResourceCode resourceCode : resouceCodes) {
			dynamicQuery = DynamicQueryFactoryUtil.forClass(Resource.class);

			dynamicQuery.add(
				RestrictionsFactoryUtil.eq("codeId", resourceCode.getCodeId()));

			List<Resource> resources = ResourceLocalServiceUtil.dynamicQuery(
				dynamicQuery);

			for (Resource resource : resources) {
				dynamicQuery = DynamicQueryFactoryUtil.forClass(
					Permission.class);

				dynamicQuery.add(
					RestrictionsFactoryUtil.eq(
						"resourceId", resource.getResourceId()));

				List<Permission> permissions =
					PermissionLocalServiceUtil.dynamicQuery(dynamicQuery);

				processPermissions(resource, permissions);
			}
		}
	}

	protected void fixOrganizationRolePermissions_6() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			ResourcePermission.class);

		dynamicQuery.add(
			RestrictionsFactoryUtil.eq("name", Organization.class.getName()));

		List<ResourcePermission> resourcePermissions =
			ResourcePermissionLocalServiceUtil.dynamicQuery(dynamicQuery);

		for (ResourcePermission resourcePermission : resourcePermissions) {
			ResourcePermission groupResourcePermission = null;

			try {
				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId());
			}
			catch (Exception e) {
				ResourcePermissionLocalServiceUtil.setResourcePermissions(
					resourcePermission.getCompanyId(), Group.class.getName(),
					resourcePermission.getScope(),
					resourcePermission.getPrimKey(),
					resourcePermission.getRoleId(),
					ResourcePermissionLocalServiceImpl.EMPTY_ACTION_IDS);

				groupResourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						resourcePermission.getCompanyId(),
						Group.class.getName(), resourcePermission.getScope(),
						resourcePermission.getPrimKey(),
						resourcePermission.getRoleId());
			}

			long organizationActions = resourcePermission.getActionIds();
			long groupActions = groupResourcePermission.getActionIds();

			for (Object[] actionIdToMask : _ORGANIZATION_ACTION_IDS_TO_MASKS) {
				long organizationActionMask = (Long)actionIdToMask[1];
				long groupActionMask = (Long)actionIdToMask[2];

				if ((organizationActions & organizationActionMask) ==
						organizationActionMask) {

					organizationActions =
						organizationActions & (~organizationActionMask);
					groupActions = groupActions | groupActionMask;
				}
			}

			try {
				resourcePermission.resetOriginalValues();

				resourcePermission.setActionIds(organizationActions);

				ResourcePermissionLocalServiceUtil.updateResourcePermission(
					resourcePermission, false);

				groupResourcePermission.resetOriginalValues();
				groupResourcePermission.setActionIds(groupActions);

				ResourcePermissionLocalServiceUtil.updateResourcePermission(
					groupResourcePermission, false);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected boolean isPrivateLayout(String name, String primKey)
		throws Exception {

		if (!name.equals(Layout.class.getName())) {
			return false;
		}

		long plid = GetterUtil.getLong(primKey);

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		if (layout.isPublicLayout() || layout.isTypeControlPanel()) {
			return false;
		}

		return true;
	}

	protected void processPermissions(
			Resource resource, List<Permission> permissions)
		throws Exception {

		Resource groupResource = null;

		try {
			groupResource = ResourceLocalServiceUtil.getResource(
				resource.getCompanyId(), Group.class.getName(),
				resource.getScope(), resource.getPrimKey());
		}
		catch (NoSuchResourceException nsre) {
			groupResource = ResourceLocalServiceUtil.addResource(
				resource.getCompanyId(), Group.class.getName(),
				resource.getScope(), resource.getPrimKey());
		}

		for (Permission permission : permissions) {
			for (Object[] actionIdToMask : _ORGANIZATION_ACTION_IDS_TO_MASKS) {
				String actionId = (String)actionIdToMask[0];
				long mask = (Long)actionIdToMask[2];

				if (!actionId.equals(permission.getActionId())) {
					continue;
				}

				try {
					if (mask != 0L) {
						permission.resetOriginalValues();

						permission.setResourceId(groupResource.getResourceId());

						PermissionLocalServiceUtil.updatePermission(
							permission, false);
					}
					else {
						PermissionLocalServiceUtil.deletePermission(
							permission.getPermissionId());
					}
				}
				catch (Exception e) {
					_log.error(e, e);
				}

				break;
			}
		}
	}

	private static final Object[][] _ORGANIZATION_ACTION_IDS_TO_MASKS =
		new Object[][] {
			new Object[] {"APPROVE_PROPOSAL", 2L, 0L},
			new Object[] {ActionKeys.ASSIGN_MEMBERS, 4L, 4L},
			new Object[] {"ASSIGN_REVIEWER", 8L, 0L},
			new Object[] {ActionKeys.MANAGE_ARCHIVED_SETUPS, 128L, 128L},
			new Object[] {ActionKeys.MANAGE_LAYOUTS, 256L, 256L},
			new Object[] {ActionKeys.MANAGE_STAGING, 512L, 512L},
			new Object[] {ActionKeys.MANAGE_TEAMS, 2048L, 1024L},
			new Object[] {ActionKeys.PUBLISH_STAGING, 16384L, 4096L}
		};

	private static Log _log = LogFactoryUtil.getLog(VerifyPermission.class);

}