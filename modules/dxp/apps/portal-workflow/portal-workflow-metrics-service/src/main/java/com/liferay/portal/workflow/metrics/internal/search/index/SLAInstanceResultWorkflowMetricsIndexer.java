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

import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAInstanceResult;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true,
	property = "workflow.metrics.index.entity.name=sla-instance-result",
	service = {
		SLAInstanceResultWorkflowMetricsIndexer.class,
		WorkflowMetricsIndex.class
	}
)
public class SLAInstanceResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document creatDefaultDocument(long companyId, long processId) {
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
			new WorkflowMetricsSLAInstanceResult();

		workflowMetricsSLAInstanceResult.setCompanyId(companyId);
		workflowMetricsSLAInstanceResult.setProcessId(processId);

		return createDocument(workflowMetricsSLAInstanceResult);
	}

	public Document createDocument(
		WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult) {

		DocumentBuilder documentBuilder = documentBuilderFactory.builder();

		documentBuilder.setLong(
			"companyId", workflowMetricsSLAInstanceResult.getCompanyId());

		if (workflowMetricsSLAInstanceResult.getCompletionLocalDateTime() !=
				null) {

			documentBuilder.setDate(
				"completionDate",
				formatLocalDateTime(
					workflowMetricsSLAInstanceResult.
						getCompletionLocalDateTime()));
		}

		documentBuilder.setValue(
			"deleted", false
		).setLong(
			"elapsedTime", workflowMetricsSLAInstanceResult.getElapsedTime()
		).setValue(
			"instanceCompleted",
			workflowMetricsSLAInstanceResult.getCompletionLocalDateTime() !=
				null
		).setLong(
			"instanceId", workflowMetricsSLAInstanceResult.getInstanceId()
		);

		if (workflowMetricsSLAInstanceResult.getLastCheckLocalDateTime() !=
				null) {

			documentBuilder.setDate(
				"lastCheckDate",
				formatLocalDateTime(
					workflowMetricsSLAInstanceResult.
						getLastCheckLocalDateTime()));
		}

		documentBuilder.setValue(
			"onTime", workflowMetricsSLAInstanceResult.isOnTime());

		if (workflowMetricsSLAInstanceResult.getOverdueLocalDateTime() !=
				null) {

			documentBuilder.setDate(
				"overdueDate",
				formatLocalDateTime(
					workflowMetricsSLAInstanceResult.
						getOverdueLocalDateTime()));
		}

		documentBuilder.setLong(
			"processId", workflowMetricsSLAInstanceResult.getProcessId()
		).setLong(
			"remainingTime", workflowMetricsSLAInstanceResult.getRemainingTime()
		).setLong(
			"slaDefinitionId",
			workflowMetricsSLAInstanceResult.getSLADefinitionId()
		);

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLAInstanceResult.getWorkflowMetricsSLAStatus();

		if (workflowMetricsSLAStatus != null) {
			documentBuilder.setString(
				"status", workflowMetricsSLAStatus.name());
		}

		documentBuilder.setString(
			"uid",
			digest(
				workflowMetricsSLAInstanceResult.getCompanyId(),
				workflowMetricsSLAInstanceResult.getInstanceId(),
				workflowMetricsSLAInstanceResult.getProcessId(),
				workflowMetricsSLAInstanceResult.getSLADefinitionId()));

		return documentBuilder.build();
	}

	@Override
	public String getIndexName(long companyId) {
		return _slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
			companyId);
	}

	@Override
	public String getIndexType() {
		return "WorkflowMetricsSLAInstanceResultType";
	}

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndexNameBuilder
		_slaInstanceResultWorkflowMetricsIndexNameBuilder;

}