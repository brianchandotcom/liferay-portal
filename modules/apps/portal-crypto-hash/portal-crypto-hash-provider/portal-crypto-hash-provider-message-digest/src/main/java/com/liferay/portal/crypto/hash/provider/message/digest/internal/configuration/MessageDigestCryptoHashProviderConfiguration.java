/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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