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

package com.liferay.portal.test.rule;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.internal.servlet.MainServlet;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.SearchEngineHelperUtil;
import com.liferay.portal.kernel.servlet.ServletContextClassLoaderPool;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.rule.ArquillianUtil;
import com.liferay.portal.kernel.test.rule.ClassTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.mock.AutoDeployMockServletContext;

import javax.servlet.ServletException;

import org.junit.runner.Description;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author     Shuyang Zhou
 * @deprecated As of Mueller (7.2.x), with no direct replacement
 */
@Deprecated
public class MainServletClassTestRule extends ClassTestRule<Void> {

	public static final MainServletClassTestRule INSTANCE =
		new MainServletClassTestRule();

	public static MainServlet getMainServlet() {
		return _mainServlet;
	}

	@Override
	public void afterClass(Description description, Void v)
		throws PortalException {

		if (ArquillianUtil.isArquillianTest(description)) {
			return;
		}

		SearchEngineHelperUtil.removeCompany(TestPropsValues.getCompanyId());
	}

	@Override
	public Void beforeClass(Description description) {
		if (ArquillianUtil.isArquillianTest(description)) {
			return null;
		}

		if (_mainServlet == null) {
			final MockServletContext mockServletContext =
				new AutoDeployMockServletContext(
					new FileSystemResourceLoader());

			mockServletContext.setServletContextName(StringPool.BLANK);

			Thread currentThread = Thread.currentThread();

			ServletContextClassLoaderPool.register(
				StringPool.BLANK, currentThread.getContextClassLoader());

			PortalLifecycleUtil.register(
				new PortalLifecycle() {

					@Override
					public void portalDestroy() {
					}

					@Override
					public void portalInit() {
						ModuleFrameworkUtilAdapter.registerContext(
							mockServletContext);
					}

				});

			ServletContextPool.put(StringPool.BLANK, mockServletContext);

			MockServletConfig mockServletConfig = new MockServletConfig(
				mockServletContext, "Main Servlet");

			mockServletConfig.addInitParameter(
				"config", "/WEB-INF/struts-config.xml");

			_mainServlet = new MainServlet();

			try {
				_mainServlet.init(mockServletConfig);
			}
			catch (ServletException se) {
				throw new RuntimeException(
					"The main servlet could not be initialized", se);
			}

			ServiceTestUtil.initStaticServices();
		}

		ServiceTestUtil.initServices();

		ServiceTestUtil.initPermissions();

		return null;
	}

	protected MainServletClassTestRule() {
	}

	private static MainServlet _mainServlet;

}