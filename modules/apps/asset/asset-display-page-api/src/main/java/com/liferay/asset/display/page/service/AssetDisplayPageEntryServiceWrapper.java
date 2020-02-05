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

import com.liferay.asset.display.page.model.AssetDisplayPageEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetDisplayPageEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetDisplayPageEntryService
 * @generated
 */
public class AssetDisplayPageEntryServiceWrapper
	implements AssetDisplayPageEntryService,
			   ServiceWrapper<AssetDisplayPageEntryService> {

	public AssetDisplayPageEntryServiceWrapper(
		AssetDisplayPageEntryService assetDisplayPageEntryService) {

		_assetDisplayPageEntryService = assetDisplayPageEntryService;
	}

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		return _assetDisplayPageEntryService.addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			type, serviceContext);
	}

	@Override
	public AssetDisplayPageEntry addAssetDisplayPageEntry(
			long userId, long groupId, long classNameId, long classPK,
			long layoutPageTemplateEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws Exception {

		return _assetDisplayPageEntryService.addAssetDisplayPageEntry(
			userId, groupId, classNameId, classPK, layoutPageTemplateEntryId,
			serviceContext);
	}

	@Override
	public void deleteAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws Exception {

		_assetDisplayPageEntryService.deleteAssetDisplayPageEntry(
			groupId, classNameId, classPK);
	}

	@Override
	public AssetDisplayPageEntry fetchAssetDisplayPageEntry(
			long groupId, long classNameId, long classPK)
		throws Exception {

		return _assetDisplayPageEntryService.fetchAssetDisplayPageEntry(
			groupId, classNameId, classPK);
	}

	@Override
	public java.util.List<AssetDisplayPageEntry>
		getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
			long layoutPageTemplateEntryId) {

		return _assetDisplayPageEntryService.
			getAssetDisplayPageEntriesByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
	}

	@Override
	public int getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
		long layoutPageTemplateEntryId) {

		return _assetDisplayPageEntryService.
			getAssetDisplayPageEntriesCountByLayoutPageTemplateEntryId(
				layoutPageTemplateEntryId);
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
	public AssetDisplayPageEntry updateAssetDisplayPageEntry(
			long assetDisplayPageEntryId, long layoutPageTemplateEntryId,
			int type)
		throws Exception {

		return _assetDisplayPageEntryService.updateAssetDisplayPageEntry(
			assetDisplayPageEntryId, layoutPageTemplateEntryId, type);
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