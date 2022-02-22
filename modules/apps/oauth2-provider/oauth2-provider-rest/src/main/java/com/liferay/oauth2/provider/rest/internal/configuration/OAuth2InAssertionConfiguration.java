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

package com.liferay.oauth2.provider.rest.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Arthur Chan
 */
@ExtendedObjectClassDefinition(
	category = "oauth2",
	factoryInstanceLabelAttribute = "oauth2.in.assertion.issuer",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.oauth2.provider.rest.internal.configuration.OAuth2InAssertionConfiguration",
	localization = "content/Language",
	name = "oauth2-in-assertion-configuration-name"
)
public interface OAuth2InAssertionConfiguration {

	@Meta.AD(
		description = "oauth2-in-assertion-issuer-help",
		id = "oauth2.in.assertion.issuer", name = "oauth2-in-assertion-issuer"
	)
	public String issuer();

	@Meta.AD(
		description = "oauth2-in-assertion-signature-json-web-key-set-help",
		id = "oauth2.in.assertion.signature.json.web.key.set",
		name = "oauth2-in-assertion-signature-json-web-key-set"
	)
	public String signatureJSONWebKeySet();

	@Meta.AD(
		deflt = "UUID", description = "oauth2-in-assertion-user-auth-type-help",
		id = "oauth2.in.assertion.user.auth.type",
		name = "oauth2-in-assertion-user-auth-type",
		optionValues = {"emailAddress", "screenName", "userId", "UUID"}
	)
	public String userAuthType();

}