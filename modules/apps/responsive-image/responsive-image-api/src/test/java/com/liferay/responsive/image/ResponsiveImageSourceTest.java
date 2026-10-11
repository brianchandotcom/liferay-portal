/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.responsive.image;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Daniel Sanz
 */
public class ResponsiveImageSourceTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetCandidatesIgnoresLaterChangesToTheGivenList() {
		List<ResponsiveImageCandidate> responsiveImageCandidates =
			new ArrayList<>(_responsiveImageCandidates);

		ResponsiveImageSource responsiveImageSource =
			ResponsiveImageSource.builder(
			).candidates(
				responsiveImageCandidates
			).query(
				null
			).sizes(
				RandomTestUtil.randomString()
			).build();

		responsiveImageCandidates.clear();

		Assert.assertEquals(
			_responsiveImageCandidates, responsiveImageSource.getCandidates());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetCandidatesReturnsAnUnmodifiableList() {
		ResponsiveImageSource responsiveImageSource =
			ResponsiveImageSource.builder(
			).candidates(
				_responsiveImageCandidates
			).query(
				null
			).sizes(
				RandomTestUtil.randomString()
			).build();

		List<ResponsiveImageCandidate> responsiveImageCandidates =
			responsiveImageSource.getCandidates();

		responsiveImageCandidates.add(
			ResponsiveImageCandidate.builder(
				RandomTestUtil.randomString()
			).build());
	}

	@Test
	public void testGetQueryWhenUnconditional() {
		ResponsiveImageSource responsiveImageSource =
			ResponsiveImageSource.builder(
			).candidates(
				Collections.<ResponsiveImageCandidate>emptyList()
			).query(
				null
			).sizes(
				RandomTestUtil.randomString()
			).build();

		Assert.assertNull(responsiveImageSource.getQuery());
	}

	@Test
	public void testGetSizesWhenNoneIsGiven() {
		String query = RandomTestUtil.randomString();

		ResponsiveImageSource responsiveImageSource =
			ResponsiveImageSource.builder(
			).candidates(
				_responsiveImageCandidates
			).query(
				query
			).sizes(
				null
			).build();

		Assert.assertEquals(query, responsiveImageSource.getQuery());
		Assert.assertNull(responsiveImageSource.getSizes());
	}

	private final List<ResponsiveImageCandidate> _responsiveImageCandidates =
		Arrays.asList(
			ResponsiveImageCandidate.builder(
				RandomTestUtil.randomString()
			).width(
				RandomTestUtil.randomInt()
			).build(),
			ResponsiveImageCandidate.builder(
				RandomTestUtil.randomString()
			).width(
				RandomTestUtil.randomInt()
			).build());

}