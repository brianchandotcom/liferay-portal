/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.message.boards.service.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.message.boards.constants.MBCategoryConstants;
import com.liferay.message.boards.model.MBMessage;
import com.liferay.message.boards.service.MBMessageLocalService;
import com.liferay.message.boards.service.MBStatsUserLocalService;
import com.liferay.message.boards.test.util.MBTestUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
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

		_mbMessageLocalService.deleteMessage(_message.getMessageId());

		Assert.assertEquals(
			initialStatsUserMessageCount, getStatsUserMessageCount());
	}

	@Test
	public void testUpdateStatsUserWhenDeletingPublishedMessage()
		throws Exception {

		int initialStatsUserMessageCount = getStatsUserMessageCount();

		addMessage(true);

		_mbMessageLocalService.deleteMessage(_message.getMessageId());

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
		_message = MBTestUtil.addMessageWithWorkflow(
			_group.getGroupId(), MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			approved,
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId()));
	}

	protected int getStatsUserMessageCount() throws Exception {
		return _mbStatsUserLocalService.getMessageCount(
			_group.getGroupId(), TestPropsValues.getUserId());
	}

	protected void updateMessage(int workflowAction) throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		serviceContext.setWorkflowAction(workflowAction);

		_message = _mbMessageLocalService.updateMessage(
			TestPropsValues.getUserId(), _message.getMessageId(),
			_message.getBody(), serviceContext);
	}

	@Inject
	private static MBMessageLocalService _mbMessageLocalService;

	@Inject
	private static MBStatsUserLocalService _mbStatsUserLocalService;

	@DeleteAfterTestRun
	private Group _group;

	private MBMessage _message;

}