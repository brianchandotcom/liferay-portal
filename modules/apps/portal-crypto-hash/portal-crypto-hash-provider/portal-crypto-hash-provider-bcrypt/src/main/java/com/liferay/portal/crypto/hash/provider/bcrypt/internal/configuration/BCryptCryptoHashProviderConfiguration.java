/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.provider.bcrypt.internal.configuration;

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
	id = "com.liferay.portal.crypto.hash.provider.bcrypt.internal.configuration.BCryptCryptoHashProviderConfiguration",
	localization = "content/Language",
	name = "bcrypt-crypto-hash-provider-configuration-name"
)
public interface BCryptCryptoHashProviderConfiguration
	extends CryptoHashProviderConfiguration {

	@Meta.AD(
		deflt = "10", description = "bcrypt-rounds", id = "bcrypt.rounds",
		name = "bcrypt-rounds", required = false
	)
	public int rounds();

}