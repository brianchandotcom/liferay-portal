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

package com.liferay.asset.kernel.service;

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link AssetEntryService}.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryService
 * @generated
 */
public class AssetEntryServiceWrapper
	implements AssetEntryService, ServiceWrapper<AssetEntryService> {

	public AssetEntryServiceWrapper(AssetEntryService assetEntryService) {
		_assetEntryService = assetEntryService;
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryServiceUtil} to access the asset entry remote service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public AssetEntry fetchEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.fetchEntry(entryId);
	}

	@Override
	public java.util.List<AssetEntry> getCompanyEntries(
		long companyId, int start, int end) {

		return _assetEntryService.getCompanyEntries(companyId, start, end);
	}

	@Override
	public int getCompanyEntriesCount(long companyId) {
		return _assetEntryService.getCompanyEntriesCount(companyId);
	}

	@Override
	public java.util.List<AssetEntry> getEntries(
			com.liferay.asset.kernel.service.persistence.AssetEntryQuery
				entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.getEntries(entryQuery);
	}

	@Override
	public int getEntriesCount(
			com.liferay.asset.kernel.service.persistence.AssetEntryQuery
				entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.getEntriesCount(entryQuery);
	}

	@Override
	public AssetEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.getEntry(entryId);
	}

	@Override
	public AssetEntry getEntry(String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.getEntry(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _assetEntryService.getOSGiServiceIdentifier();
	}

	@Override
	public void incrementViewCounter(AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		_assetEntryService.incrementViewCounter(assetEntry);
	}

	@Override
	public AssetEntry incrementViewCounter(
			long companyId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.incrementViewCounter(
			companyId, className, classPK);
	}

	@Override
	public AssetEntry updateEntry(
			long groupId, java.util.Date createDate,
			java.util.Date modifiedDate, String className, long classPK,
			String classUuid, long classTypeId, long[] categoryIds,
			String[] tagNames, boolean listable, boolean visible,
			java.util.Date startDate, java.util.Date endDate,
			java.util.Date publishDate, java.util.Date expirationDate,
			String mimeType, String title, String description, String summary,
			String url, String layoutUuid, int height, int width,
			Double priority)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _assetEntryService.updateEntry(
			groupId, createDate, modifiedDate, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, listable, visible, startDate,
			endDate, publishDate, expirationDate, mimeType, title, description,
			summary, url, layoutUuid, height, width, priority);
	}

	@Override
	public AssetEntryService getWrappedService() {
		return _assetEntryService;
	}

	@Override
	public void setWrappedService(AssetEntryService assetEntryService) {
		_assetEntryService = assetEntryService;
	}

	private AssetEntryService _assetEntryService;

}