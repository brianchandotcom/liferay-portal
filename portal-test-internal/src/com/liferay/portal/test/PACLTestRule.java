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

package com.liferay.portal.test;

import com.liferay.portal.deploy.hot.HookHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterHelper;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.spring.context.PortletContextLoaderListener;
import com.liferay.portal.test.mock.AutoDeployMockServletContext;
import com.liferay.portal.test.runners.PACLIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockServletContext;

/**
 * @author Raymond Augé
 * @author Shuyang Zhou
 */
public class PACLTestRule implements TestRule {

	public static final PACLTestRule INSTANCE = new PACLTestRule();

	@Override
	public Statement apply(
		final Statement statement, final Description description) {

		return new Statement() {

			@Override
			public void evaluate() throws Throwable {
				ReflectionTestUtil.setFieldValue(
					description, "fTestClass",
					PACLIntegrationJUnitTestRunner.getCurrentTestClass());

				HotDeployEvent hotDeployEvent = before(
					description.getTestClass());

				try {
					statement.evaluate();
				}
				finally {
					after(hotDeployEvent);
				}
			}

		};
	}

	protected void after(HotDeployEvent hotDeployEvent) {
		HotDeployUtil.fireUndeployEvent(hotDeployEvent);

		PortletContextLoaderListener portletContextLoaderListener =
			new PortletContextLoaderListener();

		ClassLoaderPool.register(
			hotDeployEvent.getServletContextName(),
			hotDeployEvent.getContextClassLoader());
		PortletClassLoaderUtil.setServletContextName(
			hotDeployEvent.getServletContextName());

		try {
			portletContextLoaderListener.contextDestroyed(
				new ServletContextEvent(hotDeployEvent.getServletContext()));
		}
		finally {
			ClassLoaderPool.unregister(hotDeployEvent.getServletContextName());
			PortletClassLoaderUtil.setServletContextName(null);
		}
	}

	protected HotDeployEvent before(Class<?> testClass) {
		ServletContext servletContext = ServletContextPool.get(
			PortalUtil.getServletContextName());

		if (servletContext == null) {
			servletContext = new AutoDeployMockServletContext(
				new FileSystemResourceLoader());

			servletContext.setAttribute(
				InvokerFilterHelper.class.getName(), new InvokerFilterHelper());

			ServletContextPool.put(PortalUtil.getPathContext(), servletContext);
		}

		HotDeployUtil.reset();

		HotDeployUtil.registerListener(new HookHotDeployListener());

		HotDeployUtil.setCapturePrematureEvents(false);

		PortalLifecycleUtil.flushInits();

		ClassLoader classLoader = testClass.getClassLoader();

		MockServletContext mockServletContext = new MockServletContext(
			new PACLResourceLoader(classLoader));

		mockServletContext.setServletContextName("a-test-hook");

		HotDeployEvent hotDeployEvent = getHotDeployEvent(
			mockServletContext, classLoader);

		HotDeployUtil.fireDeployEvent(hotDeployEvent);

		PortletContextLoaderListener portletContextLoaderListener =
			new PortletContextLoaderListener();

		ClassLoaderPool.register(
			hotDeployEvent.getServletContextName(),
			hotDeployEvent.getContextClassLoader());
		PortletClassLoaderUtil.setServletContextName(
			hotDeployEvent.getServletContextName());

		try {
			portletContextLoaderListener.contextInitialized(
				new ServletContextEvent(mockServletContext));
		}
		finally {
			ClassLoaderPool.unregister(hotDeployEvent.getServletContextName());
			PortletClassLoaderUtil.setServletContextName(null);
		}

		return hotDeployEvent;
	}

	protected HotDeployEvent getHotDeployEvent(
		ServletContext servletContext, ClassLoader classLoader) {

		boolean dependencyManagementEnabled =
			DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			return new HotDeployEvent(servletContext, classLoader);
		}
		finally {
			DependencyManagementThreadLocal.setEnabled(
				dependencyManagementEnabled);
		}
	}

	private PACLTestRule() {
	}

	private static class PACLResourceLoader implements ResourceLoader {

		public PACLResourceLoader(ClassLoader classLoader) {
			_classLoader = classLoader;
		}

		@Override
		public ClassLoader getClassLoader() {
			return _classLoader;
		}

		@Override
		public Resource getResource(String location) {
			ClassLoader classLoader = getClassLoader();

			return new ClassPathResource(
				PACLIntegrationJUnitTestRunner.RESOURCE_PATH + location,
				classLoader);
		}

		private final ClassLoader _classLoader;

	}

}