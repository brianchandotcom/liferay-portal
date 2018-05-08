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

package com.liferay.apio.architect.function.throwable;

import java.util.Objects;

/**
 * Defines a {@code BiFunction} that can throw an exception.
 *
 * @author Alejandro Hernández
 * @param  <A> the type of the first argument of the function
 * @param  <B> the type of the second argument of the function
 * @param  <R> the type of the result of the function
 */
@FunctionalInterface
public interface ThrowableBiFunction<A, B, R> {

	/**
	 * Returns the {@code ThrowableBiFunction} that first executes the current
	 * {@code ThrowableBiFunction} instance's {@code apply} method, then uses
	 * the result as input for the {@code afterFunction} parameter's {@code
	 * apply} method.
	 *
	 * @param  throwableFunction the {@code ThrowableBiFunction} to execute
	 *         after the current instance
	 * @return the {@code ThrowableBiFunction} that executes the current
	 *         instance's {@code apply} method, then uses the result as input
	 *         for the {@code throwableFunction} parameter's {@code apply}
	 *         method
	 */
	public default <V> ThrowableBiFunction<A, B, V> andThen(
		ThrowableFunction<? super R, ? extends V> throwableFunction) {

		Objects.requireNonNull(throwableFunction);

		return (A a, B b) -> throwableFunction.apply(apply(a, b));
	}

	/**
	 * Applies the current {@code ThrowableBiFunction} and returns a value of
	 * type {@code R}. This function can be implemented explicitly or with a
	 * lambda.
	 *
	 * @param  a the function's first argument
	 * @param  b the function's second argument
	 * @return the function's result, as a value of type {@code R}
	 */
	public R apply(A a, B b) throws Exception;

}