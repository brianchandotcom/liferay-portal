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

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.ProcessEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {EntityModelResource.class, ProcessResource.class}
)
public class ProcessResourceImpl
	extends BaseProcessResourceImpl implements EntityModelResource {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Process getProcess(
			Long processId, Boolean completed, Date dateEnd, Date dateStart)
		throws Exception {

		return Stream.of(
			_getProcessesSearchSearchResponse(null, null, processId, null)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> {
				Process process = _createProcess(document);

				Bucket bucket = _getProcessBucket(
					GetterUtil.getBoolean(completed), dateEnd, dateStart,
					processId);

				_populateProcessWithSLAMetrics(bucket, process);
				_setInstanceCount(bucket, process);

				_setUntrackedInstanceCount(process);

				return process;
			}
		).orElseGet(
			Process::new
		);
	}

	@Override
	public Page<Process> getProcessesPage(
			String title, Pagination pagination, Sort[] sorts)
		throws Exception {

		FieldSort fieldSort = _toFieldSort(sorts);

		SearchSearchResponse searchSearchResponse =
			_getProcessesSearchSearchResponse(
				fieldSort, pagination, null, title);

		long count = searchSearchResponse.getCount();

		if (count > 0) {
			return Page.of(
				_getProcesses(
					fieldSort, pagination,
					searchSearchResponse.getSearchHits()),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	@Override
	public String getProcessTitle(Long processId) throws Exception {
		return Stream.of(
			_getProcessesSearchSearchResponse(null, null, processId, null)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> document.getString(_getTitleFieldName())
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private BooleanQuery _createBooleanQuery(boolean completed) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addShouldQueryClauses(
			_queries.term("completed", completed),
			_queries.term("instanceId", 0));
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Set<Long> processIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery instancesBooleanQuery = _queries.booleanQuery();

		instancesBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-instances"));
		instancesBooleanQuery.addMustNotQueryClauses(
			_queries.term("instanceId", 0));
		instancesBooleanQuery.addMustQueryClauses(
			_createInstanceBooleanQuery(completed, processIds));

		BooleanQuery slaProcessResultsBooleanQuery = _queries.booleanQuery();

		slaProcessResultsBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-sla-process-results"));
		slaProcessResultsBooleanQuery.addMustNotQueryClauses(
			_queries.term("slaDefinitionId", 0));
		slaProcessResultsBooleanQuery.addMustQueryClauses(
			_createSLAProcessResultsBooleanQuery(completed, processIds));

		return booleanQuery.addShouldQueryClauses(
			instancesBooleanQuery, slaProcessResultsBooleanQuery);
	}

	private BucketSelectorPipelineAggregation
		_createBucketSelectorPipelineAggregation() {

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector", _scripts.script("params.instanceCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"instanceCount", "instanceCount.value");

		return bucketSelectorPipelineAggregation;
	}

	private BooleanQuery _createInstanceBooleanQuery(
		boolean completed, Set<Long> processIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_createBooleanQuery(completed),
			_createProcessIdTermsQuery(processIds));
	}

	private Process _createProcess(Document document) {
		return new Process() {
			{
				id = document.getLong("processId");
				instanceCount = 0L;
				onTimeInstanceCount = 0L;
				overdueInstanceCount = 0L;
				title = document.getString(_getTitleFieldName());
			}
		};
	}

	private BooleanQuery _createProcessBooleanQuery(
		Long processId, String title) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (Validator.isNotNull(processId)) {
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId));
		}

		if (Validator.isNotNull(title)) {
			booleanQuery.addMustQueryClauses(
				_queries.term(
					Field.getSortableFieldName(_getTitleFieldName()), title));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE));
	}

	private TermsQuery _createProcessIdTermsQuery(Set<Long> processIds) {
		TermsQuery termsQuery = _queries.terms("processId");

		Stream<Long> stream = processIds.stream();

		termsQuery.addValues(
			stream.map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return termsQuery;
	}

	private BooleanQuery _createSLAProcessResultsBooleanQuery(
		boolean completed, Set<Long> processIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (completed) {
			BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

			shouldBooleanQuery.addShouldQueryClauses(
				_queries.term("slaDefinitionId", 0),
				_queries.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()));

			booleanQuery.addMustQueryClauses(shouldBooleanQuery);
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()));
		}

		booleanQuery.addMustNotQueryClauses(
			_queries.term("status", WorkflowMetricsSLAStatus.EXPIRED.name()));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_createProcessIdTermsQuery(processIds));
	}

	private TermsAggregationResult _getInstanceTermsAggregationResult(
		boolean completed, FieldSort fieldSort, Pagination pagination,
		Set<Long> processIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processId", "processId");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		FilterAggregation filterAggregation = _aggregations.filter(
			"instanceCountFilter",
			booleanQuery.addMustNotQueryClauses(
				_queries.term("instanceId", "0")));

		filterAggregation.addChildAggregation(
			_aggregations.valueCount("instanceCount", "instanceId"));

		termsAggregation.addChildrenAggregations(filterAggregation);

		if ((fieldSort != null) &&
			_isOrderByInstanceCount(fieldSort.getField())) {

			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(processIds.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			_createInstanceBooleanQuery(completed, processIds));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("processId");
	}

	private Bucket _getProcessBucket(
		boolean completed, Date dateEnd, Date dateStart, long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processId", "processId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			onTimeFilterAggregation, overdueFilterAggregation,
			_resourceHelper.creatInstanceCountScriptedMetricAggregation(
				Collections.emptyList(), dateEnd, dateStart,
				Collections.emptyList(), Collections.emptyList(),
				Collections.emptyList()));

		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-instances",
			"workflow-metrics-sla-process-results");

		searchSearchRequest.setQuery(
			_createBooleanQuery(completed, Collections.singleton(processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("processId");

		return termsAggregationResult.getBucket(String.valueOf(processId));
	}

	private Collection<Process> _getProcesses(
			FieldSort fieldSort, Pagination pagination, SearchHits searchHits)
		throws Exception {

		List<Process> processes = new LinkedList<>();

		Map<Long, Process> processesMap = Stream.of(
			searchHits.getSearchHits()
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).map(
			this::_createProcess
		).collect(
			LinkedHashMap::new,
			(map, process) -> map.put(process.getId(), process), Map::putAll
		);

		TermsAggregationResult instanceTermsAggregationResult =
			_getInstanceTermsAggregationResult(
				false, fieldSort, pagination, processesMap.keySet());
		TermsAggregationResult slaTermsAggregationResult =
			_getSLATermsAggregationResult(
				false, fieldSort, pagination, processesMap.keySet());

		if (_isOrderByInstanceCount(fieldSort.getField())) {
			for (Bucket bucket : instanceTermsAggregationResult.getBuckets()) {
				Process process = processesMap.remove(
					Long.valueOf(bucket.getKey()));

				_populateProcessWithSLAMetrics(
					slaTermsAggregationResult.getBucket(bucket.getKey()),
					process);
				_setInstanceCount(bucket, process);
				_setUntrackedInstanceCount(process);

				processes.add(process);
			}
		}
		else if (_isOrderByTitle(fieldSort.getField())) {
			for (Process process : processesMap.values()) {
				_populateProcessWithSLAMetrics(
					slaTermsAggregationResult.getBucket(
						String.valueOf(process.getId())),
					process);
				_setInstanceCount(
					instanceTermsAggregationResult.getBucket(
						String.valueOf(process.getId())),
					process);
				_setUntrackedInstanceCount(process);

				processes.add(process);
			}
		}
		else {
			for (Bucket bucket : slaTermsAggregationResult.getBuckets()) {
				Process process = processesMap.remove(
					Long.valueOf(bucket.getKey()));

				_populateProcessWithSLAMetrics(bucket, process);
				_setInstanceCount(
					instanceTermsAggregationResult.getBucket(bucket.getKey()),
					process);
				_setUntrackedInstanceCount(process);

				processes.add(process);
			}
		}

		if (processes.size() > pagination.getPageSize()) {
			return processes.subList(0, processes.size() - 1);
		}

		return processes;
	}

	private SearchSearchResponse _getProcessesSearchSearchResponse(
		FieldSort fieldSort, Pagination pagination, Long processId,
		String title) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");
		searchSearchRequest.setQuery(
			_createProcessBooleanQuery(processId, title));
		searchSearchRequest.setSelectedFieldNames(
			"processId", _getTitleFieldName());

		if ((fieldSort != null) && _isOrderByTitle(fieldSort.getField())) {
			searchSearchRequest.setSize(pagination.getPageSize());
			searchSearchRequest.setSorts(Collections.singletonList(fieldSort));
			searchSearchRequest.setStart(pagination.getStartPosition());
		}
		else {
			searchSearchRequest.setSize(10000);
			searchSearchRequest.setStart(0);
		}

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private TermsAggregationResult _getSLATermsAggregationResult(
		boolean completed, FieldSort fieldSort, Pagination pagination,
		Set<Long> processIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processId", "processId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			onTimeFilterAggregation, overdueFilterAggregation);

		if ((fieldSort != null) &&
			!_isOrderByInstanceCount(fieldSort.getField()) &&
			!_isOrderByTitle(fieldSort.getField())) {

			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(processIds.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-process-results");
		searchSearchRequest.setQuery(
			_createSLAProcessResultsBooleanQuery(completed, processIds));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("processId");
	}

	private String _getTitleFieldName() {
		return Field.getLocalizedName(
			contextAcceptLanguage.getPreferredLocale(), "title");
	}

	private boolean _isOrderByInstanceCount(String fieldName) {
		return StringUtil.startsWith(fieldName, "instanceCount");
	}

	private boolean _isOrderByTitle(String fieldName) {
		return StringUtil.startsWith(fieldName, "title");
	}

	private void _populateProcessWithSLAMetrics(
		Bucket bucket, Process process) {

		_setOnTimeInstanceCount(bucket, process);
		_setOverdueInstanceCount(bucket, process);
	}

	private void _setInstanceCount(Bucket bucket, Process process) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"instanceCountFilter");

		ValueCountAggregationResult valueCountAggregationResult;

		if (filterAggregationResult != null) {
			valueCountAggregationResult =
				(ValueCountAggregationResult)
					filterAggregationResult.getChildAggregationResult(
						"instanceCount");

			process.setInstanceCount(valueCountAggregationResult.getValue());
		}
		else {
			ScriptedMetricAggregationResult scriptedMetricAggregationResult =
				(ScriptedMetricAggregationResult)
					bucket.getChildAggregationResult("instanceCount");

			process.setInstanceCount(
				GetterUtil.getLong(scriptedMetricAggregationResult.getValue()));
		}
	}

	private void _setOnTimeInstanceCount(Bucket bucket, Process process) {
		if (bucket == null) {
			return;
		}

		process.setOnTimeInstanceCount(
			_resourceHelper.getOnTimeInstanceCount(bucket));
	}

	private void _setOverdueInstanceCount(Bucket bucket, Process process) {
		if (bucket == null) {
			return;
		}

		process.setOverdueInstanceCount(
			_resourceHelper.getOverdueInstanceCount(bucket));
	}

	private void _setUntrackedInstanceCount(Process process) {
		process.setUntrackedInstanceCount(
			process.getInstanceCount() -
				GetterUtil.getLong(process.getOnTimeInstanceCount()) -
					GetterUtil.getLong(process.getOverdueInstanceCount()));
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		String titleFieldName = Field.getSortableFieldName(
			_getTitleFieldName());

		Sort sort = new Sort(titleFieldName, false);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (_isOrderByInstanceCount(fieldName)) {
			fieldName = "instanceCountFilter > instanceCount";
		}
		else if (_isOrderByTitle(fieldName)) {
			fieldName = titleFieldName;
		}
		else {
			fieldName =
				StringUtil.extractFirst(fieldName, "InstanceCount") +
					" > instanceCount.value";
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final EntityModel _entityModel = new ProcessEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private Scripts _scripts;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}