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

package com.liferay.apio.architect.test.util.json;

import static com.liferay.apio.architect.unsafe.Unsafe.unsafeCast;

import com.google.gson.JsonElement;

import java.util.Objects;
import java.util.function.Function;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * Creates a {@code Matcher} for a type of {@code JsonElement}.
 *
 * @author Alejandro Hernández
 * @param  <T> the type of {@code JsonElement} this matcher validates
 * @param  <S> the type of the element's actual value
 */
public abstract class AbstractJsonElementMatcher<T extends JsonElement, S>
	extends TypeSafeDiagnosingMatcher<JsonElement> {

	public AbstractJsonElementMatcher(
		JsonElementType jsonElementType, Matcher<S> sMatcher,
		Function<T, S> transformFunction) {

		this(
			jsonElementType, " element with a value that ", sMatcher,
			transformFunction);
	}

	public AbstractJsonElementMatcher(
		JsonElementType jsonElementType, String message, Matcher<S> sMatcher,
		Function<T, S> transformFunction) {

		super(JsonElement.class);

		_jsonElementType = Objects.requireNonNull(jsonElementType);
		_message = message;
		_sMatcher = sMatcher;
		_transformFunction = transformFunction;
	}

	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(
			_jsonElementType
		).appendText(
			_message
		).appendDescriptionOf(
			_sMatcher
		);
	}

	/**
	 * Returns the text to use when validation fails.
	 *
	 * @return the text to use when validation fails
	 */
	protected String getFailText() {
		return "was " + _jsonElementType.getReadableName() + _message;
	}

	/**
	 * Returns this matcher's inner matcher.
	 *
	 * @return this matcher's inner matcher
	 */
	protected Matcher<S> getInnerMatcher() {
		return _sMatcher;
	}

	@Override
	protected boolean matchesSafely(
		JsonElement jsonElement, Description description) {

		JsonElementType jsonElementType = JsonElementType.getJsonElementType(
			jsonElement);

		if (jsonElementType.equals(_jsonElementType)) {
			T t = unsafeCast(jsonElement);

			S value = _transformFunction.apply(t);

			if (_sMatcher.matches(value)) {
				return true;
			}

			description.appendText(getFailText());

			_sMatcher.describeMismatch(value, description);

			return false;
		}

		description.appendText(
			"was not "
		).appendDescriptionOf(
			_jsonElementType
		).appendText(
			" element, but "
		).appendDescriptionOf(
			jsonElementType
		).appendText(
			" element"
		);

		return false;
	}

	private final JsonElementType _jsonElementType;
	private final String _message;
	private final Matcher<S> _sMatcher;
	private final Function<T, S> _transformFunction;

}