/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.deploy.hot;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.plugin.PluginPackage;
import com.liferay.portal.kernel.util.PropertiesUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

/**
 * @author Ivica Cardic
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class HotDeployEvent {

	public HotDeployEvent(
		ServletContext servletContext, ClassLoader contextClassLoader) {

		_servletContext = servletContext;
		_contextClassLoader = contextClassLoader;

		try {
			initDependentServletContextNames();
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	public ClassLoader getContextClassLoader() {
		return _contextClassLoader;
	}

	public Set<String> getDependentServletContextNames() {
		return _dependentServletContextNames;
	}

	public PluginPackage getPluginPackage() {
		return _pluginPackage;
	}

	public ServletContext getServletContext() {
		return _servletContext;
	}

	public String getServletContextName() {
		return _servletContext.getServletContextName();
	}

	public void setPluginPackage(PluginPackage pluginPackage) {
		_pluginPackage = pluginPackage;
	}

	protected void initDependentServletContextNames() throws IOException {
		if (!DependencyManagementThreadLocal.isEnabled()) {
			return;
		}

		InputStream is = _servletContext.getResourceAsStream(
			"/WEB-INF/liferay-plugin-package.properties");

		if (is == null) {
			return;
		}

		String propertiesString = StringUtil.read(is);

		Properties properties = PropertiesUtil.load(propertiesString);

		String[] requiredDeploymentContexts = StringUtil.split(
			properties.getProperty("required-deployment-contexts"));

		if ((requiredDeploymentContexts.length > 0) && _log.isInfoEnabled()) {
			_log.info(
				"Plugin " + _servletContext.getServletContextName() +
					" requires " +
					StringUtil.merge(requiredDeploymentContexts, ", "));
		}

		for (String requiredDeploymentContext : requiredDeploymentContexts) {
			_dependentServletContextNames.add(requiredDeploymentContext.trim());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(HotDeployEvent.class);

	private ClassLoader _contextClassLoader;
	private Set<String> _dependentServletContextNames = new HashSet<String>();
	private PluginPackage _pluginPackage;
	private ServletContext _servletContext;

}