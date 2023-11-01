/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.web.internal.date.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.portlet.preferences.BasePortletPreferences;
import com.liferay.portal.search.web.internal.user.facet.portlet.UserFacetPortletPreferences;

import javax.portlet.PortletPreferences;

/**
 * @author Petteri Karttunen
 */
public class DateFacetPortletPreferencesImpl
	extends BasePortletPreferences implements DateFacetPortletPreferences {

	public DateFacetPortletPreferencesImpl(
		PortletPreferences portletPreferences) {

		super(portletPreferences);
	}

	@Override
	public String getAggregationField() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_AGGREGATION_FIELD,
			StringPool.BLANK);
	}

	@Override
	public String getCustomHeading() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_CUSTOM_HEADING,
			StringPool.BLANK);
	}

	@Override
	public String getFederatedSearchKey() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_FEDERATED_SEARCH_KEY,
			StringPool.BLANK);
	}

	@Override
	public int getFrequencyThreshold() {
		return getInteger(
			DateFacetPortletPreferences.PREFERENCE_KEY_FREQUENCY_THRESHOLD, 0);
	}

	@Override
	public String getOrder() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_ORDER,
			"rangesConfiguration");
	}

	@Override
	public String getParameterName() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME, "date");
	}

	@Override
	public JSONArray getRangesJSONArray() {
		String rangesString = getRangesString();

		if (Validator.isBlank(rangesString)) {
			return _getDefaultRangesJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(rangesString);
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create a JSON array from: " + rangesString,
				jsonException);

			return _getDefaultRangesJSONArray();
		}
	}

	@Override
	public String getRangesString() {
		return getString(
			DateFacetPortletPreferences.PREFERENCE_KEY_RANGES,
			StringPool.BLANK);
	}

	@Override
	public boolean isFrequenciesVisible() {
		return getBoolean(
			UserFacetPortletPreferences.PREFERENCE_KEY_FREQUENCIES_VISIBLE,
			true);
	}

	private JSONArray _getDefaultRangesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _LABELS.length; i++) {
			jsonArray.put(
				JSONUtil.put(
					"label", _LABELS[i]
				).put(
					"range", _RANGES[i]
				));
		}

		return jsonArray;
	}

	private static final String[] _LABELS = {
		"past-hour", "past-24-hours", "past-week", "past-month", "past-year"
	};

	private static final String[] _RANGES = {
		"[past-hour TO *]", "[past-24-hours TO *]", "[past-week TO *]",
		"[past-month TO *]", "[past-year TO *]"
	};

	private static final Log _log = LogFactoryUtil.getLog(
		DateFacetPortletPreferencesImpl.class);

}