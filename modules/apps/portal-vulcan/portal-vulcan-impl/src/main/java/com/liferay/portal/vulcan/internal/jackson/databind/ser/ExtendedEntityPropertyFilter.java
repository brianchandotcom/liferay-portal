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

import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.util.Optional;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntityPropertyFilter
	extends SimpleBeanPropertyFilter implements PropertyFilter {

	public static ExtendedEntityPropertyFilter with(
		DynamicPropertyFilter dynamicPropertyFilter) {

		return new ExtendedEntityPropertyFilter(dynamicPropertyFilter);
	}

	@Override
	public void serializeAsField(
			Object object, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider,
			PropertyWriter propertyWriter)
		throws Exception {

		try {
			Optional.ofNullable(
				(ExtendedEntity)object
			).map(
				ExtendedEntity::getFilteredPropertyKeys
			).filter(
				filteredPropertyKeys -> !filteredPropertyKeys.isEmpty()
			).ifPresent(
				_dynamicPropertyFilter::addFilteredPropertyKeys
			);

			_dynamicPropertyFilter.serializeAsField(
				object, jsonGenerator, serializerProvider, propertyWriter);
		}
		finally {
			_dynamicPropertyFilter.clearFilteredPropertyKeys();
		}
	}

	private ExtendedEntityPropertyFilter(
		DynamicPropertyFilter dynamicPropertyFilter) {

		_dynamicPropertyFilter = dynamicPropertyFilter;
	}

	private final DynamicPropertyFilter _dynamicPropertyFilter;

}