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

package com.liferay.portal.search.elasticsearch7.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Bryan Engler
 */
@ExtendedObjectClassDefinition(category = "search")
@Meta.OCD(
	id = "com.liferay.portal.search.elasticsearch7.configuration.XPackSecurityConfiguration",
	localization = "content/Language",
	name = "xpack-security-configuration-name"
)
public interface XPackSecurityConfiguration {

	@Meta.AD(
		deflt = "false", description = "requires-authentication-help",
		name = "requires-authentication", required = false
	)
	public boolean requiresAuthentication();

	@Meta.AD(
		deflt = "elastic", description = "username-help", name = "username",
		required = false
	)
	public String username();

	@Meta.AD(
		description = "password-help", name = "password", required = false,
		type = Meta.Type.Password
	)
	public String password();

	@Meta.AD(
		deflt = "false", description = "transport-ssl-enabled-help",
		name = "transport-ssl-enabled", required = false
	)
	public boolean transportSSLEnabled();

	@Meta.AD(
		deflt = "certificate",
		description = "transport-ssl-verification-mode-help",
		name = "transport-ssl-verification-mode",
		optionValues = {"certificate", "full", "none"}, required = false
	)
	public String transportSSLVerificationMode();

	@Meta.AD(
		deflt = "PKCS#12", description = "certificate-format-help",
		name = "certificate-format", optionValues = {"PEM", "PKCS#12"},
		required = false
	)
	public String certificateFormat();

	@Meta.AD(
		deflt = "/path/to/elastic-certificates.p12",
		description = "ssl-keystore-path-help", name = "ssl-keystore-path",
		required = false
	)
	public String sslKeystorePath();

	@Meta.AD(
		description = "ssl-keystore-password-help",
		name = "ssl-keystore-password", required = false,
		type = Meta.Type.Password
	)
	public String sslKeystorePassword();

	@Meta.AD(
		deflt = "/path/to/elastic-certificates.p12",
		description = "ssl-truststore-path-help", name = "ssl-truststore-path",
		required = false
	)
	public String sslTruststorePath();

	@Meta.AD(
		description = "ssl-truststore-password-help",
		name = "ssl-truststore-password", required = false,
		type = Meta.Type.Password
	)
	public String sslTruststorePassword();

	@Meta.AD(
		deflt = "/path/to/instance.key", description = "ssl-key-path-help",
		name = "ssl-key-path", required = false
	)
	public String sslKeyPath();

	@Meta.AD(
		deflt = "/path/to/instance.crt",
		description = "ssl-certificate-path-help",
		name = "ssl-certificate-path", required = false
	)
	public String sslCertificatePath();

	@Meta.AD(
		deflt = "/path/to/ca.crt",
		description = "ssl-certificate-authorities-paths-help",
		name = "ssl-certificate-authorities-paths", required = false
	)
	public String[] sslCertificateAuthoritiesPaths();

}