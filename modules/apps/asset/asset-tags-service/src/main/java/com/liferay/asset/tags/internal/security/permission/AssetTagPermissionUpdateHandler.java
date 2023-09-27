/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.tags.internal.security.permission;

import com.liferay.asset.kernel.model.AssetTag;
import com.liferay.asset.kernel.service.AssetTagLocalService;
import com.liferay.portal.kernel.security.permission.PermissionUpdateHandler;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Date;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gergely Mathe
 */
@Component(
	property = "model.class.name=com.liferay.asset.kernel.model.AssetTag",
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

	@Reference
	private AssetTagLocalService _assetTagLocalService;

}