/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.jaxrs.context.provider.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.portal.configuration.test.util.CompanyConfigurationTemporarySwapper;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockFeature;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockMessage;
import com.liferay.portal.vulcan.internal.jaxrs.context.provider.test.util.MockResource;
import com.liferay.portal.vulcan.pagination.InvalidPaginationException;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;

import javax.ws.rs.core.Feature;

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
 * @author Alberto Javier Moreno lage
 */
@RunWith(Arquillian.class)
public class PaginationContextProviderTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		MockFeature mockFeature = new MockFeature(_feature);

		_contextProvider = (ContextProvider<Pagination>)mockFeature.getObject(
			"com.liferay.portal.vulcan.internal.jaxrs.context.provider." +
				"PaginationContextProvider");

		Bundle bundle = FrameworkUtil.getBundle(
			PaginationContextProviderTest.class);

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
	public void tearDown() throws Exception {
		_serviceRegistration.unregister();
	}

	@Test
	public void testCreateContextOverPageSizeLimit() throws Exception {
		_modifyPageSizeLimitConfiguration(20);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("pageSize", "21");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(20, pagination.getPageSize());
	}

	@Test
	public void testCreateContextUnderPageSizeLimit() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "1");
					addParameter("pageSize", "19");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(19, pagination.getPageSize());
	}

	@Test
	public void testCreateContextWithNegativePageParameter() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "-1");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		_assertException(
			"Page -1 is not a number greater than or equal to 1",
			() -> _contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					_mockResource)));
	}

	@Test
	public void testCreateContextWithNegativePageSizeAndPageSizeLimit()
		throws Exception {

		_modifyPageSizeLimitConfiguration(30);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("pageSize", "-1");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(30, pagination.getPageSize());
	}

	@Test
	public void testCreateContextWithNullPaginationAndPageSizeLimit()
		throws Exception {

		_modifyPageSizeLimitConfiguration(10);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(10, pagination.getPageSize());
	}

	@Test
	public void testCreateContextWithNullPaginationParameters()
		throws Exception {

		_modifyPageSizeLimitConfiguration(0);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(20, pagination.getPageSize());
	}

	@Test
	public void testCreateContextWithPageParameterZero() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "0");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		_assertException(
			"Page 0 is not a number greater than or equal to 1",
			() -> _contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					_mockResource)));
	}

	@Test
	public void testCreateContextWithPageSizeEqualsPageSizeLimit()
		throws Exception {

		_modifyPageSizeLimitConfiguration(20);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "1");
					addParameter("pageSize", "20");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(20, pagination.getPageSize());
	}

	@Test
	public void testCreateContextWithPageSizeZeroAndPageSizeLimit()
		throws Exception {

		_modifyPageSizeLimitConfiguration(30);

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("pageSize", "0");
				}
			};

		Class<? extends MockResource> clazz = _mockResource.getClass();

		Pagination pagination = _contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				_mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(30, pagination.getPageSize());
	}

	private void _assertException(
		String expectedMessage,
		UnsafeRunnable<? extends Exception> unsafeRunnable) {

		try {
			unsafeRunnable.run();
			Assert.fail();
		}
		catch (InvalidPaginationException invalidPaginationException) {
			Assert.assertEquals(
				expectedMessage, invalidPaginationException.getMessage());
		}
		catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

	private void _modifyPageSizeLimitConfiguration(int pageSize)
		throws Exception {

		new CompanyConfigurationTemporarySwapper(
			TestPropsValues.getCompanyId(), _CONFIGURATION_SERVICE_PID,
			HashMapDictionaryBuilder.<String, Object>put(
				"pageSizeLimit", pageSize
			).build());
	}

	private static final String _CONFIGURATION_SERVICE_PID =
		"com.liferay.portal.vulcan.internal.configuration." +
			"HeadlessAPICompanyConfiguration";

	private ContextProvider<Pagination> _contextProvider;

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFeature"
	)
	private Feature _feature;

	private MockResource _mockResource;
	private ServiceRegistration<EntityModelResource> _serviceRegistration;

}