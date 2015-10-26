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

package com.liferay.whip.util;

/**
 * @author Shuyang Zhou
 */
public class ReflectionUtil {

	public static <T> T throwException(Throwable throwable) {
		return ReflectionUtil.<T, RuntimeException>_doThrowException(throwable);
	}

	@SuppressWarnings("unchecked")
	private static <T, E extends Throwable> T _doThrowException(
			Throwable throwable)
		throws E {

		throw (E)throwable;
	}

}