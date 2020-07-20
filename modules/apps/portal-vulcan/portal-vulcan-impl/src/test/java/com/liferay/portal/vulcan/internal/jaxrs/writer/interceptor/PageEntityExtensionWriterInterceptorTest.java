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
import com.liferay.portal.vulcan.pagination.Page;

import java.io.IOException;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.GenericType;
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
public class PageEntityExtensionWriterInterceptorTest {

	@Before
	public void setUp() {
		_mockedProviders = Mockito.mock(Providers.class);
		_mockedWriterInterceptorContext = Mockito.mock(
			WriterInterceptorContext.class);

		_pageEntityExtensionWriterInterceptor =
			new PageEntityExtensionWriterInterceptor();

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers",
			_mockedProviders);
	}

	@Test
	public void testAroundWriteNotPage() throws IOException {
		Mockito.when(
			_mockedWriterInterceptorContext.getType()
		).thenReturn(
			(Class)Dummy.class
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

		Dummy entity = new Dummy();

		Page<Dummy> page = Page.of(Collections.singleton(entity));

		Map<String, Object> extendedProperties = Collections.singletonMap(
			"testProperty", "test");
		ArgumentCaptor<Page> extendedPageCaptor = ArgumentCaptor.forClass(
			Page.class);

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
			page
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<Dummy>>() {
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
			extendedProperties, extendedEntity.getExtendedProperties());

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

		Page<Dummy> page = Page.of(Collections.singleton(new Dummy()));

		ContextResolver<ExtensionContext> mockedContextResolver = Mockito.mock(
			ContextResolver.class);

		Mockito.when(
			_mockedProviders.getContextResolver(
				eq(ExtensionContext.class), any())
		).thenReturn(
			mockedContextResolver
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			page
		);
		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<Dummy>>() {
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

	@Test
	public void testAroundWritePageWithoutExtensionContextResolver()
		throws IOException {

		Page<Dummy> page = Page.of(Collections.singleton(new Dummy()));

		Mockito.when(
			_mockedWriterInterceptorContext.getEntity()
		).thenReturn(
			page
		);

		Mockito.when(
			_mockedWriterInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<Dummy>>() {
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

	private Providers _mockedProviders;
	private WriterInterceptorContext _mockedWriterInterceptorContext;
	private PageEntityExtensionWriterInterceptor
		_pageEntityExtensionWriterInterceptor;

	private static class Dummy {
	}

}