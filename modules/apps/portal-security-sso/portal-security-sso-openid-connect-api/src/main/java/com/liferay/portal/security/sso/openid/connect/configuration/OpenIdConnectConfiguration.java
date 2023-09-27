/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.sso.openid.connect.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

import org.osgi.annotation.versioning.ProviderType;

/**
 * Defines the configuration property keys and default values.
 *
 * <p>
 * This class also defines the identity of the configuration schema which, among
 * other things, defines the filename (minus the <code>.cfg</code> extension)
 * for setting values via a file.
 * </p>
 *
 * @author Thuong Dinh
 */
@ExtendedObjectClassDefinition(category = "sso")
@Meta.OCD(
	id = "com.liferay.portal.security.sso.openid.connect.configuration.OpenIdConnectConfiguration",
	localization = "content/Language",
	name = "open-id-connect-configuration-name"
)
@ProviderType
public interface OpenIdConnectConfiguration {

	@Meta.AD(
		deflt = "false", description = "enabled-help[openid]", name = "enabled",
		required = false
	)
	public boolean enabled();

	@Meta.AD(
		deflt = "30", description = "token-refresh-offset-description",
		name = "token-refresh-offset", required = false
	)
	public int tokenRefreshOffset();

	@Meta.AD(
		deflt = "480",
		description = "token-refresh-scheduled-interval-description",
		name = "token-refresh-scheduled-interval", required = false
	)
	public int tokenRefreshScheduledInterval();

}