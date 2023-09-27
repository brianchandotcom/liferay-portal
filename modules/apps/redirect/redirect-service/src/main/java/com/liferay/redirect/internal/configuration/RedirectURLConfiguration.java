/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.redirect.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Drew Brokke
 */
@ExtendedObjectClassDefinition(
	category = "pages", scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	description = "redirect-url-configuration-description",
	id = "com.liferay.redirect.internal.configuration.RedirectURLConfiguration",
	localization = "content/Language", name = "redirect-url-configuration-name"
)
public interface RedirectURLConfiguration {

	@Meta.AD(
		deflt = "", description = "allowed-domains-help",
		name = "allowed-domains", required = false
	)
	public String[] allowedDomains();

	@Meta.AD(
		deflt = "127.0.0.1|SERVER_IP", description = "allowed-ips-help",
		name = "allowed-ips", required = false
	)
	public String[] allowedIPs();

	@Meta.AD(
		deflt = "ip", description = "security-mode-help",
		name = "security-mode", optionLabels = {"Domain", "IP"},
		optionValues = {"domain", "ip"}, required = false
	)
	public String securityMode();

}