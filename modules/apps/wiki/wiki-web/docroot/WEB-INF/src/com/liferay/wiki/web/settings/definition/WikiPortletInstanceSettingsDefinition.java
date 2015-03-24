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

package com.liferay.wiki.web.settings.definition;

import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.wiki.constants.WikiPortletKeys;
import com.liferay.wiki.web.configuration.WikiPortletInstanceConfiguration;
import com.liferay.wiki.web.settings.WikiPortletInstanceSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class WikiPortletInstanceSettingsDefinition
	implements SettingsDefinition<WikiPortletInstanceSettings,
		WikiPortletInstanceConfiguration> {

	@Override
	public Class<WikiPortletInstanceConfiguration> getConfigurationBeanClass() {
		return WikiPortletInstanceConfiguration.class;
	}

	@Override
	public Class<WikiPortletInstanceSettings> getSettingsClass() {
		return WikiPortletInstanceSettings.class;
	}

	@Override
	public Class<?> getSettingsExtraClass() {
		return WikiPortletInstanceSettingsExtraImpl.class;
	}

	@Override
	public String[] getSettingsIds() {
		return new String[] {
			WikiPortletKeys.WIKI, WikiPortletKeys.WIKI_ADMIN,
			WikiPortletKeys.WIKI_DISPLAY};
	}

}