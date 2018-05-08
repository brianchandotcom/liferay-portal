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

package com.liferay.apio.architect.test.util.internal.result;

import com.liferay.apio.architect.functional.Try;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Provides a {@code org.hamcrest.Matcher} that checks if an element is a {@link
 * Try.Success}.
 *
 * @author Alejandro Hernández
 * @param  <T> the {@code Try}'s possible value type
 */
public class SuccessTry<T> extends TypeSafeDiagnosingMatcher<Try<T>> {

	@Override
	public void describeTo(final Description description) {
		description.appendText("a Success");
	}

	@Override
	protected boolean matchesSafely(
		final Try<T> tTry, final Description description) {

		if (tTry.isSuccess()) {
			return true;
		}

		description.appendText("was a Failure");

		return false;
	}

}