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

package com.liferay.configuration.admin.web.internal.util;

import com.liferay.configuration.admin.category.ConfigurationCategory;
import com.liferay.configuration.admin.display.ConfigurationScreen;
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay;
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategorySectionDisplay;
import com.liferay.configuration.admin.web.internal.display.ConfigurationEntry;

import java.util.List;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface ConfigurationEntryRetriever {

	public ConfigurationCategory getConfigurationCategory(
		String configurationCategoryKey);

	public ConfigurationCategoryMenuDisplay getConfigurationCategoryMenuDisplay(
		String configurationCategory, String languageId);

	public List<ConfigurationCategorySectionDisplay>
		getConfigurationCategorySectionDisplays();

	public Set<ConfigurationEntry> getConfigurationEntries(
		String configurationCategory, String languageId);

	public ConfigurationScreen getConfigurationScreen(
		String configurationScreenKey);

}