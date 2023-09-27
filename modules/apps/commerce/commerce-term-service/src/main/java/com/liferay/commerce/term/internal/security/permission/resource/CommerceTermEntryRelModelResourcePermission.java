/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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