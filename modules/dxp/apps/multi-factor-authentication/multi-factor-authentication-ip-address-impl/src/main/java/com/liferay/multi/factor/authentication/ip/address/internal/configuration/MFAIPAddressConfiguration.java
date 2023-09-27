/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.multi.factor.authentication.ip.address.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Marta Medio
 */
@ExtendedObjectClassDefinition(
	category = "multi-factor-authentication",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY,
	visibilityControllerKey = "multi-factor-authentication"
)
@Meta.OCD(
	id = "com.liferay.multi.factor.authentication.ip.address.internal.configuration.MFAIPAddressConfiguration",
	localization = "content/Language",
	name = "mfa-ip-address-configuration-name"
)
public interface MFAIPAddressConfiguration {

	@Meta.AD(
		deflt = "false", description = "mfa-ip-address-enabled-description",
		name = "enabled", required = false
	)
	public boolean enabled();

	/**
	 * Allowed IPs and its network masks, use add button to add new entry for
	 * different integration. Can be both IPv4 and IPv6.
	 *
	 * @return allowed IPs and their network masks.
	 */
	@Meta.AD(
		deflt = "127.0.0.1/255.0.0.0",
		description = "allowed-ip-address-and-netmask-description",
		name = "allowed-ip-address-and-netmask-name", required = false
	)
	public String[] allowedIPAddressAndNetMask();

}