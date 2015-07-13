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

package com.liferay.portal.comments;

import com.liferay.portal.kernel.comments.CommentManager;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;
import com.liferay.registry.Registry;
import com.liferay.registry.RegistryUtil;
import com.liferay.registry.ServiceTracker;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

/**
 * @author André de Oliveira
 */
public class CommentManagerImplTest extends Mockito {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		setUpRegistryUtil();
	}

	@Test
	public void testDefaultCommentManager() throws PortalException {
		when(
			_serviceTracker.isEmpty()
		).thenReturn(
			true
		);

		CommentManager defaultCommentManager = mock(CommentManager.class);

		_commentManagerImpl = new CommentManagerImpl(defaultCommentManager);

		testAllCallsAreDelegated(defaultCommentManager);
	}

	@Test
	public void testRegistryCommentManager() throws PortalException {
		CommentManager registryCommentManager = mock(CommentManager.class);

		when(
			_serviceTracker.getService()
		).thenReturn(
			registryCommentManager
		);

		when(
			_serviceTracker.isEmpty()
		).thenReturn(
			false
		);

		CommentManager defaultCommentManager = mock(CommentManager.class);

		_commentManagerImpl = new CommentManagerImpl(defaultCommentManager);

		testAllCallsAreDelegated(registryCommentManager);

		verifyZeroInteractions(defaultCommentManager);
	}

	protected void setUpRegistryUtil() {
		Registry registry = mock(Registry.class);

		when(
			registry.getRegistry()
		).thenReturn(
			registry
		);

		when(
			registry.setRegistry(registry)
		).thenReturn(
			registry
		);

		when(
			registry.trackServices(
				(com.liferay.registry.Filter)Matchers.any())
		).thenReturn(
			_serviceTracker
		);

		RegistryUtil.setRegistry(null);
		RegistryUtil.setRegistry(registry);
	}

	protected void testAllCallsAreDelegated(CommentManager commentManager)
		throws PortalException {

		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		String className = RandomTestUtil.randomString();
		long classPK = RandomTestUtil.randomLong();
		String userName = RandomTestUtil.randomString();
		String subject = RandomTestUtil.randomString();
		String body = RandomTestUtil.randomString();
		long commentId = RandomTestUtil.randomLong();

		_commentManagerImpl.addComment(
			userId, groupId, className, classPK, body, _serviceContextFunction);

		Mockito.verify(
			commentManager
		).addComment(
			userId, groupId, className, classPK, body, _serviceContextFunction
		);

		when(
			commentManager.addComment(
				userId, groupId, className, classPK, userName, subject, body,
				_serviceContextFunction)
		).thenReturn(
			commentId
		);

		Assert.assertEquals(
			commentId,
			_commentManagerImpl.addComment(
				userId, groupId, className, classPK, userName, subject, body,
				_serviceContextFunction));

		_commentManagerImpl.addDiscussion(
			userId, groupId, className, classPK, userName);

		Mockito.verify(
			commentManager
		).addDiscussion(
			userId, groupId, className, classPK, userName
		);

		_commentManagerImpl.deleteComment(commentId);

		Mockito.verify(
			commentManager
		).deleteComment(
			commentId
		);

		_commentManagerImpl.deleteDiscussion(className, classPK);

		Mockito.verify(
			commentManager
		).deleteDiscussion(
			className, classPK
		);

		_commentManagerImpl.fetchComment(commentId);

		Mockito.verify(
			commentManager
		).fetchComment(
			commentId
		);

		int commentsCount = RandomTestUtil.randomInt();

		when(
			commentManager.getCommentsCount(className, classPK)
		).thenReturn(
			commentsCount
		);

		Assert.assertEquals(
			commentsCount,
			_commentManagerImpl.getCommentsCount(className, classPK));
	}

	private CommentManagerImpl _commentManagerImpl;

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

	@Mock
	private ServiceTracker<Object, Object> _serviceTracker;

}