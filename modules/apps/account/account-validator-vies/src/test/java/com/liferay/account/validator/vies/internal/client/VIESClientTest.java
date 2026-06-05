/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.validator.vies.internal.client;

import com.liferay.account.validator.vies.configuration.VIESAccountEntryValidatorConfiguration;
import com.liferay.portal.configuration.module.configuration.ConfigurationProviderUtil;
import com.liferay.portal.json.JSONFactoryImpl;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;

import java.io.OutputStream;

import java.net.InetSocketAddress;

import java.nio.charset.StandardCharsets;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Crescenzo Rega
 */
public class VIESClientTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();

		jsonFactoryUtil.setJSONFactory(new JSONFactoryImpl());
	}

	@After
	public void tearDown() {
		if (_httpServer != null) {
			_httpServer.stop(0);
		}
	}

	@Test
	public void testCheckVatNumber() throws Exception {
		try (MockedStatic<ConfigurationProviderUtil>
				configurationProviderUtilMockedStatic = Mockito.mockStatic(
					ConfigurationProviderUtil.class)) {

			VIESAccountEntryValidatorConfiguration
				viesAccountEntryValidatorConfiguration = Mockito.mock(
					VIESAccountEntryValidatorConfiguration.class);

			Mockito.when(
				viesAccountEntryValidatorConfiguration.viesEndpointURL()
			).thenReturn(
				"http://localhost:" + _PORT + "/check-vat-number"
			);

			long companyId = RandomTestUtil.randomLong();

			configurationProviderUtilMockedStatic.when(
				() -> ConfigurationProviderUtil.getCompanyConfiguration(
					VIESAccountEntryValidatorConfiguration.class, companyId)
			).thenReturn(
				viesAccountEntryValidatorConfiguration
			);

			VIESClient viesClient = new VIESClient();

			JSONObject jsonObject = viesClient.checkVatNumber(
				companyId, _getRequestJSONObject());

			_assertError("IO_ERROR", jsonObject);

			_startHttpServer(
				JSONUtil.put(
					"actionSucceed", false
				).put(
					"errorWrappers",
					JSONUtil.putAll(JSONUtil.put("error", "MS_UNAVAILABLE"))
				).toString());

			jsonObject = viesClient.checkVatNumber(
				companyId, _getRequestJSONObject());

			_assertError("MS_UNAVAILABLE", jsonObject);

			String countryCode = RandomTestUtil.randomString();
			String vatNumber = RandomTestUtil.randomString();

			_startHttpServer(
				JSONUtil.put(
					"countryCode", countryCode
				).put(
					"valid", true
				).put(
					"vatNumber", vatNumber
				).toString());

			jsonObject = viesClient.checkVatNumber(
				companyId, _getRequestJSONObject());

			Assert.assertEquals(
				countryCode, jsonObject.getString("countryCode"));
			Assert.assertNull(jsonObject.opt("errorWrappers"));
			Assert.assertTrue(jsonObject.getBoolean("valid"));
			Assert.assertEquals(vatNumber, jsonObject.getString("vatNumber"));
		}
	}

	private void _assertError(
		String expectedError, JSONObject responseJSONObject) {

		JSONArray jsonArray = responseJSONObject.getJSONArray("errorWrappers");

		Assert.assertEquals(jsonArray.toString(), 1, jsonArray.length());

		JSONObject jsonObject = jsonArray.getJSONObject(0);

		Assert.assertEquals(expectedError, jsonObject.getString("error"));
	}

	private JSONObject _getRequestJSONObject() {
		return JSONUtil.put(
			"countryCode", "IT"
		).put(
			"vatNumber", RandomTestUtil.randomString()
		);
	}

	private void _startHttpServer(String response) throws Exception {
		if (_httpServer != null) {
			_httpServer.stop(0);
		}

		_httpServer = HttpServer.create(new InetSocketAddress(_PORT), 0);

		HttpContext httpContext = _httpServer.createContext(
			"/check-vat-number");

		httpContext.setHandler(
			httpExchange -> {
				byte[] bytes = response.getBytes(StandardCharsets.UTF_8);

				httpExchange.sendResponseHeaders(200, bytes.length);

				try (OutputStream outputStream =
						httpExchange.getResponseBody()) {

					outputStream.write(bytes);

					outputStream.flush();
				}
			});

		_httpServer.start();
	}

	private static final int _PORT = 4251;

	private HttpServer _httpServer;

}