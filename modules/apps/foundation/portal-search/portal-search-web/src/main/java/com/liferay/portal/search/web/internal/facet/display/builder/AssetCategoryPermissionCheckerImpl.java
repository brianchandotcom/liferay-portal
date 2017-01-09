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

package com.liferay.portal.search.web.internal.facet.display.builder;

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
		catch (PortalException pe) {
			throw new RuntimeException(pe);
		}
	}

	private final PermissionChecker _permissionChecker;

}