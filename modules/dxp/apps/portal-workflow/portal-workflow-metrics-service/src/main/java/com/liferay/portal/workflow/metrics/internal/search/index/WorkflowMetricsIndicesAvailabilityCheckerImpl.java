/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.kernel.cache.PortalCache;
import com.liferay.portal.kernel.cache.SingleVMPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexRequest;
import com.liferay.portal.search.engine.adapter.index.GetIndexIndexResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndicesAvailabilityChecker;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Feliphe Marinho
 */
@Component(service = WorkflowMetricsIndicesAvailabilityChecker.class)
public class WorkflowMetricsIndicesAvailabilityCheckerImpl
	implements WorkflowMetricsIndicesAvailabilityChecker {

	@Override
	public boolean check(long companyId) {
		if (!_searchCapabilities.isWorkflowMetricsSupported()) {
			return false;
		}

		if (GetterUtil.getBoolean(portalCache.get(companyId))) {
			return true;
		}

		GetIndexIndexResponse getIndexIndexResponse =
			searchEngineAdapter.execute(
				new GetIndexIndexRequest(
					_indexNameBuilder.getIndexName(companyId) +
						"-workflow-metrics-*"));

		boolean containsAll = ArrayUtil.containsAll(
			getIndexIndexResponse.getIndexNames(),
			new String[] {
				_instanceWorkflowMetricsIndex.getIndexName(companyId),
				_nodeWorkflowMetricsIndex.getIndexName(companyId),
				_processWorkflowMetricsIndex.getIndexName(companyId),
				_slaInstanceResultWorkflowMetricsIndex.getIndexName(companyId),
				_slaTaskResultWorkflowMetricsIndex.getIndexName(companyId),
				_taskWorkflowMetricsIndex.getIndexName(companyId),
				_transitionWorkflowMetricsIndex.getIndexName(companyId)
			});

		portalCache.put(companyId, containsAll);

		return containsAll;
	}

	@Override
	public void clearCache(long company) {
		portalCache.remove(company);
	}

	@Activate
	protected void activate() {
		if (portalCache != null) {
			return;
		}

		portalCache = (PortalCache<Long, Boolean>)singleVMPool.getPortalCache(
			WorkflowMetricsIndicesAvailabilityChecker.class.getName());
	}

	@Deactivate
	protected void deactivate() {
		singleVMPool.removePortalCache(
			WorkflowMetricsIndicesAvailabilityChecker.class.getName());
	}

	protected PortalCache<Long, Boolean> portalCache;

	@Reference
	protected SearchEngineAdapter searchEngineAdapter;

	@Reference
	protected SingleVMPool singleVMPool;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=node)")
	private WorkflowMetricsIndex _nodeWorkflowMetricsIndex;

	@Reference(target = "(workflow.metrics.index.entity.name=process)")
	private WorkflowMetricsIndex _processWorkflowMetricsIndex;

	@Reference
	private SearchCapabilities _searchCapabilities;

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