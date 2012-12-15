/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.servlet.ServletContext;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Laszlo Csontos
 */
@RunWith(PowerMockRunner.class)
public class ServletContextUtilTest extends PowerMockito {

	@Test
	public void testGetRootURIWithBlank() throws Exception {
		doGetRootURI(StringPool.BLANK, _URI_SLASH);
	}

	@Test(expected = MalformedURLException.class)
	public void testGetRootURIWithInvalidChars() throws Exception {
		doGetRootURI(_PATH_INVALID_CHARS, null);
	}

	@Test
	public void testGetRootURIWithReservedChars() throws Exception {
		doGetRootURI(_PATH_RESERVED_CHARS, _URI_RESERVED_CHARS);
	}

	@Test
	public void testGetRootURIWithUnreservedChars() throws Exception {
		doGetRootURI(_PATH_UNRESERVED_CHARS, _URI_UNRESERVED_CHARS);
	}

	protected static URI createURI(String resourcePath) {
		URI uri = null;

		try {
			uri = new URI(_URI_SCHEME, resourcePath, null);
		} catch (URISyntaxException e) {
			_log.error(e);
		}

		return uri;
	}

	protected ServletContext createServletContext(final String resourcePath) {
		ServletContext servletContext = new MockServletContext() {

			@Override
			public URL getResource(String path) throws MalformedURLException {
				String fullPath =
					_URI_SCHEME + StringPool.COLON + resourcePath + path;

				URL fullUrl = new URL(fullPath);

				return fullUrl;
			}

		};

		return servletContext;
	}

	protected void doGetRootURI(String resourcePath, URI resourceURI)
			throws Exception {

		ServletContext servletContext = createServletContext(resourcePath);

		URI uri = ServletContextUtil.getRootURI(servletContext);

		Assert.assertEquals(resourceURI, uri);
		Assert.assertEquals(
			resourceURI,
			servletContext.getAttribute(ServletContextUtil.URI_ATTRIBUTE));

	}

	private static final String _PATH_INVALID_CHARS = ":?#[]/@";

	private static final String _PATH_RESERVED_CHARS =
		"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_.~";

	private static final String _PATH_UNRESERVED_CHARS = "/!$&'()*+,;= ";

	private static final URI _URI_RESERVED_CHARS = createURI(
		_PATH_RESERVED_CHARS);

	private static final String _URI_SCHEME = "file";

	private static final URI _URI_SLASH = createURI(StringPool.SLASH);

	private static final URI _URI_UNRESERVED_CHARS = createURI(
		_PATH_UNRESERVED_CHARS);

	private static Log _log = LogFactoryUtil.getLog(
		ServletContextUtilTest.class);

}