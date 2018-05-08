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

package com.liferay.apio.architect.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Defines a {@code Function} that takes ten input parameters. This interface,
 * like all function interfaces, receives several arguments and returns one
 * value of type {@code R}.
 *
 * <p>
 * This interface can be implemented with a lambda function.
 * </p>
 *
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 * @param  <A> the type of the first argument of the function
 * @param  <B> the type of the second argument of the function
 * @param  <C> the type of the third argument of the function
 * @param  <D> the type of the fourth argument of the function
 * @param  <E> the type of the fifth argument of the function
 * @param  <F> the type of the sixth argument of the function
 * @param  <G> the type of the seventh argument of the function
 * @param  <H> the type of the eighth argument of the function
 * @param  <I> the type of the ninth argument of the function
 * @param  <J> the type of the tenth argument of the function
 * @param  <R> the type of the result of the function
 */
@FunctionalInterface
public interface DecaFunction<A, B, C, D, E, F, G, H, I, J, R> {

	/**
	 * Returns the {@code DecaFunction} that first executes the current {@code
	 * DecaFunction} instance's {@code apply} method, then uses the result as
	 * input for the {@code afterFunction} parameter's {@code apply} method.
	 *
	 * @param  afterFunction the {@code DecaFunction} to execute after the
	 *         current instance
	 * @return the {@code DecaFunction} that executes the current instance's
	 *         {@code apply} method, then uses the result as input for the
	 *         {@code afterFunction} parameter's {@code apply} method
	 */
	public default <V> DecaFunction<A, B, C, D, E, F, G, H, I, J, V> andThen(
		Function<? super R, ? extends V> afterFunction) {

		Objects.requireNonNull(afterFunction);

		return (A a, B b, C c, D d, E e, F f, G g, H h, I i, J j) ->
			afterFunction.apply(apply(a, b, c, d, e, f, g, h, i, j));
	}

	/**
	 * Applies the current {@code DecaFunction} and returns a value of type
	 * {@code R}. This function can be implemented explicitly or with a lambda.
	 *
	 * @param  a the function's first argument
	 * @param  b the function's second argument
	 * @param  c the function's third argument
	 * @param  d the function's fourth argument
	 * @param  e the function's fifth argument
	 * @param  f the function's sixth argument
	 * @param  g the function's seventh argument
	 * @param  h the function's eighth argument
	 * @param  i the function's ninth argument
	 * @param  j the function's tenth argument
	 * @return the function's result, as a value of type {@code R}
	 */
	public R apply(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j);

}