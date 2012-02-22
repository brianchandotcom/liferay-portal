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

package com.liferay.portal.servlet;

import com.liferay.portal.service.ServiceTestUtil;

import java.io.File;

import junit.extensions.TestSetup;

import junit.framework.Test;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class MainServletTestSetup extends TestSetup {

	public MainServletTestSetup(Test test) {
		super(test);
	}

	@Override
	public void setUp() throws Exception {
		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		MockServletContext mockServletContext = new MockServletContext(
			getResourceBasePath(), new FileSystemResourceLoader());

		MockServletConfig mockServletConfig = new MockServletConfig(
			mockServletContext);

		_mainServlet = new MainServlet();

		_mainServlet.init(mockServletConfig);
	}

	@Override
	public void tearDown() throws Exception {
		_mainServlet.destroy();

		ServiceTestUtil.destroyServices();
	}

	protected String getResourceBasePath() {
		File file = new File("portal-web");

		return file.getAbsolutePath();
	}

	private MainServlet _mainServlet;

}