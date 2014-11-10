/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.java8.util;

/**
 * [[@]] Stripped down version of java 8 Function type
 *
 * Represents a function that accepts one argument and produces a result.
 * In functional code, the output value of a function depends only on the arguments
 * that are input to the function, so calling a function twice with the same value
 * for an argument x will produce the same result f(x) each time. Function
 * state is not changed.
 *
 * Parameters:
 * <T> the type of the input to the function
 * <R> the type of the result of the function
 */
public interface Function<T, R> {

	public R apply(T t);

}
