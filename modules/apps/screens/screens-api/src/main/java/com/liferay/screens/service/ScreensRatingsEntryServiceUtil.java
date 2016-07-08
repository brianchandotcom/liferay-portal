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
 * Provides the remote service utility for ScreensRatingsEntry. This utility wraps
 * {@link com.liferay.screens.service.impl.ScreensRatingsEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author José Manuel Navarro
 * @see ScreensRatingsEntryService
 * @see com.liferay.screens.service.base.ScreensRatingsEntryServiceBaseImpl
 * @see com.liferay.screens.service.impl.ScreensRatingsEntryServiceImpl
 * @generated
 */
@ProviderType
public class ScreensRatingsEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.screens.service.impl.ScreensRatingsEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.portal.kernel.json.JSONObject deleteRatingEntry(
		long classPK, java.lang.String className, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteRatingEntry(classPK, className, stepCount);
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
		long classPK, java.lang.String className, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRatingsEntries(classPK, className, stepCount);
	}

	public static com.liferay.portal.kernel.json.JSONObject getRatingsEntries(
		long entryId, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRatingsEntries(entryId, stepCount);
	}

	public static com.liferay.portal.kernel.json.JSONObject updateRatingEntry(
		long classPK, java.lang.String className, double score, int stepCount)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateRatingEntry(classPK, className, score, stepCount);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static ScreensRatingsEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<ScreensRatingsEntryService, ScreensRatingsEntryService> _serviceTracker =
		ServiceTrackerFactory.open(ScreensRatingsEntryService.class);
}