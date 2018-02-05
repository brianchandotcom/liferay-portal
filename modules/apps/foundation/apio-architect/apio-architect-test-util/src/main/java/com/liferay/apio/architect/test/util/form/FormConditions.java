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

package com.liferay.apio.architect.test.util.form;

import static com.liferay.apio.architect.date.DateTransformer.asDate;

import static org.hamcrest.text.IsEmptyString.emptyOrNullString;

import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Provides tests for the {@code Matcher} {@link
 * FormMatchers#isAFormWithConditions(Function)}.
 *
 * <p>
 * Don't directly instantiate this class. Use an instance of a {@link Builder}
 * instead.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class FormConditions<T> extends TypeSafeDiagnosingMatcher<Form<T>> {

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"a Form...\n"
		).appendText(
			"\t...that should have a non empty title\n"
		).appendText(
			"\t...that should have a non empty description\n"
		);

		_matchers.forEach(
			matcher -> description.appendText(
				"\t...that "
			).appendDescriptionOf(
				matcher
			).appendText(
				"\n"
			));
	}

	/**
	 * Creates {@link FormConditions}.
	 */
	public static class Builder<T> {

		/**
		 * Constructs the {@link FormConditions} instance by using the builder's
		 * information.
		 *
		 * @return the {@code FormConditions} instance
		 */
		public FormConditions<T> build() {
			return new FormConditions<>(_body, _matchers);
		}

		/**
		 * Adds a new {@code Matcher} for a boolean part of the form.
		 *
		 * <p>
		 * The form function should return the value {@code true}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, the method
		 * {@link FormMatchers#isReturnedIn(Function)} should be used.
		 * </p>
		 *
		 * @param  key the name of field being read from the HTTP body
		 * @param  function a function that takes the field value and returns a
		 *         {@code Matcher} for the {@code Form}. The method {@link
		 *         FormMatchers#isReturnedIn(Function)} should be used here.
		 * @return the builder's next step
		 * @review
		 */
		public Builder<T> whereBoolean(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, true);

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a date part of the form.
		 *
		 * <p>
		 * The form function should return the date "2016-06-15T09:00Z".
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, the method
		 * {@link FormMatchers#isReturnedIn(Function)} should be used.
		 * </p>
		 *
		 * @param  key the name of field being read from the HTTP body
		 * @param  function a function that takes the field value and returns a
		 *         {@code Matcher} for the {@code Form}. The method {@link
		 *         FormMatchers#isReturnedIn(Function)} should be used here.
		 * @return the builder's next step
		 * @review
		 */
		public Builder<T> whereDate(
			String key, Function<Object, Matcher<T>> function) {

			String dateString = "2016-06-15T09:00Z";

			Try<Date> dateTry = asDate(dateString);

			Date date = dateTry.orElseThrow(AssertionError::new);

			_body.put(key, dateString);
			_matchers.add(function.apply(date));

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a boolean part of the form.
		 *
		 * <p>
		 * The form function should return the value {@code 21.2D}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, the method
		 * {@link FormMatchers#isReturnedIn(Function)} should be used.
		 * </p>
		 *
		 * @param  key the name of field being read from the HTTP body
		 * @param  function a function that takes the field value and returns a
		 *         {@code Matcher} for the {@code Form}. The method {@link
		 *         FormMatchers#isReturnedIn(Function)} should be used here.
		 * @return the builder's next step
		 * @review
		 */
		public Builder<T> whereDouble(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, 21.2D);

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a boolean part of the form.
		 *
		 * <p>
		 * The form function should return the value {@code 42L}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, the method
		 * {@link FormMatchers#isReturnedIn(Function)} should be used.
		 * </p>
		 *
		 * @param  key the name of field being read from the HTTP body
		 * @param  function a function that takes the field value and returns a
		 *         {@code Matcher} for the {@code Form}. The method {@link
		 *         FormMatchers#isReturnedIn(Function)} should be used here.
		 * @return the builder's next step
		 * @review
		 */
		public Builder<T> whereLong(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, 42L);

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a boolean part of the form.
		 *
		 * <p>
		 * The form function should return the value {@code "String"}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, the method
		 * {@link FormMatchers#isReturnedIn(Function)} should be used.
		 * </p>
		 *
		 * @param  key the name of field being read from the HTTP body
		 * @param  function a function that takes the field value and returns a
		 *         {@code Matcher} for the {@code Form}. The method {@link
		 *         FormMatchers#isReturnedIn(Function)} should be used here.
		 * @return the builder's next step
		 * @review
		 */
		public Builder<T> whereString(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, "String");

			return this;
		}

		private void _add(
			String key, Function<Object, Matcher<T>> function, Object value) {

			_body.put(key, value);
			_matchers.add(function.apply(value));
		}

		private final Map<String, Object> _body = new HashMap<>();
		private final List<Matcher<T>> _matchers = new ArrayList<>();

	}

	@Override
	protected boolean matchesSafely(Form<T> form, Description description) {
		T t = form.get(_body);

		String title = form.getTitle(Locale::getDefault);

		boolean result = true;

		if (emptyOrNullString().matches(title)) {
			description.appendText(
				"was a Form...\n"
			).appendText(
				"\t...whose title was "
			).appendDescriptionOf(
				emptyOrNullString()
			).appendText(
				"\n"
			);

			result = false;
		}

		String formDescription = form.getDescription(Locale::getDefault);

		if (emptyOrNullString().matches(formDescription)) {
			if (result) {
				description.appendText("was a Form...\n");
			}

			description.appendText(
				"\t...whose description was "
			).appendDescriptionOf(
				emptyOrNullString()
			).appendText(
				"\n"
			);

			result = false;
		}

		Stream<Matcher<T>> stream = _matchers.stream();

		List<Matcher<T>> matchers = stream.filter(
			matcher -> !matcher.matches(t)
		).collect(
			Collectors.toList()
		);

		if (!matchers.isEmpty()) {
			if (result) {
				description.appendText("was a Form...\n");
			}

			matchers.forEach(
				matcher -> {
					description.appendText("\t...that ");
					matcher.describeMismatch(t, description);
					description.appendText("\n");
				});

			result = false;
		}

		return result;
	}

	private FormConditions(
		Map<String, Object> body, List<Matcher<T>> matchers) {

		_body = body;
		_matchers = matchers;
	}

	private final Map<String, Object> _body;
	private final List<Matcher<T>> _matchers;

}