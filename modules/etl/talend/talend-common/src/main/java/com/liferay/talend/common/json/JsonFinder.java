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
		String locatorExpression, JsonObject jsonObject) {

		JsonValue descendantJsonValue = getDescendantJsonValue(
			locatorExpression, jsonObject);

		if (_isNullJsonValue(descendantJsonValue)) {
			return JsonValue.EMPTY_JSON_ARRAY;
		}

		return descendantJsonValue.asJsonArray();
	}

	public JsonObject getDescendantJsonObject(
		String locatorExpression, JsonObject jsonObject) {

		JsonValue descendantJsonValue = getDescendantJsonValue(
			locatorExpression, jsonObject);

		if (_isNullJsonValue(descendantJsonValue)) {
			return JsonValue.EMPTY_JSON_OBJECT;
		}

		return descendantJsonValue.asJsonObject();
	}

	public JsonValue getDescendantJsonValue(
		String locatorExpression, JsonObject jsonObject) {

		if (!locatorExpression.contains(">")) {
			if (jsonObject.containsKey(locatorExpression)) {
				return jsonObject.get(locatorExpression);
			}

			return JsonValue.NULL;
		}

		int sublocationEndIdx = locatorExpression.indexOf(">");

		String sublocation = locatorExpression.substring(0, sublocationEndIdx);

		if (jsonObject.containsKey(sublocation)) {
			return getDescendantJsonValue(
				locatorExpression.substring(sublocationEndIdx + 1),
				jsonObject.getJsonObject(sublocation));
		}

		return JsonValue.NULL;
	}

	public boolean hasJsonObject(
		String locatorExpression, JsonObject jsonObject) {

		if (!locatorExpression.contains(">")) {
			return jsonObject.containsKey(locatorExpression);
		}

		int sublocationEndIdx = locatorExpression.indexOf(">");

		String sublocation = locatorExpression.substring(0, sublocationEndIdx);

		if (jsonObject.containsKey(sublocation)) {
			return hasJsonObject(
				locatorExpression.substring(sublocationEndIdx + 1),
				jsonObject.getJsonObject(sublocation));
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