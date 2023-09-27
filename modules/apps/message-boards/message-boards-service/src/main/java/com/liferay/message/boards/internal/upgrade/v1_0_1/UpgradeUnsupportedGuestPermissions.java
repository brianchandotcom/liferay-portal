/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.internal.upgrade.v1_0_1;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceAction;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.role.RoleConstants;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.service.ResourceActionLocalService;
import com.liferay.portal.kernel.service.ResourcePermissionLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeStep;

/**
 * @author Alejandro Tardín
 */
public class UpgradeUnsupportedGuestPermissions implements UpgradeStep {

	public UpgradeUnsupportedGuestPermissions(
		ResourceActionLocalService resourceActionLocalService,
		ResourcePermissionLocalService resourcePermissionLocalService,
		RoleLocalService roleLocalService) {

		_resourceActionLocalService = resourceActionLocalService;
		_resourcePermissionLocalService = resourcePermissionLocalService;
		_roleLocalService = roleLocalService;
	}

	@Override
	public void upgrade() throws UpgradeException {
		_removeResourceActions(MBCategory.class.getName(), ActionKeys.DELETE);
		_removeResourceActions(
			MBCategory.class.getName(), ActionKeys.MOVE_THREAD);
		_removeResourceActions(
			MBCategory.class.getName(), ActionKeys.PERMISSIONS);

		_removeResourceActions(MBMessage.class.getName(), ActionKeys.DELETE);
		_removeResourceActions(
			MBMessage.class.getName(), ActionKeys.PERMISSIONS);

		_removeResourceActions(
			MBConstants.RESOURCE_NAME, ActionKeys.LOCK_THREAD);
		_removeResourceActions(
			MBConstants.RESOURCE_NAME, ActionKeys.MOVE_THREAD);

		_removeResourceActions(MBThread.class.getName(), ActionKeys.DELETE);
	}

	private void _removeResourceAction(
			ResourcePermission resourcePermission,
			ResourceAction resourceAction)
		throws PortalException {

		Role guestRole = _roleLocalService.getRole(
			resourcePermission.getCompanyId(), RoleConstants.GUEST);

		if (guestRole.getRoleId() != resourcePermission.getRoleId()) {
			return;
		}

		if (_resourcePermissionLocalService.hasActionId(
				resourcePermission, resourceAction)) {

			resourcePermission.removeResourceAction(
				resourceAction.getActionId());

			_resourcePermissionLocalService.updateResourcePermission(
				resourcePermission);
		}
	}

	private void _removeResourceActions(String resourceName, String actionId)
		throws UpgradeException {

		try {
			ResourceAction resourceAction =
				_resourceActionLocalService.fetchResourceAction(
					resourceName, actionId);

			if (resourceAction == null) {
				return;
			}

			ActionableDynamicQuery actionableDynamicQuery =
				_resourcePermissionLocalService.getActionableDynamicQuery();

			actionableDynamicQuery.setAddCriteriaMethod(
				dynamicQuery -> dynamicQuery.add(
					RestrictionsFactoryUtil.eq("name", resourceName)));
			actionableDynamicQuery.setPerformActionMethod(
				(ResourcePermission resourcePermission) ->
					_removeResourceAction(resourcePermission, resourceAction));

			actionableDynamicQuery.performActions();
		}
		catch (PortalException portalException) {
			throw new UpgradeException(portalException);
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}