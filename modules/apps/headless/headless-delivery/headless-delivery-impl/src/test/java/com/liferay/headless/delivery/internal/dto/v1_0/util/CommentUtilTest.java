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

package com.liferay.headless.delivery.internal.dto.v1_0.util;

import com.liferay.headless.delivery.dto.v1_0.Creator;
import com.liferay.headless.delivery.dto.v1_0.util.CreatorUtil;
import com.liferay.portal.kernel.comment.Comment;
import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.util.Date;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * The Comment Util test.
 *
 * @author Carlos Correa
 */
@PrepareForTest(CreatorUtil.class)
@RunWith(PowerMockRunner.class)
public class CommentUtilTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@After
	public void tearDown() {
		Mockito.verifyNoMoreInteractions(_comment, _commentManager, _portal);
	}

	@Test
	public void testToComment() throws Exception {
		String body = RandomTestUtil.randomString();
		Date dateCreated = RandomTestUtil.nextDate();
		Date dateModified = RandomTestUtil.nextDate();
		String externalReferenceCode = RandomTestUtil.randomString();
		Long id = RandomTestUtil.randomLong();
		Integer numberOfComments = RandomTestUtil.randomInt();
		Long parentCommentId = RandomTestUtil.randomLong();
		User user = Mockito.mock(User.class);

		PowerMockito.mockStatic(CreatorUtil.class);
		PowerMockito.when(
			CreatorUtil.toCreator(
				Matchers.any(Portal.class), Matchers.any(Optional.class),
				Matchers.any(User.class))
		).thenReturn(
			_creator
		);

		Mockito.when(
			_comment.getBody()
		).thenReturn(
			body
		);
		Mockito.when(
			_comment.getCommentId()
		).thenReturn(
			id
		);
		Mockito.when(
			_comment.getCreateDate()
		).thenReturn(
			dateCreated
		);
		Mockito.when(
			_comment.getExternalReferenceCode()
		).thenReturn(
			externalReferenceCode
		);
		Mockito.when(
			_comment.getModifiedDate()
		).thenReturn(
			dateModified
		);
		Mockito.when(
			_comment.getParentCommentId()
		).thenReturn(
			parentCommentId
		);
		Mockito.when(
			_comment.getUser()
		).thenReturn(
			user
		);
		Mockito.when(
			_commentManager.getChildCommentsCount(
				Mockito.anyLong(), Mockito.anyInt())
		).thenReturn(
			numberOfComments
		);

		com.liferay.headless.delivery.dto.v1_0.Comment comment =
			CommentUtil.toComment(_comment, _commentManager, _portal);

		Assert.assertEquals(_creator, comment.getCreator());
		Assert.assertEquals(dateCreated, comment.getDateCreated());
		Assert.assertEquals(dateModified, comment.getDateModified());
		Assert.assertEquals(
			externalReferenceCode, comment.getExternalReferenceCode());
		Assert.assertEquals(id, comment.getId());
		Assert.assertEquals(numberOfComments, comment.getNumberOfComments());
		Assert.assertEquals(parentCommentId, comment.getParentCommentId());
		Assert.assertEquals(body, comment.getText());

		Mockito.verify(
			_comment
		).getBody();
		Mockito.verify(
			_comment, Mockito.times(2)
		).getCommentId();
		Mockito.verify(
			_comment
		).getCreateDate();
		Mockito.verify(
			_comment
		).getExternalReferenceCode();
		Mockito.verify(
			_comment
		).getModifiedDate();
		Mockito.verify(
			_comment
		).getParentCommentId();
		Mockito.verify(
			_comment
		).getUser();
		Mockito.verify(
			_commentManager
		).getChildCommentsCount(
			id, WorkflowConstants.STATUS_APPROVED
		);

		PowerMockito.verifyStatic(Mockito.only());
		CreatorUtil.toCreator(_portal, Optional.empty(), user);
	}

	@Test
	public void testToCommentNullComment() throws Exception {
		com.liferay.headless.delivery.dto.v1_0.Comment comment =
			CommentUtil.toComment(null, _commentManager, _portal);

		Assert.assertNull(comment);
	}

	@Mock
	private Comment _comment;

	@Mock
	private CommentManager _commentManager;

	@Mock
	private Creator _creator;

	@Mock
	private Portal _portal;

}