/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.http.invoker;

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
		List<Object[]> testData = new ArrayList<>();

		HttpInvoker.HttpMethod[] methods = {
			HttpInvoker.HttpMethod.GET, HttpInvoker.HttpMethod.DELETE,
			HttpInvoker.HttpMethod.PUT, HttpInvoker.HttpMethod.POST,
			HttpInvoker.HttpMethod.PATCH
		};
		String[] protocols = {"http", "https"};

		for (String protocol : protocols) {
			for (HttpInvoker.HttpMethod method : methods) {
				testData.add(new Object[] {method, protocol});
			}
		}

		return testData;
	}

	public HttpInvokerTest(HttpInvoker.HttpMethod method, String protocol) {
		_method = method;
		_protocol = protocol;
	}

	@Test
	public void testGetHttpURLConnection() throws IOException {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		HttpURLConnection httpURLConnection = httpInvoker.getHttpURLConnection(
			_method, String.format("%s://foo.demo", _protocol));

		Assert.assertEquals(
			_protocol, _method.name(), httpURLConnection.getRequestMethod());
	}

	private static final Field _methodField;

	static {
		try {
			_methodField = HttpURLConnection.class.getDeclaredField("method");

			_methodField.setAccessible(true);
		}
		catch (Exception exception) {
			throw new ExceptionInInitializerError(exception);
		}
	}

	private final HttpInvoker.HttpMethod _method;
	private final String _protocol;

}