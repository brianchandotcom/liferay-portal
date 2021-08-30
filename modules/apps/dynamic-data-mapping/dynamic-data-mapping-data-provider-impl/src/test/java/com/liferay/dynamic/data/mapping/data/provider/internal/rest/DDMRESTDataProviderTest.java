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
import com.liferay.portal.kernel.util.StringUtil;
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
@PrepareForTest(ResourceBundleUtil.class)
@RunWith(PowerMockRunner.class)
public class DDMRESTDataProviderTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		_setUpHtmlUtil();
		_setUpJSONFactoryUtil();
		_setUpLanguageUtil();
		_setUpPortalUtil();
		_setUpResourceBundleUtil();

		_ddmRESTDataProvider = spy(new DDMRESTDataProvider());
	}

	@Test
	public void testBuildURL() {
		Assert.assertEquals(
			"http://someservice.com/api/countries/1/regions",
			_ddmRESTDataProvider.buildURL(
				_createDDMDataProviderRequest(),
				_createDDMRESTDataProviderSettings()));
	}

	@Test
	public void testDoGetData() throws Exception {
		_mockDDMDataProviderInstanceService(1L, StringPool.BLANK);

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings =
			_createDDMRESTDataProviderSettings();

		_mockDDMDataProviderInstanceSettings(ddmRESTDataProviderSettings);

		HttpResponse httpResponse = _mockHttpResponse("{}");

		HttpRequest httpRequest = _mockHttpRequest(httpResponse);

		PortalCache portalCache = spy(mock(PortalCache.class));

		_mockMultiVMPool(portalCache);

		_createDDMDataProviderResponse("1");

		ArgumentCaptor<String> passwordArgumentCaptor = ArgumentCaptor.forClass(
			String.class);
		ArgumentCaptor<String> userNameArgumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			httpRequest, Mockito.times(1)
		).basicAuthentication(
			userNameArgumentCaptor.capture(), passwordArgumentCaptor.capture()
		);

		Assert.assertEquals(
			ddmRESTDataProviderSettings.password(),
			passwordArgumentCaptor.getValue());
		Assert.assertEquals(
			ddmRESTDataProviderSettings.username(),
			userNameArgumentCaptor.getValue());

		Mockito.verify(
			httpRequest, Mockito.times(1)
		).send();

		Mockito.verify(
			httpResponse, Mockito.times(1)
		).bodyText();

		Mockito.verify(
			portalCache, Mockito.times(1)
		).put(
			Matchers.any(Serializable.class), Matchers.any()
		);
	}

	@Test
	public void testDoGetDataCacheable() throws Exception {
		_mockDDMDataProviderInstanceService(2L, StringPool.BLANK);
		_mockDDMDataProviderInstanceSettings(
			_createDDMRESTDataProviderSettings());
		_mockMultiVMPool(_mockPortalCache());

		DDMDataProviderResponse ddmDataProviderResponse =
			_createDDMDataProviderResponse("2");

		Optional<String> optional = ddmDataProviderResponse.getOutputOptional(
			"output", String.class);

		Assert.assertTrue(optional.isPresent());

		Assert.assertEquals("test", optional.get());
	}

	@Test
	public void testDoGetDataServiceUnavailable() throws Exception {
		_mockDDMDataProviderInstanceService(0L, "id");

		DDMDataProviderResponse ddmDataProviderResponse =
			_createDDMDataProviderResponse("id");

		Assert.assertEquals(
			DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE,
			ddmDataProviderResponse.getStatus());
	}

	@Test
	public void testDoGetDataWithBOM() throws Exception {
		_mockDDMDataProviderInstanceService(1L, StringPool.BLANK);

		String outputParameterId = StringUtil.randomString();

		_mockDDMDataProviderInstanceSettings(
			_createSettingsWithOutputParameter(
				outputParameterId, "output", false, ".output", "list"));

		_mockHttpRequest(_mockHttpResponse("ï»¿[{output : \"value\"}]"));
		_mockMultiVMPool(spy(mock(PortalCache.class)));

		DDMDataProviderResponse ddmDataProviderResponse =
			_createDDMDataProviderResponse("1");

		Optional<List<String>> outputOptional =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertTrue(outputOptional.isPresent());

		List<String> outputs = outputOptional.get();

		Assert.assertFalse(outputs.isEmpty());
	}

	@Test
	public void testFetchDDMDataProviderInstanceNotFound1() throws Exception {
		_mockDDMDataProviderInstanceService(0L, "id");

		Optional<DDMDataProviderInstance> optional =
			_ddmRESTDataProvider.fetchDDMDataProviderInstance("id");

		Assert.assertFalse(optional.isPresent());
	}

	@Test
	public void testFetchDDMDataProviderInstanceNotFound2() throws Exception {
		_mockDDMDataProviderInstanceService(1L, "1");

		Optional<DDMDataProviderInstance> optional =
			_ddmRESTDataProvider.fetchDDMDataProviderInstance("1");

		Assert.assertTrue(optional.isPresent());
	}

	@Test
	public void testGetCacheKey() {
		HttpRequest httpRequest = spy(mock(HttpRequest.class));

		_ddmRESTDataProvider.getCacheKey(httpRequest);

		Mockito.verify(
			httpRequest, Mockito.times(1)
		).url();
	}

	@Test
	public void testGetDataCatchConnectException() throws Exception {
		_mockDDMDataProviderInstanceService(3L, StringPool.BLANK);
		_mockDDMDataProviderInstanceSettings(
			_createDDMRESTDataProviderSettings());

		when(
			_ddmRESTDataProvider.getHttpRequest(Matchers.anyString())
		).thenThrow(
			new HttpException(new ConnectException())
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.getData(
				builder.withDDMDataProviderId(
					"3"
				).build());

		Assert.assertEquals(
			DDMDataProviderResponseStatus.SERVICE_UNAVAILABLE,
			ddmDataProviderResponse.getStatus());
	}

	@Test(expected = DDMDataProviderException.class)
	public void testGetDataCatchException() throws Exception {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		_ddmRESTDataProvider.getData(
			builder.withParameter(
				"filterParameterValue", "brazil"
			).withParameter(
				"paginationStart", "1"
			).withParameter(
				"paginationEnd", "10"
			).build());
	}

	@Test(expected = DDMDataProviderException.class)
	public void testGetDataCatchHttpException() throws Exception {
		_mockDDMDataProviderInstanceService(4L, StringPool.BLANK);
		_mockDDMDataProviderInstanceSettings(
			_createDDMRESTDataProviderSettings());

		when(
			_ddmRESTDataProvider.getHttpRequest(Matchers.anyString())
		).thenThrow(
			new HttpException(new Exception())
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		_ddmRESTDataProvider.getData(
			builder.withDDMDataProviderId(
				"4"
			).build());
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
	public void testListOutputWithoutPagination() {
		DocumentContext documentContext = _mockDocumentContext(
			new ArrayList() {
				{
					add("5");
					add("6");
					add("7");
					add("8");
					add("9");
				}
			},
			new ArrayList() {
				{
					add("Rio de Janeiro");
					add("São Paulo");
					add("Sergipe");
					add("Alagoas");
					add("Amazonas");
				}
			});

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		String outputParameterId = StringUtil.randomString();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, builder.build(),
				_createSettingsWithOutputParameter(
					outputParameterId, "list output", false, "value;key",
					"list"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertEquals(
			new ArrayList<KeyValuePair>() {
				{
					add(new KeyValuePair("5", "Rio de Janeiro"));
					add(new KeyValuePair("6", "São Paulo"));
					add(new KeyValuePair("7", "Sergipe"));
					add(new KeyValuePair("8", "Alagoas"));
					add(new KeyValuePair("9", "Amazonas"));
				}
			},
			optional.get());
	}

	@Test
	public void testListOutputWithPagination() {
		DocumentContext documentContext = _mockDocumentContext(
			new ArrayList() {
				{
					add("1");
					add("2");
					add("3");
					add("4");
				}
			},
			new ArrayList() {
				{
					add("Pernambuco");
					add("Paraiba");
					add("Ceara");
					add("Rio Grande do Norte");
				}
			});

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		String outputParameterId = StringUtil.randomString();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext,
				builder.withParameter(
					"paginationStart", "0"
				).withParameter(
					"paginationEnd", "3"
				).build(),
				_createSettingsWithOutputParameter(
					outputParameterId, "list output", true, "value;key",
					"list"));

		Optional<List<KeyValuePair>> optional =
			ddmDataProviderResponse.getOutputOptional(
				outputParameterId, List.class);

		Assert.assertEquals(
			new ArrayList<KeyValuePair>() {
				{
					add(new KeyValuePair("1", "Pernambuco"));
					add(new KeyValuePair("2", "Paraiba"));
					add(new KeyValuePair("3", "Ceara"));
				}
			},
			optional.get());
	}

	@Test
	public void testNormalizePath() {
		Assert.assertEquals(
			".root", _ddmRESTDataProvider.normalizePath("root"));
	}

	@Test
	public void testNormalizePathDollar() {
		Assert.assertEquals(
			"$contacts", _ddmRESTDataProvider.normalizePath("$contacts"));
	}

	@Test
	public void testNormalizePathPeriod() {
		Assert.assertEquals(
			".path", _ddmRESTDataProvider.normalizePath(".path"));
	}

	@Test
	public void testNumberOutput() {
		DocumentContext documentContext = mock(DocumentContext.class);

		when(
			documentContext.read(".numberProp", Number.class)
		).thenReturn(
			1
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		String outputParameterId = StringUtil.randomString();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, builder.build(),
				_createSettingsWithOutputParameter(
					outputParameterId, "number output", false, "numberProp",
					"number"));

		Optional<Number> optional = ddmDataProviderResponse.getOutputOptional(
			outputParameterId, Number.class);

		Assert.assertEquals(1, optional.get());
	}

	@Test
	public void testSetMultiVMPool() {
		MultiVMPool multiVMPool = spy(mock(MultiVMPool.class));

		_ddmRESTDataProvider.setMultiVMPool(multiVMPool);

		ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(
			String.class);

		Mockito.verify(
			multiVMPool, Mockito.times(1)
		).getPortalCache(
			argumentCaptor.capture()
		);

		Assert.assertEquals(
			DDMRESTDataProvider.class.getName(), argumentCaptor.getValue());
	}

	@Test
	public void testSetRequestParameters() {
		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		DDMDataProviderRequest ddmDataProviderRequest = builder.withParameter(
			"filterParameterValue", "brazil"
		).withParameter(
			"paginationStart", "1"
		).withParameter(
			"paginationEnd", "10"
		).build();

		DDMRESTDataProviderSettings ddmRESTDataProviderSettings = mock(
			DDMRESTDataProviderSettings.class);

		when(
			ddmRESTDataProviderSettings.filterable()
		).thenReturn(
			true
		);

		when(
			ddmRESTDataProviderSettings.filterParameterName()
		).thenReturn(
			"country"
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
			ddmRESTDataProviderSettings.paginationStartParameterName()
		).thenReturn(
			"start"
		);

		when(
			ddmRESTDataProviderSettings.paginationEndParameterName()
		).thenReturn(
			"end"
		);

		when(
			ddmRESTDataProviderSettings.url()
		).thenReturn(
			"http://liferay.com/api"
		);

		HttpRequest httpRequest = spy(mock(HttpRequest.class));

		_ddmRESTDataProvider.setRequestParameters(
			ddmDataProviderRequest, ddmRESTDataProviderSettings, httpRequest);

		ArgumentCaptor<String> name = ArgumentCaptor.forClass(String.class);
		ArgumentCaptor<String> value = ArgumentCaptor.forClass(String.class);

		Mockito.verify(
			httpRequest, Mockito.times(3)
		).query(
			name.capture(), value.capture()
		);

		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("country");
					add("start");
					add("end");
				}
			},
			name.getAllValues());
		Assert.assertEquals(
			new ArrayList<String>() {
				{
					add("brazil");
					add("1");
					add("10");
				}
			},
			value.getAllValues());
	}

	@Test
	public void testTextOutput() {
		DocumentContext documentContext = mock(DocumentContext.class);

		when(
			documentContext.read(".textProp", String.class)
		).thenReturn(
			"brazil"
		);

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		String outputParameterId = StringUtil.randomString();

		DDMDataProviderResponse ddmDataProviderResponse =
			_ddmRESTDataProvider.createDDMDataProviderResponse(
				documentContext, builder.build(),
				_createSettingsWithOutputParameter(
					outputParameterId, "text output", false, "textProp",
					"text"));

		Optional<String> optional = ddmDataProviderResponse.getOutputOptional(
			outputParameterId, String.class);

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

	private DDMDataProviderResponse _createDDMDataProviderResponse(
			String ddmDataProviderId)
		throws Exception {

		DDMDataProviderRequest.Builder builder =
			DDMDataProviderRequest.Builder.newBuilder();

		return _ddmRESTDataProvider.doGetData(
			builder.withDDMDataProviderId(
				ddmDataProviderId
			).build());
	}

	private DDMRESTDataProviderSettings _createDDMRESTDataProviderSettings() {
		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			DDMFormFactory.create(DDMRESTDataProviderSettings.class));

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

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel", "Country Id"));
		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "countryId"));
		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"number\"]"));

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		inputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameters", StringPool.BLANK);

		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterLabel", "Region Name"));
		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterName", "regionName"));
		inputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"inputParameterType", "[\"text\"]"));

		ddmFormValues.addDDMFormFieldValue(inputParameters);

		return DDMFormInstanceFactory.create(
			DDMRESTDataProviderSettings.class, ddmFormValues);
	}

	private DDMRESTDataProviderSettings _createSettingsWithOutputParameter(
		String id, String name, boolean pagination, String path, String type) {

		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			DDMFormFactory.create(DDMRESTDataProviderSettings.class));

		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"pagination", Boolean.toString(pagination)));
		ddmFormValues.addDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"url", "http://someservice.com/api"));

		DDMFormFieldValue outputParameters =
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameters", StringPool.BLANK);

		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterId", id));
		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterName", name));
		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterPath", path));
		outputParameters.addNestedDDMFormFieldValue(
			DDMFormValuesTestUtil.createUnlocalizedDDMFormFieldValue(
				"outputParameterType", String.format("[\"%s\"]", type)));

		ddmFormValues.addDDMFormFieldValue(outputParameters);

		return DDMFormInstanceFactory.create(
			DDMRESTDataProviderSettings.class, ddmFormValues);
	}

	private void _mockDDMDataProviderInstanceService(
			long ddmDataProviderInstanceId, String uuid)
		throws Exception {

		DDMDataProviderInstanceService ddmDataProviderInstanceService = mock(
			DDMDataProviderInstanceService.class);

		DDMDataProviderInstance ddmDataProviderInstance = mock(
			DDMDataProviderInstance.class);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstance(
				ddmDataProviderInstanceId)
		).thenReturn(
			ddmDataProviderInstance
		);

		when(
			ddmDataProviderInstanceService.fetchDataProviderInstanceByUuid(uuid)
		).thenReturn(
			null
		);

		_ddmRESTDataProvider.ddmDataProviderInstanceService =
			ddmDataProviderInstanceService;
	}

	private void _mockDDMDataProviderInstanceSettings(
		DDMRESTDataProviderSettings ddmRESTDataProviderSettings) {

		DDMDataProviderInstanceSettings ddmDataProviderInstanceSettings = mock(
			DDMDataProviderInstanceSettings.class);

		when(
			ddmDataProviderInstanceSettings.getSettings(
				Matchers.any(DDMDataProviderInstance.class), Matchers.any())
		).thenReturn(
			ddmRESTDataProviderSettings
		);

		_ddmRESTDataProvider.ddmDataProviderInstanceSettings =
			ddmDataProviderInstanceSettings;
	}

	private DocumentContext _mockDocumentContext(
		List<String> keys, List<String> values) {

		DocumentContext documentContext = mock(DocumentContext.class);

		when(
			documentContext.read(".key")
		).thenReturn(
			keys
		);

		when(
			documentContext.read(".value", List.class)
		).thenReturn(
			values
		);

		return documentContext;
	}

	private HttpRequest _mockHttpRequest(HttpResponse httpResponse) {
		HttpRequest httpRequest = spy(mock(HttpRequest.class));

		when(
			httpRequest.send()
		).thenReturn(
			httpResponse
		);

		when(
			_ddmRESTDataProvider.getHttpRequest(Matchers.anyString())
		).thenReturn(
			httpRequest
		);

		return httpRequest;
	}

	private HttpResponse _mockHttpResponse(String bodyText) {
		HttpResponse httpResponse = spy(mock(HttpResponse.class));

		when(
			httpResponse.bodyText()
		).thenReturn(
			bodyText
		);

		return httpResponse;
	}

	private void _mockMultiVMPool(PortalCache portalCache) {
		MultiVMPool multiVMPool = mock(MultiVMPool.class);

		when(
			multiVMPool.getPortalCache(DDMRESTDataProvider.class.getName())
		).thenReturn(
			portalCache
		);

		_ddmRESTDataProvider.setMultiVMPool(multiVMPool);
	}

	private PortalCache _mockPortalCache() {
		PortalCache portalCache = spy(mock(PortalCache.class));

		DDMDataProviderResponse.Builder builder =
			DDMDataProviderResponse.Builder.newBuilder();

		when(
			portalCache.get(Matchers.any(Serializable.class))
		).thenReturn(
			builder.withOutput(
				"output", "test"
			).build()
		);

		return portalCache;
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

		languageUtil.setLanguage(mock(Language.class));
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
		mockStatic(ResourceBundleUtil.class);

		when(
			ResourceBundleUtil.getBundle(
				Matchers.anyString(), Matchers.any(Locale.class),
				Matchers.any(ClassLoader.class))
		).thenReturn(
			ResourceBundleUtil.EMPTY_RESOURCE_BUNDLE
		);
	}

	private DDMRESTDataProvider _ddmRESTDataProvider;

}