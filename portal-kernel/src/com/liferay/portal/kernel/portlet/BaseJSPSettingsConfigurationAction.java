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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.settings.LocalizedValuesMap;
import com.liferay.portal.kernel.settings.ModifiableSettings;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;

import java.io.IOException;

import java.util.Locale;
import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletRequest;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Iván Zaera
 */
public class BaseJSPSettingsConfigurationAction
	extends SettingsConfigurationAction
	implements ConfigurationAction, ResourceServingConfigurationAction {

	public String getJspPath(HttpServletRequest httpServletRequest) {
		PortletConfig selPortletConfig = getSelPortletConfig(
			httpServletRequest);

		String configTemplate = selPortletConfig.getInitParameter(
			"config-template");

		if (Validator.isNotNull(configTemplate)) {
			return configTemplate;
		}

		String configJSP = selPortletConfig.getInitParameter("config-jsp");

		if (Validator.isNotNull(configJSP)) {
			return configJSP;
		}

		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		ServletContext servletContext = getServletContext(httpServletRequest);

		RequestDispatcher requestDispatcher =
			servletContext.getRequestDispatcher(getJspPath(httpServletRequest));

		try {
			requestDispatcher.include(httpServletRequest, httpServletResponse);
		}
		catch (ServletException se) {
			_log.error(
				"Unable to include JSP " + getJspPath(httpServletRequest), se);

			throw new IOException(
				"Unable to include " + getJspPath(httpServletRequest), se);
		}
	}

	public void setServletContext(ServletContext servletContext) {
		_servletContext = servletContext;
	}

	protected ServletContext getServletContext(
		HttpServletRequest httpServletRequest) {

		if (_servletContext != null) {
			return _servletContext;
		}

		String portletResource = ParamUtil.getString(
			httpServletRequest, "portletResource");

		if (Validator.isNotNull(portletResource)) {
			String rootPortletId = PortletIdCodec.decodePortletName(
				portletResource);

			PortletBag portletBag = PortletBagPool.get(rootPortletId);

			return portletBag.getServletContext();
		}

		return (ServletContext)httpServletRequest.getAttribute(WebKeys.CTX);
	}

	protected void removeDefaultValue(
		PortletRequest portletRequest, ModifiableSettings modifiableSettings,
		String key, LocalizedValuesMap localizedMap) {

		String defaultValue = localizedMap.getDefaultValue();

		Map<Locale, String> localizedMapValues = localizedMap.getValues();

		for (Locale locale : localizedMapValues.keySet()) {
			String languageKeyId = key + "_" + LocaleUtil.toLanguageId(locale);

			String value = getParameter(portletRequest, languageKeyId);

			if (defaultValue.equals(value) ||
				StringUtil.equalsIgnoreBreakLine(defaultValue, value)) {

				modifiableSettings.reset(languageKeyId);
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BaseJSPSettingsConfigurationAction.class);

	private ServletContext _servletContext;

}