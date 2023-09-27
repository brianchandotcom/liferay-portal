/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.osgi.web.wab.generator.internal.connection;

import com.liferay.portal.kernel.security.xml.SecureXMLFactoryProviderUtil;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.UnsecureSAXReaderUtil;
import com.liferay.portal.security.xml.SecureXMLFactoryProviderImpl;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.xml.SAXReaderImpl;

import java.io.IOException;

import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.net.UnknownServiceException;

import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Gregory Amerson
 */
public class WabURLConnectionTest {

	@ClassRule
	public static LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		SAXReaderUtil saxReaderUtil = new SAXReaderUtil();

		SAXReaderImpl secureSAXReaderImpl = new SAXReaderImpl();

		secureSAXReaderImpl.setSecure(true);

		saxReaderUtil.setSAXReader(secureSAXReaderImpl);

		SecureXMLFactoryProviderUtil secureXMLFactoryProviderUtil =
			new SecureXMLFactoryProviderUtil();

		secureXMLFactoryProviderUtil.setSecureXMLFactoryProvider(
			new SecureXMLFactoryProviderImpl());

		UnsecureSAXReaderUtil unsecureSAXReaderUtil =
			new UnsecureSAXReaderUtil();

		unsecureSAXReaderUtil.setSAXReader(new SAXReaderImpl());

		URL.setURLStreamHandlerFactory(
			protocol -> new URLStreamHandler() {

				@Override
				protected URLConnection openConnection(URL url) {
					return new URLConnection(url) {

						@Override
						public void connect() {
						}

					};
				}

			});
	}

	@Test(expected = UnknownServiceException.class)
	public void testWabURLConnectionRequiredParams() throws IOException {
		WabURLConnection wabURLConnection = new WabURLConnection(
			null, null,
			new URL(
				"webbundle:/path/to/foo?Web-ContextPath=foo&protocol=file"));

		wabURLConnection.getInputStream();
	}

	@Test(expected = UnknownServiceException.class)
	public void testWabURLConnectionRequiredParamsCompatibilityMode()
		throws Exception {

		String uriString = _getURIString(
			"dependencies/classic-theme.autodeployed.war");

		WabURLConnection wabURLConnection = new WabURLConnection(
			null, null,
			new URL("webbundle:" + uriString + "?Web-ContextPath=foo"));

		wabURLConnection.getInputStream();
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWabURLConnectionRequiredParamsMissing() throws Exception {
		WabURLConnection wabURLConnection = new WabURLConnection(
			null, null, new URL("webbundle:/path/to/foo?Web-ContextPath=foo"));

		wabURLConnection.getInputStream();
	}

	private String _getURIString(String fileName) throws Exception {
		URL url = WabURLConnectionTest.class.getResource(fileName);

		URI uri = url.toURI();

		return uri.toASCIIString();
	}

}