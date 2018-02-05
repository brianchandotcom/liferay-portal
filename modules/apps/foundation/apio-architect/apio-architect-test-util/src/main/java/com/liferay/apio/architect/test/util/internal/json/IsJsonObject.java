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

import static com.liferay.apio.architect.test.util.internal.util.DescriptionUtil.indentDescription;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

/**
 * A {@code Matcher} that checks if an element is a correct JSON object.
 *
 * @author Alejandro Hernández
 */
public class IsJsonObject extends TypeSafeDiagnosingMatcher<JsonObject> {

	public IsJsonObject(
		Map<String, Matcher<? extends JsonElement>> entryMatchers,
		boolean strictMode) {

		_entryMatchers = entryMatchers;
		_strictMode = strictMode;
	}

	@Override
	public void describeTo(Description description) {
		description.appendText("{\n");

		_entryMatchers.forEach(
			(key, matcher) -> indentDescription(
				description, key, matcher::describeTo));

		description.appendText("}");
	}

	@Override
	protected boolean matchesSafely(
		JsonObject jsonObject, Description description) {

		Map<String, Consumer<Description>> mismatchedKeys = new HashMap<>();

		_entryMatchers.forEach(
			(key, value) -> {
				JsonElement jsonElement = jsonObject.get(key);

				if (!value.matches(jsonElement)) {
					mismatchedKeys.put(
						key,
						consumerDescription -> value.describeMismatch(
							jsonElement, consumerDescription));
				}
			});

		Set<String> keys = _entryMatchers.keySet();

		if (!mismatchedKeys.isEmpty()) {
			description.appendText("was a JSON object ");

			String previousMismatchKey = null;
			String previousKey = null;

			description.appendText("{\n");

			for (String key : keys) {
				if (mismatchedKeys.containsKey(key)) {
					if (previousKey != null) {
						_checkPreviousKeys(
							description, previousMismatchKey, previousKey);
					}

					indentDescription(
						description, key, mismatchedKeys.get(key));

					previousMismatchKey = key;
				}

				previousKey = key;
			}

			_checkPreviousKeys(description, previousMismatchKey, previousKey);

			description.appendText("}");

			return false;
		}

		if (_strictMode && (jsonObject.size() > _entryMatchers.size())) {
			Set<String> jsonObjectKeys = new HashSet<>(jsonObject.keySet());

			jsonObjectKeys.removeAll(keys);

			Stream<String> stream = jsonObjectKeys.stream();

			String extraKeys = stream.collect(Collectors.joining(", "));

			description.appendText(
				"was a JSON object "
			).appendText(
				"with more fields than validated. "
			).appendText(
				"Extra keys: "
			).appendText(
				extraKeys
			);

			return false;
		}

		return true;
	}

	private void _checkPreviousKeys(
		Description description, String previousMismatchKey,
		String previousKey) {

		if (!Objects.equals(previousMismatchKey, previousKey)) {
			description.appendText("  ...\n");
		}
	}

	private final Map<String, Matcher<? extends JsonElement>> _entryMatchers;
	private final boolean _strictMode;

}