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
 * Defines a {@code java.util.function.Consumer} that takes three input
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
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {

	/**
	 * Returns an empty {@code TriConsumer} that doesn't perform any operation.
	 *
	 * @return an empty {@code TriConsumer} that doesn't perform any operation
	 */
	public static <A, B, C> TriConsumer<A, B, C> empty() {
		return (a, b, c) -> {
		};
	}

	/**
	 * Operates with three parameters and returns {@code void}. This function
	 * can be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 */
	public void accept(A a, B b, C c);

	/**
	 * Returns the {@code TriConsumer} function that first executes the current
	 * {@code TriConsumer} instance's {@code accept} method, then executes the
	 * {@code after} parameter's {@code accept} method.
	 *
	 * @param  after the {@code TriConsumer} instance to execute after the
	 *         current instance
	 * @return the {@code TriConsumer} that executes the current instance's
	 *         {@code accept} method, as well as that of {@code after}
	 */
	public default TriConsumer<A, B, C> andThen(
		TriConsumer<? super A, ? super B, ? super C> after) {

		Objects.requireNonNull(after);

		return (a, b, c) -> {
			accept(a, b, c);
			after.accept(a, b, c);
		};
	}

}