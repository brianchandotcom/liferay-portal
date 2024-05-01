/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.petra.http.invoker;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;

import java.lang.reflect.Field;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0} {1}")
	public static Iterable<Object[]> data() {
		List<Object[]> testData = new ArrayList<>();

		String[] methods = {"GET", "DELETE", "PUT", "POST", "PATCH"};
		String[] protocols = {"http", "https"};

		for (String protocol : protocols) {
			for (String method : methods) {
				testData.add(
					new Object[] {
						method, String.format("%s://foo.demo", protocol)
					});
			}
		}

		return testData;
	}

	public HttpInvokerTest(String methodName, String urlString) {
		_methodName = methodName;
		_urlString = urlString;
	}

	@Test
	public void test() throws IOException {
		URL url = new URL(_urlString);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		try {
			HttpURLConnection setMethodHttpURLConnection = httpURLConnection;

			if (Objects.equals(url.getProtocol(), "https")) {
				Class<?> clazz = httpURLConnection.getClass();

				Field field = clazz.getDeclaredField("delegate");

				field.setAccessible(true);

				setMethodHttpURLConnection = (HttpURLConnection)field.get(
					httpURLConnection);
			}

			_methodField.set(setMethodHttpURLConnection, _methodName);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new IOException(reflectiveOperationException);
		}

		Assert.assertEquals(
			_urlString, _methodName, httpURLConnection.getRequestMethod());
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

	private final String _methodName;
	private final String _urlString;

}