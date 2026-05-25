/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.account.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Tancredi Covioli
 */
public interface BaseAccountEntryValidatorConfiguration {

	@Meta.AD(
		deflt = "60",
		description = "account-entry-validator-cache-validity-description",
		min = "0", name = "account-entry-validator-cache-validity",
		required = false, type = Meta.Type.Integer
	)
	public int cacheValiditySeconds();

	@Meta.AD(
		deflt = "true",
		description = "enable-account-entry-validator-description",
		name = "enable-account-entry-validator", required = false
	)
	public boolean enableValidator();

}