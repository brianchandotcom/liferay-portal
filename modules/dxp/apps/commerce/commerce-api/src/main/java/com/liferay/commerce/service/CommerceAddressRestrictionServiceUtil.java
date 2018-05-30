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
 * Provides the remote service utility for CommerceAddressRestriction. This utility wraps
 * {@link com.liferay.commerce.service.impl.CommerceAddressRestrictionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionService
 * @see com.liferay.commerce.service.base.CommerceAddressRestrictionServiceBaseImpl
 * @see com.liferay.commerce.service.impl.CommerceAddressRestrictionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.service.impl.CommerceAddressRestrictionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.model.CommerceAddressRestriction addCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceAddressRestriction(className, classPK,
			commerceCountryId, serviceContext);
	}

	public static void deleteCommerceAddressRestriction(
		long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	public static com.liferay.commerce.model.CommerceAddressRestriction fetchCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId) {
		return getService()
				   .fetchCommerceAddressRestriction(className, classPK,
			commerceCountryId);
	}

	public static java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> getCommerceAddressRestrictions(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddressRestriction> orderByComparator) {
		return getService()
				   .getCommerceAddressRestrictions(className, classPK, start,
			end, orderByComparator);
	}

	public static int getCommerceAddressRestrictionsCount(String className,
		long classPK) {
		return getService()
				   .getCommerceAddressRestrictionsCount(className, classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static boolean isCommerceAddressRestricted(String className,
		long classPK, long commerceCountryId) {
		return getService()
				   .isCommerceAddressRestricted(className, classPK,
			commerceCountryId);
	}

	public static boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId) {
		return getService()
				   .isCommercePaymentMethodRestricted(commercePaymentMethodId,
			commerceCountryId);
	}

	public static boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId) {
		return getService()
				   .isCommerceShippingMethodRestricted(commerceShippingMethodId,
			commerceCountryId);
	}

	public static CommerceAddressRestrictionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceAddressRestrictionService, CommerceAddressRestrictionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceAddressRestrictionService.class);

		ServiceTracker<CommerceAddressRestrictionService, CommerceAddressRestrictionService> serviceTracker =
			new ServiceTracker<CommerceAddressRestrictionService, CommerceAddressRestrictionService>(bundle.getBundleContext(),
				CommerceAddressRestrictionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}