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
import com.liferay.portal.crypto.hash.spi.configuration.CryptoHashProviderConfiguration;

/**
 * @author Carlos Sierra Andrés
 */
@ExtendedObjectClassDefinition(
	category = "security-tools",
	factoryInstanceLabelAttribute = "crypto.hash.provider.configuration.name",
	scope = ExtendedObjectClassDefinition.Scope.COMPANY
)
@Meta.OCD(
	factory = true,
	id = "com.liferay.portal.crypto.hash.provider.message.digest.internal.configuration.MessageDigestCryptoHashProviderConfiguration",
	localization = "content/Language",
	name = "message-digest-crypto-hash-provider-configuration-name"
)
public interface MessageDigestCryptoHashProviderConfiguration
	extends CryptoHashProviderConfiguration {

	@Meta.AD(
		deflt = "SHA-256", description = "message-digest-algorithm-description",
		id = "message.digest.algorithm", name = "message-digest-algorithm",
		required = false
	)
	public String algorithm();

	@Meta.AD(
		deflt = "32", description = "message-digest-salt-size-description",
		id = "message.digest.salt.size", name = "message-digest-salt-size",
		required = false
	)
	public int saltSize();

}