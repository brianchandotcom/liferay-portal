/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.on.demand.admin.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Pei-Jung Lan
 */
@ExtendedObjectClassDefinition(category = "users")
@Meta.OCD(
	id = "com.liferay.on.demand.admin.internal.configuration.OnDemandAdminConfiguration",
	localization = "content/Language",
	name = "on-demand-admin-configuration-name"
)
public interface OnDemandAdminConfiguration {

	@Meta.AD(
		deflt = "5",
		description = "authentication-token-expiration-time-description",
		name = "authentication-token-expiration-time", required = false
	)
	public int authenticationTokenExpirationTime();

	@Meta.AD(
		deflt = "24", description = "clean-up-interval-description",
		name = "clean-up-interval", required = false
	)
	public int cleanUpInterval();

}