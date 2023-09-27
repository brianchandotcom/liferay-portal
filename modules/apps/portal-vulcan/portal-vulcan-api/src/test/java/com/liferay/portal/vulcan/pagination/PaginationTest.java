/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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