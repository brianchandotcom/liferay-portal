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

package com.liferay.commerce.organization.service.impl.test.util;

import com.liferay.commerce.organization.constants.CommerceOrganizationConstants;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Dictionary;
import java.util.Hashtable;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Luca Pellizzon
 */
public class OrganizationTypeSetUp {

	public OrganizationTypeSetUp(ConfigurationAdmin configurationAdmin) {
		_configurationAdmin = configurationAdmin;
	}

	public void setUpEnvironment() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getConfigurationFilter(_ORGANIZATION_TYPE_CONFIGURATION_PID));

		_savedConfigurations = configurations.clone();

		for (String organizationType : _ORGANIZATION_TYPES) {
			_updateOrganizationType(configurations, organizationType);
		}
	}

	public void tearDownEnvironment() throws Exception {
		Configuration[] configurations = _configurationAdmin.listConfigurations(
			_getConfigurationFilter(_ORGANIZATION_TYPE_CONFIGURATION_PID));

		boolean account = false;
		boolean branch = false;

		for (Configuration configuration : configurations) {
			Dictionary<String, Object> properties =
				configuration.getProperties();

			String name = (String)properties.get("name");

			for (Configuration savedConfiguration : _savedConfigurations) {
				Dictionary<String, Object> savedProperties =
					savedConfiguration.getProperties();

				String savedName = (String)savedProperties.get("name");

				if (name.equals(savedName)) {
					configuration.update(savedProperties);
				}
			}

			if (!account &&
				name.equals(CommerceOrganizationConstants.TYPE_ACCOUNT)) {

				configuration.delete();
			}

			if (!branch &&
				name.equals(CommerceOrganizationConstants.TYPE_BRANCH)) {

				configuration.delete();
			}
		}
	}

	private void _createOrganizationType(String organizationType)
		throws Exception {

		Configuration configuration =
			_configurationAdmin.createFactoryConfiguration(
				_ORGANIZATION_TYPE_CONFIGURATION_PID, StringPool.QUESTION);

		configuration.update(
			_getOrganizationTypeProperties(configuration, organizationType));
	}

	private String _getConfigurationFilter(String configurationPid) {
		StringBundler sb = new StringBundler(5);

		sb.append(StringPool.OPEN_PARENTHESIS);
		sb.append(ConfigurationAdmin.SERVICE_FACTORYPID);
		sb.append(StringPool.EQUAL);
		sb.append(configurationPid);
		sb.append(StringPool.CLOSE_PARENTHESIS);

		return sb.toString();
	}

	private String[] _getOrganizationTypeChildrenTypes(
		String organizationType) {

		if (organizationType.equals(OrganizationConstants.TYPE_ORGANIZATION)) {
			return new String[] {
				OrganizationConstants.TYPE_ORGANIZATION,
				CommerceOrganizationConstants.TYPE_ACCOUNT
			};
		}
		else if (organizationType.equals(
					CommerceOrganizationConstants.TYPE_ACCOUNT)) {

			return new String[] {
				OrganizationConstants.TYPE_ORGANIZATION,
				CommerceOrganizationConstants.TYPE_BRANCH
			};
		}

		return new String[0];
	}

	private Dictionary<String, Object> _getOrganizationTypeProperties(
		Configuration configuration, String organizationType) {

		Dictionary<String, Object> properties = configuration.getProperties();

		if (properties == null) {
			properties = new Hashtable<>();
		}

		boolean rootable = false;

		if (organizationType.equals(OrganizationConstants.TYPE_ORGANIZATION)) {
			rootable = true;
		}

		properties.put(
			"childrenTypes",
			_getOrganizationTypeChildrenTypes(organizationType));
		properties.put("countryEnabled", false);
		properties.put("countryRequired", false);
		properties.put("name", organizationType);
		properties.put("rootable", rootable);

		return properties;
	}

	private void _updateOrganizationType(
			Configuration[] configurations, String organizationType)
		throws Exception {

		if (configurations != null) {
			for (Configuration configuration : configurations) {
				Dictionary<String, Object> properties =
					configuration.getProperties();

				String name = (String)properties.get("name");

				if (organizationType.equals(name)) {
					configuration.update(
						_getOrganizationTypeProperties(
							configuration, organizationType));

					return;
				}
			}
		}

		_createOrganizationType(organizationType);
	}

	private static final String _ORGANIZATION_TYPE_CONFIGURATION_PID =
		"com.liferay.organizations.service.internal.configuration." +
			"OrganizationTypeConfiguration";

	private static final String[] _ORGANIZATION_TYPES = ArrayUtil.append(
		new String[] {OrganizationConstants.TYPE_ORGANIZATION},
		CommerceOrganizationConstants.TYPES);

	private final ConfigurationAdmin _configurationAdmin;
	private Configuration[] _savedConfigurations;

}