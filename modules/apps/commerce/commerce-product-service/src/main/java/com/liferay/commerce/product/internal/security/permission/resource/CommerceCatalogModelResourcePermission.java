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

package com.liferay.commerce.product.internal.security.permission.resource;

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
	immediate = true,
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
		return null;
	}

	@Reference
	protected CommerceCatalogPermission commerceCatalogPermission;

}