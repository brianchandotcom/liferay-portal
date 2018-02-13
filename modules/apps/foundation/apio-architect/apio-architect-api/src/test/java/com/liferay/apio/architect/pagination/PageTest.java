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

package com.liferay.apio.architect.pagination;

import static com.liferay.apio.architect.operation.Method.POST;

import static com.spotify.hamcrest.optional.OptionalMatchers.optionalWithValue;

import static java.util.Collections.emptyList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;

import com.liferay.apio.architect.operation.Operation;
import com.liferay.apio.architect.uri.Path;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PageTest {

	@Before
	public void setUp() {
		_pageItems = new PageItems<>(Collections.singleton("apio"), 10);

		Pagination pagination = new Pagination(1, 4);

		_path = new Path("name", "id");

		_operations = Collections.singletonList(
			new Operation(POST, "operation"));

		_page = new Page<>("name", _pageItems, pagination, _path, _operations);
	}

	@Test
	public void testGetItemsPerPageReturnsItemsPerPage() {
		assertThat(_page.getItemsPerPage(), is(1));
	}

	@Test
	public void testGetItemsReturnsItems() {
		assertThat(_page.getItems(), contains("apio"));
	}

	@Test
	public void testGetLastPageNumberReturnsLastPageNumber() {
		assertThat(_page.getLastPageNumber(), is(10));
	}

	@Test
	public void testGetOperationsReturnsList() {
		assertThat(_page.getOperations(), is(_operations));
	}

	@Test
	public void testGetPageNumberReturnsPageNumber() {
		assertThat(_page.getPageNumber(), is(4));
	}

	@Test
	public void testGetPathReturnsPath() {
		Optional<Path> optional = _page.getPathOptional();

		assertThat(optional, is(optionalWithValue()));

		optional.ifPresent(
			path -> {
				assertThat(path.getId(), is("id"));
				assertThat(path.getName(), is("name"));
			});
	}

	@Test
	public void testGetResourceNameReturnsResourceName() {
		assertThat(_page.getResourceName(), is("name"));
	}

	@Test
	public void testGetTotalCountReturnsTotalCount() {
		assertThat(_page.getTotalCount(), is(10));
	}

	@Test
	public void testHasNextReturnsFalseWhenIsLast() {
		Pagination pagination = new Pagination(1, 10);

		_path = new Path("name", "id");

		Page<String> page = new Page<>(
			"", _pageItems, pagination, _path, emptyList());

		assertThat(page.hasNext(), is(false));
	}

	@Test
	public void testHasNextReturnsTrueWhenThereIsNext() {
		assertThat(_page.hasNext(), is(true));
	}

	@SuppressWarnings("ResultOfMethodCallIgnored")
	@Test
	public void testHasPreviousReturnsFalseWhenIsFirst() {
		Pagination pagination = new Pagination(1, 1);

		_path = new Path("name", "id");

		Page<String> page = new Page<>(
			"", _pageItems, pagination, _path, emptyList());

		assertThat(page.hasPrevious(), is(false));
	}

	@Test
	public void testHasPreviousReturnsTrueWhenThereIsPrevious() {
		assertThat(_page.hasPrevious(), is(true));
	}

	private List<Operation> _operations;
	private Page<String> _page;
	private PageItems<String> _pageItems;
	private Path _path;

}