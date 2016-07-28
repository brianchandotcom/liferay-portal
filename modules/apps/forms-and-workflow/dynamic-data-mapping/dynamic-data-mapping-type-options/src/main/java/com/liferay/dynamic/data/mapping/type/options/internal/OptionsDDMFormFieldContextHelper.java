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

package com.liferay.dynamic.data.mapping.type.options.internal;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class OptionsDDMFormFieldContextHelper {

	public OptionsDDMFormFieldContextHelper(
		JSONFactory jsonFactory, String value) {

		_jsonFactory = jsonFactory;
		_value = value;
	}

	protected List<Object> getValue() {
		List<Object> list = new ArrayList<>();

		if (Validator.isNull(_value)) {
			return list;
		}

		try {
			JSONArray jsonArray = _jsonFactory.createJSONArray(_value);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				Map<String, String> optionMap = new HashMap<>();

				optionMap.put("label", jsonObject.getString("label"));
				optionMap.put("value", jsonObject.getString("value"));

				list.add(optionMap);
			}

			return list;
		}
		catch (JSONException jsone) {
			_log.error("Unable to parse JSON array", jsone);

			return list;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OptionsDDMFormFieldContextHelper.class);

	private final JSONFactory _jsonFactory;
	private final String _value;

}