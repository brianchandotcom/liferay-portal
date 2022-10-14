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

package com.liferay.analytics.settings.web.internal.portal.settings.configuration.admin.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;

import javax.servlet.ServletContext;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Riccardo Ferrari
 */
@Component(service = ConfigurationScreen.class)
public class AnalyticsCloudConnectionConfigurationScreen
	extends BaseAnalyticsConfigurationScreen {

	@Override
	public String getKey() {
		return "analytics-cloud-connection";
	}

	@Override
	public boolean isVisible() {
		return !super.isVisible();
	}

	@Override
	protected String getJspPath() {
		return "/view.jsp";
	}

	@Override
	protected ServletContext getServletContext() {
		return _servletContext;
	}

	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.analytics.settings.web)",
		unbind = "-"
	)
	private ServletContext _servletContext;

}