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
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.util.Collections;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class NodeWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddStateNode() throws Exception {
		State startState = new State("start", StringPool.BLANK, true);

		startState.addOutgoingTransition(
			new Transition(
				"review", startState, new Task("review", StringPool.BLANK),
				true));

		KaleoNode kaleoNode = addKaleoNode(startState);

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "initial", true,
			"name", "start", "nodeId", kaleoNode.getKaleoNodeId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", false, "type",
			NodeType.STATE.toString(), "version", "1.0");

		kaleoNode = addKaleoNode(
			kaleoDefinition, new State("end", StringPool.BLANK, false));

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "initial", false,
			"name", "end", "nodeId", kaleoNode.getKaleoNodeId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", true, "type",
			NodeType.STATE.toString(), "version", "1.0");
	}

	@Test
	public void testAddTaskNode() throws Exception {
		Task reviewTask = new Task("review", StringPool.BLANK);

		reviewTask.setAssignments(Collections.emptySet());

		KaleoTask kaleoTask = addKaleoTask(reviewTask);

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "initial", false,
			"name", "review", "nodeId", kaleoTask.getKaleoTaskId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", false, "type",
			NodeType.TASK.toString(), "version", "1.0");
		retryAssertCount(
			_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsSLATaskResultType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "instanceId", 0,
			"processId", kaleoDefinition.getKaleoDefinitionId(),
			"slaDefinitionId", 0, "nodeId", kaleoTask.getKaleoTaskId(),
			"taskName", "review");
		retryAssertCount(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsTaskType", "companyId",
			kaleoDefinition.getCompanyId(), "completed", false, "deleted",
			false, "instanceId", 0, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "nodeId",
			kaleoTask.getKaleoTaskId(), "name", "review", "taskId", 0,
			"version", "1.0");
	}

	@Test
	public void testDeleteStateNode() throws Exception {
		KaleoNode kaleoNode = addKaleoNode(
			new State("end", StringPool.BLANK, false));

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "initial", false,
			"name", "end", "nodeId", kaleoNode.getKaleoNodeId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", true, "type",
			NodeType.STATE.toString(), "version", "1.0");

		deleteKaleoNode(kaleoNode);

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", true, "initial", false,
			"name", "end", "nodeId", kaleoNode.getKaleoNodeId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", true, "type",
			NodeType.STATE.toString(), "version", "1.0");
	}

	@Test
	public void testDeleteTaskNode() throws Exception {
		Task reviewTask = new Task("review", StringPool.BLANK);

		reviewTask.setAssignments(Collections.emptySet());

		KaleoTask kaleoTask = addKaleoTask(reviewTask);

		deleteKaleoTask(kaleoTask);

		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", true, "initial", false,
			"name", "review", "nodeId", kaleoTask.getKaleoTaskId(), "processId",
			kaleoDefinition.getKaleoDefinitionId(), "terminal", false, "type",
			NodeType.TASK.toString(), "version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			LinkedHashMapBuilder.put(
				_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId()),
				4
			).put(
				_slaTaskResultWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId()),
				2
			).put(
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId()),
				2
			).build(),
			new String[] {
				"WorkflowMetricsNodeType", "WorkflowMetricsSLATaskResultType",
				"WorkflowMetricsTaskType"
			},
			"companyId", kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Inject(filter = "workflow.metrics.index.entity.name=node")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=sla-task-result")
	private WorkflowMetricsIndexNameBuilder
		_slaTaskResultWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=task")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

}