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
 * Defines a {@code HexaConsumer} that can throw an exception.
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
 */
@FunctionalInterface
public interface ThrowableHexaConsumer<A, B, C, D, E, F> {

	/**
	 * Returns an empty {@code ThrowableHexaConsumer} that doesn't perform any
	 * operation.
	 *
	 * @return an empty {@code ThrowableHexaConsumer} that doesn't perform any
	 *         operation
	 */
	public static <A, B, C, D, E, F>
		ThrowableHexaConsumer<A, B, C, D, E, F> empty() {

		return (a, b, c, d, e, f) -> {
		};
	}

	/**
	 * Operates with six parameters and returns {@code void}. This function can
	 * be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @param d the fourth function argument
	 * @param e the fifth function argument
	 * @param f the sixth function argument
	 */
	public void accept(A a, B b, C c, D d, E e, F f) throws Exception;

	/**
	 * Returns the {@code ThrowableHexaConsumer} function that first executes
	 * the current {@code ThrowableHexaConsumer} instance's {@code accept}
	 * method, then executes the {@code after} parameter's {@code accept}
	 * method.
	 *
	 * @param  after the {@code ThrowableHexaConsumer} instance to execute after
	 *         the current instance
	 * @return the {@code ThrowableHexaConsumer} that executes the current
	 *         instance's {@code accept} method, as well as that of {@code
	 *         after}
	 */
	public default ThrowableHexaConsumer<A, B, C, D, E, F> andThen(
		ThrowableHexaConsumer
			<? super A, ? super B, ? super C, ? super D, ? super E,
				? super F> after) {

		Objects.requireNonNull(after);

		return (a, b, c, d, e, f) -> {
			accept(a, b, c, d, e, f);
			after.accept(a, b, c, d, e, f);
		};
	}

}