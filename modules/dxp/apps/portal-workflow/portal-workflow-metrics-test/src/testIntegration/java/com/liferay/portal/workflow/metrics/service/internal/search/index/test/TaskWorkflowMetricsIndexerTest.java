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
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.time.Duration;

import java.util.Date;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class TaskWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");
	}

	@Test
	public void testAssignTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");

		kaleoTaskInstanceToken = assignKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "assigneeId",
			TestPropsValues.getUserId(), "companyId",
			kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testCompleteTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "processId", kaleoDefinition.getKaleoDefinitionId(),
			"nodeId", kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review",
			"taskId", kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(),
			"version", "1.0");

		kaleoTaskInstanceToken = completeKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);

		Date completionDate = kaleoTaskInstanceToken.getCompletionDate();

		Date createDate = kaleoTaskInstanceToken.getCreateDate();

		Duration duration = Duration.between(
			createDate.toInstant(), completionDate.toInstant());

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "assigneeId",
			TestPropsValues.getUserId(), "companyId",
			kaleoDefinition.getCompanyId(), "duration", duration.toMillis(),
			"processId", kaleoDefinition.getKaleoDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());
	}

	@Test
	public void testDeleteTask() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		deleteKaleoTaskInstanceToken(kaleoTaskInstanceToken);

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted", true,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			addKaleoTaskInstanceToken("review");

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			new String[] {
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId())
			},
			new String[] {"WorkflowMetricsTaskType"}, "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "nodeId",
			kaleoTaskInstanceToken.getKaleoTaskId(), "name", "review", "taskId",
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId(), "version",
			"1.0");
	}

	@Inject(filter = "workflow.metrics.index.entity.name=task")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

}