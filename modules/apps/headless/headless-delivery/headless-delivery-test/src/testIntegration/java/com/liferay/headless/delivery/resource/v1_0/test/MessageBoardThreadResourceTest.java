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
import com.liferay.headless.delivery.client.pagination.Page;
import com.liferay.headless.delivery.client.pagination.Pagination;
import com.liferay.headless.delivery.client.serdes.v1_0.MessageBoardThreadSerDes;
import com.liferay.message.boards.model.MBCategory;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.model.MBThread;
import com.liferay.message.boards.service.MBCategoryLocalServiceUtil;
import com.liferay.message.boards.service.MBThreadLocalServiceUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.json.JSONUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.ratings.kernel.service.RatingsEntryLocalServiceUtil;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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

	@Override
	@Test
	public void testGetMessageBoardThreadsRankedPageWithSortString()
		throws Exception {

		MessageBoardThread messageBoardThread1 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		MessageBoardThread messageBoardThread2 =
			testGetMessageBoardThreadsRankedPage_addMessageBoardThread(
				randomMessageBoardThread());

		MBThread mbThread = MBThreadLocalServiceUtil.getThread(
			messageBoardThread1.getId());

		RatingsEntryLocalServiceUtil.updateEntry(
			TestPropsValues.getUserId(), MBMessage.class.getName(),
			mbThread.getRootMessageId(), 0.5, new ServiceContext());

		Page<MessageBoardThread> ascPage =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				null, null, null, Pagination.of(1, 2), "ratingValue:asc");

		assertEquals(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			(List<MessageBoardThread>)ascPage.getItems());

		Page<MessageBoardThread> descPage =
			messageBoardThreadResource.getMessageBoardThreadsRankedPage(
				null, null, null, Pagination.of(1, 2), "ratingValue:desc");

		assertEquals(
			Arrays.asList(messageBoardThread2, messageBoardThread1),
			(List<MessageBoardThread>)descPage.getItems());
	}

	@Override
	@Test
	public void testGraphQLGetSiteMessageBoardThreadsPage() throws Exception {
		Long siteId = testGetSiteMessageBoardThreadsPage_getSiteId();

		MessageBoardThread messageBoardThread1 =
			testGraphQLMessageBoardThread_addMessageBoardThread();
		MessageBoardThread messageBoardThread2 =
			testGraphQLMessageBoardThread_addMessageBoardThread();

		JSONObject messageBoardThreadsJSONObject =
			JSONUtil.getValueAsJSONObject(
				invokeGraphQLQuery(
					new GraphQLField(
						"messageBoardThreads",
						HashMapBuilder.<String, Object>put(
							"flatten", true
						).put(
							"page", 1
						).put(
							"pageSize", 2
						).put(
							"siteKey", "\"" + siteId + "\""
						).build(),
						new GraphQLField("items", getGraphQLFields()),
						new GraphQLField("page"),
						new GraphQLField("totalCount"))),
				"JSONObject/data", "JSONObject/messageBoardThreads");

		Assert.assertEquals(2, messageBoardThreadsJSONObject.get("totalCount"));

		assertEqualsIgnoringOrder(
			Arrays.asList(messageBoardThread1, messageBoardThread2),
			Arrays.asList(
				MessageBoardThreadSerDes.toDTOs(
					messageBoardThreadsJSONObject.getString("items"))));
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
		messageBoardThread.setSubscribed(false);
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

	@Override
	protected MessageBoardThread
			testGraphQLMessageBoardThread_addMessageBoardThread()
		throws Exception {

		return testPostMessageBoardSectionMessageBoardThread_addMessageBoardThread(
			randomMessageBoardThread());
	}

	private MBCategory _mbCategory;

}