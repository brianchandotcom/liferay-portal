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

import com.liferay.configuration.admin.web.internal.model.ConfigurationModel;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true, property = "filter.visibility=true",
	service = ConfigurationModelRetriever.class
)
public class FilterVisibilityConfigurationModelRetrieverWrapper
	implements ConfigurationModelRetriever {

	@Override
	public Map<String, Set<ConfigurationModel>> categorizeConfigurationModels(
		Map<String, ConfigurationModel> configurationModels) {

		return _configurationModelRetriever.categorizeConfigurationModels(
			configurationModels);
	}

	@Override
	public Configuration getConfiguration(
		String pid, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		if (!ConfigurationVisibilityUtil.isVisible(pid, scope, scopePK)) {
			return null;
		}

		return _configurationModelRetriever.getConfiguration(
			pid, scope, scopePK);
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		Bundle bundle, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				bundle, scope, scopePK);

		_filterVisibility(scope, scopePK, configurationModels);

		return configurationModels;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(scope, scopePK);

		_filterVisibility(scope, scopePK, configurationModels);

		return configurationModels;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		String locale, ExtendedObjectClassDefinition.Scope scope,
		Serializable scopePK) {

		Map<String, ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				locale, scope, scopePK);

		_filterVisibility(scope, scopePK, configurationModels);

		return configurationModels;
	}

	@Override
	public Set<ConfigurationModel> getConfigurationModels(
		String configurationCategory, String languageId,
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK) {

		Set<ConfigurationModel> configurationModels =
			_configurationModelRetriever.getConfigurationModels(
				configurationCategory, languageId, scope, scopePK);

		configurationModels.removeIf(
			configurationModel -> !ConfigurationVisibilityUtil.isVisible(
				configurationModel, scope, scopePK));

		return configurationModels;
	}

	@Override
	public List<ConfigurationModel> getFactoryInstances(
			ConfigurationModel factoryConfigurationModel,
			ExtendedObjectClassDefinition.Scope scope, Serializable scopePK)
		throws IOException {

		if (!ConfigurationVisibilityUtil.isVisible(
				factoryConfigurationModel, scope, scopePK)) {

			return Collections.emptyList();
		}

		return _configurationModelRetriever.getFactoryInstances(
			factoryConfigurationModel, scope, scopePK);
	}

	private void _filterVisibility(
		ExtendedObjectClassDefinition.Scope scope, Serializable scopePK,
		Map<String, ConfigurationModel> configurationModelMap) {

		Set<Map.Entry<String, ConfigurationModel>> set =
			configurationModelMap.entrySet();

		set.removeIf(
			entry -> !ConfigurationVisibilityUtil.isVisible(
				entry.getValue(), scope, scopePK));
	}

	@Reference(target = "(!(filter.visibility=*))")
	private ConfigurationModelRetriever _configurationModelRetriever;

}