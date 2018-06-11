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

package com.liferay.blogs.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.model.BlogsStatsUser;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.blogs.service.BlogsStatsUserLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;

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
public class BlogsStatsUserLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_user1 = UserTestUtil.addOmniAdminUser();
		_user2 = UserTestUtil.addOmniAdminUser();
		_user3 = UserTestUtil.addOmniAdminUser();
	}

	@Test
	public void testAddFirstUserBlogsEntryAddsNewBlogsStatsUser()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(1, blogsStatsUser.getEntryCount());
		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalEntries());
		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalScore(), 0);
		Assert.assertEquals(0, blogsStatsUser.getRatingsAverageScore(), 0);
	}

	@Test
	public void testAddNewBlogsEntryIncrementBlogsStatsUserEntryEntryCount()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(2, blogsStatsUser.getEntryCount());
		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalEntries());
		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalScore(), 0);
		Assert.assertEquals(0, blogsStatsUser.getRatingsAverageScore(), 0);
	}

	@Test
	public void testAddNewRatingsEntryIncrementsBlogsStatsUserRatings()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(1, blogsStatsUser.getRatingsTotalEntries());
		Assert.assertEquals(1.0, blogsStatsUser.getRatingsAverageScore(), 0);
		Assert.assertEquals(1.0, blogsStatsUser.getRatingsTotalScore(), 0);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		blogsStatsUser = BlogsStatsUserLocalServiceUtil.fetchStatsUser(
			blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(2, blogsStatsUser.getRatingsTotalEntries());
		Assert.assertEquals(1.0, blogsStatsUser.getRatingsAverageScore(), 0);
		Assert.assertEquals(2.0, blogsStatsUser.getRatingsTotalScore(), 0);
	}

	@Test
	public void testDeleteRatingsEntryDecreasesBlogsStatsUserEntryEntryCount()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 0.2, null);

		RatingsEntryLocalServiceUtil.deleteEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(1, blogsStatsUser.getRatingsTotalEntries());

		RatingsEntryLocalServiceUtil.deleteEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		blogsStatsUser = BlogsStatsUserLocalServiceUtil.fetchStatsUser(
			blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalEntries());
	}

	@Test
	public void testDeleteRatingsEntryUpdatesBlogsStatsUserRatings()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 0.2, null);

		RatingsEntryLocalServiceUtil.deleteEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(0.2, blogsStatsUser.getRatingsAverageScore(), 0.01);
		Assert.assertEquals(0.2, blogsStatsUser.getRatingsTotalScore(), 0.01);

		RatingsEntryLocalServiceUtil.deleteEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId());

		blogsStatsUser = BlogsStatsUserLocalServiceUtil.fetchStatsUser(
			blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(0, blogsStatsUser.getRatingsAverageScore(), 0);
		Assert.assertEquals(0, blogsStatsUser.getRatingsTotalScore(), 0);
	}

	@Test
	public void testUpdateRatingsEntryDoesNotIncreaseBlogsStatsUserEntryEntryCount()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 0.2, null);

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(2, blogsStatsUser.getRatingsTotalEntries());
	}

	@Test
	public void testUpdateRatingsEntryUpdatesBlogsStatsUserRatings()
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group, _user1.getUserId());

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			_user1.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user3.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 1, null);

		RatingsEntryLocalServiceUtil.updateEntry(
			_user2.getUserId(), BlogsEntry.class.getName(),
			blogsEntry.getEntryId(), 0.2, null);

		BlogsStatsUser blogsStatsUser =
			BlogsStatsUserLocalServiceUtil.fetchStatsUser(
				blogsEntry.getGroupId(), blogsEntry.getUserId());

		Assert.assertEquals(0.6, blogsStatsUser.getRatingsAverageScore(), 0);
		Assert.assertEquals(1.2, blogsStatsUser.getRatingsTotalScore(), 0);
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