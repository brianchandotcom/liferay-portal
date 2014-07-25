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

package com.liferay.portal.http.service.internal;

import com.liferay.portal.http.service.internal.definition.FilterDefinition;
import com.liferay.portal.http.service.internal.definition.ListenerDefinition;
import com.liferay.portal.http.service.internal.definition.ServletDefinition;
import com.liferay.portal.http.service.internal.definition.WebXMLDefinition;
import com.liferay.portal.http.service.internal.definition.WebXMLDefinitionLoader;
import com.liferay.portal.kernel.deploy.hot.DependencyManagementThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.DocumentException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.Servlet;

import org.eclipse.equinox.http.servlet.ExtendedHttpService;

import org.osgi.framework.Bundle;
import org.osgi.framework.wiring.BundleWiring;
import org.osgi.service.http.context.ServletContextHelper;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WabBundleProcessor {

	public WabBundleProcessor(
			Bundle bundle, String servletContextName, String contextPath,
			ExtendedHttpService extendedHttpService)
		throws DocumentException {

		_bundle = bundle;
		_servletContextName = servletContextName;
		_contextPath = contextPath;
		_extendedHttpService = extendedHttpService;

		BundleWiring bundleWiring = _bundle.adapt(BundleWiring.class);

		_bundleClassLoader = bundleWiring.getClassLoader();

		_webXMLDefinitionLoader = new WebXMLDefinitionLoader();
	}

	public void destroy() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(_bundleClassLoader);

			destroyServlets();
			destroyFilters();
			destroyListeners();
			destroyContext();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	public void init() throws Exception {
		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		Boolean enabled = DependencyManagementThreadLocal.isEnabled();

		try {
			DependencyManagementThreadLocal.setEnabled(false);

			currentThread.setContextClassLoader(_bundleClassLoader);

			readConfiguration(_bundle);
			initContext();
			Exception e = initListeners();

			if (e != null) {
				destroy();

				throw e;
			}

			initFilters();
			initServlets();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);

			DependencyManagementThreadLocal.setEnabled(enabled);
		}
	}

	protected void destroyContext() {
		try {
			_extendedHttpService.unregisterServletContextHelper(
				_servletContextHelper);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void destroyFilters() {
		Map<String, FilterDefinition> filterDefinitions =
			_webXML.getFilterDefinitions();

		List<FilterDefinition> list = new ArrayList<FilterDefinition>(
			filterDefinitions.values());

		Collections.reverse(list);

		for (FilterDefinition filterDefinition : list) {
			try {
				_extendedHttpService.unregisterFilter(
					filterDefinition.getFilter(), _servletContextName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		filterDefinitions.clear();
	}

	protected void destroyListeners() {
		List<ListenerDefinition> listenerDefinitions =
			_webXML.getListenerDefinitions();

		Collections.reverse(listenerDefinitions);

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				_extendedHttpService.unregisterListener(
					listenerDefinition.getListener(), _servletContextName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		listenerDefinitions.clear();
	}

	protected void destroyServlets() {
		Map<String, ServletDefinition> servlets =
			_webXML.getServletDefinitions();

		List<ServletDefinition> list = new ArrayList<ServletDefinition>(
			servlets.values());

		Collections.reverse(list);

		for (ServletDefinition servletDefinition : list) {
			Servlet servlet = servletDefinition.getServlet();

			try {
				if (servlet instanceof WabResourceServlet) {
					_extendedHttpService.unregister(
						servletDefinition.getURLPatterns().get(0),
						_servletContextName);
				}
				else {
					_extendedHttpService.unregisterServlet(
						servlet, _servletContextName);
				}
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void initContext() throws Exception {
		Map<String, String> contextParameters = _webXML.getContextParameters();

		_servletContextHelper = new WabServletContextHelper(_bundle);

		_extendedHttpService.registerServletContextHelper(
			_servletContextHelper, _bundle, new String[] {_servletContextName},
			_contextPath, contextParameters);
	}

	protected void initFilters() {
		Map<String, FilterDefinition> filterDefinitions =
			_webXML.getFilterDefinitions();

		for (Map.Entry<String, FilterDefinition> entry :
				filterDefinitions.entrySet()) {

			FilterDefinition filterDefinition = entry.getValue();

			try {
				_extendedHttpService.registerFilter(
					filterDefinition.getFilter(),
					filterDefinition.getName(),
					filterDefinition.getURLPatterns().toArray(new String[0]),
					filterDefinition.getServletNames().toArray(new String[0]),
					filterDefinition.getDispatchers().toArray(new String[0]),
					filterDefinition.isAsyncSupported(),
					filterDefinition.getPriority(),
					filterDefinition.getInitParameters(), _servletContextName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected Exception initListeners() {
		List<ListenerDefinition> listenerDefinitions =
			_webXML.getListenerDefinitions();

		for (ListenerDefinition listenerDefinition : listenerDefinitions) {
			try {
				_extendedHttpService.registerListener(
					listenerDefinition.getListener(), _servletContextName);
			}
			catch (Exception e) {
				return e;
			}
		}

		return null;
	}

	protected void initServlets() {
		Map<String, ServletDefinition> servletDefinitions =
			_webXML.getServletDefinitions();

		for (Entry<String, ServletDefinition> entry :
				servletDefinitions.entrySet()) {

			ServletDefinition servletDefinition = entry.getValue();

			Servlet servlet = servletDefinition.getServlet();

			try {
				if (servlet instanceof WabResourceServlet) {
					String prefix = MapUtil.getString(
						servletDefinition.getInitParameters(), "prefix",
						StringPool.SLASH);

					_extendedHttpService.registerResources(
						servletDefinition.getURLPatterns().toArray(
							new String[0]), prefix, _servletContextName);

					continue;
				}

				_extendedHttpService.registerServlet(
					servlet, servletDefinition.getName(),
					servletDefinition.getURLPatterns().toArray(new String[0]),
					servletDefinition.getErrorPages().toArray(new String[0]),
					servletDefinition.isAsyncSupported(),
					servletDefinition.getInitParameters(), _servletContextName);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
	}

	protected void readConfiguration(Bundle bundle)
		throws Exception {

		_webXML = _webXMLDefinitionLoader.loadWebXML(bundle);
	}

	private static Log _log = LogFactoryUtil.getLog(WabBundleProcessor.class);

	private Bundle _bundle;
	private ClassLoader _bundleClassLoader;
	private String _contextPath;
	private ExtendedHttpService _extendedHttpService;
	private ServletContextHelper _servletContextHelper;
	private String _servletContextName;
	private WebXMLDefinition _webXML;
	private WebXMLDefinitionLoader _webXMLDefinitionLoader;

}