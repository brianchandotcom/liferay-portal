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

package com.liferay.remote.app.internal.security.permission.resource;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.remote.app.constants.RemoteAppConstants;
import com.liferay.remote.app.model.RemoteAppEntry;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	immediate = true,
	property = "model.class.name=com.liferay.remote.app.model.RemoteAppEntry",
	service = ModelResourcePermission.class
)
public class RemoteAppEntryModelResourcePermission
	implements ModelResourcePermission<RemoteAppEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker, long remoteAppEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, remoteAppEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RemoteAppEntry.class.getName(),
				remoteAppEntryId, actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, RemoteAppEntry remoteAppEntry,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, remoteAppEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, RemoteAppEntry.class.getName(),
				remoteAppEntry.getRemoteAppEntryId(), actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long remoteAppEntryId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, RemoteAppEntry.class.getName(), remoteAppEntryId, actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, RemoteAppEntry remoteAppEntry,
		String actionId) {

		return permissionChecker.hasPermission(
			null, RemoteAppEntry.class.getName(),
			remoteAppEntry.getRemoteAppEntryId(), actionId);
	}

	@Override
	public String getModelName() {
		return RemoteAppEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(
		target = "(resource.name=" + RemoteAppConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}