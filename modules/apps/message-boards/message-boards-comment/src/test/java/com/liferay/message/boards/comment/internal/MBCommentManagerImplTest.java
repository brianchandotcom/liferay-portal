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

package com.liferay.message.boards.comment.internal;

import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBMessageDisplay;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.comment.DuplicateCommentException;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;

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

		ReflectionTestUtil.setFieldValue(
			_mbCommentManagerImpl, "_mbMessageLocalService",
			_mbMessageLocalService);

		ReflectionTestUtil.setFieldValue(
			_mbCommentManagerImpl, "_portal", _portal);

		setUpMBCommentManagerImpl();
		setUpPortalUtil();
		setUpServiceContext();
	}

	@Test
	public void testAddComment() throws Exception {
		_mbCommentManagerImpl.addComment(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _BODY,
			_serviceContextFunction);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			_USER_ID, StringPool.BLANK, _GROUP_ID, _CLASS_NAME, _ENTRY_ID,
			_THREAD_ID, _ROOT_MESSAGE_ID, StringPool.BLANK, _BODY,
			_serviceContext
		);

		Mockito.verify(
			_mbMessageLocalService
		).getThreadMessages(
			_THREAD_ID, WorkflowConstants.STATUS_APPROVED
		);
	}

	@Test
	public void testAddCommentWithUserNameAndSubject() throws Exception {
		when(
			_mbMessage.getMessageId()
		).thenReturn(
			_MBMESSAGE_ID
		);

		Assert.assertEquals(
			_MBMESSAGE_ID,
			_mbCommentManagerImpl.addComment(
				_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _USER_NAME,
				_SUBJECT, _BODY, _serviceContextFunction));

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			_USER_ID, _USER_NAME, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _THREAD_ID,
			_ROOT_MESSAGE_ID, _SUBJECT, _BODY, _serviceContext
		);

		Mockito.verify(
			_mbMessageLocalService
		).getDiscussionMessageDisplay(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID,
			WorkflowConstants.STATUS_APPROVED
		);
	}

	@Test
	public void testAddDiscussion() throws Exception {
		_mbCommentManagerImpl.addDiscussion(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _USER_NAME);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			_USER_ID, _USER_NAME, _GROUP_ID, _CLASS_NAME, _ENTRY_ID,
			WorkflowConstants.ACTION_PUBLISH
		);
	}

	@Test(expected = DuplicateCommentException.class)
	public void testAddDuplicateComment() throws Exception {
		setUpExistingComment(_BODY);

		_mbCommentManagerImpl.addComment(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _BODY,
			_serviceContextFunction);
	}

	@Test
	public void testAddUniqueComment() throws Exception {
		setUpExistingComment(_BODY + RandomTestUtil.randomString());

		_mbCommentManagerImpl.addComment(
			_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID, _BODY,
			_serviceContextFunction);

		Mockito.verify(
			_mbMessageLocalService
		).addDiscussionMessage(
			_USER_ID, StringPool.BLANK, _GROUP_ID, _CLASS_NAME, _ENTRY_ID,
			_THREAD_ID, _ROOT_MESSAGE_ID, StringPool.BLANK, _BODY,
			_serviceContext
		);
	}

	@Test
	public void testDeleteComment() throws Exception {
		_mbCommentManagerImpl.deleteComment(_MBMESSAGE_ID);

		Mockito.verify(
			_mbMessageLocalService
		).deleteDiscussionMessage(
			_MBMESSAGE_ID
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

	@Test
	public void testFetchComment() {
		long commentId = RandomTestUtil.randomLong();

		_mbCommentManagerImpl.fetchComment(commentId);

		Mockito.verify(
			_mbMessageLocalService
		).fetchMBMessage(
			commentId
		);
	}

	@Test
	public void testGetCommentsCount() {
		long classPK = RandomTestUtil.randomLong();
		long classNameId = RandomTestUtil.randomLong();
		int commentsCount = RandomTestUtil.randomInt();

		Mockito.when(
			_mbMessageLocalService.getDiscussionMessagesCount(
				classNameId, classPK, WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			commentsCount
		);

		Mockito.when(
			_portal.getClassNameId(_CLASS_NAME)
		).thenReturn(
			classNameId
		);

		Assert.assertEquals(
			commentsCount,
			_mbCommentManagerImpl.getCommentsCount(_CLASS_NAME, classPK));
	}

	protected void setUpExistingComment(String body) {
		when(
			_mbMessage.getBody()
		).thenReturn(
			body
		);

		List<MBMessage> messages = Collections.singletonList(_mbMessage);

		when(
			_mbMessageLocalService.getThreadMessages(
				_THREAD_ID, WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			messages
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
				Matchers.any())
		).thenReturn(
			_mbMessage
		);

		when(
			_mbMessageLocalService.getDiscussionMessageDisplay(
				_USER_ID, _GROUP_ID, _CLASS_NAME, _ENTRY_ID,
				WorkflowConstants.STATUS_APPROVED)
		).thenReturn(
			_mbMessageDisplay
		);

		when(
			_mbThread.getRootMessageId()
		).thenReturn(
			_ROOT_MESSAGE_ID
		);

		when(
			_mbThread.getThreadId()
		).thenReturn(
			_THREAD_ID
		);
	}

	protected void setUpPortalUtil() {
		PortalUtil portalUtil = new PortalUtil();

		portalUtil.setPortal(_portal);
	}

	protected void setUpServiceContext() {
		when(
			_serviceContextFunction.apply(MBMessage.class.getName())
		).thenReturn(
			_serviceContext
		);
	}

	private static final String _BODY = RandomTestUtil.randomString();

	private static final String _CLASS_NAME = RandomTestUtil.randomString();

	private static final long _ENTRY_ID = RandomTestUtil.randomLong();

	private static final long _GROUP_ID = RandomTestUtil.randomLong();

	private static final long _MBMESSAGE_ID = RandomTestUtil.randomLong();

	private static final long _ROOT_MESSAGE_ID = RandomTestUtil.randomLong();

	private static final String _SUBJECT = RandomTestUtil.randomString();

	private static final long _THREAD_ID = RandomTestUtil.randomLong();

	private static final long _USER_ID = RandomTestUtil.randomLong();

	private static final String _USER_NAME = RandomTestUtil.randomString();

	private final MBCommentManagerImpl _mbCommentManagerImpl =
		new MBCommentManagerImpl();

	@Mock
	private MBMessage _mbMessage;

	@Mock
	private MBMessageDisplay _mbMessageDisplay;

	@Mock
	private MBMessageLocalService _mbMessageLocalService;

	@Mock
	private MBThread _mbThread;

	@Mock
	private Portal _portal;

	private final ServiceContext _serviceContext = new ServiceContext();

	@Mock
	private Function<String, ServiceContext> _serviceContextFunction;

}