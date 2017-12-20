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

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the remote service utility for CShippingFixedOptionRel. This utility wraps
 * {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on a remote server. Methods of this service are expected to have security
 * checks based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelService
 * @see com.liferay.commerce.shipping.engine.fixed.service.base.CShippingFixedOptionRelServiceBaseImpl
 * @see com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelServiceImpl
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.commerce.shipping.engine.fixed.service.impl.CShippingFixedOptionRelServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */
	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .addCShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	public static void deleteCShippingFixedOptionRel(
		long cShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().deleteCShippingFixedOptionRel(cShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long cShippingFixedOptionRelId) {
		return getService()
				   .fetchCShippingFixedOptionRel(cShippingFixedOptionRelId);
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight) {
		return getService()
				   .fetchCShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	public static int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return getService()
				   .getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return getService()
				   .getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	public static java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return getService()
				   .getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	public static int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return getService()
				   .getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		long cShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService()
				   .updateCShippingFixedOptionRel(cShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	public static CShippingFixedOptionRelService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<CShippingFixedOptionRelService, CShippingFixedOptionRelService> _serviceTracker =
		ServiceTrackerFactory.open(CShippingFixedOptionRelService.class);
}