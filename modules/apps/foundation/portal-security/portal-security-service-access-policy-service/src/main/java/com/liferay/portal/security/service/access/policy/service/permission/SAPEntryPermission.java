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

package com.liferay.portal.security.service.access.policy.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.security.service.access.policy.model.SAPEntry;
import com.liferay.portal.security.service.access.policy.service.SAPEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 * @deprecated As of 2.1.0, with no direct replacement
 */
@Component(
	property = {
		"model.class.name=com.liferay.portal.security.service.access.policy.model.SAPEntry"
	}
)
@Deprecated
public class SAPEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long sapEntryId,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, sapEntryId, actionId);
	}

	public static void check(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, sapEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, classPK, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		return _entryModelResourcePermission.contains(
			permissionChecker, sapEntry, actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		_entryModelResourcePermission.check(
			permissionChecker, primaryKey, actionId);
	}

	@Reference(
		target = "(model.class.name=com.liferay.portal.security.service.access.policy.model.SAPEntry)",
		unbind = "-"
	)
	protected void setModelResourcePermission(
		ModelResourcePermission<SAPEntry> modelResourcePermission) {

		_entryModelResourcePermission = modelResourcePermission;
	}

	protected void setSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {
	}

	private static ModelResourcePermission<SAPEntry>
		_entryModelResourcePermission;

}