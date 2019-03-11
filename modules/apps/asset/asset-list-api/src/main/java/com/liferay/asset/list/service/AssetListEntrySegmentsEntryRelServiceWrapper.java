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
 * Provides a wrapper for {@link AssetListEntrySegmentsEntryRelService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntrySegmentsEntryRelService
 * @generated
 */
@ProviderType
public class AssetListEntrySegmentsEntryRelServiceWrapper
	implements AssetListEntrySegmentsEntryRelService,
			   ServiceWrapper<AssetListEntrySegmentsEntryRelService> {

	public AssetListEntrySegmentsEntryRelServiceWrapper(
		AssetListEntrySegmentsEntryRelService
			assetListEntrySegmentsEntryRelService) {

		_assetListEntrySegmentsEntryRelService =
			assetListEntrySegmentsEntryRelService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListEntrySegmentsEntryRelService.
			getOSGiServiceIdentifier();
	}

	@Override
	public AssetListEntrySegmentsEntryRelService getWrappedService() {
		return _assetListEntrySegmentsEntryRelService;
	}

	@Override
	public void setWrappedService(
		AssetListEntrySegmentsEntryRelService
			assetListEntrySegmentsEntryRelService) {

		_assetListEntrySegmentsEntryRelService =
			assetListEntrySegmentsEntryRelService;
	}

	private AssetListEntrySegmentsEntryRelService
		_assetListEntrySegmentsEntryRelService;

}