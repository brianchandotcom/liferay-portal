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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ResolvableSerializer;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.vulcan.internal.jackson.databind.ser.ExtendedEntityPropertyFilter;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.io.IOException;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntitySerializerTest {

	@Before
	public void setUp() {
		_defaultSerializer = Mockito.mock(
			ResolvableExtendedEntitySerializer.class);

		_extendedEntitySerializer = new ExtendedEntitySerializer(
			_defaultSerializer);

		_extendedEntity = ExtendedEntity.extend(
			new Object(), Collections.emptyMap(), null);
		_jsonGenerator = Mockito.mock(JsonGenerator.class);

		_filterProvider = Mockito.mock(SimpleFilterProvider.class);

		_serializationConfig = new SerializationConfig(
			null, null, null, null, null);

		_serializationConfig = _serializationConfig.withFilters(
			_filterProvider);

		_serializerProvider = Mockito.mock(SerializerProvider.class);

		ReflectionTestUtil.setFieldValue(
			_serializerProvider, "_config", _serializationConfig);
	}

	@Test
	public void testResolveWithNotResolvableDefaultSerializer()
		throws JsonMappingException {

		NotResolvableExtendedEntitySerializer notResolvableSerializer =
			Mockito.mock(NotResolvableExtendedEntitySerializer.class);

		ExtendedEntitySerializer extendedEntitySerializer =
			new ExtendedEntitySerializer(notResolvableSerializer);

		extendedEntitySerializer.resolve(_serializerProvider);

		Mockito.verifyZeroInteractions(notResolvableSerializer);
	}

	@Test
	public void testResolveWithResolvableDefaultSerializer()
		throws JsonMappingException {

		_extendedEntitySerializer.resolve(_serializerProvider);

		Mockito.verify(
			_defaultSerializer
		).resolve(
			eq(_serializerProvider)
		);
	}

	@Test
	public void testSerializeWithFilteredProperties() throws IOException {
		_extendedEntitySerializer.serialize(
			_extendedEntity, _jsonGenerator, _serializerProvider);

		Mockito.verify(
			_filterProvider
		).removeFilter(
			eq("Liferay.Vulcan")
		);
		Mockito.verify(
			_filterProvider
		).addFilter(
			eq("Liferay.Vulcan"), any(ExtendedEntityPropertyFilter.class)
		);
		Mockito.verify(
			_defaultSerializer
		).serialize(
			eq(_extendedEntity), eq(_jsonGenerator), eq(_serializerProvider)
		);
	}

	@Test
	public void testSerializeWithNullFilteredProperties() throws IOException {
		_extendedEntitySerializer.serialize(
			_extendedEntity, _jsonGenerator, _serializerProvider);

		Mockito.verify(
			_filterProvider, Mockito.never()
		).removeFilter(
			eq("Liferay.Vulcan")
		);
		Mockito.verify(
			_filterProvider, Mockito.never()
		).addFilter(
			eq("Liferay.Vulcan"), any(ExtendedEntityPropertyFilter.class)
		);
		Mockito.verify(
			_defaultSerializer
		).serialize(
			eq(_extendedEntity), eq(_jsonGenerator), eq(_serializerProvider)
		);
	}

	@Test
	public void testSerializeWithoutFilteredProperties() throws IOException {
		_extendedEntitySerializer.serialize(
			_extendedEntity, _jsonGenerator, _serializerProvider);

		Mockito.verify(
			_filterProvider, Mockito.never()
		).removeFilter(
			eq("Liferay.Vulcan")
		);
		Mockito.verify(
			_filterProvider, Mockito.never()
		).addFilter(
			eq("Liferay.Vulcan"), any(ExtendedEntityPropertyFilter.class)
		);
		Mockito.verify(
			_defaultSerializer
		).serialize(
			eq(_extendedEntity), eq(_jsonGenerator), eq(_serializerProvider)
		);
	}

	private ResolvableExtendedEntitySerializer _defaultSerializer;
	private ExtendedEntity _extendedEntity;
	private ExtendedEntitySerializer _extendedEntitySerializer;
	private SimpleFilterProvider _filterProvider;
	private JsonGenerator _jsonGenerator;
	private SerializationConfig _serializationConfig;
	private SerializerProvider _serializerProvider;

	private static class NotResolvableExtendedEntitySerializer
		extends JsonSerializer<ExtendedEntity> {

		@Override
		public void serialize(
			ExtendedEntity extendedEntity, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) {
		}

	}

	private static class ResolvableExtendedEntitySerializer
		extends JsonSerializer<ExtendedEntity> implements ResolvableSerializer {

		@Override
		public void resolve(SerializerProvider serializerProvider) {
		}

		@Override
		public void serialize(
			ExtendedEntity extendedEntity, JsonGenerator jsonGenerator,
			SerializerProvider serializerProvider) {
		}

	}

}