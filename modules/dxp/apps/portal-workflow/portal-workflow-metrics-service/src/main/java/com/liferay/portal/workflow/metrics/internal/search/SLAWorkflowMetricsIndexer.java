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

package com.liferay.portal.workflow.metrics.internal.search;

import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAInstanceResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = {Indexer.class, SLAWorkflowMetricsIndexer.class}
)
public class SLAWorkflowMetricsIndexer extends WorkflowMetricsIndexer {

	@Override
	public String getClassName() {
		return SLAWorkflowMetricsIndexer.class.getName();
	}

	@Activate
	protected void activate() throws Exception {
		createIndices(
			_slaInstanceResultWorkflowMetricsIndexer,
			_slaTaskResultWorkflowMetricsIndexer);
	}

	@Override
	protected void doReindex(String[] ids) throws Exception {
		long companyId = GetterUtil.getLong(ids[0]);

		deleteIndices(
			companyId, _slaInstanceResultWorkflowMetricsIndexer,
			_slaTaskResultWorkflowMetricsIndexer);

		createIndices(
			_slaInstanceResultWorkflowMetricsIndexer,
			_slaTaskResultWorkflowMetricsIndexer);

		_slaInstanceResultWorkflowMetricsIndexer.reindex(companyId);
		_slaTaskResultWorkflowMetricsIndexer.reindex(companyId);
	}

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

}