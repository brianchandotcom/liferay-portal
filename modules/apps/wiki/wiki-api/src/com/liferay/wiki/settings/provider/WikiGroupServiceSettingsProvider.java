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
import com.liferay.portal.kernel.settings.GroupServiceSettingsProvider;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.settings.WikiGroupServiceSettings;

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
		"class.name=com.liferay.wiki.settings.WikiGroupServiceSettings"
	},
	service = GroupServiceSettingsProvider.class
)
public class WikiGroupServiceSettingsProvider
	implements GroupServiceSettingsProvider<WikiGroupServiceSettings> {

	@Override
	public WikiGroupServiceSettings getGroupServiceSettings(long groupId)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiGroupServiceSettings(settings);
	}

	@Override
	public WikiGroupServiceSettings getGroupServiceSettings(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiGroupServiceSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	@Activate
	protected void activate() {
		_settingsFactory.registerSettingsMetadata(
			WikiGroupServiceSettings.class, _wikiGroupServiceConfiguration,
			null);
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	@Reference(unbind = "-")
	protected void setWikiGroupServiceConfiguration(
		WikiGroupServiceConfiguration wikiGroupServiceConfiguration) {

		_wikiGroupServiceConfiguration = wikiGroupServiceConfiguration;
	}

	private SettingsFactory _settingsFactory;
	private WikiGroupServiceConfiguration _wikiGroupServiceConfiguration;

}