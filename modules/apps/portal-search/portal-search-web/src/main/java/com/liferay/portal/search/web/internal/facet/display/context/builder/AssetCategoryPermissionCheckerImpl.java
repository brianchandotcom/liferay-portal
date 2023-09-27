/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.facet.display.context.builder;

import com.liferay.asset.kernel.model.AssetCategory;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portlet.asset.service.permission.AssetCategoryPermission;

/**
 * @author André de Oliveira
 */
public class AssetCategoryPermissionCheckerImpl
	implements AssetCategoryPermissionChecker {

	public AssetCategoryPermissionCheckerImpl(
		PermissionChecker permissionChecker) {

		_permissionChecker = permissionChecker;
	}

	@Override
	public boolean hasPermission(AssetCategory assetCategory) {
		try {
			return AssetCategoryPermission.contains(
				_permissionChecker, assetCategory, ActionKeys.VIEW);
		}
		catch (PortalException portalException) {
			throw new RuntimeException(portalException);
		}
	}

	private final PermissionChecker _permissionChecker;

}