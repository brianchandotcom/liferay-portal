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

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.DateFormatFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
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
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.TimeRangeUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkfowMetricsSLAStatus;

import java.text.DateFormat;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.Context;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/instance.properties",
	scope = ServiceScope.PROTOTYPE, service = InstanceResource.class
)
public class InstanceResourceImpl extends BaseInstanceResourceImpl {

	@Override
	public Instance getProcessInstance(Long processId, Long instanceId)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		TermsAggregation slaDefinitionIdTermsAggregation = _aggregations.terms(
			"slaDefinitionId", "slaDefinitionId");

		slaDefinitionIdTermsAggregation.addChildAggregation(
			_aggregations.topHits("topHits"));
		slaDefinitionIdTermsAggregation.setSize(10000);

		TermsAggregation taskNameTermsAggregation = _aggregations.terms(
			"taskName", "taskName");

		taskNameTermsAggregation.setSize(10000);

		termsAggregation.addChildrenAggregations(
			onTimeFilterAggregation, overdueFilterAggregation,
			slaDefinitionIdTermsAggregation, taskNameTermsAggregation);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-instances",
			"workflow-metrics-sla-process-results", "workflow-metrics-tokens");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", contextCompany.getCompanyId()),
				_queries.term("deleted", Boolean.FALSE),
				_queries.term("instanceId", instanceId),
				_queries.term("processId", processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		return Stream.of(
			searchSearchResponse.getSearchHits()
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::stream
		).map(
			SearchHit::getDocument
		).filter(
			document -> document.getString(
				"uid"
			).startsWith(
				"WorkflowMetricsInstance"
			)
		).findFirst(
		).map(
			this::_createInstance
		).map(
			instance -> {
				Stream.of(
					searchSearchResponse.getAggregationResultsMap()
				).map(
					aggregationResultsMap ->
						(TermsAggregationResult)aggregationResultsMap.get(
							"instanceId")
				).map(
					TermsAggregationResult::getBuckets
				).flatMap(
					Collection::stream
				).findFirst(
				).ifPresent(
					bucket -> {
						_setSLAResults(bucket, instance);
						_setSLAStatus(bucket, instance);
						_setTaskNames(instance, _getTaskNames(bucket));
					}
				);

				return instance;
			}
		).orElseGet(
			Instance::new
		);
	}

	@Override
	public Page<Instance> getProcessInstancesPage(
			Long processId, String[] slaStatuses, String[] statuses,
			String[] taskKeys, Integer timeRange, Pagination pagination)
		throws Exception {

		SearchSearchResponse searchSearchResponse = _getSearchSearchResponse(
			processId, slaStatuses, statuses, taskKeys, timeRange);

		int instanceCount = _getInstanceCount(searchSearchResponse);

		if (instanceCount > 0) {
			return Page.of(
				_getInstances(
					searchSearchResponse.getCount(), pagination, processId,
					slaStatuses, statuses, taskKeys, timeRange),
				pagination, instanceCount);
		}

		return Page.of(Collections.emptyList());
	}

	private BooleanQuery _createBooleanQuery(
		long processId, String[] statuses, String[] taskKeys) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery instancesBooleanQuery = _queries.booleanQuery();

		instancesBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-instances"));
		instancesBooleanQuery.addMustQueryClauses(
			_createInstancesBooleanQuery(processId, statuses));

		BooleanQuery slaProcessResultsBooleanQuery = _queries.booleanQuery();

		slaProcessResultsBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-sla-process-results"));
		slaProcessResultsBooleanQuery.addMustQueryClauses(
			_createSLAProcessResultsBooleanQuery(processId));

		BooleanQuery tokensBooleanQuery = _queries.booleanQuery();

		tokensBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-tokens"));
		tokensBooleanQuery.addMustQueryClauses(
			_createTokensBooleanQuery(processId, taskKeys));

		return booleanQuery.addShouldQueryClauses(
			instancesBooleanQuery, slaProcessResultsBooleanQuery,
			tokensBooleanQuery);
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

	private BucketSortPipelineAggregation _createBucketSortPipelineAggregation(
		Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("bucketSort");

		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize());

		return bucketSortPipelineAggregation;
	}

	private TermsQuery _createCompletedTermsQuery(String[] statuses) {
		TermsQuery termsQuery = _queries.terms("completed");

		for (String status : statuses) {
			if (Objects.equals(Instance.Status.COMPLETED.getValue(), status)) {
				termsQuery.addValue(Boolean.TRUE.toString());
			}
			else if (Objects.equals(
						Instance.Status.PENDING.getValue(), status)) {

				termsQuery.addValue(Boolean.FALSE.toString());
			}
		}

		return termsQuery;
	}

	private Instance _createInstance(Document document) {
		return new Instance() {
			{
				assetTitle = document.getString(
					_getLocalizedName("assetTitle"));
				assetType = document.getString(_getLocalizedName("assetType"));
				dateCompletion = _toDate(document.getDate("completionDate"));
				dateCreated = _toDate(document.getDate("createDate"));
				id = document.getLong("instanceId");
				processId = document.getLong("processId");
				status = _getStatus(
					GetterUtil.getBoolean(document.getString("completed")));
				userName = document.getString("userName");
			}
		};
	}

	private Instance _createInstance(Map<String, Object> sourcesMap) {
		return new Instance() {
			{
				assetTitle = GetterUtil.getString(
					sourcesMap.get(_getLocalizedName("assetTitle")));
				assetType = GetterUtil.getString(
					sourcesMap.get(_getLocalizedName("assetType")));
				dateCompletion = _toDate(
					GetterUtil.getString(sourcesMap.get("completionDate")));
				dateCreated = _toDate(
					GetterUtil.getString(sourcesMap.get("createDate")));
				id = GetterUtil.getLong(sourcesMap.get("instanceId"));
				processId = GetterUtil.getLong(sourcesMap.get("processId"));
				status = _getStatus(
					GetterUtil.getBoolean(sourcesMap.get("completed")));
				userName = GetterUtil.getString(sourcesMap.get("userName"));
			}
		};
	}

	private BooleanQuery _createInstancesBooleanQuery(
		long processId, String[] statuses) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", 0));

		if (statuses.length > 0) {
			booleanQuery.addMustQueryClauses(
				_createCompletedTermsQuery(statuses));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createSLAProcessResultsBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			_queries.term("slaDefinitionId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private SLAResult _createSLAResult(Map<String, Object> sourcesMap) {
		return new SLAResult() {
			{
				dateOverdue = _toDate(
					GetterUtil.getString(sourcesMap.get("overdueDate")));

				id = GetterUtil.getLong(sourcesMap.get("slaDefinitionId"));

				name = _getSLAName(id);

				onTime = GetterUtil.getBoolean(sourcesMap.get("onTime"));
				remainingTime = GetterUtil.getLong(
					sourcesMap.get("remainingTime"));
				status = _getSLAResultStatus(
					GetterUtil.getString(sourcesMap.get("status")));
			}
		};
	}

	private TermsQuery _createTaskNameTermsQuery(String[] taskNames) {
		TermsQuery termsQuery = _queries.terms("taskName");

		termsQuery.addValues(taskNames);

		return termsQuery;
	}

	private BooleanQuery _createTokensBooleanQuery(
		long processId, String[] taskKeys) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", 0));

		if (taskKeys.length > 0) {
			booleanQuery.addMustQueryClauses(
				_createTaskNameTermsQuery(taskKeys));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private int _getInstanceCount(SearchSearchResponse searchSearchResponse) {
		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)aggregationResultsMap.get(
				"instanceCount");

		return GetterUtil.getInteger(
			scriptedMetricAggregationResult.getValue());
	}

	private List<Instance> _getInstances(
		long instanceCount, Pagination pagination, long processId,
		String[] slaStatuses, String[] statuses, String[] taskKeys,
		Integer timeRange) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		TermsAggregation taskNameTermsAggregation = _aggregations.terms(
			"taskName", "taskName");

		taskNameTermsAggregation.setSize(10000);

		termsAggregation.addChildrenAggregations(
			onTimeFilterAggregation, overdueFilterAggregation,
			taskNameTermsAggregation, _aggregations.topHits("topHits"),
			_resourceHelper.creatInstanceCountScriptedMetricAggregation(
				TimeRangeUtil.getEndDate(timeRange, _user.getTimeZoneId()),
				ListUtil.toList(slaStatuses),
				TimeRangeUtil.getStartDate(timeRange, _user.getTimeZoneId()),
				ListUtil.toList(statuses), ListUtil.toList(taskKeys)));

		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation(),
			_createBucketSortPipelineAggregation(pagination));

		termsAggregation.setSize(GetterUtil.getInteger(instanceCount));

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-instances",
			"workflow-metrics-sla-process-results", "workflow-metrics-tokens");

		searchSearchRequest.setQuery(
			_createBooleanQuery(processId, statuses, taskKeys));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("instanceId")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> Stream.of(
				(TopHitsAggregationResult)bucket.getChildAggregationResult(
					"topHits")
			).map(
				TopHitsAggregationResult::getSearchHits
			).map(
				SearchHits::getSearchHits
			).flatMap(
				List::stream
			).map(
				SearchHit::getSourcesMap
			).filter(
				sourcesMap -> GetterUtil.getString(
					sourcesMap.get("uid")
				).startsWith(
					"WorkflowMetricsInstance"
				)
			).findFirst(
			).map(
				this::_createInstance
			).map(
				instance -> {
					_setSLAStatus(bucket, instance);
					_setTaskNames(instance, _getTaskNames(bucket));

					return instance;
				}
			).orElseGet(
				Instance::new
			)
		).collect(
			Collectors.toCollection(LinkedList::new)
		);
	}

	private String _getLocalizedName(String name) {
		return Field.getLocalizedName(
			contextAcceptLanguage.getPreferredLocale(), name);
	}

	private SearchSearchResponse _getSearchSearchResponse(
		long processId, String[] slaStatuses, String[] statuses,
		String[] taskKeys, Integer timeRange) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addAggregation(
			_resourceHelper.creatInstanceCountScriptedMetricAggregation(
				TimeRangeUtil.getEndDate(timeRange, _user.getTimeZoneId()),
				ListUtil.toList(slaStatuses),
				TimeRangeUtil.getStartDate(timeRange, _user.getTimeZoneId()),
				ListUtil.toList(statuses), ListUtil.toList(taskKeys)));
		searchSearchRequest.setIndexNames(
			"workflow-metrics-instances",
			"workflow-metrics-sla-process-results", "workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createBooleanQuery(processId, statuses, taskKeys));

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private String _getSLAName(long slaDefinitionId) {
		try {
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinition(slaDefinitionId);

			return workflowMetricsSLADefinition.getName();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return null;
		}
	}

	private SLAResult.Status _getSLAResultStatus(String status) {
		if (Objects.equals(status, WorkfowMetricsSLAStatus.COMPLETED.name()) ||
			Objects.equals(status, WorkfowMetricsSLAStatus.STOPPED.name())) {

			return SLAResult.Status.STOPPED;
		}
		else if (Objects.equals(
					status, WorkfowMetricsSLAStatus.PAUSED.name())) {

			return SLAResult.Status.PAUSED;
		}

		return SLAResult.Status.RUNNING;
	}

	private Instance.Status _getStatus(Boolean completed) {
		if (completed) {
			return Instance.Status.COMPLETED;
		}

		return Instance.Status.PENDING;
	}

	private List<String> _getTaskNames(Bucket bucket) {
		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)bucket.getChildAggregationResult(
				"taskName");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			Bucket::getKey
		).map(
			taskName -> _language.get(
				_resourceHelper.getResourceBundle(
					contextAcceptLanguage.getPreferredLocale()),
				taskName)
		).collect(
			Collectors.toList()
		);
	}

	private boolean _isOnTime(Bucket bucket) {
		if (_resourceHelper.getOnTimeInstanceCount(bucket) > 0) {
			return true;
		}

		return false;
	}

	private boolean _isOverdue(Bucket bucket) {
		if (_resourceHelper.getOverdueInstanceCount(bucket) > 0) {
			return true;
		}

		return false;
	}

	private void _setSLAResults(Bucket bucket, Instance instance) {
		instance.setSlaResults(
			Stream.of(
				(TermsAggregationResult)bucket.getChildAggregationResult(
					"slaDefinitionId")
			).map(
				TermsAggregationResult::getBuckets
			).flatMap(
				Collection::stream
			).map(
				childBucket -> Stream.of(
					(TopHitsAggregationResult)
						childBucket.getChildAggregationResult("topHits")
				).map(
					TopHitsAggregationResult::getSearchHits
				).map(
					SearchHits::getSearchHits
				).flatMap(
					List::stream
				).findFirst(
				).map(
					SearchHit::getSourcesMap
				).map(
					this::_createSLAResult
				).orElseGet(
					SLAResult::new
				)
			).toArray(
				SLAResult[]::new
			));
	}

	private void _setSLAStatus(Bucket bucket, Instance instance) {
		if (_isOverdue(bucket)) {
			instance.setSLAStatus(Instance.SLAStatus.OVERDUE);
		}
		else if (_isOnTime(bucket)) {
			instance.setSLAStatus(Instance.SLAStatus.ON_TIME);
		}
		else {
			instance.setSLAStatus(Instance.SLAStatus.UNTRACKED);
		}
	}

	private void _setTaskNames(Instance instance, List<String> taskNames) {
		if (taskNames == null) {
			return;
		}

		instance.setTaskNames(taskNames.toArray(new String[0]));
	}

	private Date _toDate(String dateString) {
		DateFormat dateFormat = DateFormatFactoryUtil.getSimpleDateFormat(
			_INDEX_DATE_FORMAT_PATTERN);

		try {
			return dateFormat.parse(dateString);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e, e);
			}

			return null;
		}
	}

	private static final String _INDEX_DATE_FORMAT_PATTERN = PropsUtil.get(
		PropsKeys.INDEX_DATE_FORMAT_PATTERN);

	private static final Log _log = LogFactoryUtil.getLog(
		InstanceResourceImpl.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Language _language;

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

	@Context
	private User _user;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}