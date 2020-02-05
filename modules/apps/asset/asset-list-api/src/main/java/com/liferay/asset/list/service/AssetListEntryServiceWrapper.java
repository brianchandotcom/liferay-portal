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

import com.liferay.asset.list.model.AssetListEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetListEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryService
 * @generated
 */
public class AssetListEntryServiceWrapper
	implements AssetListEntryService, ServiceWrapper<AssetListEntryService> {

	public AssetListEntryServiceWrapper(
		AssetListEntryService assetListEntryService) {

		_assetListEntryService = assetListEntryService;
	}

	@Override
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.addAssetEntrySelection(
			assetListEntryId, assetEntryId, segmentsEntryId, serviceContext);
	}

	@Override
	public void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.addAssetEntrySelections(
			assetListEntryId, assetEntryIds, segmentsEntryId, serviceContext);
	}

	@Override
	public AssetListEntry addAssetListEntry(
			long groupId, String title, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.addAssetListEntry(
			groupId, title, type, serviceContext);
	}

	@Override
	public AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.addDynamicAssetListEntry(
			userId, groupId, title, typeSettings, serviceContext);
	}

	@Override
	public AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.addManualAssetListEntry(
			userId, groupId, title, assetEntryIds, serviceContext);
	}

	@Override
	public void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.deleteAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position);
	}

	@Override
	public void deleteAssetListEntries(long[] assetListEntriesIds)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.deleteAssetListEntries(assetListEntriesIds);
	}

	@Override
	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.deleteAssetListEntry(assetListEntryId);
	}

	@Override
	public void deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.deleteAssetListEntry(
			assetListEntryId, segmentsEntryId);
	}

	@Override
	public AssetListEntry fetchAssetListEntry(long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.fetchAssetListEntry(assetListEntryId);
	}

	@Override
	public java.util.List<AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator) {

		return _assetListEntryService.getAssetListEntries(
			groupId, start, end, orderByComparator);
	}

	@Override
	public java.util.List<AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator) {

		return _assetListEntryService.getAssetListEntries(
			groupId, title, start, end, orderByComparator);
	}

	@Override
	public java.util.List<AssetListEntry> getAssetListEntries(
		long[] groupIds, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator) {

		return _assetListEntryService.getAssetListEntries(
			groupIds, start, end, orderByComparator);
	}

	@Override
	public java.util.List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<AssetListEntry>
			orderByComparator) {

		return _assetListEntryService.getAssetListEntries(
			groupIds, title, start, end, orderByComparator);
	}

	@Override
	public int getAssetListEntriesCount(long groupId) {
		return _assetListEntryService.getAssetListEntriesCount(groupId);
	}

	@Override
	public int getAssetListEntriesCount(long groupId, String title) {
		return _assetListEntryService.getAssetListEntriesCount(groupId, title);
	}

	@Override
	public int getAssetListEntriesCount(long[] groupIds) {
		return _assetListEntryService.getAssetListEntriesCount(groupIds);
	}

	@Override
	public int getAssetListEntriesCount(long[] groupIds, String title) {
		return _assetListEntryService.getAssetListEntriesCount(groupIds, title);
	}

	@Override
	public AssetListEntry getAssetListEntry(long assetListEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.getAssetListEntry(assetListEntryId);
	}

	@Override
	public AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.getAssetListEntry(
			groupId, assetListEntryKey);
	}

	@Override
	public AssetListEntry getAssetListEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.getAssetListEntryByUuidAndGroupId(
			uuid, groupId);
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
	public void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.moveAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	@Override
	public void updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.updateAssetListEntry(
			assetListEntryId, segmentsEntryId, typeSettings, serviceContext);
	}

	@Override
	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetListEntryService.updateAssetListEntry(
			assetListEntryId, title);
	}

	@Override
	public void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetListEntryService.updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
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