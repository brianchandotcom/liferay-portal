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

package com.liferay.asset.list.web.internal.security.permission.resource;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.osgi.util.service.Snapshot;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

/**
 * @author Jürgen Kappler
 */
public class AssetListEntryPermission {

	public static boolean contains(
			PermissionChecker permissionChecker, AssetListEntry assetListEntry,
			String actionId)
		throws PortalException {

		ModelResourcePermission<AssetListEntry> modelResourcePermission =
			_assetListEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, assetListEntry, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, long assetListEntryId,
			String actionId)
		throws PortalException {

		ModelResourcePermission<AssetListEntry> modelResourcePermission =
			_assetListEntryModelResourcePermissionSnapshot.get();

		return modelResourcePermission.contains(
			permissionChecker, assetListEntryId, actionId);
	}

	private static final Snapshot<ModelResourcePermission<AssetListEntry>>
		_assetListEntryModelResourcePermissionSnapshot = new Snapshot<>(
			AssetListEntryPermission.class,
			Snapshot.cast(ModelResourcePermission.class),
			"(model.class.name=com.liferay.asset.list.model.AssetListEntry)");

}