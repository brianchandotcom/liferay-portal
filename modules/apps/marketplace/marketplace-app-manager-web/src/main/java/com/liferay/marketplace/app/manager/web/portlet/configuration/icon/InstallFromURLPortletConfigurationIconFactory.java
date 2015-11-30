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

package com.liferay.marketplace.app.manager.web.portlet.configuration.icon;

import com.liferay.marketplace.app.manager.web.constants.MarketplaceAppManagerPortletKeys;
import com.liferay.portal.kernel.portlet.configuration.icon.BaseJSPPortletConfigurationIconFactory;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIconFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Douglas Wong
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MarketplaceAppManagerPortletKeys.MARKETPLACE_APP_MANAGER,
		"path=/view.jsp"
	},
	service = PortletConfigurationIconFactory.class
)
public class InstallFromURLPortletConfigurationIconFactory
	extends BaseJSPPortletConfigurationIconFactory {

	@Override
	public PortletConfigurationIcon create(HttpServletRequest request) {
		return new InstallFromURLPortletConfigurationIcon(request);
	}

	@Override
	public String getJspPath() {
		return "/configuration/icon/install_from_url.jsp";
	}

	@Override
	public double getWeight() {
		return 102;
	}

	@Override
	@Reference(
		target = "(osgi.web.symbolicname=com.liferay.marketplace.app.manager.web)",
		unbind = "-"
	)
	public void setServletContext(ServletContext servletContext) {
		super.setServletContext(servletContext);
	}

}