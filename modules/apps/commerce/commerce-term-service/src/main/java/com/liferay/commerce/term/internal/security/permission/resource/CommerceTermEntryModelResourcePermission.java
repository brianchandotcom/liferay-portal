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
	enabled = false, immediate = true,
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