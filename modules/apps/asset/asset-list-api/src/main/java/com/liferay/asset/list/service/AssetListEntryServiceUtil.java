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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for AssetListEntry. This utility wraps
 * <code>com.liferay.asset.list.service.impl.AssetListEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryService
 * @generated
 */
public class AssetListEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addAssetEntrySelection(
			assetListEntryId, assetEntryId, segmentsEntryId, serviceContext);
	}

	public static void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().addAssetEntrySelections(
			assetListEntryId, assetEntryIds, segmentsEntryId, serviceContext);
	}

	public static AssetListEntry addAssetListEntry(
			long groupId, String title, int type,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addAssetListEntry(
			groupId, title, type, serviceContext);
	}

	public static AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addDynamicAssetListEntry(
			userId, groupId, title, typeSettings, serviceContext);
	}

	public static AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		return getService().addManualAssetListEntry(
			userId, groupId, title, assetEntryIds, serviceContext);
	}

	public static void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException {

		getService().deleteAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position);
	}

	public static void deleteAssetListEntries(long[] assetListEntriesIds)
		throws PortalException {

		getService().deleteAssetListEntries(assetListEntriesIds);
	}

	public static AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException {

		return getService().deleteAssetListEntry(assetListEntryId);
	}

	public static void deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException {

		getService().deleteAssetListEntry(assetListEntryId, segmentsEntryId);
	}

	public static AssetListEntry fetchAssetListEntry(long assetListEntryId)
		throws PortalException {

		return getService().fetchAssetListEntry(assetListEntryId);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupId, start, end, orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupId, title, start, end, orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, start, end, orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, title, start, end, orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, title, assetEntrySubtype, assetEntryType, start, end,
			orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end, OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, title, assetEntryTypes, start, end, orderByComparator);
	}

	public static List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String[] assetEntryTypes, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator) {

		return getService().getAssetListEntries(
			groupIds, assetEntryTypes, start, end, orderByComparator);
	}

	public static int getAssetListEntriesCount(long groupId) {
		return getService().getAssetListEntriesCount(groupId);
	}

	public static int getAssetListEntriesCount(long groupId, String title) {
		return getService().getAssetListEntriesCount(groupId, title);
	}

	public static int getAssetListEntriesCount(long[] groupIds) {
		return getService().getAssetListEntriesCount(groupIds);
	}

	public static int getAssetListEntriesCount(long[] groupIds, String title) {
		return getService().getAssetListEntriesCount(groupIds, title);
	}

	public static int getAssetListEntriesCount(
		long[] groupIds, String assetEntrySubtype, String assetEntryType) {

		return getService().getAssetListEntriesCount(
			groupIds, assetEntrySubtype, assetEntryType);
	}

	public static int getAssetListEntriesCount(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType) {

		return getService().getAssetListEntriesCount(
			groupIds, title, assetEntrySubtype, assetEntryType);
	}

	public static int getAssetListEntriesCount(
		long[] groupIds, String title, String[] assetEntryTypes) {

		return getService().getAssetListEntriesCount(
			groupIds, title, assetEntryTypes);
	}

	public static int getAssetListEntriesCount(
		long[] groupIds, String[] assetEntryTypes) {

		return getService().getAssetListEntriesCount(groupIds, assetEntryTypes);
	}

	public static AssetListEntry getAssetListEntry(long assetListEntryId)
		throws PortalException {

		return getService().getAssetListEntry(assetListEntryId);
	}

	public static AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws PortalException {

		return getService().getAssetListEntry(groupId, assetListEntryKey);
	}

	public static AssetListEntry getAssetListEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException {

		return getService().getAssetListEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws PortalException {

		getService().moveAssetEntrySelection(
			assetListEntryId, segmentsEntryId, position, newPosition);
	}

	public static void updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws PortalException {

		getService().updateAssetListEntry(
			assetListEntryId, segmentsEntryId, typeSettings, serviceContext);
	}

	public static AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws PortalException {

		return getService().updateAssetListEntry(assetListEntryId, title);
	}

	public static void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws PortalException {

		getService().updateAssetListEntryTypeSettings(
			assetListEntryId, segmentsEntryId, typeSettings);
	}

	public static AssetListEntryService getService() {
		return _service;
	}

	public static void setService(AssetListEntryService service) {
		_service = service;
	}

	private static volatile AssetListEntryService _service;

}