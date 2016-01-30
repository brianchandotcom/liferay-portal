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

import com.liferay.configuration.admin.web.model.ConfigurationModel;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.metatype.definitions.ExtendedMetaTypeInformation;
import com.liferay.portal.metatype.definitions.ExtendedMetaTypeService;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
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
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = ConfigurationModelRetriever.class)
public class ConfigurationModelRetrieverImpl
	implements ConfigurationModelRetriever {

	@Override
	public Map<String, Set<ConfigurationModel>> categorizeConfigurationModels(
		Map<String, ConfigurationModel> configurationModels) {

		Map<String, Set<ConfigurationModel>> categorizedConfigurationModels =
			new HashMap<>();

		for (ConfigurationModel configurationModel :
				configurationModels.values()) {

			String configurationCategory = configurationModel.getCategory();

			Set<ConfigurationModel> curConfigurationModels =
				categorizedConfigurationModels.get(configurationCategory);

			if (curConfigurationModels == null) {
				curConfigurationModels = new TreeSet<>(
					getConfigurationModelComparator());

				categorizedConfigurationModels.put(
					configurationCategory, curConfigurationModels);
			}

			curConfigurationModels.add(configurationModel);
		}

		return categorizedConfigurationModels;
	}

	@Override
	public Configuration getConfiguration(String pid) {
		try {
			String pidFilter = getPidFilterString(pid, false);

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

	@Override
	public List<String> getConfigurationCategories(
		Map<String, Set<ConfigurationModel>> categorizedConfigurationModels) {

		Set<String> configurationCategories = new TreeSet<>(
			getConfigurationCategoryComparator());

		configurationCategories.addAll(categorizedConfigurationModels.keySet());

		return new ArrayList<>(configurationCategories);
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels() {
		return getConfigurationModels((String)null);
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		Bundle bundle) {

		Map<String, ConfigurationModel> configurationModels = new HashMap<>();

		collectConfigurationModels(bundle, configurationModels, true, null);
		collectConfigurationModels(bundle, configurationModels, false, null);

		return configurationModels;
	}

	@Override
	public Map<String, ConfigurationModel> getConfigurationModels(
		String locale) {

		Map<String, ConfigurationModel> configurationModels = new HashMap<>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			collectConfigurationModels(
				bundle, configurationModels, true, locale);
			collectConfigurationModels(
				bundle, configurationModels, false, locale);
		}

		return configurationModels;
	}

	@Override
	public List<ConfigurationModel> getFactoryInstances(
			ConfigurationModel factoryConfigurationModel)
		throws IOException {

		StringBundler filter = new StringBundler(5);

		filter.append(StringPool.OPEN_PARENTHESIS);
		filter.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		filter.append(StringPool.EQUAL);
		filter.append(factoryConfigurationModel.getFactoryPid());
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
			return Collections.emptyList();
		}

		List<ConfigurationModel> factoryInstances = new ArrayList<>();

		for (Configuration configuration : configurations) {
			ConfigurationModel curConfigurationModel = new ConfigurationModel(
				factoryConfigurationModel, configuration,
				configuration.getBundleLocation(), false);

			factoryInstances.add(curConfigurationModel);
		}

		return factoryInstances;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_bundleContext = bundleContext;
	}

	protected void collectConfigurationModels(
		Bundle bundle, Map<String, ConfigurationModel> configurationModels,
		boolean factory, String locale) {

		ExtendedMetaTypeInformation extendedMetaTypeInformation =
			_extendedMetaTypeService.getMetaTypeInformation(bundle);

		if (extendedMetaTypeInformation == null) {
			return;
		}

		List<String> pids = new ArrayList<>();

		if (factory) {
			pids.addAll(
				Arrays.asList(extendedMetaTypeInformation.getFactoryPids()));
		}
		else {
			pids.addAll(Arrays.asList(extendedMetaTypeInformation.getPids()));
		}

		for (String pid : pids) {
			ConfigurationModel configurationModel = getConfigurationModel(
				bundle, pid, factory, locale);

			if (configurationModel == null) {
				continue;
			}

			configurationModels.put(pid, configurationModel);
		}
	}

	protected Comparator<String> getConfigurationCategoryComparator() {
		return new ConfigurationCategoryComparator();
	}

	protected ConfigurationModel getConfigurationModel(
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

	protected Comparator<ConfigurationModel> getConfigurationModelComparator() {
		return new ConfigurationModelComparator();
	}

	protected String getPidFilterString(String pid, boolean factory) {
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

	@Reference(unbind = "-")
	protected void setConfigAdminService(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	@Reference(unbind = "-")
	protected void setExtendedMetaTypeService(
		ExtendedMetaTypeService extendedMetaTypeService) {

		_extendedMetaTypeService = extendedMetaTypeService;
	}

	private BundleContext _bundleContext;
	private ConfigurationAdmin _configurationAdmin;
	private ExtendedMetaTypeService _extendedMetaTypeService;

	private static class ConfigurationCategoryComparator
		implements Comparator<String> {

		@Override
		public int compare(
			String configurationCategory1, String configurationCategory2) {

			if (configurationCategory1.equals("other")) {
				return 1;
			}
			else if (configurationCategory1.equals(
						"web-experience-management")) {

				return -1;
			}
			else if (configurationCategory1.equals("collaboration")) {
				if (configurationCategory2.equals(
						"web-experience-management")) {

					return 1;
				}
				else {
					return -1;
				}
			}
			else if (configurationCategory1.equals("productivity")) {
				if (configurationCategory2.equals(
						"web-experience-management") ||
					configurationCategory2.equals("collaboration")) {

					return 1;
				}
				else {
					return -1;
				}
			}

			return configurationCategory1.compareTo(configurationCategory2);
		}

	}

	private static class ConfigurationModelComparator
		implements Comparator<ConfigurationModel> {

		@Override
		public int compare(
			ConfigurationModel configurationModel1,
			ConfigurationModel configurationModel2) {

			String name1 = configurationModel1.getName();
			String name2 = configurationModel2.getName();

			return name1.compareTo(name2);
		}

	}

}