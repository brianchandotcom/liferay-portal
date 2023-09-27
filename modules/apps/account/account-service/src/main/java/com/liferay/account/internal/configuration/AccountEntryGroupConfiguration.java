/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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