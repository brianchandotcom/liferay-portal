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

import jodd.introspector.PropertyDescriptor;
import jodd.json.JsonSerializer;
import jodd.json.TypeJsonVisitor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Igor Spasic
 */
public class FlexjsonBeanAnalyzerTransformer extends TypeJsonVisitor {

	public FlexjsonBeanAnalyzerTransformer(Class type) {
		super(new JsonSerializer().createJsonContext(null), type);
	}

	public List<Map<String, String>> collect() {
		_propertiesList = new ArrayList<>();

		visit();

		return _propertiesList;
	}

	protected String getTypeName(Class<?> type) {
		return type.getName();
	}

	@Override
	protected void onSerializableProperty(
			String propertyName, PropertyDescriptor propertyDescriptor) {

		Class propertyType = propertyDescriptor.getType();

		Map<String, String> properties = new LinkedHashMap<>();

		properties.put("name", propertyName);
		properties.put("type", getTypeName(propertyType));

		_propertiesList.add(properties);
	}

	private List<Map<String, String>> _propertiesList = new ArrayList<>();

}