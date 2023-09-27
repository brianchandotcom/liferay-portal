/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.definition.NodeType;
import com.liferay.portal.workflow.kaleo.definition.State;
import com.liferay.portal.workflow.kaleo.definition.Task;
import com.liferay.portal.workflow.kaleo.definition.Transition;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTask;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class NodeWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testAddStateNode() throws Exception {
		State startState = new State("start", StringPool.BLANK, true);

		startState.addOutgoingTransition(
			new Transition(
				true, null, "review", startState,
				new Task("review", StringPool.BLANK)));

		KaleoNode kaleoNode = addKaleoNode(startState);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "initial",
			true, "name", "start", "nodeId", kaleoNode.getKaleoNodeId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", false, "type", NodeType.STATE.toString(), "version",
			"1.0");

		kaleoNode = addKaleoNode(new State("end", StringPool.BLANK, false));

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "initial",
			false, "name", "end", "nodeId", kaleoNode.getKaleoNodeId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", true, "type", NodeType.STATE.toString(), "version",
			"1.0");
	}

	@Test
	public void testAddTaskNode() throws Exception {
		Task reviewTask = new Task("review", StringPool.BLANK);

		reviewTask.setAssignments(Collections.emptySet());

		KaleoTask kaleoTask = addKaleoTask(reviewTask);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "initial",
			false, "name", "review", "nodeId", kaleoTask.getKaleoTaskId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", false, "type", NodeType.TASK.toString(), "version",
			"1.0");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
			"WorkflowMetricsSLATaskResultType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "instanceId",
			0, "processId", workflowDefinition.getWorkflowDefinitionId(),
			"slaDefinitionId", 0, "nodeId", kaleoTask.getKaleoTaskId(),
			"taskName", "review");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK,
			"WorkflowMetricsTaskType", "companyId",
			workflowDefinition.getCompanyId(), "completed", false, "deleted",
			false, "instanceId", 0, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "nodeId",
			kaleoTask.getKaleoTaskId(), "name", "review", "taskId", 0,
			"version", "1.0");
	}

	@Test
	public void testDeleteStateNode() throws Exception {
		KaleoNode kaleoNode = addKaleoNode(
			new State("end", StringPool.BLANK, false));

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "initial",
			false, "name", "end", "nodeId", kaleoNode.getKaleoNodeId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", true, "type", NodeType.STATE.toString(), "version",
			"1.0");

		deleteKaleoNode(kaleoNode);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", true, "initial",
			false, "name", "end", "nodeId", kaleoNode.getKaleoNodeId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", true, "type", NodeType.STATE.toString(), "version",
			"1.0");
	}

	@Test
	public void testDeleteTaskNode() throws Exception {
		Task reviewTask = new Task("review", StringPool.BLANK);

		reviewTask.setAssignments(Collections.emptySet());

		KaleoTask kaleoTask = addKaleoTask(reviewTask);

		deleteKaleoTask(kaleoTask);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", true, "initial",
			false, "name", "review", "nodeId", kaleoTask.getKaleoTaskId(),
			"processId", workflowDefinition.getWorkflowDefinitionId(),
			"terminal", false, "type", NodeType.TASK.toString(), "version",
			"1.0");
	}

	@Test
	public void testReindex() throws Exception {
		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		assertReindex(
			LinkedHashMapBuilder.put(
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_NODE, 4
			).put(
				indexName +
					WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
				2
			).put(
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_TASK, 2
			).build(),
			new String[] {
				"WorkflowMetricsNodeType", "WorkflowMetricsSLATaskResultType",
				"WorkflowMetricsTaskType"
			},
			"companyId", workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

}