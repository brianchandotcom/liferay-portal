/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index.creation.helper;

import com.liferay.portal.background.task.service.BackgroundTaskLocalService;
import com.liferay.portal.kernel.backgroundtask.constants.BackgroundTaskContextMapConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.transaction.TransactionCommitCallbackUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.CountSearchRequest;
import com.liferay.portal.search.engine.adapter.search.CountSearchResponse;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsReindexBackgroundTaskExecutor;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;

import java.io.Serializable;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Inácio Nery
 */
@Component(service = WorkflowMetricsIndexCreator.class)
public class WorkflowMetricsIndexCreator {

	public void createIndex(Company company) throws PortalException {
		boolean indexCreated = _instanceWorkflowMetricsIndex.createIndex(
			company.getCompanyId());

		if (!indexCreated) {
			return;
		}

		_nodeWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_processWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_slaInstanceResultWorkflowMetricsIndex.createIndex(
			company.getCompanyId());
		_slaTaskResultWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_taskWorkflowMetricsIndex.createIndex(company.getCompanyId());
		_transitionWorkflowMetricsIndex.createIndex(company.getCompanyId());
	}

	public void reindex(Company company) {
		if (!_searchCapabilities.isWorkflowMetricsSupported()) {
			return;
		}

		TransactionCommitCallbackUtil.registerCallback(
			() -> {
				CountSearchRequest countSearchRequest =
					new CountSearchRequest();

				countSearchRequest.setIndexNames(
					_processWorkflowMetricsIndex.getIndexName(
						company.getCompanyId()));
				countSearchRequest.setQuery(_queries.booleanQuery());

				CountSearchResponse countSearchResponse =
					_searchEngineAdapter.execute(countSearchRequest);

				if (countSearchResponse.getCount() > 0) {
					return null;
				}

				User user = company.getGuestUser();

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
		boolean indexRemoved = _instanceWorkflowMetricsIndex.removeIndex(
			company.getCompanyId());

		if (!indexRemoved) {
			return;
		}

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

	@Reference
	private Queries _queries;

	@Reference
	private SearchCapabilities _searchCapabilities;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

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