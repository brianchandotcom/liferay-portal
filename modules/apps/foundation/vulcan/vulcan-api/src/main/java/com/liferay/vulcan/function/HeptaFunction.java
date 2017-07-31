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

package com.liferay.vulcan.function;

import java.util.Objects;
import java.util.function.Function;

/**
 * Represents a function that accepts seven arguments and produces a result.
 * This is the seven-arity specialization of {@link Function}.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a> whose
 * functional method is {@link
 * #apply(Object, Object, Object, Object, Object, Object, Object)}.
 *
 * @param <A> the type of the first argument to the function.
 * @param <B> the type of the second argument to the function.
 * @param <C> the type of the third argument to the function.
 * @param <D> the type of the fourth argument to the function.
 * @param <E> the type of the fifth argument to the function.
 * @param <F> the type of the sixth argument to the function.
 * @param <G> the type of the seventh argument to the function.
 * @param <R> the type of the result of the function.
 *
 * @see Function
 * @author Alejandro Hernández
 * @author Jorge Ferrer
 */
@FunctionalInterface
public interface HeptaFunction<A, B, C, D, E, F, G, R> {

	/**
	 * Returns a composed function that first applies this function to
	 * its input, and then applies the {@code afterFunction} function to the
	 * result.
	 * If evaluation of either function throws an exception, it is relayed to
	 * the caller of the composed function.
	 *
	 * @param <V> the type of output of the {@code afterFunction} function,
	 *            and of the composed function
	 * @param afterFunction the function to apply after this function is applied
	 *
	 * @return a composed function that first applies this function and then
	 * applies the {@code after} function
	 *
	 * @throws NullPointerException if after is null
	 */
	public default <V> HeptaFunction<A, B, C, D, E, F, G, V> andThen(
		Function<? super R, ? extends V> afterFunction) {

		Objects.requireNonNull(afterFunction);

		return (A a, B b, C c, D d, E e, F f, G g) -> afterFunction.apply(
			apply(a, b, c, d, e, f, g));
	}

	/**
	 * Applies this function to the given arguments.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @param d the fourth function argument
	 * @param e the fifth function argument
	 * @param f the sixth function argument
	 * @param g the seventh function argument
	 * @return the function result
	 */
	public R apply(A a, B b, C c, D d, E e, F f, G g);

}