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

package com.liferay.commerce.product.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CPAvailabilityRangeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CPAvailabilityRangeService
 * @see com.liferay.commerce.product.service.base.CPAvailabilityRangeServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CPAvailabilityRangeServiceImpl
 * @generated
 */
@ProviderType
public class CPAvailabilityRangeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CPAvailabilityRangeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CPAvailabilityRange addCPAvailabilityRange(
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().addCPAvailabilityRange(titleMap, serviceContext);
	}

	public static void deleteCPAvailabilityRange(long cpAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPAvailabilityRange(cpAvailabilityRangeId);
	}

	public static com.liferay.commerce.product.model.CPAvailabilityRange getCPAvailabilityRange(
		long cpAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getCPAvailabilityRange(cpAvailabilityRangeId);
	}

	public static java.util.List<com.liferay.commerce.product.model.CPAvailabilityRange> getCPAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CPAvailabilityRange> orderByComparator) {
		return getService()
				   .getCPAvailabilityRanges(groupId, start, end,
			orderByComparator);
	}

	public static int getCPAvailabilityRangesCount(long groupId) {
		return getService().getCPAvailabilityRangesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.product.model.CPAvailabilityRange updateCPAvailabilityRange(
		long cpAvailabilityRangeId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPAvailabilityRange(cpAvailabilityRangeId, titleMap,
			serviceContext);
	}

	public static CPAvailabilityRangeService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPAvailabilityRangeService, CPAvailabilityRangeService> _serviceTracker =
		ServiceTrackerFactory.open(CPAvailabilityRangeService.class);
}