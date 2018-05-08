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

package com.liferay.apio.architect.test.util.internal.list;

import static com.liferay.apio.architect.test.util.list.FunctionalListMatchers.aFunctionalListThat;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.list.FunctionalList;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class IsAFunctionalListTest {

	@Test
	public void testFunctionalListMatcherUpdatesDescription() {
		Description description = new StringDescription();

		aFunctionalListThat(contains(2, 3, 4)).describeTo(description);

		String expected =
			"a functional list that conforms with an iterable containing " +
				"[<2>, <3>, <4>]";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testInvalidFunctionalListFails() {
		FunctionalList<Integer> functionalList = new FunctionalList<>(null, 1);

		assertThat(
			functionalList, is(not(aFunctionalListThat(contains(2, 3)))));
	}

	@Test
	public void testInvalidFunctionalListUpdatesDescription() {
		FunctionalList<Integer> functionalList = new FunctionalList<>(null, 1);

		Description description = new StringDescription();

		Matcher<FunctionalList<Integer>> functionalListMatcher =
			aFunctionalListThat(contains(2));

		functionalListMatcher.describeMismatch(functionalList, description);

		String expected = "was a functional list whose item 0: was <1>";

		assertThat(description.toString(), is(expected));
	}

	@Test
	public void testValidFunctionalListValidates() {
		FunctionalList<Integer> functionalList = new FunctionalList<>(null, 1);

		boolean matches = aFunctionalListThat(
			contains(1)).matches(functionalList);

		assertThat(matches, is(true));
	}

}