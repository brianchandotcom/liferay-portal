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

package com.liferay.screens.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for ScreensAssetEntry. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensAssetEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensAssetEntryService
 * @see com.liferay.screens.service.base.ScreensAssetEntryServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensAssetEntryServiceImpl
 * @generated
 */
@ProviderType
public class ScreensAssetEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensAssetEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONArray getAssetEntries(
		com.liferay.asset.kernel.service.persistence.AssetEntryQuery assetEntryQuery,
		java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetEntries(assetEntryQuery, locale);
	}

	public static com.liferay.portal.kernel.json.JSONArray getAssetEntries(
		long companyId, long groupId, java.lang.String portletItemName,
		java.util.Locale locale, int max)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getAssetEntries(companyId, groupId, portletItemName,
			locale, max);
	}

	public static com.liferay.portal.kernel.json.JSONObject getAssetEntry(
		long entryId, java.util.Locale locale)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getAssetEntry(entryId, locale);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ScreensAssetEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensAssetEntryService, ScreensAssetEntryService> _serviceTracker =
		ServiceTrackerFactory.open(ScreensAssetEntryService.class);
}