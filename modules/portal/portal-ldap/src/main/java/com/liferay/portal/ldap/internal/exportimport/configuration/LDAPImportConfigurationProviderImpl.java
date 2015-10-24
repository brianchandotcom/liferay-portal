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

package com.liferay.portal.ldap.internal.exportimport.configuration;

import com.liferay.portal.ldap.configuration.CompanyScopedConfigurationProvider;
import com.liferay.portal.ldap.configuration.ConfigurationProvider;
import com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(
	immediate = true,
	property = {
		"factoryPid=com.liferay.portal.ldap.exportimport.configuration.LDAPImportConfiguration"
	},
	service = ConfigurationProvider.class
)
public class LDAPImportConfigurationProviderImpl
	extends CompanyScopedConfigurationProvider<LDAPImportConfiguration> {

	@Override
	public Class<LDAPImportConfiguration> getMetatype() {
		return LDAPImportConfiguration.class;
	}

}