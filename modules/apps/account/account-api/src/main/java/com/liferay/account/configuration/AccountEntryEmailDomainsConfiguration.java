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

package com.liferay.account.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(
	category = "accounts", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.account.configuration.AccountEntryEmailDomainsConfiguration",
	localization = "content/Language",
	name = "account-entry-email-domains-configuration-name"
)
public interface AccountEntryEmailDomainsConfiguration {

	@Meta.AD(
		deflt = "false", description = "enable-email-domain-validation-help",
		name = "enable-email-domain-validation", required = false
	)
	public boolean enableEmailDomainValidation();

	@Meta.AD(
		description = "blocked-email-domains-help",
		name = "blocked-email-domains", required = false
	)
	public String blockedEmailDomains();

}