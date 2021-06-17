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
import com.liferay.blogs.model.BlogsEntry;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.test.rule.DataGuard;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.service.KaleoDefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
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
		assertCount(
			4,
			_nodeWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
			"WorkflowMetricsNodeType", "companyId",
			workflowDefinition.getCompanyId(), "processId",
			workflowDefinition.getWorkflowDefinitionId());
		assertCount(
			_processWorkflowMetricsIndexNameBuilder.getIndexName(
				workflowDefinition.getCompanyId()),
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
				_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
					workflowDefinition.getCompanyId()),
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
					_slaInstanceResultWorkflowMetricsIndexNameBuilder.
						getIndexName(workflowDefinition.getCompanyId()),
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
						_slaInstanceResultWorkflowMetricsIndexNameBuilder.
							getIndexName(workflowDefinition.getCompanyId())
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

	@Inject(filter = "workflow.metrics.index.entity.name=instance")
	private WorkflowMetricsIndexNameBuilder
		_instanceWorkflowMetricsIndexNameBuilder;

	@Inject
	private JSONFactory _jsonFactory;

	@Inject
	private KaleoDefinitionVersionLocalService
		_kaleoDefinitionVersionLocalService;

	@Inject(filter = "workflow.metrics.index.entity.name=node")
	private WorkflowMetricsIndexNameBuilder
		_nodeWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=process")
	private WorkflowMetricsIndexNameBuilder
		_processWorkflowMetricsIndexNameBuilder;

	@Inject(filter = "workflow.metrics.index.entity.name=sla-instance-result")
	private WorkflowMetricsIndexNameBuilder
		_slaInstanceResultWorkflowMetricsIndexNameBuilder;

	@Inject
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Inject(
		filter = "(&(objectClass=com.liferay.portal.workflow.metrics.internal.messaging.WorkflowMetricsSLAProcessMessageListener))"
	)
	private MessageListener _workflowMetricsSLAProcessMessageListener;

}