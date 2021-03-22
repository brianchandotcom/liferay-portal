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