/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.pagination;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Arrays;
import java.util.Collections;

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alejandro Hernández
 */
public class PageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testOf() {

		// Empty list

		Page<Integer> page = Page.of(Collections.emptyList());

		MatcherAssert.assertThat(
			page.getItems(), Matchers.is(Matchers.empty()));
		MatcherAssert.assertThat(page.getLastPage(), Matchers.is(1L));
		MatcherAssert.assertThat(page.getPage(), Matchers.is(1L));
		MatcherAssert.assertThat(page.getPageSize(), Matchers.is(0L));
		MatcherAssert.assertThat(page.getTotalCount(), Matchers.is(0L));
		MatcherAssert.assertThat(page.hasNext(), Matchers.is(false));
		MatcherAssert.assertThat(page.hasPrevious(), Matchers.is(false));

		// List without pagination

		page = Page.of(Arrays.asList(1, 2, 3));

		MatcherAssert.assertThat(page.getItems(), Matchers.contains(1, 2, 3));
		MatcherAssert.assertThat(page.getLastPage(), Matchers.is(1L));
		MatcherAssert.assertThat(page.getPage(), Matchers.is(1L));
		MatcherAssert.assertThat(page.getPageSize(), Matchers.is(3L));
		MatcherAssert.assertThat(page.getTotalCount(), Matchers.is(3L));
		MatcherAssert.assertThat(page.hasNext(), Matchers.is(false));
		MatcherAssert.assertThat(page.hasPrevious(), Matchers.is(false));

		// List with pagination

		page = Page.of(Arrays.asList(1, 2, 3), Pagination.of(3, 3), 25);

		MatcherAssert.assertThat(page.getItems(), Matchers.contains(1, 2, 3));
		MatcherAssert.assertThat(page.getLastPage(), Matchers.is(9L));
		MatcherAssert.assertThat(page.getPage(), Matchers.is(3L));
		MatcherAssert.assertThat(page.getPageSize(), Matchers.is(3L));
		MatcherAssert.assertThat(page.getTotalCount(), Matchers.is(25L));
		MatcherAssert.assertThat(page.hasNext(), Matchers.is(true));
		MatcherAssert.assertThat(page.hasPrevious(), Matchers.is(true));
	}

}