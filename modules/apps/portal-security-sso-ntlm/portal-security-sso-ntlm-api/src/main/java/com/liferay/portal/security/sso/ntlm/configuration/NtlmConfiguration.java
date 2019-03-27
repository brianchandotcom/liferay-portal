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

package com.liferay.portal.security.sso.ntlm.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * Defines the configuration property keys and sensible default values.
 *
 * <p>
 * This class also defines the identity of the configuration schema which, among
 * other things, defines the filename (minus the .cfg extension) for setting
 * values via a file.
 * </p>
 *
 * @author Michael C. Han
 */
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.ntlm.configuration.NtlmConfiguration",
	localization = "content/Language", name = "ntlm-configuration-name"
)
public interface NtlmConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-help", name = "enabled",
		required = false
	)
	public boolean enabled();

	@Meta.AD(deflt = "127.0.0.1", name = "domain-controller", required = false)
	public String domainController();

	@Meta.AD(
		deflt = "EXAMPLE", name = "domain-controller-name", required = false
	)
	public String domainControllerName();

	@Meta.AD(deflt = "EXAMPLE", name = "domain", required = false)
	public String domain();

	@Meta.AD(
		deflt = "LIFERAY$@EXAMPLE.COM", name = "service-account",
		required = false
	)
	public String serviceAccount();

	@Meta.AD(deflt = "test", name = "service-password", required = false)
	public String servicePassword();

	@Meta.AD(
		deflt = "0x600FFFFF", description = "negotiate-flags-help",
		name = "negotiate-flags", required = false
	)
	public String negotiateFlags();

}