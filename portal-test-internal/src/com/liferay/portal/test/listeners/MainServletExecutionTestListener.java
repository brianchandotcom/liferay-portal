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

package com.liferay.portal.test.listeners;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.AbstractExecutionTestListener;
import com.liferay.portal.kernel.test.TestContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.util.test.TestPropsValues;

import java.io.File;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Miguel Pastor
 */
public class MainServletExecutionTestListener
	extends AbstractExecutionTestListener {

	@Override
	public void runAfterClass(TestContext testContext) {
		ServiceTestUtil.destroyServices();

		try {
			SearchEngineUtil.removeCompany(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public void runBeforeClass(TestContext testContext) {
		ServiceTestUtil.initServices();

		ServiceTestUtil.initPermissions();

		if (mainServlet != null) {
			return;
		}

		MockServletContext mockServletContext =
			new AutoDeployMockServletContext(
				getResourceBasePath(), new FileSystemResourceLoader());

		ServletContextPool.put(StringPool.BLANK, mockServletContext);

		MockServletConfig mockServletConfig = new MockServletConfig(
			mockServletContext);

		mainServlet = new MainServlet();

		try {
			mainServlet.init(mockServletConfig);
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

	protected static MainServlet mainServlet;

	protected static class FieldBag {

		public FieldBag(Class<?> fieldClass) {
			_fieldClass = fieldClass;
		}

		public void addField(Field field) {
			_fields.add(field);
		}

		public Class<?> getFieldClass() {
			return _fieldClass;
		}

		public List<Field> getFields() {
			return _fields;
		}

		private Class<?> _fieldClass;
		private List<Field> _fields = new ArrayList<Field>();

	}

	protected class AutoDeployMockServletContext extends MockServletContext {

		public AutoDeployMockServletContext(
			String resourceBasePath, ResourceLoader resourceLoader) {

			super(resourceBasePath, resourceLoader);
		}

		/**
		 * @see com.liferay.portal.server.capabilities.TomcatServerCapabilities
		 */
		protected Boolean autoDeploy = Boolean.TRUE;

	}

	private static Log _log = LogFactoryUtil.getLog(
		MainServletExecutionTestListener.class);

}