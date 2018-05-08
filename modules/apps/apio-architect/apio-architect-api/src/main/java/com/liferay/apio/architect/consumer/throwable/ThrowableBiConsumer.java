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
 * Defines a {@code BiConsumer} that can throw an exception.
 *
 * <p>
 * This interface can be implemented with a lambda function.
 * </p>
 *
 * @author Alejandro Hernández
 * @param  <A> the type of the first argument of the consumer
 * @param  <B> the type of the second argument of the consumer
 */
@FunctionalInterface
public interface ThrowableBiConsumer<A, B> {

	/**
	 * Returns an empty {@code ThrowableBiConsumer} that doesn't perform any
	 * operation.
	 *
	 * @return an empty {@code ThrowableBiConsumer} that doesn't perform any
	 *         operation
	 */
	public static <A, B> ThrowableBiConsumer<A, B> empty() {
		return (a, b) -> {
		};
	}

	/**
	 * Operates with two parameters and returns {@code void}. This function can
	 * be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 * @param b the second function argument
	 */
	public void accept(A a, B b) throws Exception;

	/**
	 * Returns the {@code ThrowableBiConsumer} function that first executes the
	 * current {@code ThrowableBiConsumer} instance's {@code accept} method,
	 * then executes the {@code after} parameter's {@code accept} method.
	 *
	 * @param  after the {@code ThrowableBiConsumer} instance to execute after
	 *         the current instance
	 * @return the {@code ThrowableBiConsumer} that executes the current
	 *         instance's {@code accept} method, as well as that of {@code
	 *         after}
	 */
	public default ThrowableBiConsumer<A, B> andThen(
		ThrowableBiConsumer<? super A, ? super B> after) {

		Objects.requireNonNull(after);

		return (a, b) -> {
			accept(a, b);
			after.accept(a, b);
		};
	}

}