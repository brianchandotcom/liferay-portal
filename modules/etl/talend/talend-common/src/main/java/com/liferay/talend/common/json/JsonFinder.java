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

package com.liferay.talend.common.json;

import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonValue;

/**
 * @author Igor Beslic
 */
public class JsonFinder {

	public JsonArray getDescendantJsonArray(
		String path, JsonObject jsonObject) {

		JsonValue descendantJsonValue = getDescendantJsonValue(
			path, jsonObject);

		if (_isNullJsonValue(descendantJsonValue)) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		return descendantJsonValue.asJsonArray();
	}

	public JsonObject getDescendantJsonObject(
		String path, JsonObject jsonObject) {

		JsonValue descendantJsonValue = getDescendantJsonValue(
			path, jsonObject);

		if (_isNullJsonValue(descendantJsonValue)) {
			return JsonValue.EMPTY_JSON_OBJECT;
		}

		return descendantJsonValue.asJsonObject();
	}

	public JsonValue getDescendantJsonValue(
		String path, JsonObject jsonObject) {

		if (!path.contains(">")) {
			if (jsonObject.containsKey(path)) {
				return jsonObject.get(path);
			}

			return JsonValue.NULL;
		}

		int subpathEndIdx = path.indexOf(">");

		String subpath = path.substring(0, subpathEndIdx);

		if (jsonObject.containsKey(subpath)) {
			return getDescendantJsonValue(
				path.substring(subpathEndIdx + 1),
				jsonObject.getJsonObject(subpath));
		}

		return JsonValue.NULL;
	}

	public boolean hasPath(String path, JsonObject jsonObject) {
		if (!path.contains(">")) {
			return jsonObject.containsKey(path);
		}

		int subpathEndIdx = path.indexOf(">");

		String subpath = path.substring(0, subpathEndIdx);

		if (jsonObject.containsKey(subpath)) {
			return hasPath(
				path.substring(subpathEndIdx + 1),
				jsonObject.getJsonObject(subpath));
		}

		return false;
	}

	private boolean _isNullJsonValue(JsonValue jsonValue) {
		if (jsonValue.getValueType() == JsonValue.ValueType.NULL) {
			return true;
		}

		return false;
	}

}