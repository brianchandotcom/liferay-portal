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

package com.liferay.ratings.verify.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.verify.VerifyProcess;
import com.liferay.portal.verify.VerifyRatings;
import com.liferay.portal.verify.test.BaseVerifyProcessTestCase;
import com.liferay.portlet.ratings.util.test.RatingsTestUtil;
import com.liferay.ratings.kernel.model.RatingsEntry;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alberto Chaparro
 */
@RunWith(Arquillian.class)
public class VerifyRatingsTest extends BaseVerifyProcessTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_active = CacheRegistryUtil.isActive();

		CacheRegistryUtil.setActive(false);

		_ratingsStats = RatingsTestUtil.addStats(_CLASS_NAME, _CLASS_PK);
	}

	@After
	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		List<RatingsEntry> ratingEntries =
			RatingsEntryLocalServiceUtil.getEntries(_CLASS_NAME, _CLASS_PK);

		for (RatingsEntry ratingsEntry : ratingEntries) {
			RatingsEntryLocalServiceUtil.deleteRatingsEntry(ratingsEntry);
		}

		CacheRegistryUtil.setActive(_active);
	}

	@Test
	public void testVerifyStatsWithEntries() throws Exception {
		int totalEntries = RandomTestUtil.randomInt(1, 10);
		double totalScore = 0.0;

		for (int i = 0; i < totalEntries; i++) {
			totalScore += addVote();
		}

		doVerify();

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			_CLASS_NAME, _CLASS_PK);

		Assert.assertEquals(totalEntries, ratingsStats.getTotalEntries());
		Assert.assertEquals(totalScore, ratingsStats.getTotalScore(), 0.0001);
		Assert.assertEquals(
			totalScore / totalEntries, ratingsStats.getAverageScore(), 0.0001);
	}

	@Test
	public void testVerifyStatsWithNoEntries() throws Exception {
		doVerify();

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			_CLASS_NAME, _CLASS_PK);

		Assert.assertEquals(0, ratingsStats.getTotalEntries());
		Assert.assertEquals(0.0, ratingsStats.getTotalScore(), 0.0001);
		Assert.assertEquals(0.0, ratingsStats.getAverageScore(), 0.0001);
	}

	protected double addVote() throws Exception {
		double score = RandomTestUtil.randomDouble();
		User user = UserTestUtil.addUser();

		_users.add(user);

		RatingsTestUtil.addEntry(
			_CLASS_NAME, _CLASS_PK, score, user.getUserId());

		return score;
	}

	@Override
	protected VerifyProcess getVerifyProcess() {
		return new VerifyRatings();
	}

	private static final String _CLASS_NAME = VerifyRatingsTest.class.getName();

	private static final int _CLASS_PK = 1;

	private boolean _active;

	@DeleteAfterTestRun
	private RatingsStats _ratingsStats;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}