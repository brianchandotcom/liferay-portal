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
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.ldap.configuration.AbstractConfigurationProvider;
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
	extends AbstractConfigurationProvider<LDAPServerConfiguration>
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
		return getConfiguration(companyId, true);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, boolean useDefault) {

		List<LDAPServerConfiguration> ldapServerConfigurations =
			getConfigurations(companyId, useDefault);

		LDAPServerConfiguration ldapServerConfiguration = null;

		if (!ldapServerConfigurations.isEmpty()) {
			ldapServerConfiguration = ldapServerConfigurations.get(0);
		}
		else if (useDefault) {
			ldapServerConfiguration = Configurable.createConfigurable(
				getMetatype(), new HashMapDictionary<>());
		}

		return ldapServerConfiguration;
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

		if (properties == null) {
			return null;
		}

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
			getConfigurationsProperties(companyId);

		if (configurationsProperties.isEmpty() && !useDefault) {
			return null;
		}

		if (configurationsProperties.isEmpty()) {
			return new HashMapDictionary<>();
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

		if ((configurations == null) && useDefault) {
			return new HashMapDictionary<>();
		}
		else if ((configurations == null) && !useDefault) {
			return null;
		}

		Configuration configuration = configurations.get(ldapServerId);

		if ((configuration == null) && useDefault) {
			return new HashMapDictionary<>();
		}
		else if ((configuration == null) && !useDefault) {
			return null;
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
			getConfigurationsProperties(companyId);

		List<LDAPServerConfiguration> ldapServerConfigurations =
			new ArrayList<>(configurationsProperties.size());

		if (ListUtil.isEmpty(configurationsProperties) && useDefault) {
			LDAPServerConfiguration ldapServerConfiguration =
				Configurable.createConfigurable(
					getMetatype(), new HashMapDictionary<>());

			ldapServerConfigurations.add(ldapServerConfiguration);
		}
		else if (ListUtil.isNotEmpty(configurationsProperties)) {
			for (Dictionary<String, Object> configurationProperties :
					configurationsProperties) {

				LDAPServerConfiguration ldapServerConfiguration =
					Configurable.createConfigurable(
						getMetatype(), configurationProperties);

				ldapServerConfigurations.add(ldapServerConfiguration);
			}
		}

		return ldapServerConfigurations;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

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

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		Tuple tuple = new Tuple(
			ldapServerConfiguration.companyId(),
			ldapServerConfiguration.ldapServerId());

		_pidToCompanyIdLDAPServerId.put(configuration.getPid(), tuple);

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
		Tuple tuple = _pidToCompanyIdLDAPServerId.get(configuration.getPid());

		if (tuple == null) {
			return;
		}

		long companyId = (Long)tuple.getObject(0);
		long ldapServerId = (Long)tuple.getObject(1);

		Map<Long, Configuration> configurations = _configurations.get(
			companyId);

		synchronized (_configurations) {
			if (!MapUtil.isEmpty(configurations)) {
				configurations.remove(ldapServerId);
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

	@Reference(unbind = "-")
	protected void setConfigurationAdmin(
		ConfigurationAdmin configurationAdmin) {

		super.configurationAdmin = configurationAdmin;
	}

	private final Map<Long, Map<Long, Configuration>>
		_configurations = new ConcurrentHashMap<>();
	private final Map<String, Tuple> _pidToCompanyIdLDAPServerId =
		new HashMap<>();

}