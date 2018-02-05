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

package com.liferay.apio.architect.test.util.internal.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.liferay.apio.architect.functional.Try;

import java.util.Objects;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A {@link Matcher} that checks if a string is a correct JSON object.
 *
 * @author Alejandro Hernández
 */
public class IsJsonObjectString extends TypeSafeDiagnosingMatcher<String> {

	public IsJsonObjectString(Matcher<JsonElement> jsonObjectMatcher) {
		_jsonObjectMatcher = jsonObjectMatcher;
	}

	@Override
	public void describeTo(Description description) {
		description.appendDescriptionOf(_jsonObjectMatcher);
	}

	@Override
	protected boolean matchesSafely(String item, Description description) {
		Try<JsonObject> jsonObjectTry = Try.fromFallible(
			() -> new Gson().fromJson(item, JsonObject.class));

		return jsonObjectTry.filter(
			Objects::nonNull
		).fold(
			__ -> {
				description.appendText("was not a JSON object");

				return false;
			},
			jsonObject -> {
				if (_jsonObjectMatcher.matches(jsonObject)) {
					return true;
				}
				else {
					_jsonObjectMatcher.describeMismatch(
						jsonObject, description);

					return false;
				}
			}
		);
	}

	private final Matcher<JsonElement> _jsonObjectMatcher;

}