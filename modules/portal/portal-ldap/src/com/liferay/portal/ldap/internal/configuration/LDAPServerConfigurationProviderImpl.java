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
				"No LDAPServerConfiguration found for companyId " + companyId);
		}

		return ldapServerConfigurations.get(0);
	}

	@Override
	public LDAPServerConfiguration getConfiguration(
		long companyId, long ldapServerId) {

		Map<Long, LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurations.get(companyId);

		if (MapUtil.isEmpty(ldapServerConfigurations)) {
			ldapServerConfigurations = _ldapServerConfigurations.get(0L);
		}

		if (MapUtil.isEmpty(ldapServerConfigurations)) {
			throw new IllegalArgumentException(
				"No default LDAPServerConfiguration found");
		}

		LDAPServerConfiguration ldapServerConfiguration =
			ldapServerConfigurations.get(ldapServerId);

		if (ldapServerConfiguration == null) {
			throw new IllegalArgumentException(
				"No LDAPServerConfiguration found: companyId=" + companyId +
					", ldapServerId=" + ldapServerId);
		}

		return ldapServerConfiguration;
	}

	@Override
	public List<LDAPServerConfiguration> getConfigurations(long companyId) {
		Map<Long, LDAPServerConfiguration> ldapServerConfigurations =
			_ldapServerConfigurations.get(companyId);

		if (MapUtil.isEmpty(ldapServerConfigurations)) {
			ldapServerConfigurations = _ldapServerConfigurations.get(0L);
		}

		if (MapUtil.isEmpty(ldapServerConfigurations)) {
			return Collections.emptyList();
		}

		return new ArrayList<>(ldapServerConfigurations.values());
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

		long companyId = ldapServerConfiguration.companyId();
		long ldapServerId = ldapServerConfiguration.ldapServerId();

		synchronized (_ldapServerConfigurations) {
			Map<Long, LDAPServerConfiguration>
				ldapServerConfigurations = _ldapServerConfigurations.get(
					companyId);

			if (ldapServerConfigurations == null) {
				ldapServerConfigurations = new TreeMap<>();

				_ldapServerConfigurations.put(
					companyId, ldapServerConfigurations);
			}

			ldapServerConfigurations.put(ldapServerId, ldapServerConfiguration);
		}
	}

	@Override
	public void unregisterConfiguration(Configuration configuration) {
		Dictionary<String, Object> properties = configuration.getProperties();

		LDAPServerConfiguration ldapServerConfiguration =
			Configurable.createConfigurable(getMetatype(), properties);

		long companyId = ldapServerConfiguration.companyId();
		long ldapServerId = ldapServerConfiguration.ldapServerId();

		synchronized (_ldapServerConfigurations) {
			Map<Long, LDAPServerConfiguration>
				ldapServerConfigurations = _ldapServerConfigurations.get(
					companyId);

			if (!MapUtil.isEmpty(ldapServerConfigurations)) {
				ldapServerConfigurations.remove(ldapServerId);
			}
		}
	}

	private final Map<Long, Map<Long, LDAPServerConfiguration>>
		_ldapServerConfigurations = new ConcurrentHashMap<>();

}