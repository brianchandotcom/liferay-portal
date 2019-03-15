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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;

import java.sql.Timestamp;

import org.osgi.service.component.annotations.Component;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = SLAProcessResultWorkflowMetricsIndexer.class
)
public class SLAProcessResultWorkflowMetricsIndexer
	extends BaseWorkflowMetricsIndexer<WorkflowMetricsSLAProcessResult> {

	@Override
	protected Document createDocument(
		WorkflowMetricsSLAProcessResult workflowMetricsSLAProcessResult) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLAProcessResult",
			digest(
				workflowMetricsSLAProcessResult.getCompanyId(),
				workflowMetricsSLAProcessResult.getInstanceId(),
				workflowMetricsSLAProcessResult.getProcessId(),
				workflowMetricsSLAProcessResult.getSLADefinitionId()));

		document.addKeyword(
			"companyId", workflowMetricsSLAProcessResult.getCompanyId());
		document.addKeyword(
			"elapsedTime", workflowMetricsSLAProcessResult.getElapsedTime());
		document.addKeyword(
			"instanceId", workflowMetricsSLAProcessResult.getInstanceId());
		document.addDateSortable(
			"lastCheckDate",
			Timestamp.valueOf(
				workflowMetricsSLAProcessResult.getLastCheckLocalDateTime()));
		document.addKeyword(
			"onTime", workflowMetricsSLAProcessResult.isOnTime());
		document.addDateSortable(
			"overdueDate",
			Timestamp.valueOf(
				workflowMetricsSLAProcessResult.getOverdueLocalDateTime()));
		document.addKeyword(
			"processId", workflowMetricsSLAProcessResult.getProcessId());
		document.addKeyword(
			"remainingTime",
			workflowMetricsSLAProcessResult.getRemainingTime());
		document.addKeyword(
			"slaDefinitionId",
			workflowMetricsSLAProcessResult.getSLADefinitionId());

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-sla-process-result";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsSLAProcessResultType";
	}

	@Override
	protected void populateIndex() throws PortalException {
	}

}