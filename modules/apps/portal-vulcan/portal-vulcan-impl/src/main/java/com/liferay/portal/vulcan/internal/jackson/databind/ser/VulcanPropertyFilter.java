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
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alejandro Hernández
 */
public class VulcanPropertyFilter
	extends SimpleBeanPropertyFilter implements PropertyFilter {

	public static VulcanPropertyFilter of(
		Set<String> fieldNames, Set<String> restrictFieldNames) {

		return new VulcanPropertyFilter(fieldNames, restrictFieldNames);
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

	private VulcanPropertyFilter(
		Set<String> fieldNames, Set<String> restrictFieldNames) {

		if (fieldNames == null) {
			_fieldNames = new HashSet<>();
		}
		else {
			_fieldNames = fieldNames;
		}

		if (restrictFieldNames == null) {
			_restrictFieldNames = new HashSet<>();
		}
		else {
			_restrictFieldNames = restrictFieldNames;
		}
	}

	private boolean _isFiltered(String path) {
		if ((_fieldNames.isEmpty() || _fieldNames.contains(path)) &&
			!_restrictFieldNames.contains(path)) {

			return true;
		}

		return false;
	}

	private boolean _isFilteredWithoutNested(String path) {
		if (_isFiltered(path)) {
			Stream<String> fieldStream = _fieldNames.stream();

			Predicate<String> stringPredicate = field -> field.startsWith(
				path + ".");

			Stream<String> restrictFieldStream = _restrictFieldNames.stream();

			if (fieldStream.noneMatch(stringPredicate) &&
				restrictFieldStream.noneMatch(stringPredicate)) {

				return true;
			}

			return false;
		}

		return false;
	}

	private boolean _shouldWrite(
		JsonGenerator jsonGenerator, PropertyWriter propertyWriter) {

		List<String> paths = _toPaths(jsonGenerator, propertyWriter.getName());

		String firstPath = paths.get(0);

		if (_isFiltered(firstPath)) {
			return true;
		}

		if (paths.size() == 1) {
			return false;
		}

		for (int i = 1; i < paths.size(); i++) {
			if (_isFilteredWithoutNested(paths.get(i))) {
				return true;
			}
		}

		return false;
	}

	private List<String> _toPaths(JsonGenerator jsonGenerator, String name) {
		List<String> paths = new ArrayList<>();

		paths.add(name);

		JsonStreamContext jsonStreamContext = jsonGenerator.getOutputContext();

		if (jsonStreamContext != null) {
			jsonStreamContext = jsonStreamContext.getParent();
		}

		while (jsonStreamContext != null) {
			String currentName = jsonStreamContext.getCurrentName();

			if ((currentName != null) && currentName.contains("items")) {
				break;
			}

			if (currentName != null) {
				Stream<String> stream = paths.stream();

				paths = stream.map(
					(currentName + ".")::concat
				).collect(
					Collectors.toList()
				);

				paths.add(currentName);
			}

			jsonStreamContext = jsonStreamContext.getParent();
		}

		return paths;
	}

	private final Set<String> _fieldNames;
	private final Set<String> _restrictFieldNames;

}