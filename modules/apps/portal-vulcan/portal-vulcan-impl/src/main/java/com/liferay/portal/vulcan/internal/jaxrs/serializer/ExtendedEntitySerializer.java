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

package com.liferay.portal.vulcan.internal.jaxrs.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.portal.vulcan.internal.jackson.databind.ser.ExtendedEntityPropertyFilter;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.io.IOException;

import java.util.Set;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntitySerializer
	extends JsonSerializer<ExtendedEntity> implements ResolvableSerializer {

	public ExtendedEntitySerializer(
		JsonSerializer<ExtendedEntity> defaultSerializer) {

		_defaultSerializer = defaultSerializer;
	}

	@Override
	public void resolve(SerializerProvider serializerProvider)
		throws JsonMappingException {

		if (_defaultSerializer instanceof ResolvableSerializer) {
			ResolvableSerializer resolvableSerializer =
				(ResolvableSerializer)_defaultSerializer;

			resolvableSerializer.resolve(serializerProvider);
		}
	}

	@Override
	public void serialize(
			ExtendedEntity extendedEntity, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider)
		throws IOException {

		Set<String> filteredProperties = extendedEntity.getFilteredProperties();

		if ((filteredProperties != null) && !filteredProperties.isEmpty()) {
			SimpleFilterProvider simpleFilterProvider =
				(SimpleFilterProvider)serializerProvider.getFilterProvider();

			PropertyFilter liferayVulcanFilter =
				simpleFilterProvider.removeFilter("Liferay.Vulcan");

			simpleFilterProvider.addFilter(
				"Liferay.Vulcan",
				new ExtendedEntityPropertyFilter(
					liferayVulcanFilter, filteredProperties));
		}

		_defaultSerializer.serialize(
			extendedEntity, jsonGenerator, serializerProvider);
	}

	private final JsonSerializer<ExtendedEntity> _defaultSerializer;

}