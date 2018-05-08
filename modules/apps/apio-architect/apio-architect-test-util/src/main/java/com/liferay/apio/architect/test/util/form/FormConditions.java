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

import com.liferay.apio.architect.form.Body;
import com.liferay.apio.architect.form.Form;
import com.liferay.apio.architect.functional.Try;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Provides tests for the {@code Matcher} returned by {@link
 * FormMatchers#isAFormWithConditions(Function)}.
 *
 * <p>
 * Don't directly instantiate this class. Use a {@link Builder} instance
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
			Body body = Body.create(
				key -> Optional.ofNullable(_map.get(key)),
				key -> Optional.ofNullable(_listMap.get(key)));

			return new FormConditions<>(body, _matchers);
		}

		/**
		 * Adds a new {@code Matcher} for a boolean part of the form.
		 *
		 * <p>
		 * The form function should return {@code true}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereBoolean(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, "true", true);

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
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereDate(
			String key, Function<Object, Matcher<T>> function) {

			String dateString = "2016-06-15T09:00Z";

			Try<Date> dateTry = asDate(dateString);

			Date date = dateTry.orElseThrow(AssertionError::new);

			_map.put(key, dateString);
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
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereDouble(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, "21.2", 21.2);

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
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereLong(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, "42", 42L);

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a string part of the form.
		 *
		 * <p>
		 * The form function should return the value {@code "String"}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereString(
			String key, Function<Object, Matcher<T>> function) {

			_add(key, function, "String", "String");

			return this;
		}

		/**
		 * Adds a new {@code Matcher} for a string list part of the form.
		 *
		 * <p>
		 * The form function should return a list containing {@code "String1"}
		 * and {@code "String2}.
		 * </p>
		 *
		 * <p>
		 * To provide information about the form method to call, use the method
		 * {@link FormMatchers#isReturnedIn(Function)}.
		 * </p>
		 *
		 * @param  key the name of the field read from the HTTP body
		 * @param  function the function that takes the field value and returns
		 *         a {@code Matcher} for the {@code Form}. Use the method {@link
		 *         FormMatchers#isReturnedIn(Function)}.
		 * @return the builder's next step
		 */
		public Builder<T> whereStringList(
			String key, Function<Object, Matcher<T>> function) {

			_addList(
				key, function, Arrays.asList("String1", "String2"),
				Arrays.asList("String1", "String2"));

			return this;
		}

		private void _add(
			String key, Function<Object, Matcher<T>> function, String value,
			Object transformedValue) {

			_map.put(key, value);
			_matchers.add(function.apply(transformedValue));
		}

		private void _addList(
			String key, Function<Object, Matcher<T>> function,
			List<String> list, List<Object> transformedList) {

			_listMap.put(key, list);
			_matchers.add(function.apply(transformedList));
		}

		private final Map<String, List<String>> _listMap = new HashMap<>();
		private final Map<String, String> _map = new HashMap<>();
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

	private FormConditions(Body body, List<Matcher<T>> matchers) {
		_body = body;
		_matchers = matchers;
	}

	private final Body _body;
	private final List<Matcher<T>> _matchers;

}