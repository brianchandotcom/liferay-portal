/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.ldap.internal.exportimport.configuration;

import com.liferay.portal.security.ldap.configuration.CompanyScopedConfigurationProvider;
import com.liferay.portal.security.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(
	property = "factoryPid=com.liferay.portal.security.ldap.exportimport.configuration.LDAPExportConfiguration",
	service = ConfigurationProvider.class
)
public class LDAPExportConfigurationProviderImpl
	extends CompanyScopedConfigurationProvider<LDAPExportConfiguration> {

	@Override
	public Class<LDAPExportConfiguration> getMetatype() {
		return LDAPExportConfiguration.class;
	}

	@Override
	protected ConfigurationAdmin getConfigurationAdmin() {
		return _configurationAdmin;
	}

	@Reference
	private ConfigurationAdmin _configurationAdmin;

}