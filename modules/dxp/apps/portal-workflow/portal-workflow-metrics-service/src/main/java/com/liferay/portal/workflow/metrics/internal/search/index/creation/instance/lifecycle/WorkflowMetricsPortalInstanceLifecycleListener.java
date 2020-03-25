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
import com.liferay.portal.workflow.metrics.internal.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.NodeWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.ProcessWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAInstanceResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.TokenWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.TransitionWorkflowMetricsIndexer;

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

		_instanceWorkflowMetricsIndexer.createIndex(company.getCompanyId());
		_nodeWorkflowMetricsIndexer.createIndex(company.getCompanyId());
		_processWorkflowMetricsIndexer.createIndex(company.getCompanyId());
		_slaInstanceResultWorkflowMetricsIndexer.createIndex(
			company.getCompanyId());
		_slaTaskResultWorkflowMetricsIndexer.createIndex(
			company.getCompanyId());
		_tokenWorkflowMetricsIndexer.createIndex(company.getCompanyId());
		_transitionWorkflowMetricsIndexer.createIndex(company.getCompanyId());
	}

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference
	private NodeWorkflowMetricsIndexer _nodeWorkflowMetricsIndexer;

	@Reference
	private ProcessWorkflowMetricsIndexer _processWorkflowMetricsIndexer;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		target = "(search.engine.impl=Elasticsearch)"
	)
	private volatile SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private TokenWorkflowMetricsIndexer _tokenWorkflowMetricsIndexer;

	@Reference
	private TransitionWorkflowMetricsIndexer _transitionWorkflowMetricsIndexer;

}