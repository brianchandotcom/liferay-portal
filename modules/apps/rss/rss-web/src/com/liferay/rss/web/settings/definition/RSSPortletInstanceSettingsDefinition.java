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

package com.liferay.rss.web.settings.definition;

import com.liferay.portal.kernel.settings.definition.SettingsDefinition;
import com.liferay.rss.web.configuration.RSSPortletInstanceConfiguration;
import com.liferay.rss.web.constants.RSSPortletKeys;
import com.liferay.rss.web.settings.RSSPortletInstanceSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Juergen Kappler
 */
@Component(immediate = true)
public class RSSPortletInstanceSettingsDefinition
		implements SettingsDefinition<RSSPortletInstanceSettings,
		RSSPortletInstanceConfiguration> {

	@Override
	public Class<RSSPortletInstanceConfiguration> getConfigurationBeanClass() {
		return RSSPortletInstanceConfiguration.class;
	}

	@Override
	public Class<RSSPortletInstanceSettings> getSettingsClass() {
		return RSSPortletInstanceSettings.class;
	}

	@Override
	public Class<?> getSettingsExtraClass() {
		return null;
	}

	@Override
	public String[] getSettingsIds() {
		return new String[] {
			RSSPortletKeys.RSS};
	}

}