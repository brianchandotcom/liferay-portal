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

package com.liferay.vulcan.result;

/**
 * A version of the Java {@link java.util.function.Supplier} that can throw an
 * {@code Exception}.
 *
 * @author Alejandro Hernández
 * @review
 */
@FunctionalInterface
public interface ThrowableSupplier<T> {

	/**
	 * Gets a result or throws an {@code Exception}.
	 *
	 * @return a result if no {@code Exception} is thrown.
	 * @review
	 */
	public T get() throws Exception;

}