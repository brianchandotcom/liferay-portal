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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinition;
import com.liferay.portal.workflow.kaleo.model.KaleoDefinitionVersion;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
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
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.List;
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
	immediate = true,
	service = {Indexer.class, SLAProcessResultWorkflowMetricsIndexer.class}
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

		long lastInstanceId = 0;
		long searchHitsTotal = 0;
		int searchRequestSize = 50;
		SearchSearchResponse searchSearchResponse = null;

		Stream<WorkflowMetricsSLADefinition> stream =
			workflowMetricsSLADefinitions.stream();

		Set<Long> processIds = stream.map(
			WorkflowMetricsSLADefinition::getProcessId
		).collect(
			Collectors.toSet()
		);

		while (true) {
			searchSearchResponse = searchEngineAdapter.execute(
				_createInstanceSearchSearchRequest(
					companyId, lastInstanceId, processIds, searchRequestSize));

			_reindexSLAProcessResults(companyId, searchSearchResponse);

			SearchHits searchHits = searchSearchResponse.getSearchHits();

			searchHitsTotal = searchHits.getTotalHits();

			if (searchHitsTotal == 0) {
				break;
			}

			List<SearchHit> searchHitsList = searchHits.getSearchHits();

			SearchHit searchHit = searchHitsList.get(searchHitsList.size() - 1);

			com.liferay.portal.search.document.Document document =
				searchHit.getDocument();

			lastInstanceId = document.getLong("instanceId");

			if (searchHitsTotal != searchRequestSize) {
				break;
			}
		}
	}

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

	private BooleanQuery _createInstancesBooleanQuery(
		long companyId, long lastInstanceId, Set<Long> processIds) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			queries.term("companyId", companyId),
			queries.term("completed", true), queries.term("deleted", false),
			queries.rangeTerm("instanceId", false, true, lastInstanceId, null),
			_createProcessIdTermsQuery(processIds));
	}

	private SearchSearchRequest _createInstanceSearchSearchRequest(
		long companyId, long lastInstanceId, Set<Long> processIds,
		int searchRequestSize) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			_createInstancesBooleanQuery(
				companyId, lastInstanceId, processIds));
		searchSearchRequest.setSize(searchRequestSize);
		searchSearchRequest.setSorts(
			Collections.singleton(sorts.field("instanceId", SortOrder.ASC)));

		return searchSearchRequest;
	}

	private TermsQuery _createProcessIdTermsQuery(Set<Long> processIds) {
		TermsQuery termsQuery = queries.terms("processId");

		Stream<Long> stream = processIds.stream();

		termsQuery.addValues(
			stream.map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return termsQuery;
	}

	private long _getStartNodeId(long processId, String version) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.getKaleoDefinition(processId);

			KaleoDefinitionVersion kaleoDefinitionVersion =
				kaleoDefinitionVersionLocalService.getKaleoDefinitionVersion(
					kaleoDefinition.getCompanyId(), kaleoDefinition.getName(),
					version);

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

	private void _reindexSLAProcessResults(
		long companyId, SearchSearchResponse searchSearchResponse) {

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Stream.of(
			searchSearchResponse.getSearchHits()
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).forEach(
			document -> {
				LocalDateTime completionDate = LocalDateTime.parse(
					document.getString("completionDate"), _dateTimeFormatter);

				List<WorkflowMetricsSLADefinitionVersion>
					workflowMetricsSLADefinitionVersions =
						workflowMetricsSLADefinitionVersionLocalService.
							getWorkflowMetricsSLADefinitionVersions(
								companyId,
								Timestamp.valueOf(
									completionDate.withNano(
										LocalDateTime.MAX.getNano())),
								WorkflowConstants.STATUS_APPROVED);

				Stream<WorkflowMetricsSLADefinitionVersion> stream =
					workflowMetricsSLADefinitionVersions.stream();

				stream.filter(
					WorkflowMetricsSLADefinitionVersion::isActive
				).forEach(
					workflowMetricsSLADefinitionVersion -> {
						Optional<WorkflowMetricsSLAProcessResult> optional =
							workflowMetricsSLAProcessor.process(
								workflowMetricsSLADefinitionVersion.
									getCompanyId(),
								LocalDateTime.parse(
									document.getString("createDate"),
									_dateTimeFormatter),
								document.getLong("instanceId"),
								LocalDateTime.now(),
								_getStartNodeId(
									document.getLong("processId"),
									document.getString("version")),
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

								slaTaskResultWorkflowMetricsIndexer.
									addDocuments(
										workflowMetricsSLAProcessResult.
											getWorkflowMetricsSLATaskResults());
							});
					}
				);
			}
		);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SLAProcessResultWorkflowMetricsIndexer.class);

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

}