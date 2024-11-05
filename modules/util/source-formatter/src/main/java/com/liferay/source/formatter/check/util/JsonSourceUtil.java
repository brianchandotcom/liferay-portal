/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.source.formatter.check.util;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.json.JSONArrayImpl;
import com.liferay.portal.json.JSONObjectImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Alan Huang
 */
public class JsonSourceUtil extends SourceUtil {

	public static String fixIndentation(JSONObject jsonObject, String indent) {
		String s = JSONUtil.toString(jsonObject);

		String[] lines = StringUtil.splitLines(s);

		StringBundler sb = new StringBundler(lines.length * 3);

		for (String line : StringUtil.splitLines(s)) {
			sb.append(indent);
			sb.append(line);
			sb.append("\n");
		}

		return sb.toString();
	}

	public static JSONObject getJSONObject(String s) {
		s = StringUtil.trim(s);

		if (Validator.isNull(s) || s.equals("{}")) {
			return null;
		}

		try {
			return new JSONObjectImpl(s);
		}
		catch (JSONException jsonException) {
			if (_log.isDebugEnabled()) {
				_log.debug(jsonException);
			}

			return null;
		}
	}

	public static JSONArray sortJSONArray(
		JSONArray jsonArray, Comparator<Object> comparator) {

		List<Object> objects = JSONUtil.toObjectList(jsonArray);

		Collections.sort(objects, comparator);

		jsonArray = new JSONArrayImpl();

		for (Object object : objects) {
			jsonArray.put(object);
		}

		return jsonArray;
	}

	private static final Log _log = LogFactoryUtil.getLog(JsonSourceUtil.class);

}