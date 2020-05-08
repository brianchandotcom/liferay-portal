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

package com.liferay.portal.workflow.metrics.service.internal.messaging.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
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
public class WorkflowMetricsSLADefinitionTransformerMessageListenerTest
	extends BaseWorkflowMetricsTestCase {

	@Test
	public void testTransform1() throws Exception {
		retryAssertCount(
			4,
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");
		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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

		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
		retryAssertCount(
			4,
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");

		_workflowMetricsSLADefinitionTransformerMessageListener.receive(
			new Message());

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
		retryAssertCount(
			4,
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "1.0");
		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
				"single-approver-definition-updated.xml"));

		retryAssertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsProcessType", "active", true, "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");
		retryAssertCount(
			4,
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "deleted", false, "processId",
			workflowDefinition.getWorkflowDefinitionId(), "version", "2.0");

		_workflowMetricsSLADefinitionTransformerMessageListener.receive(
			new Message());

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

	@Inject(filter = "workflow.metrics.index.entity.name=node")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=process")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.messaging.WorkflowMetricsSLADefinitionTransformerMessageListener))"
	)
	private MessageListener
		_workflowMetricsSLADefinitionTransformerMessageListener;

	@Inject
	private WorkflowMetricsSLADefinitionVersionLocalService
		_workflowMetricsSLADefinitionVersionLocalService;

}