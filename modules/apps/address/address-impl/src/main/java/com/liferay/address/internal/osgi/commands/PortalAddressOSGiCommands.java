/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.osgi.commands;

import com.liferay.address.internal.util.CompanyCountriesUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.osgi.util.osgi.commands.OSGiCommands;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Release;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;

import java.sql.Connection;

import java.util.HashSet;
import java.util.List;
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
	service = OSGiCommands.class
)
public class PortalAddressOSGiCommands implements OSGiCommands {

	public void initializeCompanyCountries(long companyId) throws Exception {
		_countryLocalService.deleteCompanyCountries(companyId);

		populateCompanyCountries(companyId);
	}

	public void populateCompanyCountries(long companyId) throws Exception {
		try (Connection connection = DataAccess.getConnection()) {
			CompanyCountriesUtil.populateCompanyCountries(
				_companyLocalService.getCompany(companyId),
				_counterLocalService, _countryLocalService, connection);
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

		JSONArray countriesJSONArray = CompanyCountriesUtil.getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		for (int i = 0; i < countriesJSONArray.length(); i++) {
			JSONObject countryJSONObject = countriesJSONArray.getJSONObject(i);

			try (Connection connection = DataAccess.getConnection()) {
				String name = countryJSONObject.getString("name");

				if (!countryNames.contains(name)) {
					CompanyCountriesUtil.addCountry(
						company, _counterLocalService, countryJSONObject,
						_countryLocalService, connection);

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

				CompanyCountriesUtil.processCountryRegions(
					country, connection, _counterLocalService);
			}
			catch (Exception exception) {
				_log.error(exception);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		PortalAddressOSGiCommands.class);

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private CounterLocalService _counterLocalService;

	@Reference
	private CountryLocalService _countryLocalService;

	@Reference(
		target = "(&(release.bundle.symbolic.name=portal)(release.schema.version>=9.2.0))"
	)
	private Release _release;

}