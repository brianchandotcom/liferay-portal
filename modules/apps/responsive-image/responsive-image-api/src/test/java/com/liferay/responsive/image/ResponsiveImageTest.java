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
public class ResponsiveImageTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetSourcesIgnoresLaterChangesToTheGivenList() {
		List<ResponsiveImageSource> responsiveImageSources = new ArrayList<>(
			Arrays.asList(_responsiveImageSource));

		ResponsiveImage responsiveImage = ResponsiveImage.builder(
		).sources(
			responsiveImageSources
		).build();

		responsiveImageSources.clear();

		Assert.assertEquals(
			Collections.singletonList(_responsiveImageSource),
			responsiveImage.getSources());
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testGetSourcesReturnsAnUnmodifiableList() {
		ResponsiveImage responsiveImage = ResponsiveImage.builder(
		).sources(
			Arrays.asList(_responsiveImageSource)
		).build();

		List<ResponsiveImageSource> responsiveImageSources =
			responsiveImage.getSources();

		responsiveImageSources.add(_responsiveImageSource);
	}

	private final ResponsiveImageSource _responsiveImageSource =
		ResponsiveImageSource.builder(
		).candidates(
			Collections.<ResponsiveImageCandidate>emptyList()
		).query(
			null
		).sizes(
			RandomTestUtil.randomString()
		).build();

}