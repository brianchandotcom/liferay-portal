/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.verify;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;
import com.liferay.portlet.ratings.model.RatingsEntry;
import com.liferay.portlet.ratings.model.RatingsStats;
import com.liferay.portlet.ratings.service.RatingsEntryLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Alberto Chaparro
 */
public class VerifyRatingsTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_previousCacheStatus = CacheRegistryUtil.isActive();

		CacheRegistryUtil.setActive(false);

		_ratingsStats = RatingsTestUtil.addStats(
			_TEST_CLASS_NAME, _TEST_CLASS_PK);
	}

	@After
	public void tearDown() throws Exception {
		List<RatingsEntry> ratingEntries =
			RatingsEntryLocalServiceUtil.getEntries(
				_TEST_CLASS_NAME, _TEST_CLASS_PK);

		for (RatingsEntry ratingsEntry : ratingEntries) {
			RatingsEntryLocalServiceUtil.deleteRatingsEntry(ratingsEntry);
		}

		CacheRegistryUtil.setActive(_previousCacheStatus);
	}

	@Test
	public void testVerifyStatsWithEntries() throws Exception {
		int totalEntries = RandomTestUtil.randomInt(1, 10);
		double totalScore = 0.0;

		for (int i = 0; i < totalEntries; i++) {
			totalScore += addVote();
		}

		doVerify();

		RatingsStats ratingStats = RatingsStatsLocalServiceUtil.getStats(
			_TEST_CLASS_NAME, _TEST_CLASS_PK);

		Assert.assertEquals(totalEntries, ratingStats.getTotalEntries());
		AssertUtils.assertEquals(totalScore, ratingStats.getTotalScore());
		AssertUtils.assertEquals(
			totalScore / totalEntries, ratingStats.getAverageScore());
	}

	@Test
	public void testVerifyStatsWithNoEntries() throws Exception {
		doVerify();

		RatingsStats ratingStats = RatingsStatsLocalServiceUtil.getStats(
			_TEST_CLASS_NAME, _TEST_CLASS_PK);

		Assert.assertEquals(0, ratingStats.getTotalEntries());
		AssertUtils.assertEquals(0.0, ratingStats.getTotalScore());
		AssertUtils.assertEquals(0.0, ratingStats.getAverageScore());
	}

	protected double addVote() throws Exception {
		double score = RandomTestUtil.randomDouble();
		User user = UserTestUtil.addUser();

		RatingsTestUtil.addEntry(
			_TEST_CLASS_NAME, _TEST_CLASS_PK, score, user.getUserId());

		return score;
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyRatings();
	}

	private static final String _TEST_CLASS_NAME = "testClassName";

	private static final int _TEST_CLASS_PK = 1;

	private boolean _previousCacheStatus;

	@DeleteAfterTestRun
	private RatingsStats _ratingsStats;

}