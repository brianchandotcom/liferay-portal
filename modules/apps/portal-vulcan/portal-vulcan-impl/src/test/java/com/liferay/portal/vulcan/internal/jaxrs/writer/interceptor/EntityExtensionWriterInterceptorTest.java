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

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;
import com.liferay.portal.vulcan.internal.jaxrs.util.JAXRSExtensionContextUtil;

import java.io.IOException;

import javax.ws.rs.ext.WriterInterceptorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class EntityExtensionWriterInterceptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_entityExtensionWriterInterceptor =
			new EntityExtensionWriterInterceptor();
		_mockedWriterInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);

		ReflectionTestUtil.setFieldValue(
			_entityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getTestProviders());
	}

	@Test
	public void testAroundWriteWithExtensionContextWithExtendedType()
		throws IOException {

		JAXRSExtensionContextUtil.TestObject testObject =
			JAXRSExtensionContextUtil.getTestObject();

		ArgumentCaptor<ExtendedEntity> argumentCaptor = ArgumentCaptor.forClass(
			ExtendedEntity.class);

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			testObject
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)testObject.getClass()
		);

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setEntity(
			argumentCaptor.capture()
		);

		ExtendedEntity extendedEntity = argumentCaptor.getValue();

		Assert.assertEquals(testObject, extendedEntity.getEntity());
		Assert.assertEquals(
			JAXRSExtensionContextUtil.getTestExtendedProperties(),
			extendedEntity.getExtendedProperties());

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setGenericType(
			Matchers.eq(ExtendedEntity.class)
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithExtensionContextWithNoExtendedType()
		throws IOException {

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			new Object()
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)Object.class
		);

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setEntity(
			Matchers.any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setGenericType(
			Matchers.any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithoutExtensionContextResolver()
		throws IOException {

		ReflectionTestUtil.setFieldValue(
			_entityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getNoContextResolverProviders());

		_entityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setEntity(
			Matchers.any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext, Mockito.never()
		).setGenericType(
			Matchers.any()
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	private EntityExtensionWriterInterceptor _entityExtensionWriterInterceptor;
	private WriterInterceptorContext _mockedWriterInterceptorContext;

}