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

package com.liferay.vulcan.functions;

import java.util.Objects;
import java.util.function.Function;

/**
 * @author Alejandro Hernández
 */
@FunctionalInterface
public interface OctaFunction<A, B, C, D, E, F, G, H, R> {

	public default <V> OctaFunction<A, B, C, D, E, F, G, H, V> andThen(
		Function<? super R, ? extends V> after) {

		Objects.requireNonNull(after);
		return (A a, B b, C c, D d, E e, F f, G g, H h) -> after.apply(
			apply(a, b, c, d, e, f, g, h));
	}

	public R apply(A a, B b, C c, D d, E e, F f, G g, H h);

}