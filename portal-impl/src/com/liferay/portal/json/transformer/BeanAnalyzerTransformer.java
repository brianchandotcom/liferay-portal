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

package com.liferay.portal.json.transformer;

import com.liferay.portal.kernel.util.LinkedHashMapBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jodd.introspector.PropertyDescriptor;

import jodd.json.JsonSerializer;
import jodd.json.TypeJsonVisitor;

/**
 * @author Igor Spasic
 */
public class BeanAnalyzerTransformer extends TypeJsonVisitor {

	public BeanAnalyzerTransformer(Class<?> clazz) {
		super(_jsonSerializer.createJsonContext(null), clazz);
	}

	public List<Map<String, String>> collect() {
		_propertiesList = new ArrayList<>();

		visit();

		return _propertiesList;
	}

	protected String getTypeName(Class<?> clazz) {
		return clazz.getName();
	}

	@Override
	protected void onSerializableProperty(
		String propertyName, PropertyDescriptor propertyDescriptor) {

		Map<String, String> properties = LinkedHashMapBuilder.put(
			"name", propertyName
		).put(
			"type", getTypeName(propertyDescriptor.getType())
		).build();

		_propertiesList.add(properties);
	}

	private static final JsonSerializer _jsonSerializer = new JsonSerializer();

	private List<Map<String, String>> _propertiesList = new ArrayList<>();

}