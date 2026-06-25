/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.model.SegmentsExperience;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author Lourdes Fernández Besada
 */
public class SegmentsExperienceUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testIsActive() {
		SegmentsExperience defaultSegmentsExperience = _getSegmentsExperience(
			null, null);

		Assert.assertTrue(
			SegmentsExperienceUtil.isActive(
				defaultSegmentsExperience,
				Collections.singletonList(defaultSegmentsExperience)));

		String segmentsEntryERC = RandomTestUtil.randomString();
		String segmentsEntryScopeERC = RandomTestUtil.randomString();

		SegmentsExperience activeSegmentsExperience = _getSegmentsExperience(
			segmentsEntryERC, segmentsEntryScopeERC);

		List<SegmentsExperience> segmentsExperiences = Arrays.asList(
			activeSegmentsExperience, defaultSegmentsExperience);

		Assert.assertTrue(
			SegmentsExperienceUtil.isActive(
				activeSegmentsExperience, segmentsExperiences));
		Assert.assertTrue(
			SegmentsExperienceUtil.isActive(
				defaultSegmentsExperience, segmentsExperiences));

		SegmentsExperience winnerSegmentsExperience = _getSegmentsExperience(
			segmentsEntryERC, segmentsEntryScopeERC);
		SegmentsExperience loserSegmentsExperience = _getSegmentsExperience(
			segmentsEntryERC, segmentsEntryScopeERC);

		segmentsExperiences = Arrays.asList(
			winnerSegmentsExperience, loserSegmentsExperience,
			defaultSegmentsExperience);

		Assert.assertTrue(
			SegmentsExperienceUtil.isActive(
				winnerSegmentsExperience, segmentsExperiences));
		Assert.assertFalse(
			SegmentsExperienceUtil.isActive(
				loserSegmentsExperience, segmentsExperiences));
		Assert.assertTrue(
			SegmentsExperienceUtil.isActive(
				defaultSegmentsExperience, segmentsExperiences));
	}

	private SegmentsExperience _getSegmentsExperience(
		String segmentsEntryERC, String segmentsEntryScopeERC) {

		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.when(
			segmentsExperience.getSegmentsEntryERC()
		).thenReturn(
			segmentsEntryERC
		);

		Mockito.when(
			segmentsExperience.getSegmentsEntryScopeERC()
		).thenReturn(
			segmentsEntryScopeERC
		);

		Mockito.when(
			segmentsExperience.getSegmentsExperienceId()
		).thenReturn(
			RandomTestUtil.randomLong()
		);

		Mockito.when(
			segmentsExperience.hasDefaultSegmentsEntry()
		).thenReturn(
			Validator.isNull(segmentsEntryERC)
		);

		return segmentsExperience;
	}

}