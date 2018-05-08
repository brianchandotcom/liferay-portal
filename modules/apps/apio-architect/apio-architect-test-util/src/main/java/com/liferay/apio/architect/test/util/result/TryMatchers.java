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

package com.liferay.apio.architect.test.util.result;

import com.liferay.apio.architect.functional.Try;
import com.liferay.apio.architect.test.util.internal.result.FailTry;
import com.liferay.apio.architect.test.util.internal.result.SuccessTry;
import com.liferay.apio.architect.test.util.internal.result.ValueTry;

import org.hamcrest.Matcher;

/**
 * Provides {@code Matcher} objects that can be used for testing the {@link Try}
 * monadic type.
 *
 * <p>
 * This class shouldn't be instantiated.
 * </p>
 *
 * @author Alejandro Hernández
 */
public final class TryMatchers {

	/**
	 * Returns a matcher that checks if this {@code Try} is a {@code
	 * Try.Failure}.
	 *
	 * @return the matcher that checks if this {@code Try} is a failure
	 */
	public static <T> Matcher<Try<T>> aFailTry() {
		return new FailTry<>();
	}

	/**
	 * Returns a matcher that checks if this {@code Try} is a {@code
	 * Try.Success}.
	 *
	 * @return the matcher that checks if this try is a success
	 */
	public static <T> Matcher<Try<T>> aSuccessTry() {
		return new SuccessTry<>();
	}

	/**
	 * Returns a matcher that checks if this {@code Try} is a {@code
	 * Try.Success} with a certain value.
	 *
	 * @param  matcher the matcher used to check the value
	 * @return the matcher that checks if this {@code Try} is a success with a
	 *         certain value
	 */
	public static <T> Matcher<Try<T>> aTryWithValueThat(
		final Matcher<T> matcher) {

		return new ValueTry<>(matcher);
	}

	private TryMatchers() {
		throw new UnsupportedOperationException();
	}

}