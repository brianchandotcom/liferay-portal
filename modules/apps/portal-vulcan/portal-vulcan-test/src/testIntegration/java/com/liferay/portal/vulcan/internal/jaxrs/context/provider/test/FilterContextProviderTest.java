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

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockFeature;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockMessage;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockResource;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;

import javax.ws.rs.core.Feature;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Cristina González
 */
@RunWith(Arquillian.class)
public class FilterContextProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		MockFeature mockFeature = new MockFeature(_feature);

		_contextProvider = (ContextProvider<Filter>)mockFeature.getObject(
			"com.liferay.portal.vulcan.internal.jaxrs.context.provider." +
				"FilterContextProvider");

		Registry registry = RegistryUtil.getRegistry();

		_mockResource = new MockResource();

		_serviceRegistration = registry.registerService(
			EntityModelResource.class, _mockResource,
			HashMapBuilder.<String, Object>put(
				"component.name", MockResource.class.getCanonicalName()
			).put(
				"osgi.jaxrs.resource", "true"
			).build());
	}

	@After
	public void tearDown() {
		_serviceRegistration.unregister();
	}

	@Test
	public void testCreateContext() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("filter", "title eq 'example'");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Filter filter = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertTrue(filter instanceof TermFilter);

		TermFilter termFilter = (TermFilter)filter;

		Assert.assertEquals("example", termFilter.getValue());
		Assert.assertEquals("internalTitle", termFilter.getField());
	}

	private ContextProvider<Filter> _contextProvider;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFeature"
	)
	private Feature _feature;

	private MockResource _mockResource;
	private ServiceRegistration<EntityModelResource> _serviceRegistration;

}