/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_2;

import com.liferay.address.internal.util.CompanyCountriesUtil;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.CountryLocalService;
import com.liferay.portal.kernel.service.RegionLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.sql.PreparedStatement;

import java.util.Map;

/**
 * @author Stefano Motta
 */
public class CountryUpgradeProcess extends UpgradeProcess {

	public CountryUpgradeProcess(
		CompanyLocalService companyLocalService,
		CounterLocalService counterLocalService,
		CountryLocalService countryLocalService, JSONFactory jsonFactory,
		RegionLocalService regionLocalService) {

		_companyLocalService = companyLocalService;
		_counterLocalService = counterLocalService;
		_countryLocalService = countryLocalService;
		_jsonFactory = jsonFactory;
		_regionLocalService = regionLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_companyLocalService.forEachCompany(
			company -> {
				try {
					_addCountry(
						company,
						HashMapBuilder.<String, Object>put(
							"a2", "HK"
						).put(
							"a3", "HKG"
						).put(
							"idd", 852
						).put(
							"name", "hong-kong"
						).put(
							"number", 344
						).put(
							"zipRequired", false
						).build());
					_addCountry(
						company,
						HashMapBuilder.<String, Object>put(
							"a2", "MO"
						).put(
							"a3", "MAC"
						).put(
							"idd", 853
						).put(
							"name", "macau"
						).put(
							"number", 446
						).put(
							"zipRequired", true
						).build());
				}
				catch (Exception exception) {
					_log.error(
						"Unable to populate company " + company.getCompanyId(),
						exception);
				}
			});
	}

	private void _addCountry(Company company, Map<String, Object> countryMap)
		throws Exception {

		Country country = _countryLocalService.fetchCountryByA2(
			company.getCompanyId(),
			countryMap.get(
				"a2"
			).toString());

		if (country != null) {
			return;
		}

		CompanyCountriesUtil.addCountry(
			company, _counterLocalService,
			_jsonFactory.createJSONObject(countryMap), _countryLocalService,
			connection);

		Country chinaCountry = _countryLocalService.fetchCountryByA2(
			company.getCompanyId(), "CN");

		if (chinaCountry == null) {
			return;
		}

		Region region = _regionLocalService.fetchRegion(
			chinaCountry.getCountryId(),
			countryMap.get(
				"a2"
			).toString());

		if (region == null) {
			return;
		}

		country = _countryLocalService.fetchCountryByA2(
			company.getCompanyId(),
			countryMap.get(
				"a2"
			).toString());

		_updateData(
			country.getCountryId(), chinaCountry.getCountryId(),
			region.getRegionId(), "Address");
		_updateData(
			country.getA2(), chinaCountry.getA2(), region.getRegionCode(),
			"CIWarehouse");
		_updateData(
			country.getCountryId(), chinaCountry.getCountryId(),
			region.getRegionId(), "CommerceTaxFixedRateAddressRel");
		_updateData(
			country.getCountryId(), chinaCountry.getCountryId(),
			region.getRegionId(), "CShippingFixedOptionRel");
		_updateData(
			country.getCountryId(), chinaCountry.getCountryId(),
			region.getRegionId(), "Organization_");

		_regionLocalService.deleteRegion(region.getRegionId());
	}

	private void _updateData(
			long countryId, long oldCountryId, long oldRegionId,
			String tableName)
		throws Exception {

		if (hasTable(tableName)) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", tableName,
							" set countryId = ?, regionId = 0 where countryId ",
							"= ? and regionId = ?"))) {

				preparedStatement.setLong(1, countryId);
				preparedStatement.setLong(2, oldCountryId);
				preparedStatement.setLong(3, oldRegionId);

				preparedStatement.executeUpdate();
			}
		}
	}

	private void _updateData(
			String countryA2, String oldCountryA2, String oldRegionCode,
			String tableName)
		throws Exception {

		if (hasTable(tableName)) {
			try (PreparedStatement preparedStatement =
					connection.prepareStatement(
						StringBundler.concat(
							"update ", tableName,
							" set commerceRegionCode = null, ",
							"countryTwoLettersISOCode = ? where ",
							"commerceRegionCode = ? and ",
							"countryTwoLettersISOCode = ?"))) {

				preparedStatement.setString(1, countryA2);
				preparedStatement.setString(2, oldRegionCode);
				preparedStatement.setString(3, oldCountryA2);

				preparedStatement.executeUpdate();
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountryUpgradeProcess.class);

	private final CompanyLocalService _companyLocalService;
	private final CounterLocalService _counterLocalService;
	private final CountryLocalService _countryLocalService;
	private final JSONFactory _jsonFactory;
	private final RegionLocalService _regionLocalService;

}