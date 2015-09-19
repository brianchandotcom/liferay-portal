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

package com.liferay.portal.ldap.settings;

import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationFactory;
import com.liferay.portal.kernel.settings.CompanyServiceSettingsLocator;
import com.liferay.portal.ldap.configuration.LDAPConfiguration;
import com.liferay.portal.model.CompanyConstants;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = LDAPConfigurationSettingsUtil.class)
public class LDAPConfigurationSettingsUtil {

	public LDAPConfiguration getLDAPConfiguration() {
		return getLDAPConfiguration(CompanyConstants.SYSTEM);
	}

	public LDAPConfiguration getLDAPConfiguration(long companyId) {
		try {
			LDAPConfiguration ldapConfiguration =
				_configurationFactory.getConfiguration(
					LDAPConfiguration.class,
					new CompanyServiceSettingsLocator(
						companyId, SettingsConstants.SERVICE_NAME));

			return ldapConfiguration;
		}
		catch (ConfigurationException e) {
			throw new IllegalStateException(e);
		}
	}

	@Reference(unbind = "-")
	protected void setConfigurationFactory(
		ConfigurationFactory configurationFactory) {

		_configurationFactory = configurationFactory;
	}

	private ConfigurationFactory _configurationFactory;

}