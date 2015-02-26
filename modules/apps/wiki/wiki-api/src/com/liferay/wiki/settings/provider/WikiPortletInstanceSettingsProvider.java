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

package com.liferay.wiki.settings.provider;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsFactoryUtil;
import com.liferay.portal.model.Layout;
import com.liferay.wiki.configuration.WikiPortletInstanceConfiguration;
import com.liferay.wiki.settings.WikiPortletInstanceSettings;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	immediate = true,
	property = {
		"class.name=com.liferay.wiki.settings.WikiPortletInstanceSettings"
	},
	service = PortletInstanceSettingsProvider.class
)
public class WikiPortletInstanceSettingsProvider
	implements PortletInstanceSettingsProvider<WikiPortletInstanceSettings> {

	@Override
	public WikiPortletInstanceSettings getPortletInstanceSettings(
			Layout layout, String portletId)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(settings);
	}

	@Override
	public WikiPortletInstanceSettings getPortletInstanceSettings(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = SettingsFactoryUtil.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	@Activate
	protected void activate() {
		_settingsFactory.registerSettingsMetadata(
			WikiPortletInstanceSettings.class,
			_wikiPortletInstanceConfiguration, null);
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Reference(unbind = "-")
	protected void setWikiPortletInstanceConfiguration(
		WikiPortletInstanceConfiguration wikiPortletInstanceConfiguration) {

		_wikiPortletInstanceConfiguration = wikiPortletInstanceConfiguration;
	}

	private SettingsFactory _settingsFactory;
	private WikiPortletInstanceConfiguration _wikiPortletInstanceConfiguration;

}