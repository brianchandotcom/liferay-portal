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

package com.liferay.portlet.ratings.service;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.ratings.model.RatingsStats;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class RatingsStatsLocalServiceTest {

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String className1 = StringUtil.randomString();
		long classPK1 = RandomTestUtil.randomLong();

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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

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
	public void testTotalEntriesWithMultipleRatingsEntries() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String className1 = StringUtil.randomString();
		long classPK1 = RandomTestUtil.randomLong();

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
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		String className = StringUtil.randomString();
		long classPK = RandomTestUtil.randomLong();

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