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

import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.DateRangeAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.RangeAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.document.Document;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Process;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.ProcessEntityModel;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.ProcessResource;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
	public Process getProcess(Long processId) throws Exception {
		SearchSearchResponse searchSearchResponse =
			_getProcessesSearchSearchResponse(processId, null, null, null);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Process process = _createProcess(searchHit);

			TermsAggregationResult termsAggregationResult =
				_getInstanceTermsAggregationResult(
					null, null, Collections.singleton(processId));

			for (Bucket bucket : termsAggregationResult.getBuckets()) {
				_setInstanceCount(bucket, process);
			}

			TermsAggregationResult slaTermsAggregationResult =
				_getSLATermsAggregationResult(
					null, null, Collections.singleton(processId));

			for (Bucket bucket : slaTermsAggregationResult.getBuckets()) {
				_setDueAfterInstanceCount(bucket, process);
				_setDueInInstanceCount(bucket, process);
				_setOntimeInstanceCount(bucket, process);
				_setOverdueInstanceCount(bucket, process);
			}

			return process;
		}

		return new Process();
	}

	@Override
	public Page<Process> getProcessesPage(
			String title, Pagination pagination, Sort[] sorts)
		throws Exception {

		FieldSort fieldSort = _toFieldSort(sorts);

		SearchSearchResponse searchSearchResponse =
			_getProcessesSearchSearchResponse(
				null, fieldSort, pagination, title);

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

	private BooleanFilter _createInstanceBooleanFilter(Set<Long> processIds) {
		return new BooleanFilter() {
			{
				TermsFilter termsFilter = new TermsFilter("processId");

				for (long processId : processIds) {
					termsFilter.addValue(String.valueOf(processId));
				}

				add(termsFilter, BooleanClauseOccur.MUST);

				addRequiredTerm("companyId", contextCompany.getCompanyId());
				addRequiredTerm("completed", false);
				addRequiredTerm("deleted", false);
			}
		};
	}

	private Process _createProcess(SearchHit searchHit) {
		Process process = new Process();

		Document document = searchHit.getDocument();

		process.setId(GetterUtil.getLong(document.getFieldValue("processId")));

		process.setInstanceCount(0L);

		process.setTitle(
			GetterUtil.getString(document.getFieldValue(_getTitleFieldName())));

		return process;
	}

	private BooleanFilter _createProcessBooleanFilter() {
		return new BooleanFilter() {
			{
				addRequiredTerm("companyId", contextCompany.getCompanyId());
				addRequiredTerm("deleted", false);
			}
		};
	}

	private BooleanFilter _createSLABooleanFilter(Set<Long> processIds) {
		return new BooleanFilter() {
			{
				TermsFilter termsFilter = new TermsFilter("processId");

				for (long processId : processIds) {
					termsFilter.addValue(String.valueOf(processId));
				}

				add(termsFilter, BooleanClauseOccur.MUST);

				addRequiredTerm("companyId", contextCompany.getCompanyId());
			}
		};
	}

	private TermsAggregationResult _getInstanceTermsAggregationResult(
		FieldSort fieldSort, Pagination pagination, Set<Long> processIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processIds", "processId");

		termsAggregation.addChildAggregation(
			_aggregations.cardinality("instanceCount", "instanceId"));

		if ((fieldSort != null) &&
			_isOrderByInstanceCount(fieldSort.getField())) {

			BucketSortPipelineAggregation bucketSortPipelineAggregation =
				_aggregations.bucketSort("sort");

			bucketSortPipelineAggregation.addSortFields(fieldSort);
			bucketSortPipelineAggregation.setFrom(
				pagination.getStartPosition());
			bucketSortPipelineAggregation.setSize(pagination.getPageSize());

			termsAggregation.addPipelineAggregation(
				bucketSortPipelineAggregation);
		}

		termsAggregation.setSize(processIds.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-instances");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(
						_createInstanceBooleanFilter(processIds));
				}
			});

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("processIds");
	}

	private Collection<Process> _getProcesses(
			FieldSort fieldSort, Pagination pagination, SearchHits searchHits)
		throws Exception {

		Map<Long, Process> processesMap = new LinkedHashMap<>();

		for (SearchHit searchHit : searchHits.getSearchHits()) {
			Process process = _createProcess(searchHit);

			processesMap.put(process.getId(), process);
		}

		return _getProcesses(
			_getInstanceTermsAggregationResult(
				fieldSort, pagination, processesMap.keySet()),
			fieldSort, processesMap);
	}

	private List<Process> _getProcesses(
			TermsAggregationResult termsAggregationResult, FieldSort fieldSort,
			Map<Long, Process> processesMap)
		throws Exception {

		List<Process> processes = new LinkedList<>();

		if (_isOrderByTitle(fieldSort.getField())) {
			for (Process process : processesMap.values()) {
				processes.add(
					_setInstanceCount(
						termsAggregationResult.getBucket(
							String.valueOf(process.getId())),
						process));
			}
		}
		else {
			for (Bucket bucket : termsAggregationResult.getBuckets()) {
				processes.add(
					_setInstanceCount(
						bucket,
						processesMap.remove(Long.valueOf(bucket.getKey()))));
			}
		}

		return processes;
	}

	private SearchSearchResponse _getProcessesSearchSearchResponse(
		Long processId, FieldSort fieldSort, Pagination pagination,
		String title) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					if (Validator.isNotNull(processId)) {
						addTerm("processId", processId);
					}

					if (Validator.isNotNull(title)) {
						addTerm(_getTitleFieldName(), title);
					}

					setPreBooleanFilter(_createProcessBooleanFilter());
				}
			});
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
		FieldSort fieldSort, Pagination pagination, Set<Long> processIds) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"processIds", "processId");

		DateRangeAggregation dateRangeAggregation = _aggregations.dateRange(
			"overdueDate", "overdueDate");

		dateRangeAggregation.addRange("dueAfter", "now+7d", null);
		dateRangeAggregation.addRange("dueIn", null, "now+7d");

		dateRangeAggregation.addChildAggregation(
			_aggregations.cardinality("instanceCount", "instanceId"));

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _queries.term("onTime", true));

		onTimeFilterAggregation.addChildAggregation(
			_aggregations.cardinality("onTime", "instanceId"));

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _queries.term("onTime", false));

		overdueFilterAggregation.addChildAggregation(
			_aggregations.cardinality("overdue", "instanceId"));

		termsAggregation.addChildrenAggregations(
			dateRangeAggregation, onTimeFilterAggregation,
			overdueFilterAggregation);

		if ((fieldSort != null) &&
			!_isOrderByInstanceCount(fieldSort.getField()) &&
			!_isOrderByTitle(fieldSort.getField())) {

			BucketSortPipelineAggregation bucketSortPipelineAggregation =
				_aggregations.bucketSort("sort");

			bucketSortPipelineAggregation.addSortFields(fieldSort);
			bucketSortPipelineAggregation.setFrom(
				pagination.getStartPosition());
			bucketSortPipelineAggregation.setSize(pagination.getPageSize());

			termsAggregation.addPipelineAggregation(
				bucketSortPipelineAggregation);
		}

		termsAggregation.setSize(processIds.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-process-result");
		searchSearchRequest.setQuery(
			new BooleanQueryImpl() {
				{
					setPreBooleanFilter(_createSLABooleanFilter(processIds));
				}
			});

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("processIds");
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

	private Process _setDueAfterInstanceCount(Bucket bucket, Process process) {
		if (bucket != null) {
			RangeAggregationResult rangeAggregationResult =
				(RangeAggregationResult)bucket.getChildAggregationResult(
					"overdueDate");

			Bucket dueAfterBucket = rangeAggregationResult.getBucket(
				"dueAfter");

			CardinalityAggregationResult cardinalityAggregationResult =
				(CardinalityAggregationResult)
					dueAfterBucket.getChildAggregationResult("instanceCount");

			process.setDueAfterInstanceCount(
				cardinalityAggregationResult.getValue());
		}

		return process;
	}

	private Process _setDueInInstanceCount(Bucket bucket, Process process) {
		if (bucket != null) {
			RangeAggregationResult rangeAggregationResult =
				(RangeAggregationResult)bucket.getChildAggregationResult(
					"overdueDate");

			Bucket dueInBucket = rangeAggregationResult.getBucket("dueIn");

			CardinalityAggregationResult cardinalityAggregationResult =
				(CardinalityAggregationResult)
					dueInBucket.getChildAggregationResult("instanceCount");

			process.setDueInInstanceCount(
				cardinalityAggregationResult.getValue());
		}

		return process;
	}

	private Process _setInstanceCount(Bucket bucket, Process process) {
		if (bucket != null) {
			CardinalityAggregationResult cardinalityAggregationResult =
				(CardinalityAggregationResult)bucket.getChildAggregationResult(
					"instanceCount");

			process.setInstanceCount(
				cardinalityAggregationResult.getValue() - 1);
		}

		return process;
	}

	private Process _setOntimeInstanceCount(Bucket bucket, Process process) {
		if (bucket != null) {
			FilterAggregationResult filterAggregationResult =
				(FilterAggregationResult)bucket.getChildAggregationResult(
					"onTime");

			CardinalityAggregationResult cardinalityAggregationResult =
				(CardinalityAggregationResult)
					filterAggregationResult.getChildAggregationResult("onTime");

			process.setOntimeInstanceCount(
				cardinalityAggregationResult.getValue());
		}

		return process;
	}

	private Process _setOverdueInstanceCount(Bucket bucket, Process process) {
		if (bucket != null) {
			FilterAggregationResult filterAggregationResult =
				(FilterAggregationResult)bucket.getChildAggregationResult(
					"overdue");

			CardinalityAggregationResult cardinalityAggregationResult =
				(CardinalityAggregationResult)
					filterAggregationResult.getChildAggregationResult(
						"overdue");

			process.setOverdueInstanceCount(
				cardinalityAggregationResult.getValue());
		}

		return process;
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		String titleFieldName = Field.getSortableFieldName(
			_getTitleFieldName());

		Sort sort = new Sort(titleFieldName, false);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (_isOrderByTitle(fieldName)) {
			fieldName = titleFieldName;
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
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}