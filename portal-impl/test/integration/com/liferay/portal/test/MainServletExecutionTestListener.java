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

package com.liferay.portal.test;

import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.servlet.MainServlet;

import java.io.File;

import javax.servlet.ServletException;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Miguel Pastor
 */
public class MainServletExecutionTestListener
	extends EnvironmentExecutionTestListener {

	@Override
	public void runBeforeClass(TestContext testContext) {
		super.runBeforeClass(testContext);

		MockServletContext mockServletContext =
			new AutoDeployMockServletContext(
				getResourceBasePath(), new FileSystemResourceLoader());

		MockServletConfig mockServletConfig = new MockServletConfig(
			mockServletContext);

		_mainServlet = new MainServlet();

		try {
			_mainServlet.init(mockServletConfig);
		}
		catch (ServletException se) {
			throw new RuntimeException(
				"The main servlet could not be initialized");
		}
	}

	protected String getResourceBasePath() {
		File file = new File("portal-web/docroot");

		return "file:" + file.getAbsolutePath();
	}

	private MainServlet _mainServlet;

	private class AutoDeployMockServletContext extends MockServletContext {

		public AutoDeployMockServletContext(
			String resourceBasePath, ResourceLoader resourceLoader) {

			super(resourceBasePath, resourceLoader);
		}

		/**
		 * @see com.liferay.portal.server.capabilities.TomcatServerCapabilities
		 */
		@SuppressWarnings("unused")
		protected Boolean autoDeploy = Boolean.TRUE;

	}

}