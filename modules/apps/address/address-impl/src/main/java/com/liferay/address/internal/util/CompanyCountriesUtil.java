/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author Joao Victor Alves
 */
public class CompanyCountriesUtil {

	public static void addCountry(
		Company company, JSONObject countryJSONObject,
		CountryLocalService countryLocalService,
		RegionLocalService regionLocalService) {

		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(company.getCompanyId());

			User guestUser = company.getGuestUser();

			serviceContext.setUserId(guestUser.getUserId());

			Country country = countryLocalService.addCountry(
				countryJSONObject.getString("a2"),
				countryJSONObject.getString("a3"), true, true,
				countryJSONObject.getString("idd"),
				countryJSONObject.getString("name"),
				countryJSONObject.getString("number"), 0, true, false,
				countryJSONObject.getBoolean("zipRequired"), serviceContext);

			Map<String, String> titleMap = new HashMap<>();

			for (Locale locale :
					LanguageUtil.getCompanyAvailableLocales(
						company.getCompanyId())) {

				titleMap.put(
					LanguageUtil.getLanguageId(locale),
					country.getName(locale));
			}

			countryLocalService.updateCountryLocalizations(country, titleMap);

			processCountryRegions(country, regionLocalService);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	public static JSONArray getJSONArray(String path) throws Exception {
		return JSONFactoryUtil.createJSONArray(
			StringUtil.read(
				CompanyCountriesUtil.class.getClassLoader(), path, false));
	}

	public static void populateCompanyCountries(
			Company company, CountryLocalService countryLocalService,
			RegionLocalService regionLocalService)
		throws Exception {

		int count = countryLocalService.getCompanyCountriesCount(
			company.getCompanyId());

		if (count > 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping country initialization. Countries are ",
						"already initialized for company ",
						company.getCompanyId(), "."));
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Initializing countries for company " + company.getCompanyId());
		}

		JSONArray countriesJSONArray = getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		for (int i = 0; i < countriesJSONArray.length(); i++) {
			JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);

			try {
				addCountry(
					company, countryJSONObject, countryLocalService,
					regionLocalService);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	public static void processCountryRegions(
		Country country, RegionLocalService regionLocalService) {

		String a2 = country.getA2();

		try {
			String path =
				"com/liferay/address/dependencies/regions/" + a2 + ".json";

			ClassLoader classLoader =
				CompanyCountriesUtil.class.getClassLoader();

			if (classLoader.getResource(path) == null) {
				return;
			}

			JSONArray regionsJSONArray = getJSONArray(path);

			if (_log.isDebugEnabled()) {
				_log.debug("Regions found for country " + a2);
			}

			for (int i = 0; i < regionsJSONArray.length(); i++) {
				try {
					JSONObject regionJSONObject =
						regionsJSONArray.getJSONObject(i);

					ServiceContext serviceContext = new ServiceContext();

					serviceContext.setCompanyId(country.getCompanyId());
					serviceContext.setUserId(country.getUserId());

					Region region = regionLocalService.addRegion(
						country.getCountryId(), true,
						regionJSONObject.getString("name"), 0,
						regionJSONObject.getString("regionCode"),
						serviceContext);

					JSONObject localizationsJSONObject =
						regionJSONObject.getJSONObject("localizations");

					if (localizationsJSONObject == null) {
						Map<String, String> titleMap = new HashMap<>();

						for (Locale locale :
								LanguageUtil.getCompanyAvailableLocales(
									country.getCompanyId())) {

							titleMap.put(
								LanguageUtil.getLanguageId(locale),
								region.getName());
						}

						regionLocalService.updateRegionLocalizations(
							region, titleMap);
					}
					else {
						for (String key : localizationsJSONObject.keySet()) {
							regionLocalService.updateRegionLocalization(
								region, key,
								localizationsJSONObject.getString(key));
						}
					}
				}
				catch (PortalException portalException) {
					_log.error(portalException);
				}
			}
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug("No regions found for country " + a2, exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyCountriesUtil.class);

}