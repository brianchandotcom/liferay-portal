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
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * @author David Arques
 */
public class TrafficSource {

	public TrafficSource() {
	}

	public TrafficSource(
		List<CountrySearchKeywords> countrySearchKeywordsList, String name,
		long trafficAmount, double trafficShare) {

		_countrySearchKeywordsList = countrySearchKeywordsList;
		_name = name;
		_trafficAmount = trafficAmount;
		_trafficShare = trafficShare;

		_error = false;
	}

	public TrafficSource(String name) {
		_name = name;

		_countrySearchKeywordsList = Collections.emptyList();
		_error = true;
		_trafficAmount = 0;
		_trafficShare = 0;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof TrafficSource)) {
			return false;
		}

		TrafficSource trafficSource = (TrafficSource)object;

		if (Objects.equals(
				_countrySearchKeywordsList,
				trafficSource._countrySearchKeywordsList) &&
			Objects.equals(_name, trafficSource._name) &&
			Objects.equals(_trafficAmount, trafficSource._trafficAmount) &&
			Objects.equals(_trafficShare, trafficSource._trafficShare)) {

			return true;
		}

		return false;
	}

	@JsonProperty("countryKeywords")
	public List<CountrySearchKeywords> getCountrySearchKeywordsList() {
		return _countrySearchKeywordsList;
	}

	public String getName() {
		return _name;
	}

	public long getTrafficAmount() {
		return _trafficAmount;
	}

	public double getTrafficShare() {
		return _trafficShare;
	}

	@Override
	public int hashCode() {
		return Objects.hash(
			_countrySearchKeywordsList, _name, _trafficAmount, _trafficShare);
	}

	public void setCountrySearchKeywordsList(
		List<CountrySearchKeywords> countrySearchKeywordsList) {

		_countrySearchKeywordsList = countrySearchKeywordsList;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setTrafficAmount(long trafficAmount) {
		_trafficAmount = trafficAmount;
	}

	public void setTrafficShare(double trafficShare) {
		_trafficShare = trafficShare;
	}

	public JSONObject toJSONObject(
		String helpMessage, Locale locale, String title) {

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		if (ListUtil.isNotEmpty(_countrySearchKeywordsList)) {
			jsonObject.put(
				"countryKeywords", _getCountryKeywordsJSONArray(locale));
		}

		jsonObject.put(
			"helpMessage", helpMessage
		).put(
			"name", getName()
		);

		if (!_error) {
			jsonObject.put("share", String.format("%.1f", _trafficShare));
		}

		jsonObject.put("title", title);

		if (!_error) {
			jsonObject.put("value", Math.toIntExact(_trafficAmount));
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		JSONObject jsonObject = toJSONObject(
			null, LocaleUtil.getDefault(), _name);

		return jsonObject.toString();
	}

	private JSONArray _getCountryKeywordsJSONArray(Locale locale) {
		if (ListUtil.isEmpty(_countrySearchKeywordsList)) {
			return JSONFactoryUtil.createJSONArray();
		}

		return JSONUtil.toJSONArray(
			_countrySearchKeywordsList,
			countrySearchKeywords -> countrySearchKeywords.toJSONObject(locale),
			_log);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		TrafficSource.class.getName());

	private List<CountrySearchKeywords> _countrySearchKeywordsList;
	private boolean _error;
	private String _name;
	private long _trafficAmount;
	private double _trafficShare;

}