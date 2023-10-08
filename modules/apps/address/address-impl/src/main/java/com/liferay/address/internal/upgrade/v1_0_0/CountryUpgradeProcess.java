/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.address.internal.upgrade.v1_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.jdbc.AutoBatchPreparedStatementUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.CountryConstants;
import com.liferay.portal.kernel.model.Region;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.UpgradeProcessUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * @author Mariano Álvaro Sáiz
 */
public class CountryUpgradeProcess extends UpgradeProcess {

	public CountryUpgradeProcess(CompanyLocalService companyLocalService) {
		Class<?> clazz = getClass();

		_classLoader = clazz.getClassLoader();

		_companyLocalService = companyLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		_updateRegionCounter();

		_companyLocalService.forEachCompany(
			company -> {
				try {
					_populateCompanyCountries(company);
				}
				catch (Exception exception) {
					_log.error(
						"Unable to populate company " + company.getCompanyId(),
						exception);
				}
			});
	}

	private void _addCountry(Company company, JSONObject countryJSONObject)
		throws Exception {

		long countryId = increment();

		_countryPreparedStatement.setLong(1, 0L);
		_countryPreparedStatement.setString(2, PortalUUIDUtil.generate());
		_countryPreparedStatement.setString(
			3, UpgradeProcessUtil.getDefaultLanguageId(company.getCompanyId()));
		_countryPreparedStatement.setLong(4, countryId);
		_countryPreparedStatement.setLong(5, company.getCompanyId());
		_countryPreparedStatement.setLong(6, _companyGuestUser.getUserId());
		_countryPreparedStatement.setString(7, _companyGuestUser.getFullName());
		_countryPreparedStatement.setDate(8, _companyDate);
		_countryPreparedStatement.setDate(9, _companyDate);
		_countryPreparedStatement.setString(
			10, countryJSONObject.getString("a2"));
		_countryPreparedStatement.setString(
			11, countryJSONObject.getString("a3"));
		_countryPreparedStatement.setBoolean(12, true);
		_countryPreparedStatement.setBoolean(13, true);
		_countryPreparedStatement.setBoolean(14, false);
		_countryPreparedStatement.setString(
			15, countryJSONObject.getString("idd"));
		_countryPreparedStatement.setString(
			16, countryJSONObject.getString("name"));
		_countryPreparedStatement.setString(
			17, countryJSONObject.getString("number"));
		_countryPreparedStatement.setDouble(18, 0.0);
		_countryPreparedStatement.setBoolean(19, true);
		_countryPreparedStatement.setBoolean(20, false);
		_countryPreparedStatement.setBoolean(
			21, countryJSONObject.getBoolean("zipRequired"));
		_countryPreparedStatement.setDate(22, _companyDate);

		_countryPreparedStatement.addBatch();

		for (Locale locale : _companyAvailableLocales) {
			_countryLocalizationPreparedStatement.setLong(1, 0L);
			_countryLocalizationPreparedStatement.setLong(2, increment());
			_countryLocalizationPreparedStatement.setLong(
				3, company.getCompanyId());
			_countryLocalizationPreparedStatement.setLong(4, countryId);
			_countryLocalizationPreparedStatement.setString(
				5, _getLanguageId(locale));
			_countryLocalizationPreparedStatement.setString(
				6,
				_getLocalizedName(locale, countryJSONObject.getString("name")));

			_countryLocalizationPreparedStatement.addBatch();
		}

		_processCountryRegions(
			company, countryJSONObject.getString("a2"), countryId);
	}

	private void _addRegion(
			Company company, long countryId, JSONObject regionJSONObject)
		throws Exception {

		long regionId = increment(Region.class.getName());
		String regionName = regionJSONObject.getString("name");

		_regionPreparedStatement.setLong(1, 0L);
		_regionPreparedStatement.setString(2, PortalUUIDUtil.generate());
		_regionPreparedStatement.setString(
			3, UpgradeProcessUtil.getDefaultLanguageId(company.getCompanyId()));
		_regionPreparedStatement.setLong(4, regionId);
		_regionPreparedStatement.setLong(5, company.getCompanyId());
		_regionPreparedStatement.setLong(6, _companyGuestUser.getUserId());
		_regionPreparedStatement.setString(7, _companyGuestUser.getFullName());
		_regionPreparedStatement.setDate(8, _companyDate);
		_regionPreparedStatement.setDate(9, _companyDate);
		_regionPreparedStatement.setLong(10, countryId);
		_regionPreparedStatement.setBoolean(11, true);
		_regionPreparedStatement.setString(
			12, regionJSONObject.getString("name"));
		_regionPreparedStatement.setDouble(13, 0.0);
		_regionPreparedStatement.setString(
			14, regionJSONObject.getString("regionCode"));
		_regionPreparedStatement.setDate(15, _companyDate);

		_regionPreparedStatement.addBatch();

		JSONObject localizationsJSONObject = regionJSONObject.getJSONObject(
			"localizations");

		Map<String, String> titleMap = new HashMap<>();

		if (localizationsJSONObject == null) {
			for (Locale locale : _companyAvailableLocales) {
				titleMap.put(_getLanguageId(locale), regionName);
			}
		}
		else {
			for (String key : localizationsJSONObject.keySet()) {
				titleMap.put(key, localizationsJSONObject.getString(key));
			}
		}

		for (Map.Entry<String, String> entryMap : titleMap.entrySet()) {
			_regionLocalizationPreparedStatement.setLong(1, 0L);
			_regionLocalizationPreparedStatement.setLong(2, increment());
			_regionLocalizationPreparedStatement.setLong(
				3, company.getCompanyId());
			_regionLocalizationPreparedStatement.setLong(4, regionId);
			_regionLocalizationPreparedStatement.setString(
				5, entryMap.getKey());
			_regionLocalizationPreparedStatement.setString(
				6, entryMap.getValue());

			_regionLocalizationPreparedStatement.addBatch();
		}
	}

	private JSONArray _getJSONArray(String path) throws Exception {
		return JSONFactoryUtil.createJSONArray(
			StringUtil.read(_classLoader, path, false));
	}

	private String _getLanguageId(Locale locale) {
		return _localesLanguageIds.computeIfAbsent(
			locale, key -> LanguageUtil.getLanguageId(key));
	}

	private String _getLocalizedName(Locale locale, String name) {
		String localizedName = LanguageUtil.get(
			locale, CountryConstants.NAME_PREFIX + name);

		if (!localizedName.startsWith(CountryConstants.NAME_PREFIX)) {
			return localizedName;
		}

		return name;
	}

	private boolean _hasCountries(long companyId) throws Exception {
		try (PreparedStatement preparedStatement = connection.prepareStatement(
				"select count(*) from Country where companyId = ?")) {

			preparedStatement.setLong(1, companyId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				while (resultSet.next()) {
					int count = resultSet.getInt(1);

					if (count > 0) {
						return true;
					}
				}

				return false;
			}
		}
	}

	private void _populateCompanyCountries(Company company) throws Exception {
		if (_hasCountries(company.getCompanyId())) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					StringBundler.concat(
						"Skipping country initialization. Countries are ",
						"already initialized for company ",
						company.getCompanyId(), "."));
			}

			return;
		}

		_companyAvailableLocales = LanguageUtil.getCompanyAvailableLocales(
			company.getCompanyId());

		_companyDate = new Date(System.currentTimeMillis());

		_companyGuestUser = company.getGuestUser();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Initializing countries for company " + company.getCompanyId());
		}

		JSONArray countriesJSONArray = _getJSONArray(
			"com/liferay/address/dependencies/countries.json");

		try (PreparedStatement countryPreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into Country (mvccVersion, uuid_, ",
						"defaultLanguageId, countryId, companyId, userId, ",
						"userName, createDate, modifiedDate, a2, a3, active_, ",
						"billingAllowed, groupFilterEnabled, idd_, name, ",
						"number_, position, shippingAllowed, subjectToVAT, ",
						"zipRequired, lastPublishDate) values(?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? , ?, ?, ?)"));
			PreparedStatement countryLocalizationPreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into CountryLocalization (mvccVersion, ",
						"countryLocalizationId, companyId, countryId, ",
						"languageId, title) values (?, ?, ?, ?, ?, ?)"));
			PreparedStatement regionPreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into Region (mvccVersion, uuid_, ",
						"defaultLanguageId, regionId, companyId, userId, ",
						"userName, createDate, modifiedDate, countryId, ",
						"active_, name, position, regionCode, ",
						"lastPublishDate) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ",
						"?, ?, ?, ?, ?, ?)"));
			PreparedStatement regionLocalizationPreparedStatement =
				AutoBatchPreparedStatementUtil.concurrentAutoBatch(
					connection,
					StringBundler.concat(
						"insert into RegionLocalization (mvccVersion, ",
						"regionLocalizationId, companyId, regionId, ",
						"languageId, title) values (?, ?, ?, ?, ?, ?)"))) {

			_countryPreparedStatement = countryPreparedStatement;
			_countryLocalizationPreparedStatement =
				countryLocalizationPreparedStatement;
			_regionPreparedStatement = regionPreparedStatement;
			_regionLocalizationPreparedStatement =
				regionLocalizationPreparedStatement;

			for (int i = 0; i < countriesJSONArray.length(); i++) {
				JSONObject countryJSONObject = countriesJSONArray.getJSONObject(
					i);

				_addCountry(company, countryJSONObject);
			}

			_countryPreparedStatement.executeBatch();

			_countryLocalizationPreparedStatement.executeBatch();

			_regionPreparedStatement.executeBatch();

			_regionLocalizationPreparedStatement.executeBatch();
		}
	}

	private void _processCountryRegions(
			Company company, String a2, long countryId)
		throws Exception {

		String path =
			"com/liferay/address/dependencies/regions/" + a2 + ".json";

		if (_classLoader.getResource(path) == null) {
			return;
		}

		JSONArray regionsJSONArray = _getJSONArray(path);

		if (_log.isDebugEnabled()) {
			_log.debug("Regions found for country " + a2);
		}

		for (int i = 0; i < regionsJSONArray.length(); i++) {
			JSONObject regionJSONObject = regionsJSONArray.getJSONObject(i);

			_addRegion(company, countryId, regionJSONObject);
		}
	}

	private void _updateRegionCounter() throws Exception {
		try (Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
				"select max(regionId) from Region")) {

			if (resultSet.next()) {
				increment(Region.class.getName(), (int)resultSet.getLong(1));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountryUpgradeProcess.class);

	private final ClassLoader _classLoader;
	private Set<Locale> _companyAvailableLocales;
	private Date _companyDate;
	private User _companyGuestUser;
	private final CompanyLocalService _companyLocalService;
	private PreparedStatement _countryLocalizationPreparedStatement;
	private PreparedStatement _countryPreparedStatement;
	private final Map<Locale, String> _localesLanguageIds = new HashMap<>();
	private PreparedStatement _regionLocalizationPreparedStatement;
	private PreparedStatement _regionPreparedStatement;

}