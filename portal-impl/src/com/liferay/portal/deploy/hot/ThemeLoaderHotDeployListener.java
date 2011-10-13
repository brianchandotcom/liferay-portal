/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.deploy.hot;

import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.theme.ThemeLoaderFactory;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 */
public class ThemeLoaderHotDeployListener extends BaseHotDeployListener {

	public void invokeDeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeDeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error registering theme loader for ", t);
		}
	}

	public void invokeUndeploy(HotDeployEvent event) throws HotDeployException {
		try {
			doInvokeUndeploy(event);
		}
		catch (Throwable t) {
			throwHotDeployException(
				event, "Error unregistering theme loader for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String[] xmls = new String[] {
			HttpUtil.URLtoString(
				servletContext.getResource("/WEB-INF/liferay-theme-loader.xml"))
		};

		if (xmls[0] == null) {
			return;
		}

		logRegistration(servletContextName);

		ThemeLoaderFactory.init(servletContextName, servletContext, xmls);
	}

	protected void doInvokeUndeploy(HotDeployEvent event) throws Exception {
		ServletContext servletContext = event.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		boolean value = ThemeLoaderFactory.destroy(servletContextName);

		if (!value) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Unregistering theme loader for " + servletContextName);
		}

		ServletContextPool.remove(servletContextName);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Theme loader for " + servletContextName +
					" unregistered successfully");
		}
	}

	protected void logRegistration(String servletContextName) {
		if (_log.isInfoEnabled()) {
			_log.info("Registering theme loader for " + servletContextName);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ThemeLoaderHotDeployListener.class);

}