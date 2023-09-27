/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.time.Duration;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class TaskWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testAddTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");
	}

	@Test
	public void testAssignTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");

		kaleoTaskInstanceToken = assignKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "assigneeIds",
			TestPropsValues.getUserId(), "assigneeType", User.class.getName(),
			"companyId", workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testCompleteTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");

		kaleoTaskInstanceToken = completeKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

		Date createDate = kaleoTaskInstanceToken.getCreateDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "assigneeIds",
			TestPropsValues.getUserId(), "assigneeType", User.class.getName(),
			"companyId", workflowDefinition.getCompanyId(), "duration",
			duration.toMillis(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testDeleteTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		deleteKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			true, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		assertReindex(
			new String[] {
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_TASK
			},
			new String[] {"WorkflowMetricsTaskType"}, "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

}