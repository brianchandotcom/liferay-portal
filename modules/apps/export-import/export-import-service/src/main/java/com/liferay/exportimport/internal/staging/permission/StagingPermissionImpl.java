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

package com.liferay.exportimport.internal.staging.permission;

import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PropsValues;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(immediate = true, service = StagingPermission.class)
public class StagingPermissionImpl implements StagingPermission {

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, Group group, String className,
		long classPK, String portletId, String actionId) {

		try {
			return _doHasPermission(group, portletId, actionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	@Override
	public Boolean hasPermission(
		PermissionChecker permissionChecker, long groupId, String className,
		long classPK, String portletId, String actionId) {

		try {
			return _doHasPermission(
				_groupLocalService.getGroup(groupId), portletId, actionId);
		}
		catch (Exception exception) {
			_log.error(exception, exception);
		}

		return null;
	}

	@Reference(unbind = "-")
	protected void setGroupLocalService(GroupLocalService groupLocalService) {
		_groupLocalService = groupLocalService;
	}

	private Boolean _doHasPermission(
			Group group, String portletId, String actionId)
		throws Exception {

		if (!PropsValues.STAGING_LIVE_GROUP_LOCKING_ENABLED) {
			return null;
		}

		if (!actionId.equals(ActionKeys.ACCESS) &&
			!actionId.equals(ActionKeys.ACCESS_IN_CONTROL_PANEL) &&
			!actionId.equals(ActionKeys.ADD_DISCUSSION) &&
			!actionId.equals(ActionKeys.ADD_TO_PAGE) &&
			!actionId.equals(ActionKeys.ASSIGN_MEMBERS) &&
			!actionId.equals(ActionKeys.CONFIGURATION) &&
			!actionId.equals(ActionKeys.CUSTOMIZE) &&
			!actionId.equals(ActionKeys.DELETE) &&
			!actionId.equals(ActionKeys.DELETE_DISCUSSION) &&
			!actionId.equals(ActionKeys.UPDATE_DISCUSSION) &&
			!actionId.equals(ActionKeys.VIEW) &&
			group.hasLocalOrRemoteStagingGroup() &&
			(Validator.isNull(portletId) || group.isStagedPortlet(portletId))) {

			return false;
		}

		return null;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		StagingPermissionImpl.class);

	private GroupLocalService _groupLocalService;

}