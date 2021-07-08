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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardMessage;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LoggerTestUtil;

import org.junit.Before;
import org.junit.Test;
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());
	}

	@Test
	public void testPutSiteMessageBoardMessageWithoutParentMessageId()
		throws Exception {

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		randomMessageBoardMessage.setParentMessageBoardMessageId((Long)null);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"com.liferay.portal.vulcan.internal.jaxrs.exception.mapper." +
					"WebApplicationExceptionMapper",
				LoggerTestUtil.ERROR)) {

			assertHttpResponseStatusCode(
				400,
				messageBoardMessageResource.
					putSiteMessageBoardMessageByExternalReferenceCodeHttpResponse(
						testGroup.getGroupId(),
						randomMessageBoardMessage.getExternalReferenceCode(),
						randomMessageBoardMessage));
		}
	}

	@Override
	protected String[] getAdditionalAssertFieldNames() {
		return new String[] {"articleBody", "headline"};
	}

	@Override
	protected String[] getIgnoredEntityFieldNames() {
		return new String[] {
			"creatorId", "messageBoardSectionId", "messageBoardThreadId",
			"parentMessageBoardMessageId", "ratingValue"
		};
	}

	@Override
	protected MessageBoardMessage randomMessageBoardMessage() throws Exception {
		MessageBoardMessage messageBoardMessage =
			super.randomMessageBoardMessage();

		messageBoardMessage.setMessageBoardSectionId((Long)null);

		return messageBoardMessage;
	}

	@Override
	protected MessageBoardMessage
			testDeleteMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testDeleteMessageBoardMessageMyRating_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testDeleteSiteMessageBoardMessageByExternalReferenceCode_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testGetMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected Long
			testGetMessageBoardMessageMessageBoardMessagesPage_getParentMessageBoardMessageId()
		throws Exception {

		MBMessage mbMessage = _addMbMessage(
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		return mbMessage.getRootMessageId();
	}

	@Override
	protected Long
			testGetMessageBoardThreadMessageBoardMessagesPage_getMessageBoardThreadId()
		throws Exception {

		MBMessage mbMessage = _addMbMessage(
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		return mbMessage.getThreadId();
	}

	@Override
	protected MessageBoardMessage
			testGetSiteMessageBoardMessageByExternalReferenceCode_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testGetSiteMessageBoardMessageByFriendlyUrlPath_addMessageBoardMessage()
		throws Exception {

		return testPostMessageBoardMessageMessageBoardMessage_addMessageBoardMessage(
			randomMessageBoardMessage());
	}

	@Override
	protected MessageBoardMessage
			testGetSiteMessageBoardMessagePermissionsPage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testGetSiteMessageBoardMessagesPage_addMessageBoardMessage(
				Long siteId, MessageBoardMessage messageBoardMessage)
		throws Exception {

		return _addMessageBoardMessage(messageBoardMessage, siteId);
	}

	@Override
	protected MessageBoardMessage
			testGraphQLMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		MBMessage mbMessage = MBMessageLocalServiceUtil.addMessage(
			TestPropsValues.getUserId(), "test", testGroup.getGroupId(), 0,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			new ServiceContext());

		return messageBoardMessageResource.getMessageBoardMessage(
			mbMessage.getMessageId());
	}

	@Override
	protected MessageBoardMessage
			testPatchMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutMessageBoardMessagePermission_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutMessageBoardMessageSubscribe_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutMessageBoardMessageUnsubscribe_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutSiteMessageBoardMessageByExternalReferenceCode_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutSiteMessageBoardMessageByExternalReferenceCode_createMessageBoardMessage()
		throws Exception {

		MessageBoardMessage messageBoardMessage =
			super.
				testPutSiteMessageBoardMessageByExternalReferenceCode_createMessageBoardMessage();

		messageBoardMessage.setParentMessageBoardMessageId(
			_mbThread.getRootMessageId());

		return messageBoardMessage;
	}

	@Override
	protected MessageBoardMessage
			testPutSiteMessageBoardMessagePermission_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	private MBMessage _addMbMessage(Long siteId, String subject, String body)
		throws Exception {

		MBMessage mbMessage = MBTestUtil.addMessage(
			siteId,
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			subject, body);

		_mbThread = mbMessage.getThread();

		return mbMessage;
	}

	private MessageBoardMessage _addMessageBoardMessage() throws Exception {
		return _addMessageBoardMessage(null, testGroup.getGroupId());
	}

	private MessageBoardMessage _addMessageBoardMessage(
			MessageBoardMessage messageBoardMessage, Long siteId)
		throws Exception {

		if (messageBoardMessage != null) {
			MBMessage mbMessage = _addMbMessage(
				siteId, messageBoardMessage.getHeadline(),
				messageBoardMessage.getArticleBody());

			return messageBoardMessageResource.getMessageBoardMessage(
				mbMessage.getMessageId());
		}

		_addMbMessage(
			siteId, RandomTestUtil.randomString(),
			RandomTestUtil.randomString());

		return messageBoardMessageResource.
			postMessageBoardThreadMessageBoardMessage(
				_mbThread.getThreadId(), randomMessageBoardMessage());
	}

	@DeleteAfterTestRun
	private MBThread _mbThread;

}