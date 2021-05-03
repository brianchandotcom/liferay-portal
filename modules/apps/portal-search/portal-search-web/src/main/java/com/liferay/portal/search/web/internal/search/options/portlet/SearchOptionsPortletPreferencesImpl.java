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

package com.liferay.portal.search.web.internal.search.options.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class SearchOptionsPortletPreferencesImpl
	implements SearchOptionsPortletPreferences {

	public SearchOptionsPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public JSONArray getAttributesJSONArray() {
		String fieldsString = getAttributesString();

		if (Validator.isBlank(fieldsString)) {
			return _getDefaultAttributesJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(fieldsString);
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create a JSON array from: " + fieldsString,
				jsonException);

			return _getDefaultAttributesJSONArray();
		}
	}

	@Override
	public String getAttributesString() {
		return _portletPreferencesHelper.getString(
			SearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES,
			StringPool.BLANK);
	}

	@Override
	public Optional<String> getFederatedSearchKeyOptional() {
		return _portletPreferencesHelper.getString(
			SearchOptionsPortletPreferences.
				PREFERENCE_KEY_FEDERATED_SEARCH_KEY);
	}

	@Override
	public String getFederatedSearchKeyString() {
		return getFederatedSearchKeyOptional().orElse(StringPool.BLANK);
	}

	@Override
	public boolean isAllowEmptySearches() {
		return _portletPreferencesHelper.getBoolean(
			SearchOptionsPortletPreferences.PREFERENCE_KEY_ALLOW_EMPTY_SEARCHES,
			false);
	}

	@Override
	public boolean isBasicFacetSelection() {
		return _portletPreferencesHelper.getBoolean(
			SearchOptionsPortletPreferences.
				PREFERENCE_KEY_BASIC_FACET_SELECTION,
			false);
	}

	private JSONArray _getDefaultAttributesJSONArray() {
		return JSONUtil.put(
			JSONUtil.put(
				"key", StringPool.BLANK
			).put(
				"value", StringPool.BLANK
			));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SearchOptionsPortletPreferencesImpl.class);

	private final PortletPreferencesHelper _portletPreferencesHelper;

}