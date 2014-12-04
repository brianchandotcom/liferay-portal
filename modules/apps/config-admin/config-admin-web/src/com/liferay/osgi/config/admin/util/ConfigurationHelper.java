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

package com.liferay.osgi.config.admin.util;

import com.liferay.osgi.config.admin.model.ConfigurationModel;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Constants;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.metatype.MetaTypeInformation;
import org.osgi.service.metatype.MetaTypeService;

/**
 * @author Kamesh Sampath
 * @author Raymond Augé
 */
public class ConfigurationHelper {

	public ConfigurationHelper(
		BundleContext bundleContext, ConfigurationAdmin configurationAdmin,
		MetaTypeService metaTypeService, String languageId) {

		_bundleContext = bundleContext;
		_configurationAdmin = configurationAdmin;
		_metaTypeService = metaTypeService;

		_models = _getModels(languageId);
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
		catch (InvalidSyntaxException | IOException ise) {
			ReflectionUtil.throwException(ise);
		}

		return null;
	}

	public ConfigurationModel getModel(String pid) {
		return _models.get(pid);
	}

	public List<ConfigurationModel> getModels() {
		return new ArrayList<>(_models.values());
	}

	private void _collectModels(
		Bundle bundle, Map<String, ConfigurationModel> modelMap,
		boolean factory, String locale) {

		MetaTypeInformation metaTypeInformation =
			_metaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return;
		}

		String[] pids = null;

		if (factory) {
			pids = metaTypeInformation.getFactoryPids();
		}
		else {
			pids = metaTypeInformation.getPids();
		}

		for (String pid : pids) {
			ConfigurationModel model = _getModel(bundle, pid, factory, locale);

			if (model == null) {
				continue;
			}

			modelMap.put(pid, model);
		}
	}

	private ConfigurationModel _getModel(
		Bundle bundle, String pid, boolean factory, String locale) {

		MetaTypeInformation metaTypeInformation =
			_metaTypeService.getMetaTypeInformation(bundle);

		if (metaTypeInformation == null) {
			return null;
		}

		ConfigurationModel model = new ConfigurationModel(
			metaTypeInformation.getObjectClassDefinition(pid, locale),
			bundle.getLocation(), factory);

		Configuration configuration = getConfiguration(pid);

		if (configuration != null) {
			model.setConfiguration(configuration);
		}

		return model;
	}

	private Map<String, ConfigurationModel> _getModels(String locale) {
		Map<String, ConfigurationModel> modelMap =
			new HashMap<String, ConfigurationModel>();

		Bundle[] bundles = _bundleContext.getBundles();

		for (Bundle bundle : bundles) {
			_collectModels(bundle, modelMap, false, locale);
			_collectModels(bundle, modelMap, true, locale);
		}

		return modelMap;
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
	private final ConfigurationAdmin _configurationAdmin;
	private final MetaTypeService _metaTypeService;
	private final Map<String, ConfigurationModel> _models;

}