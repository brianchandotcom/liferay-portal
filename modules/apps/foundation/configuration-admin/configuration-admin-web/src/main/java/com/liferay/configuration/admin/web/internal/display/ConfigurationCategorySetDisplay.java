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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationCategorySetDisplay {

	public ConfigurationCategorySetDisplay(String configurationCategorySet) {
		_configurationCategorySet = configurationCategorySet;
	}

	public void add(ConfigurationCategoryDisplay configurationCategoryDisplay) {
		_configurationCategoryDisplays.add(configurationCategoryDisplay);
	}

	public List<ConfigurationCategoryDisplay>
		getConfigurationCategoryDisplays() {

		return _configurationCategoryDisplays;
	}

	public String getKey() {
		return _configurationCategorySet;
	}

	private List<ConfigurationCategoryDisplay> _configurationCategoryDisplays =
		new ArrayList<>();
	private final String _configurationCategorySet;

}