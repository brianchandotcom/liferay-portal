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
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardMessageSerDes;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.List;

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

	@Test
	public void testGraphQLGetSiteMessageBoardMessageByFriendlyUrlPath()
		throws Exception {

		MessageBoardMessage messageBoardMessage =
			testGraphQLMessageBoardMessage_addMessageBoardMessage();

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardMessageByFriendlyUrlPath",
				HashMapBuilder.<String, Object>put(
					"friendlyUrlPath",
					"\"" + messageBoardMessage.getFriendlyUrlPath() + "\""
				).put(
					"siteKey", "\"" + messageBoardMessage.getSiteId() + "\""
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equals(
				messageBoardMessage,
				MessageBoardMessageSerDes.toDTO(
					dataJSONObject.getString(
						"messageBoardMessageByFriendlyUrlPath"))));
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

	private MBMessage _addMbMessage(Long siteId, String subject, String body)
		throws PortalException {

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