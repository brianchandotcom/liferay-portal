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

package com.liferay.client.extension.internal.security.permission.resource;

import com.liferay.client.extension.constants.ClientExtensionConstants;
import com.liferay.client.extension.model.ClientExtensionEntry;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Javier de Arcos
 */
@Component(
	property = "model.class.name=com.liferay.client.extension.model.ClientExtensionEntry",
	service = ModelResourcePermission.class
)
public class ClientExtensionEntryModelResourcePermission
	implements ModelResourcePermission<ClientExtensionEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			ClientExtensionEntry clientExtensionEntry, String actionId)
		throws PortalException {

		if (!contains(permissionChecker, clientExtensionEntry, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ClientExtensionEntry.class.getName(),
				clientExtensionEntry.getClientExtensionEntryId(), actionId);
		}
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long clientExtensionEntryId,
			String actionId)
		throws PortalException {

		if (!contains(permissionChecker, clientExtensionEntryId, actionId)) {
			throw new PrincipalException.MustHavePermission(
				permissionChecker, ClientExtensionEntry.class.getName(),
				clientExtensionEntryId, actionId);
		}
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker,
		ClientExtensionEntry clientExtensionEntry, String actionId) {

		return permissionChecker.hasPermission(
			null, ClientExtensionEntry.class.getName(),
			clientExtensionEntry.getClientExtensionEntryId(), actionId);
	}

	@Override
	public boolean contains(
		PermissionChecker permissionChecker, long clientExtensionEntryId,
		String actionId) {

		return permissionChecker.hasPermission(
			null, ClientExtensionEntry.class.getName(), clientExtensionEntryId,
			actionId);
	}

	@Override
	public String getModelName() {
		return ClientExtensionEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference(
		target = "(resource.name=" + ClientExtensionConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}