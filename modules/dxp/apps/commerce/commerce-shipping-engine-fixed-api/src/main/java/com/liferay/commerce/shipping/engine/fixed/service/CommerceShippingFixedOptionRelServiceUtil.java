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

package com.liferay.commerce.shipping.engine.fixed.service;

import aQute.bnd.annotation.ProviderType;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceShippingFixedOptionRel. This utility wraps
 * {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelService
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionRelServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCommerceShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	public static void deleteCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService()
			.deleteCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) {
		return getService()
				   .fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight) {
		return getService()
				   .fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return getService()
				   .getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return getService()
				   .getCommerceShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	public static CommerceShippingFixedOptionRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CommerceShippingFixedOptionRelService, CommerceShippingFixedOptionRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(CommerceShippingFixedOptionRelService.class);

		ServiceTracker<CommerceShippingFixedOptionRelService, CommerceShippingFixedOptionRelService> serviceTracker =
			new ServiceTracker<CommerceShippingFixedOptionRelService, CommerceShippingFixedOptionRelService>(bundle.getBundleContext(),
				CommerceShippingFixedOptionRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}
}