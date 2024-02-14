/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_1;

import com.liferay.address.internal.util.CompanyCountriesUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringUtil;

import java.sql.PreparedStatement;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * @author Loc Pham
 * @author Stefano Motta
 */
public class CountryRegionUpgradeProcess extends UpgradeProcess {

	public CountryRegionUpgradeProcess(
		CompanyLocalService companyLocalService,
		CounterLocalService counterLocalService,
		CountryLocalService countryLocalService,
		RegionLocalService regionLocalService) {

		_companyLocalService = companyLocalService;
		_counterLocalService = counterLocalService;
		_countryLocalService = countryLocalService;
		_regionLocalService = regionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		CompanyCountriesUtil.updateRegionCounter(
			getConnection(), _counterLocalService);
		CompanyCountriesUtil.updateRegionLocalizationCounter(
			getConnection(), _counterLocalService);

		_updateRegion("FR", "75C", "75", "Paris");
		_updateRegion("MX", "CMX", "DIF", "Ciudad de México");
		_updateRegion("NL", "BO", "BQ1", "Bonaire");
		_updateRegion("NL", "SA", "BQ2", "Saba");
		_updateRegion("NL", "SE", "BQ3", "Sint Eustatuis");

		JSONArray countriesJSONArray = CompanyCountriesUtil.getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		_companyLocalService.forEachCompany(
			company -> {
				try {
					for (int i = 0; i < countriesJSONArray.length(); i++) {
						JSONObject countryJSONObject =
							countriesJSONArray.getJSONObject(i);

						_updateCountry(company, countryJSONObject);

						_deleteRegion(company, "FR", "971.0", "GP");
						_deleteRegion(company, "FR", "972.0", "MQ");
						_deleteRegion(company, "FR", "973.0", "GF");
						_deleteRegion(company, "FR", "974.0", "RE");
						_deleteRegion(company, "FR", "976.0", "YT");
						_deleteRegion(company, "GB", "BCP", "POL");
					}
				}
				catch (Exception exception) {
					_log.error(
						"Unable to upgrade company " + company.getCompanyId(),
						exception);
				}
			});
	}

	private void _deleteRegion(
			Company company, String countryA2, String newRegionCode,
			String oldRegionCode)
		throws Exception {

		Country country = _countryLocalService.fetchCountryByA2(
			company.getCompanyId(), countryA2);

		if (country == null) {
			return;
		}

		Region newRegion = _regionLocalService.fetchRegion(
			country.getCountryId(), newRegionCode);
		Region oldRegion = _regionLocalService.fetchRegion(
			country.getCountryId(), oldRegionCode);

		if ((newRegion != null) && (oldRegion != null)) {
			_updateData(
				newRegion.getRegionId(), oldRegion.getRegionId(), "Address");
			_updateData(countryA2, newRegionCode, oldRegionCode, "CIWarehouse");
			_updateData(
				newRegion.getRegionId(), oldRegion.getRegionId(),
				"CommerceTaxFixedRateAddressRel");
			_updateData(
				newRegion.getRegionId(), oldRegion.getRegionId(),
				"CShippingFixedOptionRel");
			_updateData(
				newRegion.getRegionId(), oldRegion.getRegionId(),
				"Organization_");
		}

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"delete from Region where regionCode = ? and countryId = ?")) {

			preparedStatement.setString(1, oldRegionCode);
			preparedStatement.setLong(2, country.getCountryId());
			preparedStatement.executeUpdate();
		}
	}

	private void _updateCountry(Company company, JSONObject countryJSONObject)
		throws Exception {

		Country country = _countryLocalService.fetchCountryByA2(
			company.getCompanyId(), countryJSONObject.getString("a2"));

		if (country != null) {
			country = _countryLocalService.updateCountry(
				country.getCountryId(), countryJSONObject.getString("a2"),
				countryJSONObject.getString("a3"), country.isActive(),
				country.isBillingAllowed(), countryJSONObject.getString("idd"),
				countryJSONObject.getString("name"),
				countryJSONObject.getString("number"), country.getPosition(),
				country.isShippingAllowed(), country.isSubjectToVAT());

			_updateRegions(country);
		}
	}

	private void _updateData(
			long newRegionId, long oldRegionId, String tableName)
		throws Exception {

		if (hasTable(tableName)) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", tableName,
							" set regionId = ? where regionId = ?"))) {

				preparedStatement.setLong(1, newRegionId);
				preparedStatement.setLong(2, oldRegionId);

				preparedStatement.executeUpdate();
			}
		}
	}

	private void _updateData(
			String countryA2, String newRegionCode, String oldRegionCode,
			String tableName)
		throws Exception {

		if (hasTable(tableName)) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", tableName,
							" set commerceRegionCode = ? where ",
							"commerceRegionCode = ? and ",
							"countryTwoLettersISOCode = ?"))) {

				preparedStatement.setString(1, newRegionCode);
				preparedStatement.setString(2, oldRegionCode);
				preparedStatement.setString(3, countryA2);

				preparedStatement.executeUpdate();
			}
		}
	}

	private void _updateRegion(Country country, JSONObject regionJSONObject)
		throws Exception {

		String newRegionCode = regionJSONObject.getString("regionCode");

		Region region = _regionLocalService.fetchRegion(
			country.getCountryId(), newRegionCode);

		if (region == null) {
			region = _regionLocalService.fetchRegion(
				country.getCountryId(),
				StringUtil.removeLast(newRegionCode, ".0"));
		}

		if (region == null) {
			ServiceContext serviceContext = new ServiceContext();

			serviceContext.setCompanyId(country.getCompanyId());
			serviceContext.setUserId(country.getUserId());

			region = _regionLocalService.addRegion(
				country.getCountryId(), true,
				regionJSONObject.getString("name"), 0,
				regionJSONObject.getString("regionCode"), serviceContext);
		}
		else {
			String oldRegionCode = region.getRegionCode();
			String oldRegionName = region.getName();

			if (Objects.equals(oldRegionCode, newRegionCode) &&
				Objects.equals(
					oldRegionName, regionJSONObject.getString("name"))) {

				return;
			}

			region.setName(regionJSONObject.getString("name"));
			region.setRegionCode(newRegionCode);

			region = _regionLocalService.updateRegion(region);

			_updateData(
				country.getA2(), region.getRegionCode(), oldRegionCode,
				"CIWarehouse");
		}

		JSONObject localizationsJSONObject = regionJSONObject.getJSONObject(
			"localizations");

		if (localizationsJSONObject == null) {
			Map<String, String> titleMap = new HashMap<>();

			for (Locale locale :
					LanguageUtil.getCompanyAvailableLocales(
						country.getCompanyId())) {

				titleMap.put(
					LanguageUtil.getLanguageId(locale), region.getName());
			}

			_regionLocalService.updateRegionLocalizations(region, titleMap);
		}
		else {
			for (String key : localizationsJSONObject.keySet()) {
				_regionLocalService.updateRegionLocalization(
					region, key, localizationsJSONObject.getString(key));
			}
		}
	}

	private void _updateRegion(
			String countryA2, String newRegionCode, String oldRegionCode,
			String oldRegionName)
		throws Exception {

		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"update Region set regionCode = ? where regionCode = ? and " +
					"name = ?")) {

			preparedStatement.setString(1, newRegionCode);
			preparedStatement.setString(2, oldRegionCode);
			preparedStatement.setString(3, oldRegionName);
			preparedStatement.executeUpdate();

			_updateData(countryA2, newRegionCode, oldRegionCode, "CIWarehouse");
		}
	}

	private void _updateRegions(Country country) throws Exception {
		String path =
			"com/liferay/address/dependencies/regions/" + country.getA2() +
				".json";

		JSONArray regionsJSONArray = CompanyCountriesUtil.getJSONArray(path);

		if (regionsJSONArray == null) {
			return;
		}

		for (int i = 0; i < regionsJSONArray.length(); i++) {
			try {
				_updateRegion(country, regionsJSONArray.getJSONObject(i));
			}
			catch (PortalException portalException) {
				_log.error(portalException);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountryRegionUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final CounterLocalService _counterLocalService;
	private final CountryLocalService _countryLocalService;
	private final RegionLocalService _regionLocalService;

}