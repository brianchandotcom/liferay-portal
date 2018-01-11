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
import static org.hamcrest.core.Is.is;

import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PaginationTest {

	@Test
	public void testEndPositionReturnsEndPosition() {
		Pagination pagination = new Pagination(30, 3);

		assertThat(pagination.getEndPosition(), is(90));
	}

	@Test
	public void testStartPositionReturnsStartPosition() {
		Pagination pagination = new Pagination(30, 3);

		assertThat(pagination.getStartPosition(), is(60));
	}

}