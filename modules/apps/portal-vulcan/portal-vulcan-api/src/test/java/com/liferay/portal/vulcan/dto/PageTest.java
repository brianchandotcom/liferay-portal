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

package com.liferay.portal.vulcan.dto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import com.liferay.portal.vulcan.context.Pagination;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PageTest {

	@Test
	public void testPageFromEmptyListCreatesOnePageCollectionPage() {
		List<Integer> items = Collections.emptyList();

		Page<Integer> page = Page.of(items);

		assertThat(page.hasNext(), is(false));
		assertThat(page.hasPrevious(), is(false));
		assertThat(page.getItemsPerPage(), is(0));
		assertThat(page.getItems(), is(empty()));
		assertThat(page.getLastPageNumber(), is(1));
		assertThat(page.getPageNumber(), is(1));
		assertThat(page.getTotalCount(), is(0));
	}

	@Test
	public void testPageFromListCreatesOnePageCollectionPage() {
		List<Integer> items = Arrays.asList(1, 2, 3);

		Page<Integer> page = Page.of(items);

		assertThat(page.hasNext(), is(false));
		assertThat(page.hasPrevious(), is(false));
		assertThat(page.getItemsPerPage(), is(3));
		assertThat(page.getItems(), contains(1, 2, 3));
		assertThat(page.getLastPageNumber(), is(1));
		assertThat(page.getPageNumber(), is(1));
		assertThat(page.getTotalCount(), is(3));
	}

	@Test
	public void testPageWithPaginationCreateMultiPageCollectionPage() {
		List<Integer> items = Arrays.asList(1, 2, 3);

		Pagination pagination = Pagination.of(3, 3);

		Page<Integer> page = Page.of(items, pagination, 25);

		assertThat(page.hasNext(), is(true));
		assertThat(page.hasPrevious(), is(true));
		assertThat(page.getItemsPerPage(), is(3));
		assertThat(page.getItems(), contains(1, 2, 3));
		assertThat(page.getLastPageNumber(), is(9));
		assertThat(page.getPageNumber(), is(3));
		assertThat(page.getTotalCount(), is(25));
	}

}