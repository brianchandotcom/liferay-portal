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

package com.liferay.dynamic.data.mapping.data.provider.internal.rest;

import com.jayway.jsonpath.DocumentContext;

import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderException;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInputParametersSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderInstanceSettings;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderRequest;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponse;
import com.liferay.dynamic.data.mapping.data.provider.DDMDataProviderResponseStatus;
import com.liferay.dynamic.data.mapping.model.DDMDataProviderInstance;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.service.DDMDataProviderInstanceService;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.util.DDMFormFactory;
import com.liferay.dynamic.data.mapping.util.DDMFormInstanceFactory;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.cache.MultiVMPool;
import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.KeyValuePair;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.util.HtmlImpl;

import java.io.Serializable;

import java.net.ConnectException;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import jodd.http.HttpException;
import jodd.http.HttpRequest;
import jodd.http.HttpResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.ArgumentCaptor;
import org.mockito.Matchers;
import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Rafael Praxedes
 */
@PrepareForTest({HttpRequest.class, ResourceBundleUtil.class})
@RunWith(PowerMockRunner.class)
public class DDMRESTDataProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpHtmlUtil();
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();

		_ddmRESTDataProvider = new DDMRESTDataProvider();
	}

	@Test
	public void testBuildURL() {
		String url = _ddmRESTDataProvider.buildURL(
			_createDDMDataProviderRequest(),
			_createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			"http://someservice.com/api/countries/1/regions", url);
	}

	@Test
	public void testDoGetData() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(1L)
		).thenReturn(
			ddmDataProviderInstance
		);

		DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings = mock(
			DDMDataProviderInstanceSettings.class);

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createDDMRESTDataProviderSettings();

		when(
			ddmDataProviderInstanceSettings.getSettings(
				Matchers.any(DDMDataProviderInstance.class), Matchers.any())
		).thenReturn(
			ddmRESTDataProviderSettings
		);

		mockStatic(HttpRequest.class);

		HttpRequest httpRequest = mock(HttpRequest.class);

		HttpRequest spyHttpRequest = spy(httpRequest);

		when(
			HttpRequest.get(Matchers.anyString())
		).thenReturn(
			spyHttpRequest
		);

		HttpResponse httpResponse = mock(HttpResponse.class);

		HttpResponse spyHttpResponse = spy(httpResponse);

		when(
			spyHttpRequest.send()
		).thenReturn(
			spyHttpResponse
		);

		when(
			spyHttpResponse.bodyText()
		).thenReturn(
			"{}"
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"1"
			).build();

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;
		_ddmRESTDataProvider.ddmDataProviderInstanceSettings =
			ddmDataProviderInstanceSettings;

		MultiVMPool multiVMPool = mock(MultiVMPool.class);

		PortalCache portalCache = mock(PortalCache.class);

		PortalCache spyPortalCache = spy(portalCache);

		when(
			multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName())
		).thenReturn(
			spyPortalCache
		);

		_ddmRESTDataProvider.setMultiVMPool(multiVMPool);

		_ddmRESTDataProvider.doGetData(ddmDataProviderRequest);

		ArgumentCaptor<String> userNameArgumentCaptor = ArgumentCaptor.forClass(
			String.class);

		ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			spyHttpRequest, Mockito.times(1)
		).basicAuthentication(
			userNameArgumentCaptor.capture(), passwordArgumentCaptor.capture()
		);

		Assert.assertEquals(
			ddmRESTDataProviderSettings.username(),
			userNameArgumentCaptor.getValue());

		Assert.assertEquals(
			ddmRESTDataProviderSettings.password(),
			passwordArgumentCaptor.getValue());

		Mockito.verify(
			spyHttpRequest, Mockito.times(1)
		).send();

		Mockito.verify(
			spyHttpResponse, Mockito.times(1)
		).bodyText();

		Mockito.verify(
			spyPortalCache, Mockito.times(1)
		).put(
			Matchers.any(Serializable.class), Matchers.any()
		);
	}

	@Test
	public void testDoGetDataCacheable() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(2L)
		).thenReturn(
			ddmDataProviderInstance
		);

		DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings = mock(
			DDMDataProviderInstanceSettings.class);

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createDDMRESTDataProviderSettings();

		when(
			ddmDataProviderInstanceSettings.getSettings(
				Matchers.any(DDMDataProviderInstance.class), Matchers.any())
		).thenReturn(
			ddmRESTDataProviderSettings
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"2"
			).build();

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;
		_ddmRESTDataProvider.ddmDataProviderInstanceSettings =
			ddmDataProviderInstanceSettings;

		MultiVMPool multiVMPool = mock(MultiVMPool.class);

		PortalCache portalCache = mock(PortalCache.class);

		when(
			multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName())
		).thenReturn(
			portalCache
		);

		DDMDataProviderResponse.Builder responseBuilder =
			DDMDataProviderResponse.Builder.newBuilder();

		DDMDataProviderResponse expectedDataProviderResponse =
			responseBuilder.withOutput(
				"output", "test"
			).build();

		when(
			portalCache.get(Matchers.any(Serializable.class))
		).thenReturn(
			expectedDataProviderResponse
		);

		_ddmRESTDataProvider.setMultiVMPool(multiVMPool);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.doGetData(ddmDataProviderRequest);

		Optional<String> optional = ddmDataProviderResponse.getOutputOptional(
			"output", String.class);

		Assert.assertTrue(optional.isPresent());

		Assert.assertEquals("test", optional.get());
	}

	@Test
	public void testDoGetDataServiceUnavailable() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid("id")
		).thenReturn(
			null
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"id"
			).build();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.doGetData(ddmDataProviderRequest);

		Assert.assertEquals(
			DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE,
			ddmDataProviderResponse.getStatus());
	}

	@Test
	public void testFetchDDMDataProviderInstanceNotFound1() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid("id")
		).thenReturn(
			null
		);

		Optional<DDMDataProviderInstance> optional =
			_ddmRESTDataProvider.fetchDDMDataProviderInstance("id");

		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void testFetchDDMDataProviderInstanceNotFound2() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid("1")
		).thenReturn(
			null
		);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(1L)
		).thenReturn(
			ddmDataProviderInstance
		);

		Optional<DDMDataProviderInstance> optional =
			_ddmRESTDataProvider.fetchDDMDataProviderInstance("1");

		Assert.assertTrue(optional.isPresent());
	}

	@Test
	public void testGetCacheKey() {
		HttpRequest httpRequest = mock(HttpRequest.class);

		HttpRequest spy = spy(httpRequest);

		_ddmRESTDataProvider.getCacheKey(spy);

		Mockito.verify(
			spy, Mockito.times(1)
		).url();
	}

	@Test
	public void testGetDataCatchConnectException() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(3L)
		).thenReturn(
			ddmDataProviderInstance
		);

		DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings = mock(
			DDMDataProviderInstanceSettings.class);

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createDDMRESTDataProviderSettings();

		when(
			ddmDataProviderInstanceSettings.getSettings(
				Matchers.any(DDMDataProviderInstance.class), Matchers.any())
		).thenReturn(
			ddmRESTDataProviderSettings
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"3"
			).build();

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;
		_ddmRESTDataProvider.ddmDataProviderInstanceSettings =
			ddmDataProviderInstanceSettings;

		mockStatic(HttpRequest.class);

		HttpException httpException = new HttpException(new ConnectException());

		when(
			HttpRequest.get(Matchers.anyString())
		).thenThrow(
			httpException
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.getData(ddmDataProviderRequest);

		Assert.assertEquals(
			DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE,
			ddmDataProviderResponse.getStatus());
	}

	@Test(expected = DDMDataProviderException.class)
	public void testGetDataCatchException() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"filterParameterValue", "brazil"
		).withParameter(
			"paginationStart", "1"
		).withParameter(
			"paginationEnd", "10"
		).build();

		_ddmRESTDataProvider.getData(ddmDataProviderRequest);
	}

	@Test(expected = DDMDataProviderException.class)
	public void testGetDataCatchHttpException() throws Exception {
		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(4L)
		).thenReturn(
			ddmDataProviderInstance
		);

		DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings = mock(
			DDMDataProviderInstanceSettings.class);

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createDDMRESTDataProviderSettings();

		when(
			ddmDataProviderInstanceSettings.getSettings(
				Matchers.any(DDMDataProviderInstance.class), Matchers.any())
		).thenReturn(
			ddmRESTDataProviderSettings
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest =
			builder.withDDMDataProviderId(
				"4"
			).build();

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;
		_ddmRESTDataProvider.ddmDataProviderInstanceSettings =
			ddmDataProviderInstanceSettings;

		mockStatic(HttpRequest.class);

		HttpException httpException = new HttpException(new Exception());

		when(
			HttpRequest.get(Matchers.anyString())
		).thenThrow(
			httpException
		);

		_ddmRESTDataProvider.getData(ddmDataProviderRequest);
	}

	@Test
	public void testGetPathParameters() {
		Map<String, String> pathParameters =
			_ddmRESTDataProvider.getPathParameters(
				_createDDMDataProviderRequest(),
				_createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			pathParameters.toString(), 1, pathParameters.size());
		Assert.assertEquals("1", pathParameters.get("countryId"));
	}

	@Test
	public void testGetQueryParameters() {
		Map<String, String> queryParameters =
			_ddmRESTDataProvider.getQueryParameters(
				_createDDMDataProviderRequest(),
				_createDDMRESTDataProviderSettings());

		Assert.assertEquals(
			queryParameters.toString(), 1, queryParameters.size());
		Assert.assertEquals("Region", queryParameters.get("regionName"));
	}

	@Test
	public void testGetSettings() {
		Class<?> settings = _ddmRESTDataProvider.getSettings();

		Assert.assertEquals(
			DDMRESTDataProviderSettings.class.getCanonicalName(),
			settings.getCanonicalName());
	}

	@Test
	public void testListOutputWithoutPagination() {
		DocumentContext documentContext = mock(DocumentContext.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createSettingsWithOutputParameter(
				"list output", "value;key", "list", false);

		when(
			documentContext.read(".value", List.class)
		).thenReturn(
			new ArrayList() {
				{
					add("Rio de Janeiro");
					add("São Paulo");
					add("Sergipe");
					add("Alagoas");
					add("Amazonas");
				}
			}
		);

		when(
			documentContext.read(".key")
		).thenReturn(
			new ArrayList() {
				{
					add("5");
					add("6");
					add("7");
					add("8");
					add("9");
				}
			}
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, ddmDataProviderRequest,
				ddmRESTDataProviderSettings);

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutputOptional(
				"list output", List.class);

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("5", "Rio de Janeiro"));
				add(new KeyValuePair("6", "São Paulo"));
				add(new KeyValuePair("7", "Sergipe"));
				add(new KeyValuePair("8", "Alagoas"));
				add(new KeyValuePair("9", "Amazonas"));
			}
		};

		Assert.assertEquals(
			keyValuePairs.toString(), keyValuePairs, optional.get());
	}

	@Test
	public void testListOutputWithPagination() {
		DocumentContext documentContext = mock(DocumentContext.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"paginationStart", "0"
		).withParameter(
			"paginationEnd", "3"
		).build();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createSettingsWithOutputParameter(
				"list output", "value;key", "list", true);

		when(
			documentContext.read(".value", List.class)
		).thenReturn(
			new ArrayList() {
				{
					add("Pernambuco");
					add("Paraiba");
					add("Ceara");
					add("Rio Grande do Norte");
				}
			}
		);

		when(
			documentContext.read(".key")
		).thenReturn(
			new ArrayList() {
				{
					add("1");
					add("2");
					add("3");
					add("4");
				}
			}
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, ddmDataProviderRequest,
				ddmRESTDataProviderSettings);

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutputOptional(
				"list output", List.class);

		List<KeyValuePair> keyValuePairs = new ArrayList() {
			{
				add(new KeyValuePair("1", "Pernambuco"));
				add(new KeyValuePair("2", "Paraiba"));
				add(new KeyValuePair("3", "Ceara"));
			}
		};

		Assert.assertEquals(
			keyValuePairs.toString(), keyValuePairs, optional.get());
	}

	@Test
	public void testNormalizePath() {
		String normalizePath = _ddmRESTDataProvider.normalizePath("root");

		Assert.assertEquals(".root", normalizePath);
	}

	@Test
	public void testNormalizePathDollar() {
		String normalizePath = _ddmRESTDataProvider.normalizePath("$contacts");

		Assert.assertEquals("$contacts", normalizePath);
	}

	@Test
	public void testNormalizePathPeriod() {
		String normalizePath = _ddmRESTDataProvider.normalizePath(".path");

		Assert.assertEquals(".path", normalizePath);
	}

	@Test
	public void testNumberOutput() {
		DocumentContext documentContext = mock(DocumentContext.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createSettingsWithOutputParameter(
				"number output", "numberProp", "number", false);

		when(
			documentContext.read(".numberProp", Number.class)
		).thenReturn(
			1
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, ddmDataProviderRequest,
				ddmRESTDataProviderSettings);

		Optional<Number> optional = ddmDataProviderResponse.getOutputOptional(
			"number output", Number.class);

		Assert.assertEquals(1, optional.get());
	}

	@Test
	public void testSetMultiVMPool() {
		MultiVMPool multiVMPool = mock(MultiVMPool.class);

		MultiVMPool spy = spy(multiVMPool);

		_ddmRESTDataProvider.setMultiVMPool(spy);

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			spy, Mockito.times(1)
		).getPortalCache(
			argumentCaptor.capture()
		);

		Assert.assertEquals(
			DDMRESTDataProvider.class.getName(), argumentCaptor.getValue());
	}

	@Test
	public void testSetRequestParameters() {
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings = mock(
			DDMRESTDataProviderSettings.class);

		when(
			ddmRESTDataProviderSettings.url()
		).thenReturn(
			"http://liferay.com/api"
		);

		when(
			ddmRESTDataProviderSettings.filterable()
		).thenReturn(
			true
		);

		when(
			ddmRESTDataProviderSettings.inputParameters()
		).thenReturn(
			new DDMDataProviderInputParametersSettings[0]
		);

		when(
			ddmRESTDataProviderSettings.pagination()
		).thenReturn(
			true
		);

		when(
			ddmRESTDataProviderSettings.filterParameterName()
		).thenReturn(
			"country"
		);

		when(
			ddmRESTDataProviderSettings.paginationStartParameterName()
		).thenReturn(
			"start"
		);

		when(
			ddmRESTDataProviderSettings.paginationEndParameterName()
		).thenReturn(
			"end"
		);

		HttpRequest httpRequest = mock(HttpRequest.class);

		HttpRequest spy = spy(httpRequest);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"filterParameterValue", "brazil"
		).withParameter(
			"paginationStart", "1"
		).withParameter(
			"paginationEnd", "10"
		).build();

		_ddmRESTDataProvider.setRequestParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings, spy);

		ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> value = ArgumentCaptor.forClass(String.class);

		Mockito.verify(
			spy, Mockito.times(3)
		).query(
			name.capture(), value.capture()
		);

		List<String> names = new ArrayList() {
			{
				add("country");
				add("start");
				add("end");
			}
		};

		Assert.assertEquals(names, name.getAllValues());

		List<String> values = new ArrayList() {
			{
				add("brazil");
				add("1");
				add("10");
			}
		};

		Assert.assertEquals(values, value.getAllValues());
	}

	@Test
	public void testTextOutput() {
		DocumentContext documentContext = mock(DocumentContext.class);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.build();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createSettingsWithOutputParameter(
				"text output", "textProp", "text", false);

		when(
			documentContext.read(".textProp", String.class)
		).thenReturn(
			"brazil"
		);

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, ddmDataProviderRequest,
				ddmRESTDataProviderSettings);

		Optional<String> optional = ddmDataProviderResponse.getOutputOptional(
			"text output", String.class);

		Assert.assertEquals("brazil", optional.get());
	}

	private DDMDataProviderRequest _createDDMDataProviderRequest() {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		return builder.withParameter(
			"countryId", "1"
		).withParameter(
			"regionName", "Region"
		).build();
	}

	private DDMRESTDataProviderSettings _createDDMRESTDataProviderSettings() {
		DDMForm ddmForm = DDMFormFactory.create(
			DDMRESTDataProviderSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"cacheable", Boolean.TRUE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterable", Boolean.FALSE.toString()));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"filterParameterName", StringPool.BLANK));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"password", "1234"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url",
				"http://someservice.com/api/countries/{countryId}/regions"));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"username", "Joe"));

		DDMFormFieldValue inputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel", "Country Id"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "countryId"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"number\"]"));

		inputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel", "Region Name"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "regionName"));

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"text\"]"));

		return DDMFormInstanceFactory.create(
			DDMRESTDataProviderSettings.class, ddmFormValues);
	}

	private DDMRESTDataProviderSettings _createSettingsWithOutputParameter(
		String name, String path, String type, boolean pagination) {

		DDMForm ddmForm = DDMFormFactory.create(
			DDMRESTDataProviderSettings.class);

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", "http://someservice.com/api"));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"pagination", Boolean.toString(pagination)));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", name));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", path));

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", String.format("[\"%s\"]", type)));

		return DDMFormInstanceFactory.create(
			DDMRESTDataProviderSettings.class, ddmFormValues);
	}

	private void _setUpHtmlUtil() {
		HtmlUtil htmlUtil = new HtmlUtil();

		htmlUtil.setHtml(new HtmlImpl());
	}

	private void _setUpJSONFactoryUtil() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	private void _setUpLanguageUtil() {
		LanguageUtil languageUtil = new LanguageUtil();

		Language language = PowerMockito.mock(Language.class);

		languageUtil.setLanguage(language);
	}

	private void _setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		Portal portal = mock(Portal.class);

		ResourceBundle resourceBundle = mock(ResourceBundle.class);

		when(
			portal.getResourceBundle(Matchers.any(Locale.class))
		).thenReturn(
			resourceBundle
		);

		portalUtil.setPortal(portal);
	}

	private void _setUpResourceBundleUtil() {
		PowerMockito.mockStatic(ResourceBundleUtil.class);

		PowerMockito.when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private DDMRESTDataProvider _ddmRESTDataProvider;

}