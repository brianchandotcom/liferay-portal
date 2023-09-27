/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.commerce.punchout.oauth2.provider.rest.internal.bearer.token.provider.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Jaclyn Ong
 */
@ExtendedObjectClassDefinition(category = "oauth2")
@Meta.OCD(
	id = "com.liferay.commerce.punchout.oauth2.provider.rest.internal.bearer.token.provider.configuration.PunchOutAccessTokenProviderConfiguration",
	localization = "content/Language",
	name = "punch-out-access-token-provider-configuration-name"
)
public interface PunchOutAccessTokenProviderConfiguration {

	@Meta.AD(
		deflt = "15", description = "access-token-duration-description",
		id = "access.token.expires.in", name = "access-token-duration",
		required = false
	)
	public int accessTokenExpiresIn();

	@Meta.AD(
		deflt = "8", description = "access-token-size-description",
		id = "access.token.key.byte.size", name = "access-token-size",
		required = false
	)
	public int accessTokenKeyByteSize();

}