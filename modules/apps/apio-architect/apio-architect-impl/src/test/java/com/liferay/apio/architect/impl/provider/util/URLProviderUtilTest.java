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

package com.liferay.apio.architect.impl.provider.util;

import static com.liferay.apio.architect.impl.provider.util.URLProviderUtil.getServerURL;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class URLProviderUtilTest {

	@Before
	public void setUp() {
		_httpServletRequest = Mockito.mock(HttpServletRequest.class);

		Mockito.when(
			_httpServletRequest.getHeader(Mockito.anyString())
		).thenReturn(
			null
		);

		Mockito.when(
			_httpServletRequest.getScheme()
		).thenReturn(
			"http"
		);

		Mockito.when(
			_httpServletRequest.getServerName()
		).thenReturn(
			"localhost"
		);

		Mockito.when(
			_httpServletRequest.getServerPort()
		).thenReturn(
			80
		);
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testConstructorThrowsException() throws Throwable {
		Constructor<?> constructor =
			URLProviderUtil.class.getDeclaredConstructors()[0];

		constructor.setAccessible(true);

		try {
			constructor.newInstance();
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	@Test
	public void testGetServerURLReturnsValidURLWithForwardedHost() {
		Mockito.when(
			_httpServletRequest.getHeader("X-Forwarded-Host")
		).thenReturn(
			"www.liferay.com"
		);

		String serverURL = getServerURL(_httpServletRequest);

		assertThat(serverURL, is("http://www.liferay.com"));
	}

	@Test
	public void testGetServerURLReturnsValidURLWithForwardedProto() {
		Mockito.when(
			_httpServletRequest.getHeader("X-Forwarded-Proto")
		).thenReturn(
			"https"
		);

		String serverURL = getServerURL(_httpServletRequest);

		assertThat(serverURL, is("https://localhost:80"));
	}

	@Test
	public void testGetServerURLReturnsValidURLWithoutHeaders() {
		String serverURL = getServerURL(_httpServletRequest);

		assertThat(serverURL, is("http://localhost:80"));
	}

	private HttpServletRequest _httpServletRequest;

}