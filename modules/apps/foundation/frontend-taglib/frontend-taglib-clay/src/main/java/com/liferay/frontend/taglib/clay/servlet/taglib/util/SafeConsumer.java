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

package com.liferay.frontend.taglib.clay.servlet.taglib.util;

import java.util.function.Consumer;

/**
 * Defines a {@code Consumer} that can throw an exception.
 *
 * <p>
 * This interface can be implemented with a lambda function.
 * </p>
 *
 * @author Carlos Lancha
 * @param  <A> the type of the first argument of the consumer
 */
@FunctionalInterface
public interface SafeConsumer<A> {

	public static <T> Consumer<T> ignore(SafeConsumer<T> safeConsumer) {
		return t -> {
			try {
				safeConsumer.accept(t);
			}
			catch (Exception e) {
				throw new RuntimeException(e);
			}
		};
	}

	/**
	 * Operates with one parameter and returns {@code void}. This function can
	 * be implemented explicitly or with a lambda.
	 *
	 * @param a the first function argument
	 */
	public void accept(A a) throws Exception;

}