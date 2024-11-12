/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.web.internal.display.context;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Roberto Díaz
 */
public class DefaultMBListDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testEmptyResultsMessage() throws Exception {
		DefaultMBListDisplayContext defaultMBListDisplayContext =
			new DefaultMBListDisplayContext(
				new MockHttpServletRequest(), new MockHttpServletResponse(), 0,
				"test");

		Assert.assertEquals(
			"there-are-no-threads-or-categories",
			defaultMBListDisplayContext.getEmptyResultsMessage());
	}

	@Test
	public void testEmptyResultsMessageOnSearch() throws Exception {
		MockHttpServletRequest mockHttpServletRequest =
			new MockHttpServletRequest();

		mockHttpServletRequest.setParameter("keywords", "test");

		DefaultMBListDisplayContext defaultMBListDisplayContext =
			new DefaultMBListDisplayContext(
				mockHttpServletRequest, new MockHttpServletResponse(), 0,
				"test");

		Assert.assertEquals(
			"there-are-no-threads",
			defaultMBListDisplayContext.getEmptyResultsMessage());
	}

}