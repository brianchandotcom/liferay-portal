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

package com.liferay.portal.search.web.internal.custom.filter.portlet.action;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.search.web.internal.custom.filter.constants.CustomFilterPortletKeys;
import com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayBuilder;
import com.liferay.portal.search.web.internal.custom.filter.display.context.CustomFilterDisplayContext;

import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Igor Nazar
 * @author Luan Maoski
 */
@Component(
	immediate = true,
	property = "javax.portlet.name=" + CustomFilterPortletKeys.CUSTOM_FILTER,
	service = ConfigurationAction.class
)
public class CustomFilterPortletConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest httpServletRequest) {
		return "/custom/filter/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse)
		throws Exception {

		httpServletRequest.setAttribute(
			CustomFilterPortletKeys.CONFIGURATION_DISPLAY_CONTEXT,
			new ConfigurationDisplayContext());

		RenderRequest renderRequest =
			(RenderRequest)httpServletRequest.getAttribute(
				JavaConstants.JAVAX_PORTLET_REQUEST);

		httpServletRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_CONTEXT,
			_createCustomFilterDisplayContext(renderRequest));

		super.include(portletConfig, httpServletRequest, httpServletResponse);
	}

	private CustomFilterDisplayContext _buildDisplayContext(
			RenderRequest renderRequest)
		throws ConfigurationException {

		return CustomFilterDisplayBuilder.builder(
		).themeDisplay(
			(ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY)
		).build();
	}

	private CustomFilterDisplayContext _createCustomFilterDisplayContext(
		RenderRequest renderRequest) {

		try {
			return _buildDisplayContext(renderRequest);
		}
		catch (ConfigurationException configurationException) {
			throw new RuntimeException(configurationException);
		}
	}

}