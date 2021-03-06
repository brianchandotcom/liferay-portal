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

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.segments.asah.connector.internal.client.model.DXPVariantSettings;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author Sarai Díaz
 */
@RunWith(MockitoJUnitRunner.class)
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