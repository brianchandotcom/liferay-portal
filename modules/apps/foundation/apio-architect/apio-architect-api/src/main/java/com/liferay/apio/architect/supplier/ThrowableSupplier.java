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

package com.liferay.apio.architect.supplier;

/**
 * Defines a {@code java.util.function.Supplier} that can throw an exception.
 *
 * @author Alejandro Hernández
 * @param  <R> the type of the result of the supplier
 */
@FunctionalInterface
public interface ThrowableSupplier<R> {

	/**
	 * Returns a result of type {@code R} if the operation succeeds; otherwise
	 * throws an exception.
	 *
	 * @return a result of type {@code R} if the operation succeeds; an
	 *         exception otherwise
	 */
	public R get() throws Exception;

}