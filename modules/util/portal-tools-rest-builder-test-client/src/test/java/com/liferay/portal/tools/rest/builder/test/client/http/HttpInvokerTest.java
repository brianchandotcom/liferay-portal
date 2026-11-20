/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.test.client.http;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpServer;

import java.net.InetAddress;
import java.net.InetSocketAddress;

import java.util.concurrent.atomic.AtomicReference;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Allen Ziegenfus
 */
public class HttpInvokerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() throws Exception {
		_httpServer = HttpServer.create(
			new InetSocketAddress(InetAddress.getLoopbackAddress(), 0), 0);

		_httpServer.createContext(
			"/",
			httpExchange -> {
				Headers headers = httpExchange.getRequestHeaders();

				_userAgent.set(headers.getFirst("User-Agent"));

				httpExchange.sendResponseHeaders(204, -1);

				httpExchange.close();
			});

		_httpServer.start();
	}

	@After
	public void tearDown() {
		_httpServer.stop(0);
	}

	@Test
	public void testInvoke() throws Exception {
		_invoke(HttpInvoker.newHttpInvoker());

		Assert.assertEquals(
			"Liferay REST Client " +
				"(com.liferay.portal.tools.rest.builder.test.client)",
			_userAgent.get());

		String userAgent = RandomTestUtil.randomString();

		_invoke(
			HttpInvoker.newHttpInvoker(
			).header(
				"user-agent", userAgent
			));

		Assert.assertEquals(userAgent, _userAgent.get());
	}

	private void _invoke(HttpInvoker httpInvoker) throws Exception {
		InetSocketAddress inetSocketAddress = _httpServer.getAddress();

		HttpInvoker.HttpResponse httpResponse = httpInvoker.path(
			"http://localhost:" + inetSocketAddress.getPort() + "/"
		).invoke();

		Assert.assertEquals(204, httpResponse.getStatusCode());
	}

	private HttpServer _httpServer;
	private final AtomicReference<String> _userAgent = new AtomicReference<>();

}