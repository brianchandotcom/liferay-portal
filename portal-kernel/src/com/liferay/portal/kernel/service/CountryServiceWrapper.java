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

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CountryServiceUtil} to access the country remote service. Add custom service methods to <code>com.liferay.portal.service.impl.CountryServiceImpl</code> and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */
	@Override
	public com.liferay.portal.kernel.model.Country addCountry(
			java.lang.String name, java.lang.String a2, java.lang.String a3,
			java.lang.String number, java.lang.String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.addCountry(name, a2, a3, number, idd, active);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountry(
		long countryId) {

		return _countryService.fetchCountry(countryId);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA2(
		java.lang.String a2) {

		return _countryService.fetchCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country fetchCountryByA3(
		java.lang.String a3) {

		return _countryService.fetchCountryByA3(a3);
	}

	@Override
	public java.util.List<com.liferay.portal.kernel.model.Country>
		getCountries() {

		return _countryService.getCountries();
	}

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
			java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByA3(
			java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByA3(a3);
	}

	@Override
	public com.liferay.portal.kernel.model.Country getCountryByName(
			java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {

		return _countryService.getCountryByName(name);
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
	public CountryService getWrappedService() {
		return _countryService;
	}

	@Override
	public void setWrappedService(CountryService countryService) {
		_countryService = countryService;
	}

	private CountryService _countryService;

}