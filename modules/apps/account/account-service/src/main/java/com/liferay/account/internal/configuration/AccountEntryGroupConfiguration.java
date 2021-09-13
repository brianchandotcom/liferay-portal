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

package com.liferay.account.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedAttributeDefinition;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(
	category = "accounts", scope = ExtendedObjectClassDefinition.Scope.GROUP,
	strictScope = true
)
@Meta.OCD(
	description = "account-entry-group-configuration-description",
	id = "com.liferay.account.internal.configuration.AccountEntryGroupConfiguration",
	localization = "content/Language",
	name = "account-entry-group-configuration-name"
)
public interface AccountEntryGroupConfiguration {

	@ExtendedAttributeDefinition(requiredInput = true)
	@Meta.AD(
		deflt = "business,person", description = "allowed-account-types-help",
		name = "allowed-account-types", optionLabels = {"%business", "%person"},
		optionValues = {"business", "person"}, required = false
	)
	public String[] allowedTypes();

}