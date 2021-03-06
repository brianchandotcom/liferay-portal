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
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.GenericType;
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
public class PageEntityExtensionWriterInterceptorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		_pageEntityExtensionWriterInterceptor =
			new PageEntityExtensionWriterInterceptor();

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getTestProviders());

		_writerInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);
	}

	@Test
	public void testAroundWriteNotPage() throws IOException {
		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)JAXRSExtensionContextUtil.TestObject.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setEntity(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setGenericType(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWritePageWithExtensionContextWithExtendedType()
		throws IOException {

		JAXRSExtensionContextUtil.TestObject testObject =
			JAXRSExtensionContextUtil.getTestObject();

		Page<JAXRSExtensionContextUtil.TestObject> page = Page.of(
			Collections.singleton(testObject));

		ArgumentCaptor<Page<ExtendedEntity>> argumentCaptor =
			ArgumentCaptor.forClass(
				(Class<Page<ExtendedEntity>>)(Class<?>)Page.class);

		Mockito.when(
			_writerInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_writerInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<JAXRSExtensionContextUtil.TestObject>>() {
			}.getType()
		);

		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)page.getClass()
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext
		).setEntity(
			argumentCaptor.capture()
		);

		Page<ExtendedEntity> extendedEntityPage = argumentCaptor.getValue();

		Assert.assertEquals(page.getActions(), extendedEntityPage.getActions());

		Collection<ExtendedEntity> extendedEntities =
			extendedEntityPage.getItems();

		ExtendedEntity extendedEntity =
			extendedEntities.toArray(new ExtendedEntity[0])[0];

		Assert.assertEquals(testObject, extendedEntity.getEntity());
		Assert.assertEquals(
			JAXRSExtensionContextUtil.getTestExtendedProperties(),
			extendedEntity.getExtendedProperties());

		Assert.assertEquals(page.getLastPage(), page.getLastPage());
		Assert.assertEquals(page.getPage(), extendedEntityPage.getPage());
		Assert.assertEquals(
			page.getPageSize(), extendedEntityPage.getPageSize());
		Assert.assertEquals(page.getTotalCount(), page.getTotalCount());

		Mockito.verify(
			_writerInterceptorContext
		).setGenericType(
			Matchers.eq(
				new GenericType<Page<ExtendedEntity>>() {
				}.getType())
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWritePageWithExtensionContextWithNoExtendedType()
		throws IOException {

		Page<Object> page = Page.of(Collections.singleton(new Object()));

		Mockito.when(
			_writerInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_writerInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<Object>>() {
			}.getType()
		);

		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)Object.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setEntity(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setGenericType(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWritePageWithoutExtensionContextResolver()
		throws IOException {

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getNoContextResolverProviders());

		Page<Object> page = Page.of(
			Collections.singleton(JAXRSExtensionContextUtil.getTestObject()));

		Mockito.when(
			_writerInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_writerInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<JAXRSExtensionContextUtil.TestObject>>() {
			}.getType()
		);

		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)page.getClass()
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setEntity(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setGenericType(
			Matchers.any()
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	private PageEntityExtensionWriterInterceptor
		_pageEntityExtensionWriterInterceptor;
	private WriterInterceptorContext _writerInterceptorContext;

}