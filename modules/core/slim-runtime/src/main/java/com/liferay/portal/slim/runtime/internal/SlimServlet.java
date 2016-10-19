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

package com.liferay.portal.slim.runtime.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import com.liferay.portal.events.EventsProcessorUtil;
import com.liferay.portal.events.ShutdownHook;
import com.liferay.portal.kernel.cache.thread.local.Lifecycle;
import com.liferay.portal.kernel.cache.thread.local.ThreadLocalCacheManager;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ReleaseInfo;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PropsValues;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceRegistration;
import com.liferay.registry.collections.ServiceTrackerCollections;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;

/**
 * @author Raymond Augé
 */
public class SlimServlet extends HttpServlet {

	@Override
	public void destroy() {
		_moduleServiceLifecycleServiceRegistrationPortal.unregister();

		_servletContextServiceRegistration.unregister();

		_moduleServiceLifecycleServiceRegistrationDB.unregister();
	}

	@Override
	public void init() throws ServletException {
		if (_log.isDebugEnabled()) {
			_log.debug("Initialize");
		}

		ServletContext servletContext = getServletContext();

		servletContext.setAttribute(SlimServlet.class.getName(), Boolean.TRUE);

		super.init();

		if (_log.isDebugEnabled()) {
			_log.debug("Patching - NOT SUPPORTED");
			_log.debug("Process startup events");
		}

		try {
			processStartupEvents();
		}
		catch (Exception e) {
			_log.error(e, e);

			System.out.println(
				"Stopping the server due to unexpected startup errors");

			if (e instanceof ServletException) {
				throw (ServletException)e;
			}

			throw new ServletException(e);
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Server Detector - NOT SUPPORTED");
			_log.debug("Plugin Package - NOT SUPPORTED");
			_log.debug("Portlets - NOT SUPPORTED");
			_log.debug("Layout Templates - NOT SUPPORTED");
			_log.debug("Social - NOT SUPPORTED");
			_log.debug("Themes - NOT SUPPORTED");
			_log.debug("Web Settings - NOT SUPPORTED");
			_log.debug("EXT - NOT SUPPORTED");
			_log.debug("Global Startup Events - NOT SUPPORTED");
			_log.debug("Resource Actions - NOT SUPPORTED");
			_log.debug("Companies - NOT SUPPORTED");
			_log.debug("Setup Wizard - NOT SUPPORTED");
			_log.debug("Plugins (Legacy) - NOT SUPPORTED");
		}

		servletContext.setAttribute(WebKeys.STARTUP_FINISHED, true);

		registerPortalInitialized();

		ThreadLocalCacheManager.clearAll(Lifecycle.REQUEST);
	}

	@Override
	protected void service(
			HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException {

		if (_log.isDebugEnabled()) {
			_log.debug("Process service request");
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Shutdown Request - NOT SUPPORTED");
			_log.debug("Maintenance Request - NOT SUPPORTED");
			_log.debug("Company - NOT SUPPORTED");
			_log.debug("Group - NOT SUPPORTED");
			_log.debug("Set portal port");
		}

		PortalUtil.setPortalInetSocketAddresses(request);

		if (_log.isDebugEnabled()) {
			_log.debug("Check variables - NOT SUPPORTED");
			_log.debug("Portlet Request Processor - NOT SUPPORTED");
			_log.debug("Tiles Definitions Factory - NOT SUPPORTED");
			_log.debug("Handle non-serializable request - NOT SUPPORTED");
			_log.debug("Encrypt request - NOT SUPPORTED");
			_log.debug("User - NOT SUPPORTED");
			_log.debug("Login Pre Events - NOT SUPPORTED");
			_log.debug("Login Post Events - NOT SUPPORTED");
			_log.debug("Session Thread Local - NOT SUPPORTED");
			_log.debug("Absolute Redirect checking - NOT SUPPORTED");
			_log.debug("ThemeDisplay - NOT SUPPORTED");
			_log.debug("Service Pre Events - NOT SUPPORTED");
		}

		if (_servlets.isEmpty()) {
			response.sendError(
				HttpServletResponse.SC_SERVICE_UNAVAILABLE,
				"Module framework is unavailable");

			return;
		}

		HttpServlet httpServlet = _servlets.get(0);

		httpServlet.service(request, response);

		if (_log.isDebugEnabled()) {
			_log.debug("Service Post Events - NOT SUPPORTED");
		}
	}

	protected void processStartupEvents() throws Exception {

		// Print release information

		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		try (InputStream inputStream = classLoader.getResourceAsStream(
				"com/liferay/portal/events/dependencies/startup.txt")) {

			System.out.println(IOUtils.toString(inputStream, StringPool.UTF8));
		}

		System.out.println("Starting " + ReleaseInfo.getReleaseInfo() + "\n");

		if (_log.isDebugEnabled()) {
			_log.debug("Portal Resiliency - NOT SUPPORTED");
		}

		// Shutdown hook

		if (_log.isDebugEnabled()) {
			_log.debug("Add shutdown hook");
		}

		Runtime runtime = Runtime.getRuntime();

		runtime.addShutdownHook(new Thread(new ShutdownHook()));

		if (_log.isDebugEnabled()) {
			_log.debug("Indexer - NOT SUPPORTED");
		}

		// MySQL version

		DB db = DBManagerUtil.getDB();

		if ((db.getDBType() == DBType.MYSQL) &&
			GetterUtil.getFloat(db.getVersionString()) < 5.6F) {

			throw new ServletException(
				"Please upgrade to at least MySQL 5.6.4. The portal no " +
					"longer supports older versions of MySQL.");
		}

		// Check required build number

		if (_log.isDebugEnabled()) {
			_log.debug("Check required build number");
		}

		DBUpgrader.checkRequiredBuildNumber(ReleaseInfo.getParentBuildNumber());

		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("module.service.lifecycle", "database.initialized");
		properties.put("service.vendor", ReleaseInfo.getVendor());
		properties.put("service.version", ReleaseInfo.getVersion());

		_moduleServiceLifecycleServiceRegistrationDB = registry.registerService(
			ModuleServiceLifecycle.class, new ModuleServiceLifecycle() {},
			properties);

		// Check class names

		if (_log.isDebugEnabled()) {
			_log.debug("Check class names");
		}

		ClassNameLocalServiceUtil.checkClassNames();

		if (_log.isDebugEnabled()) {
			_log.debug("Verify - NOT SUPPORTED");
			_log.debug("Jsp Factory Swapper - NOT SUPPORTED");
			_log.debug("Jericho - NOT SUPPORTED");
		}
	}

	protected void registerPortalInitialized() {
		Registry registry = RegistryUtil.getRegistry();

		Map<String, Object> properties = new HashMap<>();

		properties.put("module.service.lifecycle", "portal.initialized");
		properties.put("service.vendor", ReleaseInfo.getVendor());
		properties.put("service.version", ReleaseInfo.getVersion());

		_moduleServiceLifecycleServiceRegistrationPortal =
			registry.registerService(
				ModuleServiceLifecycle.class, new ModuleServiceLifecycle() {},
				properties);

		properties = new HashMap<>();

		properties.put("bean.id", ServletContext.class.getName());
		properties.put("original.bean", Boolean.TRUE);
		properties.put("service.vendor", ReleaseInfo.getVendor());

		_servletContextServiceRegistration = registry.registerService(
			ServletContext.class, getServletContext(), properties);
	}

	private static final Log _log = LogFactoryUtil.getLog(SlimServlet.class);

	private ServiceRegistration<ModuleServiceLifecycle>
		_moduleServiceLifecycleServiceRegistrationDB;
	private ServiceRegistration<ModuleServiceLifecycle>
		_moduleServiceLifecycleServiceRegistrationPortal;
	private ServiceRegistration<ServletContext>
		_servletContextServiceRegistration;

	private final List<HttpServlet> _servlets =
		ServiceTrackerCollections.openList(
			HttpServlet.class,
			"(&(bean.id=" + HttpServlet.class.getName() +
				")(original.bean=*))");

}