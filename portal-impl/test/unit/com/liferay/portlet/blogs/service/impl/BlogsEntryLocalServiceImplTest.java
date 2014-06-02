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

import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;

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

		_blogsEntryLocalServiceImpl.setMBMessageLocalService(
			_mbMessageLocalService);
	}

	@Test
	public void testAddInitialDiscussion() throws Exception {
		long entryId = RandomTestUtil.randomLong();

		Mockito.when(
			_blogsEntry.getEntryId()
		).thenReturn(
			entryId
		);

		Mockito.when(
			_blogsEntry.getUserName()
		).thenReturn(
			"__UserName__"
		);

		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();

		_blogsEntryLocalServiceImpl.addInitialDiscussion(
			_blogsEntry, userId, groupId);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			userId, "__UserName__", groupId, BlogsEntry.class.getName(),
			entryId, WorkflowConstants.ACTION_PUBLISH);
	}

	@Test
	public void testAddInitialDiscussionWhenCommentsAreDisabled()
		throws Exception {

		boolean previous = PropsValues.BLOGS_ENTRY_COMMENTS_ENABLED;

		Whitebox.setInternalState(
			PropsValues.class, "BLOGS_ENTRY_COMMENTS_ENABLED", false);

		try {
			_blogsEntryLocalServiceImpl.addInitialDiscussion(
				_blogsEntry, RandomTestUtil.randomLong(),
				RandomTestUtil.randomLong());
		}
		finally {
			Whitebox.setInternalState(
				PropsValues.class, "BLOGS_ENTRY_COMMENTS_ENABLED", previous);
		}

		Mockito.verifyZeroInteractions(_mbMessageLocalService);
	}

	@Test
	public void testDeleteDiscussion() throws Exception {
		long entryId = RandomTestUtil.randomLong();

		Mockito.when(
			_blogsEntry.getEntryId()
		).thenReturn(
			entryId
		);

		_blogsEntryLocalServiceImpl.deleteDiscussion(_blogsEntry);

		Mockito.verify(
			_mbMessageLocalService
		).deleteDiscussionMessages(
			BlogsEntry.class.getName(), entryId
		);
	}

	@Mock
	private BlogsEntry _blogsEntry;

	private BlogsEntryLocalServiceImpl _blogsEntryLocalServiceImpl =
		new BlogsEntryLocalServiceImpl();

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

}