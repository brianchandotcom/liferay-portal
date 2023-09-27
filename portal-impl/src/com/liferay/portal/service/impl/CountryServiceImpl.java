/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.jsonwebservice.JSONWebService;
import com.liferay.portal.kernel.jsonwebservice.JSONWebServiceMode;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.permission.PortalPermissionUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.base.CountryServiceBaseImpl;
import com.liferay.portal.util.PortalInstances;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class CountryServiceImpl extends CountryServiceBaseImpl {

	@Override
	public Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			ServiceContext serviceContext)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return countryLocalService.addCountry(
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
		throws PortalException {

		ServiceContext serviceContext = new ServiceContext();

		PermissionChecker permissionChecker = getPermissionChecker();

		serviceContext.setCompanyId(permissionChecker.getCompanyId());
		serviceContext.setUserId(permissionChecker.getUserId());

		return addCountry(
			a2, a3, active, true, idd, name, number, 0, true, false, true,
			serviceContext);
	}

	@Override
	public void deleteCountry(long countryId) throws PortalException {
		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		countryLocalService.deleteCountry(countryId);
	}

	@Override
	public Country fetchCountry(long countryId) {
		return countryLocalService.fetchCountry(countryId);
	}

	@Override
	public Country fetchCountryByA2(long companyId, String a2) {
		return countryLocalService.fetchCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country fetchCountryByA2(String a2) {
		return fetchCountryByA2(PortalInstances.getDefaultCompanyId(), a2);
	}

	@Override
	public Country fetchCountryByA3(long companyId, String a3) {
		return countryLocalService.fetchCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country fetchCountryByA3(String a3) {
		return fetchCountryByA3(PortalInstances.getDefaultCompanyId(), a3);
	}

	@Override
	public List<Country> getCompanyCountries(long companyId) {
		return countryLocalService.getCompanyCountries(companyId);
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<Country> getCompanyCountries(long companyId, boolean active) {
		return countryLocalService.getCompanyCountries(companyId, active);
	}

	@Override
	public List<Country> getCompanyCountries(
		long companyId, boolean active, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return countryLocalService.getCompanyCountries(
			companyId, active, start, end, orderByComparator);
	}

	@Override
	public List<Country> getCompanyCountries(
		long companyId, int start, int end,
		OrderByComparator<Country> orderByComparator) {

		return countryLocalService.getCompanyCountries(
			companyId, start, end, orderByComparator);
	}

	@Override
	public int getCompanyCountriesCount(long companyId) {
		return countryLocalService.getCompanyCountriesCount(companyId);
	}

	@Override
	public int getCompanyCountriesCount(long companyId, boolean active) {
		return countryLocalService.getCompanyCountriesCount(companyId, active);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public List<Country> getCountries() {
		return getCompanyCountries(PortalInstances.getDefaultCompanyId());
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@AccessControlled(guestAccessEnabled = true)
	@Deprecated
	@Override
	public List<Country> getCountries(boolean active) {
		return getCompanyCountries(
			PortalInstances.getDefaultCompanyId(), active);
	}

	@Override
	public Country getCountry(long countryId) throws PortalException {
		return countryLocalService.getCountry(countryId);
	}

	@Override
	public Country getCountryByA2(long companyId, String a2)
		throws PortalException {

		return countryLocalService.getCountryByA2(companyId, a2);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByA2(String a2) throws PortalException {
		return getCountryByA2(PortalInstances.getDefaultCompanyId(), a2);
	}

	@Override
	public Country getCountryByA3(long companyId, String a3)
		throws PortalException {

		return countryLocalService.getCountryByA3(companyId, a3);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByA3(String a3) throws PortalException {
		return getCountryByA3(PortalInstances.getDefaultCompanyId(), a3);
	}

	@Override
	public Country getCountryByName(long companyId, String name)
		throws PortalException {

		return countryLocalService.getCountryByName(companyId, name);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x)
	 */
	@Deprecated
	@Override
	public Country getCountryByName(String name) throws PortalException {
		return getCountryByName(PortalInstances.getDefaultCompanyId(), name);
	}

	@Override
	public Country getCountryByNumber(long companyId, String number)
		throws PortalException {

		return countryLocalService.getCountryByNumber(companyId, number);
	}

	@JSONWebService(mode = JSONWebServiceMode.IGNORE)
	@Override
	public BaseModelSearchResult<Country> searchCountries(
			long companyId, Boolean active, String keywords, int start, int end,
			OrderByComparator<Country> orderByComparator)
		throws PortalException {

		return countryLocalService.searchCountries(
			companyId, active, keywords, start, end, orderByComparator);
	}

	@Override
	public Country updateActive(long countryId, boolean active)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return countryLocalService.updateActive(countryId, active);
	}

	@Override
	public Country updateCountry(
			long countryId, String a2, String a3, boolean active,
			boolean billingAllowed, String idd, String name, String number,
			double position, boolean shippingAllowed, boolean subjectToVAT)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return countryLocalService.updateCountry(
			countryId, a2, a3, active, billingAllowed, idd, name, number,
			position, shippingAllowed, subjectToVAT);
	}

	@Override
	public Country updateGroupFilterEnabled(
			long countryId, boolean groupFilterEnabled)
		throws PortalException {

		PortalPermissionUtil.check(
			getPermissionChecker(), ActionKeys.MANAGE_COUNTRIES);

		return countryLocalService.updateGroupFilterEnabled(
			countryId, groupFilterEnabled);
	}

}