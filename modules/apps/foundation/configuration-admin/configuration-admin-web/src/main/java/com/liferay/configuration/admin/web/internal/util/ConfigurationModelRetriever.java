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
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategoryMenuDisplay;
import com.liferay.configuration.admin.web.internal.display.ConfigurationCategorySectionDisplay;
import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;

import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.service.cm.Configuration;

/**
 * @author Michael C. Han
 */
public interface ConfigurationModelRetriever {

	public Map<String, Set<ConfigurationModel>> categorizeConfigurationModels(
		Map<String, ConfigurationModel> configurationModels);

	public Configuration getConfiguration(String pid);

	public ConfigurationCategory getConfigurationCategory(
		String configurationCategoryKey);

	public ConfigurationCategoryMenuDisplay getConfigurationCategoryMenuDisplay(
		String configurationCategory, String languageId);

	public List<ConfigurationCategorySectionDisplay>
		getConfigurationCategorySectionDisplays();

	public Map<String, ConfigurationModel> getConfigurationModels();

	public Map<String, ConfigurationModel> getConfigurationModels(
		Bundle bundle);

	public Map<String, ConfigurationModel> getConfigurationModels(
		String locale);

	public Set<ConfigurationModel> getConfigurationModels(
		String configurationCategory, String languageId);

	public List<ConfigurationModel> getFactoryInstances(
			ConfigurationModel factoryConfigurationModel)
		throws IOException;

}