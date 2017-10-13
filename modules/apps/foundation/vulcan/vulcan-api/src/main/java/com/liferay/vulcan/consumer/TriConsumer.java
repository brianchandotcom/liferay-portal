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

package com.liferay.vulcan.consumer;

import java.util.Objects;

/**
 * Consumer {@link java.util.function.Consumer} with three input parameters. As
 * all the consumer interfaces, it receives several arguments and doesn't return
 * anything.
 *
 * Being a functional interface, it can be implemented with a lambda function
 *
 * @author Alejandro Hernández
 * @see    java.util.function.Consumer
 * @review
 */
@FunctionalInterface
public interface TriConsumer<A, B, C> {

	/**
	 * The function to implement (explicitly or with a lambda), that operates
	 * with three parameters and returns void
	 *
	 * @param  a the first input argument.
	 * @param  b the second input argument.
	 * @param  c the third input argument.
	 * @review
	 */
	public void accept(A a, B b, C c);

	/**
	 * Method that creates a lambda function (also a {@code TriConsumer}) that
	 * executes the {@code accept} method of this instance and then the {@code
	 * accept} method of the {@code after} input parameter when invoked.
	 *
	 * @param  after the {@code TriConsumer} to execute after this instance
	 * @return another {@code TriConsumer} that executes both inputs (this own
	 *         instance plus the input parameter) in order
	 * @review
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