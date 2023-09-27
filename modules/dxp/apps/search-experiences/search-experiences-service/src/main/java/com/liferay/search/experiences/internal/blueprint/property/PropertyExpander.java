/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.property;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Petteri Karttunen
 * @author André de Oliveira
 */
public class PropertyExpander {

	public PropertyExpander(PropertyResolver... propertyResolvers) {
		_propertyResolvers = propertyResolvers;
	}

	public String expand(String json) {
		if (Validator.isNull(json) || (_propertyResolvers.length == 0)) {
			return json;
		}

		int index1 = json.indexOf("${");

		if (index1 == -1) {
			return json;
		}

		StringBundler sb = new StringBundler();

		while (index1 != -1) {
			int index2 = index1 + 2;

			int index3 = json.indexOf("}", index2);

			if (index3 == -1) {
				break;
			}

			int index4 = json.indexOf("|", index2);

			String name = null;
			String optionsString = null;

			if ((index4 == -1) || (index4 > index3)) {
				name = json.substring(index2, index3);
				optionsString = null;
			}
			else {
				name = json.substring(index2, index4);
				optionsString = json.substring(index4 + 1, index3);
			}

			Object value = _resolve(name, _getOptions(optionsString));

			if (value != null) {
				json = _replace(
					json, json.substring(index1, index3 + 1), value);
			}
			else {
				sb.append(json.substring(0, index3 + 1));

				json = json.substring(index3 + 1);
			}

			index1 = json.indexOf("${");
		}

		if (sb.index() == 0) {
			return json;
		}

		sb.append(json);

		return sb.toString();
	}

	private Map<String, String> _getOptions(String optionsString) {
		if (Validator.isNull(optionsString)) {
			return null;
		}

		Map<String, String> options = new HashMap<>();

		for (String string : optionsString.split(",")) {
			String[] array = string.split("=");

			if (array.length == 1) {
				options.put(array[0], null);
			}
			else {
				options.put(array[0], array[1]);
			}
		}

		return options;
	}

	private String _replace(String json, String placeholder, Object value) {
		String valueString = value.toString();

		if ((value instanceof JSONObject) || valueString.startsWith("[")) {
			placeholder = StringUtil.quote(placeholder, CharPool.QUOTE);
		}

		return StringUtil.replace(json, placeholder, valueString);
	}

	private Object _resolve(String name, Map<String, String> options) {
		for (PropertyResolver propertyResolver : _propertyResolvers) {
			Object value = propertyResolver.resolve(name, options);

			if (value != null) {
				return value;
			}
		}

		return null;
	}

	private final PropertyResolver[] _propertyResolvers;

}