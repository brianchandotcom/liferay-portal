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

package com.liferay.portal.security.ldap.internal.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.configuration.BaseConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;

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
		"factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration"
	},
	service = ConfigurationProvider.class
)
public class LDAPServerConfigurationProviderImpl
	extends BaseConfigurationProvider<LDAPServerConfiguration>
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
			catch (IOException ioe) {
				throw new SystemException(ioe);
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
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return true;
	}

	@Override
	public LDAPServerConfiguration getConfiguration(long companyId) {
		List<LDAPServerConfiguration> ldapServerConfigurations =
			getConfigurations(companyId);

		LDAPServerConfiguration ldapServerConfiguration = null;

		if (!ldapServerConfigurations.isEmpty()) {
			ldapServerConfiguration = ldapServerConfigurations.get(0);
		}
		else {
			ldapServerConfiguration = Configurable.createConfigurable(
				getMetatype(), new HashMapDictionary<>());
		}

		return ldapServerConfiguration;
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
			return new HashMapDictionary<>();
		}

		return configurationsProperties.get(0);
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long ldapServerId) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		Map<Long, Configuration> defaultCompanyConfigurations =
			_configurations.get(CompanyConstants.SYSTEM);

		if (MapUtil.isEmpty(configurations) &&
			MapUtil.isEmpty(defaultCompanyConfigurations)) {

			return new HashMapDictionary<>();
		}
		else if (MapUtil.isEmpty(configurations)) {
			configurations = defaultCompanyConfigurations;
		}

		Configuration configuration = configurations.get(ldapServerId);

		if ((configuration == null) &&
			!MapUtil.isEmpty(defaultCompanyConfigurations)) {

			configuration = defaultCompanyConfigurations.get(
				LDAPServerConfiguration.LDAP_SERVER_ID_DEFAULT);
		}

		if (configuration == null) {
			return new HashMapDictionary<>();
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

		if (ListUtil.isEmpty(configurationsProperties)) {
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

		if (MapUtil.isEmpty(configurations) && useDefault) {
			configurations = _configurations.get(CompanyConstants.SYSTEM);
		}

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>();

		if (MapUtil.isEmpty(configurations) && useDefault) {
			configurationsProperties.add(
				new HashMapDictionary<String, Object>());
		}
		else if (!MapUtil.isEmpty(configurations)) {
			for (Configuration configuration : configurations.values()) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				configurationsProperties.add(properties);
			}
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

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		Tuple tuple = new Tuple(
			ldapServerConfiguration.companyId(),
			ldapServerConfiguration.ldapServerId());

		_tuples.put(configuration.getPid(), tuple);

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
		Tuple tuple = _tuples.get(configuration.getPid());

		if (tuple == null) {
			return;
		}

		long companyId = (Long)tuple.getObject(0);

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		synchronized (_configurations) {
			if (!MapUtil.isEmpty(configurations)) {
				long ldapServerId = (Long)tuple.getObject(1);

				configurations.remove(ldapServerId);

				if (configurations.isEmpty()) {
					_configurations.remove(companyId);
				}
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

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		if (configurations == null) {
			configurations = new HashMap<>();

			_configurations.put(companyId, configurations);
		}

		try {
			Configuration configuration = configurations.get(ldapServerId);

			if (configuration == null) {
				if (Validator.isNull(factoryPid)) {
					factoryPid = getMetatypeId();
				}

				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);
			}

			properties.put(LDAPConstants.COMPANY_ID, companyId);
			properties.put(LDAPConstants.LDAP_SERVER_ID, ldapServerId);

			configuration.update(properties);
		}
		catch (IOException ioe) {
			throw new SystemException("Unable to update configuration", ioe);
		}
	}

	@Override
	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		super.configurationAdmin = configurationAdmin;
	}

	private final Map<Long, Map<Long, Configuration>>
		_configurations = new ConcurrentHashMap<>();
	private final Map<String, Tuple> _tuples = new HashMap<>();

}