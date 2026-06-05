/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.StringQuery;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.ProcessMetric;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.ProcessUtil;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.ProcessMetricEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessMetricResource;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/process-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = ProcessMetricResource.class
)
public class ProcessMetricResourceImpl extends BaseProcessMetricResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public ProcessMetric getProcessMetric(
			Long processId, Boolean completed, Date dateEnd, Date dateStart)
		throws Exception {

		SearchSearchResponse searchSearchResponse =
			_getProcessMetricsSearchSearchResponse(null, null, processId, null);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (searchHitsList.isEmpty()) {
			return new ProcessMetric();
		}

		SearchHit searchHit = searchHitsList.get(0);

		ProcessMetric processMetric = _createProcessMetric(
			searchHit.getDocument());

		Bucket bucket = _getProcessBucket(
			GetterUtil.getBoolean(completed), dateEnd, dateStart, processId);

		_populateProcessWithSLAMetrics(bucket, processMetric);
		_setInstanceCount(bucket, processMetric);

		_setUntrackedInstanceCount(processMetric);

		return processMetric;
	}

	@Override
	public Page<ProcessMetric> getProcessMetricsPage(
			String title, Pagination pagination, Sort[] sorts)
		throws Exception {

		FieldSort fieldSort = _toFieldSort(sorts);

		SearchSearchResponse searchSearchResponse =
			_getProcessMetricsSearchSearchResponse(
				fieldSort, pagination, null, title);

		long count = searchSearchResponse.getCount();

		if (count > 0) {
			return Page.of(
				_getProcessMetrics(
					fieldSort, pagination,
					searchSearchResponse.getSearchHits()),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(boolean completed) {
		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		return booleanQuery.addShouldQueryClauses(
			QueriesUtil.term("completed", completed),
			QueriesUtil.term("instanceId", 0));
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Set<Long> processIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.setMinimumShouldMatch(1);

		BooleanQuery instancesBooleanQuery = QueriesUtil.booleanQuery();

		instancesBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE));
		instancesBooleanQuery.addMustNotQueryClauses(
			QueriesUtil.term("instanceId", 0));
		instancesBooleanQuery.addMustQueryClauses(
			_createInstanceBooleanQuery(
				completed, dateEnd, dateStart, processIds));

		BooleanQuery slaInstanceResultsBooleanQuery =
			QueriesUtil.booleanQuery();

		slaInstanceResultsBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.
						SUFFIX_SLA_INSTANCE_RESULT));
		slaInstanceResultsBooleanQuery.addMustNotQueryClauses(
			QueriesUtil.term("slaDefinitionId", 0));
		slaInstanceResultsBooleanQuery.addMustQueryClauses(
			_createSLAInstanceResultsBooleanQuery(
				completed, dateEnd, dateStart, processIds));

		return booleanQuery.addShouldQueryClauses(
			instancesBooleanQuery, slaInstanceResultsBooleanQuery);
	}

	private BucketSelectorPipelineAggregation
		_createBucketSelectorPipelineAggregation() {

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector",
				Scripts.INSTANCE.script("params.instanceCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"instanceCount", "instanceCount.value");

		return bucketSelectorPipelineAggregation;
	}

	private BooleanQuery _createCompletionDateBooleanQuery(
		Date dateEnd, Date dateStart) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		return booleanQuery.addShouldQueryClauses(
			QueriesUtil.rangeTerm(
				"completionDate", true, true,
				_resourceHelper.getDate(dateStart),
				_resourceHelper.getDate(dateEnd)),
			QueriesUtil.term("slaDefinitionId", 0));
	}

	private BooleanQuery _createInstanceBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Set<Long> processIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		if (completed && (dateEnd != null) && (dateStart != null)) {
			booleanQuery.addMustQueryClauses(
				QueriesUtil.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.getDate(dateStart),
					_resourceHelper.getDate(dateEnd)));
		}

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("active", Boolean.TRUE),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE),
			_createBooleanQuery(completed),
			_createProcessIdTermsQuery(processIds));
	}

	private BooleanQuery _createProcessBooleanQuery(
		Long processId, String title) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		if (Validator.isNotNull(processId)) {
			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("processId", processId));
		}

		if (Validator.isNotNull(title)) {
			booleanQuery.addMustQueryClauses(_createTitleBooleanQuery(title));
		}

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE));
	}

	private TermsQuery _createProcessIdTermsQuery(Set<Long> processIds) {
		TermsQuery termsQuery = QueriesUtil.terms("processId");

		termsQuery.addValues(
			transformToArray(processIds, String::valueOf, Object.class));

		return termsQuery;
	}

	private ProcessMetric _createProcessMetric(Document document) {
		return new ProcessMetric() {
			{
				setInstanceCount(() -> 0L);
				setOnTimeInstanceCount(() -> 0L);
				setOverdueInstanceCount(() -> 0L);
				setProcess(
					() -> ProcessUtil.toProcess(
						document, contextAcceptLanguage.getPreferredLocale()));
			}
		};
	}

	private BooleanQuery _createSLAInstanceResultsBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Set<Long> processIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		if (completed) {
			BooleanQuery shouldBooleanQuery = QueriesUtil.booleanQuery();

			shouldBooleanQuery.addShouldQueryClauses(
				QueriesUtil.term("slaDefinitionId", 0),
				QueriesUtil.term("instanceCompleted", Boolean.TRUE));

			booleanQuery.addMustQueryClauses(shouldBooleanQuery);

			if ((dateEnd != null) && (dateStart != null)) {
				booleanQuery.addMustQueryClauses(
					_createCompletionDateBooleanQuery(dateEnd, dateStart));
			}
		}
		else {
			booleanQuery.addMustQueryClauses(
				QueriesUtil.term("instanceCompleted", Boolean.FALSE));
		}

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("active", Boolean.TRUE),
			QueriesUtil.term("blocked", Boolean.FALSE),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE),
			_createProcessIdTermsQuery(processIds));
	}

	private BooleanQuery _createTitleBooleanQuery(String title) {
		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		StringQuery stringQuery = QueriesUtil.string(title + StringPool.STAR);

		stringQuery.setDefaultField(_getTitleFieldName());

		return booleanQuery.addShouldQueryClauses(
			stringQuery, QueriesUtil.match(_getTitleFieldName(), title));
	}

	private TermsAggregationResult _getInstanceTermsAggregationResult(
		boolean completed, FieldSort fieldSort, Pagination pagination,
		Set<Long> processIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processId", "processId");

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		FilterAggregation filterAggregation = _aggregations.filter(
			"instanceCountFilter",
			booleanQuery.addMustNotQueryClauses(
				QueriesUtil.term("instanceId", "0")));

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

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE);
		searchSearchRequest.setQuery(
			_createInstanceBooleanQuery(completed, null, null, processIds));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

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
				Collections.emptyList(), null, dateEnd, dateStart,
				Collections.emptyList()));

		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_INSTANCE,
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT);

		searchSearchRequest.setQuery(
			_createBooleanQuery(
				completed, dateEnd, dateStart,
				Collections.singleton(processId)));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("processId");

		return termsAggregationResult.getBucket(String.valueOf(processId));
	}

	private Collection<ProcessMetric> _getProcessMetrics(
			FieldSort fieldSort, Pagination pagination, SearchHits searchHits)
		throws Exception {

		List<ProcessMetric> processMetrics = new LinkedList<>();

		Map<Long, ProcessMetric> processMetricsMap = new LinkedHashMap<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			ProcessMetric processMetric = _createProcessMetric(
				searchHit.getDocument());

			Process process = processMetric.getProcess();

			processMetricsMap.put(process.getId(), processMetric);
		}

		TermsAggregationResult instanceTermsAggregationResult =
			_getInstanceTermsAggregationResult(
				false, fieldSort, pagination, processMetricsMap.keySet());
		TermsAggregationResult slaTermsAggregationResult =
			_getSLATermsAggregationResult(
				false, fieldSort, pagination, processMetricsMap.keySet());

		if (_isOrderByInstanceCount(fieldSort.getField())) {
			for (Bucket bucket : instanceTermsAggregationResult.getBuckets()) {
				ProcessMetric processMetric = processMetricsMap.remove(
					GetterUtil.getLong(bucket.getKey()));

				_populateProcessWithSLAMetrics(
					slaTermsAggregationResult.getBucket(bucket.getKey()),
					processMetric);
				_setInstanceCount(bucket, processMetric);
				_setUntrackedInstanceCount(processMetric);

				processMetrics.add(processMetric);
			}
		}
		else if (_isOrderByTitle(fieldSort.getField())) {
			for (ProcessMetric processMetric : processMetricsMap.values()) {
				Process process = processMetric.getProcess();

				_populateProcessWithSLAMetrics(
					slaTermsAggregationResult.getBucket(
						String.valueOf(process.getId())),
					processMetric);
				_setInstanceCount(
					instanceTermsAggregationResult.getBucket(
						String.valueOf(process.getId())),
					processMetric);

				_setUntrackedInstanceCount(processMetric);

				processMetrics.add(processMetric);
			}
		}
		else {
			for (Bucket bucket : slaTermsAggregationResult.getBuckets()) {
				ProcessMetric processMetric = processMetricsMap.remove(
					GetterUtil.getLong(bucket.getKey()));

				_populateProcessWithSLAMetrics(bucket, processMetric);
				_setInstanceCount(
					instanceTermsAggregationResult.getBucket(bucket.getKey()),
					processMetric);
				_setUntrackedInstanceCount(processMetric);

				processMetrics.add(processMetric);
			}
		}

		return processMetrics;
	}

	private SearchSearchResponse _getProcessMetricsSearchSearchResponse(
		FieldSort fieldSort, Pagination pagination, Long processId,
		String title) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_PROCESS);
		searchSearchRequest.setQuery(
			_createProcessBooleanQuery(processId, title));

		if ((fieldSort != null) && _isOrderByTitle(fieldSort.getField())) {
			searchSearchRequest.setSize(pagination.getPageSize());
			searchSearchRequest.setSorts(Collections.singletonList(fieldSort));
			searchSearchRequest.setStart(pagination.getStartPosition());
		}
		else {
			searchSearchRequest.setSize(10000);
			searchSearchRequest.setStart(0);
		}

		return _searchEngineAdapter.execute(searchSearchRequest);
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
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_INSTANCE_RESULT);
		searchSearchRequest.setQuery(
			_createSLAInstanceResultsBooleanQuery(
				completed, null, null, processIds));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

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
		Bucket bucket, ProcessMetric processMetric) {

		_setOnTimeInstanceCount(bucket, processMetric);
		_setOverdueInstanceCount(bucket, processMetric);
	}

	private void _setInstanceCount(Bucket bucket, ProcessMetric processMetric) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"instanceCountFilter");

		if (filterAggregationResult != null) {
			ValueCountAggregationResult valueCountAggregationResult =
				(ValueCountAggregationResult)
					filterAggregationResult.getChildAggregationResult(
						"instanceCount");

			processMetric.setInstanceCount(
				valueCountAggregationResult::getValue);
		}
		else {
			ScriptedMetricAggregationResult scriptedMetricAggregationResult =
				(ScriptedMetricAggregationResult)
					bucket.getChildAggregationResult("instanceCount");

			processMetric.setInstanceCount(
				() -> _resourceHelper.getScriptedMetricAggregationResultValue(
					scriptedMetricAggregationResult));
		}
	}

	private void _setOnTimeInstanceCount(
		Bucket bucket, ProcessMetric processMetric) {

		if (bucket == null) {
			return;
		}

		processMetric.setOnTimeInstanceCount(
			() -> _resourceHelper.getOnTimeInstanceCount(bucket));
	}

	private void _setOverdueInstanceCount(
		Bucket bucket, ProcessMetric processMetric) {

		if (bucket == null) {
			return;
		}

		processMetric.setOverdueInstanceCount(
			() -> _resourceHelper.getOverdueInstanceCount(bucket));
	}

	private void _setUntrackedInstanceCount(ProcessMetric processMetric) {
		long onTimeInstanceCount = GetterUtil.getLong(
			processMetric.getOnTimeInstanceCount());
		long overdueInstanceCount = GetterUtil.getLong(
			processMetric.getOverdueInstanceCount());

		processMetric.setUntrackedInstanceCount(
			() ->
				processMetric.getInstanceCount() - onTimeInstanceCount -
					overdueInstanceCount);
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

	private static final EntityModel _entityModel =
		new ProcessMetricEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private Sorts _sorts;

}