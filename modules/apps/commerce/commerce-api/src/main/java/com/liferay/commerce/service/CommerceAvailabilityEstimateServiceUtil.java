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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceAvailabilityEstimate. This utility wraps
 * <code>com.liferay.commerce.service.impl.CommerceAvailabilityEstimateServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityEstimateService
 * @generated
 */
public class CommerceAvailabilityEstimateServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.service.impl.CommerceAvailabilityEstimateServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			addCommerceAvailabilityEstimate(
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceAvailabilityEstimate(
			titleMap, priority, serviceContext);
	}

	public static void deleteCommerceAvailabilityEstimate(
			long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			getCommerceAvailabilityEstimate(long commerceAvailabilityEstimateId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId);
	}

	public static java.util.List
		<com.liferay.commerce.model.CommerceAvailabilityEstimate>
				getCommerceAvailabilityEstimates(
					long companyId, int start, int end,
					com.liferay.portal.kernel.util.OrderByComparator
						<com.liferay.commerce.model.
							CommerceAvailabilityEstimate> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAvailabilityEstimates(
			companyId, start, end, orderByComparator);
	}

	public static int getCommerceAvailabilityEstimatesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceAvailabilityEstimatesCount(companyId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceAvailabilityEstimate
			updateCommerceAvailabilityEstimate(
				long commerceAvailabilityEstimateId,
				java.util.Map<java.util.Locale, String> titleMap,
				double priority,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceAvailabilityEstimate(
			commerceAvailabilityEstimateId, titleMap, priority, serviceContext);
	}

	public static CommerceAvailabilityEstimateService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceAvailabilityEstimateService,
		 CommerceAvailabilityEstimateService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAvailabilityEstimateService.class);

		ServiceTracker
			<CommerceAvailabilityEstimateService,
			 CommerceAvailabilityEstimateService> serviceTracker =
				new ServiceTracker
					<CommerceAvailabilityEstimateService,
					 CommerceAvailabilityEstimateService>(
						 bundle.getBundleContext(),
						 CommerceAvailabilityEstimateService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}