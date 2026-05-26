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
		deflt = "30",
		description = "account-entry-validator-check-interval-description",
		min = "0", name = "check-interval", required = false,
		type = Meta.Type.Integer
	)
	public int checkInterval();

	@Meta.AD(
		deflt = "true",
		description = "account-entry-validator-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

}