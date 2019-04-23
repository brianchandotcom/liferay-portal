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

import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = SLATaskResultWorkflowMetricsIndexer.class
)
public class SLATaskResultWorkflowMetricsIndexer
	extends SLAProcessResultWorkflowMetricsIndexer {

	public Document createDocument(
		long taskId, String taskName,
		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult) {

		Document document = createDocument(workflowMetricsSLAProcessResult);

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(
				workflowMetricsSLAProcessResult.getCompanyId(),
				workflowMetricsSLAProcessResult.getInstanceId(),
				workflowMetricsSLAProcessResult.getProcessId(),
				workflowMetricsSLAProcessResult.getSLADefinitionId(), taskId));
		document.addKeyword("taskId", taskId);
		document.addKeyword("taskName", taskName);

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

}