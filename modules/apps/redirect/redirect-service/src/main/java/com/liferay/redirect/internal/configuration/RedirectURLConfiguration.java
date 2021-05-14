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