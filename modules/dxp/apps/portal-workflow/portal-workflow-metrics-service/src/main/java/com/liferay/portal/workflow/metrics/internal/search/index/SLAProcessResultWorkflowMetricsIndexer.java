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
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.IndexDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
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
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.sql.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
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
	extends BaseWorkflowMetricsIndexer {

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
		document.addKeyword(
			"status",
			String.valueOf(
				workflowMetricsSLAProcessResult.getWorkfowMetricsSLAStatus()));

		return document;
	}

	public void deleteDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_deleteDocuments(
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("instanceId", instanceId)));
	}

	public void deleteDocuments(
		long companyId, long processId, long slaDefinitionId) {

		BooleanQuery booleanQuery = queries.booleanQuery();

		_deleteDocuments(
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("processId", processId),
				queries.term("slaDefinitionId", slaDefinitionId)));
	}

	public void expireDocuments(long companyId, long instanceId) {
		BooleanQuery booleanQuery = queries.booleanQuery();

		_updateDocuments(
			document -> new DocumentImpl() {
				{
					addKeyword(
						"status", WorkfowMetricsSLAStatus.EXPIRED.toString());
					addKeyword(Field.UID, document.getString(Field.UID));
				}
			},
			booleanQuery.addMustQueryClauses(
				queries.term("companyId", companyId),
				queries.term("instanceId", instanceId)));
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

		Map<Long, List<WorkflowMetricsSLADefinition>>
			workflowMetricsSLADefinitionsMap = stream.collect(
				Collectors.groupingBy(
					WorkflowMetricsSLADefinition::getProcessId));

		Map<Long, Long> startNodeIdsMap = new HashMap<>();

		Set<Long> processIds = workflowMetricsSLADefinitionsMap.keySet();

		processIds.forEach(
			processId -> startNodeIdsMap.put(
				processId, _getStartNodeId(processId)));

		while (true) {
			searchSearchResponse = searchEngineAdapter.execute(
				_createSearchSearchRequest(
					companyId, lastInstanceId, searchRequestSize,
					workflowMetricsSLADefinitionsMap));

			_reindexSLAProcessResults(
				searchSearchResponse, startNodeIdsMap,
				workflowMetricsSLADefinitionsMap);

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
	protected Queries queries;

	@Reference
	protected Sorts sorts;

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected volatile WorkflowMetricsSLADefinitionLocalService
		workflowMetricsSLADefinitionLocalService;

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

	private SearchSearchRequest _createSearchSearchRequest(
		long companyId, long lastInstanceId, int searchRequestSize,
		Map<Long, List<WorkflowMetricsSLADefinition>>
			workflowMetricsSLADefinitionsMap) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			_createInstancesBooleanQuery(
				companyId, lastInstanceId,
				workflowMetricsSLADefinitionsMap.keySet()));
		searchSearchRequest.setSize(searchRequestSize);
		searchSearchRequest.setSorts(
			Collections.singleton(sorts.field("instanceId", SortOrder.ASC)));

		return searchSearchRequest;
	}

	private void _deleteDocuments(BooleanQuery booleanQuery) {
		_updateDocuments(
			document -> new DocumentImpl() {
				{
					addKeyword("deleted", true);
					addKeyword(Field.UID, document.getString(Field.UID));
				}
			},
			booleanQuery.addMustNotQueryClauses(
				queries.term("status", WorkfowMetricsSLAStatus.COMPLETED),
				queries.term("status", WorkfowMetricsSLAStatus.STOPPED)));
	}

	private long _getStartNodeId(long processId) {
		try {
			KaleoDefinition kaleoDefinition =
				kaleoDefinitionLocalService.getKaleoDefinition(processId);

			KaleoDefinitionVersion latestKaleoDefinitionVersion =
				kaleoDefinitionVersionLocalService.
					getLatestKaleoDefinitionVersion(
						kaleoDefinition.getCompanyId(),
						kaleoDefinition.getName());

			List<KaleoNode> kaleoNodes =
				kaleoNodeLocalService.getKaleoDefinitionVersionKaleoNodes(
					latestKaleoDefinitionVersion.getKaleoDefinitionVersionId());

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
		SearchSearchResponse searchSearchResponse,
		Map<Long, Long> startNodeIdsMap,
		Map<Long, List<WorkflowMetricsSLADefinition>>
			workflowMetricsSLADefinitionsMap) {

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
				List<WorkflowMetricsSLADefinition>
					workflowMetricsSLADefinitionsList =
						workflowMetricsSLADefinitionsMap.get(
							document.getLong("processId"));

				workflowMetricsSLADefinitionsList.forEach(
					workflowMetricsSLADefinition -> {
						Optional<WorkflowMetricsSLAProcessResult> optional =
							workflowMetricsSLAProcessor.process(
								workflowMetricsSLADefinition.getCompanyId(),
								LocalDateTime.parse(
									document.getString("createDate"),
									_dateTimeFormatter),
								document.getLong("instanceId"),
								LocalDateTime.now(),
								startNodeIdsMap.get(
									document.getLong("processId")),
								workflowMetricsSLADefinition);

						optional.ifPresent(
							workflowMetricsSLAProcessResult -> {
								workflowMetricsSLAProcessResult.
									setWorkfowMetricsSLAStatus(
										WorkfowMetricsSLAStatus.COMPLETED);

								bulkDocumentRequest.addBulkableDocumentRequest(
									new IndexDocumentRequest(
										getIndexName(),
										createDocument(
											workflowMetricsSLAProcessResult)) {

										{
											setType(getIndexType());
										}
									});
							});
					});
			}
		);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			searchEngineAdapter.execute(bulkDocumentRequest);
		}
	}

	private void _updateDocuments(
		Function<com.liferay.portal.search.document.Document, Document>
			transformDocumentFunction,
		Query query) {

		if (searchEngineAdapter == null) {
			return;
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(getIndexName());
		searchSearchRequest.setQuery(query);
		searchSearchRequest.setSelectedFieldNames(Field.UID);

		SearchSearchResponse searchSearchResponse = searchEngineAdapter.execute(
			searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		if (searchHits.getTotalHits() == 0) {
			return;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();

		Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			document -> new UpdateDocumentRequest(
				getIndexName(), document.getString(Field.UID),
				transformDocumentFunction.apply(document)) {

				{
					setType(getIndexType());
				}
			}
		).forEach(
			bulkDocumentRequest::addBulkableDocumentRequest
		);

		searchEngineAdapter.execute(bulkDocumentRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		SLAProcessResultWorkflowMetricsIndexer.class);

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern(
			PropsUtil.get(PropsKeys.INDEX_DATE_FORMAT_PATTERN));

}