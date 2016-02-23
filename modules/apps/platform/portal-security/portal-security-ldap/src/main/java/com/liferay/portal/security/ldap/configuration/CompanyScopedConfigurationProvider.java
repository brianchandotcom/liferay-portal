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

package com.liferay.portal.security.ldap.configuration;

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.ldap.constants.LDAPConstants;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.cm.Configuration;

/**
 * @author Michael C. Han
 */
public abstract class CompanyScopedConfigurationProvider
	<T extends CompanyScopedConfiguration>
		extends BaseConfigurationProvider<T>
		implements ConfigurationProvider<T> {

	@Override
	public boolean delete(long companyId) {
		Configuration configuration = _configurations.get(companyId);

		if (configuration == null) {
			return false;
		}

		try {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			configuration.delete();

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Deleted configuration " + getMetatypeId() +
						" for company " + companyId + " with properties: " +
							properties);
			}
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		return true;
	}

	@Override
	public boolean delete(long companyId, long index) {
		return delete(companyId);
	}

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
			configuration = _configurations.get(CompanyConstants.SYSTEM);
		}

		Dictionary<String, Object> properties = null;

		if (configuration == null) {
			properties = new HashMapDictionary<>();
		}
		else {
			properties = configuration.getProperties();
		}

		return properties;
	}

	@Override
	public Dictionary<String, Object> getConfigurationProperties(
		long companyId, long index) {

		return getConfigurationProperties(companyId);
	}

	@Override
	public List<T> getConfigurations(long companyId) {
		return getConfigurations(companyId, true);
	}

	@Override
	public List<T> getConfigurations(long companyId, boolean useDefault) {
		List<Dictionary<String, Object>> configurationsProperties =
			getConfigurationsProperties(companyId, useDefault);

		if (ListUtil.isEmpty(configurationsProperties)) {
			return Collections.emptyList();
		}

		List<T> configurables = new ArrayList<>(
			configurationsProperties.size());

		for (Dictionary<String, Object> configurationProperties :
				configurationsProperties) {

			T configurable = Configurable.createConfigurable(
				getMetatype(), configurationProperties);

			configurables.add(configurable);
		}

		return configurables;
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId) {

		return getConfigurationsProperties(companyId, true);
	}

	@Override
	public List<Dictionary<String, Object>> getConfigurationsProperties(
		long companyId, boolean useDefault) {

		Configuration configuration = _configurations.get(companyId);

		if ((configuration == null) && useDefault) {
			configuration = _configurations.get(CompanyConstants.SYSTEM);
		}

		List<Dictionary<String, Object>> configurationsProperties =
			new ArrayList<>();

		if ((configuration == null) && useDefault) {
			configurationsProperties.add(
				new HashMapDictionary<String, Object>());
		}
		else if (configuration != null) {
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

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		T configurable = Configurable.createConfigurable(
			getMetatype(), properties);

		long companyId = configurable.companyId();

		_companyIds.put(configuration.getPid(), companyId);

		_configurations.put(companyId, configuration);
	}

	@Override
	public synchronized void unregisterConfiguration(
		Configuration configuration) {

		String pid = configuration.getPid();

		Long companyId = _companyIds.get(pid);

		if (companyId != null) {
			_configurations.remove(companyId);
		}
	}

	@Override
	public void updateProperties(
		long companyId, Dictionary<String, Object> properties) {

		if (properties == null) {
			properties = new HashMapDictionary<>();
		}

		Configuration configuration = _configurations.get(companyId);

		try {
			if (configuration == null) {
				if (Validator.isNull(factoryPid)) {
					factoryPid = getMetatypeId();
				}

				configuration = configurationAdmin.createFactoryConfiguration(
					factoryPid, StringPool.QUESTION);
			}

			properties.put(LDAPConstants.COMPANY_ID, companyId);

			configuration.update(properties);

			if (_log.isDebugEnabled()) {
				_log.debug(
					"Updated configuration " + getMetatypeId() +
						" for company " + companyId + " with properties: " +
							properties);
			}
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

	private static final Log _log = LogFactoryUtil.getLog(
		CompanyScopedConfigurationProvider.class);

	private final Map<String, Long> _companyIds = new HashMap<>();
	private final Map<Long, Configuration> _configurations = new HashMap<>();

}