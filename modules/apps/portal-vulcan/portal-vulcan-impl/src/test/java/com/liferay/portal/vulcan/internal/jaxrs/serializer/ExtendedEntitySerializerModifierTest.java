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

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;

import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;

import org.junit.Assert;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class ExtendedEntitySerializerModifierTest {

	@Test
	public void testModifySerializerOfExtendedEntity() {
		BeanDescription beanDescription = Mockito.mock(BeanDescription.class);
		JsonSerializer<Object> serializer = Mockito.mock(JsonSerializer.class);

		Mockito.when(
			beanDescription.getBeanClass()
		).thenReturn(
			(Class)ExtendedEntity.class
		);

		JsonSerializer<?> jsonSerializer =
			_extendedEntitySerializerModifier.modifySerializer(
				null, beanDescription, serializer);

		Assert.assertTrue(jsonSerializer instanceof ExtendedEntitySerializer);
	}

	@Test
	public void testModifySerializerOfObject() {
		BeanDescription beanDescription = Mockito.mock(BeanDescription.class);
		JsonSerializer<ExtendedEntity> serializer = Mockito.mock(
			JsonSerializer.class);

		Mockito.when(
			beanDescription.getBeanClass()
		).thenReturn(
			(Class)Object.class
		);

		JsonSerializer<?> jsonSerializer =
			_extendedEntitySerializerModifier.modifySerializer(
				null, beanDescription, serializer);

		Assert.assertEquals(serializer, jsonSerializer);
	}

	private final ExtendedEntitySerializerModifier
		_extendedEntitySerializerModifier =
			new ExtendedEntitySerializerModifier();

}