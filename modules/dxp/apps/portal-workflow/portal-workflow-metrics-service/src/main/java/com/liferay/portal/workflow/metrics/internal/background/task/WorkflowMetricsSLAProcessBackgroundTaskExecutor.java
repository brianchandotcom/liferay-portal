/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.internal.background.task;

import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.backgroundtask.BackgroundTask;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.BackgroundTaskResult;
import com.liferay.portal.kernel.backgroundtask.BaseBackgroundTaskExecutor;
import com.liferay.portal.kernel.backgroundtask.display.BackgroundTaskDisplay;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.search.capabilities.SearchCapabilities;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.document.BulkDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateByQueryDocumentRequest;
import com.liferay.portal.search.engine.adapter.document.UpdateDocumentRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.RangeTermQuery;
import com.liferay.portal.search.script.ScriptBuilder;
import com.liferay.portal.search.script.ScriptType;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAInstanceResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.util.WorkflowMetricsIndexerUtil;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAInstanceResult;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLAProcessor;
import com.liferay.portal.workflow.metrics.internal.sla.processor.WorkflowMetricsSLATaskResult;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.search.index.WorkflowMetricsIndex;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionVersionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;
import com.liferay.portal.workflow.metrics.util.comparator.WorkflowMetricsSLADefinitionVersionIdComparator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Rafael Praxedes
 */
@Component(
	property = "background.task.executor.class.name=com.liferay.portal.workflow.metrics.internal.background.task.WorkflowMetricsSLAProcessBackgroundTaskExecutor",
	service = BackgroundTaskExecutor.class
)
public class WorkflowMetricsSLAProcessBackgroundTaskExecutor
	extends BaseBackgroundTaskExecutor {

	@Override
	public BackgroundTaskExecutor clone() {
		return this;
	}

	@Override
	public BackgroundTaskResult execute(BackgroundTask backgroundTask)
		throws Exception {

		if (!_searchCapabilities.isWorkflowMetricsSupported()) {
			return BackgroundTaskResult.SUCCESS;
		}

		long workflowMetricsSLADefinitionId = MapUtil.getLong(
			backgroundTask.getTaskContextMap(),
			"workflowMetricsSLADefinitionId");

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			_workflowMetricsSLADefinitionLocalService.
				fetchWorkflowMetricsSLADefinition(
					workflowMetricsSLADefinitionId);

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				_workflowMetricsSLADefinitionVersionLocalService.
					getWorkflowMetricsSLADefinitionVersion(
						workflowMetricsSLADefinitionId,
						workflowMetricsSLADefinition.getVersion());

		long startNodeId = _getStartNodeId(
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId(),
			workflowMetricsSLADefinition.getProcessVersion());

		if (workflowMetricsSLADefinitionVersion.isActive()) {
			long instanceId = 0;

			while (true) {
				long nextInstanceId = _processInstances(
					false, null, instanceId, null, startNodeId,
					workflowMetricsSLADefinitionVersion);

				if (nextInstanceId == instanceId) {
					break;
				}

				instanceId = nextInstanceId;
			}
		}

		if (MapUtil.getBoolean(backgroundTask.getTaskContextMap(), "reindex")) {
			_processCompletedInstances(
				startNodeId, workflowMetricsSLADefinitionId);
		}
		else {
			Date endDate = null;

			if (!workflowMetricsSLADefinitionVersion.isActive()) {
				endDate = workflowMetricsSLADefinitionVersion.getCreateDate();
			}

			long instanceId = 0;

			while (true) {
				long nextInstanceId = _processInstances(
					true, endDate, instanceId,
					workflowMetricsSLADefinition.getCreateDate(), startNodeId,
					workflowMetricsSLADefinitionVersion);

				if (nextInstanceId == instanceId) {
					break;
				}

				instanceId = nextInstanceId;
			}
		}

		return BackgroundTaskResult.SUCCESS;
	}

	@Override
	public BackgroundTaskDisplay getBackgroundTaskDisplay(
		BackgroundTask backgroundTask) {

		return null;
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date endDate, long instanceId, long processId,
		long slaDefinitionId, Date startDate) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (completed) {
			booleanQuery.addMustNotQueryClauses(
				_queries.term("slaDefinitionIds", slaDefinitionId));
		}

		return booleanQuery.addMustQueryClauses(
			_createInstancesBooleanQuery(
				completed, endDate, instanceId, processId, startDate));
	}

	private BooleanQuery _createInstancesBooleanQuery(
		boolean completed, Date endDate, long instanceId, long processId,
		Date startDate) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", "0"));

		if (startDate != null) {
			RangeTermQuery rangeTermQuery = _queries.rangeTerm(
				"completionDate", true, false);

			rangeTermQuery.setLowerBound(_getDate(startDate));

			if (endDate != null) {
				rangeTermQuery.setUpperBound(_getDate(endDate));
			}

			booleanQuery.addMustQueryClauses(rangeTermQuery);
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("active", true),
			_queries.term("completed", completed),
			_queries.term("deleted", false),
			_queries.rangeTerm("instanceId", false, false, instanceId, null),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createMustNotCompletionDateBooleanQuery() {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustNotQueryClauses(
			_queries.exists("completionDate"));
	}

	private BooleanQuery _createSLAInstanceResultsBooleanQuery(
		long endInstanceId, long processId, long slaDefinitionId,
		long startInstanceId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("active", true), _queries.term("blocked", false),
			_queries.term("deleted", false),
			_queries.rangeTerm(
				"instanceId", true, true, startInstanceId, endInstanceId),
			_queries.term("processId", processId),
			_queries.term("slaDefinitionId", slaDefinitionId));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long endInstanceId, LocalDateTime lastCheckLocalDateTime,
		long processId, long startInstanceId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (lastCheckLocalDateTime != null) {
			booleanQuery.addShouldQueryClauses(
				_createMustNotCompletionDateBooleanQuery(),
				_queries.dateRangeTerm(
					"completionDate", true, false,
					_dateTimeFormatter.format(lastCheckLocalDateTime), null));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("active", true), _queries.term("deleted", false),
			_queries.rangeTerm(
				"instanceId", true, true, startInstanceId, endInstanceId),
			_queries.term("processId", processId));
	}

	private LocalDateTime _getCompletionLocalDateTime(Document document) {
		if (document.getDate("completionDate") != null) {
			return LocalDateTime.parse(
				document.getDate("completionDate"), _dateTimeFormatter);
		}

		return null;
	}

	private String _getDate(Date date) {
		try {
			return DateUtil.getDate(
				date, "yyyyMMddHHmmss", LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception);
			}

			return null;
		}
	}

	private long _getStartNodeId(
		long companyId, long processId, String version) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_NODE);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("deleted", false), _queries.term("initial", true),
				_queries.term("processId", processId),
				_queries.term("version", version)));

		searchSearchRequest.setSelectedFieldNames("nodeId");
		searchSearchRequest.setSize(1);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Document document = searchHit.getDocument();

			return GetterUtil.getLong(document.getLong("nodeId"));
		}

		return 0L;
	}

	private Map<Long, WorkflowMetricsSLAInstanceResult>
		_getWorkflowMetricsSLAInstanceResults(
			long companyId, long endInstanceId, long processId,
			long slaDefinitionId, long startInstanceId) {

		Map<Long, WorkflowMetricsSLAInstanceResult>
			workflowMetricsSLAInstanceResults = new HashMap<>();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				_createSLAInstanceResultsBooleanQuery(
					endInstanceId, processId, slaDefinitionId,
					startInstanceId)));

		searchSearchRequest.setSelectedFieldNames(
			"elapsedTime", "instanceId", "modifiedDate", "onTime",
			"overdueDate", "remainingTime", "status");
		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Document document = searchHit.getDocument();

			WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
				new WorkflowMetricsSLAInstanceResult() {
					{
						setCompanyId(companyId);
						setElapsedTime(document.getLong("elapsedTime"));
						setInstanceId(document.getLong("instanceId"));
						setModifiedLocalDateTime(
							LocalDateTime.parse(
								document.getDate("modifiedDate"),
								_dateTimeFormatter));
						setOnTime(
							GetterUtil.getBoolean(document.getValue("onTime")));
						setOverdueLocalDateTime(
							LocalDateTime.parse(
								document.getString("overdueDate"),
								_dateTimeFormatter));
						setProcessId(processId);
						setRemainingTime(document.getLong("remainingTime"));
						setSLADefinitionId(slaDefinitionId);
						setWorkflowMetricsSLAStatus(
							WorkflowMetricsSLAStatus.valueOf(
								document.getString("status")));
					}
				};

			workflowMetricsSLAInstanceResults.put(
				workflowMetricsSLAInstanceResult.getInstanceId(),
				workflowMetricsSLAInstanceResult);
		}

		return workflowMetricsSLAInstanceResults;
	}

	private long _populateTaskDocuments(
		long companyId, long endInstanceId,
		LocalDateTime lastCheckLocalDateTime, long processId,
		long startInstanceId, Map<Long, List<Document>> taskDocuments) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(_sorts.field("instanceId", SortOrder.ASC));
		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(companyId) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				_createTasksBooleanQuery(
					endInstanceId, lastCheckLocalDateTime, processId,
					startInstanceId)));

		searchSearchRequest.setSelectedFieldNames(
			"assigneeIds", "assigneeType", "completed", "completionDate",
			"completionUserId", "createDate", "instanceId", "name", "nodeId",
			"taskId");
		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<Long> instanceIds = TransformUtil.transform(
			searchHits.getSearchHits(),
			searchHit -> {
				Document document = searchHit.getDocument();

				List<Document> documents = taskDocuments.computeIfAbsent(
					document.getLong("instanceId"), key -> new ArrayList<>());

				documents.add(document);

				documents.sort(
					Comparator.comparing(
						curDocument -> LocalDateTime.parse(
							curDocument.getDate("createDate"),
							_dateTimeFormatter)));

				return document.getLong("instanceId");
			});

		if (instanceIds.isEmpty()) {
			return startInstanceId;
		}

		if (searchHits.getTotalHits() >= 10000) {
			return Collections.max(instanceIds);
		}

		return startInstanceId;
	}

	private void _processCompletedInstances(
		long startNodeId, long workflowMetricsSLADefinitionId) {

		List<WorkflowMetricsSLADefinitionVersion>
			workflowMetricsSLADefinitionVersions =
				_workflowMetricsSLADefinitionVersionLocalService.
					getWorkflowMetricsSLADefinitionVersions(
						workflowMetricsSLADefinitionId,
						new WorkflowMetricsSLADefinitionVersionIdComparator(
							true));

		Iterator<WorkflowMetricsSLADefinitionVersion> iterator =
			workflowMetricsSLADefinitionVersions.iterator();

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion = iterator.next();

		Date startDate = workflowMetricsSLADefinitionVersion.getCreateDate();

		while (startDate != null) {
			Date endDate = null;

			WorkflowMetricsSLADefinitionVersion
				nextWorkflowMetricsSLADefinitionVersion = null;

			if (iterator.hasNext()) {
				nextWorkflowMetricsSLADefinitionVersion = iterator.next();

				endDate =
					nextWorkflowMetricsSLADefinitionVersion.getCreateDate();
			}

			if (workflowMetricsSLADefinitionVersion.isActive()) {
				long instanceId = 0;

				while (true) {
					long nextInstanceId = _processInstances(
						true, endDate, instanceId, startDate, startNodeId,
						workflowMetricsSLADefinitionVersion);

					if (nextInstanceId == instanceId) {
						break;
					}

					instanceId = nextInstanceId;
				}
			}

			startDate = endDate;
			workflowMetricsSLADefinitionVersion =
				nextWorkflowMetricsSLADefinitionVersion;
		}
	}

	private long _processInstances(
		boolean completed, Date endDate, long instanceId, Date startDate,
		long startNodeId,
		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addSorts(_sorts.field("instanceId", SortOrder.ASC));

		String indexName = _indexNameBuilder.getIndexName(
			workflowMetricsSLADefinitionVersion.getCompanyId());

		searchSearchRequest.setIndexNames(
			indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE);

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				_createBooleanQuery(
					completed, endDate, instanceId,
					workflowMetricsSLADefinitionVersion.getProcessId(),
					workflowMetricsSLADefinitionVersion.
						getWorkflowMetricsSLADefinitionId(),
					startDate)));

		searchSearchRequest.setSelectedFieldNames(
			"completionDate", "createDate", "instanceId");
		searchSearchRequest.setSize(10000);

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<Document> instanceDocuments = TransformUtil.transform(
			searchHits.getSearchHits(), SearchHit::getDocument);

		if (instanceDocuments.isEmpty()) {
			return instanceId;
		}

		Document firstInstanceDocument = instanceDocuments.get(0);
		Document lastInstanceDocument = instanceDocuments.get(
			instanceDocuments.size() - 1);

		Map<Long, WorkflowMetricsSLAInstanceResult>
			workflowMetricsSLAInstanceResults =
				_getWorkflowMetricsSLAInstanceResults(
					workflowMetricsSLADefinitionVersion.getCompanyId(),
					lastInstanceDocument.getLong("instanceId"),
					workflowMetricsSLADefinitionVersion.getProcessId(),
					workflowMetricsSLADefinitionVersion.
						getWorkflowMetricsSLADefinitionId(),
					firstInstanceDocument.getLong("instanceId"));

		LocalDateTime nowLocalDateTime = LocalDateTime.now();

		Map<Long, List<Document>> taskDocuments = new HashMap<>();

		long firstInstanceId = firstInstanceDocument.getLong("instanceId");

		while (true) {
			long nextInstanceId = _populateTaskDocuments(
				workflowMetricsSLADefinitionVersion.getCompanyId(),
				lastInstanceDocument.getLong("instanceId"), nowLocalDateTime,
				workflowMetricsSLADefinitionVersion.getProcessId(),
				firstInstanceId, taskDocuments);

			if (nextInstanceId == firstInstanceId) {
				break;
			}

			firstInstanceId = nextInstanceId;
		}

		BulkDocumentRequest bulkDocumentRequest = new BulkDocumentRequest();
		List<Document> slaInstanceResultDocuments = new ArrayList<>();
		List<Document> slaTaskResultDocuments = new ArrayList<>();

		for (Document document : instanceDocuments) {
			WorkflowMetricsSLAInstanceResult workflowMetricsSLAInstanceResult =
				_workflowMetricsSLAProcessor.process(
					_getCompletionLocalDateTime(document),
					LocalDateTime.parse(
						document.getDate("createDate"), _dateTimeFormatter),
					taskDocuments.get(document.getLong("instanceId")),
					document.getLong("instanceId"), nowLocalDateTime,
					startNodeId, workflowMetricsSLADefinitionVersion,
					workflowMetricsSLAInstanceResults.get(
						document.getLong("instanceId")));

			if (workflowMetricsSLAInstanceResult == null) {
				continue;
			}

			slaInstanceResultDocuments.add(
				_slaInstanceResultWorkflowMetricsIndexer.createDocument(
					workflowMetricsSLAInstanceResult));

			for (WorkflowMetricsSLATaskResult workflowMetricsSLATaskResult :
					workflowMetricsSLAInstanceResult.
						getWorkflowMetricsSLATaskResults()) {

				slaTaskResultDocuments.add(
					_slaTaskResultWorkflowMetricsIndexer.createDocument(
						workflowMetricsSLATaskResult));
			}

			ScriptBuilder scriptBuilder = _scripts.builder();

			bulkDocumentRequest.addBulkableDocumentRequest(
				new UpdateDocumentRequest(
					_instanceWorkflowMetricsIndex.getIndexName(
						workflowMetricsSLAInstanceResult.getCompanyId()),
					WorkflowMetricsIndexerUtil.digest(
						_instanceWorkflowMetricsIndex.getIndexType(),
						workflowMetricsSLAInstanceResult.getCompanyId(),
						workflowMetricsSLAInstanceResult.getInstanceId()),
					scriptBuilder.idOrCode(
						StringUtil.read(
							getClass(),
							"dependencies/workflow-metrics-update-sla-" +
								"instance-script.painless")
					).language(
						"painless"
					).putParameter(
						"slaResult",
						HashMapBuilder.<String, Object>put(
							"onTime",
							workflowMetricsSLAInstanceResult.isOnTime()
						).put(
							"overdueDate",
							_dateTimeFormatter.format(
								workflowMetricsSLAInstanceResult.
									getOverdueLocalDateTime())
						).put(
							"remainingTime",
							workflowMetricsSLAInstanceResult.getRemainingTime()
						).put(
							"slaDefinitionId",
							workflowMetricsSLAInstanceResult.
								getSLADefinitionId()
						).put(
							"status",
							() -> {
								WorkflowMetricsSLAStatus
									workflowMetricsSLAStatus =
										workflowMetricsSLAInstanceResult.
											getWorkflowMetricsSLAStatus();

								return workflowMetricsSLAStatus.name();
							}
						).build()
					).scriptType(
						ScriptType.INLINE
					).build()));
		}

		_slaInstanceResultWorkflowMetricsIndexer.addDocuments(
			slaInstanceResultDocuments);
		_slaTaskResultWorkflowMetricsIndexer.addDocuments(
			slaTaskResultDocuments);

		if (ListUtil.isNotEmpty(
				bulkDocumentRequest.getBulkableDocumentRequests())) {

			_searchEngineAdapter.execute(bulkDocumentRequest);
		}

		if (completed) {
			_updateInstances(
				workflowMetricsSLADefinitionVersion.getCompanyId(),
				lastInstanceDocument.getLong("instanceId"),
				workflowMetricsSLADefinitionVersion.
					getWorkflowMetricsSLADefinitionId(),
				firstInstanceDocument.getLong("instanceId"));
		}

		if (searchHits.getTotalHits() >= 10000) {
			return lastInstanceDocument.getLong("instanceId");
		}

		return instanceId;
	}

	private void _updateInstances(
		long companyId, long endInstanceId, long slaDefinitionId,
		long startInstanceId) {

		String indexName = _indexNameBuilder.getIndexName(companyId);

		UpdateByQueryDocumentRequest updateByQueryDocumentRequest =
			new UpdateByQueryDocumentRequest(
				_queries.rangeTerm(
					"instanceId", true, true, startInstanceId, endInstanceId),
				_scripts.script(
					StringBundler.concat(
						"if (!ctx._source.containsKey('slaDefinitionIds')) ",
						"ctx._source['slaDefinitionIds'] = [];",
						"ctx._source.slaDefinitionIds.add(", slaDefinitionId,
						")")),
				indexName + WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE);

		if (PortalRunMode.isTestMode()) {
			updateByQueryDocumentRequest.setRefresh(true);
		}

		_searchEngineAdapter.execute(updateByQueryDocumentRequest);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		WorkflowMetricsSLAProcessBackgroundTaskExecutor.class);

	private final DateTimeFormatter _dateTimeFormatter =
		DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndex _instanceWorkflowMetricsIndex;

	@Reference(target = ModuleServiceLifecycle.PORTLETS_INITIALIZED)
	private ModuleServiceLifecycle _moduleServiceLifecycle;

	@Reference
	private Queries _queries;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchCapabilities _searchCapabilities;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private SLAInstanceResultWorkflowMetricsIndexer
		_slaInstanceResultWorkflowMetricsIndexer;

	@Reference
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@Reference
	private Sorts _sorts;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

	@Reference
	private WorkflowMetricsSLADefinitionVersionLocalService
		_workflowMetricsSLADefinitionVersionLocalService;

	@Reference
	private WorkflowMetricsSLAProcessor _workflowMetricsSLAProcessor;

}