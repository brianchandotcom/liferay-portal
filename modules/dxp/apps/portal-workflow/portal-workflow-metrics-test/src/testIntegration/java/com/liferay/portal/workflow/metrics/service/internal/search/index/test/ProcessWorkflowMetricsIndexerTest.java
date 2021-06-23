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
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
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
		assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0",
			"versions", "1.0");
		assertCount(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsInstanceType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "instanceId", 0);
		assertCount(
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId", 0);
	}

	@Test
	public void testDeleteProcess() throws Exception {
		long companyId = workflowDefinition.getCompanyId();
		long workflowDefinitionId =
			workflowDefinition.getWorkflowDefinitionId();

		undeployWorkflowDefinition();

		assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(companyId),
			"WorkflowMetricsProcessType", "companyId", companyId, "deleted",
			true, "processId", workflowDefinitionId, "version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		assertReindex(
			new String[] {
				_processWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId()),
				_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId()),
				_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId())
			},
			new String[] {
				"WorkflowMetricsProcessType", "WorkflowMetricsInstanceType",
				"WorkflowMetricsSLAInstanceResultType"
			},
			"companyId", workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
	}

	@Test
	public void testUpdateProcess() throws Exception {
		assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");

		updateWorkflowDefinition();

		assertCount(
			booleanQuery -> {
				TermsQuery termsQuery = queries.terms("versions");

				termsQuery.addValues("1.0", "2.0");

				booleanQuery.addMustQueryClauses(termsQuery);
			},
			1,
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
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