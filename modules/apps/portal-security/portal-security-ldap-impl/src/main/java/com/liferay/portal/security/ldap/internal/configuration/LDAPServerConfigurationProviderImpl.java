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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.security.ldap.configuration.BaseConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration;
import com.liferay.portal.security.ldap.constants.LDAPConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
	property = "factoryPid=com.liferay.portal.security.ldap.configuration.LDAPServerConfiguration",
	service = ConfigurationProvider.class
)
public class LDAPServerConfigurationProviderImpl
	extends BaseConfigurationProvider<LDAPServerConfiguration>
	implements ConfigurationProvider<LDAPServerConfiguration> {

	@Override
	public boolean delete(long companyId) {
		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.remove(companyId);

		if (MapUtil.isEmpty(objectValuePairs)) {
			return false;
		}

		for (ObjectValuePair<Configuration, LDAPServerConfiguration>
				objectValuePair : objectValuePairs.values()) {

			Configuration configuration = objectValuePair.getKey();

			_pidCompanyConfigurations.remove(configuration.getPid());
			_pidServerConfigurations.remove(configuration.getPid());

			try {
				configuration.delete();
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return true;
	}

	public boolean delete(long companyId, long ldapServerId) {
		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.get(companyId);

		if (MapUtil.isEmpty(objectValuePairs)) {
			return false;
		}

		ObjectValuePair<Configuration, LDAPServerConfiguration>
			objectValuePair = objectValuePairs.remove(ldapServerId);

		if (objectValuePair == null) {
			return false;
		}

		Configuration configuration = objectValuePair.getKey();

		_pidCompanyConfigurations.remove(configuration.getPid());
		_pidServerConfigurations.remove(configuration.getPid());

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
			ldapServerConfiguration = ConfigurableUtil.createConfigurable(
				getMetatype(), new HashMapDictionary<>());
		}

		return ldapServerConfiguration;
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, long ldapServerId) {

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.get(companyId);

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			defaultObjectValuePairs = _configurations.get(
				CompanyConstants.SYSTEM);

		if (MapUtil.isEmpty(objectValuePairs) &&
			MapUtil.isEmpty(defaultObjectValuePairs)) {

			return _defaultLDAPServerConfiguration;
		}
		else if (MapUtil.isEmpty(objectValuePairs)) {
			objectValuePairs = defaultObjectValuePairs;
		}

		ObjectValuePair<Configuration, LDAPServerConfiguration>
			objectValuePair = objectValuePairs.get(ldapServerId);

		if ((objectValuePair == null) &&
			!MapUtil.isEmpty(defaultObjectValuePairs)) {

			objectValuePair = defaultObjectValuePairs.get(
				LDAPServerConfiguration.LDAP_SERVER_ID_DEFAULT);
		}

		if (objectValuePair == null) {
			return _defaultLDAPServerConfiguration;
		}

		return objectValuePair.getValue();
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

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.get(companyId);

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			defaultObjectValuePairs = _configurations.get(
				CompanyConstants.SYSTEM);

		if (MapUtil.isEmpty(objectValuePairs) &&
			MapUtil.isEmpty(defaultObjectValuePairs)) {

			return new HashMapDictionary<>();
		}
		else if (MapUtil.isEmpty(objectValuePairs)) {
			objectValuePairs = defaultObjectValuePairs;
		}

		ObjectValuePair<Configuration, LDAPServerConfiguration>
			objectValuePair = objectValuePairs.get(ldapServerId);

		if ((objectValuePair == null) &&
			!MapUtil.isEmpty(defaultObjectValuePairs)) {

			objectValuePair = defaultObjectValuePairs.get(
				LDAPServerConfiguration.LDAP_SERVER_ID_DEFAULT);
		}

		if (objectValuePair == null) {
			return new HashMapDictionary<>();
		}

		Configuration configuration = objectValuePair.getKey();

		return configuration.getProperties();
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(long companyId) {
		return getConfigurations(companyId, true);
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(
		long companyId, boolean useDefault) {

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.get(companyId);

		if (MapUtil.isEmpty(objectValuePairs) && useDefault) {
			objectValuePairs = _configurations.get(CompanyConstants.SYSTEM);
		}

		List<LDAPServerConfiguration> ldapServerConfigurations =
			new ArrayList<>();

		if (MapUtil.isEmpty(objectValuePairs) && useDefault) {
			ldapServerConfigurations.add(_defaultLDAPServerConfiguration);
		}
		else if (!MapUtil.isEmpty(objectValuePairs)) {
			List<ObjectValuePair<Configuration, LDAPServerConfiguration>>
				objectValuePairsList = new ArrayList<>(
					objectValuePairs.values());

			objectValuePairsList.sort(
				Comparator.comparing(
					o -> {
						Configuration configuration = o.getKey();

						try {
							Dictionary<String, Object> properties =
								configuration.getProperties();

							return GetterUtil.getLong(
								properties.get(
									LDAPConstants.AUTH_SERVER_PRIORITY));
						}
						catch (IllegalStateException ise) {
							return 0L;
						}
					}));

			objectValuePairsList.forEach(
				o -> ldapServerConfigurations.add(o.getValue()));
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

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.get(companyId);

		if (MapUtil.isEmpty(objectValuePairs) && useDefault) {
			objectValuePairs = _configurations.get(CompanyConstants.SYSTEM);
		}

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>();

		if (MapUtil.isEmpty(objectValuePairs) && useDefault) {
			configurationsProperties.add(
				new HashMapDictionary<String, Object>());
		}
		else if (!MapUtil.isEmpty(objectValuePairs)) {
			for (ObjectValuePair<Configuration, LDAPServerConfiguration>
					objectValuePair : objectValuePairs.values()) {

				Configuration configuration = objectValuePair.getKey();

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
			ConfigurableUtil.createConfigurable(getMetatype(), properties);

		synchronized (_configurations) {
			Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
				ldapServerConfigurations = _configurations.computeIfAbsent(
					ldapServerConfiguration.companyId(), k -> new TreeMap<>());

			ldapServerConfigurations.put(
				ldapServerConfiguration.ldapServerId(),
				new ObjectValuePair<>(configuration, ldapServerConfiguration));

			_pidCompanyConfigurations.put(
				configuration.getPid(), ldapServerConfiguration.companyId());
			_pidServerConfigurations.put(
				configuration.getPid(), ldapServerConfiguration.ldapServerId());
		}
	}

	@Override
	public void unregisterConfiguration(String pid) {
		synchronized (_configurations) {
			Long companyId = _pidCompanyConfigurations.remove(pid);
			Long ldapServerId = _pidServerConfigurations.remove(pid);

			Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
				objectValuePairs = null;

			if (companyId != null) {
				objectValuePairs = _configurations.get(companyId);
			}

			if ((ldapServerId != null) && !MapUtil.isEmpty(objectValuePairs)) {
				objectValuePairs.remove(ldapServerId);
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

		properties.put(LDAPConstants.COMPANY_ID, companyId);
		properties.put(LDAPConstants.LDAP_SERVER_ID, ldapServerId);

		Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>
			objectValuePairs = _configurations.computeIfAbsent(
				companyId, k -> new HashMap<>());

		try {
			ObjectValuePair<Configuration, LDAPServerConfiguration>
				objectValuePair = objectValuePairs.get(ldapServerId);

			Configuration configuration = null;

			if (objectValuePair == null) {
				configuration = configurationAdmin.createFactoryConfiguration(
					getMetatypeId(), StringPool.QUESTION);
			}
			else {
				configuration = objectValuePair.getKey();
			}

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

	private final Map
		<Long,
		 Map<Long, ObjectValuePair<Configuration, LDAPServerConfiguration>>>
			_configurations = new ConcurrentHashMap<>();
	private final LDAPServerConfiguration _defaultLDAPServerConfiguration =
		ConfigurableUtil.createConfigurable(
			LDAPServerConfiguration.class, Collections.emptyMap());
	private final Map<String, Long> _pidCompanyConfigurations = new HashMap<>();
	private final Map<String, Long> _pidServerConfigurations = new HashMap<>();

}