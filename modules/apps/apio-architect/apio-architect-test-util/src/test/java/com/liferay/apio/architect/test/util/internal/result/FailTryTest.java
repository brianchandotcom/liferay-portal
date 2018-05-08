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

import static com.liferay.apio.architect.test.util.result.TryMatchers.aFailTry;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.functional.Try;

import org.hamcrest.Description;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FailTryTest {

	@Test
	public void testFailTryMatcherUpdatesDescription() {
		Description description = new StringDescription();

		aFailTry().describeTo(description);

		String expected = "a Failure";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testFailTryValidates() {
		Try<String> stringTry = Try.fail(new Exception());

		boolean matches = aFailTry().matches(stringTry);

		assertThat(matches, is(true));
	}

	@Test
	public void testSuccessTryFails() {
		Try<String> stringTry = Try.success("Live long and prosper");

		assertThat(stringTry, is(not(aFailTry())));
	}

	@Test
	public void testSuccessTryUpdatesDescription() {
		Try<String> stringTry = Try.success("Live long and prosper");

		Description description = new StringDescription();

		aFailTry().describeMismatch(stringTry, description);

		String expected = "was a Success";

		assertThat(description.toString(), is(expected));
	}

}