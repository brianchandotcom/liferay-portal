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
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.service.util.BaseWorkflowMetricsTestCase;
import com.liferay.portal.workflow.metrics.service.util.WorkflowDefinitionUtil;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Rafael Praxedes
 */
@DataGuard(scope = DataGuard.Scope.METHOD)
@RunWith(Arquillian.class)
public class
	WorkflowMetricsSLADefinitionTransformerSchedulerJobConfigurationTest
		extends BaseWorkflowMetricsTestCase {

	@Test
	public void testTransform1() throws Exception {
		assertCount(
			4,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Abc",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {getInitialNodeKey(workflowDefinition)},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		updateWorkflowDefinition();

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
		assertCount(
			4,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");

		UnsafeRunnable<Exception> jobExecutorUnsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		jobExecutorUnsafeRunnable.run();

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				_workflowMetricsSLADefinitionVersionLocalService.
					getWorkflowMetricsSLADefinitionVersion(
						workflowMetricsSLADefinition.
							getWorkflowMetricsSLADefinitionId(),
						"2.0");

		Assert.assertEquals(
			workflowDefinition.getWorkflowDefinitionId(),
			workflowMetricsSLADefinitionVersion.getProcessId());
		Assert.assertEquals(
			getInitialNodeKey(workflowDefinition, "2.0"),
			workflowMetricsSLADefinitionVersion.getStartNodeKeys());
		Assert.assertEquals(
			getTerminalNodeKey(workflowDefinition, "2.0"),
			workflowMetricsSLADefinitionVersion.getStopNodeKeys());
	}

	@Test
	public void testTransform2() throws Exception {
		assertCount(
			4,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");
		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				addWorkflowMetricsSLADefinition(
					StringPool.BLANK, StringPool.BLANK, 5000, "Abc",
					new String[0], workflowDefinition.getWorkflowDefinitionId(),
					new String[] {
						getTaskName(workflowDefinition, "review") + ":enter"
					},
					new String[] {getTerminalNodeKey(workflowDefinition)},
					ServiceContextTestUtil.getServiceContext());

		updateWorkflowDefinition(
			WorkflowDefinitionUtil.getBytes(
				"single-approver-updated-workflow-definition.xml"));

		assertCount(
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS,
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
		assertCount(
			4,
			_indexNameBuilder.getIndexName(workflowDefinition.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE,
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");

		UnsafeRunnable<Exception> jobExecutorUnsafeRunnable =
			_schedulerJobConfiguration.getJobExecutorUnsafeRunnable();

		jobExecutorUnsafeRunnable.run();

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				_workflowMetricsSLADefinitionVersionLocalService.
					getWorkflowMetricsSLADefinitionVersion(
						workflowMetricsSLADefinition.
							getWorkflowMetricsSLADefinitionId(),
						"2.0");

		Assert.assertEquals(
			workflowDefinition.getWorkflowDefinitionId(),
			workflowMetricsSLADefinitionVersion.getProcessId());
		Assert.assertEquals(
			StringPool.BLANK,
			workflowMetricsSLADefinitionVersion.getStartNodeKeys());
		Assert.assertEquals(
			WorkflowConstants.STATUS_DRAFT,
			workflowMetricsSLADefinitionVersion.getStatus());
	}

	@Inject
	private IndexNameBuilder _indexNameBuilder;

	@Inject(
		filter = "component.name=com.liferay.portal.workflow.metrics.internal.scheduler.WorkflowMetricsSLADefinitionTransformerSchedulerJobConfiguration"
	)
	private SchedulerJobConfiguration _schedulerJobConfiguration;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Inject
	private WorkflowMetricsSLADefinitionVersionLocalService
		_workflowMetricsSLADefinitionVersionLocalService;

}