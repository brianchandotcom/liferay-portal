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

package com.liferay.comments.remote.comment.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.comments.remote.comment.Comment;
import com.liferay.comments.remote.comment.CommentService;
import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Subscription;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.test.ConstantPermissionChecker;
import com.liferay.portal.security.permission.test.PermissionCheckerReplacer;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
@Sync
public class CommentServiceTest {

	@Before
	public void setUp() throws Exception {
		ServiceTestUtil.setUser(TestPropsValues.getUser());

		_blogsEntry = BlogsEntryLocalServiceUtil.addEntry(
			TestPropsValues.getUserId(), StringUtil.randomString(),
			StringUtil.randomString(), StringUtil.randomString(),
			StringUtil.randomString(), new Date(), true, true, new String[0],
			StringPool.BLANK, null, null,
			ServiceContextTestUtil.getServiceContext());

		Registry registry = RegistryUtil.getRegistry();

		_commentService = registry.getService(CommentService.class);
	}

	@Test(expected = PrincipalException.class)
	public void testAddCommentWithNoPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			addComment();
		}
	}

	@Test
	public void testAddCommentWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			Assert.assertEquals(0, getCommentsCount());

			addComment();

			Assert.assertEquals(1, getCommentsCount());
		}
	}

	@Test(expected = PrincipalException.class)
	public void testDeleteCommentWithNoPermission() throws Exception {
		long commentId = 0;

		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			commentId = addComment();
		}

		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			_commentService.deleteComment(commentId);
		}
	}

	@Test
	public void testDeleteCommentWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			int commentsCount = getCommentsCount();
			long commentId = addComment();

			_commentService.deleteComment(commentId);

			Assert.assertEquals(commentsCount, getCommentsCount());
		}
	}

	@Test(expected = PrincipalException.class)
	public void testGetAllCommentsWithNoPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			addComment();
			addComment();
		}

		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			getComments(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
	}

	@Test
	public void testGetAllCommentsWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			addComment();
			addComment();

			List<Comment> comments = getComments(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertEquals(2, comments.size());
		}
	}

	@Test
	public void testGetCommentRange() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			addComment();
			long commentId = addComment();
			addComment();

			List<Comment> comments = getComments(1, 2);

			Assert.assertEquals(1, comments.size());
			Assert.assertEquals(commentId, comments.get(0).getCommentId());
		}
	}

	@Test(expected = PrincipalException.class)
	public void testGetEmptyCommentsCountWithNoPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			getCommentsCount();
		}
	}

	@Test
	public void testGetEmptyCommentsCountWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			Assert.assertEquals(0, getCommentsCount());
		}
	}

	@Test(expected = PrincipalException.class)
	public void testGetEmptyCommentsWithNoPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			getComments(QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
	}

	@Test
	public void testGetEmptyCommentsWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			List<Comment> comments = getComments(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Assert.assertTrue(comments.isEmpty());
		}
	}

	@Test
	public void testHasDiscussion() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			Assert.assertTrue(
				_commentService.hasDiscussion(
					TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
					_blogsEntry.getEntryId()));

			CommentManagerUtil.deleteDiscussion(
				BlogsEntry.class.getName(), _blogsEntry.getEntryId());

			Assert.assertFalse(
				_commentService.hasDiscussion(
					TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
					_blogsEntry.getEntryId()));
		}
	}

	@Test
	public void testSubscribe() throws Exception {
		_commentService.subscribeDiscussion(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId());

		Subscription subscription =
			SubscriptionLocalServiceUtil.fetchSubscription(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				BlogsEntry.class.getName(), _blogsEntry.getEntryId());

		Assert.assertNotNull(subscription);
	}

	@Test
	public void testUnsubscribe() throws Exception {
		_commentService.subscribeDiscussion(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId());

		_commentService.unsubscribeDiscussion(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId());

		Subscription subscription =
			SubscriptionLocalServiceUtil.fetchSubscription(
				TestPropsValues.getCompanyId(), TestPropsValues.getUserId(),
				BlogsEntry.class.getName(), _blogsEntry.getEntryId());

		Assert.assertNull(subscription);
	}

	@Test(expected = PrincipalException.class)
	public void testUpdateCommentWithNoPermission() throws Exception {
		long commentId = 0;

		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			commentId = addComment();
		}

		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysDenyingPermissionCheckerReplacer()) {

			String newBody = StringUtil.randomString();

			_commentService.updateComment(
				BlogsEntry.class.getName(), _blogsEntry.getEntryId(), commentId,
				newBody, newBody);
		}
	}

	@Test
	public void testUpdateCommentWithPermission() throws Exception {
		try (PermissionCheckerReplacer permissionCheckerReplacer =
				getAlwaysAllowingPermissionCheckerReplacer()) {

			long commentId = addComment();

			String newBody = StringUtil.randomString();

			_commentService.updateComment(
				BlogsEntry.class.getName(), _blogsEntry.getEntryId(), commentId,
				newBody, newBody);

			List<Comment> comments = getComments(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

			Comment comment = comments.get(0);

			Assert.assertEquals(newBody, comment.getBody());
		}
	}

	@Rule
	public final AggregateTestRule aggregateTestRule = new AggregateTestRule(
		new LiferayIntegrationTestRule(),
		SynchronousDestinationTestRule.INSTANCE);

	protected long addComment() throws Exception {
		return _commentService.addComment(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId(), StringUtil.randomString());
	}

	protected PermissionCheckerReplacer
			getAlwaysAllowingPermissionCheckerReplacer()
		throws PortalException {

		return new PermissionCheckerReplacer(
			ConstantPermissionChecker.getAlwaysAllowingPermissionChecker(
				TestPropsValues.getUser()));
	}

	protected PermissionCheckerReplacer
			getAlwaysDenyingPermissionCheckerReplacer()
		throws PortalException {

		return new PermissionCheckerReplacer(
			ConstantPermissionChecker.getAlwaysDenyingPermissionChecker(
				TestPropsValues.getUser()));
	}

	protected List<Comment> getComments(int start, int end)
		throws PortalException {

		return _commentService.getComments(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId(), start, end);
	}

	protected int getCommentsCount() throws Exception {
		return _commentService.getCommentsCount(
			TestPropsValues.getGroupId(), BlogsEntry.class.getName(),
			_blogsEntry.getEntryId());
	}

	@DeleteAfterTestRun
	private BlogsEntry _blogsEntry;

	private CommentService _commentService;

}