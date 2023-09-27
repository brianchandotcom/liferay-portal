/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.search.index.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class SLAInstanceResultWorkflowMetricsIndexerTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testReindex() throws Exception {
		String indexName = _indexNameBuilder.getIndexName(
			workflowDefinition.getCompanyId());

		assertCount(
			4, indexName + WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			indexName + WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());

		List<BlogsEntry> blogsEntries = ListUtil.fromArray(
			addBlogsEntry(), addBlogsEntry());

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			new ArrayList<>();

		workflowMetricsSLADefinitions.add(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Abc",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext()));
		workflowMetricsSLADefinitions.add(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Def",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext()));
		workflowMetricsSLADefinitions.add(
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Ghi",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext()));

		WorkflowMetricsSLADefinition firstWorkflowMetricsSLADefinition =
			workflowMetricsSLADefinitions.remove(0);

		_workflowMetricsSLADefinitionLocalService.
			deactivateWorkflowMetricsSLADefinition(
				firstWorkflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId(),
				ServiceContextTestUtil.getServiceContext());

		for (BlogsEntry blogsEntry : blogsEntries) {
			KaleoInstance kaleoInstance = getKaleoInstance(blogsEntry);

			completeKaleoTaskInstanceToken(kaleoInstance);

			completeKaleoInstanceToken(kaleoInstance);

			completeKaleoInstance(kaleoInstance);

			assertCount(
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
				"WorkflowMetricsInstanceType", "className",
				kaleoInstance.getClassName(), "classPK",
				kaleoInstance.getClassPK(), "companyId",
				kaleoInstance.getCompanyId(), "completed", true, "instanceId",
				kaleoInstance.getKaleoInstanceId(), "processId",
				workflowDefinition.getWorkflowDefinitionId());
		}

		for (BlogsEntry blogsEntry : blogsEntries) {
			KaleoInstance kaleoInstance = getKaleoInstance(blogsEntry);

			assertSLAReindex(
				LinkedHashMapBuilder.put(
					indexName +
						WorkflowMetricsIndexNameConstants.
							SUFFIX_SLA_INSTANCE_RESULT,
					2
				).build(),
				new String[] {"WorkflowMetricsSLAInstanceResultType"},
				"companyId", workflowDefinition.getCompanyId(), "instanceId",
				kaleoInstance.getKaleoInstanceId(), "processId",
				workflowDefinition.getWorkflowDefinitionId());
		}

		for (WorkflowMetricsSLADefinition workflowMetricsSLADefinition :
				workflowMetricsSLADefinitions) {

			for (BlogsEntry blogsEntry : blogsEntries) {
				KaleoInstance kaleoInstance = getKaleoInstance(blogsEntry);

				assertSLAReindex(
					new String[] {
						indexName +
							WorkflowMetricsIndexNameConstants.
								SUFFIX_SLA_INSTANCE_RESULT
					},
					new String[] {"WorkflowMetricsSLAInstanceResultType"},
					"companyId", workflowDefinition.getCompanyId(),
					"instanceId", kaleoInstance.getKaleoInstanceId(),
					"processId", workflowDefinition.getWorkflowDefinitionId(),
					"slaDefinitionId",
					workflowMetricsSLADefinition.
						getWorkflowMetricsSLADefinitionId());
			}
		}
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}