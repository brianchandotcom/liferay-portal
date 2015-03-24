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
import com.liferay.wiki.configuration.WikiGroupServiceConfiguration;
import com.liferay.wiki.constants.WikiConstants;
import com.liferay.wiki.settings.WikiGroupServiceSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class WikiGroupServiceSettingsDefinition
	implements SettingsDefinition<WikiGroupServiceSettings,
		WikiGroupServiceConfiguration> {

	@Override
	public Class<WikiGroupServiceConfiguration> getConfigurationBeanClass() {
		return WikiGroupServiceConfiguration.class;
	}

	@Override
	public Class<WikiGroupServiceSettings> getSettingsClass() {
		return WikiGroupServiceSettings.class;
	}

	@Override
	public Class<?> getSettingsExtraClass() {
		return WikiGroupServiceSettingsExtraImpl.class;
	}

	@Override
	public String[] getSettingsIds() {
		return new String[] {WikiConstants.SERVICE_NAME};
	}

}