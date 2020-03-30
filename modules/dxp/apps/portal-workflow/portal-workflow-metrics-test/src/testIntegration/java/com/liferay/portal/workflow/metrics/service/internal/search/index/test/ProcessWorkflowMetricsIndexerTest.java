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
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
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
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");
		retryAssertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "instanceId", 0);
		retryAssertCount(
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "slaDefinitionId", 0);
	}

	@Test
	public void testDeleteProcess() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		undeployWorkflowDefinition();

		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", true, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		assertReindex(
			new String[] {
				_processWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId()),
				_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId()),
				_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
					kaleoDefinition.getCompanyId())
			},
			new String[] {
				"WorkflowMetricsProcessType", "WorkflowMetricsInstanceType",
				"WorkflowMetricsSLAInstanceResultType"
			},
			"companyId", kaleoDefinition.getCompanyId(), "processId",
			kaleoDefinition.getKaleoDefinitionId());
	}

	@Test
	public void testUpdateProcess() throws Exception {
		KaleoDefinition kaleoDefinition = getKaleoDefinition();

		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "1.0");

		updateKaleoDefinition();

		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				kaleoDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			kaleoDefinition.getCompanyId(), "deleted", false, "processId",
			kaleoDefinition.getKaleoDefinitionId(), "version", "2.0");
	}

	@Inject(filter = "workflow.metrics.index.entity.name=instance")
	private WorkflowMetricsIndexNameBuilder
		_instanceWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=process")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=sla-instance-result")
	private WorkflowMetricsIndexNameBuilder
		_slaInstanceResultWorkflowMetricsIndexNameBuilder;

}