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

package com.liferay.apio.architect.wiring.osgi.manager;

import aQute.bnd.annotation.ProviderType;

import com.liferay.apio.architect.identifier.Identifier;
import com.liferay.apio.architect.uri.Path;

import java.util.Optional;

/**
 * Provides methods to map a {@link Path} to an {@code Identifier}, and vice
 * versa.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface PathIdentifierMapperManager {

	/**
	 * Converts a {@code Path} to its equivalent {@code Identifier} of type
	 * {@code T}, if a valid {@link
	 * com.liferay.apio.architect.identifier.mapper.PathIdentifierMapper} can be
	 * found. Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the class of the desired {@code Identifier}
	 * @param  path the {@code Path}
	 * @return the {@code Identifier}, if a valid {@code PathIdentifierMapper}
	 *         is present; {@code Optional#empty()} otherwise
	 */
	public <T extends Identifier> Optional<T> map(Class<T> clazz, Path path);

	/**
	 * Converts an {@code Identifier} to its equivalent {@code Path}, if a valid
	 * {@code PathIdentifierMapper} can be found. Returns {@code
	 * Optional#empty()} otherwise.
	 *
	 * @param  identifier the {@code Identifier}
	 * @param  modelClass the class of the model identified by the {@code
	 *         Identifier}
	 * @return the {@code Path}, if a valid {@code PathIdentifierMapper} is
	 *         present; {@code Optional#empty()} otherwise
	 */
	public <T extends Identifier, U> Optional<Path> map(
		T identifier, Class<? extends Identifier> identifierClass,
		Class<U> modelClass);

}