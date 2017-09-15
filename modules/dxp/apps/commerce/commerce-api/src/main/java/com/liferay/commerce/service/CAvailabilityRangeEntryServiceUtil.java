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

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CAvailabilityRangeEntry. This utility wraps
 * {@link com.liferay.commerce.service.impl.CAvailabilityRangeEntryServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CAvailabilityRangeEntryService
 * @see com.liferay.commerce.service.base.CAvailabilityRangeEntryServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CAvailabilityRangeEntryServiceImpl
 * @generated
 */
@ProviderType
public class CAvailabilityRangeEntryServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CAvailabilityRangeEntryServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCAvailabilityRangeEntry(
		long cAvailabilityRangeEntryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCAvailabilityRangeEntry(cAvailabilityRangeEntryId);
	}

	public static com.liferay.commerce.model.CAvailabilityRangeEntry fetchCAvailabilityRangeEntry(
		long groupId, long cpDefinitionId) {
		return getService().fetchCAvailabilityRangeEntry(groupId, cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CAvailabilityRangeEntry updateCAvailabilityRangeEntry(
		long cAvailabilityRangeEntryId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCAvailabilityRangeEntry(cAvailabilityRangeEntryId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	public static CAvailabilityRangeEntryService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CAvailabilityRangeEntryService, CAvailabilityRangeEntryService> _serviceTracker =
		ServiceTrackerFactory.open(CAvailabilityRangeEntryService.class);
}