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

package com.liferay.site.navigation.menu.web.portlet.action;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portlet.display.template.PortletDisplayTemplate;
import com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebConfiguration;
import com.liferay.site.navigation.menu.web.constants.SiteNavigationMenuPortletKeys;

import java.io.IOException;

import java.util.Map;

import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Douglas Wong
 * @author Raymond Augé
 */
@Component(
	configurationPid = "com.liferay.site.navigation.menu.web.configuration.SiteNavigationMenuWebConfiguration",
	configurationPolicy = ConfigurationPolicy.OPTIONAL, immediate = true,
	property = {"javax.portlet.name=" + SiteNavigationMenuPortletKeys.SITE_NAVIGATION_MENU},
	service = ConfigurationAction.class
)
public class SiteNavigationMenuConfigurationAction
	extends DefaultConfigurationAction {

	@Override
	public String getJspPath(HttpServletRequest request) {
		return "/configuration.jsp";
	}

	@Override
	public void include(
			PortletConfig portletConfig, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		request.setAttribute(
			SiteNavigationMenuWebConfiguration.class.getName(),
			_siteNavigationMenuWebConfiguration);

		super.include(portletConfig, request, response);
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.site.navigation.menu.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_siteNavigationMenuWebConfiguration = Configurable.createConfigurable(
			SiteNavigationMenuWebConfiguration.class, properties);
	}

	@Override
	protected void doDispatch(
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws IOException, PortletException {

		renderRequest.setAttribute(
			WebKeys.PORTLET_DISPLAY_TEMPLATE, _portletDisplayTemplate);

		super.doDispatch(renderRequest, renderResponse);
	}

	@Reference(unbind = "-")
	protected void setPortletDisplayTemplate(
		PortletDisplayTemplate portletDisplayTemplate) {

		_portletDisplayTemplate = portletDisplayTemplate;
	}

	private volatile PortletDisplayTemplate _portletDisplayTemplate;
	private volatile SiteNavigationMenuWebConfiguration
		_siteNavigationMenuWebConfiguration;

}