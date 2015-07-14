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

import java.util.List;
import java.util.Locale;

/**
* @author Andrew Betts
*/
public class BackgroundTaskDisplayDetails
	implements BackgroundTaskDisplayJSONObject {

	public BackgroundTaskDisplayDetails(
		String header, int status,
		List<BackgroundTaskDisplayDetailsSection> sections) {

		_header = header;
		_status = status;
		_sections = sections;
	}

	@Override
	public JSONObject toJSONObject() {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("header", _header);
		jsonObject.put("status", _status);
		jsonObject.put(
			"sections",
			BackgroundTaskDisplayJSONTransformer.toJSONArray(_sections));

		return jsonObject;
	}

	public JSONObject toJSONObject(Locale locale) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("header", LanguageUtil.get(locale, _header));
		jsonObject.put("status", _status);
		jsonObject.put(
			"sections",
			BackgroundTaskDisplayJSONTransformer.toJSONArray(
				_sections, locale));

		return jsonObject;
	}

	private final String _header;
	private final List<BackgroundTaskDisplayDetailsSection> _sections;
	private final int _status;

}