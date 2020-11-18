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
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.base.CountryLocalServiceBaseImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @see CountryLocalServiceBaseImpl
 */
public class CountryLocalServiceImpl extends CountryLocalServiceBaseImpl {

	@Override
	public Country addCountry(
			String name, String a2, String a3, String number, String idd,
			boolean active)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new CountryNameException();
		}

		if (Validator.isNull(a2)) {
			throw new CountryA2Exception();
		}

		if (Validator.isNull(a3)) {
			throw new CountryA3Exception();
		}

		if (Validator.isNull(number)) {
			throw new CountryNumberException();
		}

		if (Validator.isNull(idd)) {
			throw new CountryIddException();
		}

		long countryId = counterLocalService.increment();

		Country country = countryPersistence.create(countryId);

		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setIdd(idd);
		country.setName(name);
		country.setNumber(number);

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

}