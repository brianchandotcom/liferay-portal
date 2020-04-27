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
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.ResourceBundleUtil;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Task;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.TaskUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.NoSuchTaskException;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.TaskResource;
import com.liferay.portal.workflow.metrics.search.index.TaskWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

		_taskWorkflowMetricsIndexer.deleteTask(
			contextCompany.getCompanyId(), taskId);
	}

	@Override
	public Task getProcessTask(Long processId, Long taskId) throws Exception {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId, taskId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

		searchSearchRequest.setSize(1);

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getSearchHits
		).map(
			SearchHits::getSearchHits
		).flatMap(
			List::parallelStream
		).map(
			SearchHit::getDocument
		).findFirst(
		).map(
			document -> TaskUtil.toTask(
				document, _language, contextAcceptLanguage.getPreferredLocale(),
				_portal,
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class),
				_userLocalService::fetchUser)
		).orElseThrow(
			() -> new NoSuchTaskException(
				"No task exists with the task ID " + taskId)
		);
	}

	@Override
	public Page<Task> getProcessTasksPage(Long processId) throws Exception {
		return Page.of(_getTasks(processId));
	}

	@Override
	public void patchProcessTask(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		Assignee assignee = task.getAssignee();

		Long[] assigneeIds = null;
		String assigneeType = null;

		if ((assignee != null) && (assignee.getId() != null)) {
			assigneeIds = new Long[] {assignee.getId()};
			assigneeType = User.class.getName();
		}

		_taskWorkflowMetricsIndexer.updateTask(
			LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n()),
			LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n()),
			assigneeIds, assigneeType, contextCompany.getCompanyId(),
			task.getDateModified(), task.getId(), contextUser.getUserId());
	}

	@Override
	public void patchProcessTaskComplete(Long processId, Long taskId, Task task)
		throws Exception {

		getProcessTask(processId, taskId);

		_taskWorkflowMetricsIndexer.completeTask(
			contextCompany.getCompanyId(), task.getDateCompletion(),
			task.getCompletionUserId(), task.getDuration(),
			task.getDateModified(), taskId, contextUser.getUserId());
	}

	@Override
	public Task postProcessTask(Long processId, Task task) throws Exception {
		Assignee assignee = task.getAssignee();

		Long[] assigneeIds = null;
		String assigneeType = null;

		if ((assignee != null) && (assignee.getId() != null)) {
			assigneeIds = new Long[] {assignee.getId()};
			assigneeType = User.class.getName();
		}

		return TaskUtil.toTask(
			_taskWorkflowMetricsIndexer.addTask(
				LocalizedMapUtil.getLocalizedMap(task.getAssetTitle_i18n()),
				LocalizedMapUtil.getLocalizedMap(task.getAssetType_i18n()),
				assigneeIds, assigneeType, task.getClassName(),
				task.getClassPK(), contextCompany.getCompanyId(), false, null,
				null, task.getDateCreated(), false, null, task.getInstanceId(),
				task.getDateModified(), task.getName(), task.getNodeId(),
				processId, task.getProcessVersion(), task.getId(),
				contextUser.getUserId()),
			_language, contextAcceptLanguage.getPreferredLocale(), _portal,
			ResourceBundleUtil.getModuleAndPortalResourceBundle(
				contextAcceptLanguage.getPreferredLocale(),
				TaskResourceImpl.class),
			_userLocalService::fetchUser);
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

	private BooleanQuery _createFilterBooleanQuery(
		long processId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addShouldQueryClauses(_createBooleanQuery(processId));

		return booleanQuery.addShouldQueryClauses(
			_createBooleanQuery(processId, version));
	}

	private BooleanQuery _createTasksBooleanQuery(
		long processId, long taskId, String version) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
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

	private List<Task> _getTasks(long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms("name", "name");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createTasksBooleanQuery(
				processId,
				_resourceHelper.getLatestProcessVersion(
					contextCompany.getCompanyId(), processId)));

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
			bucket -> TaskUtil.toTask(
				_language, bucket.getKey(),
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					TaskResourceImpl.class))
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Language _language;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private TaskWorkflowMetricsIndexer _taskWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

	@Reference
	private UserLocalService _userLocalService;

}