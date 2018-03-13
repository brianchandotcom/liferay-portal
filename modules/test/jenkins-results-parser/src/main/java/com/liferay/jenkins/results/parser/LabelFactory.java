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

package com.liferay.jenkins.results.parser;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

/**
 * @author Michael Hashimoto
 */
public class LabelFactory {

	public static Label newLabel(String labelsURL, String name) {
		return newLabel(labelsURL, name, null);
	}

	public static Label newLabel(String labelsURL, String name, String color) {
		if (!Label.isValidLabelsURL(labelsURL)) {
			throw new IllegalArgumentException("Invalid url " + labelsURL);
		}

		if (name == null) {
			throw new IllegalArgumentException("Invalid name " + name);
		}

		if (_labels.containsKey(name)) {
			Label label = _labels.get(name);

			if (color != null) {
				label.setColor(color);
			}

			return label;
		}

		Label label = new Label(labelsURL, name, color);

		_labels.put(name, label);

		return label;
	}

	protected static Label newLabel(JSONObject jsonObject) {
		String name = jsonObject.getString("name");

		if (_labels.containsKey(name)) {
			return _labels.get(name);
		}

		Label label = new Label(jsonObject);

		_labels.put(name, label);

		return label;
	}

	private static final Map<String, Label> _labels = new HashMap<>();

}