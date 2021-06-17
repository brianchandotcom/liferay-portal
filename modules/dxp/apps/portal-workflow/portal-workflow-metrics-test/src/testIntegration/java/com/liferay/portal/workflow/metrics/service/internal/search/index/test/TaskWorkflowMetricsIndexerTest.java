/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
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
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");

		kaleoTaskInstanceToken = assignKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		assertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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

		assertReindex(
			new String[] {
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId())
			},
			new String[] {"WorkflowMetricsTaskType"}, "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");
	}

	@Inject(filter = "workflow.metrics.index.entity.name=task")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

}