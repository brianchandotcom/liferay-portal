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

package com.liferay.commerce.address.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCountryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryService
 * @generated
 */
@ProviderType
public class CommerceCountryServiceWrapper implements CommerceCountryService,
	ServiceWrapper<CommerceCountryService> {
	public CommerceCountryServiceWrapper(
		CommerceCountryService commerceCountryService) {
		_commerceCountryService = commerceCountryService;
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry addCommerceCountry(
		java.lang.String name, boolean allowsBilling, boolean allowsShipping,
		java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published,
		com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.addCommerceCountry(name, allowsBilling,
			allowsShipping, twoLettersISOCode, threeLettersISOCode,
			numericISOCode, subjectToVAT, priority, published, serviceContext);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry deleteCommerceCountry(
		long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.deleteCommerceCountry(commerceCountryId);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry fetchCommerceCountry(
		long commerceCountryId) {
		return _commerceCountryService.fetchCommerceCountry(commerceCountryId);
	}

	@Override
	public com.liferay.commerce.address.model.CommerceCountry updateCommerceCountry(
		long commerceCountryId, java.lang.String name, boolean allowsBilling,
		boolean allowsShipping, java.lang.String twoLettersISOCode,
		java.lang.String threeLettersISOCode, int numericISOCode,
		boolean subjectToVAT, double priority, boolean published)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _commerceCountryService.updateCommerceCountry(commerceCountryId,
			name, allowsBilling, allowsShipping, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority,
			published);
	}

	@Override
	public int getCommerceCountriesCount() {
		return _commerceCountryService.getCommerceCountriesCount();
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _commerceCountryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.address.model.CommerceCountry> getCommerceCountries(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.commerce.address.model.CommerceCountry> orderByComparator) {
		return _commerceCountryService.getCommerceCountries(start, end,
			orderByComparator);
	}

	@Override
	public CommerceCountryService getWrappedService() {
		return _commerceCountryService;
	}

	@Override
	public void setWrappedService(CommerceCountryService commerceCountryService) {
		_commerceCountryService = commerceCountryService;
	}

	private CommerceCountryService _commerceCountryService;
}