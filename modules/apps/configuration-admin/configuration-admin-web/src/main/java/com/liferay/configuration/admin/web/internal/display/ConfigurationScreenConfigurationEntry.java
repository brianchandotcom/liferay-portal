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

package com.liferay.configuration.admin.web.internal.display;

import com.liferay.configuration.admin.display.ConfigurationScreen;

import java.util.Locale;

import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationScreenConfigurationEntry
	implements ConfigurationEntry {

	public ConfigurationScreenConfigurationEntry(
		ConfigurationScreen configurationScreen, Locale locale) {

		_configurationScreen = configurationScreen;
		_locale = locale;
	}

	@Override
	public String getCategory() {
		return _configurationScreen.getCategoryKey();
	}

	@Override
	public String getEditURL(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		PortletURL portletURL = renderResponse.createRenderURL();

		portletURL.setParameter(
			"mvcRenderCommandName", "/view_configuration_screen");
		portletURL.setParameter(
			"configurationScreenKey", _configurationScreen.getKey());

		return portletURL.toString();
	}

	@Override
	public String getName() {
		return _configurationScreen.getName(_locale);
	}

	@Override
	public String getScope() {
		return _configurationScreen.getScope();
	}

	private final ConfigurationScreen _configurationScreen;
	private final Locale _locale;

}