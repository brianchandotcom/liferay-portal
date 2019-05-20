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

package com.liferay.source.formatter.checks.configuration;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;

/**
 * @author Hugo Huijser
 */
public class SourceCheckConfiguration {

	public SourceCheckConfiguration(String name) {
		_name = name;
	}

	public void addAttribute(String name, String value) {
		JSONArray jsonArray = _attributesJSONObject.getJSONArray(name);

		if (jsonArray == null) {
			jsonArray = new JSONArrayImpl();
		}

		for (String s : StringUtil.split(value, StringPool.COMMA)) {
			jsonArray.put(s);
		}

		_attributesJSONObject.put(name, jsonArray);
	}

	public JSONObject getAttributesJSONObject() {
		return _attributesJSONObject;
	}

	public String getName() {
		return _name;
	}

	private final JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final String _name;

}