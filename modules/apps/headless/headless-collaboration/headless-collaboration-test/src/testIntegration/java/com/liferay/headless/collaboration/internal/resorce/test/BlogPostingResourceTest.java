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

package com.liferay.headless.collaboration.internal.resorce.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.headless.collaboration.dto.BlogPosting;
import com.liferay.headless.collaboration.resource.BlogPostingResource;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;
import com.liferay.portal.vulcan.dto.Page;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Víctor Galán
 */
@RunWith(Arquillian.class)
public class BlogPostingResourceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testGetBlogPostingPage() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		Page<BlogPosting> blogPostingPage =
			_blogPostingResource.getContentSpaceBlogPostingPage(
				_group.getGroupId(), PaginationRequest.of(20, 1));

		List<BlogPosting> blogPostings =
			(List<BlogPosting>)blogPostingPage.getItems();

		BlogPosting blogPosting = blogPostings.get(0);

		Assert.assertEquals(
			blogsEntry.getSubtitle(), blogPosting.getAlternativeHeadline());
		Assert.assertEquals(
			blogsEntry.getContent(), blogPosting.getArticleBody());
		Assert.assertEquals(
			blogsEntry.getCoverImageCaption(), blogPosting.getCaption());
		Assert.assertEquals(
			blogsEntry.getDescription(), blogPosting.getDescription());
		Assert.assertEquals((long)blogPosting.getId(), blogsEntry.getEntryId());
		Assert.assertEquals(blogsEntry.getTitle(), blogPosting.getHeadline());
		Assert.assertEquals(
			blogsEntry.getUrlTitle(), blogPosting.getFriendlyUrlPath());
	}

	@Test
	public void testGetBlogPostingPageWith1Pending() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		_blogsEntryLocalService.updateStatus(
			serviceContext.getUserId(), blogsEntry.getEntryId(),
			WorkflowConstants.STATUS_PENDING, serviceContext,
			Collections.emptyMap());

		Page<BlogPosting> blogPostingPage =
			_blogPostingResource.getContentSpaceBlogPostingPage(
				_group.getGroupId(), PaginationRequest.of(20, 1));

		Assert.assertEquals(0, blogPostingPage.getTotalCount());
	}

	@Test
	public void testGetBlogPostingPageWith1Scheduled() throws Exception {
		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		_addBlogEntry(displayDate, serviceContext);

		Page<BlogPosting> blogPostingPage =
			_blogPostingResource.getContentSpaceBlogPostingPage(
				_group.getGroupId(), PaginationRequest.of(20, 1));

		Assert.assertEquals(0, blogPostingPage.getTotalCount());
	}

	private BlogsEntry _addBlogEntry(
			Date displayDate, ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), displayDate, true, true, new String[0],
			StringUtil.randomString(), null, null, serviceContext);
	}

	@Inject
	private BlogPostingResource _blogPostingResource;

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

}