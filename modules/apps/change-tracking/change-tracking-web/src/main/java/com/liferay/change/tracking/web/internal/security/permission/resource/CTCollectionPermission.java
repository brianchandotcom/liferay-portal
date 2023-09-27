/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.change.tracking.web.internal.security.permission.resource;

import com.liferay.change.tracking.model.CTCollection;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Preston Crary
 */
public class CTCollectionPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, CTCollection ctCollection,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CTCollection> modelResourcePermission =
			_ctCollectionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ctCollection, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long ctCollectionId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<CTCollection> modelResourcePermission =
			_ctCollectionModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, ctCollectionId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<CTCollection>>
		_ctCollectionModelResourcePermissionSnapshot = new Snapshot<>(
			CTCollectionPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.change.tracking.model." +
				"CTCollection)");

}