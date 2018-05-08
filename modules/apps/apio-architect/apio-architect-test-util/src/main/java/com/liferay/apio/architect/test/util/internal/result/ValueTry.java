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

import static com.liferay.apio.architect.test.util.result.TryMatchers.aSuccessTry;

import com.liferay.apio.architect.functional.Try;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A {@code Matcher} that checks if an element is a {@link Try.Success} with a
 * specific value.
 *
 * @author Alejandro Hernández
 * @param  <T> the {@code Try}'s possible value type
 */
public class ValueTry<T> extends TypeSafeDiagnosingMatcher<Try<T>> {

	public ValueTry(final Matcher<T> matcher) {
		_matcher = matcher;
	}

	@Override
	public void describeTo(final Description description) {
		description.appendDescriptionOf(
			_successMatcher
		).appendText(
			" with a value that "
		).appendDescriptionOf(
			_matcher
		);
	}

	@Override
	protected boolean matchesSafely(
		final Try<T> tTry, final Description description) {

		if (!_successMatcher.matches(tTry)) {
			_successMatcher.describeMismatch(tTry, description);

			return false;
		}

		T t = tTry.getUnchecked();

		if (_matcher.matches(t)) {
			return true;
		}

		description.appendText("was a Success whose value ");

		_matcher.describeMismatch(t, description);

		return false;
	}

	private final Matcher<T> _matcher;
	private final Matcher<Try<T>> _successMatcher = aSuccessTry();

}