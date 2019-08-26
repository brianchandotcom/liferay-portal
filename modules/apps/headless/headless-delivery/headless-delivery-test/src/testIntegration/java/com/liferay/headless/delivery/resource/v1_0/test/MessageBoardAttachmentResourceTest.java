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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardAttachment;
import com.liferay.headless.delivery.client.http.HttpInvoker;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.FileUtil;

import java.io.File;

import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		MBMessage mbMessage = MBTestUtil.addMessage(
			testGroup.getGroupId(),
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			RandomTestUtil.randomString(), RandomTestUtil.randomString());

		_mbThread = mbMessage.getThread();
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLDeleteMessageBoardAttachment() {
	}

	@Ignore
	@Override
	@Test
	public void testGraphQLGetMessageBoardAttachment() {
	}

	@Override
	protected void assertValid(
			MessageBoardAttachment messageBoardAttachment,
			Map<String, File> multipartFiles)
		throws Exception {

		Assert.assertEquals(
			new String(FileUtil.getBytes(multipartFiles.get("file"))),
			_read(
				"http://localhost:8080" +
					messageBoardAttachment.getContentUrl()));
	}

	@Override
	protected Map<String, File> getMultipartFiles() throws Exception {
		Map<String, File> files = new HashMap<>();

		String randomString = RandomTestUtil.randomString();

		files.put("file", FileUtil.createTempFile(randomString.getBytes()));

		return files;
	}

	@Override
	protected MessageBoardAttachment
			testDeleteMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardThreadMessageBoardAttachment(
				_mbThread.getThreadId(), randomMessageBoardAttachment(),
				getMultipartFiles());
	}

	@Override
	protected MessageBoardAttachment
			testGetMessageBoardAttachment_addMessageBoardAttachment()
		throws Exception {

		return messageBoardAttachmentResource.
			postMessageBoardThreadMessageBoardAttachment(
				_mbThread.getThreadId(), randomMessageBoardAttachment(),
				getMultipartFiles());
	}

	@Override
	protected Long
		testGetMessageBoardMessageMessageBoardAttachmentsPage_getMessageBoardMessageId() {

		return _mbThread.getRootMessageId();
	}

	@Override
	protected Long
		testGetMessageBoardThreadMessageBoardAttachmentsPage_getMessageBoardThreadId() {

		return _mbThread.getThreadId();
	}

	private String _read(String url) throws Exception {
		HttpInvoker httpInvoker = HttpInvoker.newHttpInvoker();

		httpInvoker.httpMethod(HttpInvoker.HttpMethod.GET);
		httpInvoker.path(url);
		httpInvoker.userNameAndPassword("test@liferay.com:test");

		HttpInvoker.HttpResponse httpResponse = httpInvoker.invoke();

		return httpResponse.getContent();
	}

	private MBThread _mbThread;

}