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
import com.liferay.headless.delivery.dto.v1_0.MessageBoardThread;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
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
public class MessageBoardThreadResourceTest
	extends BaseMessageBoardThreadResourceTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		User user = UserTestUtil.addGroupAdminUser(testGroup);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		_mbCategory = MBCategoryLocalServiceUtil.addCategory(
			user.getUserId(), testGroup.getGroupId(),
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);
	}

	@Override
	protected void assertValid(MessageBoardThread messageBoardThread) {
		boolean valid = false;

		if (Objects.equals(
				messageBoardThread.getContentSpaceId(),
				testGroup.getGroupId()) &&
			(messageBoardThread.getDateCreated() != null) &&
			(messageBoardThread.getDateModified() != null) &&
			(messageBoardThread.getHeadline() != null) &&
			(messageBoardThread.getId() != null)) {

			valid = true;
		}

		Assert.assertTrue(valid);
	}

	@Override
	protected boolean equals(
		MessageBoardThread messageBoardThread1,
		MessageBoardThread messageBoardThread2) {

		if (Objects.equals(
				messageBoardThread1.getContentSpaceId(),
				messageBoardThread2.getContentSpaceId()) &&
			Objects.equals(
				messageBoardThread1.getHeadline(),
				messageBoardThread2.getHeadline()) &&
			Objects.equals(
				messageBoardThread1.getArticleBody(),
				messageBoardThread2.getArticleBody())) {

			return true;
		}

		return false;
	}

	protected MessageBoardThread randomMessageBoardThread() {
		MessageBoardThread messageBoardThread =
			super.randomMessageBoardThread();

		messageBoardThread.setContentSpaceId(testGroup.getGroupId());
		messageBoardThread.setThreadType("Urgent");

		return messageBoardThread;
	}

	@Override
	protected MessageBoardThread randomPatchMessageBoardThread() {
		MessageBoardThread messageBoardThread =
			super.randomPatchMessageBoardThread();

		messageBoardThread.setContentSpaceId(testGroup.getGroupId());

		return messageBoardThread;
	}

	@Override
	protected MessageBoardThread
			testDeleteMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Override
	protected MessageBoardThread
			testDeleteMessageBoardThreadMyRating_addMessageBoardThread()
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Override
	protected MessageBoardThread
			testGetContentSpaceMessageBoardThreadsPage_addMessageBoardThread(
				Long contentSpaceId, MessageBoardThread messageBoardThread)
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			contentSpaceId, messageBoardThread);
	}

	@Override
	protected MessageBoardThread
			testGetMessageBoardSectionMessageBoardThreadsPage_addMessageBoardThread(
				Long messageBoardSectionId,
				MessageBoardThread messageBoardThread)
		throws Exception {

		return invokePostMessageBoardSectionMessageBoardThread(
			messageBoardSectionId, messageBoardThread);
	}

	@Override
	protected Long
			testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId()
		throws Exception {

		return _mbCategory.getCategoryId();
	}

	@Override
	protected MessageBoardThread
			testGetMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Override
	protected MessageBoardThread
			testPatchMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	@Override
	protected MessageBoardThread
			testPostContentSpaceMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), messageBoardThread);
	}

	@Override
	protected MessageBoardThread
			testPostMessageBoardSectionMessageBoardThread_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), messageBoardThread);
	}

	@Override
	protected MessageBoardThread
			testPutMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return invokePostContentSpaceMessageBoardThread(
			testGroup.getGroupId(), randomMessageBoardThread());
	}

	private MBCategory _mbCategory;

}