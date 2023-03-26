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

package com.liferay.change.tracking.taglib.internal.security.permission.resource;

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