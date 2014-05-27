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

import com.liferay.portal.kernel.comments.Comments;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.stubbing.answers.CallsRealMethods;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author André de Oliveira
 */
@PrepareForTest({MBMessageLocalServiceUtil.class})
@RunWith(PowerMockRunner.class)
public class CommentsImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpMessageBoards();
		setUpServiceContext();
	}

	@Test
	public void testAddComment() throws Exception {
		long messageId = RandomTestUtil.randomLong();

		when(
			_mbMessage.getMessageId()
		).thenReturn(
			messageId
		);

		long parentMessageId = RandomTestUtil.randomLong();

		when(
			_mbThread.getRootMessageId()
		).thenReturn(
			parentMessageId
		);

		long threadId = RandomTestUtil.randomLong();

		when(
			_mbThread.getThreadId()
		).thenReturn(
			threadId
		);

		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		String className = BlogsEntry.class.getName();
		long classPK = RandomTestUtil.randomLong();

		Assert.assertEquals(
			messageId,
			_comments.addComment(
				userId, groupId, className, classPK, "__blogName__",
				"__title__", "__body__", _serviceContextFunction));

		Mockito.verify(
			_mbMessageLocalService
		).getDiscussionMessageDisplay(
			userId, groupId, className, classPK,
			WorkflowConstants.STATUS_APPROVED
		);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			Matchers.eq(userId), Matchers.eq("__blogName__"),
			Matchers.eq(groupId), Matchers.eq(className), Matchers.eq(classPK),
			Matchers.eq(threadId), Matchers.eq(parentMessageId),
			Matchers.eq("__title__"), Matchers.eq("__body__"),
			Matchers.same(_serviceContext)
		);
	}

	@Test
	public void testDeleteComment() throws Exception {
		long messageId = RandomTestUtil.randomLong();

		_comments.deleteComment(messageId);

		Mockito.verify(
			_mbMessageLocalService
		).deleteDiscussionMessage(
			messageId
		);
	}

	protected void setUpMessageBoards() throws Exception {
		when(
			_mbMessageDisplay.getThread()
		).thenReturn(
			_mbThread
		);

		when(
			_mbMessageLocalService.addDiscussionMessage(
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyLong(),
				Matchers.anyString(), Matchers.anyLong(), Matchers.anyLong(),
				Matchers.anyLong(), Matchers.anyString(), Matchers.anyString(),
				(ServiceContext)Matchers.any()
			)
		).thenReturn(
			_mbMessage
		);

		when(
			_mbMessageLocalService.getDiscussionMessageDisplay(
				Matchers.anyLong(), Matchers.anyLong(),
				Matchers.eq(BlogsEntry.class.getName()), Matchers.anyLong(),
				Matchers.eq(WorkflowConstants.STATUS_APPROVED)
			)
		).thenReturn(
			_mbMessageDisplay
		);

		mockStatic(MBMessageLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(MBMessageLocalServiceUtil.class, "getService")
		).toReturn(
			_mbMessageLocalService
		);
	}

	protected void setUpServiceContext() {
		when(
			_serviceContextFunction.apply(MBMessage.class.getName())
		).thenReturn(
			_serviceContext
		);
	}

	private Comments _comments = new CommentsImpl();

	@Mock
	private MBMessage _mbMessage;

	@Mock
	private MBMessageDisplay _mbMessageDisplay;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	@Mock
	private MBThread _mbThread;

	private ServiceContext _serviceContext = new ServiceContext();

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

}