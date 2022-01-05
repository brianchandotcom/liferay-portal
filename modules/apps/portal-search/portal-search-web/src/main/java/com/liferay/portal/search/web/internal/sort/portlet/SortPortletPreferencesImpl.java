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

package com.liferay.portal.search.web.internal.sort.portlet;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.web.internal.helper.PortletPreferencesHelper;

import java.util.Optional;

import javax.portlet.PortletPreferences;

/**
 * @author Wade Cao
 */
public class SortPortletPreferencesImpl implements SortPortletPreferences {

	public SortPortletPreferencesImpl(
		Optional<PortletPreferences> portletPreferencesOptional) {

		_portletPreferencesHelper = new PortletPreferencesHelper(
			portletPreferencesOptional);
	}

	@Override
	public JSONArray getFieldsJSONArray() {
		String fieldsString = getFieldsString();

		if (Validator.isBlank(fieldsString)) {
			return _getDefaultFieldsJSONArray();
		}

		try {
			return JSONFactoryUtil.createJSONArray(fieldsString);
		}
		catch (JSONException jsonException) {
			_log.error(
				"Unable to create a JSON array from: " + fieldsString,
				jsonException);

			return _getDefaultFieldsJSONArray();
		}
	}

	@Override
	public String getFieldsString() {
		return _portletPreferencesHelper.getString(
			SortPortletPreferences.PREFERENCE_KEY_FIELDS, StringPool.BLANK);
	}

	@Override
	public String getParameterName() {
		return "sort";
	}

	private JSONArray _getDefaultFieldsJSONArray() {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (Preset preset : _presets) {
			jsonArray.put(
				JSONUtil.put(
					"field", preset._field
				).put(
					"label", preset._label
				));
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SortPortletPreferencesImpl.class);

	private static final Preset[] _presets = {
		new Preset("", "relevance"), new Preset("title", "title"),
		new Preset("modified-", "modified"),
		new Preset("modified+", "modified-oldest-first"),
		new Preset("createDate-", "created"),
		new Preset("createDate+", "created-oldest-first"),
		new Preset("userName", "user")
	};

	private final PortletPreferencesHelper _portletPreferencesHelper;

	private static class Preset {

		public Preset(String field, String label) {
			_field = field;
			_label = label;
		}

		private final String _field;
		private final String _label;

	}

}