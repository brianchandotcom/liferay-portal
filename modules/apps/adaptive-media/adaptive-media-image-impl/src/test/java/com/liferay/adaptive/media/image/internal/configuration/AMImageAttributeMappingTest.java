/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.adaptive.media.image.internal.configuration;

import com.liferay.adaptive.media.image.processor.AMImageAttribute;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Adolfo Pérez
 */
public class AMImageAttributeMappingTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testCreateFromEmptyMap() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(Collections.emptyMap());

		Assert.assertNull(
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertNull(
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenCreatingFromNullMap() {
		AMImageAttributeMapping.fromProperties(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testFailWhenGettingValueOfNullAttribute() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "100",
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
					"200"));

		amImageAttributeMapping.getValue(null);
	}

	@Test
	public void testIgnoreUnknownAttributes() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray("foo", RandomTestUtil.randomString()));

		Assert.assertNull(
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertNull(
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testValidAttributes() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(), "100",
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH.getName(),
					"200"));

		Assert.assertEquals(
			Integer.valueOf(100),
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertEquals(
			Integer.valueOf(200),
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

	@Test
	public void testValidSingleAttribute() {
		AMImageAttributeMapping amImageAttributeMapping =
			AMImageAttributeMapping.fromProperties(
				MapUtil.fromArray(
					AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT.getName(),
					"100"));

		Assert.assertEquals(
			Integer.valueOf(100),
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_HEIGHT));
		Assert.assertNull(
			amImageAttributeMapping.getValue(
				AMImageAttribute.AM_IMAGE_ATTRIBUTE_WIDTH));
	}

}