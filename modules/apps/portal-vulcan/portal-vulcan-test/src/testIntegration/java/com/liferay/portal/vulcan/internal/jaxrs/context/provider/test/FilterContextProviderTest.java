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
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockFeature;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockMessage;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockResource;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.core.Feature;
import javax.ws.rs.core.HttpHeaders;

import org.apache.cxf.jaxrs.ext.ContextProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

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

		Bundle bundle = FrameworkUtil.getBundle(
			FilterContextProviderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		_mockResource = new MockResource();

		_serviceRegistration = bundleContext.registerService(
			EntityModelResource.class, _mockResource,
			HashMapDictionaryBuilder.<String, Object>put(
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

		Assert.assertTrue(filter instanceof QueryFilter);

		QueryFilter queryFilter = (QueryFilter)filter;

		TermQuery termQuery = (TermQuery)queryFilter.getQuery();

		QueryTerm queryTerm = termQuery.getQueryTerm();

		Assert.assertEquals("example", queryTerm.getValue());
		Assert.assertEquals("internalTitle", queryTerm.getField());
	}

	@Test(expected = InvalidFilterException.class)
	public void testCreateContextThrowsInvalidFilterException()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("filter", "title eeq 'example'");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		_contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));
	}

	@Test(expected = NotAcceptableException.class)
	public void testCreateContextThrowsNotAcceptableException()
		throws Exception {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("filter", "title eq 'example'");
					addHeader(
						HttpHeaders.ACCEPT_LANGUAGE,
						LocaleUtil.toW3cLanguageId(LocaleUtil.TAIWAN));
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		_contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));
	}

	private ContextProvider<Filter> _contextProvider;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFilterFeature"
	)
	private Feature _feature;

	private MockResource _mockResource;
	private ServiceRegistration<EntityModelResource> _serviceRegistration;

}