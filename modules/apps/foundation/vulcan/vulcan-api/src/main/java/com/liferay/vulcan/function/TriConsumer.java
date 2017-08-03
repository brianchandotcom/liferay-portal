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

/**
 * Represents an operation that accepts three input arguments and returns no
 * result. This is the three-arity specialization of {@link
 * java.util.function.Consumer}. Unlike most other functional interfaces, {@code
 * TriConsumer} is expected to operate via side-effects.
 *
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object, Object)}.
 *
 * @author Alejandro Hernández
 * @see    java.util.function.Consumer
 */
@FunctionalInterface
public interface TriConsumer<T, U, V> {

	/**
	 * Performs this operation on the given arguments.
	 *
	 * @param t the first input argument.
	 * @param u the second input argument.
	 * @param v the third input argument.
	 */
	public void accept(T t, U u, V v);

	/**
	 * Returns a composed {@code TriConsumer} that performs, in sequence, this
	 * operation followed by the {@code after} operation. If performing either
	 * operation throws an exception, it is relayed to the caller of the
	 * composed operation. If performing this operation throws an exception, the
	 * {@code after} operation will not be performed.
	 *
	 * @param  after the operation to perform after this operation
	 * @return a composed {@code TriConsumer} that performs in sequence this
	 *         operation followed by the {@code after} operation
	 */
	public default TriConsumer<T, U, V> andThen(
		TriConsumer<? super T, ? super U, ? super V> after) {

		Objects.requireNonNull(after);

		return (t, u, v) -> {
			accept(t, u, v);
			after.accept(t, u, v);
		};
	}

}