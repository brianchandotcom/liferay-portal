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

import com.liferay.portal.kernel.exception.CountryA2Exception;
import com.liferay.portal.kernel.exception.CountryA3Exception;
import com.liferay.portal.kernel.exception.CountryIddException;
import com.liferay.portal.kernel.exception.CountryNameException;
import com.liferay.portal.kernel.exception.CountryNumberException;
import com.liferay.portal.kernel.exception.DuplicateCountryException;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.CountryLocalServiceBaseImpl;

import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @see CountryLocalServiceBaseImpl
 */
public class CountryLocalServiceImpl extends CountryLocalServiceBaseImpl {

	@Override
	public Country addCountry(
			String a2, String a3, boolean active, boolean billingAllowed,
			String idd, String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, boolean zipRequired,
			Map<String, String> titleMap, ServiceContext serviceContext)
		throws PortalException {

		if (countryPersistence.fetchByC_A2(serviceContext.getCompanyId(), a2) !=
				null) {

			throw new DuplicateCountryException();
		}

		validate(a2, a3, idd, name, number);

		User user = userLocalService.getUser(serviceContext.getUserId());

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getDefault());

		long countryId = counterLocalService.increment();

		Country country = countryPersistence.create(countryId);

		country.setCompanyId(user.getCompanyId());
		country.setUserId(user.getUserId());
		country.setUserName(user.getFullName());
		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setBillingAllowed(billingAllowed);
		country.setDefaultLanguageId(defaultLanguageId);
		country.setGroupFilterEnabled(false);
		country.setIdd(idd);
		country.setName(name);
		country.setNumber(number);
		country.setPosition(position);
		country.setShippingAllowed(shippingAllowed);
		country.setSubjectToVAT(subjectToVAT);
		country.setZipRequired(zipRequired);

		return countryPersistence.update(country);
	}

	@Override
	public List<Country> getCountriesByCompanyId(long companyId) {
		return countryPersistence.findByCompanyId(companyId);
	}

	@Override
	public int getCountriesCountByCompanyId(long companyId) {
		return countryPersistence.countByCompanyId(companyId);
	}

	protected void validate(
			String a2, String a3, String idd, String name, String number)
		throws PortalException {

		if (Validator.isNull(a2) || (a2.length() != 2)) {
			throw new CountryA2Exception();
		}

		if (Validator.isNull(a3) || (a3.length() != 3)) {
			throw new CountryA3Exception();
		}

		if (Validator.isNull(idd)) {
			throw new CountryIddException();
		}

		if (Validator.isNull(name)) {
			throw new CountryNameException();
		}

		if (Validator.isNull(number)) {
			throw new CountryNumberException();
		}
	}

}