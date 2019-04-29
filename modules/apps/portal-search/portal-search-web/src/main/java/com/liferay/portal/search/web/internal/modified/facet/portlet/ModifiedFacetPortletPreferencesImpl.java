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

package com.liferay.portal.search.web.internal.modified.facet.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Lino Alves
 */
public class ModifiedFacetPortletPreferencesImpl
	implements ModifiedFacetPortletPreferences {

	public ModifiedFacetPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferences) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferences);
	}

	@Override
	public String getParameterName() {
		return _portletPreferencesHelper.getString(
			ModifiedFacetPortletPreferences.PREFERENCE_KEY_PARAMETER_NAME,
			"modified");
	}

	@Override
	public JSONArray getRangesJSONArray() {
		String rangesString = getRangesString();

		if (Validator.isBlank(rangesString)) {
			return getDefaultRangesJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(rangesString);
		}
		catch (JSONException jsone) {
			_log.error(
				"Unable to create a JSON array from: " + rangesString, jsone);

			return getDefaultRangesJSONArray();
		}
	}

	@Override
	public String getRangesString() {
		return _portletPreferencesHelper.getString(
			ModifiedFacetPortletPreferences.PREFERENCE_KEY_RANGES,
			StringPool.BLANK);
	}

	protected JSONArray getDefaultRangesJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (int i = 0; i < _LABELS.length; i++) {
			JSONObject jsonObject = JSONUtil.put(
				"label", _LABELS[i]
			).put(
				"range", _RANGES[i]
			);

			jsonArray.put(jsonObject);
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
		ModifiedFacetPortletPreferencesImpl.class);

	private final PortletPreferencesHelper _portletPreferencesHelper;

}