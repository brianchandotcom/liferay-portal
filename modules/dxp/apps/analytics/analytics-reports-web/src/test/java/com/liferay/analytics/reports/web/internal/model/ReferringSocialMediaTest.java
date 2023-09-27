/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Collections;
import java.util.Enumeration;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class ReferringSocialMediaTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		ReferringSocialMedia referringSocialMedia = new ReferringSocialMedia(
			"twitter", RandomTestUtil.randomInt());

		Assert.assertEquals(
			JSONUtil.put(
				"name", "twitter"
			).put(
				"title", "Twitter"
			).put(
				"trafficAmount", referringSocialMedia.getTrafficAmount()
			).toString(),
			String.valueOf(
				referringSocialMedia.toJSONObject(_getResourceBundle())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToJSONObjectWithNullName() {
		new ReferringSocialMedia(null, RandomTestUtil.randomInt());
	}

	@Test
	public void testToJSONObjectWithOtherName() {
		ReferringSocialMedia referringSocialMedia = new ReferringSocialMedia(
			"other", RandomTestUtil.randomInt());

		Assert.assertEquals(
			JSONUtil.put(
				"name", "other"
			).put(
				"title", "Other"
			).put(
				"trafficAmount", referringSocialMedia.getTrafficAmount()
			).toString(),
			String.valueOf(
				referringSocialMedia.toJSONObject(_getResourceBundle())));
	}

	private ResourceBundle _getResourceBundle() {
		return new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return Collections.enumeration(
					Collections.singletonList("other"));
			}

			@Override
			protected Object handleGetObject(String key) {
				return "Other";
			}

		};
	}

}