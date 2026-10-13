/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.depot.workflow.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.depot.constants.DepotConstants;
import com.liferay.depot.model.DepotEntry;
import com.liferay.depot.service.DepotEntryLocalService;
import com.liferay.depot.test.util.DepotTestUtil;
import com.liferay.journal.constants.JournalArticleConstants;
import com.liferay.journal.constants.JournalFolderConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.journal.test.util.JournalTestUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.WorkflowInstanceLink;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalService;
import com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManager;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.PermissionCheckerMethodTestRule;

import java.util.Collections;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Jürgen Kappler
 */
@RunWith(Arquillian.class)
public class WorkflowTaskManagerTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			PermissionCheckerMethodTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_depotEntry = _depotEntryLocalService.addDepotEntry(
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			Collections.singletonMap(
				LocaleUtil.getDefault(), RandomTestUtil.randomString()),
			DepotConstants.TYPE_ASSET_LIBRARY,
			ServiceContextTestUtil.getServiceContext());

		_workflowDefinitionLinkLocalService.updateWorkflowDefinitionLink(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			_depotEntry.getGroupId(), JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL, "Single Approver", 1);
	}

	@After
	public void tearDown() throws Exception {
		_workflowDefinitionLinkLocalService.deleteWorkflowDefinitionLink(
			TestPropsValues.getCompanyId(), _depotEntry.getGroupId(),
			JournalFolder.class.getName(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			JournalArticleConstants.DDM_STRUCTURE_ID_ALL);
	}

	@Test
	public void testCompleteWorkflowTask() throws Exception {
		_testCompleteWorkflowTaskWithApprove();
		_testCompleteWorkflowTaskWithReject();
	}

	private void _assertAssignableUser(User user, WorkflowTask workflowTask)
		throws Exception {

		Assert.assertTrue(
			ListUtil.exists(
				_workflowTaskManager.getAssignableUsers(
					workflowTask.getWorkflowTaskId()),
				assignableUser ->
					assignableUser.getUserId() == user.getUserId()));
	}

	private void _completeWorkflowTask(
			JournalArticle article, String transitionName, User user)
		throws Exception {

		WorkflowTask workflowTask = _getWorkflowTask(article);

		_assertAssignableUser(user, workflowTask);

		_workflowTaskManager.assignWorkflowTaskToUser(
			TestPropsValues.getCompanyId(), user.getUserId(),
			workflowTask.getWorkflowTaskId(), user.getUserId(), null, null,
			null);

		_workflowTaskManager.completeWorkflowTask(
			TestPropsValues.getCompanyId(), user.getUserId(),
			workflowTask.getWorkflowTaskId(), transitionName, null, null);
	}

	private int _getStatus(JournalArticle article) throws Exception {
		article = _journalArticleLocalService.getArticle(article.getId());

		return article.getStatus();
	}

	private WorkflowTask _getWorkflowTask(JournalArticle article)
		throws Exception {

		WorkflowInstanceLink workflowInstanceLink =
			_workflowInstanceLinkLocalService.getWorkflowInstanceLink(
				TestPropsValues.getCompanyId(), _depotEntry.getGroupId(),
				JournalArticle.class.getName(), article.getId());

		List<WorkflowTask> workflowTasks =
			_workflowTaskManager.getWorkflowTasksByWorkflowInstance(
				TestPropsValues.getCompanyId(), null,
				workflowInstanceLink.getWorkflowInstanceId(), false, 0, 1,
				null);

		return workflowTasks.get(0);
	}

	private void _testCompleteWorkflowTaskWithApprove() throws Exception {
		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, article.getStatus());

		DepotTestUtil.withAssetLibraryContentReviewer(
			_depotEntry,
			user -> _completeWorkflowTask(article, "approve", user));

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, _getStatus(article));
	}

	private void _testCompleteWorkflowTaskWithReject() throws Exception {
		JournalArticle article = JournalTestUtil.addArticleWithWorkflow(
			_depotEntry.getGroupId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID, true);

		DepotTestUtil.withAssetLibraryContentReviewer(
			_depotEntry,
			user -> _completeWorkflowTask(article, "reject", user));

		WorkflowTask workflowTask = _getWorkflowTask(article);

		Assert.assertEquals("update", workflowTask.getName());

		Assert.assertEquals(
			WorkflowConstants.STATUS_PENDING, _getStatus(article));
	}

	@DeleteAfterTestRun
	private DepotEntry _depotEntry;

	@Inject
	private DepotEntryLocalService _depotEntryLocalService;

	@Inject
	private JournalArticleLocalService _journalArticleLocalService;

	@Inject
	private WorkflowDefinitionLinkLocalService
		_workflowDefinitionLinkLocalService;

	@Inject
	private WorkflowInstanceLinkLocalService _workflowInstanceLinkLocalService;

	@Inject
	private WorkflowTaskManager _workflowTaskManager;

}