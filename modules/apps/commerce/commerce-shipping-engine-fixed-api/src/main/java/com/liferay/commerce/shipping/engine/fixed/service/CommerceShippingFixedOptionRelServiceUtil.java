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

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CommerceShippingFixedOptionRel. This utility wraps
 * <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelService
 * @generated
 */
public class CommerceShippingFixedOptionRelServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.commerce.shipping.engine.fixed.service.impl.CommerceShippingFixedOptionRelServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				long userId, long groupId, long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long commerceCountryId,
				long commerceRegionId, String zip, double weightFrom,
				double weightTo, java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceShippingFixedOptionRel(
			userId, groupId, commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceInventoryWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
				long commerceShippingMethodId,
				long commerceShippingFixedOptionId,
				long commerceInventoryWarehouseId, long commerceCountryId,
				long commerceRegionId, String zip, double weightFrom,
				double weightTo, java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
				com.liferay.portal.kernel.service.ServiceContext serviceContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().addCommerceShippingFixedOptionRel(
			commerceShippingMethodId, commerceShippingFixedOptionId,
			commerceInventoryWarehouseId, commerceCountryId, commerceRegionId,
			zip, weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage, serviceContext);
	}

	public static void deleteCommerceShippingFixedOptionRel(
			long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {

		getService().deleteCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().fetchCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId);
	}

	public static java.util.List
		<com.liferay.commerce.shipping.engine.fixed.model.
			CommerceShippingFixedOptionRel>
					getCommerceShippingMethodFixedOptionRels(
						long commerceShippingMethodId, int start, int end,
						com.liferay.portal.kernel.util.OrderByComparator
							<com.liferay.commerce.shipping.engine.fixed.model.
								CommerceShippingFixedOptionRel>
									orderByComparator)
				throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShippingMethodFixedOptionRels(
			commerceShippingMethodId, start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
			long commerceShippingMethodId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return getService().getCommerceShippingMethodFixedOptionRelsCount(
			commerceShippingMethodId);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.
		CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
				long commerceShippingFixedOptionRelId,
				long commerceInventoryWarehouseId, long commerceCountryId,
				long commerceRegionId, String zip, double weightFrom,
				double weightTo, java.math.BigDecimal fixedPrice,
				java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
			throws com.liferay.portal.kernel.exception.PortalException {

		return getService().updateCommerceShippingFixedOptionRel(
			commerceShippingFixedOptionRelId, commerceInventoryWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage);
	}

	public static CommerceShippingFixedOptionRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CommerceShippingFixedOptionRelService,
		 CommerceShippingFixedOptionRelService> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceShippingFixedOptionRelService.class);

		ServiceTracker
			<CommerceShippingFixedOptionRelService,
			 CommerceShippingFixedOptionRelService> serviceTracker =
				new ServiceTracker
					<CommerceShippingFixedOptionRelService,
					 CommerceShippingFixedOptionRelService>(
						 bundle.getBundleContext(),
						 CommerceShippingFixedOptionRelService.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}