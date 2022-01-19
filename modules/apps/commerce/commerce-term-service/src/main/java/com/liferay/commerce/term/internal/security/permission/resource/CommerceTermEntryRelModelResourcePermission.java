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

import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.permission.CommerceTermEntryPermission;
import com.liferay.commerce.term.service.CommerceTermEntryRelLocalService;
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
	property = "model.class.name=com.liferay.commerce.term.model.CommerceTermEntryRel",
	service = ModelResourcePermission.class
)
public class CommerceTermEntryRelModelResourcePermission
	implements ModelResourcePermission<CommerceTermEntryRel> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceTermEntryRel commerceTermEntryRel, String actionId)
		throws PortalException {

		_commerceTermEntryPermission.check(
			permissionChecker, commerceTermEntryRel.getCommerceTermEntryId(),
			actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceTermEntryRelId,
			String actionId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			_commerceTermEntryRelLocalService.getCommerceTermEntryRel(
				commerceTermEntryRelId);

		_commerceTermEntryPermission.check(
			permissionChecker, commerceTermEntryRel.getCommerceTermEntryId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceTermEntryRel commerceTermEntryRel, String actionId)
		throws PortalException {

		return _commerceTermEntryPermission.contains(
			permissionChecker, commerceTermEntryRel.getCommerceTermEntryId(),
			actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceTermEntryRelId,
			String actionId)
		throws PortalException {

		CommerceTermEntryRel commerceTermEntryRel =
			_commerceTermEntryRelLocalService.getCommerceTermEntryRel(
				commerceTermEntryRelId);

		return _commerceTermEntryPermission.contains(
			permissionChecker, commerceTermEntryRel.getCommerceTermEntryId(),
			actionId);
	}

	@Override
	public String getModelName() {
		return CommerceTermEntryRel.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return null;
	}

	@Reference
	private CommerceTermEntryPermission _commerceTermEntryPermission;

	@Reference
	private CommerceTermEntryRelLocalService _commerceTermEntryRelLocalService;

}