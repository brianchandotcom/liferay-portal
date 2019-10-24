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

package com.liferay.batch.engine.internal.writer;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author Ivica cardic
 */
public class ObjectWriterFactory {

	public static ObjectWriter getObjectWriter(
		List<String> allFieldNames, List<String> includeFieldNames) {

		SimpleFilterProvider simpleFilterProvider = new SimpleFilterProvider();

		if (includeFieldNames.isEmpty()) {
			simpleFilterProvider.setFailOnUnknownId(false);
		}
		else {
			List<String> excludeFieldNames = new ArrayList<>();

			for (String fieldName : allFieldNames) {
				if (!includeFieldNames.contains(fieldName)) {
					excludeFieldNames.add(fieldName);
				}
			}

			SimpleBeanPropertyFilter simpleBeanPropertyFilter =
				SimpleBeanPropertyFilter.serializeAllExcept(
					new HashSet<>(excludeFieldNames));

			simpleFilterProvider.addFilter(
				"Liferay.Vulcan", simpleBeanPropertyFilter);
		}

		return _objectMapper.writer(simpleFilterProvider);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			enable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);
			enable(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS);
			disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		}
	};

}