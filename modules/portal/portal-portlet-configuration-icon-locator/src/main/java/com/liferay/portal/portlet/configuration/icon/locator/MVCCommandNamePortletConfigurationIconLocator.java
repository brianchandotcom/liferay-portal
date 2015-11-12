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

package com.liferay.portal.portlet.configuration.icon.locator;

import com.liferay.portal.kernel.portlet.configuration.icon.locator.PortletConfigurationIconLocator;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.service.PortletLocalService;

import javax.portlet.PortletRequest;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Sergio González
 */
@Component(immediate = true, service = PortletConfigurationIconLocator.class)
public class MVCCommandNamePortletConfigurationIconLocator
	extends MVCPortletConfigurationIconLocator {

	@Override
	public String getPath(PortletRequest portletRequest) {
		return ParamUtil.getString(portletRequest, "mvcRenderCommandName");
	}

	@Reference(unbind = "-")
	protected void setPortletLocalService(
		PortletLocalService portletLocalService) {

		_portletLocalService = portletLocalService;
	}

	private PortletLocalService _portletLocalService;

}