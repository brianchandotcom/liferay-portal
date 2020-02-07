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
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
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
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
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
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
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
			Long processId, Boolean completed, Date dateEnd, Date dateStart,
			String key, Pagination pagination, Sort[] sorts)
		throws Exception {

		String latestProcessVersion = _resourceHelper.getLatestProcessVersion(
			contextCompany.getCompanyId(), processId);

		Map<String, Bucket> taskBuckets = _getTaskBuckets(
			GetterUtil.getBoolean(completed), key, processId,
			latestProcessVersion);

		Map<String, Task> tasksMap = _getTasksMap(
			key, processId, taskBuckets.keySet(), latestProcessVersion);

		long count = tasksMap.size();

		if (count > 0) {
			if (pagination == null) {
				return Page.of(tasksMap.values());
			}

			return Page.of(
				_getTasks(
					GetterUtil.getBoolean(completed), dateEnd, dateStart,
					_toFieldSort(sorts), pagination, processId, tasksMap),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery filterBooleanQuery = _queries.booleanQuery();

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery slaTaskResultsBooleanQuery = _queries.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-sla-task-results"));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(
				completed, dateEnd, dateStart, processId, taskNames));

		BooleanQuery tokensBooleanQuery = _queries.booleanQuery();

		tokensBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-tokens"));
		tokensBooleanQuery.addMustQueryClauses(
			_createTokensBooleanQuery(
				completed, dateEnd, dateStart, processId, taskNames));

		return filterBooleanQuery.addFilterQueryClauses(
			booleanQuery.addShouldQueryClauses(
				slaTaskResultsBooleanQuery, tokensBooleanQuery));
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("completed", completed),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createBooleanQuery(long processId, String version) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId),
			_queries.term("version", version));
	}

	private BooleanQuery _createCompletionDateBooleanQuery(
		Date dateEnd, Date dateStart) {

		BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

		BooleanQuery mustBooleanQuery = _queries.booleanQuery();

		return shouldBooleanQuery.addShouldQueryClauses(
			mustBooleanQuery.addMustQueryClauses(
				_queries.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.formatDate(dateStart),
					_resourceHelper.formatDate(dateEnd)),
				_queries.term("instanceCompleted", true)),
			_queries.term("slaDefinitionId", 0));
	}

	private BooleanQuery _createFilterBooleanQuery(
		boolean completed, String key, long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (Validator.isNotNull(key)) {
			booleanQuery.addMustQueryClauses(
				_queries.wildcard("taskName", "*" + key + "*"));
		}

		booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(completed, processId));

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createFilterBooleanQuery(
		String key, long processId, Set<String> taskNames, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (Validator.isNotNull(key)) {
			booleanQuery.addMustQueryClauses(
				_queries.wildcard("name", "*" + key + "*"));
		}

		if (!taskNames.isEmpty()) {
			TermsQuery termsQuery = _queries.terms("name");

			termsQuery.addValues(taskNames.toArray(new Object[0]));

			booleanQuery.addShouldQueryClauses(termsQuery);
		}

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createNodesBooleanQuery(
		String key, long processId, Set<String> taskNames, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(key, processId, taskNames, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("type", "TASK"));
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (completed) {
			if ((dateEnd != null) && (dateStart != null)) {
				booleanQuery.addMustQueryClauses(
					_createCompletionDateBooleanQuery(dateEnd, dateStart));
			}
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				_queries.exists("completionDate"),
				_queries.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()));
			booleanQuery.addMustQueryClauses(
				_queries.term("instanceCompleted", Boolean.FALSE));
		}

		TermsQuery termsQuery = _queries.terms("taskName");

		termsQuery.addValues(taskNames.toArray(new Object[0]));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId), termsQuery);
	}

	private Task _createTask(String taskName) {
		return new Task() {
			{
				key = taskName;
				name = _language.get(
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						contextAcceptLanguage.getPreferredLocale(),
						TaskResourceImpl.class),
					taskName);
			}
		};
	}

	private BooleanQuery _createTokensBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		Set<String> taskNames) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", "0"));

		if (completed && (dateEnd != null) && (dateStart != null)) {
			booleanQuery.addMustQueryClauses(
				_queries.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.formatDate(dateStart),
					_resourceHelper.formatDate(dateEnd)));
		}

		if (!taskNames.isEmpty()) {
			TermsQuery termsQuery = _queries.terms("taskName");

			termsQuery.addValues(taskNames.toArray(new Object[0]));

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", completed),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceCompleted", completed),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createTokensBooleanQuery(
		boolean completed, String key, long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(completed, key, processId, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE));
	}

	private Map<String, Bucket> _getTaskBuckets(
		boolean completed, String key, long processId, String version) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", "taskName");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createTokensBooleanQuery(completed, key, processId, version));

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
			(map, bucket) -> map.put(bucket.getKey(), bucket), Map::putAll);
	}

	private Collection<Task> _getTasks(
		boolean completed, Date dateEnd, Date dateStart, FieldSort fieldSort,
		Pagination pagination, long processId, Map<String, Task> tasksMap) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"taskName", "taskName");

		FilterAggregation breachedFilterAggregation = _aggregations.filter(
			"breached",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		breachedFilterAggregation.addChildAggregation(
			_resourceHelper.createBreachedScriptedMetricAggregation());

		FilterAggregation countFilterAggregation = _aggregations.filter(
			"countFilter", _resourceHelper.createTokensBooleanQuery(completed));

		countFilterAggregation.addChildrenAggregations(
			_aggregations.avg("durationAvg", "duration"),
			_aggregations.valueCount("instanceCount", "instanceId"));

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue",
			_resourceHelper.createInstanceCompletedBooleanQuery(completed));

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			breachedFilterAggregation, countFilterAggregation,
			onTimeFilterAggregation, overdueFilterAggregation);

		termsAggregation.addPipelineAggregation(
			_resourceHelper.createBucketScriptPipelineAggregation());

		if (fieldSort != null) {
			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(tasksMap.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-tokens", "workflow-metrics-sla-task-results");
		searchSearchRequest.setQuery(
			_createBooleanQuery(
				completed, dateEnd, dateStart, processId, tasksMap.keySet()));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("taskName")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> {
				Task task = tasksMap.remove(bucket.getKey());

				_populateTaskWithSLAMetrics(bucket, completed, task);
				_setDurationAvg(bucket, task);
				_setInstanceCount(bucket, task);

				return task;
			}
		).collect(
			Collectors.toList()
		);
	}

	private Map<String, Task> _getTasksMap(
		String key, long processId, Set<String> taskNames, String version) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");
		searchSearchRequest.setQuery(
			_createNodesBooleanQuery(key, processId, taskNames, version));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("name")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
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

	private boolean _isOrderByDurationAvg(String fieldName) {
		if (StringUtil.equals(fieldName, "durationAvg") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"countFilter", StringPool.GREATER_THAN, "durationAvg"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "instanceCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"countFilter", StringPool.GREATER_THAN, "instanceCount"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOnTimeInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "onTimeInstanceCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"onTime", StringPool.GREATER_THAN,
					"instanceCount.value"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOverdueInstanceCount(String fieldName) {
		if (StringUtil.equals(fieldName, "overdueInstanceCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"overdue", StringPool.GREATER_THAN,
					"instanceCount.value"))) {

			return true;
		}

		return false;
	}

	private void _populateTaskWithSLAMetrics(
		Bucket bucket, boolean completed, Task task) {

		if (completed) {
			_setBreachedInstanceCount(bucket, task);
			_setBreachedInstancePercentage(bucket, task);
		}
		else {
			_setOnTimeInstanceCount(bucket, task);
			_setOverdueInstanceCount(bucket, task);
		}
	}

	private void _setBreachedInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		task.setBreachedInstanceCount(
			_resourceHelper.getBreachedInstanceCount(bucket));
	}

	private void _setBreachedInstancePercentage(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		task.setBreachedInstancePercentage(
			_resourceHelper.getBreachedInstancePercentage(bucket));
	}

	private void _setDurationAvg(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		AvgAggregationResult avgAggregationResult =
			(AvgAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"durationAvg");

		task.setDurationAvg(
			GetterUtil.getLong(avgAggregationResult.getValue()));
	}

	private void _setInstanceCount(Bucket bucket, Task task) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		ValueCountAggregationResult valueCountAggregationResult =
			(ValueCountAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"instanceCount");

		task.setInstanceCount(
			GetterUtil.getLong(valueCountAggregationResult.getValue()));
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

		if (_isOrderByDurationAvg(fieldName)) {
			fieldName = StringBundler.concat(
				"countFilter", StringPool.GREATER_THAN, "durationAvg");
		}
		else if (_isOrderByInstanceCount(fieldName)) {
			fieldName = StringBundler.concat(
				"countFilter", StringPool.GREATER_THAN, "instanceCount");
		}
		else if (_isOrderByOnTimeInstanceCount(fieldName) ||
				 _isOrderByOverdueInstanceCount(fieldName)) {

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
	private Language _language;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

}