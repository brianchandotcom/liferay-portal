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

package com.liferay.portal.workflow.metrics.internal.search.index;

import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=sla-task-result",
	service = WorkflowMetricsIndex.class
)
public class SLATaskResultWorkflowMetricsIndexImpl
	extends BaseWorkflowMetricsIndex implements WorkflowMetricsIndex {

	@Override
	public String getIndexName(long companyId) {
		return _workflowMetricsIndexNameBuilder.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

	@Reference(target = "(workflow.metrics.index.entity.name=sla-task-result)")
	private WorkflowMetricsIndexNameBuilder _workflowMetricsIndexNameBuilder;

}