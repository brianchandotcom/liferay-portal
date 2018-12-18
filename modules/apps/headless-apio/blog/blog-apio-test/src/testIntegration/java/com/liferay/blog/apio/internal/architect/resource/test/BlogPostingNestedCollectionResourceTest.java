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

package com.liferay.blog.apio.internal.architect.resource.test;

import com.liferay.apio.architect.pagination.PageItems;
import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blog.apio.architect.model.BlogPosting;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalService;
import com.liferay.portal.apio.exception.ValidationException;
import com.liferay.portal.apio.test.util.PaginationRequest;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.context.ContextUserReplace;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerTestRule;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.assertj.core.api.AbstractThrowableAssert;
import org.assertj.core.api.Assertions;

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
public class BlogPostingNestedCollectionResourceTest
	extends BaseBlogPostingNestedCollectionResourceTest {

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
	public void testAddBlogsEntry() throws Exception {
		Date dateNow = new Date();
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		BlogPosting blogPosting = new BlogPostingImpl(
			"alternativeHeadline", "articleBody", "caption", Collections.emptyList(),
			-1L, dateNow, dateNow, dateNow, "description", "friendlyurlpath",
			"headline", -1L, Collections.emptyList());

		BlogsEntry blogsEntry = addBlogsEntry(
			_group.getGroupId(), blogPosting, user);

		Assert.assertEquals(blogPosting.getAlternativeHeadline(), blogsEntry.getSubtitle());
		Assert.assertEquals(blogPosting.getArticleBody(), blogsEntry.getContent());
		Assert.assertEquals(blogPosting.getCaption(), blogsEntry.getCoverImageCaption());
		Assert.assertEquals(dateNow, blogsEntry.getCreateDate());
		Assert.assertEquals(dateNow, blogsEntry.getDisplayDate());
		Assert.assertEquals(dateNow, blogsEntry.getModifiedDate());
		Assert.assertEquals(blogPosting.getDescription(), blogsEntry.getDescription());
		Assert.assertEquals(blogPosting.getFriendlyURLPath(), blogsEntry.getUrlTitle());
		Assert.assertEquals(blogPosting.getHeadline(), blogsEntry.getTitle());
	}

	@Test
	public void testAddBlogsEntryThrowsValidationException() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = _userLocalService.getUser(serviceContext.getUserId());

		String friendlyURLPath = "friendlyurlpath";

		BlogPosting blogPosting1 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(), 0L,
			new Date(), new Date(), new Date(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		addBlogsEntry(_group.getGroupId(), blogPosting1, user);

		BlogPosting blogPosting2 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(), 0L,
			new Date(), new Date(), new Date(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		AbstractThrowableAssert exception = Assertions.assertThatThrownBy(
			() -> addBlogsEntry(_group.getGroupId(), blogPosting2, user)
		).isInstanceOf(
			ValidationException.class
		);

		exception.hasMessage("Duplicate friendly URL");
	}

	@Test
	public void testGetPageItems() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		PageItems<BlogsEntry> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(1, pageItems.getTotalCount());

		Collection<BlogsEntry> blogEntries = pageItems.getItems();

		Assert.assertTrue(
			"Journal articles: " + blogEntries,
			blogEntries.contains(blogsEntry));
	}

	@Test
	public void testGetPageItemsWith1Pending() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		BlogsEntry blogsEntry = _addBlogEntry(new Date(), serviceContext);

		_blogsEntryLocalService.updateStatus(
			serviceContext.getUserId(), blogsEntry.getEntryId(),
			WorkflowConstants.STATUS_PENDING, serviceContext,
			Collections.emptyMap());

		PageItems<BlogsEntry> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWith1Scheduled() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		_addBlogEntry(displayDate, serviceContext);

		PageItems<BlogsEntry> pageItems = getPageItems(
			PaginationRequest.of(10, 1), _group.getGroupId());

		Assert.assertEquals(0, pageItems.getTotalCount());
	}

	@Test
	public void testGetPageItemsWithPermissions() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setAddGuestPermissions(false);
		serviceContext.setAddGroupPermissions(false);

		_addBlogEntry(new Date(), serviceContext);

		User user = UserTestUtil.addUser();

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			PageItems<BlogsEntry> pageItems = getPageItems(
				PaginationRequest.of(10, 1), _group.getGroupId());

			Assert.assertEquals(0, pageItems.getTotalCount());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
	}

	@Test
	public void testUpdateBlogsEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = UserLocalServiceUtil.getUser(serviceContext.getUserId());

		String alternativeHeadline = "alternativeHeadline";
		String articleBody = "articleBody";
		String caption = "caption";
		Date dateNow = new Date();
		String description = "description";
		String friendlyURLPath = "friendlyurlpath";
		String headline = "headline";

		BlogPosting blogPosting = new BlogPostingImpl(
			alternativeHeadline, articleBody, caption, Collections.emptyList(),
			-1L, dateNow, dateNow, dateNow, description, friendlyURLPath,
			headline, -1L, Collections.emptyList());

		BlogsEntry blogsEntry = addBlogsEntry(
			_group.getGroupId(), blogPosting, user);

		String updatedHeadline = "updatedHeadline";

		BlogPosting updatedBlogPosting = new BlogPostingImpl(
			alternativeHeadline, articleBody, caption, Collections.emptyList(),
			-1L, dateNow, dateNow, dateNow, description, friendlyURLPath,
			updatedHeadline, -1L, Collections.emptyList());

		blogsEntry = updateBlogsEntry(
			blogsEntry.getEntryId(), updatedBlogPosting, user);

		Assert.assertEquals(alternativeHeadline, blogsEntry.getSubtitle());
		Assert.assertEquals(articleBody, blogsEntry.getContent());
		Assert.assertEquals(caption, blogsEntry.getCoverImageCaption());
		Assert.assertEquals(dateNow, blogsEntry.getCreateDate());
		Assert.assertEquals(dateNow, blogsEntry.getDisplayDate());
		Assert.assertEquals(dateNow, blogsEntry.getModifiedDate());
		Assert.assertEquals(description, blogsEntry.getDescription());
		Assert.assertEquals(friendlyURLPath, blogsEntry.getUrlTitle());
		Assert.assertEquals(updatedHeadline, blogsEntry.getTitle());
	}

	private BlogsEntry _addBlogEntry(
			Date displayDate, ServiceContext serviceContext)
		throws PortalException {

		return _blogsEntryLocalService.addEntry(
			serviceContext.getUserId(), "headline", "alternativeHeadline",
			"/friendly/url/path", "some description", "article body",
			displayDate, true, true, new String[0], "image caption", null, null,
			serviceContext);
	}

	@Inject
	private BlogsEntryLocalService _blogsEntryLocalService;

	@DeleteAfterTestRun
	private Group _group;

	@Inject
	private UserLocalService _userLocalService;

	private static class BlogPostingImpl implements BlogPosting {

		public BlogPostingImpl(
			String alternativeHeadline, String articleBody, String caption,
			List<Long> categories, long creatorId, Date dateCreated,
			Date dateModified, Date datePublished, String description,
			String friendlyURLPath, String headline, long imageId,
			List<String> keywords) {

			_alternativeHeadline = alternativeHeadline;
			_articleBody = articleBody;
			_caption = caption;
			_categories = categories;
			_creatorId = creatorId;
			_dateCreated = dateCreated;
			_dateModified = dateModified;
			_datePublished = datePublished;
			_description = description;
			_friendlyURLPath = friendlyURLPath;
			_headline = headline;
			_imageId = imageId;
			_keywords = keywords;
		}

		@Override
		public String getAlternativeHeadline() {
			return _alternativeHeadline;
		}

		@Override
		public String getArticleBody() {
			return _articleBody;
		}

		@Override
		public String getCaption() {
			return _caption;
		}

		@Override
		public List<Long> getCategories() {
			return _categories;
		}

		@Override
		public Long getCreatorId() {
			return _creatorId;
		}

		@Override
		public Date getDateCreated() {
			return _dateCreated;
		}

		@Override
		public Date getDateModified() {
			return _dateModified;
		}

		@Override
		public Date getDatePublished() {
			return _datePublished;
		}

		@Override
		public String getDescription() {
			return _description;
		}

		@Override
		public String getFriendlyURLPath() {
			return _friendlyURLPath;
		}

		@Override
		public String getHeadline() {
			return _headline;
		}

		@Override
		public Long getImageId() {
			return _imageId;
		}

		@Override
		public List<String> getKeywords() {
			return _keywords;
		}

		private final String _alternativeHeadline;
		private final String _articleBody;
		private final String _caption;
		private final List<Long> _categories;
		private final long _creatorId;
		private final Date _dateCreated;
		private final Date _dateModified;
		private final Date _datePublished;
		private final String _description;
		private final String _friendlyURLPath;
		private final String _headline;
		private final long _imageId;
		private final List<String> _keywords;

	}

}