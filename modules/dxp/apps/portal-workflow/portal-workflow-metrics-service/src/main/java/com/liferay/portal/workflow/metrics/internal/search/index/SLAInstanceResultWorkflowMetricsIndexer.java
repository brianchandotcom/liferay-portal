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

import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.document.DocumentBuilder;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAInstanceResult;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = SLAInstanceResultWorkflowMetricsIndexer.class
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

		if (workflowMetricsSLAInstanceResult.getModifiedLocalDateTime() !=
				null) {

			documentBuilder.setDate(
				"modifiedDate",
				formatLocalDateTime(
					workflowMetricsSLAInstanceResult.
						getModifiedLocalDateTime()));
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
	public void deleteDocuments(
		long companyId, long processId, long slaDefinitionId) {

		super.deleteDocuments(companyId, processId, slaDefinitionId);

		BooleanQuery booleanQuery = queries.booleanQuery();

		BooleanQuery filterBooleanQuery = queries.booleanQuery();

		filterBooleanQuery.addMustNotQueryClauses(
			queries.term("instanceCompleted", Boolean.TRUE));

		filterBooleanQuery.addMustQueryClauses(
			queries.term("completed", false),
			queries.term("processId", processId),
			queries.nested(
				"slaResults",
				queries.term("slaResults.slaDefinitionId", slaDefinitionId)));

		booleanQuery.addFilterQueryClauses(filterBooleanQuery);

		ScriptBuilder scriptBuilder = scripts.builder();

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				booleanQuery,
				scriptBuilder.idOrCode(
					StringUtil.read(
						getClass(),
						"dependencies/workflow-metrics-delete-sla-result-" +
							"script.painless")
				).language(
					"painless"
				).putParameter(
					"slaDefinitionId", slaDefinitionId
				).scriptType(
					ScriptType.INLINE
				).build(),
				_instanceWorkflowMetricsIndex.getIndexName(companyId));

		if (PortalRunMode.isTestMode()) {
			updateByQueryDocumentRequest.setRefresh(true);
		}

		searchEngineAdapter.execute(updateByQueryDocumentRequest);
	}

	@Override
	public String getIndexName(long companyId) {
		return _slaInstanceResultWorkflowMetricsIndex.getIndexName(companyId);
	}

	@Override
	public String getIndexType() {
		return _slaInstanceResultWorkflowMetricsIndex.getIndexType();
	}

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndex _slaInstanceResultWorkflowMetricsIndex;

}