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

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class ListSpiraCustomPropertyValue extends SpiraCustomPropertyValue {

	public SpiraCustomList getSpiraCustomList() {
		if (_spiraCustomList != null) {
			return _spiraCustomList;
		}

		SpiraCustomProperty spiraCustomProperty = getSpiraCustomProperty();

		_spiraCustomList = SpiraCustomList.createSpiraCustomListByName(
			getSpiraProject(), spiraCustomProperty.getSpiraArtifactClass(),
			spiraCustomProperty.getName());

		return _spiraCustomList;
	}

	public SpiraCustomListValue getSpiraCustomListValue() {
		Integer integerValue = _getIntegerValue();

		if ((integerValue == null) || (integerValue == JSONObject.NULL)) {
			return null;
		}

		SpiraCustomList spiraCustomList = getSpiraCustomList();

		return spiraCustomList.getSpiraCustomListValueByID(integerValue);
	}

	protected ListSpiraCustomPropertyValue(
		JSONObject jsonObject, SpiraCustomProperty spiraCustomProperty) {

		super(jsonObject, spiraCustomProperty);
	}

	protected ListSpiraCustomPropertyValue(
		SpiraCustomListValue spiraCustomListValue,
		SpiraCustomProperty spiraCustomProperty) {

		super(new JSONObject(), spiraCustomProperty);

		jsonObject.put("Definition", getDefinitionJSONObject());
		jsonObject.put("IntegerValue", spiraCustomListValue.getID());

		_spiraCustomList = spiraCustomListValue.getSpiraCustomList();
	}

	private int _getIntegerValue() {
		return jsonObject.optInt("IntegerValue");
	}

	private SpiraCustomList _spiraCustomList;

}