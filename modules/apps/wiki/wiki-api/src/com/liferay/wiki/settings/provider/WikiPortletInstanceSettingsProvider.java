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
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.resource.manager.ResourceManager;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.PortletInstanceSettingsProvider;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.model.Layout;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.settings.WikiPortletInstanceConfiguration;
import com.liferay.wiki.settings.WikiPortletInstanceSettings;

import java.util.Map;

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

		Settings settings = _settingsFactory.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(settings);
	}

	@Override
	public WikiPortletInstanceSettings getPortletInstanceSettings(
			Layout layout, String portletId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getPortletInstanceSettings(
			layout, portletId);

		return new WikiPortletInstanceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Reference(unbind = "unsetWikiPortletInstanceConfiguration")
	protected void setWikiPortletInstanceConfiguration(
		WikiPortletInstanceConfiguration wikiPortletInstanceConfiguration) {

		_wikiPortletInstanceConfiguration = wikiPortletInstanceConfiguration;

		_registerSettingsMetadata();
	}

	protected void unsetWikiPortletInstanceConfiguration(
		WikiPortletInstanceConfiguration wikiPortletInstanceConfiguration) {

		_wikiPortletInstanceConfiguration = null;

		_registerSettingsMetadata();
	}

	private void _registerSettingsMetadata() {
		ResourceManager resourceManager = new ClassLoaderResourceManager(
			getClass().getClassLoader());

		_settingsFactory.registerSettingsMetadata(
			WikiPortletKeys.WIKI, null,
			WikiPortletInstanceSettings.MULTI_VALUED_KEYS,
			_wikiPortletInstanceConfiguration, resourceManager);
		_settingsFactory.registerSettingsMetadata(
			WikiPortletKeys.WIKI_ADMIN, null,
			WikiPortletInstanceSettings.MULTI_VALUED_KEYS,
			_wikiPortletInstanceConfiguration, resourceManager);
		_settingsFactory.registerSettingsMetadata(
			WikiPortletKeys.WIKI_DISPLAY, null,
			WikiPortletInstanceSettings.MULTI_VALUED_KEYS,
			_wikiPortletInstanceConfiguration, resourceManager);
	}

	private SettingsFactory _settingsFactory;
	private WikiPortletInstanceConfiguration _wikiPortletInstanceConfiguration;

}