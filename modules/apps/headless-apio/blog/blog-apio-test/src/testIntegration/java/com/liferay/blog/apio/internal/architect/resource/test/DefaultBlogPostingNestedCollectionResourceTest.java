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

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
public class DefaultBlogPostingNestedCollectionResourceTest
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
		LocalDateTime localDateTime = LocalDateTime.of(
			2015, Month.JULY, 29, 19, 0);

		BlogPosting blogPosting = new BlogPostingImpl(
			"alternativeHeadline", "articleBody", "caption",
			Collections.emptyList(), localDateTime, "description",
			"friendlyurlpath", "headline", 0L, Collections.emptyList());

		BlogsEntry blogsEntry = addBlogsEntry(_group.getGroupId(), blogPosting);

		ZonedDateTime zonedDateTime = localDateTime.atZone(
			ZoneId.systemDefault());

		Date date = Date.from(zonedDateTime.toInstant());

		Assert.assertEquals(
			blogPosting.getArticleBody(), blogsEntry.getContent());
		Assert.assertEquals(
			blogPosting.getCaption(), blogsEntry.getCoverImageCaption());
		Assert.assertEquals(
			blogPosting.getDescription(), blogsEntry.getDescription());
		Assert.assertEquals(date, blogsEntry.getDisplayDate());
		Assert.assertEquals(
			blogPosting.getAlternativeHeadline(), blogsEntry.getSubtitle());
		Assert.assertEquals(blogPosting.getHeadline(), blogsEntry.getTitle());
		Assert.assertEquals(
			blogPosting.getFriendlyURLPath(), blogsEntry.getUrlTitle());
	}

	@Test
	public void testAddBlogsEntryThrowsValidationException() throws Exception {
		String friendlyURLPath = "friendlyurlpath";

		BlogPosting blogPosting1 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(),
			LocalDateTime.now(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		addBlogsEntry(_group.getGroupId(), blogPosting1);

		BlogPosting blogPosting2 = new BlogPostingImpl(
			RandomTestUtil.randomString(10), RandomTestUtil.randomString(10),
			RandomTestUtil.randomString(10), Collections.emptyList(),
			LocalDateTime.now(), RandomTestUtil.randomString(10),
			friendlyURLPath, RandomTestUtil.randomString(10), 0L,
			Collections.emptyList());

		AbstractThrowableAssert abstractThrowableAssert =
			Assertions.assertThatThrownBy(
				() -> addBlogsEntry(_group.getGroupId(), blogPosting2)
			).isInstanceOf(
				ValidationException.class
			);

		abstractThrowableAssert.hasMessage("Duplicate friendly URL");
	}

	@Test
	public void testAddBlogsEntryWithSpecificUser() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User currentUser = _userLocalService.getUser(
			serviceContext.getUserId());

		User user = UserTestUtil.addGroupAdminUser(_group);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		try (ContextUserReplace contextUserReplace = new ContextUserReplace(
				user, permissionChecker)) {

			String friendlyURLPath = "friendlyurlpath";

			BlogPosting blogPosting = new BlogPostingImpl(
				RandomTestUtil.randomString(10),
				RandomTestUtil.randomString(10),
				RandomTestUtil.randomString(10), Collections.emptyList(),
				LocalDateTime.now(), RandomTestUtil.randomString(10),
				friendlyURLPath, RandomTestUtil.randomString(10), 0L,
				Collections.emptyList());

			BlogsEntry blogsEntry = addBlogsEntry(
				_group.getGroupId(), blogPosting);

			Assert.assertEquals(user.getUserId(), blogsEntry.getUserId());
			Assert.assertNotEquals(
				currentUser.getUserId(), blogsEntry.getUserId());
		}
		finally {
			_userLocalService.deleteUser(user);
		}
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
			"Blog entries: " + blogEntries, blogEntries.contains(blogsEntry));
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
		Date displayDate = new Date(
			System.currentTimeMillis() + 24 * 60 * 60 * 1000);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

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
		LocalDateTime localDateTime = LocalDateTime.of(
			2015, Month.JULY, 29, 19, 0);

		BlogPosting blogPosting = new BlogPostingImpl(
			"alternativeHeadline", "articleBody", "caption",
			Collections.emptyList(), localDateTime, "description",
			"friendlyurlpath", "headline", 0L, Collections.emptyList());

		BlogsEntry blogsEntry = addBlogsEntry(_group.getGroupId(), blogPosting);

		String updatedHeadline = "updatedHeadline";

		BlogPosting updatedBlogPosting = new BlogPostingImpl(
			blogPosting.getAlternativeHeadline(), blogPosting.getArticleBody(),
			blogPosting.getCaption(), Collections.emptyList(), localDateTime,
			blogPosting.getDescription(), blogPosting.getFriendlyURLPath(),
			updatedHeadline, 0L, Collections.emptyList());

		blogsEntry = updateBlogsEntry(
			blogsEntry.getEntryId(), updatedBlogPosting);

		ZoneId zone = ZoneId.systemDefault();

		Date date = Date.from(localDateTime.atZone(zone).toInstant());

		Assert.assertEquals(
			updatedBlogPosting.getArticleBody(), blogsEntry.getContent());
		Assert.assertEquals(
			updatedBlogPosting.getCaption(), blogsEntry.getCoverImageCaption());
		Assert.assertEquals(
			updatedBlogPosting.getDescription(), blogsEntry.getDescription());
		Assert.assertEquals(date, blogsEntry.getDisplayDate());
		Assert.assertEquals(
			updatedBlogPosting.getAlternativeHeadline(),
			blogsEntry.getSubtitle());
		Assert.assertEquals(
			updatedBlogPosting.getHeadline(), blogsEntry.getTitle());
		Assert.assertEquals(
			updatedBlogPosting.getFriendlyURLPath(), blogsEntry.getUrlTitle());
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
			List<Long> categories, LocalDateTime publishedDate,
			String description, String friendlyURLPath, String headline,
			long imageId, List<String> keywords) {

			_alternativeHeadline = alternativeHeadline;
			_articleBody = articleBody;
			_caption = caption;
			_categories = categories;
			_publishedDate = publishedDate;
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

		@Override
		public Optional<LocalDateTime> getPublishedDateOptional() {
			return Optional.ofNullable(_publishedDate);
		}

		private final String _alternativeHeadline;
		private final String _articleBody;
		private final String _caption;
		private final List<Long> _categories;
		private final String _description;
		private final String _friendlyURLPath;
		private final String _headline;
		private final long _imageId;
		private final List<String> _keywords;
		private final LocalDateTime _publishedDate;

	}

}