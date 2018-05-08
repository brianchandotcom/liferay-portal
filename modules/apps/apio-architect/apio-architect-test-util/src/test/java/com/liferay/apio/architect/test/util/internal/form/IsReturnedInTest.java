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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;

import java.util.Optional;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class IsReturnedInTest {

	@Test
	public void testInvalidReturnFails() {
		Optional<String> optional = Optional.of("Api");

		IsReturnedIn<Optional<String>> isReturnedIn = new IsReturnedIn<>(
			Optional::get, is("Apio"));

		assertThat(optional, not(isReturnedIn));
	}

	@Test
	public void testInvalidReturnUpdatesValidDescription() {
		Optional<String> optional = Optional.of("Api");

		IsReturnedIn<Optional<String>> isReturnedIn = new IsReturnedIn<>(
			Optional::get, is("Apio"));

		Description description = new StringDescription();

		isReturnedIn.describeMismatch(optional, description);

		String expected =
			"should have returned something that is \"Apio\" instead of Api";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testIsReturnedInMatcherUpdatesDescription() {
		IsReturnedIn<Optional<String>> isReturnedIn = new IsReturnedIn<>(
			Optional::get, is("Apio"));

		Description description = new StringDescription();

		isReturnedIn.describeTo(description);

		String expected = "should return something that is \"Apio\"";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testValidReturnValidates() {
		Optional<String> optional = Optional.of("Apio");

		IsReturnedIn<Optional<String>> isReturnedIn = new IsReturnedIn<>(
			Optional::get, is("Apio"));

		assertThat(optional, isReturnedIn);
	}

}