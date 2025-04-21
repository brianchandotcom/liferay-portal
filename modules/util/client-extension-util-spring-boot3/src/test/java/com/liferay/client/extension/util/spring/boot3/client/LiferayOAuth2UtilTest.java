/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.client.extension.util.spring.boot3.client;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import org.mockserver.client.server.MockServerClient;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.matchers.Times;
import org.mockserver.model.Header;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;

/**
 * @author Gregory Amerson
 */
public class LiferayOAuth2UtilTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_clientAndServer = ClientAndServer.startClientAndServer(63636);
	}

	@AfterClass
	public static void tearDownClass() {
		_clientAndServer.stop();
	}

	@Test
	public void testGetClientIdOnly() {
		new MockServerClient(
			"localhost", 63636
		).when(
			HttpRequest.request(
			).withMethod(
				"GET"
			).withPath(
				"/o/oauth2/application"
			).withQueryStringParameter(
				"externalReferenceCode", "foo-able-user-agent"
			),
			Times.unlimited()
		).respond(
			HttpResponse.response(
			).withBody(
				"{\"client_id\": \"123456789\"}"
			).withHeader(
				new Header("Content-Type", "application/json")
			).withStatusCode(
				200
			)
		);

		Assert.assertEquals(
			"123456789",
			LiferayOAuth2Util.getClientId(
				"foo-able-user-agent", "localhost:63636", "http"));
		Assert.assertNull(
			"Expected homePageURL to be null",
			LiferayOAuth2Util.getHomePageURL(
				"foo-able-user-agent", "localhost:63636", "http"));
	}

	@Test
	public void testGetHomePageURL() {
		new MockServerClient(
			"localhost", 63636
		).when(
			HttpRequest.request(
			).withMethod(
				"GET"
			).withPath(
				"/o/oauth2/application"
			).withQueryStringParameter(
				"externalReferenceCode", "foo-able-user-agent"
			),
			Times.unlimited()
		).respond(
			HttpResponse.response(
			).withBody(
				"{\"client_id\": \"123456789\", \"homePageURL\": " +
					"\"http://example.com/home\", \"unknown\": \"foo\"}"
			).withHeader(
				new Header("Content-Type", "application/json")
			).withStatusCode(
				200
			)
		);

		Assert.assertEquals(
			"123456789",
			LiferayOAuth2Util.getClientId(
				"foo-able-user-agent", "localhost:63636", "http"));
		Assert.assertEquals(
			"http://example.com/home",
			LiferayOAuth2Util.getHomePageURL(
				"foo-able-user-agent", "localhost:63636", "http"));
	}

	private static ClientAndServer _clientAndServer;

}