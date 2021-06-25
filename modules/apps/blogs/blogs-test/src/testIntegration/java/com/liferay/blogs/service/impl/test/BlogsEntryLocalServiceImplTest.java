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
import com.liferay.blogs.exception.DuplicateEntryExternalReferenceCodeException;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.service.IdentityServiceContextFunction;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
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
 */
@RunWith(Arquillian.class)
public class BlogsEntryLocalServiceImplTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test(expected = DuplicateEntryExternalReferenceCodeException.class)
	public void testAddBlogsEntryWithExistingExternalReferenceCode()
		throws Exception {

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(),
			ServiceContextTestUtil.getServiceContext());

		BlogsEntryLocalServiceUtil.addEntry(
			blogsEntry.getExternalReferenceCode(), blogsEntry.getUserId(),
			blogsEntry.getTitle(), blogsEntry.getSubtitle(),
			blogsEntry.getUrlTitle(), blogsEntry.getDescription(),
			blogsEntry.getContent(), blogsEntry.getDisplayDate(),
			blogsEntry.isAllowPingbacks(), blogsEntry.isAllowTrackbacks(),
			new String[] {blogsEntry.getTrackbacks()},
			blogsEntry.getCoverImageCaption(), null, null,
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testAddBlogsEntryWithExternalReferenceCode() throws Exception {
		String externalReferenceCode = RandomTestUtil.randomString();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			externalReferenceCode, TestPropsValues.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.nextDate(), false,
			false, new String[0], RandomTestUtil.randomString(), null, null,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			externalReferenceCode, blogsEntry.getExternalReferenceCode());
	}

	@Test
	public void testAddBlogsEntryWithoutExternalReferenceCode()
		throws Exception {

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			null, TestPropsValues.getUserId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			RandomTestUtil.nextDate(), false, false, new String[0],
			RandomTestUtil.randomString(), null, null,
			ServiceContextTestUtil.getServiceContext());

		Assert.assertEquals(
			blogsEntry.getExternalReferenceCode(),
			String.valueOf(blogsEntry.getEntryId()));
	}

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
	public void testDeleteDiscussion() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext();

		BlogsEntry blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), serviceContext);

		_blogsEntries.add(blogsEntry);

		CommentManagerUtil.addComment(
			TestPropsValues.getUserId(), TestPropsValues.getGroupId(),
			BlogsEntry.class.getName(), blogsEntry.getEntryId(),
			StringUtil.randomString(),
			new IdentityServiceContextFunction(serviceContext));

		Assert.assertTrue(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));

		CommentManagerUtil.deleteDiscussion(
			BlogsEntry.class.getName(), blogsEntry.getEntryId());

		Assert.assertFalse(
			CommentManagerUtil.hasDiscussion(
				BlogsEntry.class.getName(), blogsEntry.getEntryId()));
	}

	@DeleteAfterTestRun
	private final List<BlogsEntry> _blogsEntries = new ArrayList<>();

}