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

package com.liferay.asset.display.page.service;

import com.liferay.portal.kernel.service.ServiceWrapper;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides a wrapper for {@link AssetDisplayPageEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryService
 * @generated
 */
@ProviderType
public class AssetDisplayPageEntryServiceWrapper
	implements AssetDisplayPageEntryService,
			   ServiceWrapper<AssetDisplayPageEntryService> {

	public AssetDisplayPageEntryServiceWrapper(
		AssetDisplayPageEntryService assetDisplayPageEntryService) {

		_assetDisplayPageEntryService = assetDisplayPageEntryService;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetDisplayPageEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public AssetDisplayPageEntryService getWrappedService() {
		return _assetDisplayPageEntryService;
	}

	@Override
	public void setWrappedService(
		AssetDisplayPageEntryService assetDisplayPageEntryService) {

		_assetDisplayPageEntryService = assetDisplayPageEntryService;
	}

	private AssetDisplayPageEntryService _assetDisplayPageEntryService;

}