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
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import org.apache.log4j.Level;

import org.junit.Assert;
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

	@Override
	@Test
	public void testPutMessageBoardThreadMessageBoardMessage()
		throws Exception {

		// Update

		super.testPutMessageBoardThreadMessageBoardMessage();

		// Insert

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		randomMessageBoardMessage.setMessageBoardThreadId(
			_mbThread.getThreadId());

		randomMessageBoardMessage.setParentMessageBoardMessageId(
			_mbThread.getRootMessageId());

		MessageBoardMessage insertMessageBoardMessage =
			messageBoardMessageResource.
				putMessageBoardThreadMessageBoardMessage(
					randomMessageBoardMessage.getMessageBoardThreadId(),
					randomMessageBoardMessage.getExternalReferenceCode(),
					randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, insertMessageBoardMessage);
		assertValid(insertMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			messageBoardMessageResource.
				getMessageBoardThreadMessageBoardMessage(
					insertMessageBoardMessage.getMessageBoardThreadId(),
					insertMessageBoardMessage.getExternalReferenceCode());

		assertEquals(randomMessageBoardMessage, getMessageBoardMessage);
		assertValid(getMessageBoardMessage);
		Assert.assertEquals(
			randomMessageBoardMessage.getExternalReferenceCode(),
			getMessageBoardMessage.getExternalReferenceCode());
	}

	@Override
	@Test
	public void testPutSiteMessageBoardMessage() throws Exception {

		// Update

		super.testPutSiteMessageBoardMessage();

		// Insert

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		randomMessageBoardMessage.setParentMessageBoardMessageId(
			_mbThread.getRootMessageId());

		MessageBoardMessage insertMessageBoardMessage =
			messageBoardMessageResource.putSiteMessageBoardMessage(
				testGroup.getGroupId(),
				randomMessageBoardMessage.getExternalReferenceCode(),
				randomMessageBoardMessage);

		assertEquals(randomMessageBoardMessage, insertMessageBoardMessage);
		assertValid(insertMessageBoardMessage);

		MessageBoardMessage getMessageBoardMessage =
			messageBoardMessageResource.getSiteMessageBoardMessage(
				testGroup.getGroupId(),
				insertMessageBoardMessage.getExternalReferenceCode());

		assertEquals(randomMessageBoardMessage, getMessageBoardMessage);
		assertValid(getMessageBoardMessage);
		Assert.assertEquals(
			randomMessageBoardMessage.getExternalReferenceCode(),
			getMessageBoardMessage.getExternalReferenceCode());
	}

	@Test
	public void testPutSiteMessageBoardMessageWithoutParentMessageId()
		throws Exception {

		MessageBoardMessage randomMessageBoardMessage =
			randomMessageBoardMessage();

		randomMessageBoardMessage.setParentMessageBoardMessageId((Long)null);

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					"com.liferay.portal.vulcan.internal.jaxrs.exception." +
						"mapper.WebApplicationExceptionMapper",
					Level.ERROR)) {

			assertHttpResponseStatusCode(
				400,
				messageBoardMessageResource.
					putSiteMessageBoardMessageHttpResponse(
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
			testDeleteMessageBoardThreadMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testDeleteSiteMessageBoardMessage_addMessageBoardMessage()
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
	protected MessageBoardMessage
			testGetMessageBoardThreadMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
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
			testGetSiteMessageBoardMessage_addMessageBoardMessage()
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
			testPutMessageBoardThreadMessageBoardMessage_addMessageBoardMessage()
		throws Exception {

		return _addMessageBoardMessage();
	}

	@Override
	protected MessageBoardMessage
			testPutSiteMessageBoardMessage_addMessageBoardMessage()
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