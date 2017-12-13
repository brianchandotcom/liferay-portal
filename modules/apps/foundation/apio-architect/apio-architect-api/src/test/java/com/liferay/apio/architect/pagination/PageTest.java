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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;

import com.liferay.apio.architect.uri.Path;

import java.util.Collections;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class PageTest {

	@Before
	public void setUp() {
		_pageItems = new PageItems<>(Collections.singleton("apio"), 10);

		_pagination = Mockito.mock(Pagination.class);

		Mockito.when(
			_pagination.getItemsPerPage()
		).thenReturn(
			1
		);

		Mockito.when(
			_pagination.getPageNumber()
		).thenReturn(
			4
		);

		_path = new Path("name", "id");

		_page = new Page<>(String.class, _pageItems, _pagination, _path);
	}

	@Test
	public void testGetItemsPerPageReturnsItemsPerPage() {
		assertThat(_page.getItemsPerPage(), is(equalTo(1)));
	}

	@Test
	public void testGetItemsReturnsItems() {
		assertThat(_page.getItems(), contains("apio"));
	}

	@Test
	public void testGetLastPageNumberReturnsLastPageNumber() {
		assertThat(_page.getLastPageNumber(), is(equalTo(10)));
	}

	@Test
	public void testGetModelClassReturnsModelClass() {
		assertThat(_page.getModelClass(), is(equalTo(String.class)));
	}

	@Test
	public void testGetPageNumberReturnsPageNumber() {
		assertThat(_page.getPageNumber(), is(equalTo(4)));
	}

	@Test
	public void testGetPathReturnsPath() {
		Path path = _page.getPath();

		assertThat(path.getId(), is(equalTo("id")));
		assertThat(path.getName(), is(equalTo("name")));
	}

	@Test
	public void testGetTotalCountReturnsTotalCount() {
		assertThat(_page.getTotalCount(), is(equalTo(10)));
	}

	@Test
	public void testHasNextReturnsFalseWhenIsLast() {
		Mockito.when(
			_pagination.getPageNumber()
		).thenReturn(
			10
		);

		_path = new Path("name", "id");

		Page<String> page = new Page<>(
			String.class, _pageItems, _pagination, _path);

		assertThat(page.hasNext(), is(false));
	}

	@Test
	public void testHasNextReturnsTrueWhenThereIsNext() {
		assertThat(_page.hasNext(), is(true));
	}

	@Test
	public void testHasPreviousReturnsFalseWhenIsFirst() {
		Mockito.when(
			_pagination.getPageNumber()
		).thenReturn(
			1
		);

		_path = new Path("name", "id");

		Page<String> page = new Page<>(
			String.class, _pageItems, _pagination, _path);

		assertThat(page.hasPrevious(), is(false));
	}

	@Test
	public void testHasPreviousReturnsTrueWhenThereIsPrevious() {
		assertThat(_page.hasPrevious(), is(true));
	}

	private Page<String> _page;
	private PageItems<String> _pageItems;
	private Pagination _pagination;
	private Path _path;

}