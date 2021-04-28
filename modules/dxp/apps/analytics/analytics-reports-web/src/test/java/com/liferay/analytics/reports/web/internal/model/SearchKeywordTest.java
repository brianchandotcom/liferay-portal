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