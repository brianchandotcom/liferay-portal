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

package com.liferay.portal.security.sso.ntlm.module.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.configuration.admin.ConfigurationAdmin;

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
@ConfigurationAdmin(category = "platform")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.ntlm.module.configuration.NtlmConfiguration",
	localization = "content/Language"
)
public interface NtlmConfiguration {

	@Meta.AD(deflt = "EXAMPLE", required = false)
	public String domain();

	@Meta.AD(deflt = "127.0.0.1", required = false)
	public String domainController();

	@Meta.AD(deflt = "EXAMPLE", required = false)
	public String domainControllerName();

	@Meta.AD(
		deflt = "false",
		description = "Set this to true to enable NTLM single sign on. NTLM will work only if LDAP authentication is also enabled and the authentication is made by screen name.",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "0x600FFFFF",
		description = "Negotiate flags are set according to the client's requested capabilities and the server's ServerCapabilities. See the following link: http://msdn.microsoft.com/en-us/library/cc717152%28v=PROT.10%29.aspx",
		required = false
	)
	public String negotiateFlags();

	@Meta.AD(deflt = "LIFERAY$@EXAMPLE.COM", required = false)
	public String serviceAccount();

	@Meta.AD(deflt = "test", required = false)
	public String servicePassword();

}