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

package com.liferay.portlet.blogs.service.impl;

import com.liferay.portal.kernel.comment.CommentManager;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

/**
 * @author André de Oliveira
 */
@PrepareForTest({PropsValues.class})
@RunWith(PowerMockRunner.class)
public class BlogsEntryLocalServiceImplTest {

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);

		_blogsEntryLocalServiceImpl.commentManager = _commentManager;
	}

	@Test
	public void testAddDiscussion() throws Exception {
		long blogsEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			_blogsEntry.getEntryId()
		).thenReturn(
			blogsEntryId
		);

		String userName = RandomTestUtil.randomString();

		Mockito.when(
			_blogsEntry.getUserName()
		).thenReturn(
			userName
		);

		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();

		_blogsEntryLocalServiceImpl.addDiscussion(_blogsEntry, userId, groupId);

		Mockito.verify(
			_commentManager
		).addDiscussion(
			userId, groupId, BlogsEntry.class.getName(), blogsEntryId,
			userName);
	}

	@Test
	public void testAddDiscussionWhenCommentsAreDisabled() throws Exception {
		boolean value = PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_ENTRY_COMMENTS_ENABLED", false);

		try {
			_blogsEntryLocalServiceImpl.addDiscussion(
				_blogsEntry, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong());
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_ENTRY_COMMENTS_ENABLED", value);
		}

		Mockito.verifyZeroInteractions(_commentManager);
	}

	@Test
	public void testDeleteDiscussion() throws Exception {
		long blogsEntryId = RandomTestUtil.randomLong();

		Mockito.when(
			_blogsEntry.getEntryId()
		).thenReturn(
			blogsEntryId
		);

		_blogsEntryLocalServiceImpl.deleteDiscussion(_blogsEntry);

		Mockito.verify(
			_commentManager
		).deleteDiscussion(
			BlogsEntry.class.getName(), blogsEntryId
		);
	}

	@Mock
	private BlogsEntry _blogsEntry;

	private final BlogsEntryLocalServiceImpl _blogsEntryLocalServiceImpl =
		new BlogsEntryLocalServiceImpl();

	@Mock
	private CommentManager _commentManager;

}