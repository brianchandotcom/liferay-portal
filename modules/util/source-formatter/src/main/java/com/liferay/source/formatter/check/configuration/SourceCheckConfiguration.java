/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.configuration;

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

	public SourceCheckConfiguration(String category, String name) {
		_category = category;
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

	public String getCategory() {
		return _category;
	}

	public String getName() {
		return _name;
	}

	public int getWeight() {
		return _weight;
	}

	public void setWeight(int weight) {
		_weight = weight;
	}

	private final JSONObject _attributesJSONObject = new JSONObjectImpl();
	private final String _category;
	private final String _name;
	private int _weight = -1;

}