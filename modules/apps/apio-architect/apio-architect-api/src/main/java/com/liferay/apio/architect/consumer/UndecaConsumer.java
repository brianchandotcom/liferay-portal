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

package com.liferay.apio.architect.consumer;

import java.util.Objects;

/**
 * Defines a {@code java.util.function.Consumer} that takes eleven input
 * parameters. This consumer, like all consumers, doesn't return a result.
 *
 * <p>
 * This interface can be implemented with a lambda function.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <A> the type of the first argument of the consumer
 * @param  <B> the type of the second argument of the consumer
 * @param  <C> the type of the third argument of the consumer
 * @param  <D> the type of the fourth argument of the consumer
 * @param  <E> the type of the fifth argument of the consumer
 * @param  <F> the type of the sixth argument of the consumer
 * @param  <G> the type of the seventh argument of the consumer
 * @param  <H> the type of the eighth argument of the consumer
 * @param  <I> the type of the ninth argument of the consumer
 * @param  <J> the type of the tenth argument of the consumer
 * @param  <K> the type of the eleventh argument of the consumer
 */
@FunctionalInterface
public interface UndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> {

	/**
	 * Returns an empty {@code UndecaConsumer} that doesn't perform any
	 * operation.
	 *
	 * @return an empty {@code UndecaConsumer} that doesn't perform any
	 *         operation
	 */
	public static <A, B, C, D, E, F, G, H, I, J, K>
		UndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> empty() {

		return (a, b, c, d, e, f, g, h, i, j, k) -> {
		};
	}

	/**
	 * Operates with eleven parameters and returns {@code void}. This function
	 * can be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @param d the fourth function argument
	 * @param e the fifth function argument
	 * @param f the sixth function argument
	 * @param g the seventh function argument
	 * @param h the eighth function argument
	 * @param i the ninth function argument
	 * @param j the tenth function argument
	 * @param k the eleventh function argument
	 */
	public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k);

	/**
	 * Returns the {@code UndecaConsumer} function that first executes the
	 * current {@code UndecaConsumer} instance's {@code accept} method, then
	 * executes the {@code after} parameter's {@code accept} method.
	 *
	 * @param  after the {@code UndecaConsumer} instance to execute after the
	 *         current instance
	 * @return the {@code UndecaConsumer} that executes the current instance's
	 *         {@code accept} method, as well as that of {@code after}
	 */
	public default UndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> andThen(
		UndecaConsumer<? super A, ? super B, ? super C, ? super D, ? super E,
			? super F, ? super G, ? super H, ? super I, ? super J, ? super K>
				after) {

		Objects.requireNonNull(after);

		return (a, b, c, d, e, f, g, h, i, j, k) -> {
			accept(a, b, c, d, e, f, g, h, i, j, k);
			after.accept(a, b, c, d, e, f, g, h, i, j, k);
		};
	}

}