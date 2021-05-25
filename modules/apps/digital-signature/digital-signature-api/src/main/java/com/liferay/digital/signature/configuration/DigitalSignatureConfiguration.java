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

package com.liferay.digital.signature.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author José Abelenda
 */
@ExtendedObjectClassDefinition(
	category = "digital-signature", generateUI = false,
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	id = "com.liferay.digital.signature.configuration.DigitalSignatureConfiguration",
	localization = "content/Language",
	name = "digital-signature-configuration-name"
)
public interface DigitalSignatureConfiguration {

	public boolean enabled();

	public String apiUsername();

	public String apiAccountId();

	public String accountBaseURI();

	public String integrationKey();

	public String rsaPrivateKey();

	public String siteSettingsStrategy();

}