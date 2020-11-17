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

package com.liferay.portal.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.security.access.control.AccessControlled;
import com.liferay.portal.kernel.security.auth.PrincipalException;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.service.base.CountryServiceBaseImpl;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public class CountryServiceImpl extends CountryServiceBaseImpl {

	@Override
	public Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			Map<String, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		if (!getPermissionChecker().isOmniadmin()) {
			throw new PrincipalException.MustBeOmniadmin(
				getPermissionChecker());
		}

		return countryLocalService.addCountry(
			a2, a3, active, billingAllowed, idd, name, number, position,
			shippingAllowed, subjectToVAT, zipRequired, titleMap,
			serviceContext);
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
			Collections.singletonMap(PropsValues.COMPANY_DEFAULT_LOCALE, name),
			serviceContext);
	}

	@Override
	public Country fetchCountry(long countryId) {
		return countryPersistence.fetchByPrimaryKey(countryId);
	}

	@Override
	public Country fetchCountryByA2(String a2) {
		return countryPersistence.fetchByC_A2(
			PortalInstances.getDefaultCompanyId(), a2);
	}

	@Override
	public Country fetchCountryByA3(String a3) {
		return countryPersistence.fetchByC_A3(
			PortalInstances.getDefaultCompanyId(), a3);
	}

	@Override
	public List<Country> getCountries() {
		return countryPersistence.findAll();
	}

	@AccessControlled(guestAccessEnabled = true)
	@Override
	public List<Country> getCountries(boolean active) {
		return countryPersistence.findByActive(active);
	}

	@Override
	public Country getCountry(long countryId) throws PortalException {
		return countryPersistence.findByPrimaryKey(countryId);
	}

	@Override
	public Country getCountryByA2(String a2) throws PortalException {
		return countryPersistence.findByC_A2(
			PortalInstances.getDefaultCompanyId(), a2);
	}

	@Override
	public Country getCountryByA3(String a3) throws PortalException {
		return countryPersistence.findByC_A3(
			PortalInstances.getDefaultCompanyId(), a3);
	}

	@Override
	public Country getCountryByName(String name) throws PortalException {
		return countryPersistence.findByC_N(
			PortalInstances.getDefaultCompanyId(), name);
	}

}