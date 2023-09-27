/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.product.internal.security.permission.resource;

import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.commerce.product.permission.CommerceCatalogPermission;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alec Sloan
 */
@Component(
	property = "model.class.name=com.liferay.commerce.product.model.CommerceCatalog",
	service = ModelResourcePermission.class
)
public class CommerceCatalogModelResourcePermission
	implements ModelResourcePermission<CommerceCatalog> {

	@Override
	public void check(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException {

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public void check(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException {

		commerceCatalogPermission.check(
			permissionChecker, commerceCatalogId, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException {

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalog, actionId);
	}

	@Override
	public boolean contains(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException {

		return commerceCatalogPermission.contains(
			permissionChecker, commerceCatalogId, actionId);
	}

	@Override
	public String getModelName() {
		return CommerceCatalog.class.getName();
	}

	@Override
	public PortletResourcePermission getPortletResourcePermission() {
		return _portletResourcePermission;
	}

	@Reference
	protected CommerceCatalogPermission commerceCatalogPermission;

	@Reference(
		target = "(resource.name=" + CPConstants.RESOURCE_NAME_CATALOG + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

}