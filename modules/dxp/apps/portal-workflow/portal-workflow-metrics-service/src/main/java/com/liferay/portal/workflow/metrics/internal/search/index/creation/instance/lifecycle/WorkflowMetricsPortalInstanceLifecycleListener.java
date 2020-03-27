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

package com.liferay.portal.workflow.metrics.internal.search.index.creation.instance.lifecycle;

import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.workflow.metrics.internal.search.index.WorkflowMetricsIndex;

import java.util.Objects;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rafael Praxedes
 */
@Component(immediate = true, service = PortalInstanceLifecycleListener.class)
public class WorkflowMetricsPortalInstanceLifecycleListener
	extends BasePortalInstanceLifecycleListener {

	@Override
	public void portalInstanceRegistered(Company company) throws Exception {
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
	}

	@Override
	public void portalInstanceUnregistered(Company company) throws Exception {
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