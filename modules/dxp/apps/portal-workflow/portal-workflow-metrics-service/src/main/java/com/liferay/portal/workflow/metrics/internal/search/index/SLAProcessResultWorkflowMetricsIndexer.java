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

import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessResult;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessor;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.sql.Timestamp;

import java.time.LocalDateTime;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Rafael Praxedes
 */
@Component(
	immediate = true, service = SLAProcessResultWorkflowMetricsIndexer.class
)
public class SLAProcessResultWorkflowMetricsIndexer
	extends BaseSLAWorkflowMetricsIndexer {

	public Document createDocument(
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
		document.addKeyword("deleted", false);
		document.addKeyword(
			"elapsedTime", workflowMetricsSLAProcessResult.getElapsedTime());

		KaleoInstance kaleoInstance =
			kaleoInstanceLocalService.fetchKaleoInstance(
				workflowMetricsSLAProcessResult.getInstanceId());

		if (kaleoInstance != null) {
			document.addKeyword(
				"instanceCompleted", kaleoInstance.isCompleted());
		}

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

		WorkflowMetricsSLAStatus workflowMetricsSLAStatus =
			workflowMetricsSLAProcessResult.getWorkflowMetricsSLAStatus();

		document.addKeyword("status", workflowMetricsSLAStatus.name());

		return document;
	}

	@Override
	protected String getIndexName() {
		return "workflow-metrics-sla-process-results";
	}

	@Override
	protected String getIndexType() {
		return "WorkflowMetricsSLAProcessResultType";
	}

	@Override
	protected void reindex(long companyId) throws PortalException {
		if (workflowMetricsSLADefinitionLocalService == null) {
			return;
		}

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			workflowMetricsSLADefinitionLocalService.
				getWorkflowMetricsSLADefinitions(
					companyId, WorkflowConstants.STATUS_APPROVED);

		if (workflowMetricsSLADefinitions.isEmpty()) {
			return;
		}

		Stream<WorkflowMetricsSLADefinition> stream =
			workflowMetricsSLADefinitions.stream();

		Set<String> kaleoDefinitionNames = stream.map(
			WorkflowMetricsSLADefinition::getProcessId
		).map(
			kaleoDefinitionLocalService::fetchKaleoDefinition
		).filter(
			Objects::nonNull
		).map(
			KaleoDefinition::getName
		).collect(
			Collectors.toSet()
		);

		if (kaleoDefinitionNames.isEmpty()) {
			return;
		}

		ActionableDynamicQuery actionableDynamicQuery =
			kaleoInstanceLocalService.getActionableDynamicQuery();

		actionableDynamicQuery.setAddCriteriaMethod(
			dynamicQuery -> {
				Property companyIdProperty = PropertyFactoryUtil.forName(
					"companyId");

				dynamicQuery.add(companyIdProperty.eq(companyId));

				Property kaleoDefinitionNameProperty =
					PropertyFactoryUtil.forName("kaleoDefinitionName");

				dynamicQuery.add(
					kaleoDefinitionNameProperty.in(kaleoDefinitionNames));

				Property completedProperty = PropertyFactoryUtil.forName(
					"completed");

				dynamicQuery.add(completedProperty.eq(true));
			});
		actionableDynamicQuery.setPerformActionMethod(
			(KaleoInstance kaleoInstance) -> _reindexSLAProcessResults(
				kaleoInstance));

		actionableDynamicQuery.performActions();
	}

	@Reference
	protected KaleoInstanceLocalService kaleoInstanceLocalService;

	@Reference
	protected KaleoNodeLocalService kaleoNodeLocalService;

	@Reference
	protected SLATaskResultWorkflowMetricsIndexer
		slaTaskResultWorkflowMetricsIndexer;

	@Reference
	protected Sorts sorts;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile WorkflowMetricsSLADefinitionLocalService
		workflowMetricsSLADefinitionLocalService;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile WorkflowMetricsSLADefinitionVersionLocalService
		workflowMetricsSLADefinitionVersionLocalService;

	@Reference
	protected WorkflowMetricsSLAProcessor workflowMetricsSLAProcessor;

	private long _getStartNodeId(KaleoInstance kaleoInstance) {
		try {
			KaleoDefinitionVersion kaleoDefinitionVersion =
				kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					kaleoInstance.getKaleoDefinitionVersionId());

			List<KaleoNode> kaleoNodes =
				kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
					kaleoDefinitionVersion.getKaleoDefinitionVersionId());

			Stream<KaleoNode> stream = kaleoNodes.stream();

			return stream.filter(
				KaleoNode::isInitial
			).findFirst(
			).map(
				KaleoNode::getKaleoNodeId
			).orElseGet(
				() -> 0L
			);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return 0L;
	}

	private void _reindexSLAProcessResults(KaleoInstance kaleoInstance) {
		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions =
				workflowMetricsSLADefinitionVersionLocalService.
					getWorkflowMetricsSLADefinitionVersions(
						kaleoInstance.getCompanyId(),
						kaleoInstance.getCompletionDate(),
						WorkflowConstants.STATUS_APPROVED);

		Stream<WorkflowMetricsSLADefinitionVersion> stream =
			workflowMetricsSLADefinitionVersions.stream();

		stream.filter(
			WorkflowMetricsSLADefinitionVersion::isActive
		).forEach(
			workflowMetricsSLADefinitionVersion -> {
				Optional<WorkflowMetricsSLAProcessResult> optional =
					workflowMetricsSLAProcessor.process(
						workflowMetricsSLADefinitionVersion.getCompanyId(),
						new Timestamp(
							kaleoInstance.getCreateDate(
							).getTime()
						).toLocalDateTime(),
						kaleoInstance.getKaleoInstanceId(), LocalDateTime.now(),
						_getStartNodeId(kaleoInstance),
						workflowMetricsSLADefinitionVersion);

				optional.ifPresent(
					workflowMetricsSLAProcessResult -> {
						workflowMetricsSLAProcessResult.
							setWorkflowMetricsSLAStatus(
								WorkflowMetricsSLAStatus.COMPLETED);

						bulkDocumentRequest.addBulkableDocumentRequest(
							new IndexDocumentRequest(
								getIndexName(),
								createDocument(
									workflowMetricsSLAProcessResult)) {

								{
									setType(getIndexType());
								}
							});

						slaTaskResultWorkflowMetricsIndexer.addDocuments(
							workflowMetricsSLAProcessResult.
								getWorkflowMetricsSLATaskResults());
					});
			}
		);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			if (PortalRunMode.isTestMode()) {
				bulkDocumentRequest.setRefresh(true);
			}

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SLAProcessResultWorkflowMetricsIndexer.class);

}