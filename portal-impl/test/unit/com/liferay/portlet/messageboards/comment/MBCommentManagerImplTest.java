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

package com.liferay.portlet.messageboards.comment;

import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.util.Function;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;

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
public class MBCommentManagerImplTest extends Mockito {

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);

		setUpMBCommentManagerImpl();
		setUpServiceContext();
	}

	@Test
	public void testAddComment() throws Exception {
		long mbMessageId = RandomTestUtil.randomLong();

		when(
			_mbMessage.getMessageId()
		).thenReturn(
			mbMessageId
		);

		long rootMessageId = RandomTestUtil.randomLong();

		when(
			_mbThread.getRootMessageId()
		).thenReturn(
			rootMessageId
		);

		long threadId = RandomTestUtil.randomLong();

		when(
			_mbThread.getThreadId()
		).thenReturn(
			threadId
		);

		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();
		String userName = RandomTestUtil.randomString();
		String subject = RandomTestUtil.randomString();
		String body = RandomTestUtil.randomString();

		Assert.assertEquals(
			mbMessageId,
			_mbCommentManagerImpl.addComment(
				userId, groupId, _CLASS_NAME, classPK, userName, subject, body,
				_serviceContextFunction));

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			userId, userName, groupId, _CLASS_NAME, classPK, threadId,
			rootMessageId, subject, body, _serviceContext
		);

		Mockito.verify(
			_mbMessageLocalService
		).getDiscussionMessageDisplay(
			userId, groupId, _CLASS_NAME, classPK,
			WorkflowConstants.STATUS_APPROVED
		);
	}

	@Test
	public void testAddDiscussion() throws Exception {
		long userId = RandomTestUtil.randomLong();
		long groupId = RandomTestUtil.randomLong();
		long classPK = RandomTestUtil.randomLong();
		String userName = RandomTestUtil.randomString();

		_mbCommentManagerImpl.addDiscussion(
			userId, groupId, _CLASS_NAME, classPK, userName);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			userId, userName, groupId, _CLASS_NAME, classPK,
			WorkflowConstants.ACTION_PUBLISH);
	}

	@Test
	public void testDeleteComment() throws Exception {
		long mbMessageId = RandomTestUtil.randomLong();

		_mbCommentManagerImpl.deleteComment(mbMessageId);

		Mockito.verify(
			_mbMessageLocalService
		).deleteDiscussionMessage(
			mbMessageId
		);
	}

	@Test
	public void testDeleteDiscussion() throws Exception {
		long classPK = RandomTestUtil.randomLong();

		_mbCommentManagerImpl.deleteDiscussion(_CLASS_NAME, classPK);

		Mockito.verify(
			_mbMessageLocalService
		).deleteDiscussionMessages(
			_CLASS_NAME, classPK
		);
	}

	protected void setUpMBCommentManagerImpl() throws Exception {
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
				Matchers.eq(_CLASS_NAME), Matchers.anyLong(),
				Matchers.eq(WorkflowConstants.STATUS_APPROVED)
			)
		).thenReturn(
			_mbMessageDisplay
		);

		ReflectionTestUtil.setFieldValue(
			MBMessageLocalServiceUtil.class, "_service",
			_mbMessageLocalService);
	}

	protected void setUpServiceContext() {
		when(
			_serviceContextFunction.apply(MBMessage.class.getName())
		).thenReturn(
			_serviceContext
		);
	}

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private MBCommentManagerImpl _mbCommentManagerImpl =
		new MBCommentManagerImpl();

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