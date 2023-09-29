/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariant;
import com.liferay.segments.model.SegmentsExperience;
import com.liferay.segments.model.SegmentsExperimentRel;
import com.liferay.segments.service.SegmentsExperienceLocalService;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.Mockito;

/**
 * @author David Arques
 */
public class DXPVariantUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToDXPVariant() throws PortalException {
		Boolean control = RandomTestUtil.randomBoolean();
		Locale locale = LocaleUtil.ENGLISH;
		long segmentsExperienceId = RandomTestUtil.randomLong();
		String segmentsExperienceKey = RandomTestUtil.randomString();
		String segmentsExperienceName = RandomTestUtil.randomString();
		double split = RandomTestUtil.randomDouble();

		SegmentsExperimentRel segmentsExperimentRel =
			_createSegmentsExperimentRel(
				control, locale, segmentsExperienceId, segmentsExperienceKey,
				segmentsExperienceName, split);

		SegmentsExperience segmentsExperience = _createSegmentsExperience(
			segmentsExperienceKey);

		Mockito.when(
			_segmentsExperienceLocalService.getSegmentsExperience(
				segmentsExperienceId)
		).thenReturn(
			segmentsExperience
		);

		DXPVariant dxpVariant = DXPVariantUtil.toDXPVariant(
			locale, _segmentsExperienceLocalService, segmentsExperimentRel);

		Assert.assertEquals(Integer.valueOf(0), dxpVariant.getChanges());
		Assert.assertEquals(
			segmentsExperimentRel.isControl(), dxpVariant.isControl());
		Assert.assertEquals(
			segmentsExperimentRel.getSegmentsExperienceKey(),
			dxpVariant.getDXPVariantId());
		Assert.assertEquals(
			segmentsExperimentRel.getName(locale),
			dxpVariant.getDXPVariantName());
		Assert.assertEquals(
			segmentsExperimentRel.getSplit(),
			dxpVariant.getTrafficSplit() / 100, 0.001);
	}

	private SegmentsExperience _createSegmentsExperience(
		String segmentsExperienceKey) {

		SegmentsExperience segmentsExperience = Mockito.mock(
			SegmentsExperience.class);

		Mockito.doReturn(
			segmentsExperienceKey
		).when(
			segmentsExperience
		).getSegmentsExperienceKey();

		Mockito.doReturn(
			new UnicodeProperties()
		).when(
			segmentsExperience
		).getTypeSettingsUnicodeProperties();

		return segmentsExperience;
	}

	private SegmentsExperimentRel _createSegmentsExperimentRel(
			boolean control, Locale locale, long segmentsExperienceId,
			String segmentsExperienceKey, String segmentsExperienceName,
			double split)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel = Mockito.mock(
			SegmentsExperimentRel.class);

		Mockito.doReturn(
			control
		).when(
			segmentsExperimentRel
		).isControl();

		Mockito.doReturn(
			segmentsExperienceId
		).when(
			segmentsExperimentRel
		).getSegmentsExperienceId();

		Mockito.doReturn(
			segmentsExperienceKey
		).when(
			segmentsExperimentRel
		).getSegmentsExperienceKey();

		Mockito.doReturn(
			segmentsExperienceName
		).when(
			segmentsExperimentRel
		).getName(
			locale
		);

		Mockito.doReturn(
			split
		).when(
			segmentsExperimentRel
		).getSplit();

		return segmentsExperimentRel;
	}

	private final SegmentsExperienceLocalService
		_segmentsExperienceLocalService = Mockito.mock(
			SegmentsExperienceLocalService.class);

}