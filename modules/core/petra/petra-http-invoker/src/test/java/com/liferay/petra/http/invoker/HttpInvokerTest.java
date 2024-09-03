/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.http.invoker;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.lang.reflect.Field;

import java.net.HttpURLConnection;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Drew Brokke
 */
@RunWith(Parameterized.class)
public class HttpInvokerTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Parameterized.Parameters(name = "Testcase-{index}: testing {1} {0}")
	public static Iterable<Object[]> data() {
		List<Object[]> data = new ArrayList<>();

		HttpInvoker.HttpMethod[] httpMethods = {
			HttpInvoker.HttpMethod.GET, HttpInvoker.HttpMethod.DELETE,
			HttpInvoker.HttpMethod.PUT, HttpInvoker.HttpMethod.POST,
			HttpInvoker.HttpMethod.PATCH
		};
		String[] protocols = {"http", "https"};

		for (String protocol : protocols) {
			for (HttpInvoker.HttpMethod httpMethod : httpMethods) {
				data.add(new Object[] {httpMethod, protocol});
			}
		}

		return data;
	}

	public HttpInvokerTest(HttpInvoker.HttpMethod httpMethod, String protocol) {
		_httpMethod = httpMethod;
		_protocol = protocol;
	}

	@Test
	public void testGetHttpURLConnection() throws IOException {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		HttpURLConnection httpURLConnection = httpInvoker.getHttpURLConnection(
			_httpMethod, String.format("%s://foo.bar", _protocol));

		Assert.assertEquals(
			_protocol, _httpMethod.name(),
			httpURLConnection.getRequestMethod());
	}

	@Test
	public void testPathReplacement() {
		_testPathReplacement("$");
		_testPathReplacement("\\\\");
	}

	private void _testPathReplacement(String specialCharacter) {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.path("/api/users/{name}");
		httpInvoker.path("name", "value" + specialCharacter);

		Assert.assertEquals(
			"/api/users/value" + specialCharacter,
			ReflectionTestUtil.getFieldValue(httpInvoker, "_path"));
	}

	private static final Field _httpMethodField;

	static {
		try {
			_httpMethodField = HttpURLConnection.class.getDeclaredField(
				"method");

			_httpMethodField.setAccessible(true);
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final HttpInvoker.HttpMethod _httpMethod;
	private final String _protocol;

}