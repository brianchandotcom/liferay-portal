/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.configuration.easyconf;

import com.germinus.easyconf.AggregatedProperties;
import com.germinus.easyconf.ConfigurationException;
import com.germinus.easyconf.Conventions;
import com.germinus.easyconf.DatasourceURL;
import com.germinus.easyconf.FileConfigurationChangedReloadingStrategy;
import com.germinus.easyconf.JndiURL;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.net.URL;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.AbstractFileConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.SubsetConfiguration;
import org.apache.commons.configuration.SystemConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;

/**
 * @author Raymond Augé
 */
public class ClassLoaderAggregateProperties extends AggregatedProperties {

	public ClassLoaderAggregateProperties(
		ClassLoader classLoader, String companyId, String componentName) {

		super(companyId, componentName);

		_classLoader = classLoader;
		_companyId = companyId;
		_componentName = componentName;

		_baseConfiguration = new CompositeConfiguration();
		_globalConfiguration = new CompositeConfiguration();
		_systemConfiguration = new SystemConfiguration();
		_prefixedSystemConfiguration = new SubsetConfiguration(
			_systemConfiguration, getPrefix(), null);
	}

	@Override
	public void addBaseFileName(String fileName) {
		URL url = _classLoader.getResource(fileName);

		Configuration conf = addPropertiesSource(
			fileName, url, _baseConfiguration);

		if ((conf != null) && (!conf.isEmpty())) {
			_baseConfigurationLoaded = true;
		}
	}

	@Override
	public void addGlobalFileName(String fileName) {
		URL url = _classLoader.getResource(fileName);

		addPropertiesSource(fileName, url, _globalConfiguration);
	}

	public CompositeConfiguration getBaseConfiguration() {
		return _baseConfiguration;
	}

	@Override
	public String getComponentName() {
		return _componentName;
	}

	@Override
	public Object getProperty(String key) {
		Object value = null;

		if (value == null) {
			value = System.getProperty(getPrefix().concat(key));
		}

		if (value == null) {
			value = _globalConfiguration.getProperty(getPrefix().concat(key));
		}

		if (value == null) {
			value = _globalConfiguration.getProperty(key);
		}

		if (value == null) {
			value = _baseConfiguration.getProperty(key);
		}

		if (value == null) {
			value = super.getProperty(key);
		}

		if (value == null) {
			value = System.getProperty(key);
		}

		if ((value == null) && (key.equals(Conventions.COMPANY_ID_PROPERTY))) {
			value = _companyId;
		}

		if ((value == null) &&
			(key.equals(Conventions.COMPONENT_NAME_PROPERTY))) {

			value = _componentName;
		}

		return value;
	}

	@Override
	public boolean hasBaseConfiguration() {
		return _baseConfigurationLoaded;
	}

	@Override
	public List<String> loadedSources() {
		return _loadedSources;
	}

	private Configuration addDatasourceProperties(String datasourcePath) {
		DatasourceURL dsUrl = new DatasourceURL(
			datasourcePath, _companyId, _componentName,
			DatasourceURL.PROPERTIES_TABLE);

		return dsUrl.getConfiguration();
	}

	private Configuration addFileProperties(
			String fileName, CompositeConfiguration loadedConf)
		throws ConfigurationException {

		try {
			FileConfiguration newConf = new PropertiesConfiguration(fileName);

			URL fileURL = newConf.getURL();

			if (_log.isDebugEnabled()) {
				_log.debug("Adding file: " + fileURL);
			}

			Long delay = getReloadDelay(loadedConf, newConf);

			if (delay != null) {
				FileChangedReloadingStrategy reloadingStrategy =
					new FileConfigurationChangedReloadingStrategy();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"File " + fileURL + " will be reloaded every " +
							delay + " seconds");
				}

				long milliseconds = delay.longValue() * 1000;

				reloadingStrategy.setRefreshDelay(milliseconds);

				newConf.setReloadingStrategy(reloadingStrategy);
			}

			addIncludedPropertiesSources(newConf, loadedConf);

			return newConf;
		}
		catch (org.apache.commons.configuration.ConfigurationException e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Configuration source " + fileName + " ignored");
			}

			return null;
		}
	}

	private Configuration addJndiProperties(String sourcePath) {
		JndiURL jndiUrl = new JndiURL(sourcePath, _companyId, _componentName);

		return jndiUrl.getConfiguration();
	}

	private void addIncludedPropertiesSources(
		Configuration newConf, CompositeConfiguration loadedConf) {

		CompositeConfiguration tempConf = new CompositeConfiguration();

		tempConf.addConfiguration(_prefixedSystemConfiguration);
		tempConf.addConfiguration(newConf);
		tempConf.addConfiguration(_systemConfiguration);
		tempConf.addProperty(Conventions.COMPANY_ID_PROPERTY, _companyId);
		tempConf.addProperty(
			Conventions.COMPONENT_NAME_PROPERTY, _componentName);

		String[] fileNames = tempConf.getStringArray(
			Conventions.INCLUDE_PROPERTY);

		for (String fileName : fileNames) {
			URL url = _classLoader.getResource(fileName);

			addPropertiesSource(fileName, url, loadedConf);
		}
	}

	private Configuration addPropertiesSource(
		String sourceName, URL url, CompositeConfiguration loadedConf) {

		try {
			Configuration newConf;

			if (DatasourceURL.isDatasource(sourceName)) {
				newConf = addDatasourceProperties(sourceName);
			}
			else if (JndiURL.isJndi(sourceName)) {
				newConf = addJndiProperties(sourceName);
			}
			else if (url != null) {
				newConf = addUrlProperties(url, loadedConf);
			}
			else {
				newConf = addFileProperties(sourceName, loadedConf);
			}

			if (newConf != null) {
				loadedConf.addConfiguration(newConf);

				super.addConfiguration(newConf);

				if (newConf instanceof AbstractFileConfiguration) {
					AbstractFileConfiguration abstractFileConfiguration =
						(AbstractFileConfiguration)newConf;

					_loadedSources.add(
						abstractFileConfiguration.getURL().toString());
				}
				else {
					_loadedSources.add(sourceName);
				}
			}

			return newConf;
		}
		catch (Exception ignore) {
			if (_log.isDebugEnabled()) {
				_log.debug("Configuration source " + sourceName + " ignored");
			}

			return null;
		}
	}

	private Configuration addUrlProperties(
			URL url, CompositeConfiguration loadedConf)
		throws ConfigurationException {

		String resourceURL = url.toString();

		try {
			FileConfiguration newConf = new PropertiesConfiguration(url);

			if (_log.isDebugEnabled()) {
				_log.debug("Adding resource: " + resourceURL);
			}

			Long delay = getReloadDelay(loadedConf, newConf);

			if (delay != null) {
				FileChangedReloadingStrategy reloadingStrategy =
					new FileConfigurationChangedReloadingStrategy();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Resource " + resourceURL + " will be reloaded every " +
							delay + " seconds");
				}

				long milliseconds = delay.longValue() * 1000;

				reloadingStrategy.setRefreshDelay(milliseconds);

				newConf.setReloadingStrategy(reloadingStrategy);
			}

			addIncludedPropertiesSources(newConf, loadedConf);

			return newConf;
		}
		catch (org.apache.commons.configuration.ConfigurationException e) {
			if (_log.isDebugEnabled()) {
				_log.debug("Configuration source " + resourceURL + " ignored");
			}

			return null;
		}
	}

	private String getPrefix() {
		return _componentName.concat(Conventions.PREFIX_SEPARATOR);
	}

	private Long getReloadDelay(
		CompositeConfiguration loadedConf, FileConfiguration newConf) {

		Long delay = newConf.getLong(Conventions.RELOAD_DELAY_PROPERTY, null);

		if (delay == null) {
			delay = loadedConf.getLong(Conventions.RELOAD_DELAY_PROPERTY, null);
		}

		return delay;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AggregatedProperties.class);

	private boolean _baseConfigurationLoaded = false;
	private CompositeConfiguration _baseConfiguration;
	private ClassLoader _classLoader;
	private String _companyId;
	private String _componentName;
	private CompositeConfiguration _globalConfiguration;
	private List<String> _loadedSources = new ArrayList<String>();
	private SystemConfiguration _systemConfiguration;
	private Configuration _prefixedSystemConfiguration;

}