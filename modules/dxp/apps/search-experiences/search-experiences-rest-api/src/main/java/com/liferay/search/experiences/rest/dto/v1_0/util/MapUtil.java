/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * @author André de Oliveira
 */
public class MapUtil {

	public static void unpack(Map<String, Object> values1) {
		if (com.liferay.portal.kernel.util.MapUtil.isEmpty(values1)) {
			return;
		}

		Map<String, Object> values2 = new HashMap<>(values1);

		values2.forEach((name, value) -> values1.put(name, _unpack(value)));
	}

	private static Object _unpack(Object value) {
		if (value instanceof Map) {
			return JSONFactoryUtil.createJSONObject((Map<?, ?>)value);
		}

		if (value instanceof Object[]) {
			return JSONFactoryUtil.createJSONArray((Object[])value);
		}

		if (value instanceof String) {
			try {
				return JSONFactoryUtil.createJSONObject((String)value);
			}
			catch (JSONException jsonException) {
				return value;
			}
		}

		return value;
	}

}