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

package com.liferay.portlet.portletconfiguration;

import com.liferay.portal.kernel.portlet.BasePortletProvider;
import com.liferay.portal.kernel.portlet.ViewPortletProvider;
import com.liferay.portal.kernel.spring.osgi.OSGiBeanProperties;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.portletconfiguration.util.PortletConfigurationApplicationType;

/**
 * @author Juergen Kappler
 */

@OSGiBeanProperties(
	property = {
		"model.class.name=" + PortletConfigurationApplicationType.PortletConfiguration.CLASS_NAME
	},
	service = ViewPortletProvider.class
)
public class PortletConfigurationViewPortletProvider extends BasePortletProvider
	implements ViewPortletProvider {

	@Override
	public String getPortletId() {
		return PortletKeys.PORTLET_CONFIGURATION;
	}

	@Override
	protected long getPlid(ThemeDisplay themeDisplay) {
		return themeDisplay.getPlid();
	}

}