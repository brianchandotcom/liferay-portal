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

package com.liferay.portal.deploy.hot;

import com.liferay.petra.io.StreamUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.deploy.hot.BaseHotDeployListener;
import com.liferay.portal.kernel.deploy.hot.HotDeployEvent;
import com.liferay.portal.kernel.deploy.hot.HotDeployException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Theme;
import com.liferay.portal.kernel.service.ThemeLocalServiceUtil;
import com.liferay.portal.kernel.servlet.FileTimestampUtil;
import com.liferay.portal.kernel.template.TemplateConstants;
import com.liferay.portal.kernel.template.TemplateResourceLoaderUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.WebKeys;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

/**
 * @author Brian Wing Shun Chan
 * @author Brian Myunghun Kim
 * @author Ivica Cardic
 */
public class ThemeHotDeployListener extends BaseHotDeployListener {

	@Override
	public void invokeDeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeDeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error registering themes for ", t);
		}
	}

	@Override
	public void invokeUndeploy(HotDeployEvent hotDeployEvent)
		throws HotDeployException {

		try {
			doInvokeUndeploy(hotDeployEvent);
		}
		catch (Throwable t) {
			throwHotDeployException(
				hotDeployEvent, "Error unregistering themes for ", t);
		}
	}

	protected void doInvokeDeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + servletContextName);
		}

		String[] xmls = {
			StreamUtil.toString(
				servletContext.getResourceAsStream(
					"/WEB-INF/liferay-look-and-feel.xml"))
		};

		if (xmls[0] == null) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Registering themes for " + servletContextName);
		}

		FileTimestampUtil.reset(servletContext);

		List<Theme> themes = ThemeLocalServiceUtil.init(
			servletContextName, servletContext, null, true, xmls,
			hotDeployEvent.getPluginPackage());

		_themes.put(servletContextName, themes);

		servletContext.setAttribute(WebKeys.PLUGIN_THEMES, themes);

		if (_log.isInfoEnabled()) {
			if (themes.size() == 1) {
				_log.info(
					"1 theme for " + servletContextName +
						" is available for use");
			}
			else {
				_log.info(
					StringBundler.concat(
						themes.size(), " themes for ", servletContextName,
						" are available for use"));
			}
		}
	}

	protected void doInvokeUndeploy(HotDeployEvent hotDeployEvent)
		throws Exception {

		ServletContext servletContext = hotDeployEvent.getServletContext();

		String servletContextName = servletContext.getServletContextName();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking undeploy for " + servletContextName);
		}

		List<Theme> themes = _themes.remove(servletContextName);

		if (themes != null) {
			if (_log.isDebugEnabled()) {
				_log.debug("Unregistering themes for " + servletContextName);
			}

			try {
				ThemeLocalServiceUtil.uninstallThemes(themes);
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}
		else {
			return;
		}

		// LEP-2057

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			currentThread.setContextClassLoader(
				PortalClassLoaderUtil.getClassLoader());

			TemplateResourceLoaderUtil.clearCache(
				TemplateConstants.LANG_TYPE_FTL);
			TemplateResourceLoaderUtil.clearCache(
				TemplateConstants.LANG_TYPE_VM);
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}

		if (_log.isInfoEnabled()) {
			if (themes.size() == 1) {
				_log.info(
					"1 theme for " + servletContextName + " was unregistered");
			}
			else {
				_log.info(
					StringBundler.concat(
						themes.size(), " themes for ", servletContextName,
						" were unregistered"));
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		ThemeHotDeployListener.class);

	private static final Map<String, List<Theme>> _themes = new HashMap<>();

}