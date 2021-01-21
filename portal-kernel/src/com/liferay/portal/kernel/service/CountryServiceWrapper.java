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

package com.liferay.portal.kernel.service;

/**
 * Provides a wrapper for {@link CountryService}.
 *
 * @author Brian Wing Shun Chan
 * @see CountryService
 * @generated
 */
public class CountryServiceWrapper
	implements CountryService, ServiceWrapper<CountryService> {

	public CountryServiceWrapper(CountryService countryService) {
		_countryService = countryService;
	}

	@Override
	public com.liferay.portal.kernel.model.Country addCountry(
			java.lang.String a2, java.lang.String a3, boolean active,
			boolean billingAllowed, java.lang.String idd, java.lang.String name,
			java.lang.String number, double position, boolean shippingAllowed,
			boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.addCountry(
			a2, a3, active, billingAllowed, idd, name, number, position,
			shippingAllowed, subjectToVAT, zipRequired, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country addCountry(
			java.lang.String name, java.lang.String a2, java.lang.String a3,
			java.lang.String number, java.lang.String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.addCountry(name, a2, a3, number, idd, active);
	}

	@Override
	public void deleteCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_countryService.deleteCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountry(
		long countryId) {

		return _countryService.fetchCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA2(
		long companyId, java.lang.String a2) {

		return _countryService.fetchCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA2(
		java.lang.String a2) {

		return _countryService.fetchCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA3(
		long companyId, java.lang.String a3) {

		return _countryService.fetchCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA3(
		java.lang.String a3) {

		return _countryService.fetchCountryByA3(a3);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId) {

		return _countryService.getCompanyCountries(companyId);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(long companyId, boolean active) {

		return _countryService.getCompanyCountries(companyId, active);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, boolean active, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return _countryService.getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCompanyCountries(
			long companyId, int start, int end,
			com.liferay.portal.kernel.util.OrderByComparator
				<com.liferay.portal.kernel.model.Country> orderByComparator) {

		return _countryService.getCompanyCountries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyCountriesCount(long companyId) {
		return _countryService.getCompanyCountriesCount(companyId);
	}

	@Override
	public int getCompanyCountriesCount(long companyId, boolean active) {
		return _countryService.getCompanyCountriesCount(companyId, active);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCountries() {

		return _countryService.getCountries();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country> getCountries(
		boolean active) {

		return _countryService.getCountries(active);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA2(
			long companyId, java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA2(
			java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA3(
			long companyId, java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA3(
			java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA3(a3);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByName(
			long companyId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByName(companyId, name);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public com.liferay.portal.kernel.model.Country getCountryByName(
			java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByName(name);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByNumber(
			long companyId, java.lang.String number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByNumber(companyId, number);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public java.lang.String getOSGiServiceIdentifier() {
		return _countryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult
		<com.liferay.portal.kernel.model.Country> searchCountries(
				long companyId, java.lang.Boolean active,
				java.lang.String keywords, int start, int end,
				com.liferay.portal.kernel.util.OrderByComparator
					<com.liferay.portal.kernel.model.Country> orderByComparator)
			throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateActive(
			long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.updateActive(countryId, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateCountry(
			long countryId, java.lang.String a2, java.lang.String a3,
			boolean active, boolean billingAllowed, java.lang.String idd,
			java.lang.String name, java.lang.String number, double position,
			boolean shippingAllowed, boolean subjectToVAT)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	@Override
	public com.liferay.portal.kernel.model.Country updateGroupFilterEnabled(
			long countryId, boolean groupFilterEnabled)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.updateGroupFilterEnabled(
			countryId, groupFilterEnabled);
	}

	@Override
	public CountryService getWrappedService() {
		return _countryService;
	}

	@Override
	public void setWrappedService(CountryService countryService) {
		_countryService = countryService;
	}

	private CountryService _countryService;

}