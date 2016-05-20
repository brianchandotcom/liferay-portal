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

package com.liferay.blogs.service.impl.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.kernel.model.BlogsEntry;
import com.liferay.blogs.kernel.service.BlogsEntryLocalService;
import com.liferay.blogs.kernel.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 * @author Christopher Kian
 */
@RunWith(Arquillian.class)
@Sync
public class BlogsEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE);

	@Test
	public void testAddDiscussion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), serviceContext);

		_blogsEntries.add(blogsEntry);

		long initialCommentsCount = CommentManagerUtil.getCommentsCount(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		CommentManagerUtil.addComment(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			StringUtil.randomString(),
			new IdentityServiceContextFunction(serviceContext));

		Assert.assertEquals(
			initialCommentsCount + 1,
			CommentManagerUtil.getCommentsCount(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	@Test
	public void testBlogsInOrder() throws Exception {
		int[][] testBlogs = new int[][]{
			new int[]{1, 0},
			new int[]{2, 1},
			new int[]{3, 2}};

		generateAndTestBlogsEntries(testBlogs);
	}

	@Test
	public void testBlogsInOrderDuplicateDisplayDates() throws Exception {
		int[][] testBlogs = new int[][]{
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{2, 2},
			new int[]{2, 3}};

		generateAndTestBlogsEntries(testBlogs);
	}

	@Test
	public void testDeleteDiscussion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), serviceContext);

		_blogsEntries.add(blogsEntry);

		Assert.assertTrue(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		CommentManagerUtil.deleteDiscussion(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Assert.assertFalse(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	@Test
	public void testGroupsOfDisplayDatesCreatedOutOfOrder() throws Exception {
		int[][] testBlogs = new int[][]{
			new int[]{1, 0},
			new int[]{1, 1},
			new int[]{1, 2},
			new int[]{3, 6},
			new int[]{3, 7},
			new int[]{3, 8},
			new int[]{2, 3},
			new int[]{2, 4},
			new int[]{2, 5}};

		generateAndTestBlogsEntries(testBlogs);
	}

	@Test
	public void testMiddleBlogHasMostRecentDisplayDate() throws Exception {
		int[][] testBlogs = new int[][]{
			new int[]{1, 0},
			new int[]{3, 2},
			new int[]{2, 1}};

		generateAndTestBlogsEntries(testBlogs);
	}

	protected void generateAndTestBlogsEntries(int[][] displayDateOrder)
		throws Exception {

		BlogsEntryLocalService blogsEntryLocalService =
			(BlogsEntryLocalService) PortalBeanLocatorUtil.locate(
				BlogsEntryLocalService.class.getName());

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				TestPropsValues.getGroupId());

		BlogsEntry[] blogsEntries = new BlogsEntry[displayDateOrder.length];

		for (int i = 0; i < displayDateOrder.length; i++) {
			int displayDate = displayDateOrder[i][0];
			int order = displayDateOrder[i][1];

			blogsEntries[order] = BlogsEntryLocalServiceUtil.addEntry(
				TestPropsValues.getUserId(), StringUtil.randomString(),
				StringUtil.randomString(), new Date(displayDate * Time.DAY),
				serviceContext);

			_blogsEntries.add(blogsEntries[order]);
		}

		for (int i = 0; i < blogsEntries.length; i++) {
			long entryId = blogsEntries[i].getEntryId();

			BlogsEntry[] prevAndNextValues =
				blogsEntryLocalService.getEntriesPrevAndNext(entryId);

			if (i > 0) {
				Assert.assertEquals(
					prevAndNextValues[0].getEntryId(),
					blogsEntries[i - 1].getEntryId());
			}
			else {
				Assert.assertNull(prevAndNextValues[0]);
			}

			if (i < (blogsEntries.length - 1)) {
				Assert.assertEquals(
					prevAndNextValues[2].getEntryId(),
					blogsEntries[i + 1].getEntryId());
			}
			else {
				Assert.assertNull(prevAndNextValues[2]);
			}
		}
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

}