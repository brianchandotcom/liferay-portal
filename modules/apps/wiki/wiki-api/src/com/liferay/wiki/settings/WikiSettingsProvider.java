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

package com.liferay.wiki.settings;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.resource.manager.ClassLoaderResourceManager;
import com.liferay.portal.kernel.settings.ParameterMapSettings;
import com.liferay.portal.kernel.settings.Settings;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.settings.SettingsProvider;
import com.liferay.wiki.configuration.WikiServiceConfiguration;
import com.liferay.wiki.constants.WikiConstants;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(
	configurationPid =
		"com.liferay.wiki.configuration.WikiServiceConfiguration",
	immediate = true,
	property = {
		"class.name=com.liferay.wiki.settings.WikiSettings"
	},
	service = SettingsProvider.class
)
public class WikiSettingsProvider implements SettingsProvider<WikiSettings> {

	@Override
	public WikiSettings getGroupServiceSettings(long groupId)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiSettings(settings);
	}

	@Override
	public WikiSettings getGroupServiceSettings(
			long groupId, Map<String, String[]> parameterMap)
		throws PortalException {

		Settings settings = _settingsFactory.getGroupServiceSettings(
			groupId, WikiConstants.SERVICE_NAME);

		return new WikiSettings(
			new ParameterMapSettings(parameterMap, settings));
	}

	protected static WikiSettingsProvider getWikiSettingsProvider() {
		if (_wikiSettingsProvider == null) {
			throw new IllegalStateException(
				"Wiki settings provider is not available");
		}

		return _wikiSettingsProvider;
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		WikiServiceConfiguration wikiServiceConfiguration =
			Configurable.createConfigurable(
				WikiServiceConfiguration.class, properties);

		_settingsFactory.registerSettingsMetadata(
			WikiConstants.SERVICE_NAME, WikiSettings.getFallbackKeys(),
			WikiSettings.MULTI_VALUED_KEYS, wikiServiceConfiguration,
			new ClassLoaderResourceManager(
				WikiSettings.class.getClassLoader()));

		_wikiSettingsProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_wikiSettingsProvider = null;
	}

	@Reference
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	private static WikiSettingsProvider _wikiSettingsProvider;

	private SettingsFactory _settingsFactory;

}