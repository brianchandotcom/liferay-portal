/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author David Arques
 */
public class CountrySearchKeywords {

	public CountrySearchKeywords() {
	}

	public CountrySearchKeywords(
		String countryCode, List<SearchKeyword> searchKeywords) {

		_countryCode = countryCode;
		_searchKeywords = searchKeywords;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof CountrySearchKeywords)) {
			return false;
		}

		CountrySearchKeywords countrySearchKeywords =
			(CountrySearchKeywords)object;

		if (Objects.equals(_countryCode, countrySearchKeywords._countryCode) &&
			Objects.equals(
				_searchKeywords, countrySearchKeywords._searchKeywords)) {

			return true;
		}

		return false;
	}

	public String getCountryCode() {
		return _countryCode;
	}

	@JsonProperty("keywords")
	public List<SearchKeyword> getSearchKeywords() {
		return _searchKeywords;
	}

	@Override
	public int hashCode() {
		return Objects.hash(_countryCode, _searchKeywords);
	}

	public void setCountryCode(String countryCode) {
		_countryCode = countryCode;
	}

	public void setSearchKeywords(List<SearchKeyword> searchKeywords) {
		_searchKeywords = searchKeywords;
	}

	public JSONObject toJSONObject(Locale locale) {
		return JSONUtil.put(
			"countryCode", _countryCode
		).put(
			"countryName", _getCountryName(_countryCode, locale)
		).put(
			"keywords", _getSearchKeywordsJSONArray()
		);
	}

	private String _getCountryName(String countryCode, Locale locale) {
		Locale countryLocale = new Locale("", countryCode);

		return countryLocale.getDisplayCountry(locale);
	}

	private JSONArray _getSearchKeywordsJSONArray() {
		if (ListUtil.isEmpty(_searchKeywords)) {
			return JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.toJSONArray(
			ListUtil.subList(_searchKeywords, 0, 5),
			SearchKeyword::toJSONObject, _log);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CountrySearchKeywords.class.getName());

	private String _countryCode;
	private List<SearchKeyword> _searchKeywords;

}