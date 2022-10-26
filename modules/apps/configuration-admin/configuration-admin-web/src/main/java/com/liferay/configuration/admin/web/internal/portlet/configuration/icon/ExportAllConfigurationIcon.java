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

package com.liferay.configuration.admin.web.internal.portlet.configuration.icon;

import com.liferay.configuration.admin.constants.ConfigurationAdminPortletKeys;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.portlet.configuration.icon.PortletConfigurationIcon;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.WebKeys;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jorge Ferrer
 */
@Component(service = PortletConfigurationIcon.class)
public class ExportAllConfigurationIcon extends BasePortletConfigurationIcon {

	@Override
	public String getMessage(PortletRequest portletRequest) {
		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		return _language.get(themeDisplay.getLocale(), "export-all-settings");
	}

	@Override
	public String getMethod() {
		return "GET";
	}

	@Override
	public String getURL(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		LiferayPortletURL liferayPortletURL =
			(LiferayPortletURL)_portal.getControlPanelPortletURL(
				portletRequest, _portal.getPortletId(portletRequest),
				PortletRequest.RESOURCE_PHASE);

		liferayPortletURL.setResourceID(
			"/configuration_admin/export_configuration");

		return liferayPortletURL.toString();
	}

	@Override
	public double getWeight() {
		return 1;
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		String portletId = _portal.getPortletId(portletRequest);

		if (portletId.equals(ConfigurationAdminPortletKeys.INSTANCE_SETTINGS) ||
			portletId.equals(ConfigurationAdminPortletKeys.SYSTEM_SETTINGS)) {

			return true;
		}

		return false;
	}

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

}