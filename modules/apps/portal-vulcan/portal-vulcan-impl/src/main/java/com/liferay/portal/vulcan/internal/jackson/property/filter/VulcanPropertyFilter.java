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

package com.liferay.portal.vulcan.internal.jackson.property.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.Set;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 */
public class VulcanPropertyFilter
	extends SimpleBeanPropertyFilter implements PropertyFilter {

	public static VulcanPropertyFilter of(Set<String> fieldNames) {
		return new VulcanPropertyFilter(fieldNames);
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

		if (_shouldWrite(jsonGenerator, propertyWriter)) {
			propertyWriter.serializeAsField(
				object, jsonGenerator, serializerProvider);
		}
	}

	private VulcanPropertyFilter(Set<String> fieldNames) {
		_fieldNames = fieldNames;
	}

	private String _createPath(String name, JsonGenerator jsonGenerator) {
		StringBuilder stringBuilder = new StringBuilder(name);

		JsonStreamContext jsonStreamContext = jsonGenerator.getOutputContext();

		if (jsonStreamContext != null) {
			jsonStreamContext = jsonStreamContext.getParent();
		}

		while (jsonStreamContext != null) {
			String currentName = jsonStreamContext.getCurrentName();

			if (currentName != null) {
				stringBuilder.insert(0, currentName + ".");
			}

			jsonStreamContext = jsonStreamContext.getParent();
		}

		return stringBuilder.toString();
	}

	private boolean _shouldWrite(
		JsonGenerator jsonGenerator, PropertyWriter propertyWriter) {

		String path = _createPath(propertyWriter.getName(), jsonGenerator);

		if (_fieldNames.contains(path)) {
			return true;
		}

		if (path.contains(".")) {
			String parent = path.substring(0, path.lastIndexOf('.'));

			if (_fieldNames.contains(parent)) {
				Stream<String> stream = _fieldNames.stream();

				return stream.noneMatch(
					field -> field.startsWith(parent + "."));
			}
		}

		return false;
	}

	private final Set<String> _fieldNames;

}