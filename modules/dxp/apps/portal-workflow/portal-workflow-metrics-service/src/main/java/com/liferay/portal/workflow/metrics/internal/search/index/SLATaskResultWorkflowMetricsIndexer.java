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
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLATaskResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.sql.Timestamp;

import org.osgi.service.component.annotations.Component;

/**
 * @author Inácio Nery
 */
@Component(
	immediate = true, service = SLATaskResultWorkflowMetricsIndexer.class
)
public class SLATaskResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document createDocument(
		WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult) {

		Document document = new DocumentImpl();

		document.addUID(
			"WorkflowMetricsSLATaskResult",
			digest(
				workflowMetricsSLATaskResult.getCompanyId(),
				workflowMetricsSLATaskResult.getInstanceId(),
				workflowMetricsSLATaskResult.getProcessId(),
				workflowMetricsSLATaskResult.getSLADefinitionId(),
				workflowMetricsSLATaskResult.getTaskId(),
				workflowMetricsSLATaskResult.getTokenId()));

		if (workflowMetricsSLATaskResult.getAssigneeId() != null) {
			document.addKeyword(
				"assigneeId", workflowMetricsSLATaskResult.getAssigneeId());
		}

		document.addKeyword(
			"breached", workflowMetricsSLATaskResult.isBreached());
		document.addKeyword(
			"companyId", workflowMetricsSLATaskResult.getCompanyId());

		if (workflowMetricsSLATaskResult.getCompletionLocalDateTime() != null) {
			document.addDateSortable(
				"completionDate",
				Timestamp.valueOf(
					workflowMetricsSLATaskResult.getCompletionLocalDateTime()));
		}

		if (workflowMetricsSLATaskResult.getCompletionUserId() != null) {
			document.addKeyword(
				"completionUserId",
				workflowMetricsSLATaskResult.getCompletionUserId());
		}

		document.addKeyword("deleted", false);
		document.addKeyword(
			"instanceCompleted",
			workflowMetricsSLATaskResult.isInstanceCompleted());
		document.addKeyword(
			"instanceId", workflowMetricsSLATaskResult.getInstanceId());
		document.addDateSortable(
			"lastCheckDate",
			Timestamp.valueOf(
				workflowMetricsSLATaskResult.getLastCheckLocalDateTime()));
		document.addKeyword("onTime", workflowMetricsSLATaskResult.isOnTime());
		document.addKeyword(
			"processId", workflowMetricsSLATaskResult.getProcessId());
		document.addKeyword(
			"slaDefinitionId",
			workflowMetricsSLATaskResult.getSLADefinitionId());

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLATaskResult.getWorkflowMetricsSLAStatus();

		document.addKeyword("status", workflowMetricsSLAStatus.name());

		document.addKeyword("taskId", workflowMetricsSLATaskResult.getTaskId());
		document.addKeyword(
			"taskName", workflowMetricsSLATaskResult.getTaskName());
		document.addKeyword(
			"tokenId", workflowMetricsSLATaskResult.getTokenId());

		return document;
	}

	@Override
	public String getIndexName() {
		return "workflow-metrics-sla-task-results";
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLATaskResultType";
	}

	@Override
	public void reindex(long companyId) {
	}

}