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

package com.liferay.portal.kernel.dao.search;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class SearchPaginationUtilTest {

	@Test
	public void testCalculateStartAndEndWhenEmptyResultsPage() {
		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			40, 60, 10);

		Assert.assertEquals(0, startAndEnd[0]);
		Assert.assertEquals(10, startAndEnd[1]);
	}

	@Test
	public void testCalculateStartAndEndWhenFullResultsPage() {
		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			20, 40, 20);

		Assert.assertEquals(0, startAndEnd[0]);
		Assert.assertEquals(20, startAndEnd[1]);
	}

	@Test
	public void testCalculateStartAndEndWhenNoResults() {
		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			20, 40, 0);

		Assert.assertEquals(0, startAndEnd[0]);
		Assert.assertEquals(0, startAndEnd[1]);
	}

	@Test
	public void testCalculateStartAndEndWhenResultsPage() {
		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(
			20, 40, 80);

		Assert.assertEquals(20, startAndEnd[0]);
		Assert.assertEquals(40, startAndEnd[1]);
	}

	@Test
	public void testNotCalculateStartAndEndWhenNoResultsAndInitialPage() {
		int[] startAndEnd = SearchPaginationUtil.calculateStartAndEnd(0, 20, 0);

		Assert.assertEquals(0, startAndEnd[0]);
		Assert.assertEquals(0, startAndEnd[1]);
	}

}