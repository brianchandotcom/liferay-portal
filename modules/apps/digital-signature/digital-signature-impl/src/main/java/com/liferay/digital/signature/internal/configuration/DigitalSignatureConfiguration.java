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

package com.liferay.digital.signature.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "digital-signature",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.digital.signature.internal.configuration.DigitalSignatureConfiguration",
	localization = "content/Language", name = "digital-signature-configuration"
)
public interface DigitalSignatureConfiguration {

	@Meta.AD(name = "digital-signature-enable")
	public boolean enable();

	@Meta.AD(name = "digital-signature-api-account-id")
	public String digitalSignatureAPIAccountId();

	@Meta.AD(name = "digital-signature-api-username")
	public String digitalSignatureAPIUsername();

	@Meta.AD(name = "digital-signature-integration-key")
	public String digitalSignatureIntegrationKey();

	@Meta.AD(name = "digital-signature-base-uri")
	public String digitalSignatureAccountBaseURI();

	@Meta.AD(name = "digital-signature-private-key")
	public String digitalSignaturePrivateKey();

}