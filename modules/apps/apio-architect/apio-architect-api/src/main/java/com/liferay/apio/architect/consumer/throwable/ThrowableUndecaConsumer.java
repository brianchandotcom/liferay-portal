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

package com.liferay.apio.architect.consumer.throwable;

import java.util.Objects;

/**
 * Defines a {@code UndecaConsumer} that can throw an exception.
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
public interface ThrowableUndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> {

	/**
	 * Returns an empty {@code ThrowableUndecaConsumer} that doesn't perform any
	 * operation.
	 *
	 * @return an empty {@code ThrowableUndecaConsumer} that doesn't perform any
	 *         operation
	 */
	public static <A, B, C, D, E, F, G, H, I, J, K>
		ThrowableUndecaConsumer<A, B, C, D, E, F, G, H, I, J, K> empty() {

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
	public void accept(A a, B b, C c, D d, E e, F f, G g, H h, I i, J j, K k)
		throws Exception;

	/**
	 * Returns the {@code ThrowableUndecaConsumer} function that first executes
	 * the current {@code ThrowableUndecaConsumer} instance's {@code accept}
	 * method, then executes the {@code after} parameter's {@code accept}
	 * method.
	 *
	 * @param  after the {@code ThrowableUndecaConsumer} instance to execute
	 *         after the current instance
	 * @return the {@code ThrowableUndecaConsumer} that executes the current
	 *         instance's {@code accept} method, as well as that of {@code
	 *         after}
	 */
	public default ThrowableUndecaConsumer<A, B, C, D, E, F, G, H, I, J, K>
		andThen(
			ThrowableUndecaConsumer<? super A, ? super B, ? super C, ? super D,
				? super E, ? super F, ? super G, ? super H, ? super I,
			? super J, ? super K> after) {

		Objects.requireNonNull(after);

		return (a, b, c, d, e, f, g, h, i, j, k) -> {
			accept(a, b, c, d, e, f, g, h, i, j, k);
			after.accept(a, b, c, d, e, f, g, h, i, j, k);
		};
	}

}