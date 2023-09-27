/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.configuration.admin.web.internal.display;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author Jorge Ferrer
 */
public class ConfigurationCategorySectionDisplay {

	public ConfigurationCategorySectionDisplay(
		String configurationCategorySection) {

		_configurationCategorySection = configurationCategorySection;
	}

	public void add(ConfigurationCategoryDisplay configurationCategoryDisplay) {
		_configurationCategoryDisplays.add(configurationCategoryDisplay);
	}

	public List<ConfigurationCategoryDisplay>
		getConfigurationCategoryDisplays() {

		return new ArrayList(_configurationCategoryDisplays);
	}

	public String getConfigurationCategorySection() {
		return _configurationCategorySection;
	}

	public String getConfigurationCategorySectionLabel(Locale locale) {
		for (ConfigurationCategoryDisplay configurationCategoryDisplay :
				_configurationCategoryDisplays) {

			return configurationCategoryDisplay.getSectionLabel(locale);
		}

		return "category-section." + _configurationCategorySection;
	}

	private final Set<ConfigurationCategoryDisplay>
		_configurationCategoryDisplays = new TreeSet<>(
			new ConfigurationCategoryDisplayComparator());
	private final String _configurationCategorySection;

	private static class ConfigurationCategoryDisplayComparator
		implements Comparator<ConfigurationCategoryDisplay> {

		@Override
		public int compare(
			ConfigurationCategoryDisplay configurationCategoryDisplay1,
			ConfigurationCategoryDisplay configurationCategoryDisplay2) {

			String categoryKey1 =
				configurationCategoryDisplay1.getCategoryKey();
			String categoryKey2 =
				configurationCategoryDisplay2.getCategoryKey();

			return categoryKey1.compareTo(categoryKey2);
		}

	}

}