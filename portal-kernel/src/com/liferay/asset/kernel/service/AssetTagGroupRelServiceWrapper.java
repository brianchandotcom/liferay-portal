/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetTagGroupRel;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetTagGroupRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetTagGroupRelService
 * @generated
 */
public class AssetTagGroupRelServiceWrapper
	implements AssetTagGroupRelService,
			   ServiceWrapper<AssetTagGroupRelService> {

	public AssetTagGroupRelServiceWrapper() {
		this(null);
	}

	public AssetTagGroupRelServiceWrapper(
		AssetTagGroupRelService assetTagGroupRelService) {

		_assetTagGroupRelService = assetTagGroupRelService;
	}

	@Override
	public AssetTagGroupRel addAssetTagGroupRel(long groupId, long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagGroupRelService.addAssetTagGroupRel(groupId, tagId);
	}

	@Override
	public java.util.List<AssetTagGroupRel> getAssetTagGroupRelsByTagId(
			long tagId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetTagGroupRelService.getAssetTagGroupRelsByTagId(tagId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetTagGroupRelService.getOSGiServiceIdentifier();
	}

	@Override
	public void setAssetTagGroupRels(long tagId, long[] groupIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetTagGroupRelService.setAssetTagGroupRels(tagId, groupIds);
	}

	@Override
	public AssetTagGroupRelService getWrappedService() {
		return _assetTagGroupRelService;
	}

	@Override
	public void setWrappedService(
		AssetTagGroupRelService assetTagGroupRelService) {

		_assetTagGroupRelService = assetTagGroupRelService;
	}

	private AssetTagGroupRelService _assetTagGroupRelService;

}