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

package com.liferay.asset.tags.service.permission;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.security.permission.PermissionUpdateHandler;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.service.AssetTagLocalService;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = {"model.class.name=com.liferay.portlet.asset.model.AssetTag"},
	service = PermissionUpdateHandler.class
)
public class AssetTagPermissionUpdateHandler
	implements PermissionUpdateHandler {

	@Override
	public void updatedPermission(String primKey) {
		AssetTag assetTag = _assetTagLocalService.fetchAssetTag(
			GetterUtil.getLong(primKey));

		if (assetTag == null) {
			return;
		}

		assetTag.setModifiedDate(new Date());

		_assetTagLocalService.updateAssetTag(assetTag);
	}

	@Reference(unbind = "-")
	protected void setAssetTagLocalService(
		AssetTagLocalService assetTagLocalService) {

		_assetTagLocalService = assetTagLocalService;
	}

	private volatile AssetTagLocalService _assetTagLocalService;

}