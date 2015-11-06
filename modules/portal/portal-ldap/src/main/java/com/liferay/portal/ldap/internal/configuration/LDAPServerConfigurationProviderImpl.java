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

package com.liferay.portal.ldap.internal.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.ldap.constants.LDAPConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"factoryPid=com.liferay.portal.ldap.configuration.LDAPServerConfiguration"
	},
	service = ConfigurationProvider.class
)
public class LDAPServerConfigurationProviderImpl
	implements ConfigurationProvider<LDAPServerConfiguration> {

	@Override
	public boolean delete(long companyId) {
		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (MapUtil.isEmpty(configurations)) {
			return false;
		}

		for (Configuration configuration : configurations.values()) {
			try {
				configuration.delete();
			}
			catch (IOException ie) {
				throw new SystemException(ie);
			}
		}

		return true;
	}

	@Override
	public boolean delete(long companyId, long ldapServerId) {
		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (MapUtil.isEmpty(configurations)) {
			return false;
		}

		Configuration configuration = configurations.get(ldapServerId);

		if (configuration == null) {
			return false;
		}

		try {
			configuration.delete();
		}
		catch (IOException ie) {
			throw new SystemException(ie);
		}

		return true;
	}

	@Override
	public LDAPServerConfiguration getConfiguration(long companyId) {
		return getConfiguration(companyId, true);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, boolean useDefault) {

		List<LDAPServerConfiguration> ldapServerConfigurations =
			getConfigurations(companyId, useDefault);

		if (ldapServerConfigurations.isEmpty()) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId);
		}

		return ldapServerConfigurations.get(0);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, long ldapServerId) {

		return getConfiguration(companyId, ldapServerId, true);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, long ldapServerId, boolean useDefault) {

		Dictionary<String, Object> properties = getConfigurationProperties(
			companyId, ldapServerId, useDefault);

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		return ldapServerConfiguration;
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId) {

		return getConfigurationProperties(companyId, true);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, boolean useDefault) {

		List<Dictionary<String, Object>> configurationsProperties =
			getConfigurationsProperties(companyId, useDefault);

		if (configurationsProperties.isEmpty()) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId);
		}

		return configurationsProperties.get(0);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long ldapServerId) {

		return getConfigurationProperties(companyId, ldapServerId, true);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long ldapServerId, boolean useDefault) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (useDefault && MapUtil.isEmpty(configurations)) {
			configurations = _configurations.get(0L);
		}
		else if (!useDefault && MapUtil.isEmpty(configurations)) {
			return new HashMapDictionary<>();
		}

		Configuration configuration = configurations.get(ldapServerId);

		if (useDefault && (configuration == null)) {
			configurations = _configurations.get(0L);

			configuration = configurations.get(0L);
		}

		if (configuration == null) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId +
					" and LDAP server " + ldapServerId);
		}

		return configuration.getProperties();
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(long companyId) {
		return getConfigurations(companyId, true);
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(
		long companyId, boolean useDefault) {

		List<Dictionary<String, Object>> configurationsProperties =
			getConfigurationsProperties(companyId, useDefault);

		if (configurationsProperties.isEmpty()) {
			return Collections.emptyList();
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			new ArrayList<>(configurationsProperties.size());

		for (Dictionary<String, Object> configurationProperties :
				configurationsProperties) {

			LDAPServerConfiguration ldapServerConfiguration =
				Configurable.createConfigurable(
					getMetatype(), configurationProperties);

			ldapServerConfigurations.add(ldapServerConfiguration);
		}

		return ldapServerConfigurations;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		return getConfigurationsProperties(companyId, true);
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId, boolean useDefault) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (useDefault && MapUtil.isEmpty(configurations)) {
			configurations = _configurations.get(0L);
		}

		if (MapUtil.isEmpty(configurations)) {
			return Collections.emptyList();
		}

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>(configurations.size());

		for (Configuration configuration : configurations.values()) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			configurationsProperties.add(properties);
		}

		return configurationsProperties;
	}

	@Override
	public Class<LDAPServerConfiguration> getMetatype() {
		return LDAPServerConfiguration.class;
	}

	@Override
	public void registerConfiguration(Configuration configuration) {
		Dictionary<String, Object> properties = configuration.getProperties();

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		synchronized (_configurations) {
			Map<Long, Configuration> ldapServerConfigurations =
				_configurations.get(ldapServerConfiguration.companyId());

			if (ldapServerConfigurations == null) {
				ldapServerConfigurations = new TreeMap<>();

				_configurations.put(
					ldapServerConfiguration.companyId(),
					ldapServerConfigurations);
			}

			ldapServerConfigurations.put(
				ldapServerConfiguration.ldapServerId(), configuration);
		}
	}

	@Override
	public void unregisterConfiguration(Configuration configuration) {
		Dictionary<String, Object> properties = configuration.getProperties();

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		synchronized (_configurations) {
			Map<Long, Configuration> configurations = _configurations.get(
				ldapServerConfiguration.companyId());

			if (!MapUtil.isEmpty(configurations)) {
				configurations.remove(ldapServerConfiguration.ldapServerId());
			}
		}
	}

	@Override
	public void updateProperties(
		long companyId, Dictionary<String, Object> properties) {

		updateProperties(companyId, 0L, properties);
	}

	@Override
	public void updateProperties(
		long companyId, long ldapServerId,
		Dictionary<String, Object> properties) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (configurations == null) {
			configurations = new HashMap<>();

			_configurations.put(companyId, configurations);
		}

		Map<Long, Configuration> defaultConfigurations = _configurations.get(
			0L);

		if (defaultConfigurations == null) {
			Class<?> metatype = getMetatype();

			throw new IllegalArgumentException(
				"No default configuration for " + metatype.getName());
		}

		try {
			Configuration configuration = configurations.get(ldapServerId);

			if (configuration == null) {
				Configuration defaultConfiguration = defaultConfigurations.get(
					0L);

				configuration = _configurationAdmin.createFactoryConfiguration(
					defaultConfiguration.getFactoryPid());
			}

			properties.put(LDAPConstants.COMPANY_ID, companyId);
			properties.put(LDAPConstants.LDAP_SERVER_ID, ldapServerId);

			configuration.update(properties);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to update configuration", ioe);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		_configurationAdmin = configurationAdmin;
	}

	private ConfigurationAdmin _configurationAdmin;
	private final Map<Long, Map<Long, Configuration>>
		_configurations = new ConcurrentHashMap<>();

}