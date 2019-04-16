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

package com.liferay.portal.remote.cors.client.test;

import com.liferay.oauth2.provider.test.util.OAuth2ProviderTestUtil;
import com.liferay.portal.kernel.util.DigesterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.util.DigesterImpl;
import com.liferay.portal.util.HttpImpl;

import java.io.File;

import java.net.HttpURLConnection;
import java.net.URL;

import java.util.List;
import java.util.Map;

import javax.ws.rs.HttpMethod;

import org.jboss.shrinkwrap.api.Archive;

import org.junit.Assert;
import org.junit.BeforeClass;

import org.osgi.framework.BundleActivator;

/**
 * @author Marta Medio
 */
public abstract class BaseCORSClientTestCase {

	public static Archive<?> getArchive(
			Class<? extends BundleActivator> bundleActivatorClass)
		throws Exception {

		return OAuth2ProviderTestUtil.getArchive(
			bundleActivatorClass, new File("bnd.bnd"));
	}

	@BeforeClass
	public static void setUpClass() {
		System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

		HttpUtil httpUtil = new HttpUtil();

		httpUtil.setHttp(new HttpImpl());

		DigesterUtil digesterUtil = new DigesterUtil();

		digesterUtil.setDigester(new DigesterImpl());
	}

	protected void assertURL(String urlString, boolean allowOrigin)
		throws Exception {

		URL url = new URL("http://localhost:8080/o" + urlString);

		HttpURLConnection httpURLConnection =
			(HttpURLConnection)url.openConnection();

		httpURLConnection.setRequestProperty("Origin", _TEST_CORS_URI);
		httpURLConnection.setRequestProperty(
			_ACCESS_CONTROL_REQUEST_METHOD, HttpMethod.GET);

		Map<String, List<String>> headerFields =
			httpURLConnection.getHeaderFields();

		if (allowOrigin) {
			Assert.assertEquals(
				_TEST_CORS_URI,
				StringUtil.merge(
					headerFields.get(_ACCESS_CONTROL_ALLOW_ORIGIN)));
		}
		else {
			Assert.assertNull(headerFields.get(_ACCESS_CONTROL_ALLOW_ORIGIN));
		}

		Assert.assertEquals(
			"get", StringUtil.read(httpURLConnection.getInputStream()));


		Assert.assertEquals(200, httpURLConnection.getResponseCode());
	}

	private static final String _ACCESS_CONTROL_ALLOW_ORIGIN =
		"Access-Control-Allow-Origin";

	private static final String _ACCESS_CONTROL_REQUEST_METHOD =
		"Access-Control-Request-Method";

	private static final String _TEST_CORS_URI = "http://test-cors.com";

}