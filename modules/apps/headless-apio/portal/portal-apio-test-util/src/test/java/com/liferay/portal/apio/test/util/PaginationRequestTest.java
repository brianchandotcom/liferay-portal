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

package com.liferay.portal.apio.test.util;

import com.liferay.apio.architect.pagination.Pagination;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PaginationRequestTest {

	@Test
	public void testPaginationRequestOfCreatesValidPaginationInstance() {
		Pagination pagination = PaginationRequest.of(30, 2);

		Assert.assertEquals(60, pagination.getEndPosition());
		Assert.assertEquals(30, pagination.getItemsPerPage());
		Assert.assertEquals(2, pagination.getPageNumber());
		Assert.assertEquals(30, pagination.getStartPosition());
	}

}