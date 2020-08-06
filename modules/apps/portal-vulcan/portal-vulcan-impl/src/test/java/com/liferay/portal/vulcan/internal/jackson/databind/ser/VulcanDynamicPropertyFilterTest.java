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

import static org.mockito.Matchers.eq;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.PropertyFilter;
import com.fasterxml.jackson.databind.ser.PropertyWriter;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class VulcanDynamicPropertyFilterTest {

	@Before
	public void setUp() {
		_jsonGenerator = Mockito.mock(JsonGenerator.class);
		_propertyFilter = Mockito.mock(PropertyFilter.class);
		_propertyWriter = Mockito.mock(PropertyWriter.class);
		_serializerProvider = Mockito.mock(SerializerProvider.class);
		_vulcanDynamicPropertyFilter = VulcanDynamicPropertyFilter.of(
			_propertyFilter);
	}

	@Test
	public void testSerializeAsField() throws Exception {
		Object object = new Object();

		_vulcanDynamicPropertyFilter.addPropertyFilters(
			Collections.singleton("filtered"));

		Mockito.when(
			_propertyWriter.getName()
		).thenReturn(
			"filtered"
		);

		_vulcanDynamicPropertyFilter.serializeAsField(
			object, _jsonGenerator, _serializerProvider, _propertyWriter);

		Mockito.verifyZeroInteractions(_propertyFilter);

		_vulcanDynamicPropertyFilter.clearPropertyFilters();

		_vulcanDynamicPropertyFilter.serializeAsField(
			object, _jsonGenerator, _serializerProvider, _propertyWriter);

		Mockito.verify(
			_propertyFilter
		).serializeAsField(
			eq(object), eq(_jsonGenerator), eq(_serializerProvider),
			eq(_propertyWriter)
		);
	}

	private JsonGenerator _jsonGenerator;
	private PropertyFilter _propertyFilter;
	private PropertyWriter _propertyWriter;
	private SerializerProvider _serializerProvider;
	private VulcanDynamicPropertyFilter _vulcanDynamicPropertyFilter;

}