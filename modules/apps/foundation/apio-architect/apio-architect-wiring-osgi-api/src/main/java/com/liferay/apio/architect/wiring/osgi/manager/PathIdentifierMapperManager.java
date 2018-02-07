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
 * Provides methods to map a {@link Path} to an identifier, and vice versa.
 *
 * @author Alejandro Hernández
 */
@ProviderType
public interface PathIdentifierMapperManager {

	/**
	 * Converts a {@code Path} to its equivalent identifier of type {@code T},
	 * if a valid {@link
	 * com.liferay.apio.architect.uri.mapper.PathIdentifierMapper} can be found.
	 * Returns {@code Optional#empty()} otherwise.
	 *
	 * @param  clazz the class of the desired identifier
	 * @param  path the {@code Path}
	 * @return the identifier, if a valid {@code PathIdentifierMapper} is
	 *         present; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<T> mapToIdentifier(
		Class<? extends Identifier<T>> clazz, Path path);

	/**
	 * Converts an identifier to its equivalent {@code Path}, if a valid {@code
	 * PathIdentifierMapper} can be found. Returns {@code Optional#empty()}
	 * otherwise.
	 *
	 * @param  clazz the class of the identifier
	 * @param  identifier the identifier
	 * @return the {@code Path}, if a valid {@code PathIdentifierMapper} is
	 *         present; {@code Optional#empty()} otherwise
	 */
	public <T> Optional<Path> mapToPath(
		Class<? extends Identifier<T>> clazz, T identifier);

}