/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Tomas Polesovsky
 */
@ExtendedObjectClassDefinition(category = "oauth2")
@Meta.OCD(
	id = "com.liferay.oauth2.provider.rest.internal.spi.bearer.token.provider.configuration.DefaultBearerTokenProviderConfiguration",
	localization = "content/Language",
	name = "default-bearer-token-provider-configuration-name"
)
public interface DefaultBearerTokenProviderConfiguration {

	@Meta.AD(
		deflt = "600", description = "access-token-expires-in-description",
		id = "access.token.expires.in", name = "access-token-expires-in",
		required = false
	)
	public int accessTokenExpiresIn();

	@Meta.AD(
		deflt = "32", description = "access-token-key-byte-size-description",
		id = "access.token.key.byte.size", name = "access-token-key-byte-size",
		required = false
	)
	public int accessTokenKeyByteSize();

	@Meta.AD(
		deflt = "604800", description = "refresh-token-expires-in-description",
		id = "refresh.token.expires.in", name = "refresh-token-expires-in",
		required = false
	)
	public int refreshTokenExpiresIn();

	@Meta.AD(
		deflt = "32", description = "refresh-token-key-byte-size-description",
		id = "refresh.token.key.byte.size",
		name = "refresh-token-key-byte-size", required = false
	)
	public int refreshTokenKeyByteSize();

}