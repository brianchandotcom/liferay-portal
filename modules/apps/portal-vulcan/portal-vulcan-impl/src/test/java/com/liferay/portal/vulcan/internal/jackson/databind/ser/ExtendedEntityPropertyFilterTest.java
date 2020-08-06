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

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyWriter;

import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import java.util.Collections;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntityPropertyFilterTest {

	@Before
	public void setUp() {
		_dynamicPropertyFilter = Mockito.mock(DynamicPropertyFilter.class);

		_extendedEntityPropertyFilter = ExtendedEntityPropertyFilter.with(
			_dynamicPropertyFilter);

		_jsonGenerator = Mockito.mock(JsonGenerator.class);
		_propertyWriter = Mockito.mock(PropertyWriter.class);
		_serializerProvider = Mockito.mock(SerializerProvider.class);
	}

	@Test
	public void testSerializeAsFieldWithExtendedEntityWithEmptyFilteredPropertyKeys()
		throws Exception {

		ExtendedEntity extendedEntity = ExtendedEntity.extend(
			new Object(), null, Collections.emptySet());

		_extendedEntityPropertyFilter.serializeAsField(
			extendedEntity, _jsonGenerator, _serializerProvider,
			_propertyWriter);

		Mockito.verify(
			_dynamicPropertyFilter, Mockito.never()
		).addPropertyFilters(
			any()
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).serializeAsField(
			eq(extendedEntity), eq(_jsonGenerator), eq(_serializerProvider),
			eq(_propertyWriter)
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).clearPropertyFilters();
	}

	@Test
	public void testSerializeAsFieldWithExtendedEntityWithFilteredPropertyKeys()
		throws Exception {

		Set<String> filteredPropertyKeys = Collections.singleton("test");

		ExtendedEntity extendedEntity = ExtendedEntity.extend(
			new Object(), null, filteredPropertyKeys);

		_extendedEntityPropertyFilter.serializeAsField(
			extendedEntity, _jsonGenerator, _serializerProvider,
			_propertyWriter);

		Mockito.verify(
			_dynamicPropertyFilter
		).addPropertyFilters(
			eq(filteredPropertyKeys)
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).serializeAsField(
			eq(extendedEntity), eq(_jsonGenerator), eq(_serializerProvider),
			eq(_propertyWriter)
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).clearPropertyFilters();
	}

	@Test
	public void testSerializeAsFieldWithExtendedEntityWithNullFilteredPropertyKeys()
		throws Exception {

		ExtendedEntity extendedEntity = ExtendedEntity.extend(
			new Object(), null, null);

		_extendedEntityPropertyFilter.serializeAsField(
			extendedEntity, _jsonGenerator, _serializerProvider,
			_propertyWriter);

		Mockito.verify(
			_dynamicPropertyFilter, Mockito.never()
		).addPropertyFilters(
			any()
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).serializeAsField(
			eq(extendedEntity), eq(_jsonGenerator), eq(_serializerProvider),
			eq(_propertyWriter)
		);
		Mockito.verify(
			_dynamicPropertyFilter
		).clearPropertyFilters();
	}

	private DynamicPropertyFilter _dynamicPropertyFilter;
	private ExtendedEntityPropertyFilter _extendedEntityPropertyFilter;
	private JsonGenerator _jsonGenerator;
	private PropertyWriter _propertyWriter;
	private SerializerProvider _serializerProvider;

}