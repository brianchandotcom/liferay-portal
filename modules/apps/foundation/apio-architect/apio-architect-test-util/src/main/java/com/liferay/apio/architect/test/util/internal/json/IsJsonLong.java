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

import com.google.gson.JsonPrimitive;

import com.liferay.apio.architect.test.util.json.AbstractJsonElementMatcher;
import com.liferay.apio.architect.test.util.json.JsonElementType;

import java.util.Objects;

import org.hamcrest.Matcher;

/**
 * A {@code Matcher} that checks if an element is a correct long JSON primitive.
 *
 * @author Alejandro Hernández
 */
public class IsJsonLong
	extends AbstractJsonElementMatcher<JsonPrimitive, Long> {

	public IsJsonLong(Matcher<Long> numberMatcher) {
		super(JsonElementType.NUMBER, numberMatcher, JsonPrimitive::getAsLong);

		Objects.requireNonNull(numberMatcher);
	}

}