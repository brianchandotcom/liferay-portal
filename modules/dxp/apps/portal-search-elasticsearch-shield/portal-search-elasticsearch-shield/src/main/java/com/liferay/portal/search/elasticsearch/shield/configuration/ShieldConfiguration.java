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

package com.liferay.portal.search.elasticsearch.shield.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author André de Oliveira
 */
@ExtendedObjectClassDefinition(category = "foundation")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch.shield.configuration.ShieldConfiguration",
	localization = "content/Language", name = "shield-configuration-name"
)
public interface ShieldConfiguration {

	@Meta.AD(
		deflt = "liferay", description = "password-help", name = "password",
		required = false, type = Meta.Type.Password
	)
	public String password();

	@Meta.AD(
		deflt = "false", description = "requires-authentication-help",
		name = "requires-authentication", required = false
	)
	public boolean requiresAuthentication();

	@Meta.AD(
		deflt = "true", description = "requires-ssl-help",
		name = "requires-ssl", required = false
	)
	public boolean requiresSSL();

	@Meta.AD(
		description = "ssl-keystore-key-password-help",
		name = "ssl-keystore-key-password", required = false,
		type = Meta.Type.Password
	)
	public String sslKeystoreKeyPassword();

	@Meta.AD(
		deflt = "liferay", description = "ssl-keystore-password-help",
		name = "ssl-keystore-password", required = false,
		type = Meta.Type.Password
	)
	public String sslKeystorePassword();

	@Meta.AD(
		deflt = "/path/to/keystore.jks", description = "ssl-keystore-path-help",
		name = "ssl-keystore-path", required = false
	)
	public String sslKeystorePath();

	@Meta.AD(
		deflt = "liferay", description = "username-help", name = "username",
		required = false
	)
	public String username();

}