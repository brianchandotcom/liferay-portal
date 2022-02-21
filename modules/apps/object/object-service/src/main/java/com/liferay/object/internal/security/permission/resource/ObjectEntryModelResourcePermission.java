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

package com.liferay.object.internal.security.permission.resource;

import com.liferay.object.model.ObjectEntry;
import com.liferay.object.service.ObjectEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

/**
 * @author Marco Leo
 * @author Brian Wing Shun Chan
 */
public class ObjectEntryModelResourcePermission
	implements ModelResourcePermission<ObjectEntry> {

	public ObjectEntryModelResourcePermission(
		String modelName, ObjectEntryLocalService objectEntryLocalService,
		PortletResourcePermission portletResourcePermission) {

		_modelName = modelName;
		_objectEntryLocalService = objectEntryLocalService;
		_portletResourcePermission = portletResourcePermission;
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long objectEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelName, objectEntryId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, objectEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, _modelName, objectEntry.getObjectEntryId(),
				actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long objectEntryId,
			String actionId)
		throws PortalException {

		ObjectEntry objectEntry = _objectEntryLocalService.getObjectEntry(
			objectEntryId);

		return contains(permissionChecker, objectEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, ObjectEntry objectEntry,
			String actionId)
		throws PortalException {

		User user = permissionChecker.getUser();

		if (user.isDefaultUser()) {
			return permissionChecker.hasPermission(
				objectEntry.getGroupId(), _modelName,
				objectEntry.getObjectEntryId(), actionId);
		}

		if (permissionChecker.hasOwnerPermission(
				permissionChecker.getCompanyId(), _modelName,
				objectEntry.getObjectEntryId(), objectEntry.getUserId(),
				actionId) ||
			permissionChecker.hasPermission(
				objectEntry.getGroupId(), _modelName,
				objectEntry.getObjectEntryId(), actionId)) {

			return true;
		}

		return false;
	}

	@Override
	public String getModelName() {
		return _modelName;
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private final String _modelName;
	private final ObjectEntryLocalService _objectEntryLocalService;
	private final PortletResourcePermission _portletResourcePermission;

}