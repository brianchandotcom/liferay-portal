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

package com.liferay.redirect.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.redirect.constants.RedirectConstants;
import com.liferay.redirect.model.RedirectEntry;
import com.liferay.redirect.service.RedirectEntryLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.redirect.model.RedirectEntry",
	service = ModelResourcePermission.class
)
public class RedirectEntryModelResourcePermission
	implements ModelResourcePermission<RedirectEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long redirectEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, redirectEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RedirectEntry.class.getName(),
				redirectEntryId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, RedirectEntry redirectEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, redirectEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RedirectEntry.class.getName(),
				redirectEntry.getRedirectEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long redirectEntryId,
			String actionId)
		throws PortalException {

		return _contains(
			permissionChecker,
			_redirectEntryLocalService.getRedirectEntry(redirectEntryId),
			actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, RedirectEntry redirectEntry,
		String actionId) {

		return _contains(permissionChecker, redirectEntry, actionId);
	}

	@Override
	public String getModelName() {
		return RedirectEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	private boolean _contains(
		PermissionChecker permissionChecker, RedirectEntry redirectEntry,
		String actionId) {

		if (permissionChecker.hasOwnerPermission(
				redirectEntry.getCompanyId(), RedirectEntry.class.getName(),
				redirectEntry.getRedirectEntryId(), redirectEntry.getUserId(),
				actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			redirectEntry.getGroupId(), RedirectEntry.class.getName(),
			redirectEntry.getRedirectEntryId(), actionId);
	}

	@Reference(
		target = "(resource.name=" + RedirectConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private RedirectEntryLocalService _redirectEntryLocalService;

}