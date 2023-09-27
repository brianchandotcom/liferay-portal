/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0",
			"versions", "1.0");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "instanceId", 0);
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
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
			_indexNameBuilder.getIndexName(companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "companyId", companyId, "deleted",
			true, "processId", workflowDefinitionId, "version", "1.0");
	}

	@Test
	public void testReindex() throws Exception {
		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		assertReindex(
			new String[] {
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
				indexName +
					WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
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
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

}