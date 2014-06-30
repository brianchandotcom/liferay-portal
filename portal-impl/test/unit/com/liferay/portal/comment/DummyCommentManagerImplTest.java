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

package com.liferay.portal.comment;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author André de Oliveira
 */
public class DummyCommentManagerImplTest {

	@Test
	public void testAllCallsDoNothing() throws PortalException {
		String body = RandomTestUtil.randomString();
		String className = RandomTestUtil.randomString();
		long classPK = RandomTestUtil.randomLong();
		long commentId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		String subject = RandomTestUtil.randomString();
		long userId = RandomTestUtil.randomLong();
		String userName = RandomTestUtil.randomString();

		Assert.assertEquals(
			0,
			_commentManager.addComment(
				userId, groupId, className, classPK, userName, subject, body,
				null));

		_commentManager.addDiscussion(
			userId, groupId, className, classPK, userName);

		_commentManager.deleteComment(commentId);

		_commentManager.deleteDiscussion(className, classPK);
	}

	private CommentManager _commentManager = new DummyCommentManagerImpl();

}