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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceShippingFixedOption. This utility wraps
 * {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionService
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption addCommerceShippingFixedOption(
		long commerceShippingMethodId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.math.BigDecimal amount, double priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShippingFixedOption(commerceShippingMethodId,
			nameMap, descriptionMap, amount, priority, serviceContext);
	}

	public static void deleteCommerceShippingFixedOption(
		long commerceShippingFixedOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long commerceShippingFixedOptionId) {
		return getService()
				   .fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {
		return getService()
				   .getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption> orderByComparator) {
		return getService()
				   .getCommerceShippingFixedOptions(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {
		return getService()
				   .getCommerceShippingFixedOptionsCount(commerceShippingMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption updateCommerceShippingFixedOption(
		long commerceShippingFixedOptionId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.util.Map<java.util.Locale, String> descriptionMap,
		java.math.BigDecimal amount, double priority)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShippingFixedOption(commerceShippingFixedOptionId,
			nameMap, descriptionMap, amount, priority);
	}

	public static CommerceShippingFixedOptionService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingFixedOptionService, CommerceShippingFixedOptionService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingFixedOptionService.class);

		ServiceTracker<CommerceShippingFixedOptionService, CommerceShippingFixedOptionService> serviceTracker =
			new ServiceTracker<CommerceShippingFixedOptionService, CommerceShippingFixedOptionService>(bundle.getBundleContext(),
				CommerceShippingFixedOptionService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}