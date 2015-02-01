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

package com.liferay.portlet.messageboards.service;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.Collections;
import java.util.List;

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
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testUpdateStatsUserWhenAddingDraftMessage() throws Exception {
		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenAddingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenDeletingDraftMessage() throws Exception {
		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenDeletingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_PUBLISH);

		MBMessageLocalServiceUtil.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenPublishingDraftMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		updateMessage(WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenPublishingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_PUBLISH);

		updateMessage(WorkflowConstants.ACTION_PUBLISH);

		Assert.assertEquals(
			initialStatsUserMessageCount + 1, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenSavingDraftMessageAsDraft()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		updateMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenSavingPublishedMessageAsDraft()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(WorkflowConstants.ACTION_PUBLISH);

		updateMessage(WorkflowConstants.ACTION_SAVE_DRAFT);

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	protected void addMessage(int workflowAction) throws Exception {
		_message = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			workflowAction == WorkflowConstants.ACTION_PUBLISH);
	}

	protected int getStatsUserMessageCount() throws Exception {
		MBStatsUser statsUser = MBStatsUserLocalServiceUtil.getStatsUser(
			_group.getGroupId(), TestPropsValues.getUserId());

		return statsUser.getMessageCount();
	}

	protected void updateMessage(int workflowAction) throws Exception {
		List<ObjectValuePair<String, InputStream>> inputStreamOVPs =
			Collections.emptyList();
		List<String> existingFiles = Collections.emptyList();

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		serviceContext.setWorkflowAction(workflowAction);

		_message = MBMessageLocalServiceUtil.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(),
			_message.getSubject(), _message.getBody(), inputStreamOVPs,
			existingFiles, _message.getPriority(), _message.getAllowPingbacks(),
			serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

	private MBMessage _message;

}