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

import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.configuration.LDAPServerConfiguration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.cm.Configuration;
import org.osgi.service.component.annotations.Component;

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
	public LDAPServerConfiguration getConfiguration(long companyId) {
		List<LDAPServerConfiguration> ldapServerConfigurations =
			getConfigurations(companyId);

		if (ldapServerConfigurations.isEmpty()) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId);
		}

		return ldapServerConfigurations.get(0);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, long ldapServerId) {

		Dictionary<String, Object> properties = getConfigurationProperties(
			companyId, ldapServerId);

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		return ldapServerConfiguration;
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId) {

		List<Dictionary<String, Object>> configurationsProperties =
			getConfigurationsProperties(companyId);

		if (configurationsProperties.isEmpty()) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId);
		}

		return configurationsProperties.get(0);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long ldapServerId) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (MapUtil.isEmpty(configurations)) {
			configurations = _configurations.get(0L);
		}

		if (MapUtil.isEmpty(configurations)) {
			throw new IllegalArgumentException(
				"No default LDAP server configuration found");
		}

		Configuration configuration = configurations.get(ldapServerId);

		if (configuration == null) {
			throw new IllegalArgumentException(
				"No LDAP server configuration found for company " + companyId +
					" and LDAP server " + ldapServerId);
		}

		Dictionary<String, Object> properties = configuration.getProperties();

		return properties;
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(long companyId) {
		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (MapUtil.isEmpty(configurations)) {
			configurations = _configurations.get(0L);
		}

		if (MapUtil.isEmpty(configurations)) {
			return Collections.emptyList();
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			new ArrayList<>(configurations.size());

		for (Configuration configuration : configurations.values()) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			LDAPServerConfiguration ldapServerConfiguration =
				Configurable.createConfigurable(getMetatype(), properties);

			ldapServerConfigurations.add(ldapServerConfiguration);
		}

		return ldapServerConfigurations;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (MapUtil.isEmpty(configurations)) {
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

	private final Map<Long, Map<Long, Configuration>>
		_configurations = new ConcurrentHashMap<>();

}