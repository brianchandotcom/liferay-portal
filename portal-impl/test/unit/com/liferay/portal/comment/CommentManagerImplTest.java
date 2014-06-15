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
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
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

		_commentManagerImpl = new CommentManagerImpl();
	}

	@Test
	public void testCommentManagerFromRegistry() throws PortalException {
		CommentManager commentManagerFromRegistry = mock(CommentManager.class);

		when(
			_serviceTracker.getService()
		).thenReturn(
			commentManagerFromRegistry
		);

		when(
			_serviceTracker.isEmpty()
		).thenReturn(
			false
		);

		CommentManager defaultCommentManager = mock(CommentManager.class);

		_commentManagerImpl.setDefaultCommentManager(defaultCommentManager);

		testAllCallsAreDelegated(commentManagerFromRegistry);

		verifyZeroInteractions(defaultCommentManager);
	}

	@Test
	public void testDefaultCommentManager() throws PortalException {
		when(
			_serviceTracker.isEmpty()
		).thenReturn(
			true
		);

		CommentManager defaultCommentManager = mock(CommentManager.class);

		_commentManagerImpl.setDefaultCommentManager(defaultCommentManager);

		testAllCallsAreDelegated(defaultCommentManager);
	}

	protected void setUpRegistryUtil() {
		Registry registry = mock(Registry.class);

		when(
			registry.setRegistry(registry)
		).thenReturn(
			registry
		);

		when(
			registry.getRegistry()
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

		when(
			commentManager.addComment(
				_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME,
				_SUBJECT, _BODY, _serviceContextFunction)
		).thenReturn(
			_COMMENT_ID
		);

		Assert.assertEquals(
			_COMMENT_ID,
			_commentManagerImpl.addComment(
				_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME,
				_SUBJECT, _BODY, _serviceContextFunction));

		_commentManagerImpl.addDiscussion(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME);

		_commentManagerImpl.deleteComment(_COMMENT_ID);

		_commentManagerImpl.deleteDiscussion(_CLASS_NAME, _CLASS_PK);

		Mockito.verify(
			commentManager
		).addDiscussion(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _CLASS_PK, _USER_NAME
		);

		Mockito.verify(
			commentManager
		).deleteComment(
			_COMMENT_ID
		);

		Mockito.verify(
			commentManager
		).deleteDiscussion(
			_CLASS_NAME, _CLASS_PK
		);
	}

	private static final String _BODY = RandomTestUtil.randomString();

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final long _CLASS_PK = RandomTestUtil.randomLong();

	private static final long _COMMENT_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final String _SUBJECT = RandomTestUtil.randomString();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final String _USER_NAME = RandomTestUtil.randomString();

	private CommentManagerImpl _commentManagerImpl;

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

	@Mock
	private ServiceTracker<Object, Object> _serviceTracker;

}