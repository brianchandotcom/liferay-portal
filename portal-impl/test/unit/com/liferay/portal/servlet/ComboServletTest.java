/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.servlet;

import static org.mockito.Mockito.verify;

import com.liferay.portal.cache.SingleVMPoolImpl;
import com.liferay.portal.cache.memory.MemoryPortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import java.net.URI;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Carlos Sierra Andrés
 */
@RunWith(PowerMockRunner.class)
public class ComboServletTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		MemoryPortalCacheManager<Serializable, Serializable>
			memoryPortalCacheManager =
				new MemoryPortalCacheManager<Serializable, Serializable>();

		memoryPortalCacheManager.afterPropertiesSet();

		SingleVMPoolImpl singleVMPoolImpl = new SingleVMPoolImpl();

		singleVMPoolImpl.setPortalCacheManager(memoryPortalCacheManager);

		SingleVMPoolUtil singleVMPoolUtil = new SingleVMPoolUtil();

		singleVMPoolUtil.setSingleVMPool(singleVMPoolImpl);
	}

	@Before
	public void setUp() throws IOException, ServletException {
		_comboServlet = new ComboServlet();

		_portalServletContext = spy(new MockServletContext());

		ServletConfig servletConfig = new MockServletConfig(
			_portalServletContext);

		_portalServletContext.setContextPath("portal");

		File tempFile = _temporaryFolder.newFile();

		URI tempFileURI = tempFile.toURI();

		when(
			_portalServletContext.getResource(Mockito.anyString())
		).thenReturn(
			tempFileURI.toURL()
		);

		_comboServlet.init(servletConfig);

		_pluginServletContext = spy(new MockServletContext("plugin-context"));

		when(
			_pluginServletContext.getResource(Mockito.anyString())
		).thenReturn(
			tempFileURI.toURL()
		);

		ServletContextPool.put("plugin-context", _pluginServletContext);
	}

	@Test(expected = ServletException.class)
	public void testGetResourceFromNonexistingPluginContext() throws Exception {
		_comboServlet.getResourceURL(
			"non-existing-plugin-context:/js/javascript.js");
	}

	@Test
	public void testGetResourceFromPluginContext() throws Exception {
		_comboServlet.getResourceURL("plugin-context:/js/javascript.js");

		verify(_pluginServletContext);

		_pluginServletContext.getResource("/js/javascript.js");
	}

	@Test
	public void testGetResourceFromPluginContextWithInitialSlash()
		throws Exception {

		_comboServlet.getResourceURL("/plugin-context:/js/javascript.js");

		verify(_pluginServletContext);

		_pluginServletContext.getResource("/js/javascript.js");
	}

	@Test
	public void testGetResourceFromPortalContext() throws Exception {
		String path = "/js/javascript.js";

		_comboServlet.getResourceURL("/js/javascript.js");

		verify(_portalServletContext);

		_portalServletContext.getResource(path);
	}

	private ComboServlet _comboServlet;
	private MockServletContext _pluginServletContext;
	private MockServletContext _portalServletContext;

	@Rule
	private TemporaryFolder _temporaryFolder = new TemporaryFolder();

}