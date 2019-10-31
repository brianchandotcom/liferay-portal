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
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@RunWith(Arquillian.class)
public class ProcessWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testAddProcess() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "deleted", false,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "version",
			"1.0");
		retryAssertCount(
			"workflow-metrics-instances", "WorkflowMetricsInstanceType",
			"companyId", kaleoDefinition.getCompanyId(), "deleted", false,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "instanceId",
			0);
		retryAssertCount(
			"workflow-metrics-sla-process-results",
			"WorkflowMetricsSLAProcessResultType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "slaDefinitionId", 0);
	}

	@Test
	public void testDeleteProcess() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		deleteKaleoDefinition(kaleoDefinition);

		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "deleted", true,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "version",
			"1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			_processWorkflowMetricsIndexer,
			new String[] {
				"workflow-metrics-processes", "workflow-metrics-instances",
				"workflow-metrics-sla-process-results"
			},
			new String[] {
				"WorkflowMetricsProcessType", "WorkflowMetricsInstanceType",
				"WorkflowMetricsSLAProcessResultType"
			},
			"companyId", kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Test
	public void testUpdateProcess() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "deleted", false,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "version",
			"1.0");

		updateKaleoDefinition(kaleoDefinition);

		retryAssertCount(
			"workflow-metrics-processes", "WorkflowMetricsProcessType",
			"companyId", kaleoDefinition.getCompanyId(), "deleted", false,
			"processId", kaleoDefinition.getKaleoDefinitionId(), "version",
			"2.0");
	}

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.search.index.ProcessWorkflowMetricsIndexer))"
	)
	private Indexer<Object> _processWorkflowMetricsIndexer;

}