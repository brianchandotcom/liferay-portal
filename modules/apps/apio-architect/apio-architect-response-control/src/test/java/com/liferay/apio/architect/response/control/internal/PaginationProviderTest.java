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

package com.liferay.apio.architect.response.control.internal;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import com.liferay.apio.architect.pagination.Pagination;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Alejandro Hernández
 */
public class PaginationProviderTest {

	@Test
	public void testPaginationProviderReturnDefaultValuesIfError() {
		PaginationProvider paginationProvider = new PaginationProvider();

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("per_page")
		).thenReturn(
			"Apio"
		);

		Pagination pagination = paginationProvider.createContext(
			httpServletRequest);

		assertThat(pagination.getPageNumber(), is(1));
		assertThat(pagination.getItemsPerPage(), is(30));
	}

	@Test
	public void testPaginationProviderReturnDefaultValuesIfLessThanOne() {
		PaginationProvider paginationProvider = new PaginationProvider();

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("per_page")
		).thenReturn(
			"-4"
		);

		Mockito.when(
			httpServletRequest.getParameter("page")
		).thenReturn(
			"0"
		);

		Pagination pagination = paginationProvider.createContext(
			httpServletRequest);

		assertThat(pagination.getPageNumber(), is(1));
		assertThat(pagination.getItemsPerPage(), is(30));
	}

	@Test
	public void testPaginationProviderReturnsPaginationIfParams() {
		PaginationProvider paginationProvider = new PaginationProvider();

		HttpServletRequest httpServletRequest = Mockito.mock(
			HttpServletRequest.class);

		Mockito.when(
			httpServletRequest.getParameter("per_page")
		).thenReturn(
			"42"
		);

		Mockito.when(
			httpServletRequest.getParameter("page")
		).thenReturn(
			"6"
		);

		Pagination pagination = paginationProvider.createContext(
			httpServletRequest);

		assertThat(pagination.getPageNumber(), is(6));
		assertThat(pagination.getItemsPerPage(), is(42));
	}

}