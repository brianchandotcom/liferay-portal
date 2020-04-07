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
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.service.BaseService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Provides the remote service interface for AssetListEntry. Methods of this
 * service are expected to have security checks based on the propagated JAAS
 * credentials because this service can be accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetListEntryServiceUtil
 * @generated
 */
@AccessControlled
@JSONWebService
@ProviderType
@Transactional(
	isolation = Isolation.PORTAL,
	rollbackFor = {PortalException.class, SystemException.class}
)
public interface AssetListEntryService extends BaseService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetListEntryServiceUtil} to access the asset list entry remote service. Add custom service methods to <code>com.liferay.asset.list.service.impl.AssetListEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public void addAssetEntrySelection(
			long assetListEntryId, long assetEntryId, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public void addAssetEntrySelections(
			long assetListEntryId, long[] assetEntryIds, long segmentsEntryId,
			ServiceContext serviceContext)
		throws PortalException;

	public AssetListEntry addAssetListEntry(
			long groupId, String title, int type, ServiceContext serviceContext)
		throws PortalException;

	public AssetListEntry addDynamicAssetListEntry(
			long userId, long groupId, String title, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException;

	public AssetListEntry addManualAssetListEntry(
			long userId, long groupId, String title, long[] assetEntryIds,
			ServiceContext serviceContext)
		throws PortalException;

	public void deleteAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position)
		throws PortalException;

	public void deleteAssetListEntries(long[] assetListEntriesIds)
		throws PortalException;

	public AssetListEntry deleteAssetListEntry(long assetListEntryId)
		throws PortalException;

	public void deleteAssetListEntry(
			long assetListEntryId, long segmentsEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListEntry fetchAssetListEntry(long assetListEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long groupId, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long groupId, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String assetEntrySubtype, String assetEntryType,
		int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String title, String[] assetEntryTypes, int start,
		int end, OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public List<AssetListEntry> getAssetListEntries(
		long[] groupIds, String[] assetEntryTypes, int start, int end,
		OrderByComparator<AssetListEntry> orderByComparator);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(long groupId);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(long groupId, String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(long[] groupIds);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(long[] groupIds, String title);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(
		long[] groupIds, String assetEntrySubtype, String assetEntryType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(
		long[] groupIds, String title, String assetEntrySubtype,
		String assetEntryType);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(
		long[] groupIds, String title, String[] assetEntryTypes);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAssetListEntriesCount(
		long[] groupIds, String[] assetEntryTypes);

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListEntry getAssetListEntry(long assetListEntryId)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListEntry getAssetListEntry(
			long groupId, String assetListEntryKey)
		throws PortalException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public AssetListEntry getAssetListEntryByUuidAndGroupId(
			String uuid, long groupId)
		throws PortalException;

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public String getOSGiServiceIdentifier();

	public void moveAssetEntrySelection(
			long assetListEntryId, long segmentsEntryId, int position,
			int newPosition)
		throws PortalException;

	public void updateAssetListEntry(
			long assetListEntryId, long segmentsEntryId, String typeSettings,
			ServiceContext serviceContext)
		throws PortalException;

	public AssetListEntry updateAssetListEntry(
			long assetListEntryId, String title)
		throws PortalException;

	public void updateAssetListEntryTypeSettings(
			long assetListEntryId, long segmentsEntryId, String typeSettings)
		throws PortalException;

}