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

package com.liferay.jenkins.results.parser.spira;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class MultiListSpiraCustomPropertyValue
	extends SpiraCustomPropertyValue<List<SpiraCustomListValue>> {

	@Override
	public JSONObject getCustomPropertyJSONObject() {
		JSONObject customPropertyJSONObject =
			super.getCustomPropertyJSONObject();

		customPropertyJSONObject.put(
			"IntegerListValue", _getIntegerListValueJSONArray());

		return customPropertyJSONObject;
	}

	public SpiraCustomList getSpiraCustomList() {
		if (_spiraCustomList != null) {
			return _spiraCustomList;
		}

		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		return spiraCustomProperty.getSpiraCustomList();
	}

	@Override
	public List<SpiraCustomListValue> getValue() {
		List<SpiraCustomListValue> spiraCustomListValues = new ArrayList<>();

		JSONArray integerListValueJSONArray = _getIntegerListValueJSONArray();

		if (integerListValueJSONArray == null) {
			return spiraCustomListValues;
		}

		SpiraCustomList spiraCustomList = getSpiraCustomList();

		for (int i = 0; i < integerListValueJSONArray.length(); i++) {
			spiraCustomListValues.add(
				spiraCustomList.getSpiraCustomListValueByID(
					integerListValueJSONArray.getInt(i)));
		}

		return spiraCustomListValues;
	}

	@Override
	public String getValueString() {
		StringBuilder sb = new StringBuilder();

		for (SpiraCustomListValue spiraCustomListValue : getValue()) {
			sb.append(spiraCustomListValue.getName());
			sb.append(",");
		}

		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1);
		}

		return sb.toString();
	}

	protected MultiListSpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject, spiraCustomProperty);
	}

	protected MultiListSpiraCustomPropertyValue(
		SpiraCustomListValue spiraCustomListValue,
		SpiraCustomProperty spiraCustomProperty) {

		super(new JSONObject(), spiraCustomProperty);

		jsonObject.put("Definition", getDefinitionJSONObject());

		JSONArray integerListValueJSONArray = new JSONArray();

		integerListValueJSONArray.put(spiraCustomListValue.getID());

		jsonObject.put("IntegerListValue", integerListValueJSONArray);

		_spiraCustomList = spiraCustomListValue.getSpiraCustomList();
	}

	@Override
	protected JSONObject getFilterJSONObject() {
		JSONObject filterJSONObject = super.getFilterJSONObject();

		List<SpiraCustomListValue> spiraCustomListValues = getValue();

		SpiraCustomListValue spiraCustomListValue = spiraCustomListValues.get(
			0);

		filterJSONObject.put("IntValue", spiraCustomListValue.getID());

		return filterJSONObject;
	}

	@Override
	protected boolean matchesJSONObject(JSONObject customPropertyJSONObject) {
		JSONArray customPropertyJSONArray =
			customPropertyJSONObject.optJSONArray("IntegerListValue");

		if ((customPropertyJSONArray == null) ||
			customPropertyJSONArray.isEmpty()) {

			return false;
		}

		List<SpiraCustomListValue> spiraCustomListValues = getValue();

		if (spiraCustomListValues.isEmpty()) {
			return false;
		}

		for (int i = 0; i < customPropertyJSONArray.length(); i++) {
			int spiraCustomListValueID = customPropertyJSONArray.getInt(i);

			for (SpiraCustomListValue spiraCustomListValue :
					spiraCustomListValues) {

				if (spiraCustomListValueID == spiraCustomListValue.getID()) {
					return true;
				}
			}
		}

		return false;
	}

	private JSONArray _getIntegerListValueJSONArray() {
		return jsonObject.optJSONArray("IntegerListValue");
	}

	private SpiraCustomList _spiraCustomList;

}