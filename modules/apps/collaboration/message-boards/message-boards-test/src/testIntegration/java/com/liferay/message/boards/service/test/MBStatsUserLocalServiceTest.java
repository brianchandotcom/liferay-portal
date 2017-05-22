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

package com.liferay.message.boards.service.test;

import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBStatsUser;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.message.boards.kernel.service.MBStatsUserLocalServiceUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 */
public class MBStatsUserLocalServiceTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpdateStatsUserWhenAddingDraftMessage() throws Exception {
		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(false);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenAddingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(true);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenDeletingDraftMessage() throws Exception {
		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(false);

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenDeletingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(true);

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenPublishingDraftMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(false);

		updateMessage(WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenPublishingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(true);

		updateMessage(WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenSavingDraftMessageAsDraft()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(false);

		updateMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenSavingPublishedMessageAsDraft()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(true);

		updateMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	protected void addMessage(boolean approved) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		_message = MBTestUtil.addMessageWithWorkflow(
			TestPropsValues.getUserId(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			approved, serviceContext);
	}

	protected int getStatsUserMessageCount() throws Exception {
		MBStatsUser statsUser = MBStatsUserLocalServiceUtil.getStatsUser(
			_group.getGroupId(), TestPropsValues.getUserId());

		return statsUser.getMessageCount();
	}

	protected void updateMessage(int workflowAction) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(workflowAction);

		_message = MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(),
			_message.getBody(), serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private MBMessage _message;

}