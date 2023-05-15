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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.util.List;

/**
 * Provides the remote service utility for Country. This utility wraps
 * <code>com.liferay.portal.service.impl.CountryServiceImpl</code> and is an
 * access point for service operations in application layer code running on a
 * remote server. Methods of this service are expected to have security checks
 * based on the propagated JAAS credentials because this service can be
 * accessed remotely.
 *
 * @author Brian Wing Shun Chan
 * @see CountryService
 * @generated
 */
public class CountryServiceUtil {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to <code>com.liferay.portal.service.impl.CountryServiceImpl</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws PortalException {

		return getService().addCountry(
			a2, a3, active, billingAllowed, idd, name, number, position,
			shippingAllowed, subjectToVAT, zipRequired, serviceContext);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country addCountry(
			String name, String a2, String a3, String number, String idd,
			boolean active)
		throws PortalException {

		return getService().addCountry(name, a2, a3, number, idd, active);
	}

	public static void deleteCountry(long countryId) throws PortalException {
		getService().deleteCountry(countryId);
	}

	public static Country fetchCountry(long countryId) {
		return getService().fetchCountry(countryId);
	}

	public static Country fetchCountryByA2(long companyId, String a2) {
		return getService().fetchCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country fetchCountryByA2(String a2) {
		return getService().fetchCountryByA2(a2);
	}

	public static Country fetchCountryByA3(long companyId, String a3) {
		return getService().fetchCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country fetchCountryByA3(String a3) {
		return getService().fetchCountryByA3(a3);
	}

	public static List<Country> getCompanyCountries(long companyId) {
		return getService().getCompanyCountries(companyId);
	}

	public static List<Country> getCompanyCountries(
		long companyId, boolean active) {

		return getService().getCompanyCountries(companyId, active);
	}

	public static List<Country> getCompanyCountries(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getService().getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	public static List<Country> getCompanyCountries(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return getService().getCompanyCountries(
			companyId, start, end, orderByComparator);
	}

	public static int getCompanyCountriesCount(long companyId) {
		return getService().getCompanyCountriesCount(companyId);
	}

	public static int getCompanyCountriesCount(long companyId, boolean active) {
		return getService().getCompanyCountriesCount(companyId, active);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static List<Country> getCountries() {
		return getService().getCountries();
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static List<Country> getCountries(boolean active) {
		return getService().getCountries(active);
	}

	public static Country getCountry(long countryId) throws PortalException {
		return getService().getCountry(countryId);
	}

	public static Country getCountryByA2(long companyId, String a2)
		throws PortalException {

		return getService().getCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country getCountryByA2(String a2) throws PortalException {
		return getService().getCountryByA2(a2);
	}

	public static Country getCountryByA3(long companyId, String a3)
		throws PortalException {

		return getService().getCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country getCountryByA3(String a3) throws PortalException {
		return getService().getCountryByA3(a3);
	}

	public static Country getCountryByName(long companyId, String name)
		throws PortalException {

		return getService().getCountryByName(companyId, name);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	public static Country getCountryByName(String name) throws PortalException {
		return getService().getCountryByName(name);
	}

	public static Country getCountryByNumber(long companyId, String number)
		throws PortalException {

		return getService().getCountryByNumber(companyId, number);
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	public static String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	public static com.liferay.portal.kernel.search.BaseModelSearchResult
		<Country> searchCountries(
				long companyId, Boolean active, String keywords, int start,
				int end, OrderByComparator<Country> orderByComparator)
			throws PortalException {

		return getService().searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	public static Country updateActive(long countryId, boolean active)
		throws PortalException {

		return getService().updateActive(countryId, active);
	}

	public static Country updateCountry(
			long countryId, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT)
		throws PortalException {

		return getService().updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	public static Country updateGroupFilterEnabled(
			long countryId, boolean groupFilterEnabled)
		throws PortalException {

		return getService().updateGroupFilterEnabled(
			countryId, groupFilterEnabled);
	}

	public static CountryService getService() {
		return _service;
	}

	public static void setService(CountryService service) {
		_service = service;
	}

	private static volatile CountryService _service;

}