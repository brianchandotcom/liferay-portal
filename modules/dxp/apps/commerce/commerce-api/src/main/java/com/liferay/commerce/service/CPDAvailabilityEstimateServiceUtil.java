/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CPDAvailabilityEstimate. This utility wraps
 * {@link com.liferay.commerce.service.impl.CPDAvailabilityEstimateServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CPDAvailabilityEstimateService
 * @see com.liferay.commerce.service.base.CPDAvailabilityEstimateServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CPDAvailabilityEstimateServiceImpl
 * @generated
 */
@ProviderType
public class CPDAvailabilityEstimateServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CPDAvailabilityEstimateServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static void deleteCPDAvailabilityEstimate(
		long cpdAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCPDAvailabilityEstimate(cpdAvailabilityEstimateId);
	}

	public static com.liferay.commerce.model.CPDAvailabilityEstimate fetchCPDAvailabilityEstimateByCPDefinitionId(
		long cpDefinitionId) {
		return getService()
				   .fetchCPDAvailabilityEstimateByCPDefinitionId(cpDefinitionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CPDAvailabilityEstimate updateCPDAvailabilityEstimate(
		long cpdAvailabilityEstimateId, long cpDefinitionId,
		long commerceAvailabilityEstimateId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCPDAvailabilityEstimate(cpdAvailabilityEstimateId,
			cpDefinitionId, commerceAvailabilityEstimateId, serviceContext);
	}

	public static CPDAvailabilityEstimateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CPDAvailabilityEstimateService, CPDAvailabilityEstimateService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CPDAvailabilityEstimateService.class);

		ServiceTracker<CPDAvailabilityEstimateService, CPDAvailabilityEstimateService> serviceTracker =
			new ServiceTracker<CPDAvailabilityEstimateService, CPDAvailabilityEstimateService>(bundle.getBundleContext(),
				CPDAvailabilityEstimateService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}