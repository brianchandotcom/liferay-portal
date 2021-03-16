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

package com.liferay.portal.search.tuning.blueprints.internal.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.GetterUtil;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author Petteri Karttunen
 */
public class BlueprintJSONUtil {

	public static Object getValue(Object object, String... paths) {
		if (object == null) {
			return null;
		}

		Object value = null;

		String[] parts = paths[0].split("/");

		String type = parts[0];
		String key = parts[1];

		if (type.equals("JSONArray")) {
			JSONObject jsonObject = (JSONObject)object;

			value = jsonObject.getJSONArray(key);
		}
		else if (type.equals("JSONObject")) {
			JSONObject jsonObject = (JSONObject)object;

			value = jsonObject.getJSONObject(key);
		}
		else if (type.equals("Object")) {
			if (object instanceof JSONArray) {
				JSONArray jsonArray = (JSONArray)object;

				value = jsonArray.get(GetterUtil.getInteger(key));
			}
			else if (object instanceof JSONObject) {
				JSONObject jsonObject = (JSONObject)object;

				value = jsonObject.get(key);
			}
		}

		if (paths.length == 1) {
			return value;
		}

		if (value == null) {
			return null;
		}

		return getValue(value, Arrays.copyOfRange(paths, 1, paths.length));
	}

	public static Optional<Boolean> getValueAsBooleanOptional(
		Object object, String... paths) {

		Object value = getValue(object, paths);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(GetterUtil.getBoolean(value));
	}

	public static Optional<Integer> getValueAsIntegerOptional(
		Object object, String... paths) {

		Object value = getValue(object, paths);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(GetterUtil.getInteger(value));
	}

	public static Optional<JSONArray> getValueAsJSONArrayOptional(
		Object object, String... paths) {

		Object value = getValue(object, paths);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of((JSONArray)value);
	}

	public static Optional<JSONObject> getValueAsJSONObjectOptional(
		Object object, String... paths) {

		Object value = getValue(object, paths);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of((JSONObject)value);
	}

	public static Optional<String> getValueAsStringOptional(
		Object object, String... paths) {

		Object value = getValue(object, paths);

		if (value == null) {
			return Optional.empty();
		}

		return Optional.of(String.valueOf(value));
	}

}