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

import java.util.Objects;

/**
 * A version of the Java {@link java.util.function.Function} that can throw an
 * {@code Exception}.
 *
 * @author Alejandro Hernández
 * @review
 */
@FunctionalInterface
@SuppressWarnings("unused")
public interface ThrowableFunction<T, R> {

	/**
	 * Returns a composed function that first applies this function to its
	 * input, and then applies the {@code after} function to the result.
	 *
	 * @param  throwableFunction the function to apply after this function is
	 *         applied
	 * @return a composed function that first applies this function and then
	 *         applies the {@code after} function
	 * @see    #compose(ThrowableFunction)
	 * @review
	 */
	public default <V> ThrowableFunction<T, V> andThen(
		ThrowableFunction<? super R, ? extends V> throwableFunction) {

		Objects.requireNonNull(throwableFunction);

		return (T t) -> throwableFunction.apply(apply(t));
	}

	/**
	 * Applies this function to the given argument.
	 *
	 * @param  t the function argument
	 * @return the function result
	 * @review
	 */
	public R apply(T t) throws Exception;

	/**
	 * Returns a composed function that first applies the {@code before}
	 * function to its input, and then applies this function to the result.
	 *
	 * @param  throwableFunction the function to apply before this function is
	 *         applied
	 * @return a composed function that first applies the {@code before}
	 *         function and then applies this function
	 * @see    #andThen(ThrowableFunction)
	 * @review
	 */
	public default <V> ThrowableFunction<V, R> compose(
		ThrowableFunction<? super V, ? extends T> throwableFunction) {

		Objects.requireNonNull(throwableFunction);

		return (V v) -> apply(throwableFunction.apply(v));
	}

}