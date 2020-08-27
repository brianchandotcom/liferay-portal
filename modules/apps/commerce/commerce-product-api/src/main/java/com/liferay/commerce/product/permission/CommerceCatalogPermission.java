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

package com.liferay.commerce.product.permission;

import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

/**
 * @author Alec Sloan
 */
public interface CommerceCatalogPermission {

	public void check(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException;

	public void check(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker,
			CommerceCatalog commerceCatalog, String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException;

	public boolean contains(
			PermissionChecker permissionChecker, long[] commerceCatalogIds,
			String actionId)
		throws PortalException;

}