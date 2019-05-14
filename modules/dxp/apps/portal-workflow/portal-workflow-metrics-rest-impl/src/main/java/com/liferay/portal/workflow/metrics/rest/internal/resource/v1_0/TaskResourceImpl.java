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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.resource.EntityModelResource;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.TaskEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
	properties = "OSGI-INF/liferay/rest/v1_0/task.properties",
	scope = ServiceScope.PROTOTYPE,
	service = {EntityModelResource.class, TaskResource.class}
)
public class TaskResourceImpl
	extends BaseTaskResourceImpl implements EntityModelResource {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<Task> getProcessTasksPage(
		Long processId, Pagination pagination, Sort[] sorts) {

		String latestProcessVersion = _resourceHelper.getLatestProcessVersion(
			contextCompany.getCompanyId(), processId);

		if (pagination == null) {
			Map<String, Long> instanceCountMap = _getInstanceCountMap(
				null, null, processId, latestProcessVersion);

			Map<String, Task> tasksMap = _getTasksMap(
				processId, instanceCountMap.keySet(), latestProcessVersion);

			return Page.of(tasksMap.values());
		}

		FieldSort fieldSort = _toFieldSort(sorts);

		Map<String, Long> instanceCountMap = _getInstanceCountMap(
			fieldSort, pagination, processId, latestProcessVersion);

		Map<String, Task> tasksMap = _getTasksMap(
			processId, instanceCountMap.keySet(), latestProcessVersion);

		long count = tasksMap.size();

		if (count > 0) {
			return Page.of(
				_getTasks(
					fieldSort, instanceCountMap, pagination, processId,
					tasksMap),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("processId", processId));
	}

	private BooleanQuery _createBooleanQuery(long processId, String version) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("processId", processId),
			_queries.term("version", version));
	}

	private BooleanQuery _createFilterBooleanQuery(
		long processId, Set<String> taskNames, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (!taskNames.isEmpty()) {
			TermsQuery termsQuery = _queries.terms("name");

			termsQuery.addValues(taskNames.toArray(new Object[0]));

			booleanQuery.addShouldQueryClauses(termsQuery);
		}

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createFilterBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addShouldQueryClauses(_createBooleanQuery(processId));

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createNodesBooleanQuery(
		long processId, Set<String> taskNames, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(processId, taskNames, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false), _queries.term("type", "TASK"));
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		long processId, Set<String> taskNames) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			_queries.term("status", WorkfowMetricsSLAStatus.COMPLETED),
			_queries.term("status", WorkfowMetricsSLAStatus.EXPIRED));

		TermsQuery termsQuery = _queries.terms("taskName");

		termsQuery.addValues(taskNames.toArray(new Object[0]));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", false),
			_queries.term("processId", processId), termsQuery);
	}

	private Task _createTask(String taskName) {
		return new Task() {
			{
				key = taskName;
				name = LanguageUtil.get(
					contextAcceptLanguage.getPreferredLocale(), taskName);
			}
		};
	}

	private BooleanQuery _createTokensBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(processId, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", false), _queries.term("deleted", false));
	}

	private long _getInstanceCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"instanceCountFilter");

		CardinalityAggregationResult cardinalityAggregationResult =
			(CardinalityAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		return cardinalityAggregationResult.getValue();
	}

	private Map<String, Long> _getInstanceCountMap(
		FieldSort fieldSort, Pagination pagination, long processId,
		String version) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", "taskName");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		FilterAggregation filterAggregation = _aggregations.filter(
			"instanceCountFilter",
			booleanQuery.addMustNotQueryClauses(
				_queries.term("instanceId", "0")));

		filterAggregation.addChildAggregation(
			_aggregations.cardinality("instanceCount", "instanceId"));

		termsAggregation.addChildrenAggregations(filterAggregation);

		if ((fieldSort != null) &&
			_isOrderByInstanceCount(fieldSort.getField())) {

			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createTokensBooleanQuery(processId, version));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("taskName");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.collect(
			LinkedHashMap::new,
			(map, bucket) -> map.put(
				bucket.getKey(), _getInstanceCount(bucket)),
			Map::putAll);
	}

	private TermsAggregationResult _getSLATermsAggregationResult(
		FieldSort fieldSort, Pagination pagination, long processId,
		Set<String> taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", "taskName");

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
			!_isOrderByInstanceCount(fieldSort.getField())) {

			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(taskNames.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-sla-task-results");
		searchSearchRequest.setQuery(
			_createSLATaskResultsBooleanQuery(processId, taskNames));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		return (TermsAggregationResult)aggregationResultsMap.get("taskName");
	}

	private Collection<Task> _getTasks(
		FieldSort fieldSort, Map<String, Long> instanceCountMap,
		Pagination pagination, long processId, Map<String, Task> tasksMap) {

		List<Task> tasks = new LinkedList<>();

		TermsAggregationResult slaTermsAggregationResult =
			_getSLATermsAggregationResult(
				fieldSort, pagination, processId, tasksMap.keySet());

		if (_isOrderByInstanceCount(fieldSort.getField())) {
			instanceCountMap.forEach(
				(taskName, instanceCount) -> {
					Task task = tasksMap.remove(taskName);

					_populateTaskWithSLAMetrics(
						slaTermsAggregationResult.getBucket(taskName), task);
					_setInstanceCount(instanceCount, task);

					tasks.add(task);
				});
		}
		else {
			for (Bucket bucket : slaTermsAggregationResult.getBuckets()) {
				Task task = tasksMap.remove(bucket.getKey());

				_populateTaskWithSLAMetrics(bucket, task);
				_setInstanceCount(instanceCountMap.get(bucket.getKey()), task);

				tasks.add(task);
			}
		}

		if (tasks.size() > pagination.getPageSize()) {
			return tasks.subList(0, tasks.size() - 1);
		}

		return tasks;
	}

	private Map<String, Task> _getTasksMap(
		long processId, Set<String> taskNames, String version) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");
		searchSearchRequest.setQuery(
			_createNodesBooleanQuery(processId, taskNames, version));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		return buckets.stream(
		).map(
			Bucket::getKey
		).map(
			this::_createTask
		).sorted(
			Comparator.comparing(Task::getName)
		).collect(
			LinkedHashMap::new, (map, task) -> map.put(task.getKey(), task),
			Map::putAll
		);
	}

	private boolean _isOrderByInstanceCount(String fieldName) {
		return StringUtil.startsWith(fieldName, "instanceCount");
	}

	private void _populateTaskWithSLAMetrics(Bucket bucket, Task task) {
		_setOnTimeInstanceCount(bucket, task);
		_setOverdueInstanceCount(bucket, task);
	}

	private void _setInstanceCount(Long instanceCount, Task task) {
		task.setInstanceCount(instanceCount);
	}

	private void _setOnTimeInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		task.setOnTimeInstanceCount(
			_resourceHelper.getOnTimeInstanceCount(bucket));
	}

	private void _setOverdueInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		task.setOverdueInstanceCount(
			_resourceHelper.getOverdueInstanceCount(bucket));
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		Sort sort = new Sort("instanceCount", false);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (_isOrderByInstanceCount(fieldName)) {
			fieldName = StringBundler.concat(
				"instanceCountFilter", StringPool.GREATER_THAN,
				"instanceCount");
		}
		else {
			fieldName = StringBundler.concat(
				StringUtil.extractFirst(fieldName, "InstanceCount"),
				StringPool.GREATER_THAN, "instanceCount.value");
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final EntityModel _entityModel = new TaskEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}