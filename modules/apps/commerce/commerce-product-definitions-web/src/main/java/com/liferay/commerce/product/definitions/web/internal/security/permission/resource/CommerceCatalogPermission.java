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

package com.liferay.commerce.product.definitions.web.internal.security.permission.resource;

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CommerceCatalog;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Marco Leo
 */
public class CommerceCatalogPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CPDefinition cpDefinition,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CommerceCatalog> modelResourcePermission =
			_commerceCatalogModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, cpDefinition.getCommerceCatalog(), actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long commerceCatalogId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CommerceCatalog> modelResourcePermission =
			_commerceCatalogModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, commerceCatalogId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CommerceCatalog>>
		_commerceCatalogModelResourcePermissionSnapshot = new Snapshot<>(
			CommerceCatalogPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.commerce.product.model." +
				"CommerceCatalog)");

}