/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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