/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service;

import com.liferay.portal.kernel.model.Country;

/**
 * Provides a wrapper for {@link CountryService}.
 *
 * @author Brian Wing Shun Chan
 * @see CountryService
 * @generated
 */
public class CountryServiceWrapper
	implements CountryService, ServiceWrapper<CountryService> {

	public CountryServiceWrapper() {
		this(null);
	}

	public CountryServiceWrapper(CountryService countryService) {
		_countryService = countryService;
	}

	@Override
	public Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
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
	public Country addCountry(
			String name, String a2, String a3, String number, String idd,
			boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.addCountry(name, a2, a3, number, idd, active);
	}

	@Override
	public void deleteCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		_countryService.deleteCountry(countryId);
	}

	@Override
	public Country fetchCountry(long countryId) {
		return _countryService.fetchCountry(countryId);
	}

	@Override
	public Country fetchCountryByA2(long companyId, String a2) {
		return _countryService.fetchCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country fetchCountryByA2(String a2) {
		return _countryService.fetchCountryByA2(a2);
	}

	@Override
	public Country fetchCountryByA3(long companyId, String a3) {
		return _countryService.fetchCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country fetchCountryByA3(String a3) {
		return _countryService.fetchCountryByA3(a3);
	}

	@Override
	public java.util.List<Country> getCompanyCountries(long companyId) {
		return _countryService.getCompanyCountries(companyId);
	}

	@Override
	public java.util.List<Country> getCompanyCountries(
		long companyId, boolean active) {

		return _countryService.getCompanyCountries(companyId, active);
	}

	@Override
	public java.util.List<Country> getCompanyCountries(
		long companyId, boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator) {

		return _countryService.getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public java.util.List<Country> getCompanyCountries(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<Country>
			orderByComparator) {

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
	public java.util.List<Country> getCountries() {
		return _countryService.getCountries();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public java.util.List<Country> getCountries(boolean active) {
		return _countryService.getCountries(active);
	}

	@Override
	public Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountry(countryId);
	}

	@Override
	public Country getCountryByA2(long companyId, String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByA2(String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA2(a2);
	}

	@Override
	public Country getCountryByA3(long companyId, String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByA3(String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA3(a3);
	}

	@Override
	public Country getCountryByName(long companyId, String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByName(companyId, name);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByName(String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByName(name);
	}

	@Override
	public Country getCountryByNumber(long companyId, String number)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByNumber(companyId, number);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return _countryService.getOSGiServiceIdentifier();
	}

	@Override
	public com.liferay.portal.kernel.search.BaseModelSearchResult<Country>
			searchCountries(
				long companyId, Boolean active, String keywords, int start,
				int end,
				com.liferay.portal.kernel.util.OrderByComparator<Country>
					orderByComparator)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	@Override
	public Country updateActive(long countryId, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.updateActive(countryId, active);
	}

	@Override
	public Country updateCountry(
			long countryId, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	@Override
	public Country updateGroupFilterEnabled(
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