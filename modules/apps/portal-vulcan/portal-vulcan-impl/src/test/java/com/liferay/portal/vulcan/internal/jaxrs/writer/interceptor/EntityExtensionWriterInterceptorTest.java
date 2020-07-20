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

package com.liferay.portal.vulcan.internal.jaxrs.writer.interceptor;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;
import com.liferay.portal.vulcan.jaxrs.context.ExtensionContext;

import java.io.IOException;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class EntityExtensionWriterInterceptorTest {

	@Before
	public void setUp() {
		_entityExtensionWriterInterceptor =
			new EntityExtensionWriterInterceptor();
		_mockedProviders = Mockito.mock(Providers.class);
		_mockedWriterInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);

		ReflectionTestUtil.setFieldValue(
			_entityExtensionWriterInterceptor, "_providers", _mockedProviders);
	}

	@Test
	public void testAroundWriteWithExtensionContextWithExtendedType()
		throws IOException {

		Dummy entity = new Dummy();
		Map<String, Object> extendedProperties = Collections.singletonMap(
			"testProperty", "test");
		ArgumentCaptor<ExtendedEntity> extendedEntityCaptor =
			ArgumentCaptor.forClass(ExtendedEntity.class);

		ContextResolver<ExtensionContext> mockedContextResolver = Mockito.mock(
			ContextResolver.class);
		ExtensionContext mockedExtensionContext = Mockito.mock(
			ExtensionContext.class);

		Mockito.when(
			mockedContextResolver.getContext(eq(Dummy.class))
		).thenReturn(
			mockedExtensionContext
		);
		Mockito.when(
			mockedExtensionContext.getExtendedProperties(eq(entity))
		).thenReturn(
			extendedProperties
		);

		Mockito.when(
			_mockedProviders.getContextResolver(
				eq(ExtensionContext.class), any())
		).thenReturn(
			mockedContextResolver
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			entity
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)entity.getClass()
		);

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setEntity(
			extendedEntityCaptor.capture()
		);
		ExtendedEntity extendedEntity = extendedEntityCaptor.getValue();

		Assert.assertEquals(entity, extendedEntity.getEntity());
		Assert.assertEquals(
			extendedProperties, extendedEntity.getExtendedProperties());

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setGenericType(
			eq(ExtendedEntity.class)
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithExtensionContextWithNoExtendedType()
		throws IOException {

		ContextResolver<ExtensionContext> mockedContextResolver = Mockito.mock(
			ContextResolver.class);

		Mockito.when(
			_mockedProviders.getContextResolver(
				eq(ExtensionContext.class), any())
		).thenReturn(
			mockedContextResolver
		);

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setEntity(
			any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setGenericType(
			any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithoutExtensionContextResolver()
		throws IOException {

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setEntity(
			any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setGenericType(
			any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	private EntityExtensionWriterInterceptor _entityExtensionWriterInterceptor;
	private Providers _mockedProviders;
	private WriterInterceptorContext _mockedWriterInterceptorContext;

	private static class Dummy {
	}

}