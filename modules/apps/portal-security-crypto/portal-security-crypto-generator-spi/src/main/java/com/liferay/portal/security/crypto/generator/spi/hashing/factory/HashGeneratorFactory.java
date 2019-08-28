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

package com.liferay.portal.security.crypto.generator.spi.hashing.factory;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.security.crypto.generator.spi.hashing.HashGenerator;

import org.json.JSONObject;

/**
 * @author Arthur Chan
 */
@ProviderType
public interface HashGeneratorFactory {

	/**
	 * Construct a {@link com.liferay.portal.security.crypto.generator.spi.hashing.HasherGenerator} from given algorithm.
	 *
	 * @param generatorName The name of algorithm this generator is associated with
	 * @param generatorMeta A JSON Object of meta info required by some algorithms
	 * @return An instance of HashGenerator
	 */
	public HashGenerator create(String generatorName, JSONObject generatorMeta)
		throws Exception;

	/**
	 * Construct a {@link com.liferay.portal.security.crypto.generator.spi.hashing.HasherGenerator} from given algorithm.
	 *
	 * @param generatorName The name of algorithm this generator is associated with
	 * @param generatorMetaJSON A JSON Object formatted String of meta info required by some algorithms
	 * @return An instance of HashGenerator
	 */
	public default HashGenerator create(
			String generatorName, String generatorMetaJSON)
		throws Exception {

		JSONObject generatorMetaJSONObject = new JSONObject(generatorMetaJSON);

		return create(generatorName, generatorMetaJSONObject);
	}

}