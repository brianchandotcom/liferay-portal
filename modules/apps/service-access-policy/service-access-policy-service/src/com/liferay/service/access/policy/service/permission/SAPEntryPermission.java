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

package com.liferay.service.access.policy.service.permission;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.BaseModelPermissionChecker;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.service.access.policy.model.SAPEntry;
import com.liferay.service.access.policy.service.SAPEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Mika Koivisto
 */
@Component(
	property = {
		"model.class.name=com.liferay.service.access.policy.model.SAPEntry"
	}
)
public class SAPEntryPermission implements BaseModelPermissionChecker {

	public static void check(
			PermissionChecker permissionChecker, long sapEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sapEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPEntry.class.getName(), sapEntryId,
				actionId);
		}
	}

	public static void check(
			PermissionChecker permissionChecker, SAPEntry sapEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, sapEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, SAPEntry.class.getName(),
				sapEntry.getSapEntryId(), actionId);
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long classPK, String actionId)
		throws PortalException {

		SAPEntry sapEntry = _sapEntryLocalService.getSAPEntry(classPK);

		return contains(permissionChecker, sapEntry, actionId);
	}

	public static boolean contains(
		PermissionChecker permissionChecker, SAPEntry sapEntry,
		String actionId) {

		return permissionChecker.hasPermission(
			0, SAPEntry.class.getName(), sapEntry.getSapEntryId(), actionId);
	}

	@Override
	public void checkBaseModel(
			PermissionChecker permissionChecker, long groupId, long primaryKey,
			String actionId)
		throws PortalException {

		check(permissionChecker, primaryKey, actionId);
	}

	@Reference(unbind = "-")
	protected void setSAPEntryLocalService(
		SAPEntryLocalService sapEntryLocalService) {

		_sapEntryLocalService = sapEntryLocalService;
	}

	private static SAPEntryLocalService _sapEntryLocalService;

}