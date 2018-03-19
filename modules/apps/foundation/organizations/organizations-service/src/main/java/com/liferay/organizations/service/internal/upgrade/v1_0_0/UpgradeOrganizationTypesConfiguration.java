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

package com.liferay.organizations.service.internal.upgrade.v1_0_0;

import com.liferay.organizations.service.internal.configuration.OrganizationTypeConfiguration;
import com.liferay.organizations.service.internal.constants.LegacyOrganizationTypesKeys;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.StringBundler;

import java.util.Dictionary;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

/**
 * @author Drew Brokke
 */
public class UpgradeOrganizationTypesConfiguration extends UpgradeProcess {

	public UpgradeOrganizationTypesConfiguration(
		ConfigurationAdmin configurationAdmin, Props props) {

		_configurationAdmin = configurationAdmin;
		_props = props;
	}

	@Override
	protected void doUpgrade() throws Exception {
		for (String organizationType :
				_props.getArray(
					LegacyOrganizationTypesKeys.ORGANIZATIONS_TYPES)) {

			Dictionary<String, Object> properties = new HashMapDictionary<>();

			properties.put("name", organizationType);

			Filter filter = new Filter(organizationType);

			properties.put(
				"childrenTypes",
				GetterUtil.getStringValues(
					_props.getArray(
						LegacyOrganizationTypesKeys.
							ORGANIZATIONS_CHILDREN_TYPES,
						filter),
					_organizationTypeConfiguration.childrenTypes()));
			properties.put(
				"countryEnabled",
				GetterUtil.getBoolean(
					_props.get(
						LegacyOrganizationTypesKeys.
							ORGANIZATIONS_COUNTRY_ENABLED,
						filter),
					_organizationTypeConfiguration.countryEnabled()));
			properties.put(
				"countryRequired",
				GetterUtil.getBoolean(
					_props.get(
						LegacyOrganizationTypesKeys.
							ORGANIZATIONS_COUNTRY_REQUIRED,
						filter),
					_organizationTypeConfiguration.countryRequired()));
			properties.put(
				"rootable",
				GetterUtil.getBoolean(
					_props.get(
						LegacyOrganizationTypesKeys.ORGANIZATIONS_ROOTABLE,
						filter),
					_organizationTypeConfiguration.rootable()));

			Configuration configuration = _getFactoryConfigurationInstance(
				organizationType);

			configuration.update(properties);
		}
	}

	private Configuration _getFactoryConfigurationInstance(
			String organizationType)
		throws Exception {

		Configuration[] configurations = _configurationAdmin.listConfigurations(
			StringBundler.concat(
				"(&(", ConfigurationAdmin.SERVICE_FACTORYPID, "=", _FACTORY_PID,
				")(name=", organizationType, "))"));

		if (configurations != null) {
			return configurations[0];
		}

		return _configurationAdmin.createFactoryConfiguration(
			_FACTORY_PID, StringPool.QUESTION);
	}

	private static final String _FACTORY_PID =
		OrganizationTypeConfiguration.class.getName();

	private final ConfigurationAdmin _configurationAdmin;
	private final OrganizationTypeConfiguration _organizationTypeConfiguration =
		ConfigurableUtil.createConfigurable(
			OrganizationTypeConfiguration.class,
			new HashMapDictionary<String, Object>());
	private final Props _props;

}