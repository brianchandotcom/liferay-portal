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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDefinitionAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDefinitionAvailabilityRangeService
 * @see com.liferay.commerce.service.base.CPDefinitionAvailabilityRangeServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeServiceImpl
 * @generated
 */
@ProviderType
public class CPDefinitionAvailabilityRangeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDefinitionAvailabilityRangeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRangeId);
	}

	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange fetchCPDefinitionAvailabilityRangeByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .fetchCPDefinitionAvailabilityRangeByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CPDefinitionAvailabilityRange updateCPDefinitionAvailabilityRange(
		long cpDefinitionAvailabilityRangeId, long cpDefinitionId,
		long commerceAvailabilityRangeId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDefinitionAvailabilityRange(cpDefinitionAvailabilityRangeId,
			cpDefinitionId, commerceAvailabilityRangeId, serviceContext);
	}

	public static CPDefinitionAvailabilityRangeService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDefinitionAvailabilityRangeService, CPDefinitionAvailabilityRangeService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDefinitionAvailabilityRangeService.class);

		ServiceTracker<CPDefinitionAvailabilityRangeService, CPDefinitionAvailabilityRangeService> serviceTracker =
			new ServiceTracker<CPDefinitionAvailabilityRangeService, CPDefinitionAvailabilityRangeService>(bundle.getBundleContext(),
				CPDefinitionAvailabilityRangeService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}