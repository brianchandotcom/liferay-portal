package com.liferay.petra.http.invoker;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * @author Drew Brokke
 */
@RunWith(Parameterized.class)
public class HttpInvokerTest {

	public HttpInvokerTest(String methodName, String urlString) {
		_methodName = methodName;
		_urlString = urlString;
	}

	@Parameterized.Parameters(name = "Testcase-{index}: testing {0} {1}")
	public static Iterable<Object[]> data() {
		List<Object[]> testData = new ArrayList<>();

		String[] methods = {"GET", "DELETE", "PUT", "POST", "PATCH"};
		String[] protocols = {"http", "https"};

		for (String protocol : protocols) {
			for (String method : methods) {
				testData.add(
					new Object[]{
						method, String.format("%s://foo.demo", protocol)}
				);
			}
		}
		return testData;
	}

	private final String _urlString;
	private final String _methodName;

	@Test
	public void test() throws IOException {
		URL url = new URL(_urlString);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		try {
			_methodField.set(httpURLConnection, _methodName);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new IOException(reflectiveOperationException);
		}

		Assert.assertEquals(_urlString, _methodName, httpURLConnection.getRequestMethod());
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

}