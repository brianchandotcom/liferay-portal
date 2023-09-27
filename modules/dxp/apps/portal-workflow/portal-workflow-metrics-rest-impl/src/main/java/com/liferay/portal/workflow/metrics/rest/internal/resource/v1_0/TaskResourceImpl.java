/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.workflow.metrics.rest.internal.resource.v1_0;

import com.liferay.portal.kernel.language.Language;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.Order;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.ScriptedMetricAggregationResult;
import com.liferay.portal.search.aggregation.metrics.TopHitsAggregationResult;
import com.liferay.portal.search.aggregation.pipeline.BucketSelectorPipelineAggregation;
import com.liferay.portal.search.aggregation.pipeline.BucketSortPipelineAggregation;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.index.IndexNameBuilder;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.search.script.Scripts;
import com.liferay.portal.search.sort.FieldSort;
import com.liferay.portal.search.sort.SortOrder;
import com.liferay.portal.search.sort.Sorts;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.model.AddTaskRequest;
import com.liferay.portal.workflow.metrics.model.Assignment;
import com.liferay.portal.workflow.metrics.model.CompleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.DeleteTaskRequest;
import com.liferay.portal.workflow.metrics.model.UpdateTaskRequest;
import com.liferay.portal.workflow.metrics.model.UserAssignment;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.TaskBulkSelection;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.TaskUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.NoSuchTaskException;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.constants.WorkflowMetricsIndexNameConstants;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Rafael Praxedes
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/task.properties",
	scope = ServiceScope.PROTOTYPE, service = TaskResource.class
)
public class TaskResourceImpl extends BaseTaskResourceImpl {

	@Override
	public void deleteProcessTask(Long processId, Long taskId)
		throws Exception {

		DeleteTaskRequest.Builder deleteTaskRequestBuilder =
			new DeleteTaskRequest.Builder();

		_taskWorkflowMetricsIndexer.deleteTask(
			deleteTaskRequestBuilder.companyId(
				contextCompany.getCompanyId()
			).taskId(
				taskId
			).build());
	}

	@Override
	public Task getProcessTask(Long processId, Long taskId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId, taskId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

		searchSearchRequest.setSize(1);

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		SearchHits searchHits = searchSearchResponse.getSearchHits();

		List<SearchHit> searchHitsList = searchHits.getSearchHits();

		if (searchHitsList.isEmpty()) {
			throw new NoSuchTaskException(
				"No task exists with the task ID " + taskId);
		}

		SearchHit searchHit = searchHitsList.get(0);

		return TaskUtil.toTask(
			searchHit.getDocument(), _language,
			contextAcceptLanguage.getPreferredLocale(), _portal,
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				contextAcceptLanguage.getPreferredLocale(),
				TaskResourceImpl.class),
			_userLocalService::fetchUser);
	}

	@Override
	public Page<Task> getProcessTasksPage(Long processId) throws Exception {
		return Page.of(_getTasks(processId));
	}

	@Override
	public void patchProcessTask(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		List<Assignment> assignments = new ArrayList<>();

		Assignee assignee = task.getAssignee();

		if ((assignee != null) && (assignee.getId() != null)) {
			User user = _userLocalService.fetchUser(assignee.getId());

			assignments.add(
				new UserAssignment(assignee.getId(), user.getFullName()));
		}

		UpdateTaskRequest.Builder updateTaskRequestBuilder =
			new UpdateTaskRequest.Builder();

		_taskWorkflowMetricsIndexer.updateTask(
			updateTaskRequestBuilder.assetTitleMap(
				LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n())
			).assetTypeMap(
				LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n())
			).assignments(
				assignments
			).companyId(
				contextCompany.getCompanyId()
			).modifiedDate(
				task.getDateModified()
			).taskId(
				task.getId()
			).userId(
				contextUser.getUserId()
			).build());
	}

	@Override
	public void patchProcessTaskComplete(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		CompleteTaskRequest.Builder completeTaskRequestBuilder =
			new CompleteTaskRequest.Builder();

		_taskWorkflowMetricsIndexer.completeTask(
			completeTaskRequestBuilder.companyId(
				contextCompany.getCompanyId()
			).completionDate(
				task.getDateCompletion()
			).completionUserId(
				task.getCompletionUserId()
			).duration(
				task.getDuration()
			).modifiedDate(
				task.getDateModified()
			).taskId(
				taskId
			).userId(
				contextUser.getUserId()
			).build());
	}

	@Override
	public Task postProcessTask(Long processId, Task task) throws Exception {
		return TaskUtil.toTask(
			_taskWorkflowMetricsIndexer.addTask(
				_toAddTaskRequest(processId, task)),
			_language, contextAcceptLanguage.getPreferredLocale(), _portal,
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				contextAcceptLanguage.getPreferredLocale(),
				TaskResourceImpl.class),
			_userLocalService::fetchUser);
	}

	@Override
	public Page<Task> postTasksPage(
			Pagination pagination, TaskBulkSelection taskBulkSelection)
		throws Exception {

		if (ArrayUtil.isEmpty(taskBulkSelection.getAssigneeIds())) {
			taskBulkSelection.setAssigneeIds(
				new Long[] {-1L, contextUser.getUserId()});
		}

		SearchSearchResponse searchSearchResponse = _getSearchSearchResponse(
			taskBulkSelection.getAssigneeIds(),
			taskBulkSelection.getInstanceIds(),
			taskBulkSelection.getProcessId(),
			taskBulkSelection.getSlaStatuses(),
			taskBulkSelection.getTaskNames());

		int taskCount = _getTaskCount(searchSearchResponse);

		if (taskCount <= 0) {
			return Page.of(Collections.emptyList());
		}

		if (pagination == null) {
			Map<String, AggregationResult> aggregationResultsMap =
				searchSearchResponse.getAggregationResultsMap();

			TermsAggregationResult termsAggregationResult =
				(TermsAggregationResult)aggregationResultsMap.get("name");

			return Page.of(
				transform(
					termsAggregationResult.getBuckets(),
					bucket -> TaskUtil.toTask(
						_language, bucket.getKey(),
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							contextAcceptLanguage.getPreferredLocale(),
							TaskResourceImpl.class))));
		}

		return Page.of(
			_getTasks(
				taskBulkSelection.getAssigneeIds(),
				taskBulkSelection.getInstanceIds(), pagination,
				taskBulkSelection.getProcessId(),
				taskBulkSelection.getSlaStatuses(),
				searchSearchResponse.getCount(),
				taskBulkSelection.getTaskNames()),
			pagination, taskCount);
	}

	private BooleanQuery _createAssigneeIdsTermsBooleanQuery(
		Long[] assigneeIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (ArrayUtil.contains(assigneeIds, -1L)) {
			BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

			TermsQuery termsQuery = _queries.terms("assigneeIds");

			termsQuery.addValues(
				transform(
					ArrayUtil.toArray(contextUser.getRoleIds()),
					String::valueOf, Object.class));

			shouldBooleanQuery.addMustQueryClauses(
				termsQuery,
				_queries.term("assigneeType", Role.class.getName()));

			booleanQuery.addShouldQueryClauses(shouldBooleanQuery);
		}

		if (!ArrayUtil.contains(assigneeIds, -1L) || (assigneeIds.length > 1)) {
			BooleanQuery shouldBooleanQuery = _queries.booleanQuery();

			TermsQuery termsQuery = _queries.terms("assigneeIds");

			termsQuery.addValues(
				transform(
					assigneeIds,
					assigneeId -> {
						if (assigneeId <= 0) {
							return null;
						}

						return String.valueOf(assigneeId);
					},
					Object.class));

			shouldBooleanQuery.addMustQueryClauses(
				termsQuery,
				_queries.term("assigneeType", User.class.getName()));

			booleanQuery.addShouldQueryClauses(shouldBooleanQuery);
		}

		return booleanQuery;
	}

	private BooleanQuery _createBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("completed", Boolean.FALSE),
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

	private BooleanQuery _createBooleanQuery(
		Long[] assigneeIds, Long[] instanceIds, Long processId) {

		BooleanQuery filterBooleanQuery = _queries.booleanQuery();

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery slaTaskResultsBooleanQuery = _queries.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(instanceIds, processId));

		BooleanQuery tasksBooleanQuery = _queries.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_TASK));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(assigneeIds, instanceIds, processId));

		return filterBooleanQuery.addFilterQueryClauses(
			booleanQuery.addShouldQueryClauses(
				slaTaskResultsBooleanQuery, tasksBooleanQuery));
	}

	private BucketSelectorPipelineAggregation
		_createBucketSelectorPipelineAggregation() {

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector", _scripts.script("params.taskCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"taskCount", "taskCount.value");

		return bucketSelectorPipelineAggregation;
	}

	private BucketSortPipelineAggregation _createBucketSortPipelineAggregation(
		Pagination pagination) {

		BucketSortPipelineAggregation bucketSortPipelineAggregation =
			_aggregations.bucketSort("bucketSort");

		FieldSort keyFieldSort = _sorts.field("_key");

		keyFieldSort.setSortOrder(SortOrder.ASC);

		bucketSortPipelineAggregation.addSortFields(keyFieldSort);

		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize());

		return bucketSortPipelineAggregation;
	}

	private BooleanQuery _createFilterBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addShouldQueryClauses(_createBooleanQuery(processId));

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createInstanceIdsTermsBooleanQuery(
		Long[] instanceIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("instanceId");

		termsQuery.addValues(
			transform(instanceIds, String::valueOf, Object.class));

		return booleanQuery.addMustQueryClauses(termsQuery);
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		Long[] instanceIds, Long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			_queries.term("slaDefinitionId", 0),
			_queries.term("status", WorkflowMetricsSLAStatus.NEW.name()));

		if (instanceIds != null) {
			booleanQuery.addMustQueryClauses(
				_createInstanceIdsTermsBooleanQuery(instanceIds));
		}

		if (processId != null) {
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceCompleted", Boolean.FALSE));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long processId, long taskId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("active", Boolean.TRUE),
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId),
			_queries.term("taskId", taskId), _queries.term("version", version));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_createFilterBooleanQuery(processId, version));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE));
	}

	private BooleanQuery _createTasksBooleanQuery(
		Long[] assigneeIds, Long[] instanceIds, Long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		if (instanceIds != null) {
			booleanQuery.addMustQueryClauses(
				_createInstanceIdsTermsBooleanQuery(instanceIds));
		}

		if (processId != null) {
			booleanQuery.addMustQueryClauses(
				_queries.term("processId", processId));
		}

		return booleanQuery.addMustQueryClauses(
			_createAssigneeIdsTermsBooleanQuery(assigneeIds),
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceCompleted", Boolean.FALSE));
	}

	private SearchSearchResponse _getSearchSearchResponse(
		Long[] assigneeIds, Long[] instanceIds, Long processId,
		String[] slaStatuses, String[] taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.addChildrenAggregations(
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));

		termsAggregation.addOrders(Order.key(true));
		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation());

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.addAggregation(
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));
		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, instanceIds, processId));

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private int _getTaskCount(SearchSearchResponse searchSearchResponse) {
		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		ScriptedMetricAggregationResult scriptedMetricAggregationResult =
			(ScriptedMetricAggregationResult)aggregationResultsMap.get(
				"taskCount");

		return GetterUtil.getInteger(
			scriptedMetricAggregationResult.getValue());
	}

	private List<Task> _getTasks(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get("name");

		return transform(
			termsAggregationResult.getBuckets(),
			bucket -> TaskUtil.toTask(
				_language, bucket.getKey(),
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class)));
	}

	private List<Task> _getTasks(
		Long[] assigneeIds, Long[] instanceIds, Pagination pagination,
		Long processId, String[] slaStatuses, long taskCount,
		String[] taskNames) {

		List<Task> tasks = new LinkedList<>();

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceIdTaskId", null);

		termsAggregation.setScript(
			_scripts.script("doc['instanceId'].value + doc['taskId'].value"));

		FilterAggregation indexFilterAggregation = _aggregations.filter(
			"index",
			_queries.term(
				"_index",
				_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
					WorkflowMetricsIndexNameConstants.SUFFIX_TASK));

		indexFilterAggregation.addChildAggregation(
			_aggregations.topHits("topHits"));

		termsAggregation.addChildrenAggregations(
			indexFilterAggregation,
			_resourceHelper.creatTaskCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds),
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));

		termsAggregation.addOrders(Order.key(true));
		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation(),
			_createBucketSortPipelineAggregation(pagination));

		termsAggregation.setSize(GetterUtil.getInteger(taskCount));

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_SLA_TASK_RESULT,
			_indexNameBuilder.getIndexName(contextCompany.getCompanyId()) +
				WorkflowMetricsIndexNameConstants.SUFFIX_TASK);

		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, instanceIds, processId));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)aggregationResultsMap.get(
				"instanceIdTaskId");

		for (Bucket bucket : termsAggregationResult.getBuckets()) {
			FilterAggregationResult filterAggregationResult =
				(FilterAggregationResult)bucket.getChildAggregationResult(
					"index");

			TopHitsAggregationResult topHitsAggregationResult =
				(TopHitsAggregationResult)
					filterAggregationResult.getChildAggregationResult(
						"topHits");

			SearchHits searchHits = topHitsAggregationResult.getSearchHits();

			tasks.addAll(
				transform(
					searchHits.getSearchHits(),
					searchHit -> TaskUtil.toTask(
						_language, contextAcceptLanguage.getPreferredLocale(),
						_portal,
						ResourceBundleUtil.getModuleAndPortalResourceBundle(
							contextAcceptLanguage.getPreferredLocale(),
							TaskResourceImpl.class),
						searchHit.getSourcesMap(),
						_userLocalService::fetchUser)));
		}

		return tasks;
	}

	private AddTaskRequest _toAddTaskRequest(Long processId, Task task) {
		AddTaskRequest.Builder addTaskRequestBuilder =
			new AddTaskRequest.Builder();

		return addTaskRequestBuilder.assetTitleMap(
			LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n())
		).assetTypeMap(
			LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n())
		).assignments(
			() -> {
				List<Assignment> assignments = new ArrayList<>();

				Assignee assignee = task.getAssignee();

				if ((assignee != null) && (assignee.getId() != null)) {
					User user = _userLocalService.fetchUser(assignee.getId());

					assignments.add(
						new UserAssignment(
							assignee.getId(), user.getFullName()));
				}

				return assignments;
			}
		).className(
			task.getClassName()
		).classPK(
			task.getClassPK()
		).companyId(
			contextCompany.getCompanyId()
		).completed(
			false
		).createDate(
			task.getDateCreated()
		).instanceCompleted(
			false
		).instanceId(
			task.getInstanceId()
		).modifiedDate(
			task.getDateModified()
		).name(
			task.getName()
		).nodeId(
			task.getNodeId()
		).processId(
			processId
		).processVersion(
			task.getProcessVersion()
		).taskId(
			task.getId()
		).userId(
			contextUser.getUserId()
		).build();
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private IndexNameBuilder _indexNameBuilder;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

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

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

	@Reference
	private UserLocalService _userLocalService;

}