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
 * Defines a {@code java.util.function.Consumer} that takes four input
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
 */
@FunctionalInterface
public interface TetraConsumer<A, B, C, D> {

	/**
	 * Returns an empty {@code TetraConsumer} that doesn't perform any
	 * operation.
	 *
	 * @return an empty {@code TetraConsumer} that doesn't perform any operation
	 */
	public static <A, B, C, D> TetraConsumer<A, B, C, D> empty() {
		return (a, b, c, d) -> {
		};
	}

	/**
	 * Operates with four parameters and returns {@code void}. This function can
	 * be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 * @param c the third function argument
	 * @param d the fourth function argument
	 */
	public void accept(A a, B b, C c, D d);

	/**
	 * Returns the {@code TetraConsumer} function that first executes the
	 * current {@code TetraConsumer} instance's {@code accept} method, then
	 * executes the {@code after} parameter's {@code accept} method.
	 *
	 * @param  after the {@code TetraConsumer} instance to execute after the
	 *         current instance
	 * @return the {@code TetraConsumer} that executes the current instance's
	 *         {@code accept} method, as well as that of {@code after}
	 */
	public default TetraConsumer<A, B, C, D> andThen(
		TetraConsumer<? super A, ? super B, ? super C, ? super D> after) {

		Objects.requireNonNull(after);

		return (a, b, c, d) -> {
			accept(a, b, c, d);
			after.accept(a, b, c, d);
		};
	}

}