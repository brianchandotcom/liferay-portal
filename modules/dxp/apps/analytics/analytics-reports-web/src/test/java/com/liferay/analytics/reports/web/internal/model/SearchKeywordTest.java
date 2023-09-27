/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.analytics.reports.web.internal.model;

import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author David Arques
 */
public class SearchKeywordTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testToJSONObject() {
		SearchKeyword searchKeyword = new SearchKeyword(
			RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomInt(), RandomTestUtil.randomInt());

		Assert.assertEquals(
			JSONUtil.put(
				"keyword", searchKeyword.getKeyword()
			).put(
				"position", searchKeyword.getPosition()
			).put(
				"searchVolume", searchKeyword.getSearchVolume()
			).put(
				"traffic", Math.toIntExact(searchKeyword.getTraffic())
			).toString(),
			String.valueOf(searchKeyword.toJSONObject()));
	}

	@Test(expected = ArithmeticException.class)
	public void testToJSONObjectWithLongTraffic() {
		SearchKeyword searchKeyword = new SearchKeyword(
			RandomTestUtil.randomString(), RandomTestUtil.randomInt(),
			RandomTestUtil.randomInt(), Long.MAX_VALUE);

		searchKeyword.toJSONObject();
	}

}