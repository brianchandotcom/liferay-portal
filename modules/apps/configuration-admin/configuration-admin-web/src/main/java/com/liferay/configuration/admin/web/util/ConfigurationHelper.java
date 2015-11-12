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

package com.liferay.configuration.admin.web.util;

import com.liferay.configuration.admin.ExtendedMetaTypeInformation;
import com.liferay.configuration.admin.ExtendedMetaTypeService;
import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationHelper {

	public ConfigurationHelper(
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		ExtendedMetaTypeService extendedMetaTypeService, String languageId) {

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_extendedMetaTypeService = extendedMetaTypeService;

		_configurationModels = _getConfigurationModels(languageId);

		_categorizedConfigurationModels = _getCategorizedConfigurationModels(
			_configurationModels);
	}

	public Configuration getConfiguration(String pid) {
		try {
			String pidFilter = _getPidFilterString(pid, false);

			Configuration[] configurations =
				_configurationAdmin.listConfigurations(pidFilter);

			if (configurations != null) {
				return configurations[0];
			}
		}
		catch (InvalidSyntaxException | IOException e) {
			ReflectionUtil.throwException(e);
		}

		return null;
	}

	public List<String> getConfigurationCategories() {
		return new ArrayList<>(_categorizedConfigurationModels.keySet());
	}

	public ConfigurationModel getConfigurationModel(String pid) {
		return _configurationModels.get(pid);
	}

	public List<ConfigurationModel> getConfigurationModels() {
		TreeSet orderedConfigurationModels = new TreeSet(
			new ConfigurationModelComparator());

		orderedConfigurationModels.addAll(_configurationModels.values());

		return new ArrayList<>(orderedConfigurationModels);
	}

	public List<ConfigurationModel> getConfigurationModels(
		String configurationCategory) {

		TreeSet orderedConfigurationModels = new TreeSet(
			new ConfigurationModelComparator());

		orderedConfigurationModels.addAll(
			_categorizedConfigurationModels.get(configurationCategory));

		return new ArrayList<>(orderedConfigurationModels);
	}

	public List<ConfigurationModel> getFactoryInstances(
			String languageId, String factoryPid)
		throws IOException {

		List<ConfigurationModel> configurationModels = new ArrayList<>();

		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);
		filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		filter.append(StringPool.EQUAL);
		filter.append(factoryPid);
		filter.append(StringPool.CLOSE_PARENTHESIS);

		Configuration[] configurations = null;

		try {
			configurations = _configurationAdmin.listConfigurations(
				filter.toString());
		}
		catch (InvalidSyntaxException ise) {
			ReflectionUtil.throwException(ise);
		}

		if (configurations == null) {
			return configurationModels;
		}

		ConfigurationModel configurationModel = getConfigurationModel(
			factoryPid);

		for (Configuration configuration : configurations) {
			ConfigurationModel curConfigurationModel = new ConfigurationModel(
				configurationModel, configuration,
				configuration.getBundleLocation(), false);

			configurationModels.add(curConfigurationModel);
		}

		return configurationModels;
	}

	private void _collectConfigurationModels(
		Bundle bundle, Map<String, ConfigurationModel> modelMap, String locale,
		boolean factory) {

		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (extendedMetaTypeInformation == null) {
			return;
		}

		String[] pids = null;

		if (factory) {
			pids = extendedMetaTypeInformation.getFactoryPids();
		}
		else {
			pids = extendedMetaTypeInformation.getPids();
		}

		for (String pid : pids) {
			ConfigurationModel configurationModel = _getConfigurationModel(
				bundle, pid, factory, locale);

			if (configurationModel == null) {
				continue;
			}

			modelMap.put(pid, configurationModel);
		}
	}

	private Map<String, Set<ConfigurationModel>>
		_getCategorizedConfigurationModels(
			Map<String, ConfigurationModel> configurationModels) {

		Map<String, Set<ConfigurationModel>> categorizedConfigurationModels =
			new HashMap<>();

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			String configurationCategory = configurationModel.getCategory();

			Set<ConfigurationModel> curConfigurationModels =
				categorizedConfigurationModels.get(configurationCategory);

			if (curConfigurationModels == null) {
				curConfigurationModels = new HashSet<>();

				categorizedConfigurationModels.put(
					configurationCategory, curConfigurationModels);
			}

			curConfigurationModels.add(configurationModel);
		}

		return categorizedConfigurationModels;
	}

	private ConfigurationModel _getConfigurationModel(
		Bundle bundle, String pid, boolean factory, String locale) {

		ExtendedMetaTypeInformation metaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return null;
		}

		return new ConfigurationModel(
			metaTypeInformation.getObjectClassDefinition(pid, locale),
			getConfiguration(pid), StringPool.QUESTION, factory);
	}

	private Map<String, ConfigurationModel> _getConfigurationModels(
		String locale) {

		Map<String, ConfigurationModel> configurationModelMap = new HashMap<>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			_collectConfigurationModels(
				bundle, configurationModelMap, locale, false);
			_collectConfigurationModels(
				bundle, configurationModelMap, locale, true);
		}

		return configurationModelMap;
	}

	private String _getPidFilterString(String pid, boolean factory) {
		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);

		if (factory) {
			filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		}
		else {
			filter.append(Constants.SERVICE_PID);
		}

		filter.append(StringPool.EQUAL);
		filter.append(pid);
		filter.append(StringPool.CLOSE_PARENTHESIS);

		return filter.toString();
	}

	private final BundleContext _bundleContext;
	private final Map<String, Set<ConfigurationModel>>
		_categorizedConfigurationModels;
	private final ConfigurationAdmin _configurationAdmin;
	private final Map<String, ConfigurationModel> _configurationModels;
	private final ExtendedMetaTypeService _extendedMetaTypeService;

	private class ConfigurationModelComparator
		implements Comparator<ConfigurationModel> {

		@Override
		public int compare(ConfigurationModel cm1, ConfigurationModel cm2) {
			String name1 = cm1.getName();
			String name2 = cm2.getName();

			return name1.compareTo(name2);
		}

	}

}