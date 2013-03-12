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

package com.liferay.httpservice.internal.servlet;

import java.util.Arrays;

import javax.servlet.Filter;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.HttpContext;
import org.osgi.service.http.NamespaceException;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockServletConfig;

/**
 * @author Miguel Pastor
 */
@RunWith(PowerMockRunner.class)
public class BundleServletContextTest extends PowerMockito {

	@Before
	public void setUp() {
		_bundleServletContext = new BundleServletContext(
			_bundle, "test", _servletContext);
	}

	@Test
	public void testGetClassloader() {
		mockBundleWiring();

		ClassLoader classLoader = getClass().getClassLoader();

		Assert.assertEquals(
			classLoader, _bundleServletContext.getClassLoader());

		verifyBundleWiring();
	}

	@Test
	public void testGetServletContextName() {
		Assert.assertEquals(
			"test",_bundleServletContext.getServletContextName());
	}

	@Test
	public void testRegisterNullFilter()
		throws NamespaceException, ServletException {

		try {
			_bundleServletContext.registerFilter(
				"Filter A", Arrays.asList("/a"), null, null, _httpContext);
		}
		catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterNullServlet()
		throws NamespaceException, ServletException {

		try {
			_bundleServletContext.registerServlet(
				"Servlet A", Arrays.asList("/a"), null, null, _httpContext);
		}
		catch (IllegalArgumentException iae) {
			return;
		}

		Assert.fail();
	}

	@Test
	public void testRegisterServletMultipleMapping()
		throws NamespaceException, ServletException {

		registerServlet("Servlet A", "/a", "/b", "/c");
	}

	@Test
	public void testRegisterServletSingleMapping()
		throws NamespaceException, ServletException {

		registerServlet("Servlet B", "/a");
	}

	protected void mockBundleWiring() {
		when(
			_bundle.adapt(BundleWiring.class)
		).thenReturn(
			_bundleWiring
		);

		when(
			_bundleWiring.getClassLoader()
		).thenReturn(
			getClass().getClassLoader()
		);
	}

	protected void registerServlet(String servletName, String ... urlMappings)
		throws NamespaceException, ServletException {

		mockBundleWiring();

		when(
			_servlet.getServletConfig()
		).thenReturn(
			new MockServletConfig(servletName)
		);

		_bundleServletContext.registerServlet(
			servletName, Arrays.asList(urlMappings), _servlet, null,
			_httpContext);

		Servlet servlet = _bundleServletContext.getServlet(servletName);

		Assert.assertNotNull(servlet);

		Assert.assertEquals(
			servletName, servlet.getServletConfig().getServletName());

		Mockito.verify(_servlet).getServletConfig();

		verifyBundleWiring();
	}

	protected void verifyBundleWiring() {
		Mockito.verify(_bundleWiring).getClassLoader();
		Mockito.verify(_bundle).adapt(BundleWiring.class);
	}

	@Mock
	private Bundle _bundle;

	private BundleServletContext _bundleServletContext;

	@Mock
	private BundleWiring _bundleWiring;

	@Mock
	private Filter _filter;

	@Mock
	private HttpContext _httpContext;

	@Mock
	private Servlet _servlet;

	@Mock
	private ServletContext _servletContext;

}