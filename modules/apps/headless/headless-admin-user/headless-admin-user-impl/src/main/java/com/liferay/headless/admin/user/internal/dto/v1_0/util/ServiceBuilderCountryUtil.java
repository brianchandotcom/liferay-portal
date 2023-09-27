/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.user.internal.dto.v1_0.util;

import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.service.CountryServiceUtil;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
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

			BaseModelSearchResult<Country> baseModelSearchResult =
				CountryServiceUtil.searchCountries(
					companyId, true,
					StringUtil.quote(addressCountry, CharPool.QUOTE),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					OrderByComparatorFactoryUtil.create(
						CountryImpl.TABLE_NAME, "name", true));

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