/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.term.internal.security.permission.resource;

import com.liferay.commerce.term.constants.CommerceTermEntryConstants;
import com.liferay.commerce.term.model.CommerceTermEntry;
import com.liferay.commerce.term.permission.CommerceTermEntryPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alessio Antonio Rendina
 */
@Component(
	property = "model.class.name=com.liferay.commerce.term.model.CommerceTermEntry",
	service = ModelResourcePermission.class
)
public class CommerceTermEntryModelResourcePermission
	implements ModelResourcePermission<CommerceTermEntry> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException {

		_commerceTermEntryPermission.check(
			permissionChecker, commerceTermEntry, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException {

		_commerceTermEntryPermission.check(
			permissionChecker, commerceTermEntryId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceTermEntry commerceTermEntry, String actionId)
		throws PortalException {

		return _commerceTermEntryPermission.contains(
			permissionChecker, commerceTermEntry, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceTermEntryId,
			String actionId)
		throws PortalException {

		return _commerceTermEntryPermission.contains(
			permissionChecker, commerceTermEntryId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceTermEntry.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	private CommerceTermEntryPermission _commerceTermEntryPermission;

	@Reference(
		target = "(resource.name=" + CommerceTermEntryConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}