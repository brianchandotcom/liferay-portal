/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot3.service;

import java.net.URI;

import java.util.Collections;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

import org.springframework.web.reactive.function.client.WebClientResponseException;

/**
 * @author Allen Ziegenfus
 */
public class BaseServiceTest {

	@BeforeClass
	public static void setUpClass() {
		_clientAndServer = ClientAndServer.startClientAndServer(0);
		_port = _clientAndServer.getPort();
	}

	@AfterClass
	public static void tearDownClass() {
		_clientAndServer.stop();
	}

	@Test
	public void testEmptyBodyErrorResponseThrows() {
		new MockServerClient(
			"localhost", _port
		).when(
			HttpRequest.request(
			).withMethod(
				"GET"
			).withPath(
				"/empty-error"
			),
			Times.unlimited()
		).respond(
			HttpResponse.response(
			).withStatusCode(
				403
			)
		);

		try {
			TestService testService = new TestService();

			testService.doGet(
				URI.create("http://localhost:" + _port + "/empty-error"));

			Assert.fail("Expected WebClientResponseException");
		}
		catch (WebClientResponseException webClientResponseException) {
			Assert.assertEquals(
				403,
				webClientResponseException.getStatusCode(
				).value());
		}
	}

	private static ClientAndServer _clientAndServer;
	private static int _port;

	private static class TestService extends BaseService {

		public String doGet(URI uri) {
			return get(Collections.emptyMap(), uri);
		}

	}

}