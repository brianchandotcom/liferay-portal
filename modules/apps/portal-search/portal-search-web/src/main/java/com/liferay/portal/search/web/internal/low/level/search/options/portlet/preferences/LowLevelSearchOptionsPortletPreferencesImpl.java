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

package com.liferay.portal.search.web.internal.low.level.search.options.portlet.preferences;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.search.options.portlet.SearchOptionsPortletPreferences;
import com.liferay.portal.search.web.internal.util.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class LowLevelSearchOptionsPortletPreferencesImpl
	implements LowLevelSearchOptionsPortletPreferences {

	public LowLevelSearchOptionsPortletPreferencesImpl(
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
			LowLevelSearchOptionsPortletPreferences.PREFERENCE_ATTRIBUTES,
			StringPool.BLANK);
	}

	@Override
	public Optional<String> getConnectionIdOptional() {
		return _portletPreferencesHelper.getString(
			LowLevelSearchOptionsPortletPreferences.
				PREFERENCE_KEY_CONNECTION_ID);
	}

	@Override
	public String getConnectionIdString() {
		return getConnectionIdOptional().orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getContributorsToExcludeOptional() {
		return _portletPreferencesHelper.getString(
			LowLevelSearchOptionsPortletPreferences.
				PREFERENCE_KEY_CONTRIBUTORS_TO_EXCLUDE);
	}

	@Override
	public String getContributorsToExcludeString() {
		return getContributorsToExcludeOptional().orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getContributorsToIncludeOptional() {
		return _portletPreferencesHelper.getString(
			LowLevelSearchOptionsPortletPreferences.
				PREFERENCE_KEY_CONTRIBUTORS_TO_INCLUDE);
	}

	@Override
	public String getContributorsToIncludeString() {
		return getContributorsToIncludeOptional().orElse(StringPool.BLANK);
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
	public Optional<String> getFieldsToReturnOptional() {
		return _portletPreferencesHelper.getString(
			LowLevelSearchOptionsPortletPreferences.
				PREFERENCE_KEY_FIELDS_TO_RETURN);
	}

	@Override
	public String getFieldsToReturnString() {
		return getFieldsToReturnOptional().orElse(StringPool.BLANK);
	}

	@Override
	public Optional<String> getIndexesOptional() {
		return _portletPreferencesHelper.getString(
			LowLevelSearchOptionsPortletPreferences.PREFERENCE_KEY_INDEXES);
	}

	@Override
	public String getIndexesString() {
		return getIndexesOptional().orElse(StringPool.BLANK);
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
		LowLevelSearchOptionsPortletPreferencesImpl.class);

	private final PortletPreferencesHelper _portletPreferencesHelper;

}