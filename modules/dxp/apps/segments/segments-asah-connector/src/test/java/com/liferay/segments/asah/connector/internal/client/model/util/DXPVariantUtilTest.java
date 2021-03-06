/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariant;
import com.liferay.segments.model.SegmentsExperimentRel;

import java.util.Locale;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author David Arques
 */
@RunWith(MockitoJUnitRunner.class)
public class DXPVariantUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToDXPVariant() throws PortalException {
		Boolean control = RandomTestUtil.randomBoolean();
		Locale locale = LocaleUtil.ENGLISH;
		String segmentsExperienceKey = RandomTestUtil.randomString();
		String segmentsExperienceName = RandomTestUtil.randomString();
		double split = RandomTestUtil.randomDouble();

		SegmentsExperimentRel segmentsExperimentRel =
			_createSegmentsExperimentRel(
				control, locale, segmentsExperienceKey, segmentsExperienceName,
				split);

		DXPVariant dxpVariant = DXPVariantUtil.toDXPVariant(
			locale, segmentsExperimentRel);

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

	private SegmentsExperimentRel _createSegmentsExperimentRel(
			boolean control, Locale locale, String segmentsExperienceKey,
			String segmentsExperienceName, double split)
		throws PortalException {

		SegmentsExperimentRel segmentsExperimentRel = Mockito.mock(
			SegmentsExperimentRel.class);

		Mockito.doReturn(
			control
		).when(
			segmentsExperimentRel
		).isControl();

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

}