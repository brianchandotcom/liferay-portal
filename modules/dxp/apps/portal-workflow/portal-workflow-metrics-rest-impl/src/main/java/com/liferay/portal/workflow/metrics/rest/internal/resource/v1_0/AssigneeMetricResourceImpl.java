/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ValueCountAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.QueriesUtil;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetric;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeMetricBulkSelection;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.AssigneeUtil;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.AssigneeMetricEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeMetricResource;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import jakarta.ws.rs.core.MultivaluedMap;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
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
	properties = "OSGI-INF/liferay/rest/v1_0/assignee-metric.properties",
	scope = ServiceScope.PROTOTYPE, service = AssigneeMetricResource.class
)
public class AssigneeMetricResourceImpl extends BaseAssigneeMetricResourceImpl {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<AssigneeMetric> postProcessAssigneeMetricsPage(
			Long processId, Pagination pagination, Sort[] sorts,
			AssigneeMetricBulkSelection assigneeMetricBulkSelection)
		throws Exception {

		Set<Long> userIds = Collections.emptySet();

		if (Validator.isNotNull(assigneeMetricBulkSelection.getKeywords()) ||
			ArrayUtil.isNotEmpty(assigneeMetricBulkSelection.getRoleIds())) {

			userIds = _getUserIds(
				assigneeMetricBulkSelection.getKeywords(),
				assigneeMetricBulkSelection.getRoleIds());

			if (userIds.isEmpty()) {
				return Page.of(Collections.emptyList());
			}
		}

		long count = _getAssigneeMetricsCount(
			GetterUtil.getBoolean(assigneeMetricBulkSelection.getCompleted()),
			assigneeMetricBulkSelection.getDateEnd(),
			assigneeMetricBulkSelection.getDateStart(),
			assigneeMetricBulkSelection.getInstanceIds(), processId,
			assigneeMetricBulkSelection.getTaskNames(), userIds);

		if (count > 0) {
			return Page.of(
				_getAssigneeMetrics(
					GetterUtil.getBoolean(
						assigneeMetricBulkSelection.getCompleted()),
					assigneeMetricBulkSelection.getDateEnd(),
					assigneeMetricBulkSelection.getDateStart(),
					assigneeMetricBulkSelection.getInstanceIds(),
					_toFieldSort(sorts), pagination, processId,
					assigneeMetricBulkSelection.getTaskNames(), userIds),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private TermsQuery _createAssigneeIdTermsQuery(
		boolean completed, Set<Long> userIds) {

		TermsQuery termsQuery = QueriesUtil.terms(
			completed ? "completionUserId" : "assigneeIds");

		termsQuery.addValues(
			transformToArray(userIds, String::valueOf, Object.class));

		return termsQuery;
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Long[] instanceIds,
		long processId, String[] taskNames, Set<Long> userIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.setMinimumShouldMatch(1);

		BooleanQuery slaTaskResultsBooleanQuery = QueriesUtil.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(
				completed, dateEnd, dateStart, instanceIds, processId,
				taskNames, userIds));

		BooleanQuery tasksBooleanQuery = QueriesUtil.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_TASK));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(
				completed, dateEnd, dateStart, instanceIds, processId,
				taskNames, userIds));

		return booleanQuery.addShouldQueryClauses(
			slaTaskResultsBooleanQuery, tasksBooleanQuery);
	}

	private BooleanQuery _createBooleanQuery(
		BooleanQuery booleanQuery, boolean completed, Long[] instanceIds,
		long processId, Set<Long> userIds) {

		if (ArrayUtil.isNotEmpty(instanceIds)) {
			TermsQuery termsQuery = QueriesUtil.terms("instanceId");

			termsQuery.addValues(
				transform(
					instanceIds,
					instanceId -> {
						if (instanceId <= 0) {
							return null;
						}

						return String.valueOf(instanceId);
					},
					Object.class));

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		if (!userIds.isEmpty()) {
			booleanQuery.addMustQueryClauses(
				_createAssigneeIdTermsQuery(completed, userIds));
		}

		return booleanQuery.addMustQueryClauses(
			QueriesUtil.term("active", Boolean.TRUE),
			QueriesUtil.term("assigneeType", User.class.getName()),
			QueriesUtil.term("companyId", contextCompany.getCompanyId()),
			QueriesUtil.term("deleted", Boolean.FALSE),
			QueriesUtil.term("instanceCompleted", completed),
			QueriesUtil.term("processId", processId));
	}

	private BucketSelectorPipelineAggregation
		_createBucketSelectorPipelineAggregation() {

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector",
				Scripts.INSTANCE.script("params.taskCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"taskCount", "countFilter>taskCount.value");

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

	private BooleanQuery _createCountFilterBooleanQuery() {
		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			QueriesUtil.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_TASK));

		return booleanQuery.addMustNotQueryClauses(
			QueriesUtil.term("taskId", 0));
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Long[] instanceIds,
		long processId, String[] taskNames, Set<Long> userIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustNotQueryClauses(QueriesUtil.term("instanceId", 0));

		if (completed) {
			if ((dateEnd != null) && (dateStart != null)) {
				booleanQuery.addMustQueryClauses(
					_createCompletionDateBooleanQuery(dateEnd, dateStart));
			}
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				QueriesUtil.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()));
		}

		if (ArrayUtil.isNotEmpty(taskNames)) {
			TermsQuery termsQuery = QueriesUtil.terms("taskName");

			termsQuery.addValues(taskNames);

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		return _createBooleanQuery(
			booleanQuery, completed, instanceIds, processId, userIds);
	}

	private BooleanQuery _createTasksBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, Long[] instanceIds,
		long processId, String[] taskNames, Set<Long> userIds) {

		BooleanQuery booleanQuery = QueriesUtil.booleanQuery();

		booleanQuery.addMustNotQueryClauses(QueriesUtil.term("taskId", "0"));

		if (completed && (dateEnd != null) && (dateStart != null)) {
			booleanQuery.addMustQueryClauses(
				QueriesUtil.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.getDate(dateStart),
					_resourceHelper.getDate(dateEnd)));
		}

		booleanQuery.addMustQueryClauses(
			QueriesUtil.term("completed", completed));

		if (ArrayUtil.isNotEmpty(taskNames)) {
			TermsQuery termsQuery = QueriesUtil.terms("name");

			termsQuery.addValues(taskNames);

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		return _createBooleanQuery(
			booleanQuery, completed, instanceIds, processId, userIds);
	}

	private List<AssigneeMetric> _getAssigneeMetrics(
			boolean completed, Date dateEnd, Date dateStart, Long[] instanceIds,
			FieldSort fieldSort, Pagination pagination, Long processId,
			String[] taskNames, Set<Long> userIds)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"assigneeId", completed ? "completionUserId" : "assigneeIds");

		FilterAggregation countFilterAggregation = _aggregations.filter(
			"countFilter", _createCountFilterBooleanQuery());

		countFilterAggregation.addChildrenAggregations(
			_aggregations.avg("durationTaskAvg", "duration"),
			_aggregations.valueCount("taskCount", "taskId"));

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.
				createOnTimeTaskByAssigneeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.
				createOverdueTaskByAssigneeScriptedMetricAggregation());

		termsAggregation.addChildrenAggregations(
			countFilterAggregation, onTimeFilterAggregation,
			overdueFilterAggregation);

		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation());

		if (fieldSort != null) {
			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createBooleanQuery(
				completed, dateEnd, dateStart, instanceIds, processId,
				taskNames, userIds));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("assigneeId");

		return transform(
			termsAggregationResult.getBuckets(), this::_toAssigneeMetric);
	}

	private long _getAssigneeMetricsCount(
			boolean completed, Date dateEnd, Date dateStart, Long[] instanceIds,
			long processId, String[] taskNames, Set<Long> userIds)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addAggregation(
			_aggregations.cardinality(
				"assigneeId", completed ? "completionUserId" : "assigneeIds"));
		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				completed, dateEnd, dateStart, instanceIds, processId,
				taskNames, userIds));

		SearchSearchResponse searchSearchResponse =
			_searchEngineAdapter.execute(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		CardinalityAggregationResult cardinalityAggregationResult =
			(CardinalityAggregationResult)aggregationResultsMap.get(
				"assigneeId");

		if (cardinalityAggregationResult == null) {
			return 0L;
		}

		return cardinalityAggregationResult.getValue();
	}

	private long _getDurationTaskAvg(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		if (filterAggregationResult == null) {
			return 0L;
		}

		AvgAggregationResult avgAggregationResult =
			(AvgAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"durationTaskAvg");

		double value = avgAggregationResult.getValue();

		if (Double.isInfinite(value)) {
			return 0L;
		}

		return GetterUtil.getLong(value);
	}

	private long _getTaskCount(Bucket bucket) {
		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		if (filterAggregationResult == null) {
			return 0L;
		}

		ValueCountAggregationResult valueCountAggregationResult =
			(ValueCountAggregationResult)
				filterAggregationResult.getChildAggregationResult("taskCount");

		return GetterUtil.getLong(valueCountAggregationResult.getValue());
	}

	private Set<Long> _getUserIds(String keywords, Long[] roleIds) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>(1);

		if (ArrayUtil.isNotEmpty(roleIds)) {
			params.put("usersRoles", roleIds);
		}

		return new HashSet<>(
			transform(
				_userLocalService.search(
					contextCompany.getCompanyId(), keywords, keywords, keywords,
					null, null, WorkflowConstants.STATUS_ANY, params, false,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					(OrderByComparator<User>)null),
				User::getUserId));
	}

	private boolean _isOrderByDurationTaskAvg(String fieldName) {
		if (StringUtil.equals(fieldName, "durationTaskAvg") ||
			StringUtil.equals(fieldName, "countFilter>durationTaskAvg")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOnTimeTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "onTimeTaskCount") ||
			StringUtil.equals(fieldName, "onTime>taskCount.value")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOverdueTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "overdueTaskCount") ||
			StringUtil.equals(fieldName, "overdue>taskCount.value")) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "taskCount") ||
			StringUtil.equals(fieldName, "countFilter>taskCount")) {

			return true;
		}

		return false;
	}

	private AssigneeMetric _toAssigneeMetric(Bucket bucket) {
		return new AssigneeMetric() {
			{
				setAssignee(
					() -> AssigneeUtil.toAssignee(
						_language, _portal,
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							contextAcceptLanguage.getPreferredLocale(),
							AssigneeMetricResourceImpl.class),
						GetterUtil.getLong(bucket.getKey()),
						_userLocalService::fetchUser));
				setDurationTaskAvg(() -> _getDurationTaskAvg(bucket));
				setOnTimeTaskCount(
					() -> _resourceHelper.getOnTimeTaskCount(bucket));
				setOverdueTaskCount(
					() -> _resourceHelper.getOverdueTaskCount(bucket));
				setTaskCount(() -> _getTaskCount(bucket));
			}
		};
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		Sort sort = new Sort("taskCount", true);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (_isOrderByDurationTaskAvg(fieldName)) {
			fieldName = "countFilter>durationTaskAvg";
		}
		else if (_isOrderByTaskCount(fieldName)) {
			fieldName = "countFilter>taskCount";
		}
		else if (_isOrderByOnTimeTaskCount(fieldName) ||
				 _isOrderByOverdueTaskCount(fieldName)) {

			fieldName =
				StringUtil.extractFirst(fieldName, "TaskCount") +
					">taskCount.value";
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final EntityModel _entityModel =
		new AssigneeMetricEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchEngineAdapter _searchEngineAdapter;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserLocalService _userLocalService;

}