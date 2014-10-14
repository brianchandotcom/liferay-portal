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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.impl.ResourcePermissionLocalServiceImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortletKeys;

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

		List<String> portletNames = ResourceActionsUtil.getPortletNames();

		for (String portletName : portletNames) {
			List<String> actionIds =
				ResourceActionsUtil.getPortletResourceActions(portletName);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portletName, actionIds, true);
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
		fixDockbarPermissions();
		fixOrganizationRolePermissions();
	}

	protected void fixDockbarPermissions() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			try {
				Role role = RoleLocalServiceUtil.getRole(
					companyId, RoleConstants.USER);

				ResourcePermissionLocalServiceUtil.addResourcePermission(
					companyId, PortletKeys.DOCKBAR,
					ResourceConstants.SCOPE_COMPANY,
					String.valueOf(role.getCompanyId()), role.getRoleId(),
					ActionKeys.VIEW);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(e, e);
				}
			}
		}
	}

	protected void fixOrganizationRolePermissions() throws Exception {
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
					resourcePermission);

				groupResourcePermission.resetOriginalValues();
				groupResourcePermission.setActionIds(groupActions);

				ResourcePermissionLocalServiceUtil.updateResourcePermission(
					groupResourcePermission);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		PermissionCacheUtil.clearCache();
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

	private static final Log _log = LogFactoryUtil.getLog(
		VerifyPermission.class);

}