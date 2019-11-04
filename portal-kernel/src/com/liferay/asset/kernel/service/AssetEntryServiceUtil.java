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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * Provides the remote service utility for AssetEntry. This utility wraps
 * <code>com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see AssetEntryService
 * @generated
 */
public class AssetEntryServiceUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AssetEntryServiceUtil} to access the asset entry remote service. Add custom service methods to <code>com.liferay.portlet.asset.service.impl.AssetEntryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	public static com.liferay.asset.kernel.model.AssetEntry fetchEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchEntry(entryId);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetEntry>
		getCompanyEntries(long companyId, int start, int end) {

		return getService().getCompanyEntries(companyId, start, end);
	}

	public static int getCompanyEntriesCount(long companyId) {
		return getService().getCompanyEntriesCount(companyId);
	}

	public static java.util.List<com.liferay.asset.kernel.model.AssetEntry>
			getEntries(
				com.liferay.asset.kernel.service.persistence.AssetEntryQuery
					entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntries(entryQuery);
	}

	public static int getEntriesCount(
			com.liferay.asset.kernel.service.persistence.AssetEntryQuery
				entryQuery)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntriesCount(entryQuery);
	}

	public static com.liferay.asset.kernel.model.AssetEntry getEntry(
			long entryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntry(entryId);
	}

	public static com.liferay.asset.kernel.model.AssetEntry getEntry(
			String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getEntry(className, classPK);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static void incrementViewCounter(
			com.liferay.asset.kernel.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().incrementViewCounter(assetEntry);
	}

	public static com.liferay.asset.kernel.model.AssetEntry
			incrementViewCounter(long companyId, String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().incrementViewCounter(companyId, className, classPK);
	}

	public static com.liferay.asset.kernel.model.AssetEntry updateEntry(
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

		return getService().updateEntry(
			groupId, createDate, modifiedDate, className, classPK, classUuid,
			classTypeId, categoryIds, tagNames, listable, visible, startDate,
			endDate, publishDate, expirationDate, mimeType, title, description,
			summary, url, layoutUuid, height, width, priority);
	}

	public static AssetEntryService getService() {
		if (_service == null) {
			_service = (AssetEntryService)PortalBeanLocatorUtil.locate(
				AssetEntryService.class.getName());
		}

		return _service;
	}

	private static AssetEntryService _service;

}