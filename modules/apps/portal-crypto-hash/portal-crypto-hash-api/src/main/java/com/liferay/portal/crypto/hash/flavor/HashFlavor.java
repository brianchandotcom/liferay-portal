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

package com.liferay.portal.crypto.hash.flavor;

import java.io.IOException;

import java.util.Optional;

/**
 * @author Arthur Chan
 * @author Carlos Sierra Andrés
 */
public interface HashFlavor {

	public Optional<String> getPepperId();

	public Optional<byte[]> getSalt();

	/**
	 * Serialize the object of HashFlavor into bytes,
	 * {@link HashGenerationContext}.Builder.build(byte[])
	 * will de-serialize bytes back to the object,
	 * and use the object to build an instance of
	 * {@link HashGenerationContext}
	 */
	public byte[] toBytes() throws IOException;

}