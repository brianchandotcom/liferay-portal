/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.crypto.hash.spi.configuration;

import aQute.bnd.annotation.metatype.Meta;

/**
 * @author Carlos Sierra Andrés
 */
public interface CryptoHashProviderConfiguration {

	@Meta.AD(
		description = "crypto-hash-provider-configuration-name-description",
		id = "crypto.hash.provider.configuration.name",
		name = "crypto-hash-provider-configuration-name"
	)
	public String configurationName();

}