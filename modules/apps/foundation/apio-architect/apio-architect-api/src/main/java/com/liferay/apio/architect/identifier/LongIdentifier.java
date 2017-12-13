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

package com.liferay.apio.architect.identifier;

/**
 * Represents a simple identifier with a {@code long} ID.
 *
 * @author Alejandro Hernández
 */
@FunctionalInterface
public interface LongIdentifier extends Identifier {

	/**
	 * Returns the identifier's ID as a {@code long}.
	 *
	 * <p>
	 * For example, for a resource in the URL {@code /p/product/100}, this
	 * method returns {@code 100} as a {@code long}.
	 * </p>
	 *
	 * @return the identifier's {@code long} ID
	 */
	public long getId();

}