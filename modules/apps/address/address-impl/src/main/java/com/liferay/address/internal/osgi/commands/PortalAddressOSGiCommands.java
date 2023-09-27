/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.osgi.commands;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	property = {
		"osgi.command.function=initializeCompanyCountries",
		"osgi.command.function=populateCompanyCountries",
		"osgi.command.function=repopulateCompanyCountries",
		"osgi.command.scope=address"
	},
	service = PortalAddressOSGiCommands.class
)
public class PortalAddressOSGiCommands {

	public void initializeCompanyCountries(long companyId) throws Exception {
		_countryLocalService.deleteCompanyCountries(companyId);

		populateCompanyCountries(companyId);
	}

	public void populateCompanyCountries(long companyId) throws Exception {
		Company company = _companyLocalService.getCompany(companyId);

		int count = _countryLocalService.getCompanyCountriesCount(companyId);

		if (count > 0) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping country initialization. Countries are ",
						"already initialized for company ", companyId, "."));
			}

			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Initializing countries for company " + companyId);
		}

		JSONArray countriesJSONArray = _getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		for (int i = 0; i < countriesJSONArray.length(); i++) {
			JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);

			try {
				_addCountry(company, countryJSONObject);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	public void repopulateCompanyCountries(long companyId) throws Exception {
		if (_log.isDebugEnabled()) {
			_log.debug("Reinitializing countries for company " + companyId);
		}

		Company company = _companyLocalService.getCompany(companyId);

		Set<String> countryNames = new HashSet<>();

		List<Country> countries = _countryLocalService.getCompanyCountries(
			companyId);

		for (Country country : countries) {
			countryNames.add(country.getName());
		}

		JSONArray countriesJSONArray = _getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		for (int i = 0; i < countriesJSONArray.length(); i++) {
			JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);

			try {
				String name = countryJSONObject.getString("name");

				if (!countryNames.contains(name)) {
					_addCountry(company, countryJSONObject);

					continue;
				}

				Country country = _countryLocalService.getCountryByName(
					companyId, name);

				country = _countryLocalService.updateCountry(
					country.getCountryId(), countryJSONObject.getString("a2"),
					countryJSONObject.getString("a3"), country.isActive(),
					country.isBillingAllowed(),
					countryJSONObject.getString("idd"), name,
					countryJSONObject.getString("number"),
					country.getPosition(), country.isShippingAllowed(),
					country.isSubjectToVAT());

				_processCountryRegions(country);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private void _addCountry(Company company, JSONObject countryJSONObject) {
		try {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(company.getCompanyId());

			User guestUser = company.getGuestUser();

			serviceContext.setUserId(guestUser.getUserId());

			Country country = _countryLocalService.addCountry(
				countryJSONObject.getString("a2"),
				countryJSONObject.getString("a3"), true, true,
				countryJSONObject.getString("idd"),
				countryJSONObject.getString("name"),
				countryJSONObject.getString("number"), 0, true, false,
				countryJSONObject.getBoolean("zipRequired"), serviceContext);

			Map<String, String> titleMap = new HashMap<>();

			for (Locale locale :
					_language.getCompanyAvailableLocales(
						company.getCompanyId())) {

				titleMap.put(
					_language.getLanguageId(locale), country.getName(locale));
			}

			_countryLocalService.updateCountryLocalizations(country, titleMap);

			_processCountryRegions(country);
		}
		catch (PortalException portalException) {
			_log.error(portalException);
		}
	}

	private ClassLoader _getClassLoader() {
		Class<?> clazz = getClass();

		return clazz.getClassLoader();
	}

	private JSONArray _getJSONArray(String path) throws Exception {
		return _jsonFactory.createJSONArray(
			StringUtil.read(_getClassLoader(), path, false));
	}

	private void _processCountryRegions(Country country) {
		String a2 = country.getA2();

		try {
			String path =
				"com/liferay/address/dependencies/regions/" + a2 + ".json";

			ClassLoader classLoader = _getClassLoader();

			if (classLoader.getResource(path) == null) {
				return;
			}

			JSONArray regionsJSONArray = _getJSONArray(path);

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

					Region region = _regionLocalService.addRegion(
						country.getCountryId(), true,
						regionJSONObject.getString("name"), 0,
						regionJSONObject.getString("regionCode"),
						serviceContext);

					JSONObject localizationsJSONObject =
						regionJSONObject.getJSONObject("localizations");

					if (localizationsJSONObject == null) {
						Map<String, String> titleMap = new HashMap<>();

						for (Locale locale :
								_language.getCompanyAvailableLocales(
									country.getCompanyId())) {

							titleMap.put(
								_language.getLanguageId(locale),
								region.getName());
						}

						_regionLocalService.updateRegionLocalizations(
							region, titleMap);
					}
					else {
						for (String key : localizationsJSONObject.keySet()) {
							_regionLocalService.updateRegionLocalization(
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
		PortalAddressOSGiCommands.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private Language _language;

	@Reference
	private RegionLocalService _regionLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=portal)(release.schema.version>=9.2.0))"
	)
	private Release _release;

}