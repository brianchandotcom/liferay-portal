/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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