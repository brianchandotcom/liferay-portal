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

package com.liferay.portal.search.tuning.rankings.web.internal.display.context;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Wade Cao
 */
public class RankingEntryDisplayContextTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testSetterGetter() {
		_rankingEntryDisplayContext = new RankingEntryDisplayContext();

		_rankingEntryDisplayContext.setAliases("aliases");
		_rankingEntryDisplayContext.setHiddenResultsCount("hiddenResultsCount");
		_rankingEntryDisplayContext.setInactive(false);
		_rankingEntryDisplayContext.setIndex("index");
		_rankingEntryDisplayContext.setKeywords("keywords");
		_rankingEntryDisplayContext.setPinnedResultsCount("pinnedResultsCount");
		_rankingEntryDisplayContext.setUid("uid");

		Assert.assertEquals(
			"aliases", _rankingEntryDisplayContext.getAliases());
		Assert.assertFalse(_rankingEntryDisplayContext.getInactive());
		Assert.assertEquals(
			"hiddenResultsCount",
			_rankingEntryDisplayContext.getHiddenResultsCount());
		Assert.assertEquals("index", _rankingEntryDisplayContext.getIndex());
		Assert.assertEquals(
			"keywords", _rankingEntryDisplayContext.getKeywords());
		Assert.assertEquals(
			"pinnedResultsCount",
			_rankingEntryDisplayContext.getPinnedResultsCount());
		Assert.assertEquals("uid", _rankingEntryDisplayContext.getUid());
	}

	private RankingEntryDisplayContext _rankingEntryDisplayContext;

}