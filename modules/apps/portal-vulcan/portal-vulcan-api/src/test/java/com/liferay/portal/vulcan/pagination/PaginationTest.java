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

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.hamcrest.MatcherAssert;
import org.hamcrest.core.Is;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PaginationTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testOf() {
		Pagination pagination = Pagination.of(3, 30);

		MatcherAssert.assertThat(pagination.getEndPosition(), Is.is(90));
		MatcherAssert.assertThat(pagination.getPage(), Is.is(3));
		MatcherAssert.assertThat(pagination.getPageSize(), Is.is(30));
		MatcherAssert.assertThat(pagination.getStartPosition(), Is.is(60));
	}

}