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

package com.liferay.asset.list.item.selector.web.internal.layout.list.provider;

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.layout.list.provider.LayoutListPermissionProvider;
import com.liferay.layout.list.retriever.ClassedModelListObjectReference;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Lourdes Fernández Besada
 */
@Component(service = LayoutListPermissionProvider.class)
public class AssetEntryListLayoutListPermissionProvider
	implements LayoutListPermissionProvider<ClassedModelListObjectReference> {

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker,
		ClassedModelListObjectReference classedModelListObjectReference,
		String actionId) {

		AssetListEntry assetListEntry =
			_assetListEntryLocalService.fetchAssetListEntry(
				classedModelListObjectReference.getClassPK());

		if (assetListEntry == null) {
			return true;
		}

		try {
			if (_assetListEntryModelResourcePermission.contains(
					permissionChecker, assetListEntry, actionId)) {

				return true;
			}
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssetEntryListLayoutListPermissionProvider.class.getName());

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference(
		target = "(model.class.name=com.liferay.asset.list.model.AssetListEntry)"
	)
	private ModelResourcePermission<AssetListEntry>
		_assetListEntryModelResourcePermission;

}