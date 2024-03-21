/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.search.QueryTerm;
import com.liferay.portal.kernel.search.TermQuery;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.filter.QueryFilter;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.odata.filter.InvalidFilterException;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockFeature;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockMessage;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockResource;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import java.util.Locale;

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

	@Test
	public void testCreateContextWithDifferentLocale() throws Exception {
		_testCreateContext(LocaleUtil.TAIWAN, Http.Method.GET);

		_testCreateContext(LocaleUtil.TAIWAN, Http.Method.POST);
	}

	private void _testCreateContext(Locale locale, Http.Method method) {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("filter", "title eq 'example'");
					addHeader(
						HttpHeaders.ACCEPT_LANGUAGE,
						LocaleUtil.toW3cLanguageId(locale));
				}
			};

		mockHttpServletRequest.setMethod(method.toString());

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Filter filter = null;

		try {
			filter = _contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					_mockResource));

			Assert.assertNotNull(filter);
		}
		catch (Exception exception) {
			Assert.assertEquals(
				NotAcceptableException.class, exception.getClass());
			Assert.assertEquals(
				"No locales match the accepted languages: " +
					locale.toLanguageTag(),
				exception.getMessage());

			Assert.assertNull(filter);
		}
	}

	private ContextProvider<Filter> _contextProvider;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFilterFeature"
	)
	private Feature _feature;

	private MockResource _mockResource;
	private ServiceRegistration<EntityModelResource> _serviceRegistration;

}