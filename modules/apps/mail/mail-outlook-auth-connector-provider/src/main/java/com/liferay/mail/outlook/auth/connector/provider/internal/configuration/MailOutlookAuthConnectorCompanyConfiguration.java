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

package com.liferay.mail.outlook.auth.connector.provider.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Rafael Praxedes
 */
@ExtendedObjectClassDefinition(
	category = "email", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.mail.outlook.auth.connector.provider.internal.configuration.MailOutlookAuthConnectorCompanyConfiguration",
	localization = "content/Language",
	name = "outlook-auth-connector-configuration-name"
)
public interface MailOutlookAuthConnectorCompanyConfiguration {

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		description = "outlook-auth-connector-client-id-description",
		name = "client-id", required = false
	)
	public String clientId();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		description = "outlook-auth-connector-client-secret-description",
		name = "client-secret", required = false
	)
	public String clientSecret();

	@ExtendedAttributeDefinition(
		descriptionArguments = "https://learn.microsoft.com/en-us/azure/active-directory/fundamentals/active-directory-how-to-find-tenant",
		requiredInput = true
	)
	@Meta.AD(
		description = "outlook-auth-connector-tenant-id-description",
		name = "tenant-id", required = false
	)
	public String tenantId();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "false",
		description = "outlook-auth-connector-pop3-connection-enabled-description",
		name = "outlook-auth-connector-pop3-connection-enabled",
		required = false
	)
	public boolean pop3ConnectionEnabled();

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "false",
		description = "outlook-auth-connector-smtp-connection-enabled-description",
		name = "outlook-auth-connector-smtp-connection-enabled",
		required = false
	)
	public boolean smtpConnectionEnabled();

}