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

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.internal.jaxrs.context.resolver.EntityExtensionHandlerContextResolver;
import com.liferay.portal.vulcan.internal.jaxrs.extension.ExtendedEntity;
import com.liferay.portal.vulcan.pagination.Page;

import java.io.Serializable;

import java.lang.reflect.Type;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.Providers;
import javax.ws.rs.ext.WriterInterceptorContext;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

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
		MockitoAnnotations.initMocks(this);

		_pageEntityExtensionWriterInterceptor =
			new PageEntityExtensionWriterInterceptor();

		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_providers", _providers);
		ReflectionTestUtil.setFieldValue(
			_pageEntityExtensionWriterInterceptor, "_company", _company);
	}

	@Test
	public void testAroundWrite() throws Exception {
		ArgumentCaptor<Page<ExtendedEntity>> argumentCaptor =
			ArgumentCaptor.forClass(
				(Class<Page<ExtendedEntity>>)(Class<?>)Page.class);
		Map<String, Serializable> extendedProperties = Collections.singletonMap(
			"test", "test");
		Page<TestEntity> page = Page.of(Collections.singleton(_ENTITY_TEST));

		Mockito.when(
			_company.getCompanyId()
		).thenReturn(
			_COMPANY_ID_TEST
		);
		Mockito.when(
			_entityExtensionHandler.getExtendedProperties(
				Mockito.anyLong(), Mockito.any())
		).thenReturn(
			extendedProperties
		);
		Mockito.when(
			_entityExtensionHandlerContextResolver.getContext(Mockito.any())
		).thenReturn(
			_entityExtensionHandler
		);
		Mockito.when(
			_providers.getContextResolver(
				Mockito.eq(EntityExtensionHandler.class),
				Mockito.any(MediaType.class))
		).thenReturn(
			_entityExtensionHandlerContextResolver
		);
		Mockito.when(
			_writerInterceptorContext.getEntity()
		).thenReturn(
			Page.of(Collections.singleton(_ENTITY_TEST))
		);
		Mockito.when(
			_writerInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<TestEntity>>() {
			}.getType()
		);
		Mockito.when(
			_writerInterceptorContext.getMediaType()
		).thenReturn(
			MediaType.APPLICATION_JSON_TYPE
		);
		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)Page.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_entityExtensionHandler
		).getExtendedProperties(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_ENTITY_TEST)
		);
		Mockito.verify(
			_entityExtensionHandler
		).getFilteredPropertyNames(
			Mockito.eq(_COMPANY_ID_TEST), Mockito.eq(_ENTITY_TEST)
		);
		Mockito.verify(
			_entityExtensionHandlerContextResolver
		).getContext(
			Mockito.eq(TestEntity.class)
		);
		Mockito.verify(
			_providers
		).getContextResolver(
			Mockito.eq(EntityExtensionHandler.class),
			Mockito.eq(MediaType.APPLICATION_JSON_TYPE)
		);
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

		Assert.assertEquals(_ENTITY_TEST, extendedEntity.getEntity());
		Assert.assertEquals(
			extendedProperties, extendedEntity.getExtendedProperties());

		Assert.assertEquals(page.getLastPage(), page.getLastPage());
		Assert.assertEquals(page.getPage(), extendedEntityPage.getPage());
		Assert.assertEquals(
			page.getPageSize(), extendedEntityPage.getPageSize());
		Assert.assertEquals(page.getTotalCount(), page.getTotalCount());

		Mockito.verify(
			_writerInterceptorContext
		).setGenericType(
			Mockito.eq(
				new GenericType<Page<ExtendedEntity>>() {
				}.getType())
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithNoEntityExtensionHandler() throws Exception {
		Mockito.when(
			_writerInterceptorContext.getGenericType()
		).thenReturn(
			new GenericType<Page<TestEntity>>() {
			}.getType()
		);
		Mockito.when(
			_writerInterceptorContext.getMediaType()
		).thenReturn(
			MediaType.APPLICATION_JSON_TYPE
		);
		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)Page.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verify(
			_providers
		).getContextResolver(
			Mockito.eq(EntityExtensionHandler.class),
			Mockito.eq(MediaType.APPLICATION_JSON_TYPE)
		);
		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setEntity(
			Mockito.any()
		);

		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setGenericType(
			Mockito.any(Type.class)
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	@Test
	public void testAroundWriteWithNoPageType() throws Exception {
		Mockito.when(
			_writerInterceptorContext.getType()
		).thenReturn(
			(Class)TestEntity.class
		);

		_pageEntityExtensionWriterInterceptor.aroundWriteTo(
			_writerInterceptorContext);

		Mockito.verifyZeroInteractions(_providers);
		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setEntity(
			Mockito.any()
		);
		Mockito.verify(
			_writerInterceptorContext, Mockito.never()
		).setGenericType(
			Mockito.any(Type.class)
		);

		Mockito.verify(
			_writerInterceptorContext
		).proceed();
	}

	private static final long _COMPANY_ID_TEST = 11111;

	private static final TestEntity _ENTITY_TEST = new TestEntity();

	@Mock
	private Company _company;

	@Mock
	private EntityExtensionHandler _entityExtensionHandler;

	@Mock
	private EntityExtensionHandlerContextResolver
		_entityExtensionHandlerContextResolver;

	private PageEntityExtensionWriterInterceptor
		_pageEntityExtensionWriterInterceptor;

	@Mock
	private Providers _providers;

	@Mock
	private WriterInterceptorContext _writerInterceptorContext;

	private static final class TestEntity {
	}

}