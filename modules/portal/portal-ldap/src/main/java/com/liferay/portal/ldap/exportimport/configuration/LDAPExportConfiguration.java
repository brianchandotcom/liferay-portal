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

package com.liferay.portal.ldap.exportimport.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;
import com.liferay.portal.ldap.configuration.CompanyScopedConfiguration;

/**
 * @author Michael C. Han
 */
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.ldap.exportimport.configuration.LDAPExportConfiguration",
	localization = "content/Language"
)
public interface LDAPExportConfiguration extends CompanyScopedConfiguration {

	@Meta.AD(deflt = "0", required = false)
	@Override
	public long companyId();

	@Meta.AD(
		deflt = "false", description = "%export-enabled-help", required = false
	)
	public boolean exportEnabled();

	@Meta.AD(
		deflt = "true", description = "%export-group-enabled-help",
		required = false
	)
	public boolean exportGroupEnabled();

}