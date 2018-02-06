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

package com.liferay.portal.servlet.filters.aggregate;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.servlet.ServletContextUtil;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.util.FileImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import javax.servlet.ServletContext;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
public class ServletPathsTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@BeforeClass
	public static void setUpClass() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		FileUtil.deltree(_testDir);
	}

	@AfterClass
	public static void tearDownClass() {
		FileUtil.deltree(_testDir);
	}

	@Test
	public void testConstructor() {
		try {
			new ServletPaths(null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Servlet context is null", npe.getMessage());
		}

		try {
			new ServletPaths(new MockServletContext(), null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Resource path is null", iae.getMessage());
		}

		ServletContext servletContext = _prepareServletContext(
			new MockServletContext());

		ServletPaths servletPaths = new ServletPaths(
			servletContext, "/test1/test2/");

		Assert.assertEquals("/test1/test2/", servletPaths.getResourcePath());

		servletPaths = new ServletPaths(servletContext, "test1/");

		Assert.assertEquals("test1/", servletPaths.getResourcePath());
	}

	@Test
	public void testDown() {
		ServletContext servletContext = _prepareServletContext(
			new MockServletContext());

		ServletPaths servletPaths1 = new ServletPaths(servletContext, "/test1");

		Assert.assertSame(servletPaths1, servletPaths1.down(null));
		Assert.assertSame(servletPaths1, servletPaths1.down(StringPool.SLASH));

		ServletPaths servletPaths2 = servletPaths1.down("test2/");

		Assert.assertEquals("/test1/test2", servletPaths2.getResourcePath());

		ServletPaths servletPaths3 = servletPaths1.down("/test2");

		Assert.assertEquals("/test1/test2", servletPaths3.getResourcePath());

		ServletPaths servletPaths4 = new ServletPaths(servletContext, "test1/");

		ServletPaths servletPaths5 = servletPaths4.down("test2");

		Assert.assertEquals("test1/test2", servletPaths5.getResourcePath());

		ServletPaths servletPaths6 = servletPaths1.down(
			"/test2?extraparameters");

		Assert.assertEquals("/test1/test2", servletPaths6.getResourcePath());

		ServletPaths servletPaths7 = servletPaths1.down("../test2");

		Assert.assertEquals("/test2", servletPaths7.getResourcePath());

		ServletPaths servletPaths8 = servletPaths1.down("./test2");

		Assert.assertEquals("/test1/test2", servletPaths8.getResourcePath());
	}

	@Test
	public void testGetContent() throws IOException {
		final File file1 = new File(_testDir, "test1");
		final File file2 = new File(_testDir, "test2");

		String testContent = "Test Content";

		FileUtil.write(file2, testContent);

		ServletContext servletContext = new MockServletContext() {

			@Override
			public URL getResource(String path) throws MalformedURLException {
				if (path.contains(file1.getName())) {
					URI uri = file1.toURI();

					return uri.toURL();
				}

				if (path.contains(file2.getName())) {
					URI uri = file2.toURI();

					return uri.toURL();
				}

				return super.getResource(path);
			}

		};

		servletContext = _prepareServletContext(servletContext);

		ServletPaths servletPaths = new ServletPaths(servletContext, "test");

		Assert.assertNull(servletPaths.getContent());

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					ServletPaths.class.getName(), Level.SEVERE)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			servletPaths = new ServletPaths(servletContext, file1.getName());

			Assert.assertNull(servletPaths.getContent());

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				FileNotFoundException.class, throwable.getClass());

			servletPaths = new ServletPaths(servletContext, file2.getName());

			Assert.assertEquals(testContent, servletPaths.getContent());
		}
	}

	@Test
	public void testGetParentPath() {
		try {
			ServletPaths.getParentPath(null);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals("Resource path is null", iae.getMessage());
		}

		Assert.assertEquals(
			"test1", ServletPaths.getParentPath("test1/test2/"));
		Assert.assertEquals("test1", ServletPaths.getParentPath("test1/test2"));
		Assert.assertEquals("test1", ServletPaths.getParentPath("test1/"));
		Assert.assertEquals("test1", ServletPaths.getParentPath("test1"));
	}

	private ServletContext _prepareServletContext(
		ServletContext servletContext) {

		File webINFFile = new File(_testDir, ServletContextUtil.PATH_WEB_INF);

		servletContext.setAttribute(
			ServletContextUtil.URI_ATTRIBUTE, webINFFile.toURI());

		return servletContext;
	}

	private static final File _testDir = new File(
		SystemProperties.get(SystemProperties.TMP_DIR),
		ServletPathsTest.class.getSimpleName());

}