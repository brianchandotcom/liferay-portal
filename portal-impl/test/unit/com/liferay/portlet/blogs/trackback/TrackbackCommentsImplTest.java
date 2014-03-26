/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.trackback;

import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
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
@RunWith(PowerMockRunner.class)
@PrepareForTest({MBMessageLocalServiceUtil.class})
public class TrackbackCommentsImplTest extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpMessageBoards();
		setUpServiceContext();
	}

	@Test
	public void testAddTrackbackComment() throws Exception {

		// Prepare

		long threadId = 7;

		when(
			_mbThread.getThreadId()
		).thenReturn(
			threadId
		);

		long parentMessageId = 37;

		when(
			_mbThread.getRootMessageId()
		).thenReturn(
			parentMessageId
		);

		long messageId = 99999L;

		when(
			_mbMessage.getMessageId()
		).thenReturn(
			messageId
		);

		// Execute

		long userId = 42;
		long groupId = 16;
		String className = BlogsEntry.class.getName();
		long classPK = 142857;

		long result = _comments.addTrackbackComment(
			userId, groupId, className, classPK, "__blogName__", "__title__",
			"__body__", _serviceContextFunction);

		// Verify

		Assert.assertEquals(messageId, result);

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
			Matchers.same(_mockServiceContext)
		);
	}

	protected void setUpMessageBoards() throws Exception {
		mockStatic(MBMessageLocalServiceUtil.class, new CallsRealMethods());

		stub(
			method(MBMessageLocalServiceUtil.class, "getService")
		).toReturn(
			_mbMessageLocalService
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
			_mbMessageDisplay.getThread()
		).thenReturn(
			_mbThread
		);
	}

	protected void setUpServiceContext() {
		when(
			_serviceContextFunction.apply(MBMessage.class.getName())
		).thenReturn(
			_mockServiceContext
		);
	}

	private TrackbackCommentsImpl _comments = new TrackbackCommentsImpl();

	@Mock
	private MBMessage _mbMessage;

	@Mock
	private MBMessageDisplay _mbMessageDisplay;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	@Mock
	private MBThread _mbThread;

	private ServiceContext _mockServiceContext = new ServiceContext();

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

}