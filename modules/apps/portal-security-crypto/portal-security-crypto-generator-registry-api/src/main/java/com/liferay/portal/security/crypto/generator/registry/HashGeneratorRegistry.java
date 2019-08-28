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

package com.liferay.portal.security.crypto.generator.registry;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.security.crypto.generator.spi.hashing.HashGenerator;

import java.util.Set;

import org.json.JSONObject;

/**
 * @author Arthur Chan
 */
@ProviderType
public interface HashGeneratorRegistry {

	public HashGenerator getHashGenerator(
			String generatorName, JSONObject generatorMeta)
		throws Exception;

	public HashGenerator getHashGenerator(
			String generatorName, String generatorMetaJSON)
		throws Exception;

	public Set<String> getSupportedHashGeneratorNames();

}