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

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceAddressRestrictionService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressRestrictionService
 * @generated
 */
@ProviderType
public class CommerceAddressRestrictionServiceWrapper
	implements CommerceAddressRestrictionService,
		ServiceWrapper<CommerceAddressRestrictionService> {
	public CommerceAddressRestrictionServiceWrapper(
		CommerceAddressRestrictionService commerceAddressRestrictionService) {
		_commerceAddressRestrictionService = commerceAddressRestrictionService;
	}

	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction addCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceAddressRestrictionService.addCommerceAddressRestriction(className,
			classPK, commerceCountryId, serviceContext);
	}

	@Override
	public void deleteCommerceAddressRestriction(
		long commerceAddressRestrictionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		_commerceAddressRestrictionService.deleteCommerceAddressRestriction(commerceAddressRestrictionId);
	}

	@Override
	public com.liferay.commerce.model.CommerceAddressRestriction fetchCommerceAddressRestriction(
		String className, long classPK, long commerceCountryId) {
		return _commerceAddressRestrictionService.fetchCommerceAddressRestriction(className,
			classPK, commerceCountryId);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceAddressRestriction> getCommerceAddressRestrictions(
		String className, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.model.CommerceAddressRestriction> orderByComparator) {
		return _commerceAddressRestrictionService.getCommerceAddressRestrictions(className,
			classPK, start, end, orderByComparator);
	}

	@Override
	public int getCommerceAddressRestrictionsCount(String className,
		long classPK) {
		return _commerceAddressRestrictionService.getCommerceAddressRestrictionsCount(className,
			classPK);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceAddressRestrictionService.getOSGiServiceIdentifier();
	}

	@Override
	public boolean isCommerceAddressRestricted(String className, long classPK,
		long commerceCountryId) {
		return _commerceAddressRestrictionService.isCommerceAddressRestricted(className,
			classPK, commerceCountryId);
	}

	@Override
	public boolean isCommercePaymentMethodRestricted(
		long commercePaymentMethodId, long commerceCountryId) {
		return _commerceAddressRestrictionService.isCommercePaymentMethodRestricted(commercePaymentMethodId,
			commerceCountryId);
	}

	@Override
	public boolean isCommerceShippingMethodRestricted(
		long commerceShippingMethodId, long commerceCountryId) {
		return _commerceAddressRestrictionService.isCommerceShippingMethodRestricted(commerceShippingMethodId,
			commerceCountryId);
	}

	@Override
	public CommerceAddressRestrictionService getWrappedService() {
		return _commerceAddressRestrictionService;
	}

	@Override
	public void setWrappedService(
		CommerceAddressRestrictionService commerceAddressRestrictionService) {
		_commerceAddressRestrictionService = commerceAddressRestrictionService;
	}

	private CommerceAddressRestrictionService _commerceAddressRestrictionService;
}