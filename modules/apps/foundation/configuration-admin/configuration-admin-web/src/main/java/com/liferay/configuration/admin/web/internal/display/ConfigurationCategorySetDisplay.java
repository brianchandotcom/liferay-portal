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
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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

		return new ArrayList(_configurationCategoryDisplays);
	}

	public String getKey() {
		return _configurationCategorySet;
	}

	private Set<ConfigurationCategoryDisplay> _configurationCategoryDisplays =
		new TreeSet(new ConfigurationCategoryDisplayComparator());
	private final String _configurationCategorySet;

	private static class ConfigurationCategoryDisplayComparator
		implements Comparator<ConfigurationCategoryDisplay> {

		@Override
		public int compare(
			ConfigurationCategoryDisplay configurationCategoryDisplay1,
			ConfigurationCategoryDisplay configurationCategoryDisplay2) {

			String configurationCategoryDisplayKey1 =
				configurationCategoryDisplay1.getKey();
			String configurationCategoryDisplayKey2 =
				configurationCategoryDisplay2.getKey();

			return configurationCategoryDisplayKey1.compareTo(
				configurationCategoryDisplayKey2);
		}

	}

}