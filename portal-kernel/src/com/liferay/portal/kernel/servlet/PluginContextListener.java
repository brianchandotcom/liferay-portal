/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BasePortalLifecycle;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Brian Wing Shun Chan
 */
public class PluginContextListener
	extends BasePortalLifecycle
	implements ServletContextAttributeListener, ServletContextListener {

	public static final String PLUGIN_CLASS_LOADER = "PLUGIN_CLASS_LOADER";

	@Override
	public void attributeAdded(
		ServletContextAttributeEvent servletContextAttributeEvent) {

		if (servletContextAttributeEvent.getServletContext() !=
				servletContext) {

			return;
		}

		String name = servletContextAttributeEvent.getName();

		if (_addedPluginClassLoader && name.equals(PLUGIN_CLASS_LOADER) &&
			(servletContextAttributeEvent.getValue() != pluginClassLoader)) {

			if (_log.isWarnEnabled()) {
				_log.warn(
					"Preventing the addition of another plugin class loader");
			}

			servletContext.setAttribute(PLUGIN_CLASS_LOADER, pluginClassLoader);
		}
		else if (!_addedPluginClassLoader && name.equals(PLUGIN_CLASS_LOADER)) {
			_addedPluginClassLoader = true;
		}
	}

	@Override
	public void attributeRemoved(
		ServletContextAttributeEvent servletContextAttributeEvent) {

		if (servletContextAttributeEvent.getServletContext() !=
				servletContext) {

			return;
		}

		String name = servletContextAttributeEvent.getName();

		if (_addedPluginClassLoader && name.equals(PLUGIN_CLASS_LOADER)) {
			if (_log.isWarnEnabled()) {
				_log.warn("Preventing the removal of the plugin class loader");
			}

			servletContext.setAttribute(PLUGIN_CLASS_LOADER, pluginClassLoader);
		}
	}

	@Override
	public void attributeReplaced(
		ServletContextAttributeEvent servletContextAttributeEvent) {

		if (servletContextAttributeEvent.getServletContext() !=
				servletContext) {

			return;
		}

		String name = servletContextAttributeEvent.getName();

		if (_addedPluginClassLoader && name.equals(PLUGIN_CLASS_LOADER)) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Preventing the replacement of the plugin class loader");
			}

			servletContext.removeAttribute(PLUGIN_CLASS_LOADER);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		portalDestroy();

		if (_classLoaderRegistered) {
			ServletContextClassLoaderPool.unregister(
				servletContext.getServletContextName());
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		servletContext = servletContextEvent.getServletContext();

		Thread currentThread = Thread.currentThread();

		pluginClassLoader = currentThread.getContextClassLoader();

		String servletContextName = servletContext.getServletContextName();

		if (ServletContextClassLoaderPool.getClassLoader(servletContextName) ==
				null) {

			ServletContextClassLoaderPool.register(
				servletContextName, pluginClassLoader);

			_classLoaderRegistered = true;
		}

		servletContext.setAttribute(PLUGIN_CLASS_LOADER, pluginClassLoader);

		ServletContextPool.put(servletContextName, servletContext);

		registerPortalLifecycle();
	}

	@Override
	protected void doPortalDestroy() throws Exception {
		PluginContextLifecycleThreadLocal.setDestroying(true);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != pluginClassLoader) {
			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			fireUndeployEvent();
		}
		finally {
			PluginContextLifecycleThreadLocal.setDestroying(false);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	protected void doPortalInit() throws Exception {
		PluginContextLifecycleThreadLocal.setInitializing(true);

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		if (contextClassLoader != pluginClassLoader) {
			currentThread.setContextClassLoader(pluginClassLoader);
		}

		try {
			fireDeployEvent();
		}
		finally {
			PluginContextLifecycleThreadLocal.setInitializing(false);

			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	protected void fireDeployEvent() {
		HotDeployUtil.fireDeployEvent(
			new HotDeployEvent(servletContext, pluginClassLoader));
	}

	protected void fireUndeployEvent() {
		HotDeployUtil.fireUndeployEvent(
			new HotDeployEvent(servletContext, pluginClassLoader));
	}

	protected ClassLoader pluginClassLoader;
	protected ServletContext servletContext;

	private static final Log _log = LogFactoryUtil.getLog(
		PluginContextListener.class);

	private boolean _addedPluginClassLoader;
	private boolean _classLoaderRegistered;

}