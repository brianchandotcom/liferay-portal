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

package com.liferay.dynamic.data.mapping.data.provider.internal.servlet.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceLocalServiceUtil;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.skyscreamer.jsonassert.JSONAssert;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class DDMDataProviderPaginatorServletTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		setUpDDMDataProvider();
		setUpDDMDataProviderPaginatorServlet();
	}

	@Ignore
	@Test
	public void testPagination() throws Exception {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_URL_CONFIGURATION_RESOURCE_CLASS_NAME, Level.OFF)) {

			DDMDataProviderInstance ddmDataProviderInstance =
				createDDMDataProviderInstance();

			Map<String, String> params = createRequestParametersMap(
				ddmDataProviderInstance);

			HttpServletRequest httpServletRequest = createHttpServletRequest(
				params, 0, 10);

			JSONArray responseJSONArray = createJSONArray(
				executeDDMDataProviderPaginatorServlet(httpServletRequest));

			Assert.assertEquals(10, responseJSONArray.length());
		}
	}

	@Ignore
	@Test
	public void testPaginationToDifferentPagesShouldBeDifferent()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_URL_CONFIGURATION_RESOURCE_CLASS_NAME, Level.OFF)) {

			DDMDataProviderInstance ddmDataProviderInstance =
				createDDMDataProviderInstance();

			Map<String, String> params = createRequestParametersMap(
				ddmDataProviderInstance);

			HttpServletRequest httpServletRequest = createHttpServletRequest(
				params, 0, 1);

			JSONArray responseJSONArray1 = createJSONArray(
				executeDDMDataProviderPaginatorServlet(httpServletRequest));

			httpServletRequest = createHttpServletRequest(params, 1, 2);

			JSONArray responseJSONArray2 = createJSONArray(
				executeDDMDataProviderPaginatorServlet(httpServletRequest));

			JSONAssert.assertNotEquals(
				responseJSONArray1.toJSONString(),
				responseJSONArray2.toJSONString(), false);
		}
	}

	@Ignore
	@Test
	public void testPaginationToSamePageShouldBeEquals() throws Exception {
		DDMDataProviderInstance ddmDataProviderInstance =
			createDDMDataProviderInstance();

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_URL_CONFIGURATION_RESOURCE_CLASS_NAME, Level.OFF)) {

			Map<String, String> params = createRequestParametersMap(
				ddmDataProviderInstance);

			HttpServletRequest httpServletRequest = createHttpServletRequest(
				params, 0, 1);

			JSONArray responseJSONArray1 = createJSONArray(
				executeDDMDataProviderPaginatorServlet(httpServletRequest));

			httpServletRequest = createHttpServletRequest(params, 0, 1);

			JSONArray responseJSONArray2 = createJSONArray(
				executeDDMDataProviderPaginatorServlet(httpServletRequest));

			Assert.assertEquals(1, responseJSONArray1.length());

			Assert.assertEquals(
				responseJSONArray1.length(), responseJSONArray2.length());

			JSONAssert.assertEquals(
				responseJSONArray1.toJSONString(),
				responseJSONArray2.toJSONString(), false);
		}
	}

	@Test
	public void testPaginationWithInvalidPaginationParametersShouldFail()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_URL_CONFIGURATION_RESOURCE_CLASS_NAME, Level.OFF)) {

			DDMDataProviderInstance ddmDataProviderInstance =
				createDDMDataProviderInstance();

			Map<String, String> params = createRequestParametersMap(
				ddmDataProviderInstance);

			HttpServletRequest httpServletRequest = createHttpServletRequest(
				params, 1, 1);

			MockHttpServletResponse mockHttpServletResponse =
				executeDDMDataProviderPaginatorServlet(httpServletRequest);

			Assert.assertEquals(
				MockHttpServletResponse.SC_BAD_REQUEST,
				mockHttpServletResponse.getStatus());
		}
	}

	@Test
	public void testPaginationWithoutRequiredParametersShouldFail()
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_URL_CONFIGURATION_RESOURCE_CLASS_NAME, Level.OFF)) {

			HttpServletRequest httpServletRequest = createHttpServletRequest(
				new HashMap<String, String>(), 0, 1);

			MockHttpServletResponse mockHttpServletResponse =
				executeDDMDataProviderPaginatorServlet(httpServletRequest);

			Assert.assertEquals(
				MockHttpServletResponse.SC_BAD_REQUEST,
				mockHttpServletResponse.getStatus());
		}
	}

	protected DDMDataProviderInstance createDDMDataProviderInstance()
		throws Exception {

		DDMForm ddmForm = DDMFormFactory.create(
			_restDDMDataProvider.getSettings());

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"pagination", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "test"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"paginationEndParameterName", "end"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"paginationStartParameterName", "start"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://localhost:8080/api/jsonws/country/get-countries"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "test@liferay.com"));

		ddmFormValues.addDDMFormFieldValue(
			createOutputParameter(
				"Countries", "nameCurrentValue", "[\"list\"]"));

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.getSiteDefault(), "Data provider");

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				group, TestPropsValues.getUserId());

		return DDMDataProviderInstanceLocalServiceUtil.addDataProviderInstance(
			TestPropsValues.getUserId(), group.getGroupId(), nameMap, nameMap,
			ddmFormValues, "rest", serviceContext);
	}

	protected HttpServletRequest createHttpServletRequest(
		Map<String, String> params, int start, int end) {

		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameters(params);

		mockHttpServletRequest.setParameter("start", String.valueOf(start));
		mockHttpServletRequest.setParameter("end", String.valueOf(end));

		return mockHttpServletRequest;
	}

	protected DDMFormFieldValue createInputParameter(
		String inputParameterName, String inputParameterType,
		String inputParameterRequired) {

		DDMFormFieldValue inputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"inputParameters", null);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", inputParameterName));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", inputParameterType));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterRequired", inputParameterRequired));

		return inputParameters;
	}

	protected JSONArray createJSONArray(
			MockHttpServletResponse mockHttpServletResponse)
		throws Exception {

		return JSONFactoryUtil.createJSONArray(
			mockHttpServletResponse.getContentAsString());
	}

	protected DDMFormFieldValue createOutputParameter(
		String outputParameterName, String outputParameterPath,
		String outputParameterType) {

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createDDMFormFieldValue(
				"outputParameters", null);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", outputParameterName));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", outputParameterPath));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", outputParameterType));

		return outputParameters;
	}

	protected Map<String, String> createRequestParametersMap(
		DDMDataProviderInstance ddmDataProviderInstance) {

		Map<String, String> params = new HashMap<>();

		params.put(
			"dataProviderInstanceUUID", ddmDataProviderInstance.getUuid());

		params.put("outputParameterName", "Countries");

		return params;
	}

	protected MockHttpServletResponse executeDDMDataProviderPaginatorServlet(
			HttpServletRequest request)
		throws Exception {

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		invokeDoPost(request, mockHttpServletResponse);

		return mockHttpServletResponse;
	}

	protected void invokeDoPost(
			HttpServletRequest request, HttpServletResponse response)
		throws Exception {

		Class<?> clazz = _ddmDataProviderPaginatorServlet.getClass();

		Method method = clazz.getDeclaredMethod(
			"doPost", HttpServletRequest.class, HttpServletResponse.class);

		method.setAccessible(true);
		method.invoke(_ddmDataProviderPaginatorServlet, request, response);
	}

	protected void setUpDDMDataProvider() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		DDMDataProvider[] ddmDataProviders = registry.getServices(
			"com.liferay.dynamic.data.mapping.data.provider.DDMDataProvider",
			"(ddm.data.provider.type=rest)");

		_restDDMDataProvider = ddmDataProviders[0];
	}

	protected void setUpDDMDataProviderPaginatorServlet() throws Exception {
		Registry registry = RegistryUtil.getRegistry();

		StringBundler sb = new StringBundler(5);

		sb.append("(&(objectClass=");
		sb.append(Servlet.class.getName());
		sb.append(")(osgi.http.whiteboard.servlet.name=com.liferay.dynamic.");
		sb.append("data.mapping.data.provider.internal.servlet.");
		sb.append("DDMDataProviderPaginatorServlet))");

		Servlet[] servlets = registry.getServices(
			Servlet.class.getName(), sb.toString());

		_ddmDataProviderPaginatorServlet = servlets[0];
	}

	@DeleteAfterTestRun
	protected Group group;

	private static final String _URL_CONFIGURATION_RESOURCE_CLASS_NAME =
		"com.netflix.config.sources.URLConfigurationSource";

	private Servlet _ddmDataProviderPaginatorServlet;
	private DDMDataProvider _restDDMDataProvider;

}