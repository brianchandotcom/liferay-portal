/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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