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

import org.junit.Assert;
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

	@Test
	public void test() throws Exception {
		MockFeature mockFeature = new MockFeature(_feature);

		ContextProvider<Pagination> contextProvider =
			(ContextProvider<Pagination>)mockFeature.getObject(
				"com.liferay.portal.vulcan.internal.jaxrs.context.provider." +
					"PaginationContextProvider");

		Bundle bundle = FrameworkUtil.getBundle(
			PaginationContextProviderTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		MockResource mockResource = new MockResource();

		Class<? extends MockResource> clazz = mockResource.getClass();

		ServiceRegistration<EntityModelResource> serviceRegistration =
			bundleContext.registerService(
				EntityModelResource.class, mockResource,
				HashMapDictionaryBuilder.<String, Object>put(
					"component.name", MockResource.class.getCanonicalName()
				).put(
					"osgi.jaxrs.resource", "true"
				).build());

		// Context over page size limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 20
						).build())) {

			MockHttpServletRequest mockHttpServletRequest =
				new MockHttpServletRequest() {
					{
						addParameter("pageSize", "21");
					}
				};

			Pagination pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(20, pagination.getPageSize());
		}

		// Context under page size limit

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "1");
					addParameter("pageSize", "19");
				}
			};

		Pagination pagination = contextProvider.createContext(
			new MockMessage(
				mockHttpServletRequest,
				clazz.getMethod(MockResource.METHOD_NAME, String.class),
				mockResource));

		Assert.assertEquals(1, pagination.getPage());
		Assert.assertEquals(19, pagination.getPageSize());

		// Context with negative page

		MockHttpServletRequest negativePageMockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "-1");
				}
			};

		_assertException(
			"Page -1 is not a number greater than or equal to 1",
			() -> contextProvider.createContext(
				new MockMessage(
					negativePageMockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource)));

		// Context with page size unlimited and page size limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 30
						).build())) {

			mockHttpServletRequest = new MockHttpServletRequest() {
				{
					addParameter("pageSize", "-1");
				}
			};

			pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(30, pagination.getPageSize());
		}

		// Context with null pagination and page size limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 10
						).build())) {

			mockHttpServletRequest = new MockHttpServletRequest();

			pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(10, pagination.getPageSize());
		}

		// Context with null pagination and unlimited page size limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 0
						).build())) {

			mockHttpServletRequest = new MockHttpServletRequest();

			pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(20, pagination.getPageSize());
		}

		// Context with page parameter zero

		MockHttpServletRequest pageZeroMockHttpServletRequest =
			new MockHttpServletRequest() {
				{
					addParameter("page", "0");
				}
			};

		_assertException(
			"Page 0 is not a number greater than or equal to 1",
			() -> contextProvider.createContext(
				new MockMessage(
					pageZeroMockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource)));

		// Context with page size equal to the limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 20
						).build())) {

			mockHttpServletRequest = new MockHttpServletRequest() {
				{
					addParameter("page", "1");
					addParameter("pageSize", "20");
				}
			};

			pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(20, pagination.getPageSize());
		}

		// Context with page size zero and page size limit

		try (CompanyConfigurationTemporarySwapper
				companyConfigurationTemporarySwapper =
					new CompanyConfigurationTemporarySwapper(
						TestPropsValues.getCompanyId(),
						_CONFIGURATION_SERVICE_PID,
						HashMapDictionaryBuilder.<String, Object>put(
							"pageSizeLimit", 30
						).build())) {

			mockHttpServletRequest = new MockHttpServletRequest() {
				{
					addParameter("pageSize", "0");
				}
			};

			pagination = contextProvider.createContext(
				new MockMessage(
					mockHttpServletRequest,
					clazz.getMethod(MockResource.METHOD_NAME, String.class),
					mockResource));

			Assert.assertEquals(1, pagination.getPage());
			Assert.assertEquals(30, pagination.getPageSize());
		}

		serviceRegistration.unregister();
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

	private static final String _CONFIGURATION_SERVICE_PID =
		"com.liferay.portal.vulcan.internal.configuration." +
			"HeadlessAPICompanyConfiguration";

	@Inject(
		filter = "component.name=com.liferay.portal.vulcan.internal.jaxrs.feature.VulcanFeature"
	)
	private Feature _feature;

}