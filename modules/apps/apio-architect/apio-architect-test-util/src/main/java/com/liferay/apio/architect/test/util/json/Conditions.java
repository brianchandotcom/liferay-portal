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

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.test.util.internal.json.IsJsonObject;

import java.util.HashMap;
import java.util.Map;

import org.hamcrest.Description;
import org.hamcrest.Matcher;

/**
 * Provides tests for the {@code Matcher} returned by {@link
 * JsonMatchers#aJsonObjectStringWith(Conditions)}.
 *
 * <p>
 * Don't directly instantiate this class. Use an instance of a {@link
 * Conditions.Builder} instead.
 * </p>
 *
 * @author Alejandro Hernández
 */
public class Conditions
	extends AbstractJsonElementMatcher<JsonObject, JsonObject> {

	@Override
	public void describeTo(Description description) {
		description.appendText(
			"a JSON object where "
		).appendDescriptionOf(
			getInnerMatcher()
		);
	}

	/**
	 * Creates {@link Conditions}.
	 */
	public static class Builder {

		public Builder() {
			this(new HashMap<>());
		}

		/**
		 * Constructs the {@link Conditions} instance by using the builder's
		 * information.
		 *
		 * @return the {@code Conditions} instance
		 */
		public Conditions build() {
			return new Conditions(this);
		}

		/**
		 * Adds a new {@code Matcher} for a JSON object field.
		 *
		 * @param  key the name of the field being tested
		 * @param  valueMatcher the matcher for testing the field value
		 * @return the builder's next step
		 */
		public Builder where(
			String key, Matcher<? extends JsonElement> valueMatcher) {

			Map<String, Matcher<? extends JsonElement>> newMap = new HashMap<>(
				_entryMatchers);

			newMap.put(key, valueMatcher);

			return new Builder(newMap);
		}

		/**
		 * Deactivate strict mode for this checking.
		 *
		 * <p>
		 * Strict mode means that all the elements of the JSON object being
		 * tested must be tested with a {@link #where(String, Matcher)} call.
		 * </p>
		 *
		 * @return the builder's next step
		 */
		public Builder withStrictModeDeactivated() {
			return new Builder(_entryMatchers, false);
		}

		private Builder(
			Map<String, Matcher<? extends JsonElement>> entryMatchers) {

			this(entryMatchers, true);
		}

		private Builder(
			Map<String, Matcher<? extends JsonElement>> entryMatchers,
			boolean strictMode) {

			_entryMatchers = entryMatchers;
			_strictMode = strictMode;
		}

		private final Map<String, Matcher<? extends JsonElement>>
			_entryMatchers;
		private final boolean _strictMode;

	}

	@Override
	protected String getFailText() {
		return "";
	}

	private Conditions(Builder builder) {
		super(
			JsonElementType.OBJECT,
			new IsJsonObject(builder._entryMatchers, builder._strictMode),
			JsonObject::getAsJsonObject);
	}

}