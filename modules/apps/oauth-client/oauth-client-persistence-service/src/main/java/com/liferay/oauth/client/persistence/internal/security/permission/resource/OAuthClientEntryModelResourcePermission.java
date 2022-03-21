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

package com.liferay.oauth.client.persistence.internal.security.permission.resource;

import com.liferay.oauth.client.persistence.model.OAuthClientEntry;
import com.liferay.oauth.client.persistence.service.OAuthClientEntryLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Arthur Chan
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.oauth.client.persistence.model.OAuthClientEntry",
	service = ModelResourcePermission.class
)
public class OAuthClientEntryModelResourcePermission
	implements ModelResourcePermission<OAuthClientEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long oAuthClientEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthClientEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientEntry.class.getName(),
				oAuthClientEntryId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker,
			OAuthClientEntry oAuthClientEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, oAuthClientEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, OAuthClientEntry.class.getName(),
				oAuthClientEntry.getOAuthClientEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long oAuthClientEntryId,
			String actionId)
		throws PortalException {

		return contains(
			permissionChecker,
			_oAuthClientEntryLocalService.getOAuthClientEntry(
				oAuthClientEntryId),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			OAuthClientEntry oAuthClientEntry, String actionId)
		throws PortalException {

		if (permissionChecker.hasOwnerPermission(
				oAuthClientEntry.getCompanyId(),
				OAuthClientEntry.class.getName(),
				oAuthClientEntry.getOAuthClientEntryId(),
				oAuthClientEntry.getUserId(), actionId)) {

			return true;
		}

		return permissionChecker.hasPermission(
			null, OAuthClientEntry.class.getName(),
			oAuthClientEntry.getOAuthClientEntryId(), actionId);
	}

	@Override
	public String getModelName() {
		return OAuthClientEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private OAuthClientEntryLocalService _oAuthClientEntryLocalService;

	@Reference(target = "(resource.name=com.liferay.oauth.client.persistence)")
	private PortletResourcePermission _portletResourcePermission;

}