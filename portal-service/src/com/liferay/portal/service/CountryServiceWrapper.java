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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

/**
 * Provides a wrapper for {@link CountryService}.
 *
 * @author Brian Wing Shun Chan
 * @see CountryService
 * @generated
 */
@ProviderType
public class CountryServiceWrapper implements CountryService,
	ServiceWrapper<CountryService> {
	public CountryServiceWrapper(CountryService countryService) {
		_countryService = countryService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	@Override
	public java.lang.String getBeanIdentifier() {
		return _countryService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	@Override
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_countryService.setBeanIdentifier(beanIdentifier);
	}

	@Override
	public com.liferay.portal.model.Country addCountry(java.lang.String name,
		java.lang.String a2, java.lang.String a3, java.lang.String number,
		java.lang.String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _countryService.addCountry(name, a2, a3, number, idd, active);
	}

	@Override
	public com.liferay.portal.model.Country fetchCountry(long countryId) {
		return _countryService.fetchCountry(countryId);
	}

	@Override
	public com.liferay.portal.model.Country fetchCountryByA2(
		java.lang.String a2) {
		return _countryService.fetchCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.model.Country fetchCountryByA3(
		java.lang.String a3) {
		return _countryService.fetchCountryByA3(a3);
	}

	@Override
	public java.util.List<com.liferay.portal.model.Country> getCountries() {
		return _countryService.getCountries();
	}

	@Override
	public java.util.List<com.liferay.portal.model.Country> getCountries(
		boolean active) {
		return _countryService.getCountries(active);
	}

	@Override
	public com.liferay.portal.model.Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _countryService.getCountry(countryId);
	}

	@Override
	public com.liferay.portal.model.Country getCountryByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _countryService.getCountryByA2(a2);
	}

	@Override
	public com.liferay.portal.model.Country getCountryByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _countryService.getCountryByA3(a3);
	}

	@Override
	public com.liferay.portal.model.Country getCountryByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException {
		return _countryService.getCountryByName(name);
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #getWrappedService}
	 */
	@Deprecated
	public CountryService getWrappedCountryService() {
		return _countryService;
	}

	/**
	 * @deprecated As of 6.1.0, replaced by {@link #setWrappedService}
	 */
	@Deprecated
	public void setWrappedCountryService(CountryService countryService) {
		_countryService = countryService;
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