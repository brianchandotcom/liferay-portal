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

import com.liferay.portal.kernel.exception.PortalException;

/**
 * Provides the remote service utility for ScreensRatingsEntry. This utility wraps
 * <code>com.liferay.screens.service.impl.ScreensRatingsEntryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensRatingsEntryService
 * @generated
 */
public class ScreensRatingsEntryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.screens.service.impl.ScreensRatingsEntryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONObject deleteRatingsEntry(
			long classPK, String className, int ratingsLength)
		throws PortalException {

		return getService().deleteRatingsEntry(
			classPK, className, ratingsLength);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
			long assetEntryId, int ratingsLength)
		throws PortalException {

		return getService().getRatingsEntries(assetEntryId, ratingsLength);
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
			long classPK, String className, int ratingsLength)
		throws PortalException {

		return getService().getRatingsEntries(
			classPK, className, ratingsLength);
	}

	public static com.liferay.portal.kernel.json.JSONObject updateRatingsEntry(
			long classPK, String className, double score, int ratingsLength)
		throws PortalException {

		return getService().updateRatingsEntry(
			classPK, className, score, ratingsLength);
	}

	public static ScreensRatingsEntryService getService() {
		return _service;
	}

	private static volatile ScreensRatingsEntryService _service;

}