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

package com.liferay.apio.architect.unsafe;

/**
 * Provides a utility class for unsafe operations.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class Unsafe {

	/**
	 * Performs an unsafe cast from {@code T} to {@code S}.
	 *
	 * @param  t the object to cast
	 * @return the casted object
	 */
	@SuppressWarnings("unchecked")
	public static <T, S> S unsafeCast(T t) {
		return (S)t;
	}

	private Unsafe() {
		throw new UnsupportedOperationException();
	}

}