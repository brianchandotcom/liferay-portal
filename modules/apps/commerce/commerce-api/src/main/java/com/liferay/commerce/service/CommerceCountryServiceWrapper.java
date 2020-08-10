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

import com.liferay.portal.kernel.service.ServiceWrapper;

/**
 * Provides a wrapper for {@link CommerceCountryService}.
 *
 * @author Alessio Antonio Rendina
 * @see CommerceCountryService
 * @generated
 */
public class CommerceCountryServiceWrapper
	implements CommerceCountryService, ServiceWrapper<CommerceCountryService> {

	public CommerceCountryServiceWrapper(
		CommerceCountryService commerceCountryService) {

		_commerceCountryService = commerceCountryService;
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry addCommerceCountry(
			java.util.Map<java.util.Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.addCommerceCountry(
			nameMap, billingAllowed, shippingAllowed, twoLettersISOCode,
			threeLettersISOCode, numericISOCode, subjectToVAT, priority, active,
			serviceContext);
	}

	@Override
	public void deleteCommerceCountry(long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_commerceCountryService.deleteCommerceCountry(commerceCountryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry fetchCommerceCountry(
			long companyId, String twoLettersISOCode)
		throws com.liferay.portal.kernel.security.auth.PrincipalException {

		return _commerceCountryService.fetchCommerceCountry(
			companyId, twoLettersISOCode);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountries(
			long companyId, boolean billingAllowed, boolean active) {

		return _commerceCountryService.getBillingCommerceCountries(
			companyId, billingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getBillingCommerceCountriesByChannelId(
			long commerceChannelId, int start, int end) {

		return _commerceCountryService.getBillingCommerceCountriesByChannelId(
			commerceChannelId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getCommerceCountries(long companyId, boolean active) {

		return _commerceCountryService.getCommerceCountries(companyId, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
			getCommerceCountries(
				long companyId, boolean active, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
			getCommerceCountries(
				long companyId, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.commerce.model.CommerceCountry>
						orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCommerceCountriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountriesCount(companyId);
	}

	@Override
	public int getCommerceCountriesCount(long companyId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountriesCount(
			companyId, active);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			long commerceCountryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountry(commerceCountryId);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry getCommerceCountry(
			long companyId, String twoLettersISOCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getCommerceCountry(
			companyId, twoLettersISOCode);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _commerceCountryService.getOSGiServiceIdentifier();
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountries(
			long companyId, boolean shippingAllowed, boolean active) {

		return _commerceCountryService.getShippingCommerceCountries(
			companyId, shippingAllowed, active);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
		getShippingCommerceCountriesByChannelId(
			long commerceChannelId, int start, int end) {

		return _commerceCountryService.getShippingCommerceCountriesByChannelId(
			commerceChannelId, start, end);
	}

	@Override
	public java.util.List<com.liferay.commerce.model.CommerceCountry>
			getWarehouseCommerceCountries(long companyId, boolean all)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.getWarehouseCommerceCountries(
			companyId, all);
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				long companyId, Boolean active, String keywords, int start,
				int end, com.liferay.portal.kernel.search.Sort sort)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.searchCommerceCountries(
			companyId, active, keywords, start, end, sort);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.commerce.model.CommerceCountry> searchCommerceCountries(
				com.liferay.portal.kernel.search.SearchContext searchContext)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.searchCommerceCountries(searchContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry setActive(
			long commerceCountryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.setActive(commerceCountryId, active);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry updateCommerceCountry(
			long commerceCountryId,
			java.util.Map<java.util.Locale, String> nameMap,
			boolean billingAllowed, boolean shippingAllowed,
			String twoLettersISOCode, String threeLettersISOCode,
			int numericISOCode, boolean subjectToVAT, double priority,
			boolean active,
			com.liferay.portal.kernel.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.updateCommerceCountry(
			commerceCountryId, nameMap, billingAllowed, shippingAllowed,
			twoLettersISOCode, threeLettersISOCode, numericISOCode,
			subjectToVAT, priority, active, serviceContext);
	}

	@Override
	public com.liferay.commerce.model.CommerceCountry
			updateCommerceCountryChannelFilter(
				long commerceCountryId, boolean enable)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _commerceCountryService.updateCommerceCountryChannelFilter(
			commerceCountryId, enable);
	}

	@Override
	public CommerceCountryService getWrappedService() {
		return _commerceCountryService;
	}

	@Override
	public void setWrappedService(
		CommerceCountryService commerceCountryService) {

		_commerceCountryService = commerceCountryService;
	}

	private CommerceCountryService _commerceCountryService;

}