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

package com.liferay.portal.workflow.metrics.internal.search.index.creation.helper;

import com.liferay.portal.background.task.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsReindexBackgroundTaskExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;

import java.io.Serializable;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Inácio Nery
 */
@Component(immediate = true, service = WorkflowMetricsIndexCreator.class)
public class WorkflowMetricsIndexCreator {

	public void createIndex(Company company) throws PortalException {
		if (Objects.isNull(_searchEngineAdapter)) {
			return;
		}

		_instanceWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_nodeWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_processWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_slaInstanceResultWorkflowMetricsIndex.createIndex(
			company.getCompanyId());
		_slaTaskResultWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_taskWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_transitionWorkflowMetricsIndex.createIndex(company.getCompanyId());

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				int count = _backgroundTaskLocalService.getBackgroundTasksCount(
					company.getGroupId(),
					WorkflowMetricsIndexCreator.class.getSimpleName(),
					WorkflowMetricsReindexBackgroundTaskExecutor.class.
						getName(),
					false);

				if (count > 0) {
					return null;
				}

				User user = company.getDefaultUser();

				_backgroundTaskLocalService.addBackgroundTask(
					user.getUserId(), company.getGroupId(),
					WorkflowMetricsIndexCreator.class.getSimpleName(),
					WorkflowMetricsReindexBackgroundTaskExecutor.class.
						getName(),
					HashMapBuilder.<String, Serializable>put(
						BackgroundTaskContextMapConstants.DELETE_ON_SUCCESS,
						true
					).put(
						"workflow.metrics.index.entity.names",
						new String[] {
							"instance", "node", "process",
							"sla-instance-result", "sla-task-result", "task",
							"transition"
						}
					).build(),
					new ServiceContext());

				return null;
			});
	}

	public void removeIndex(Company company) throws PortalException {
		if (Objects.isNull(_searchEngineAdapter)) {
			return;
		}

		_instanceWorkflowMetricsIndex.removeIndex(company.getCompanyId());
		_nodeWorkflowMetricsIndex.removeIndex(company.getCompanyId());
		_processWorkflowMetricsIndex.removeIndex(company.getCompanyId());
		_slaInstanceResultWorkflowMetricsIndex.removeIndex(
			company.getCompanyId());
		_slaTaskResultWorkflowMetricsIndex.removeIndex(company.getCompanyId());
		_taskWorkflowMetricsIndex.removeIndex(company.getCompanyId());
		_transitionWorkflowMetricsIndex.removeIndex(company.getCompanyId());
	}

	@Reference
	private BackgroundTaskLocalService _backgroundTaskLocalService;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _nodeWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _processWorkflowMetricsIndex;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	private volatile SearchEngineAdapter _searchEngineAdapter;

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndex _slaInstanceResultWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndex _slaTaskResultWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndex _taskWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndex _transitionWorkflowMetricsIndex;

}