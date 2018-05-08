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

package com.liferay.apio.architect.test.util.internal.form;

import java.util.function.Function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Provides a {@code Matcher} that checks if the element returned by a function
 * matches another specific {@code Matcher}.
 *
 * @author Alejandro Hernández
 */
public class IsReturnedIn<T> extends TypeSafeDiagnosingMatcher<T> {

	public IsReturnedIn(Function<T, ?> function, Matcher<?> matcher) {
		_function = function;
		_matcher = matcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"should return something that "
		).appendDescriptionOf(
			_matcher
		);
	}

	@Override
	protected boolean matchesSafely(T t, Description description) {
		Object result = _function.apply(t);

		if (!_matcher.matches(result)) {
			description.appendText(
				"should have returned something that "
			).appendDescriptionOf(
				_matcher
			).appendText(
				" instead of "
			).appendText(
				String.valueOf(result)
			);

			return false;
		}

		return true;
	}

	private final Function<T, ?> _function;
	private final Matcher<?> _matcher;

}