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

package com.liferay.frontend.taglib.clay.servlet.taglib.model.table;

import com.liferay.petra.string.StringPool;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author Iván Zaera Avellón
 */
public class Schema {

	public void addField(Field field) {
		_fields.add(field);
	}

	public void addInputNameMapping(String type, String name) {
		_inputNamesMap.put(type, name);
	}

	public Collection<Field> getFields() {
		return _fields;
	}

	public void setInputName(String inputName) {
		_inputName = inputName;
	}

	public void setInputNameField(String inputNameField) {
		_inputNameField = inputNameField;
	}

	public void setInputValueField(String inputValueField) {
		_inputValueField = inputValueField;
	}

	public Map<String, ?> toMap() {
		Map<String, Object> map = new HashMap<>();

		map.put("fields", _getFields());
		map.put("inputName", _inputName);
		map.put("inputNameField", _inputNameField);
		map.put("inputNamesMap", _inputNamesMap);
		map.put("inputValueField", _inputValueField);

		return map;
	}

	private List<Map<String, ?>> _getFields() {
		Stream<Field> stream = StreamSupport.stream(
			_fields.spliterator(), false);

		return stream.map(
			Field::toMap
		).collect(
			Collectors.toList()
		);
	}

	private final List<Field> _fields = new ArrayList<>();
	private String _inputName = StringPool.BLANK;
	private String _inputNameField = StringPool.BLANK;
	private final Map<String, String> _inputNamesMap = new HashMap<>();
	private String _inputValueField = StringPool.BLANK;

}