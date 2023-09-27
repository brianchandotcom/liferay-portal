/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.list.internal.security.permission.resource;

import com.liferay.asset.list.constants.AssetListConstants;
import com.liferay.asset.list.constants.AssetListPortletKeys;
import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.asset.list.service.AssetListEntryLocalService;
import com.liferay.exportimport.kernel.staging.permission.StagingPermission;
import com.liferay.portal.kernel.security.permission.resource.BaseModelResourcePermissionWrapper;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.ModelResourcePermissionFactory;
import com.liferay.portal.kernel.security.permission.resource.PortletResourcePermission;
import com.liferay.portal.kernel.security.permission.resource.StagedModelPermissionLogic;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jürgen Kappler
 */
@Component(
	property = "model.class.name=com.liferay.asset.list.model.AssetListEntry",
	service = ModelResourcePermission.class
)
public class AssetListEntryModelResourcePermissionWrapper
	extends BaseModelResourcePermissionWrapper<AssetListEntry> {

	@Override
	protected ModelResourcePermission<AssetListEntry>
		doGetModelResourcePermission() {

		return ModelResourcePermissionFactory.create(
			AssetListEntry.class, AssetListEntry::getAssetListEntryId,
			_assetListEntryLocalService::getAssetListEntry,
			_portletResourcePermission,
			(modelResourcePermission, consumer) -> consumer.accept(
				new StagedModelPermissionLogic<>(
					_stagingPermission, AssetListPortletKeys.ASSET_LIST,
					AssetListEntry::getAssetListEntryId)));
	}

	@Reference
	private AssetListEntryLocalService _assetListEntryLocalService;

	@Reference(
		target = "(resource.name=" + AssetListConstants.RESOURCE_NAME + ")"
	)
	private PortletResourcePermission _portletResourcePermission;

	@Reference
	private StagingPermission _stagingPermission;

}