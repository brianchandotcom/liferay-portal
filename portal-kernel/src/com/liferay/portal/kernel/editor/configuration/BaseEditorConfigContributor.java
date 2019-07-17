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

package com.liferay.portal.kernel.editor.configuration;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageConstants;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.Locale;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 */
public abstract class BaseEditorConfigContributor
	implements EditorConfigContributor {

	protected String getContentsLanguageDir(
		Map<String, Object> inputEditorTaglibAttributes) {

		return LanguageUtil.get(
			getContentsLocale(inputEditorTaglibAttributes),
			LanguageConstants.KEY_DIR);
	}

	protected String getContentsLanguageId(
		Map<String, Object> inputEditorTaglibAttributes) {

		return LocaleUtil.toLanguageId(
			getContentsLocale(inputEditorTaglibAttributes));
	}

	protected Locale getContentsLocale(
		Map<String, Object> inputEditorTaglibAttributes) {

		String contentsLanguageId = (String)inputEditorTaglibAttributes.get(
			"liferay-ui:input-editor:contentsLanguageId");

		return LocaleUtil.fromLanguageId(contentsLanguageId);
	}

	protected String getLanguageId(ThemeDisplay themeDisplay) {
		String languageId = LocaleUtil.toLanguageId(themeDisplay.getLocale());

		Locale locale = LocaleUtil.fromLanguageId(languageId);

		return LocaleUtil.toLanguageId(locale);
	}

	protected JSONArray toJSONArray(String json) {
		try {
			return JSONFactoryUtil.createJSONArray(json);
		}
		catch (JSONException jsone) {
			_log.error("Unable to create a JSON array from: " + json, jsone);
		}

		return JSONFactoryUtil.createJSONArray();
	}

	protected JSONArray toJSONArray(String... values) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (String value : values) {
			jsonArray.put(value);
		}

		return jsonArray;
	}

	protected JSONObject toJSONObject(String json) {
		try {
			return JSONFactoryUtil.createJSONObject(json);
		}
		catch (JSONException jsone) {
			_log.error("Unable to create a JSON object from: " + json, jsone);
		}

		return JSONFactoryUtil.createJSONObject();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseEditorConfigContributor.class);

}