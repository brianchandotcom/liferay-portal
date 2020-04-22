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
import com.liferay.headless.delivery.client.dto.v1_0.MessageBoardThread;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardThreadSerDes;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
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

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setScopeGroupId(testGroup.getGroupId());

		_mbCategory = MBCategoryLocalServiceUtil.addCategory(
			UserLocalServiceUtil.getDefaultUserId(testGroup.getCompanyId()),
			testGroup.getGroupId(), RandomTestUtil.randomString(),
			RandomTestUtil.randomString(), serviceContext);
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortDateTime() {
	}

	@Ignore
	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortString() {
	}

	@Test
	public void testGraphQLGetSiteMessageBoardThreadByFriendlyUrlPath()
		throws Exception {

		MessageBoardThread messageBoardThread =
			testPostSiteMessageBoardThread_addMessageBoardThread(
				randomMessageBoardThread());

		List<GraphQLField> graphQLFields = getGraphQLFields();

		GraphQLField graphQLField = new GraphQLField(
			"query",
			new GraphQLField(
				"messageBoardThreadByFriendlyUrlPath",
				(HashMap)HashMapBuilder.put(
					"friendlyUrlPath",
					"\"" + messageBoardThread.getFriendlyUrlPath() + "\""
				).put(
					"siteKey", "\"" + messageBoardThread.getSiteId() + "\""
				).build(),
				graphQLFields.toArray(new GraphQLField[0])));

		JSONObject jsonObject = JSONFactoryUtil.createJSONObject(
			invoke(graphQLField.toString()));

		JSONObject dataJSONObject = jsonObject.getJSONObject("data");

		Assert.assertTrue(
			equals(
				messageBoardThread,
				MessageBoardThreadSerDes.toDTO(
					dataJSONObject.getString(
						"messageBoardThreadByFriendlyUrlPath"))));
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
	protected MessageBoardThread randomMessageBoardThread() throws Exception {
		MessageBoardThread messageBoardThread =
			super.randomMessageBoardThread();

		messageBoardThread.setMessageBoardSectionId((Long)null);
		messageBoardThread.setThreadType("Urgent");

		return messageBoardThread;
	}

	@Override
	protected Long
		testGetMessageBoardSectionMessageBoardThreadsPage_getMessageBoardSectionId() {

		return _mbCategory.getCategoryId();
	}

	@Override
	protected MessageBoardThread
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				MessageBoardThread messageBoardThread)
		throws Exception {

		messageBoardThread =
			testPostMessageBoardSectionMessageBoardThread_addMessageBoardThread(
				messageBoardThread);

		MBThread mbThread = MBThreadLocalServiceUtil.getThread(
			messageBoardThread.getId());

		RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), MBMessage.class.getName(),
			mbThread.getRootMessageId(), 1.0, new ServiceContext());

		return messageBoardThread;
	}

	private MBCategory _mbCategory;

}