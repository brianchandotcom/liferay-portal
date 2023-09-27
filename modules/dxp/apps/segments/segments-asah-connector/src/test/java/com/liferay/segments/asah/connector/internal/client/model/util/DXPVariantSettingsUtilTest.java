/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.asah.connector.internal.client.model.util;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Sarai Díaz
 */
public class DXPVariantSettingsUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToDXPVariantSettingsWithControlVariant() {
		String controlSegmentsExperienceKey = RandomTestUtil.randomString();

		double split = RandomTestUtil.randomDouble();

		DXPVariantSettings dxpVariantSettings =
			DXPVariantSettingsUtil.toDXPVariantSettings(
				controlSegmentsExperienceKey, controlSegmentsExperienceKey,
				split);

		Assert.assertEquals(
			controlSegmentsExperienceKey, dxpVariantSettings.getDXPVariantId());
		Assert.assertEquals(
			split, dxpVariantSettings.getTrafficSplit() / 100, 0.001);
		Assert.assertTrue(dxpVariantSettings.isControl());
	}

	@Test
	public void testToDXPVariantSettingsWithNoncontrolVariant() {
		String controlSegmentsExperienceKey = RandomTestUtil.randomString();

		String segmentsExperienceKey = RandomTestUtil.randomString();

		double split = RandomTestUtil.randomDouble();

		DXPVariantSettings dxpVariantSettings =
			DXPVariantSettingsUtil.toDXPVariantSettings(
				controlSegmentsExperienceKey, segmentsExperienceKey, split);

		Assert.assertEquals(
			segmentsExperienceKey, dxpVariantSettings.getDXPVariantId());
		Assert.assertEquals(
			split, dxpVariantSettings.getTrafficSplit() / 100, 0.001);
		Assert.assertFalse(dxpVariantSettings.isControl());
	}

}