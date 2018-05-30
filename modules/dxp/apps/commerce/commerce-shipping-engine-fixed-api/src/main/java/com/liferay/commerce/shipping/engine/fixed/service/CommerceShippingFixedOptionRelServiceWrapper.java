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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceShippingFixedOptionRelService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceShippingFixedOptionRelService
 * @generated
 */
@ProviderType
public class CommerceShippingFixedOptionRelServiceWrapper
	implements CommerceShippingFixedOptionRelService,
		ServiceWrapper<CommerceShippingFixedOptionRelService> {
	public CommerceShippingFixedOptionRelServiceWrapper(
		CommerceShippingFixedOptionRelService commerceShippingFixedOptionRelService) {
		_commerceShippingFixedOptionRelService = commerceShippingFixedOptionRelService;
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel addCommerceShippingFixedOptionRel(
		long commerceShippingMethodId, long commerceShippingFixedOptionId,
		long commerceWarehouseId, long commerceCountryId,
		long commerceRegionId, String zip, double weightFrom, double weightTo,
		java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRelService.addCommerceShippingFixedOptionRel(commerceShippingMethodId,
			commerceShippingFixedOptionId, commerceWarehouseId,
			commerceCountryId, commerceRegionId, zip, weightFrom, weightTo,
			fixedPrice, rateUnitWeightPrice, ratePercentage, serviceContext);
	}

	@Override
	public void deleteCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceShippingFixedOptionRelService.deleteCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId) {
		return _commerceShippingFixedOptionRelService.fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId);
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel fetchCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionId, long commerceCountryId,
		long commerceRegionId, String zip, double weight) {
		return _commerceShippingFixedOptionRelService.fetchCommerceShippingFixedOptionRel(commerceShippingFixedOptionId,
			commerceCountryId, commerceRegionId, zip, weight);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingFixedOptionRels(
		long commerceShippingFixedOptionId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingFixedOptionRels(commerceShippingFixedOptionId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingFixedOptionRelsCount(
		long commerceShippingFixedOptionId) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingFixedOptionRelsCount(commerceShippingFixedOptionId);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> getCommerceShippingMethodFixedOptionRels(
		long commerceShippingMethodId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel> orderByComparator) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRels(commerceShippingMethodId,
			start, end, orderByComparator);
	}

	@Override
	public int getCommerceShippingMethodFixedOptionRelsCount(
		long commerceShippingMethodId) {
		return _commerceShippingFixedOptionRelService.getCommerceShippingMethodFixedOptionRelsCount(commerceShippingMethodId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceShippingFixedOptionRelService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionRel updateCommerceShippingFixedOptionRel(
		long commerceShippingFixedOptionRelId, long commerceWarehouseId,
		long commerceCountryId, long commerceRegionId, String zip,
		double weightFrom, double weightTo, java.math.BigDecimal fixedPrice,
		java.math.BigDecimal rateUnitWeightPrice, double ratePercentage)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceShippingFixedOptionRelService.updateCommerceShippingFixedOptionRel(commerceShippingFixedOptionRelId,
			commerceWarehouseId, commerceCountryId, commerceRegionId, zip,
			weightFrom, weightTo, fixedPrice, rateUnitWeightPrice,
			ratePercentage);
	}

	@Override
	public CommerceShippingFixedOptionRelService getWrappedService() {
		return _commerceShippingFixedOptionRelService;
	}

	@Override
	public void setWrappedService(
		CommerceShippingFixedOptionRelService commerceShippingFixedOptionRelService) {
		_commerceShippingFixedOptionRelService = commerceShippingFixedOptionRelService;
	}

	private CommerceShippingFixedOptionRelService _commerceShippingFixedOptionRelService;
}