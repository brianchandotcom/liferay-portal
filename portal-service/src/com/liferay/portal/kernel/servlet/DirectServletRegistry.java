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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.io.File;

import java.lang.reflect.Method;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

/**
 * @author Shuyang Zhou
 */
public class DirectServletRegistry {

	public static void clearServlets() {
		_instance._clearServlets();
	}

	public static Servlet getServlet(String path) {
		return _instance._getServlet(path);
	}

	public static void putServlet(String path, Servlet servlet) {
		_instance._putServlet(path, servlet);
	}

	private DirectServletRegistry() {
	}

	private void _clearServlets() {
		_servletInfos.clear();
	}

	private long _getFileLastModified(String path, Servlet servlet) {
		ServletConfig servletConfig = servlet.getServletConfig();

		ServletContext servletContext = servletConfig.getServletContext();

		String rootPath = servletContext.getRealPath(StringPool.BLANK);

		File file = new File(rootPath, path);

		return file.lastModified();
	}

	private Servlet _getServlet(String path) {
		ServletInfo servletInfo = _servletInfos.get(path);

		if (servletInfo == null) {
			return null;
		}

		Servlet servlet = servletInfo.getServlet();

		if (_DIRECT_SERVLET_CONTEXT_RELOAD) {
			long lastModified = _getFileLastModified(path, servlet);

			if ((lastModified == 0) ||
				(lastModified != servletInfo.getLastModified())) {

				_servletInfos.remove(path);

				servlet = null;

				if (_log.isDebugEnabled()) {
					_log.debug("Reload " + path);
				}
			}
			else {
				servlet = _reloadDependents(path, servlet, servletInfo);
			}
		}

		return servlet;
	}

	private void _putServlet(String path, Servlet servlet) {
		if (_servletInfos.containsKey(path)) {
			return;
		}

		long lastModified = 1;

		if (_DIRECT_SERVLET_CONTEXT_RELOAD) {
			lastModified = _getFileLastModified(path, servlet);
		}

		if (lastModified > 0) {
			ServletInfo servletInfo = new ServletInfo();

			servletInfo.setLastModified(lastModified);
			servletInfo.setServlet(servlet);

			_servletInfos.put(path, servletInfo);
		}
	}

	private Servlet _reloadDependents(
		String path, Servlet servlet, ServletInfo servletInfo) {

		try {
			if (!_reloadDependents) {
				return servlet;
			}

			Method method = ReflectionUtil.getDeclaredMethod(
				servlet.getClass(), "getDependants");

			Collection<String> dependants = null;

			String jasperVersion = JasperVersionDetector.getJasperVersion();

			if (jasperVersion.contains("7.0")) {
				Map<String, ?> dependantsMap = (Map<String, ?>)method.invoke(
					servlet);

				if (dependantsMap != null) {
					dependants = dependantsMap.keySet();
				}
			}
			else {
				dependants = (List<String>)method.invoke(servlet);
			}

			if (dependants == null) {
				return servlet;
			}

			boolean reloadServlet = false;

			for (String dependant : dependants) {
				long lastModified = _getFileLastModified(dependant, servlet);

				Long previousLastModified = _dependantTimestamps.get(dependant);

				if (previousLastModified == null) {
					previousLastModified = lastModified;
				}

				if ((lastModified == 0) ||
					(lastModified != previousLastModified.longValue())) {

					reloadServlet = true;

					_dependantTimestamps.put(dependant, lastModified);

					if (_log.isDebugEnabled()) {
						_log.debug("Reload dependent " + dependant);
					}
				}
			}

			if (reloadServlet) {
				_servletInfos.remove(path);

				servlet = null;
			}
		}
		catch (NoSuchMethodException nsme) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Reloading of dependant JSP is disabled because your " +
						"Servlet container is not a variant of Jasper");
			}

			_reloadDependents = false;
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		return servlet;
	}

	private static final boolean _DIRECT_SERVLET_CONTEXT_RELOAD =
		GetterUtil.getBoolean(
			PropsUtil.get(PropsKeys.DIRECT_SERVLET_CONTEXT_RELOAD));

	private static Log _log = LogFactoryUtil.getLog(
		DirectServletRegistry.class);

	private static DirectServletRegistry _instance =
		new DirectServletRegistry();

	private static boolean _reloadDependents = true;

	private Map<String, Long> _dependantTimestamps =
		new ConcurrentHashMap<String, Long>();
	private Map<String, ServletInfo> _servletInfos =
		new ConcurrentHashMap<String, ServletInfo>();

	private class ServletInfo {

		public long getLastModified() {
			return _lastModified;
		}

		public Servlet getServlet() {
			return _servlet;
		}

		public void setLastModified(long lastModified) {
			_lastModified = lastModified;
		}

		public void setServlet(Servlet servlet) {
			_servlet = servlet;
		}

		private long _lastModified;
		private Servlet _servlet;

	}

}