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
 * Provides the remote service utility for CommerceProductOptionValue. This utility wraps
 * {@link com.liferay.commerce.product.service.impl.CommerceProductOptionValueServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Marco Leo
 * @see CommerceProductOptionValueService
 * @see com.liferay.commerce.product.service.base.CommerceProductOptionValueServiceBaseImpl
 * @see com.liferay.commerce.product.service.impl.CommerceProductOptionValueServiceImpl
 * @generated
 */
@ProviderType
public class CommerceProductOptionValueServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.product.service.impl.CommerceProductOptionValueServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.product.model.CommerceProductOptionValue addCommerceProductOptionValue(
		long commerceProductOptionId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceProductOptionValue(commerceProductOptionId,
			titleMap, priority, serviceContext);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		com.liferay.commerce.product.model.CommerceProductOptionValue commerceProductOptionValue)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductOptionValue(commerceProductOptionValue);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue deleteCommerceProductOptionValue(
		long commerceProductOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .deleteCommerceProductOptionValue(commerceProductOptionValueId);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue getCommerceProductOptionValue(
		long commerceProductOptionValueId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionValue(commerceProductOptionValueId);
	}

	public static com.liferay.commerce.product.model.CommerceProductOptionValue updateCommerceProductOptionValue(
		long commerceProductOptionValueId,
		java.util.Map<java.util.Locale, java.lang.String> titleMap,
		int priority,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceProductOptionValue(commerceProductOptionValueId,
			titleMap, priority, serviceContext);
	}

	public static int getCommerceProductOptionValuesCount(
		long commerceProductOptionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionValuesCount(commerceProductOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionValues(commerceProductOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.product.model.CommerceProductOptionValue> getCommerceProductOptionValues(
		long commerceProductOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.product.model.CommerceProductOptionValue> orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .getCommerceProductOptionValues(commerceProductOptionId,
			start, end, orderByComparator);
	}

	public static CommerceProductOptionValueService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceProductOptionValueService, CommerceProductOptionValueService> _serviceTracker =
		ServiceTrackerFactory.open(CommerceProductOptionValueService.class);
}