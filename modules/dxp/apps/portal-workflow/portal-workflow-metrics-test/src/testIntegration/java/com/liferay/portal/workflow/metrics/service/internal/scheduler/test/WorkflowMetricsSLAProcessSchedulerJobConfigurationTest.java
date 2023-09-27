/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.service.internal.scheduler.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.function.UnsafeRunnable;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.scheduler.SchedulerJobConfiguration;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsIndexerTestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class WorkflowMetricsSLAProcessSchedulerJobConfigurationTest
	extends BaseWorkflowMetricsIndexerTestCase {

	@Test
	public void testProcess() throws Exception {
		assertCount(
			4,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 50000, "Abc",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		_workflowMetricsSLADefinitionLocalService.
			deactivateWorkflowMetricsSLADefinition(
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId(),
				ServiceContextTestUtil.getServiceContext());

		assertCount(
			0,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId",
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());

		KaleoInstance kaleoInstance = getKaleoInstance(addBlogsEntry());

		completeKaleoTaskInstanceToken(kaleoInstance);

		completeKaleoInstance(kaleoInstance);

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", true,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", true,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			0,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());

		UnsafeRunnable<Exception> jobExecutorUnsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		jobExecutorUnsafeRunnable.run();

		assertCount(
			0,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId",
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());

		workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 50000, "Def",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		kaleoInstance = getKaleoInstance(addBlogsEntry());

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			"WorkflowMetricsInstanceType", "className",
			kaleoInstance.getClassName(), "classPK", kaleoInstance.getClassPK(),
			"companyId", kaleoInstance.getCompanyId(), "completed", false,
			"instanceId", kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());

		jobExecutorUnsafeRunnable.run();

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId",
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId(),
			"status", "RUNNING");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId",
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());

		jobExecutorUnsafeRunnable.run();

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT,
			"WorkflowMetricsSLAInstanceResultType", "companyId",
			workflowDefinition.getCompanyId(), "instanceId",
			kaleoInstance.getKaleoInstanceId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "slaDefinitionId",
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.metrics.internal.scheduler.WorkflowMetricsSLAProcessSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}