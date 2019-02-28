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

package com.liferay.portal.vulcan.pagination;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PageTest {

	@Test
	public void testOf() {

		// Empty list

		Page<Integer> page = Page.of(Collections.emptyList());

		assertThat(page.getItems(), is(empty()));
		assertThat(page.getItemsPerPage(), is(0L));
		assertThat(page.getLastPageNumber(), is(1L));
		assertThat(page.getPageNumber(), is(1L));
		assertThat(page.getTotalCount(), is(0L));
		assertThat(page.hasNext(), is(false));
		assertThat(page.hasPrevious(), is(false));

		// List without pagination

		page = Page.of(Arrays.asList(1, 2, 3));

		assertThat(page.getItems(), contains(1, 2, 3));
		assertThat(page.getItemsPerPage(), is(3L));
		assertThat(page.getLastPageNumber(), is(1L));
		assertThat(page.getPageNumber(), is(1L));
		assertThat(page.getTotalCount(), is(3L));
		assertThat(page.hasNext(), is(false));
		assertThat(page.hasPrevious(), is(false));

		// List with pagination

		page = Page.of(Arrays.asList(1, 2, 3), Pagination.of(3, 3), 25);

		assertThat(page.getItems(), contains(1, 2, 3));
		assertThat(page.getItemsPerPage(), is(3L));
		assertThat(page.getLastPageNumber(), is(9L));
		assertThat(page.getPageNumber(), is(3L));
		assertThat(page.getTotalCount(), is(25L));
		assertThat(page.hasNext(), is(true));
		assertThat(page.hasPrevious(), is(true));
	}

}