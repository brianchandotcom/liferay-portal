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

package com.liferay.search.experiences.internal.blueprint.util;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Petteri Karttunen
 */
@Component(immediate = true, service = SetterHelper.class)
public class SetterHelper {

	public void setBooleanValue(
		JSONObject jsonObject, String key, Consumer<Boolean> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.getBoolean(key));
	}

	public void setDoubleArrayValue(
		JSONObject jsonObject, String key, Consumer<double[]> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(SXPJSONUtil.toDoubleArray(jsonObject.getJSONArray(key)));
	}

	public void setDoubleValue(
		JSONObject jsonObject, String key, Consumer<Double> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.getDouble(key));
	}

	public void setFieldSorts(
		JSONObject jsonObject, Consumer<FieldSort[]> setter) {

		if (!jsonObject.has("sort")) {
			return;
		}

		JSONArray sortJSONArray = jsonObject.getJSONArray("sort");

		List<FieldSort> sorts = new ArrayList<>();

		for (int i = 0; i < sortJSONArray.length(); i++) {
			JSONObject orderJSONObject = sortJSONArray.getJSONObject(i);

			Iterator<String> iterator = orderJSONObject.keys();

			String field = iterator.next();

			JSONObject fieldJSONObject = orderJSONObject.getJSONObject(field);

			if (fieldJSONObject.has("order")) {
				_addFieldSortWithOrder(sorts, field, fieldJSONObject);
			}
			else {
				sorts.add(_sorts.field(field));
			}

			setter.accept(sorts.toArray(new FieldSort[0]));
		}
	}

	public void setFloatValue(
		JSONObject jsonObject, String key, Consumer<Float> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(GetterUtil.getFloat(jsonObject.get(key)));
	}

	public void setIntegerValue(
		JSONObject jsonObject, String key, Consumer<Integer> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.getInt(key));
	}

	public void setLongValue(
		JSONObject jsonObject, String key, Consumer<Long> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.getLong(key));
	}

	public void setObjectValue(
		JSONObject jsonObject, String key, Consumer<Object> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.get(key));
	}

	public void setStringArrayValue(
		JSONObject jsonObject, String key, Consumer<String[]> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(JSONUtil.toStringArray(jsonObject.getJSONArray(key)));
	}

	public void setStringValue(
		JSONObject jsonObject, String key, Consumer<String> setter) {

		if (!jsonObject.has(key)) {
			return;
		}

		setter.accept(jsonObject.getString(key));
	}

	private void _addFieldSortWithOrder(
		List<FieldSort> sorts, String fieldName, JSONObject fieldJSONObject) {

		String order = fieldJSONObject.getString("order");

		try {
			SortOrder sortOrder = SortOrder.valueOf(
				StringUtil.toUpperCase(order));

			sorts.add(_sorts.field(fieldName, sortOrder));
		}
		catch (IllegalArgumentException illegalArgumentException) {
			_log.error(illegalArgumentException);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(SetterHelper.class);

	@Reference
	private Sorts _sorts;

}