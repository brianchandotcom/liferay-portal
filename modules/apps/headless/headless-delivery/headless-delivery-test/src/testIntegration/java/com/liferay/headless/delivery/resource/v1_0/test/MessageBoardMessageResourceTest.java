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

package com.liferay.headless.delivery.resource.v1_0.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.headless.delivery.dto.v1_0.MessageBoardMessage;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;

import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MessageBoardMessageResourceTest
	extends BaseMessageBoardMessageResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addGroupAdminUser(testGroup);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		MBMessage mbMessage = MBTestUtil.addMessage(
			testGroup.getGroupId(), user.getUserId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_mbThread = mbMessage.getThread();
	}

	@Override
	protected void assertValid(MessageBoardMessage messageBoardMessage) {
		boolean valid = false;

		if (Objects.equals(
				messageBoardMessage.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(messageBoardMessage.getDateCreated() != null) &&
			(messageBoardMessage.getDateModified() != null) &&
			(messageBoardMessage.getHeadline() != null) &&
			(messageBoardMessage.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		MessageBoardMessage messageBoardMessage1,
		MessageBoardMessage messageBoardMessage2) {

		if (Objects.equals(
				messageBoardMessage1.getContentSpaceId(),
				messageBoardMessage2.getContentSpaceId()) &&
			Objects.equals(
				messageBoardMessage1.getHeadline(),
				messageBoardMessage2.getHeadline()) &&
			Objects.equals(
				messageBoardMessage1.getArticleBody(),
				messageBoardMessage2.getArticleBody())) {

			return true;
		}

		return false;
	}

	@Override
	protected MessageBoardMessage randomIrrelevantMessageBoardMessage() {
		MessageBoardMessage messageBoardMessage =
			super.randomIrrelevantMessageBoardMessage();

		messageBoardMessage.setContentSpaceId(irrelevantGroup.getGroupId());

		return messageBoardMessage;
	}

	protected MessageBoardMessage randomMessageBoardMessage() {
		MessageBoardMessage messageBoardMessage =
			super.randomMessageBoardMessage();

		messageBoardMessage.setContentSpaceId(testGroup.getGroupId());

		return messageBoardMessage;
	}

	@Override
	protected MessageBoardMessage
			testDeleteMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	@Override
	protected MessageBoardMessage
			testDeleteMessageBoardMessageMyRating_addMessageBoardMessage()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	@Override
	protected MessageBoardMessage
			testGetMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	@Override
	protected MessageBoardMessage
			testGetMessageBoardMessageMessageBoardMessagesPage_addMessageBoardMessage(
				Long messageBoardMessageId,
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return invokePostMessageBoardMessageMessageBoardMessage(
			messageBoardMessageId, messageBoardMessage);
	}

	@Override
	protected Long
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId()
		throws Exception {

		return _mbThread.getRootMessageId();
	}

	@Override
	protected MessageBoardMessage
			testGetMessageBoardThreadMessageBoardMessagesPage_addMessageBoardMessage(
				Long messageBoardThreadId,
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			messageBoardThreadId, messageBoardMessage);
	}

	@Override
	protected Long
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId()
		throws Exception {

		return _mbThread.getThreadId();
	}

	@Override
	protected MessageBoardMessage
			testPatchMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	@Override
	protected MessageBoardMessage
			testPostMessageBoardMessageMessageBoardMessage_addMessageBoardMessage(
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), messageBoardMessage);
	}

	@Override
	protected MessageBoardMessage
			testPostMessageBoardThreadMessageBoardMessage_addMessageBoardMessage(
				MessageBoardMessage messageBoardMessage)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), messageBoardMessage);
	}

	@Override
	protected MessageBoardMessage
			testPutMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardMessage(
			_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	private MBThread _mbThread;

}