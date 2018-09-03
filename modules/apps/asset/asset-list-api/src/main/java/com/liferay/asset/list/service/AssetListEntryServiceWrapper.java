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
 * Provides a wrapper for {@link AssetListEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryService
 * @generated
 */
@ProviderType
public class AssetListEntryServiceWrapper implements AssetListEntryService,
	ServiceWrapper<AssetListEntryService> {
	public AssetListEntryServiceWrapper(
		AssetListEntryService assetListEntryService) {
		_assetListEntryService = assetListEntryService;
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry addAssetListEntry(
		long groupId, String title, int type,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListEntryService.addAssetListEntry(groupId, title, type,
			serviceContext);
	}

	@Override
	public void deleteAssetListEntries(long[] assetListEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetListEntryService.deleteAssetListEntries(assetListEntriesIds);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry deleteAssetListEntry(
		long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListEntryService.deleteAssetListEntry(assetListEntryId);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry fetchAssetListEntry(
		long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListEntryService.fetchAssetListEntry(assetListEntryId);
	}

	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.list.model.AssetListEntry> orderByComparator) {
		return _assetListEntryService.getAssetListEntries(groupId, start, end,
			orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.asset.list.model.AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.asset.list.model.AssetListEntry> orderByComparator) {
		return _assetListEntryService.getAssetListEntries(groupId, title,
			start, end, orderByComparator);
	}

	@Override
	public int getAssetListEntriesCount(long groupId) {
		return _assetListEntryService.getAssetListEntriesCount(groupId);
	}

	@Override
	public int getAssetListEntriesCount(long groupId, String title) {
		return _assetListEntryService.getAssetListEntriesCount(groupId, title);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetListEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry updateAssetListEntry(
		long assetListEntryId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListEntryService.updateAssetListEntry(assetListEntryId,
			title);
	}

	@Override
	public com.liferay.asset.list.model.AssetListEntry updateAssetListEntrySettings(
		long assetListEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _assetListEntryService.updateAssetListEntrySettings(assetListEntryId,
			typeSettings);
	}

	@Override
	public AssetListEntryService getWrappedService() {
		return _assetListEntryService;
	}

	@Override
	public void setWrappedService(AssetListEntryService assetListEntryService) {
		_assetListEntryService = assetListEntryService;
	}

	private AssetListEntryService _assetListEntryService;
}