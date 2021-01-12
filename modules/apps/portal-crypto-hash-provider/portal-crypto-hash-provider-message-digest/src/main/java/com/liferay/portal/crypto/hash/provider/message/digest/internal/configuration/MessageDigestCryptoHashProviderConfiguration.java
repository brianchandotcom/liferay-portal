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

package com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration;

import aQute.bnd.annotation.metatype.Meta;

import com.liferay.portal.configuration.metatype.annotations.ExtendedObjectClassDefinition;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "security-tools",
	factoryInstanceLabelAttribute = "configuration.name"
)
@Meta.OCD(
	description = "crypto-hash-processor-configuration-description",
	factory = true,
	id = "com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration",
	localization = "content/Language", name = "crypto-hash-processor"
)
public interface MessageDigestCryptoHashProviderConfiguration {

	@Meta.AD(id = "configuration.name", name = "configuration-name")
	public String name();

	@Meta.AD(
		id = "crypto.hash.provider.name", name = "crypto-hash-provider-name"
	)
	public String cryptoHashProvider();

	@Meta.AD(id = "salt.size", name = "salt-size")
	public int saltSize();

}