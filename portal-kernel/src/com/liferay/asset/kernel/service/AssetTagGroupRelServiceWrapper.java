/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.asset.kernel.service;

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