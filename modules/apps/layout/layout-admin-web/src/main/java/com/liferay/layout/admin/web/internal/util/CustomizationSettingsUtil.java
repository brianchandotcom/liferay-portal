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

package com.liferay.layout.admin.web.internal.util;

import com.liferay.portal.kernel.layoutconfiguration.util.RuntimePageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutTemplate;
import com.liferay.portal.kernel.servlet.PluginContextListener;
import com.liferay.portal.kernel.servlet.ServletContextPool;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.taglib.util.DummyVelocityTaglib;
import com.liferay.taglib.util.VelocityTaglib;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Shuyang Zhou
 */
public class CustomizationSettingsUtil {

	public static void processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception {

		ClassLoader pluginClassLoader = null;

		LayoutTemplate layoutTemplate = RuntimePageUtil.getLayoutTemplate(
			templateResource.getTemplateId());

		if (layoutTemplate != null) {
			String pluginServletContextName = GetterUtil.getString(
				layoutTemplate.getServletContextName());

			ServletContext pluginServletContext = ServletContextPool.get(
				pluginServletContextName);

			if (pluginServletContext != null) {
				pluginClassLoader =
					(ClassLoader)pluginServletContext.getAttribute(
						PluginContextListener.PLUGIN_CLASS_LOADER);
			}
		}

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		try {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				currentThread.setContextClassLoader(pluginClassLoader);
			}

			_processCustomizationSettings(
				httpServletRequest, httpServletResponse, templateResource,
				langType);
		}
		finally {
			if ((pluginClassLoader != null) &&
				(pluginClassLoader != contextClassLoader)) {

				currentThread.setContextClassLoader(contextClassLoader);
			}
		}
	}

	private static void _processCustomizationSettings(
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse,
			TemplateResource templateResource, String langType)
		throws Exception {

		CustomizationSettingsProcessor processor =
			new CustomizationSettingsProcessor(
				httpServletRequest, httpServletResponse);

		Template template = TemplateManagerUtil.getTemplate(
			langType, templateResource, false);

		template.put("processor", processor);

		// Velocity variables

		template.prepare(httpServletRequest);

		// liferay:include tag library

		VelocityTaglib velocityTaglib = new DummyVelocityTaglib();

		template.put("taglibLiferay", velocityTaglib);
		template.put("theme", velocityTaglib);

		try {
			template.processTemplate(httpServletResponse.getWriter());
		}
		catch (Exception exception) {
			_log.error(exception, exception);

			throw exception;
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CustomizationSettingsUtil.class);

}