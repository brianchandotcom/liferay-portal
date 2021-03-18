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

package com.liferay.commerce.internal.upgrade.v6_0_0;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Country;
import com.liferay.portal.kernel.service.CountryLocalServiceUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LocalizationUtil;

import java.sql.ResultSet;
import java.sql.Statement;

import java.util.Date;

/**
 * @author Pei-Jung Lan
 */
public class CommerceCountryUpgradeProcess extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		try (Statement selectStatement = connection.createStatement()) {
			ResultSet rs = selectStatement.executeQuery(
				"select * from CommerceCountry order by commerceCountryId asc");

			while (rs.next()) {
				String a2 = rs.getString("twoLettersISOCode");
				String a3 = rs.getString("threeLettersISOCode");
				boolean active = rs.getBoolean("active_");
				boolean billingAllowed = rs.getBoolean("billingAllowed");
				boolean channelFilterEnabled = rs.getBoolean(
					"channelFilterEnabled");
				long commerceCountryId = rs.getLong("commerceCountryId");
				long companyId = rs.getLong("companyId");
				Date lastPublishDate = rs.getTimestamp("lastPublishDate");
				String name = rs.getString("name");
				String numericISOCode = rs.getString("numericISOCode");
				Double priority = rs.getDouble("priority");
				boolean shippingAllowed = rs.getBoolean("shippingAllowed");
				boolean subjectToVAT = rs.getBoolean("subjectToVAT");

				Country country = CountryLocalServiceUtil.fetchCountryByA2(
					companyId, a2);

				if (country != null) {
					country = _updateCountry(
						country, a2, a3, active, billingAllowed,
						channelFilterEnabled, name, numericISOCode, priority,
						shippingAllowed, subjectToVAT, lastPublishDate);
				}
				else {
					country = _addCountry(
						commerceCountryId, companyId, rs.getLong("userId"),
						rs.getString("userName"), rs.getTimestamp("createDate"),
						rs.getTimestamp("modifiedDate"), a2, a3, active,
						billingAllowed, channelFilterEnabled, name,
						numericISOCode, priority, shippingAllowed, subjectToVAT,
						lastPublishDate);
				}

				if (country.getCountryId() != commerceCountryId) {
					for (String tableName : _TABLE_NAMES) {
						_updateCountryId(
							tableName, country.getCountryId(),
							commerceCountryId);
					}
				}
			}

			runSQL("drop table CommerceCountry");
		}
	}

	private Country _addCountry(
			long countryId, long companyId, long userId, String userName,
			Date createDate, Date modifiedDate, String a2, String a3,
			boolean active, boolean billingAllowed, boolean groupFilterEnabled,
			String name, String number, double position,
			boolean shippingAllowed, boolean subjectToVAT, Date lastPublishDate)
		throws Exception {

		if (CountryLocalServiceUtil.fetchCountry(countryId) != null) {
			countryId = increment();
		}

		Country country = CountryLocalServiceUtil.createCountry(countryId);

		country.setCompanyId(companyId);
		country.setUserId(userId);
		country.setUserName(userName);
		country.setCreateDate(createDate);
		country.setModifiedDate(modifiedDate);
		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setBillingAllowed(billingAllowed);
		country.setDefaultLanguageId(
			LocalizationUtil.getDefaultLanguageId(name));
		country.setGroupFilterEnabled(groupFilterEnabled);
		country.setName(
			LocalizationUtil.getLocalization(
				name, LocalizationUtil.getDefaultLanguageId(name)));
		country.setNumber(number);
		country.setPosition(position);
		country.setShippingAllowed(shippingAllowed);
		country.setSubjectToVAT(subjectToVAT);
		country.setLastPublishDate(lastPublishDate);

		CountryLocalServiceUtil.addCountry(country);

		for (String languageId :
				LocalizationUtil.getAvailableLanguageIds(name)) {

			CountryLocalServiceUtil.updateCountryLocalization(
				country, languageId,
				LocalizationUtil.getLocalization(name, languageId));
		}

		return country;
	}

	private Country _updateCountry(
			Country country, String a2, String a3, boolean active,
			boolean billingAllowed, boolean groupFilterEnabled, String name,
			String number, double position, boolean shippingAllowed,
			boolean subjectToVAT, Date lastPublishDate)
		throws Exception {

		country.setA2(a2);
		country.setA3(a3);
		country.setActive(active);
		country.setBillingAllowed(billingAllowed);
		country.setDefaultLanguageId(
			LocalizationUtil.getDefaultLanguageId(name));
		country.setGroupFilterEnabled(groupFilterEnabled);
		country.setName(
			LocalizationUtil.getLocalization(
				name, LocalizationUtil.getDefaultLanguageId(name)));
		country.setNumber(number);
		country.setPosition(position);
		country.setShippingAllowed(shippingAllowed);
		country.setSubjectToVAT(subjectToVAT);
		country.setLastPublishDate(lastPublishDate);

		for (String languageId :
				LocalizationUtil.getAvailableLanguageIds(name)) {

			CountryLocalServiceUtil.updateCountryLocalization(
				country, languageId,
				LocalizationUtil.getLocalization(name, languageId));
		}

		return CountryLocalServiceUtil.updateCountry(country);
	}

	private void _updateCountryId(
			String tableName, long newCountryId, long oldCountryId)
		throws Exception {

		String columnName = "countryId";

		if (tableName.equals("CommerceRegion")) {
			columnName = "commerceCountryId";
		}

		StringBundler sb = new StringBundler(10);

		sb.append("update ");
		sb.append(tableName);
		sb.append(" set ");
		sb.append(columnName);
		sb.append(" = ");
		sb.append(newCountryId);
		sb.append(" where ");
		sb.append(columnName);
		sb.append(" = ");
		sb.append(oldCountryId);

		runSQL(sb.toString());
	}

	private static final String[] _TABLE_NAMES = {
		"CommerceAddress", "CommerceAddressRestriction", "CommerceRegion",
		"CommerceTaxFixedRateAddressRel", "CShippingFixedOptionRel"
	};

}