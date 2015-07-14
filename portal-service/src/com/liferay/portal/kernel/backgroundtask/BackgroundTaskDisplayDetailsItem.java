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

package com.liferay.portal.kernel.backgroundtask;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;

import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDisplayDetailsItem
	implements BackgroundTaskDisplayJSONObject {

	public BackgroundTaskDisplayDetailsItem(JSONObject jsonObject) {
		_info = jsonObject.getString("info");
		_errorMessage = jsonObject.getString("errorMessage");
		_errorStrongMessage = jsonObject.getString("errorStrongMessage");
	}

	public BackgroundTaskDisplayDetailsItem(
		String info, String errorMessage, String errorStrongMessage) {

		_info = info;
		_errorMessage = errorMessage;
		_errorStrongMessage = errorStrongMessage;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("info", _info);
		jsonObject.put("errorMessage", _errorMessage);
		jsonObject.put("errorStrongMessage", _errorStrongMessage);

		return jsonObject;
	}

	@Override
	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("info", LanguageUtil.get(locale, _info));
		jsonObject.put("errorMessage", LanguageUtil.get(locale, _errorMessage));
		jsonObject.put(
			"errorStrongMessage",
			LanguageUtil.get(locale, _errorStrongMessage));

		return jsonObject;
	}

	private final String _errorMessage;
	private final String _errorStrongMessage;
	private final String _info;

}