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

import com.liferay.portal.cache.memory.MemoryPortalCacheManager;
import com.liferay.portal.kernel.cache.SingleVMPoolUtil;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.util.CharPool;

import java.io.Serializable;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import org.springframework.mock.web.MockServletContext;

/**
 * @author László Csontos
 */
@PrepareForTest({ComboServlet.class, SingleVMPoolUtil.class})
@RunWith(PowerMockRunner.class)
public class ComboServletTest extends PowerMockito {

	@BeforeClass
	public static void setUpClass() throws Exception {
		mockStatic(SingleVMPoolUtil.class);

		MemoryPortalCacheManager<Serializable, Object>
			memoryPortalCacheManager =
				new MemoryPortalCacheManager<Serializable, Object>();

		memoryPortalCacheManager.afterPropertiesSet();

		String className = ComboServlet.class.getName();

		when(
			SingleVMPoolUtil.getCache(className)
		).thenReturn(
			memoryPortalCacheManager.getCache(className)
		);

		String fileContentBagClass =
			className + CharPool.PERIOD + "FileContentBag";

		when(
			SingleVMPoolUtil.getCache(fileContentBagClass)
		).thenReturn(
			memoryPortalCacheManager.getCache(fileContentBagClass)
		);

		_comboServlet = new ComboServlet();
	}

	@Test
	public void testGetResourceURLWithUnixDir() throws Exception {
		ServletContext servletContext = getServletContext(
			_WAS_DEFAULT_PATH_UNIX);

		testGetResourceURL(
			servletContext, CharPool.SLASH + _JAVASCRIPT_DIR, false);
	}

	@Test
	public void testGetResourceURLWithWindowsDir() throws Exception {
		ServletContext servletContext = getServletContext(
			_WAS_DEFAULT_PATH_WINDOWS);

		testGetResourceURL(
			servletContext, CharPool.SLASH + _JAVASCRIPT_DIR, false);
	}

	@Test
	public void testGetResourceURLWithWrongContext() throws Exception {
		ServletContext servletContext = getServletContext(null);

		testGetResourceURL(servletContext, null, true);
	}

	@Test
	public void testGetResourceURLWithWrongPath() throws Exception {
		ServletContext servletContext = getServletContext(
			_WAS_DEFAULT_PATH_UNIX);

		testGetResourceURL(servletContext, "/dummyPath", true);
	}

	protected ServletContext getServletContext(final String path) {
		return new MockServletContext() {

			@Override
			public URL getResource(String resourcePath)
				throws MalformedURLException {

				if (path == null) {
					return null;
				}

				return new URL("file:" + path + resourcePath);
			}

		};
	}

	protected void testGetResourceURL(
			ServletContext servletContext, String path, boolean expectNull)
		throws Exception {

		String rootPath = "dummyPath";

		if (!expectNull) {
			rootPath = ServletContextUtil.getRootPath(servletContext);
		}

		URL resourceURL = Whitebox.invokeMethod(
			_comboServlet, "getResourceURL", servletContext, rootPath, path);

		if (expectNull) {
			Assert.assertNull(resourceURL);
		}
		else {
			Assert.assertEquals(servletContext.getResource(path), resourceURL);
		}

	}

	private static final String _JAVASCRIPT_DIR = "html/js";

	private static final String _WAS_DEFAULT_PATH_UNIX =
		"/opt/IBM/WebSphere/AppServer/profiles/appsrv01" +
			"/installedApps/cell1/lportal_war.ear/lportal.war";

	private static final String _WAS_DEFAULT_PATH_WINDOWS =
		"C:/Program Files/IBM/WebSphere/AppServer/profiles/appsrv01" +
			"/installedApps/cell1/lportal_war.ear/lportal.war";

	private static ComboServlet _comboServlet;

}