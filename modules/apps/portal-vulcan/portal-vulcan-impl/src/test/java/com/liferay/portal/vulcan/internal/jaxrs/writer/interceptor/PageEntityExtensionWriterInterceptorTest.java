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
import com.liferay.portal.vulcan.internal.jaxrs.util.JAXRSExtensionContextUtil;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

/**
 * @author Javier de Arcos
 */
public class PageEntityExtensionWriterInterceptorTest {

	@Before
	public void setUp() {
		_mockedWriterInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);

		_pageEntityExtensionWriterInterceptor =
			new PageEntityExtensionWriterInterceptor();

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getTestProviders());
	}

	@Test
	public void testAroundWriteNotPage() throws IOException {
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)JAXRSExtensionContextUtil.TestObject.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
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
	public void testAroundWritePageWithExtensionContextWithExtendedType()
		throws IOException {

		JAXRSExtensionContextUtil.TestObject entity =
			JAXRSExtensionContextUtil.getTestObject();

		Page<JAXRSExtensionContextUtil.TestObject> page = Page.of(
			Collections.singleton(entity));

		ArgumentCaptor<Page> extendedPageCaptor = ArgumentCaptor.forClass(
			Page.class);

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			page
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<JAXRSExtensionContextUtil.TestObject>>() {
			}.getType()
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)page.getClass()
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_mockedWriterInterceptorContext);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setEntity(
			extendedPageCaptor.capture()
		);
		Page<ExtendedEntity> extendedPage = extendedPageCaptor.getValue();

		Assert.assertEquals(page.getActions(), extendedPage.getActions());

		Collection<ExtendedEntity> items = extendedPage.getItems();

		ExtendedEntity extendedEntity = items.toArray(new ExtendedEntity[0])[0];

		Assert.assertEquals(entity, extendedEntity.getEntity());
		Assert.assertEquals(
			JAXRSExtensionContextUtil.getTestExtendedProperties(),
			extendedEntity.getExtendedProperties());

		Assert.assertEquals(page.getPage(), extendedPage.getPage());
		Assert.assertEquals(page.getPageSize(), extendedPage.getPageSize());
		Assert.assertEquals(page.getLastPage(), page.getLastPage());
		Assert.assertEquals(page.getTotalCount(), page.getTotalCount());

		Mockito.verify(
			_mockedWriterInterceptorContext
		).setGenericType(
			eq(
				new GenericType<Page<ExtendedEntity>>() {
				}.getType())
		);

		Mockito.verify(
			_mockedWriterInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWritePageWithExtensionContextWithNoExtendedType()
		throws IOException {

		Page<Object> page = Page.of(Collections.singleton(new Object()));

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<Object>>() {
			}.getType()
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)Object.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
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
	public void testAroundWritePageWithoutExtensionContextResolver()
		throws IOException {

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers",
			JAXRSExtensionContextUtil.getNoContextResolverProviders());

		Page<Object> page = Page.of(
			Collections.singleton(JAXRSExtensionContextUtil.getTestObject()));

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<JAXRSExtensionContextUtil.TestObject>>() {
			}.getType()
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)page.getClass()
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
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

	private WriterInterceptorContext _mockedWriterInterceptorContext;
	private PageEntityExtensionWriterInterceptor
		_pageEntityExtensionWriterInterceptor;

}