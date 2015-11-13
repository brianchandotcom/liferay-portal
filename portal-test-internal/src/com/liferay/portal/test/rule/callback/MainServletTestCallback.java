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

package com.liferay.portal.test.rule.callback;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.test.rule.callback.BaseTestCallback;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.PortalLifecycle;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.module.framework.ModuleFrameworkUtilAdapter;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.servlet.MainServlet;
import com.liferay.portal.test.mock.AutoDeployMockServletContext;
import com.liferay.portal.test.rule.MainServletTestRule;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Shuyang Zhou
 */
public class MainServletTestCallback extends BaseTestCallback<Object, Object> {

	public static final MainServletTestCallback INSTANCE =
		new MainServletTestCallback();

	public static MainServlet getMainServlet() {
		return _mainServlet;
	}

	@Override
	public void afterClass(Description description, Object object) {
		try {
			SearchEngineUtil.removeCompany(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	@Override
	public Object beforeClass(Description description) {
		if (isArquillianTest(description)) {
			Assert.fail(
				description.getTestClass() + " is an Arquillian test and " +
					"should not use " + MainServletTestRule.class);
		}

		if (_mainServlet == null) {
			final MockServletContext mockServletContext =
				new AutoDeployMockServletContext(
					new FileSystemResourceLoader());

			PortalLifecycleUtil.register(
				new PortalLifecycle() {

					@Override
					public void portalInit() {
						ModuleFrameworkUtilAdapter.registerContext(
							mockServletContext);
					}

					@Override
					public void portalDestroy() {
					}

				});

			ServletContextPool.put(StringPool.BLANK, mockServletContext);

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

			ServiceTestUtil.initStaticServices();
		}

		ServiceTestUtil.initServices();

		ServiceTestUtil.initPermissions();

		return null;
	}

	protected MainServletTestCallback() {
	}

	protected boolean isArquillianTest(Description description) {
		RunWith runWith = description.getAnnotation(RunWith.class);

		if (runWith == null) {
			return false;
		}

		Class<? extends Runner> runnerClass = runWith.value();

		String runnerClassName = runnerClass.getName();

		if (runnerClassName.equals(
				"com.liferay.arquillian.extension.junit.bridge.junit." +
					"Arquillian")) {

			return true;
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		MainServletTestCallback.class);

	private static MainServlet _mainServlet;

}