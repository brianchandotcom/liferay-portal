/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.segments.model.impl;

import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.UnicodePropertiesBuilder;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Riccardo Ferrari
 */
public class SegmentsExperimentImplTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetTypeSettings() {
		SegmentsExperimentImpl segmentsExperimentImpl =
			new SegmentsExperimentImpl();

		UnicodeProperties unicodeProperties = UnicodePropertiesBuilder.put(
			"test-property-1", RandomTestUtil.randomString()
		).put(
			"test-property-2", RandomTestUtil.randomString()
		).build();

		segmentsExperimentImpl.setTypeSettings(unicodeProperties.toString());

		UnicodeProperties typeSettingsUnicodeProperties =
			segmentsExperimentImpl.getTypeSettingsProperties();

		Assert.assertEquals(
			unicodeProperties.getProperty("test-property-1"),
			typeSettingsUnicodeProperties.getProperty("test-property-1"));
		Assert.assertEquals(
			unicodeProperties.getProperty("test-property-2"),
			typeSettingsUnicodeProperties.getProperty("test-property-2"));

		unicodeProperties.put("test-property-1", RandomTestUtil.randomString());
		unicodeProperties.put("test-property-2", RandomTestUtil.randomString());
		unicodeProperties.put("test-property-3", RandomTestUtil.randomString());

		segmentsExperimentImpl.setTypeSettings(unicodeProperties.toString());

		typeSettingsUnicodeProperties =
			segmentsExperimentImpl.getTypeSettingsProperties();

		Assert.assertEquals(
			unicodeProperties.getProperty("test-property-1"),
			typeSettingsUnicodeProperties.getProperty("test-property-1"));
		Assert.assertEquals(
			unicodeProperties.getProperty("test-property-2"),
			typeSettingsUnicodeProperties.getProperty("test-property-2"));
		Assert.assertEquals(
			unicodeProperties.getProperty("test-property-3"),
			typeSettingsUnicodeProperties.getProperty("test-property-3"));
	}

}