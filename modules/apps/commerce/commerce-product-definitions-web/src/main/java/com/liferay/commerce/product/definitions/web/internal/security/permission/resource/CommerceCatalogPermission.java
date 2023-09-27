/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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