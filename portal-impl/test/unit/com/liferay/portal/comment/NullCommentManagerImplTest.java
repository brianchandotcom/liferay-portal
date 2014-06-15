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
public class NullCommentManagerImplTest {

	@Test
	public void testAllCallsDoNothing() throws PortalException {
		Assert.assertEquals(
			0,
			_commentManager.addComment(
				_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME,
				_SUBJECT, _BODY, null));

		_commentManager.addDiscussion(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME);

		_commentManager.deleteComment(_COMMENT_ID);

		_commentManager.deleteDiscussion(_CLASS_NAME, _CLASS_PK);
	}

	private static final String _BODY = RandomTestUtil.randomString();

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final long _CLASS_PK = RandomTestUtil.randomLong();

	private static final long _COMMENT_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _SUBJECT = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final String _USER_NAME = RandomTestUtil.randomString();

	private CommentManager _commentManager = new DummyCommentManagerImpl();

}