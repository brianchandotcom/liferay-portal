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

package com.liferay.portal.apio.permission;

import com.liferay.apio.architect.credentials.Credentials;
import com.liferay.apio.architect.function.throwable.ThrowableBiFunction;
import com.liferay.apio.architect.functional.Try;

import java.util.function.BiFunction;

/**
 * Provides utility functions for APIs permission checkers.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Sarai Díaz
 * @review
 */
public class HasPermissionUtil {

	/**
	 * Execute the received function and transforms any occurred exception into a {@code false}
	 * @review
	 */
	public static BiFunction<Credentials, Long, Boolean> failOnException(
		ThrowableBiFunction<Credentials, Long, Boolean> throwableBiFunction) {

		return (credentials, id) -> Try.fromFallible(
			() -> throwableBiFunction.apply(credentials, id)
		).orElse(
			false
		);
	}

}