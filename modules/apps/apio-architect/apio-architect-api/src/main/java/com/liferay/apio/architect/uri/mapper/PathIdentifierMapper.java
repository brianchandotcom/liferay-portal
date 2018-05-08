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

package com.liferay.apio.architect.uri.mapper;

import aQute.bnd.annotation.ConsumerType;

import com.liferay.apio.architect.uri.Path;

/**
 * Converts a {@link Path} to its corresponding identifier, and vice versa.
 *
 * <p>
 * Instances of {@code PathIdentifierMapper} should only be created for
 * identifiers used as a single model's identifier. The identifier's class can
 * then be provided as a parameter in the methods of the different routes
 * builders.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <T> the identifier type to map
 */
@ConsumerType
public interface PathIdentifierMapper<T> {

	/**
	 * Converts a path to its corresponding identifier.
	 *
	 * @param  path the resource's path
	 * @return the corresponding identifier
	 */
	public T map(Path path);

	/**
	 * Converts an identifier to its corresponding path.
	 *
	 * @param  name the resource's name
	 * @param  t the identifier
	 * @return the corresponding path
	 */
	public Path map(String name, T t);

}