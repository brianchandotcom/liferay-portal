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

import com.liferay.portal.deploy.hot.HookHotDeployListener;
import com.liferay.portal.deploy.hot.IndexerPostProcessorRegistry;
import com.liferay.portal.deploy.hot.SchedulerEntryRegistry;
import com.liferay.portal.deploy.hot.ServiceWrapperRegistry;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.portlet.PortletClassLoaderUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.servlet.filters.invoker.InvokerFilterHelper;
import com.liferay.portal.kernel.test.rule.BaseTestRule;
import com.liferay.portal.kernel.util.ClassLoaderPool;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalLifecycleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.spring.context.PortletContextLoaderListener;
import com.liferay.portal.test.mock.AutoDeployMockServletContext;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;

import java.lang.reflect.Method;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import java.security.CodeSource;
import java.security.ProtectionDomain;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.naming.Context;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

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
public class PACLTestRule extends BaseTestRule<HotDeployEvent, Object> {

	public static final String RESOURCE_PATH =
		"com/liferay/portal/security/pacl/test/dependencies";

	@Override
	protected void afterClass(
		Description description, HotDeployEvent hotDeployEvent) {

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

	@Override
	protected HotDeployEvent beforeClass(Description description)
		throws ReflectiveOperationException {

		_testClass = _loadTestClass(description.getTestClass());
		_instance = _testClass.newInstance();

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

		ClassLoader classLoader = _testClass.getClassLoader();

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

	@Override
	protected void invokeStatement(Statement statement, Description description)
		throws Throwable {

		String methodName = description.getMethodName();

		if (methodName == null) {
			statement.evaluate();

			return;
		}

		Method method = _testClass.getMethod(description.getMethodName());

		method.invoke(_instance);
	}

	private static Class<?> _loadTestClass(Class<?> clazz)
		throws ClassNotFoundException {

		ProtectionDomain protectionDomain = clazz.getProtectionDomain();

		CodeSource codeSource = protectionDomain.getCodeSource();

		ClassLoader classLoader = new PACLClassLoader(
			new URL[] {codeSource.getLocation()}, clazz.getClassLoader());

		return Class.forName(clazz.getName(), true, classLoader);
	}

	private static final String _PACKAGE_PATH =
		"com.liferay.portal.security.pacl.test.";

	static {
		URL resource = PACLTestRule.class.getResource("pacl-test.properties");

		if (resource != null) {
			System.setProperty("external-properties", resource.getPath());
		}

		System.setProperty(
			Context.INITIAL_CONTEXT_FACTORY,
			"org.apache.naming.java.javaURLContextFactory");

		System.setProperty("catalina.base", ".");

		List<String> configLocations = ListUtil.fromArray(
			PropsUtil.getArray(PropsKeys.SPRING_CONFIGS));

		InitUtil.initWithSpring(configLocations, true);

		ServiceTestUtil.initServices();
		ServiceTestUtil.initPermissions();

		new IndexerPostProcessorRegistry();
		new SchedulerEntryRegistry();
		new ServiceWrapperRegistry();
	}

	private Object _instance;
	private Class<?> _testClass;

	private static class PACLClassLoader extends URLClassLoader {

		public PACLClassLoader(URL[] urls, ClassLoader parentClassLoader) {
			super(urls, parentClassLoader);
		}

		@Override
		public URL findResource(String name) {
			if (_urls.containsKey(name)) {
				return _urls.get(name);
			}

			URL resource = null;

			if (!name.contains(RESOURCE_PATH)) {
				String newName = name;

				if (!newName.startsWith(StringPool.SLASH)) {
					newName = StringPool.SLASH.concat(newName);
				}

				newName = RESOURCE_PATH.concat(newName);

				resource = super.findResource(newName);
			}

			if ((resource == null) && !name.contains(RESOURCE_PATH)) {
				String newName = name;

				if (!newName.startsWith(StringPool.SLASH)) {
					newName = StringPool.SLASH.concat(newName);
				}

				newName = RESOURCE_PATH.concat("/WEB-INF/classes").concat(
					newName);

				resource = super.findResource(newName);
			}

			if (resource == null) {
				resource = super.findResource(name);
			}

			if (resource != null) {
				_urls.put(name, resource);
			}

			return resource;
		}

		@Override
		public URL getResource(String name) {
			if (name.equals(
					"com/liferay/util/bean/PortletBeanLocatorUtil.class")) {

				URL url = findResource("/");

				String path = url.getPath();

				path = path.substring(
					0, path.length() - RESOURCE_PATH.length() - 1);

				path = path.concat(name);

				try {
					return new URL("file", null, path);
				}
				catch (MalformedURLException murle) {
				}
			}

			URL url = findResource(name);

			if (url != null) {
				return url;
			}

			return super.getResource(name);
		}

		@Override
		public Class<?> loadClass(String name) throws ClassNotFoundException {
			if (name.startsWith(_PACKAGE_PATH)) {
				if (_classes.containsKey(name)) {
					return _classes.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classes.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name);
		}

		@Override
		protected synchronized Class<?> loadClass(String name, boolean resolve)
			throws ClassNotFoundException {

			if (name.startsWith(_PACKAGE_PATH)) {
				if (_classes.containsKey(name)) {
					return _classes.get(name);
				}

				Class<?> clazz = super.findClass(name);

				_classes.put(name, clazz);

				return clazz;
			}

			return super.loadClass(name, resolve);
		}

		private final Map<String, Class<?>> _classes =
			new ConcurrentHashMap<>();
		private final Map<String, URL> _urls = new ConcurrentHashMap<>();

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

			return new ClassPathResource(RESOURCE_PATH + location, classLoader);
		}

		private final ClassLoader _classLoader;

	}

}