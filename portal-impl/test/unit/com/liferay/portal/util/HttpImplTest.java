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

package com.liferay.portal.util;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.randomizerbumpers.NumericStringRandomizerBumper;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Http;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.URLCodec;

import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Miguel Pastor
 */
public class HttpImplTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor() {

			@Override
			public void appendAssertClasses(List<Class<?>> assertClasses) {
				assertClasses.clear();
			}

			@Override
			public List<Method> getAssertMethods()
				throws ReflectiveOperationException {

				return Arrays.asList(
					HttpImpl.class.getDeclaredMethod(
						"_shortenURL", String.class, int.class, String.class,
						String.class, String.class));
			}

		};

	@BeforeClass
	public static void setUpClass() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(
			new PortalImpl() {

				@Override
				public String[] stripURLAnchor(String url, String separator) {
					return new String[] {url, StringPool.BLANK};
				}

			});
	}

	@Test
	public void testAddBooleanParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(Boolean.TRUE));
	}

	@Test
	public void testAddDoubleParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(111.1D));
	}

	@Test
	public void testAddIntParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(1));
	}

	@Test
	public void testAddLongParameter() {
		_addParameter("http://foo.com", "p", String.valueOf(111111L));
	}

	@Test
	public void testAddShortParameter() {
		_addParameter("http://foo.com", "p", String.valueOf((short)1));
	}

	@Test
	public void testAddStringParameter() {
		_addParameter("http://foo.com", "p", new String("foo"));
	}

	@Test
	public void testDecodeMultipleCharacterEncodedPath() {
		Assert.assertEquals(
			"http://foo?p=$param",
			_httpImpl.decodePath("http://foo%3Fp%3D%24param"));
	}

	@Test
	public void testDecodeNoCharacterEncodedPath() {
		Assert.assertEquals("http://foo", _httpImpl.decodePath("http://foo"));
	}

	@Test
	public void testDecodeSingleCharacterEncodedPath() {
		Assert.assertEquals(
			"http://foo#anchor", _httpImpl.decodePath("http://foo%23anchor"));
	}

	@Test
	public void testDecodeURLWithInvalidURLEncoding() {
		testDecodeURLWithInvalidURLEncoding("%");
		testDecodeURLWithInvalidURLEncoding("%0");
		testDecodeURLWithInvalidURLEncoding("%00%");
		testDecodeURLWithInvalidURLEncoding("%00%0");
		testDecodeURLWithInvalidURLEncoding("http://localhost:8080/?id=%");
	}

	@Test
	public void testDecodeURLWithNotHexChars() throws Exception {
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.NUMBER_0 - 1));
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.NUMBER_9 + 1));
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.UPPER_CASE_A - 1));
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.UPPER_CASE_F + 1));
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.LOWER_CASE_A - 1));
		testDecodeURLWithNotHexChars("%0" + (char)(CharPool.LOWER_CASE_F + 1));
	}

	@Test
	public void testEncodeMultipleCharacterEncodedPath() {
		Assert.assertEquals(
			"http%3A//foo%3Fp%3D%24param",
			_httpImpl.encodePath("http://foo?p=$param"));
	}

	@Test
	public void testEncodeNoCharacterEncodedPath() {
		Assert.assertEquals("http%3A//foo", _httpImpl.encodePath("http://foo"));
	}

	@Test
	public void testEncodeSingleCharacterEncodedPath() {
		Assert.assertEquals(
			"http%3A//foo%23anchor", _httpImpl.encodePath("http://foo#anchor"));
	}

	@Test
	public void testGetDomainWithInvalidURLs() {
		Assert.assertEquals("", _httpImpl.getDomain("foo.foo.1"));
		Assert.assertEquals("", _httpImpl.getDomain("test:test@/a/b"));
		Assert.assertEquals("", _httpImpl.getDomain("https://:foo.com"));
		Assert.assertEquals("", _httpImpl.getDomain("https://test:foo.com"));
	}

	@Test
	public void testGetDomainWithRelativeURLs() {
		Assert.assertEquals("", _httpImpl.getDomain("/a/b?key1=value1#anchor"));
	}

	@Test
	public void testGetDomainWithValidURLs() {
		Assert.assertEquals("foo.com", _httpImpl.getDomain("foo.com"));
		Assert.assertEquals("foo.com", _httpImpl.getDomain(" foo.com"));
		Assert.assertEquals("foo.com", _httpImpl.getDomain("foo.com "));
		Assert.assertEquals("foo.com", _httpImpl.getDomain("https://foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://www.foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://test@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://:@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://:test@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com", _httpImpl.getDomain("https://test:@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com",
			_httpImpl.getDomain("https://test:test@www.foo.com"));
		Assert.assertEquals(
			"www.foo.com",
			_httpImpl.getDomain("https://test:test@www.foo.com:8080"));
		Assert.assertEquals(
			"www.foo.com",
			_httpImpl.getDomain(" https://test:test@www.foo.com:8080"));
		Assert.assertEquals(
			"www.foo.com",
			_httpImpl.getDomain("https://test:test@www.foo.com:8080 "));
		Assert.assertEquals(
			"www.foo.com",
			_httpImpl.getDomain("https://www.foo.com/a/b?key1=value1#anchor"));
	}

	@Test
	public void testGetParameterMapWithCorrectQuery() {
		Map<String, String[]> parameterMap = _httpImpl.getParameterMap(
			"a=1&b=2");

		Assert.assertNotNull(parameterMap);

		Assert.assertEquals("1", parameterMap.get("a")[0]);
		Assert.assertEquals("2", parameterMap.get("b")[0]);
	}

	@Test
	public void testGetParameterMapWithMultipleBadParameter() {
		Map<String, String[]> parameterMap = _httpImpl.getParameterMap(
			"null&a=1&null");

		Assert.assertNotNull(parameterMap);

		Assert.assertEquals("1", parameterMap.get("a")[0]);
	}

	@Test
	public void testGetParameterMapWithSingleBadParameter() {
		Map<String, String[]> parameterMap = _httpImpl.getParameterMap(
			"null&a=1");

		Assert.assertNotNull(parameterMap);

		Assert.assertEquals("1", parameterMap.get("a")[0]);
	}

	@Test
	public void testGetProtocols() {
		Assert.assertEquals("https", _httpImpl.getProtocol(" https://foo.com"));
		Assert.assertEquals("https", _httpImpl.getProtocol("https://foo.com"));
		Assert.assertEquals("HtTps", _httpImpl.getProtocol("HtTps://foo.com"));
		Assert.assertEquals("a012", _httpImpl.getProtocol("a012://foo.com"));
		Assert.assertEquals("", _httpImpl.getProtocol("://foo.com"));
		Assert.assertEquals("", _httpImpl.getProtocol("1a://foo.com"));
		Assert.assertEquals("", _httpImpl.getProtocol("#%://foo.com"));
		Assert.assertEquals("", _httpImpl.getProtocol("foo.com"));
	}

	@Test
	public void testNormalizePath() {
		Assert.assertEquals("/api/axis", _httpImpl.normalizePath("/api/axis?"));
		Assert.assertEquals("/", _httpImpl.normalizePath("/.."));
		Assert.assertEquals(
			"/api/axis", _httpImpl.normalizePath("/api/%61xis"));
		Assert.assertEquals(
			"/api/%2561xis", _httpImpl.normalizePath("/api/%2561xis"));
		Assert.assertEquals(
			"/api/ax%3Fs", _httpImpl.normalizePath("/api/ax%3fs"));
		Assert.assertEquals(
			"/api/%2F/axis",
			_httpImpl.normalizePath("/api/%2f/;x=aaa_%2f_y/axis"));
		Assert.assertEquals(
			"/api/axis", _httpImpl.normalizePath("/api/;x=aaa_%2f_y/axis"));
		Assert.assertEquals(
			"/api/axis", _httpImpl.normalizePath("/api/;x=aaa_%5b_y/axis"));
		Assert.assertEquals(
			"/api/axis",
			_httpImpl.normalizePath("/api/;x=aaa_LIFERAY_TEMP_SLASH_y/axis"));
		Assert.assertEquals(
			"/api/axis",
			_httpImpl.normalizePath("/api///////%2e/../;x=y/axis"));
		Assert.assertEquals(
			"/api/axis",
			_httpImpl.normalizePath("/////api///////%2e/a/../;x=y/axis"));
		Assert.assertEquals(
			"/api/axis",
			_httpImpl.normalizePath("/////api///////%2e/../;x=y/axis"));
		Assert.assertEquals(
			"/api/axis", _httpImpl.normalizePath("/api///////%2e/axis"));
		Assert.assertEquals(
			"/api/axis", _httpImpl.normalizePath("./api///////%2e/axis"));
		Assert.assertEquals(
			"/api/axis?foo=bar&bar=foo",
			_httpImpl.normalizePath("./api///////%2e/axis?foo=bar&bar=foo"));
	}

	@Test
	public void testParameterMapFromString() {
		Map<String, String[]> expectedParameterMap = new HashMap<>();

		expectedParameterMap.put("key1", new String[] {"value1", "value2"});
		expectedParameterMap.put("key2", new String[] {"value3"});

		StringBundler sb = new StringBundler(12);

		for (Map.Entry<String, String[]> entry :
				expectedParameterMap.entrySet()) {

			String key = entry.getKey();

			for (String value : entry.getValue()) {
				sb.append(key);
				sb.append(StringPool.EQUAL);
				sb.append(value);
				sb.append(StringPool.AMPERSAND);
			}
		}

		sb.setIndex(sb.index() - 1);

		Map<String, String[]> actualParameterMap = _httpImpl.getParameterMap(
			sb.toString());

		Assert.assertEquals(
			"Actual parameter map size: " + actualParameterMap.size(),
			expectedParameterMap.size(), actualParameterMap.size());

		for (Map.Entry<String, String[]> entry :
				actualParameterMap.entrySet()) {

			Assert.assertArrayEquals(
				expectedParameterMap.get(entry.getKey()), entry.getValue());
		}
	}

	@Test
	public void testProtocolizeMalformedURL() {
		Assert.assertEquals(
			"foo.com", _httpImpl.protocolize("foo.com", 8080, true));
	}

	@Test
	public void testProtocolizeNonsecure() {
		Assert.assertEquals(
			"http://foo.com:8080",
			_httpImpl.protocolize("https://foo.com", 8080, false));
	}

	@Test
	public void testProtocolizeSecure() {
		Assert.assertEquals(
			"https://foo.com:8443",
			_httpImpl.protocolize("http://foo.com", 8443, true));
	}

	@Test
	public void testProtocolizeWithoutPort() {
		Assert.assertEquals(
			"http://foo.com:8443/web/guest",
			_httpImpl.protocolize("https://foo.com:8443/web/guest", -1, false));
	}

	@Test
	public void testRemovePathParameters() {
		Assert.assertEquals(
			"/TestServlet/one/two",
			_httpImpl.removePathParameters(
				"/TestServlet;jsessionid=ae01b0f2af/one;test=$one@two/two"));
		Assert.assertEquals(
			"/TestServlet/one/two",
			_httpImpl.removePathParameters(
				"/TestServlet;jsessionid=ae01b0f2af;test2=123,456" +
					"/one;test=$one@two/two"));
		Assert.assertEquals(
			"/TestServlet/one/two",
			_httpImpl.removePathParameters(
				"/TestServlet/one;test=$one@two/two;jsessionid=ae01b0f2af;" +
					"test2=123,456"));
		Assert.assertEquals("/", _httpImpl.removePathParameters("/;?"));
		Assert.assertEquals("/", _httpImpl.removePathParameters("//;/;?"));
		Assert.assertEquals("//", _httpImpl.removePathParameters("///;?"));
		Assert.assertEquals(
			"/TestServlet/one",
			_httpImpl.removePathParameters("/TestServlet/;x=y/one"));
		Assert.assertEquals(
			"/TestServlet/one",
			_httpImpl.removePathParameters("/;x=y/TestServlet/one/;x=y"));

		try {
			_httpImpl.removePathParameters(";x=y");

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Unable to handle URI: ;x=y", iae.getMessage());
		}
	}

	@Test
	public void testRemoveProtocol() {
		Assert.assertEquals(
			"#^&://abc.com", _httpImpl.removeProtocol("#^&://abc.com"));
		Assert.assertEquals(
			"/^&://abc.com", _httpImpl.removeProtocol("/^&://abc.com"));
		Assert.assertEquals(
			"ftp.foo.com", _httpImpl.removeProtocol("ftp://ftp.foo.com"));
		Assert.assertEquals(
			"///foo.com", _httpImpl.removeProtocol("http://///foo.com"));
		Assert.assertEquals(
			"////foo.com", _httpImpl.removeProtocol("////foo.com"));
		Assert.assertEquals(
			"http://foo.com",
			_httpImpl.removeProtocol("http://http://foo.com"));
		Assert.assertEquals(
			"/\\www.google.com", _httpImpl.removeProtocol("/\\www.google.com"));
		Assert.assertEquals(
			"/\\//\\/www.google.com",
			_httpImpl.removeProtocol("/\\//\\/www.google.com"));
		Assert.assertEquals(
			"/path/name", _httpImpl.removeProtocol("/path/name"));
		Assert.assertEquals(
			"./path/name", _httpImpl.removeProtocol("./path/name"));
		Assert.assertEquals(
			"../path/name", _httpImpl.removeProtocol("../path/name"));
		Assert.assertEquals(
			"foo.com?redirect=http%3A%2F%2Ffoo2.com",
			_httpImpl.removeProtocol(
				"http://foo.com?redirect=http%3A%2F%2Ffoo2.com"));
		Assert.assertEquals(
			"www.google.com/://localhost",
			_httpImpl.removeProtocol("http://www.google.com/://localhost"));
		Assert.assertEquals(
			"a:b@foo.com", _httpImpl.removeProtocol("http://a:b@foo.com"));
		Assert.assertEquals(
			"a:b@foo.com", _httpImpl.removeProtocol(" http://a:b@foo.com"));
		Assert.assertEquals(
			"a:b@foo.com", _httpImpl.removeProtocol("a:b@foo.com"));
		Assert.assertEquals(
			":@foo.com", _httpImpl.removeProtocol("http://:@foo.com"));
		Assert.assertEquals(":@foo.com", _httpImpl.removeProtocol(":@foo.com"));
		Assert.assertEquals(
			"?k1=v1&k2=v2", _httpImpl.removeProtocol("http://?k1=v1&k2=v2"));
		Assert.assertEquals(
			"?k1=v1&k2=v2", _httpImpl.removeProtocol("?k1=v1&k2=v2"));
		Assert.assertEquals(
			"#page1", _httpImpl.removeProtocol("http://#page1"));
		Assert.assertEquals("#page1", _httpImpl.removeProtocol("#page1"));
	}

	@Test
	public void testShortenURL() {

		// No change

		Assert.assertSame(
			"www.liferay.com", _httpImpl.shortenURL("www.liferay.com"));
		Assert.assertSame(
			"www.liferay.com?", _httpImpl.shortenURL("www.liferay.com?"));
		Assert.assertSame(
			"www.liferay.com?key1=value1",
			_httpImpl.shortenURL("www.liferay.com?key1=value1"));
		Assert.assertSame(
			"www.liferay.com?key1=value1&redirect=test",
			_httpImpl.shortenURL("www.liferay.com?key1=value1&redirect=test"));

		String paramValue = RandomTestUtil.randomString(
			Http.URL_MAXIMUM_LENGTH, NumericStringRandomizerBumper.INSTANCE);

		// Cannot safely remove anything

		Assert.assertSame(paramValue, _httpImpl.shortenURL(paramValue));

		String url = "www.liferay.com?key=" + paramValue;

		Assert.assertEquals(url, _httpImpl.shortenURL(url));

		// Bad parameter format

		url = "www.liferay.com?redirectX" + paramValue;

		Assert.assertEquals(url, _httpImpl.shortenURL(url));

		// Remove redirect one deep

		Assert.assertEquals(
			"www.liferay.com",
			_httpImpl.shortenURL("www.liferay.com?_backURL=" + paramValue));
		Assert.assertEquals(
			"www.liferay.com?key1=value1",
			_httpImpl.shortenURL(
				"www.liferay.com?key1=value1&_redirect=" + paramValue));
		Assert.assertEquals(
			"www.liferay.com?key1=value1",
			_httpImpl.shortenURL(
				"www.liferay.com?redirect=" + paramValue + "&key1=value1"));

		// Remove redirect and keep _returnToFullPageURL

		Assert.assertEquals(
			"www.liferay.com?key1=value1&_returnToFullPageURL=test",
			_httpImpl.shortenURL(
				"www.liferay.com?_returnToFullPageURL=test&redirect=" +
					paramValue + "&key1=value1"));

		// Remove redirect two deep

		String encodedURL = URLCodec.encodeURL(
			"www.liferay.com?key1=value1&redirect=" + paramValue);

		Assert.assertEquals(
			"www.liferay.com?key1=value1&redirect=" +
				URLCodec.encodeURL("www.liferay.com?key1=value1"),
			_httpImpl.shortenURL(
				"www.liferay.com?key1=value1&redirect=" + encodedURL));

		// Remove redirect three deep

		String encodedURL2 = URLCodec.encodeURL(
			"www.liferay.com?key1=value1&redirect=" +
				URLCodec.encodeURL("www.liferay.com?key1=value1"));

		String encodedURL3 = URLCodec.encodeURL(
			"www.liferay.com?key1=value1&redirect=" + encodedURL);

		Assert.assertEquals(
			"www.liferay.com?key1=value1&redirect=" + encodedURL2,
			_httpImpl.shortenURL(
				"www.liferay.com?redirect=" + encodedURL3 + "&key1=value1"));

		// Remove redirect three deep and keep _returnToFullPageURL two deep

		String encodedURL4 = URLCodec.encodeURL(
			"www.liferay.com?key1=value1&_returnToFullPageURL=test&redirect=" +
				URLCodec.encodeURL("www.liferay.com?key1=value1"));

		String encodedURL5 = URLCodec.encodeURL(
			"www.liferay.com?_returnToFullPageURL=test&key1=value1&redirect=" +
				encodedURL);

		Assert.assertEquals(
			"www.liferay.com?key1=value1&redirect=" + encodedURL4,
			_httpImpl.shortenURL(
				"www.liferay.com?redirect=" + encodedURL5 + "&key1=value1"));
	}

	protected void testDecodeURLWithInvalidURLEncoding(String url) {
		_testDecodeURL(url, "Invalid URL encoding " + url);
	}

	protected void testDecodeURLWithNotHexChars(String url) {
		_testDecodeURL(url, "is not a hex char");
	}

	private void _addParameter(
		String url, String parameterName, String parameterValue) {

		String newURL = _httpImpl.addParameter(
			url, parameterName, parameterValue);

		StringBundler sb = new StringBundler(5);

		sb.append(url);
		sb.append(StringPool.QUESTION);
		sb.append(parameterName);
		sb.append(StringPool.EQUAL);
		sb.append(parameterValue);

		Assert.assertEquals(sb.toString(), newURL);
	}

	private void _testDecodeURL(String url, String expectedMessage) {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					HttpImpl.class.getName(), Level.SEVERE)) {

			String decodeURL = _httpImpl.decodeURL(url);

			Assert.assertEquals(StringPool.BLANK, decodeURL);

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			String message = logRecord.getMessage();

			Assert.assertTrue(message, message.contains(expectedMessage));
		}
	}

	private final HttpImpl _httpImpl = new HttpImpl();

}