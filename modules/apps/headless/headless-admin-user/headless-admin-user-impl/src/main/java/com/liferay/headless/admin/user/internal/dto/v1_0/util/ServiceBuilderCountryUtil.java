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

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.model.impl.CountryImpl;

import java.util.List;

/**
 * @author Drew Brokke
 */
public class ServiceBuilderCountryUtil {

	public static Country toServiceBuilderCountry(
		long companyId, String addressCountry) {

		try {
			Country country = CountryServiceUtil.fetchCountryByA2(
				companyId, addressCountry);

			if (country != null) {
				return country;
			}

			country = CountryServiceUtil.fetchCountryByA3(
				companyId, addressCountry);

			if (country != null) {
				return country;
			}

			String addressCountryKeyWord =
				StringPool.QUOTE + addressCountry + StringPool.QUOTE;

			OrderByComparator<Country> orderByComparator =
				OrderByComparatorFactoryUtil.create(
					CountryImpl.TABLE_NAME, "name", true);

			BaseModelSearchResult<Country> baseModelSearchResult =
				CountryServiceUtil.searchCountries(
					companyId, true, addressCountryKeyWord, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, orderByComparator);

			List<Country> countries = baseModelSearchResult.getBaseModels();

			if (countries != null) {
				return countries.get(0);
			}

			return CountryServiceUtil.getCountryByName(
				companyId, addressCountry);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}
		}

		return null;
	}

	public static long toServiceBuilderCountryId(
		long companyId, String addressCountry) {

		if (addressCountry == null) {
			return 0;
		}

		Country country = toServiceBuilderCountry(companyId, addressCountry);

		if (country == null) {
			return 0;
		}

		return country.getCountryId();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ServiceBuilderCountryUtil.class);

}