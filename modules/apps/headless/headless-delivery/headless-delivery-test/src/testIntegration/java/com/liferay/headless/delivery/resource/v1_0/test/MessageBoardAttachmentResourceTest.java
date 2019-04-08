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
import com.liferay.headless.delivery.dto.v1_0.MessageBoardAttachment;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.vulcan.multipart.BinaryFile;
import com.liferay.portal.vulcan.multipart.MultipartBody;

import java.io.ByteArrayInputStream;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * @author Javier Gamarra
 */
@RunWith(Arquillian.class)
public class MessageBoardAttachmentResourceTest
	extends BaseMessageBoardAttachmentResourceTestCase {

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
	protected void assertValid(MessageBoardAttachment messageBoardAttachment) {
		boolean valid = false;

		if ((messageBoardAttachment.getContentUrl() != null) &&
			(messageBoardAttachment.getEncodingFormat() != null) &&
			(messageBoardAttachment.getId() != null) &&
			(messageBoardAttachment.getTitle() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		MessageBoardAttachment messageBoardAttachment1,
		MessageBoardAttachment messageBoardAttachment2) {

		if (Objects.equals(
				messageBoardAttachment1.getContentUrl(),
				messageBoardAttachment2.getContentUrl()) &&
			Objects.equals(
				messageBoardAttachment1.getTitle(),
				messageBoardAttachment2.getTitle())) {

			return true;
		}

		return false;
	}

	@Override
	protected MessageBoardAttachment
			testDeleteMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardAttachment(
			_mbThread.getThreadId(),
			_getMultipartBody(randomMessageBoardAttachment()));
	}

	@Override
	protected MessageBoardAttachment
			testGetMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardAttachment(
			_mbThread.getThreadId(),
			_getMultipartBody(randomMessageBoardAttachment()));
	}

	@Override
	protected MessageBoardAttachment
			testGetMessageBoardMessageMessageBoardAttachmentsPage_addMessageBoardAttachment(
				Long messageBoardMessageId,
				MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardAttachment(
			_mbThread.getThreadId(), _getMultipartBody(messageBoardAttachment));
	}

	@Override
	protected Long
			testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId()
		throws Exception {

		return _mbThread.getRootMessageId();
	}

	@Override
	protected MessageBoardAttachment
			testGetMessageBoardThreadMessageBoardAttachmentsPage_addMessageBoardAttachment(
				Long messageBoardThreadId,
				MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardAttachment(
			_mbThread.getThreadId(), _getMultipartBody(messageBoardAttachment));
	}

	@Override
	protected Long
			testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId()
		throws Exception {

		return _mbThread.getThreadId();
	}

	@Override
	protected MessageBoardAttachment
			testPostMessageBoardThreadMessageBoardAttachment_addMessageBoardAttachment(
				MessageBoardAttachment messageBoardAttachment)
		throws Exception {

		return invokePostMessageBoardThreadMessageBoardAttachment(
			_mbThread.getThreadId(), _getMultipartBody(messageBoardAttachment));
	}

	private MultipartBody _getMultipartBody(
		MessageBoardAttachment messageBoardAttachment) {

		contentType = "multipart/form-data;boundary=PART";

		Map<String, BinaryFile> binaryFileMap = new HashMap<>();

		String randomString = RandomTestUtil.randomString();

		binaryFileMap.put(
			"file",
			new BinaryFile(
				contentType, RandomTestUtil.randomString(),
				new ByteArrayInputStream(randomString.getBytes()), 0));

		return MultipartBody.of(
			binaryFileMap, __ -> inputObjectMapper,
			inputObjectMapper.convertValue(
				messageBoardAttachment, HashMap.class));
	}

	private MBThread _mbThread;

}