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

package com.liferay.portal.vulcan.internal.jackson.databind.ser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Javier de Arcos
 */
public class VulcanDynamicPropertyFilter
	extends SimpleBeanPropertyFilter implements DynamicPropertyFilter {

	public static VulcanDynamicPropertyFilter of(
		PropertyFilter propertyFilter) {

		return new VulcanDynamicPropertyFilter(propertyFilter);
	}

	public void addFilteredPropertyKeys(Set<String> filteredPropertyKeys) {
		_filteredPropertyKeys.addAll(filteredPropertyKeys);
	}

	public void clearFilteredPropertyKeys() {
		_filteredPropertyKeys.clear();
	}

	@Override
	public void serializeAsField(
			Object object, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider,
			PropertyWriter propertyWriter)
		throws Exception {

		if (!include(propertyWriter)) {
			return;
		}

		_propertyFilter.serializeAsField(
			object, jsonGenerator, serializerProvider, propertyWriter);
	}

	@Override
	protected boolean include(PropertyWriter propertyWriter) {
		return !_filteredPropertyKeys.contains(propertyWriter.getName());
	}

	private VulcanDynamicPropertyFilter(PropertyFilter propertyFilter) {
		_propertyFilter = propertyFilter;
	}

	private final Set<String> _filteredPropertyKeys = new HashSet<>();
	private final PropertyFilter _propertyFilter;

}