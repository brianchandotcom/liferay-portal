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

package com.liferay.ratings.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.model.RatingsStats;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;
import com.liferay.ratings.kernel.service.RatingsStatsLocalServiceUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@RunWith(Arquillian.class)
public class RatingsStatsLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addUser();
		_user2 = UserTestUtil.addUser();
		_user3 = UserTestUtil.addUser();
	}

	@Test
	public void testAverageScoreWithDifferentMultipleRatingsEntries()
		throws Exception {

		String className1 = StringUtil.randomString();
		long classPK1 = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className1, classPK1, 1, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), className1, classPK1, 0.4, serviceContext);

		String className2 = StringUtil.randomString();
		long classPK2 = RandomTestUtil.randomLong();

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), className2, classPK2, 0.2, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className1, classPK1);

		Assert.assertEquals(1.4 / 2, ratingsStats.getAverageScore(), 0.001);

		ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className2, classPK2);

		Assert.assertEquals(0.2 / 1, ratingsStats.getAverageScore(), 0.001);
	}

	@Test
	public void testAverageScoreWithMultipleRatingsEntries() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className, classPK, 1, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), className, classPK, 0.4, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), className, classPK, 0.2, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className, classPK);

		Assert.assertEquals(1.6 / 3, ratingsStats.getAverageScore(), 0.001);
	}

	@Test
	public void testRatingsStatsDeletedWhenNoRatingsEntries() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className, classPK, 1, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className, classPK);

		RatingsEntryLocalServiceUtil.deleteEntry(
			_user1.getUserId(), className, classPK);

		ratingsStats = RatingsStatsLocalServiceUtil.fetchStats(
			className, classPK);

		Assert.assertNull(ratingsStats);
	}

	@Test
	public void testTotalEntriesWithMultipleRatingsEntries() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className, classPK, 1, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), className, classPK, 0.4, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className, classPK);

		Assert.assertEquals(2, ratingsStats.getTotalEntries());
	}

	@Test
	public void testTotalScoreWithDifferentMultipleRatingsEntries()
		throws Exception {

		String className1 = StringUtil.randomString();
		long classPK1 = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className1, classPK1, 1, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), className1, classPK1, 0.4, serviceContext);

		String className2 = StringUtil.randomString();
		long classPK2 = RandomTestUtil.randomLong();

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), className2, classPK2, 0.2, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className1, classPK1);

		Assert.assertEquals(1.4, ratingsStats.getTotalScore(), 0.001);

		ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className2, classPK2);

		Assert.assertEquals(0.2, ratingsStats.getTotalScore(), 0.001);
	}

	@Test
	public void testTotalScoreWithMultipleRatingsEntries() throws Exception {
		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		RatingsEntryLocalServiceUtil.updateEntry(
			_user1.getUserId(), className, classPK, 1, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), className, classPK, 0.4, serviceContext);
		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), className, classPK, 0.2, serviceContext);

		RatingsStats ratingsStats = RatingsStatsLocalServiceUtil.getStats(
			className, classPK);

		Assert.assertEquals(1.6, ratingsStats.getTotalScore(), 0.001);
	}

	@DeleteAfterTestRun
	private Group _group;

	@DeleteAfterTestRun
	private User _user1;

	@DeleteAfterTestRun
	private User _user2;

	@DeleteAfterTestRun
	private User _user3;

}