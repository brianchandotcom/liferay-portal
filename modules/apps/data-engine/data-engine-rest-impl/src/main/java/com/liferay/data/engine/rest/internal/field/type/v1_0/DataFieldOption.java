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

package com.liferay.data.engine.rest.internal.field.type.v1_0;

import com.liferay.portal.kernel.util.MapUtil;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcela Cunha
 */
public class DataFieldOption {

	public DataFieldOption(Map<String, Object> labels, String value) {
		_labels = labels;
		_value = value;
	}

	public DataFieldOption(String label, String languageId, String value) {
		_labels.put(languageId, label);
		_value = value;
	}

	public String getLabel(String languageId) {
		return MapUtil.getString(_labels, languageId);
	}

	public Map<String, Object> getLabels() {
		return Collections.unmodifiableMap(_labels);
	}

	public String getValue() {
		return _value;
	}

	private Map<String, Object> _labels = new HashMap<>();
	private final String _value;

}