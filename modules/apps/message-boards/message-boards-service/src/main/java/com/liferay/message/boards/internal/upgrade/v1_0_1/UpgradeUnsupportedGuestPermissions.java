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

package com.liferay.message.boards.internal.upgrade.v1_0_1;

import com.liferay.message.boards.constants.MBConstants;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.portal.kernel.dao.db.DBProcessContext;
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
	public void upgrade(DBProcessContext dbProcessContext)
		throws UpgradeException {

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
		catch (PortalException pe) {
			throw new UpgradeException(pe);
		}
	}

	private final ResourceActionLocalService _resourceActionLocalService;
	private final ResourcePermissionLocalService
		_resourcePermissionLocalService;
	private final RoleLocalService _roleLocalService;

}