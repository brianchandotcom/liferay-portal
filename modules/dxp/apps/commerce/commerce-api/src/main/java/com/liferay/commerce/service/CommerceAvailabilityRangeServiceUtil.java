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
 * Provides the remote service utility for CommerceAvailabilityRange. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceAvailabilityRangeServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAvailabilityRangeService
 * @see com.liferay.commerce.service.base.CommerceAvailabilityRangeServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAvailabilityRangeServiceImpl
 * @generated
 */
@ProviderType
public class CommerceAvailabilityRangeServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAvailabilityRangeServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceAvailabilityRange addCommerceAvailabilityRange(
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceAvailabilityRange(titleMap, priority,
			serviceContext);
	}

	public static void deleteCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRange getCommerceAvailabilityRange(
		long commerceAvailabilityRangeId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceAvailabilityRange(commerceAvailabilityRangeId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAvailabilityRange> getCommerceAvailabilityRanges(
		long groupId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAvailabilityRange> orderByComparator) {
		return getService()
				   .getCommerceAvailabilityRanges(groupId, start, end,
			orderByComparator);
	}

	public static int getCommerceAvailabilityRangesCount(long groupId) {
		return getService().getCommerceAvailabilityRangesCount(groupId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.model.CommerceAvailabilityRange updateCommerceAvailabilityRange(
		long commerceAvailabilityRangeId,
		java.util.Map<java.util.Locale, String> titleMap, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceAvailabilityRange(commerceAvailabilityRangeId,
			titleMap, priority, serviceContext);
	}

	public static CommerceAvailabilityRangeService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAvailabilityRangeService, CommerceAvailabilityRangeService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAvailabilityRangeService.class);

		ServiceTracker<CommerceAvailabilityRangeService, CommerceAvailabilityRangeService> serviceTracker =
			new ServiceTracker<CommerceAvailabilityRangeService, CommerceAvailabilityRangeService>(bundle.getBundleContext(),
				CommerceAvailabilityRangeService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}