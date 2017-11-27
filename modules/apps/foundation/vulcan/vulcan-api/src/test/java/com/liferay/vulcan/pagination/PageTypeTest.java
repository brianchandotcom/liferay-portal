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

package com.liferay.vulcan.pagination;

import static com.liferay.vulcan.pagination.PageType.CURRENT;
import static com.liferay.vulcan.pagination.PageType.FIRST;
import static com.liferay.vulcan.pagination.PageType.LAST;
import static com.liferay.vulcan.pagination.PageType.NEXT;
import static com.liferay.vulcan.pagination.PageType.PREVIOUS;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

import static org.springframework.test.util.MatcherAssertionErrors.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Alejandro Hernández
 */
@RunWith(MockitoJUnitRunner.class)
public class PageTypeTest {

	@Before
	public void setUp() {
		Mockito.when(
			_page.getPageNumber()
		).thenReturn(
			3
		);

		Mockito.when(
			_page.getLastPageNumber()
		).thenReturn(
			5
		);
	}

	@Test
	public void testCallingPageNumberOnCurrentReturnsCurrent() {
		Integer pageNumber = CURRENT.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(3)));
	}

	@Test
	public void testCallingPageNumberOnFirstReturnsOne() {
		Integer pageNumber = FIRST.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(1)));
	}

	@Test
	public void testCallingPageNumberOnLastReturnsLast() {
		Integer pageNumber = LAST.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(5)));
	}

	@Test
	public void testCallingPageNumberOnNextReturnsNext() {
		Integer pageNumber = NEXT.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(4)));
	}

	@Test
	public void testCallingPageNumberOnNextWithLastPageReturnsLast() {
		Mockito.when(
			_page.getLastPageNumber()
		).thenReturn(
			3
		);

		Integer pageNumber = NEXT.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(3)));
	}

	@Test
	public void testCallingPageNumberOnPreviousReturnsPrevious() {
		Integer pageNumber = PREVIOUS.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(2)));
	}

	@Test
	public void testCallingPageNumberOnPreviousWithPageOneReturnsOne() {
		Mockito.when(
			_page.getPageNumber()
		).thenReturn(
			1
		);

		Integer pageNumber = PREVIOUS.getPageNumber(_page);

		assertThat(pageNumber, is(equalTo(1)));
	}

	@Mock
	private final Page _page = Mockito.mock(Page.class);

}