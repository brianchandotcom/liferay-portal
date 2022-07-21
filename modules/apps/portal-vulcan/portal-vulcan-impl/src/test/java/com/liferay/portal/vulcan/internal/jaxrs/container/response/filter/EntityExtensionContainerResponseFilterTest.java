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

package com.liferay.portal.vulcan.internal.jaxrs.container.response.filter;

import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionHandler;
import com.liferay.portal.vulcan.internal.extension.EntityExtensionThreadLocal;

import java.io.Serializable;

import java.util.Collections;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Providers;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author Carlos Correa
 */
public class EntityExtensionContainerResponseFilterTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		EntityExtensionThreadLocal.setExtendedProperties(null);

		ReflectionTestUtil.setFieldValue(
			_entityExtensionContainerResponseFilter, "_company", _company);
		ReflectionTestUtil.setFieldValue(
			_entityExtensionContainerResponseFilter, "_providers", _providers);
	}

	@Test
	public void testFilter() throws Exception {
		Long companyId = RandomTestUtil.randomLong();

		Mockito.when(
			_company.getCompanyId()
		).thenReturn(
			companyId
		);

		Mockito.when(
			_containerResponseContext.getEntity()
		).thenReturn(
			_TEST_ENTITY
		);

		Mockito.doReturn(
			TestEntity.class
		).when(
			_containerResponseContext
		).getEntityClass();

		Mockito.when(
			_containerResponseContext.getMediaType()
		).thenReturn(
			MediaType.APPLICATION_JSON_TYPE
		);

		Mockito.when(
			_contextResolver.getContext(Mockito.any())
		).thenReturn(
			_entityExtensionHandler
		);

		Mockito.doNothing(
		).when(
			_entityExtensionHandler
		).setExtendedProperties(
			Mockito.anyLong(), Mockito.any(), Mockito.anyMap()
		);

		Mockito.when(
			_providers.getContextResolver(
				Mockito.any(Class.class), Mockito.any(MediaType.class))
		).thenReturn(
			_contextResolver
		);

		Map<String, Serializable> extendedProperties = Collections.singletonMap(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		EntityExtensionThreadLocal.setExtendedProperties(extendedProperties);

		_entityExtensionContainerResponseFilter.filter(
			_containerRequestContext, _containerResponseContext);

		Mockito.verify(
			_company
		).getCompanyId();

		Mockito.verify(
			_containerResponseContext
		).getEntity();

		Mockito.verify(
			_containerResponseContext
		).getEntityClass();

		Mockito.verify(
			_containerResponseContext
		).getMediaType();

		Mockito.verify(
			_contextResolver
		).getContext(
			TestEntity.class
		);

		Mockito.verify(
			_entityExtensionHandler
		).setExtendedProperties(
			companyId, _TEST_ENTITY, extendedProperties
		);

		Mockito.verifyNoMoreInteractions(_entityExtensionHandler);

		Mockito.verify(
			_providers
		).getContextResolver(
			EntityExtensionHandler.class, MediaType.APPLICATION_JSON_TYPE
		);
	}

	@Test
	public void testFilterWithNoContextResolver() throws Exception {
		Mockito.when(
			_containerResponseContext.getMediaType()
		).thenReturn(
			MediaType.APPLICATION_JSON_TYPE
		);

		Mockito.when(
			_providers.getContextResolver(
				Mockito.any(Class.class), Mockito.any(MediaType.class))
		).thenReturn(
			null
		);

		Map<String, Serializable> extendedProperties = Collections.singletonMap(
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		EntityExtensionThreadLocal.setExtendedProperties(extendedProperties);

		_entityExtensionContainerResponseFilter.filter(
			_containerRequestContext, _containerResponseContext);

		Mockito.verify(
			_containerResponseContext
		).getMediaType();

		Mockito.verifyNoMoreInteractions(_entityExtensionHandler);

		Mockito.verify(
			_providers
		).getContextResolver(
			EntityExtensionHandler.class, MediaType.APPLICATION_JSON_TYPE
		);
	}

	@Test
	public void testFilterWithNoExtendedProperties() throws Exception {
		_entityExtensionContainerResponseFilter.filter(
			_containerRequestContext, _containerResponseContext);

		Mockito.verifyNoMoreInteractions(_entityExtensionHandler);
	}

	private static final TestEntity _TEST_ENTITY = new TestEntity();

	@Mock
	private Company _company;

	@Mock
	private ContainerRequestContext _containerRequestContext;

	@Mock
	private ContainerResponseContext _containerResponseContext;

	@Mock
	private ContextResolver<EntityExtensionHandler> _contextResolver;

	private final EntityExtensionContainerResponseFilter
		_entityExtensionContainerResponseFilter =
			new EntityExtensionContainerResponseFilter();

	@Mock
	private EntityExtensionHandler _entityExtensionHandler;

	@Mock
	private Providers _providers;

	private static class TestEntity {
	}

}