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

package com.liferay.asset.list.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetListSegmentRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListSegmentRelService
 * @generated
 */
@ProviderType
public class AssetListSegmentRelServiceWrapper
	implements AssetListSegmentRelService,
			   ServiceWrapper<AssetListSegmentRelService> {

	public AssetListSegmentRelServiceWrapper(
		AssetListSegmentRelService assetListSegmentRelService) {

		_assetListSegmentRelService = assetListSegmentRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListSegmentRelService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetListSegmentRelService getWrappedService() {
		return _assetListSegmentRelService;
	}

	@Override
	public void setWrappedService(
		AssetListSegmentRelService assetListSegmentRelService) {

		_assetListSegmentRelService = assetListSegmentRelService;
	}

	private AssetListSegmentRelService _assetListSegmentRelService;

}