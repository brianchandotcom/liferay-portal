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

package com.liferay.apio.architect.list;

import static co.unruly.matchers.StreamMatchers.contains;
import static co.unruly.matchers.StreamMatchers.empty;

import static com.spotify.hamcrest.optional.OptionalMatchers.emptyOptional;
import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class FunctionalListTest {

	@Test
	public void testRetrievingHeadFromMultiElementListReturnsFirstElement() {
		FunctionalList<String> stringFunctionalList = _getFunctionalList();

		String head = stringFunctionalList.head();

		assertThat(head, is("element1"));
	}

	@Test
	public void testRetrievingHeadFromOneElementListReturnsElement() {
		FunctionalList<String> stringFunctionalList = new FunctionalList<>(
			null, "test");

		String head = stringFunctionalList.head();

		assertThat(head, is("test"));
	}

	@Test
	public void testRetrievingInitFromMultiElementListReturnsInitSublist() {
		FunctionalList<String> stringFunctionalList = _getFunctionalList();

		Stream<String> stream = stringFunctionalList.initStream();

		assertThat(stream, contains("element1", "element2", "element3"));
	}

	@Test
	public void testRetrievingInitFromOneElementListReturnsOneElementStream() {
		FunctionalList<String> stringFunctionalList = new FunctionalList<>(
			null, "test");

		Stream<String> stream = stringFunctionalList.initStream();

		assertThat(stream, contains("test"));
	}

	@Test
	public void testRetrievingLastFromMultiElementListReturnsLastElement() {
		FunctionalList<String> stringFunctionalList = _getFunctionalList();

		Optional<String> optional = stringFunctionalList.lastOptional();

		assertThat(optional, optionalWithValue(equalTo("element4")));
	}

	@Test
	public void testRetrievingLastFromOneElementListReturnsEmpty() {
		FunctionalList<String> stringFunctionalList = new FunctionalList<>(
			null, "test");

		Optional<String> optional = stringFunctionalList.lastOptional();

		assertThat(optional, emptyOptional());
	}

	@Test
	public void testRetrievingMiddleFromMultiElementListReturnsMiddleSublist() {
		FunctionalList<String> stringFunctionalList = _getFunctionalList();

		Stream<String> stream = stringFunctionalList.middleStream();

		assertThat(stream, contains("element2", "element3"));
	}

	@Test
	public void testRetrievingMiddleFromOneElementListReturnsEmptyStream() {
		FunctionalList<String> stringFunctionalList = new FunctionalList<>(
			null, "test");

		Stream<String> stream = stringFunctionalList.middleStream();

		assertThat(stream, empty());
	}

	@Test
	public void testRetrievingTailFromMultiElementListReturnsMiddleSublist() {
		FunctionalList<String> stringFunctionalList = _getFunctionalList();

		Stream<String> stream = stringFunctionalList.tailStream();

		assertThat(stream, contains("element2", "element3", "element4"));
	}

	@Test
	public void testRetrievingTailFromOneElementListReturnsEmptyStream() {
		FunctionalList<String> stringFunctionalList = new FunctionalList<>(
			null, "test");

		Stream<String> stream = stringFunctionalList.tailStream();

		assertThat(stream, empty());
	}

	private FunctionalList<String> _getFunctionalList() {
		FunctionalList<String> stringFunctionalList1 = new FunctionalList<>(
			null, "element1");

		FunctionalList<String> stringFunctionalList2 = new FunctionalList<>(
			stringFunctionalList1, "element2");

		FunctionalList<String> stringFunctionalList3 = new FunctionalList<>(
			stringFunctionalList2, "element3");

		return new FunctionalList<>(stringFunctionalList3, "element4");
	}

}