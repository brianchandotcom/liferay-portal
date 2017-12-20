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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CShippingFixedOptionRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CShippingFixedOptionRelService
 * @generated
 */
@ProviderType
public class CShippingFixedOptionRelServiceWrapper
	implements CShippingFixedOptionRelService,
		ServiceWrapper<CShippingFixedOptionRelService> {
	public CShippingFixedOptionRelServiceWrapper(
		CShippingFixedOptionRelService cShippingFixedOptionRelService) {
		_cShippingFixedOptionRelService = cShippingFixedOptionRelService;
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel addCShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weightFrom,
		double weightTo, double fixedPrice, double rateUnitWeightPrice,
		double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelService.addCShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	@Override
	public void deleteCShippingFixedOptionRel(long cShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_cShippingFixedOptionRelService.deleteCShippingFixedOptionRel(cShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long cShippingFixedOptionRelId) {
		return _cShippingFixedOptionRelService.fetchCShippingFixedOptionRel(cShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel fetchCShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, java.lang.String zip, double weight) {
		return _cShippingFixedOptionRelService.fetchCShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return _cShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return _cShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return _cShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return _cShippingFixedOptionRelService.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> getCShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel> orderByComparator) {
		return _cShippingFixedOptionRelService.getCShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	@Override
	public int getCShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return _cShippingFixedOptionRelService.getCShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _cShippingFixedOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CShippingFixedOptionRel updateCShippingFixedOptionRel(
		long cShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, java.lang.String zip,
		double weightFrom, double weightTo, double fixedPrice,
		double rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _cShippingFixedOptionRelService.updateCShippingFixedOptionRel(cShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	@Override
	public CShippingFixedOptionRelService getWrappedService() {
		return _cShippingFixedOptionRelService;
	}

	@Override
	public void setWrappedService(
		CShippingFixedOptionRelService cShippingFixedOptionRelService) {
		_cShippingFixedOptionRelService = cShippingFixedOptionRelService;
	}

	private CShippingFixedOptionRelService _cShippingFixedOptionRelService;
}