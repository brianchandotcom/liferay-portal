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

package com.liferay.portal.ldap.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.ldap.constants.LDAPConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Michael C. Han
 */
public abstract class CompanyScopedConfigurationProvider
	<T extends CompanyScopedConfiguration> implements ConfigurationProvider<T> {

	@Override
	public T getConfiguration(long companyId) {
		Dictionary<String, Object> properties = getConfigurationProperties(
			companyId);

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		return configurable;
	}

	@Override
	public T getConfiguration(long companyId, long index) {
		return getConfiguration(companyId);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId) {

		Configuration configuration = _configurations.get(companyId);

		if (configuration == null) {
			configuration = _configurations.get(0L);
		}

		if (configuration == null) {
			Class<?> clazz = getMetatype();

			throw new IllegalArgumentException(
				"No instance of " + clazz.getName() + " for company " +
					companyId);
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		return properties;
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long index) {

		return getConfigurationProperties(companyId);
	}

	@Override
	public List<T> getConfigurations(long companyId) {
		List<T> configurations = new ArrayList<>();

		T t = getConfiguration(companyId);

		configurations.add(t);

		return configurations;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>();

		for (Configuration configuration : _configurations.values()) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			configurationsProperties.add(properties);
		}

		return configurationsProperties;
	}

	@Override
	public synchronized void registerConfiguration(
		Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		long companyId = configurable.companyId();

		_configurations.put(companyId, configuration);
	}

	@Override
	public synchronized void unregisterConfiguration(
		Configuration configuration) {

		Dictionary<String, Object> properties = configuration.getProperties();

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		long companyId = configurable.companyId();

		_configurations.remove(companyId);
	}

	@Override
	public void updateProperties(
		long companyId, Dictionary<String, Object> properties) {

		Configuration configuration = _configurations.get(companyId);

		Configuration defaultConfiguration = _configurations.get(0L);

		if (defaultConfiguration == null) {
			throw new IllegalArgumentException(
				"No default configuration for " + getMetatype().getName());
		}

		try {
			if (configuration == null) {
				configuration = configurationAdmin.createFactoryConfiguration(
					defaultConfiguration.getFactoryPid());
			}

			properties.put(LDAPConstants.COMPANY_ID, companyId);

			configuration.update(properties);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to update configuration", ioe);
		}
	}

	@Override
	public void updateProperties(
		long companyId, long index, Dictionary<String, Object> properties) {

		updateProperties(companyId, properties);
	}

	protected abstract void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin);

	protected ConfigurationAdmin configurationAdmin;

	private final Map<Long, Configuration> _configurations = new HashMap<>();

}