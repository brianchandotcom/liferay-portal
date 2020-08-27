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