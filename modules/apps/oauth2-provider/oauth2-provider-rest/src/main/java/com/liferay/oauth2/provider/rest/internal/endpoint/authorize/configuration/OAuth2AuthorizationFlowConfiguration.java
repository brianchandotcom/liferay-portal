/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "oauth2", scope = ExtendedObjectClassDefinition.Scope.SYSTEM
)
@Meta.OCD(
	id = "com.liferay.oauth2.provider.rest.internal.endpoint.authorize.configuration.OAuth2AuthorizationFlowConfiguration",
	localization = "content/Language",
	name = "authorization-flow-configuration-name"
)
public interface OAuth2AuthorizationFlowConfiguration {

	@Meta.AD(
		deflt = "300", description = "authorization-code-grant-ttl-description",
		id = "authorization.code.grant.ttl",
		name = "authorization-code-grant-ttl", required = false
	)
	public int authorizationCodeGrantTTL();

}