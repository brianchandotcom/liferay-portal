/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.internal.configuration.validator;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.account.configuration.BaseAccountEntryValidatorConfiguration;
import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tancredi Covioli
 */
@ExtendedObjectClassDefinition(
	category = "accounts", scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	strictScope = true
)
@Meta.OCD(
	description = "default-account-entry-validator-configuration-description",
	id = "com.liferay.account.internal.configuration.validator.DefaultAccountEntryValidatorConfiguration",
	localization = "content/Language",
	name = "default-account-entry-validator-configuration-name"
)
public interface DefaultAccountEntryValidatorConfiguration
	extends BaseAccountEntryValidatorConfiguration {
}