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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.odata.entity.EntityModel;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregation;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.aggregation.metrics.AvgAggregationResult;
import com.liferay.portal.search.aggregation.metrics.CardinalityAggregationResult;
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
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.AssigneeUser;
import com.liferay.portal.workflow.metrics.rest.internal.odata.entity.v1_0.AssigneeUserEntityModel;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.AssigneeUserResource;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ServiceScope;

/**
 * @author Inácio Nery
 */
@Component(
	properties = "OSGI-INF/liferay/rest/v1_0/assignee-user.properties",
	scope = ServiceScope.PROTOTYPE, service = AssigneeUserResource.class
)
public class AssigneeUserResourceImpl
	extends BaseAssigneeUserResourceImpl implements EntityModelResource {

	@Override
	public EntityModel getEntityModel(MultivaluedMap multivaluedMap)
		throws Exception {

		return _entityModel;
	}

	@Override
	public Page<AssigneeUser> getProcessAssigneeUsersPage(
			Long processId, Boolean completed, Date dateEnd, Date dateStart,
			String keywords, Long[] roleIds, String[] taskKeys,
			Pagination pagination, Sort[] sorts)
		throws Exception {

		Set<Long> userIds = Collections.emptySet();

		if (Validator.isNotNull(keywords) || ArrayUtil.isNotEmpty(roleIds)) {
			userIds = _getUserIds(keywords, roleIds);

			if (userIds.isEmpty()) {
				return Page.of(Collections.emptyList());
			}
		}

		if (pagination == null) {
			return Page.of(
				_getAssigneeUsers(
					GetterUtil.getBoolean(completed), dateEnd, dateStart,
					processId, taskKeys, userIds));
		}

		long count = _getAssigneeUsersCount(
			GetterUtil.getBoolean(completed), dateEnd, dateStart, processId,
			taskKeys, userIds);

		if (count > 0) {
			return Page.of(
				_getAssigneeUsers(
					GetterUtil.getBoolean(completed), dateEnd, dateStart,
					_toFieldSort(sorts), pagination, processId, taskKeys,
					userIds),
				pagination, count);
		}

		return Page.of(Collections.emptyList());
	}

	private TermsQuery _createAssigneeIdTermsQuery(Set<Long> userIds) {
		TermsQuery termsQuery = _queries.terms("assigneeId");

		Stream<Long> stream = userIds.stream();

		termsQuery.addValues(
			stream.map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return termsQuery;
	}

	private BooleanQuery _createBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		String[] taskKeys, Set<Long> userIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery slaTaskResultsBooleanQuery = _queries.booleanQuery();

		slaTaskResultsBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-sla-task-results"));
		slaTaskResultsBooleanQuery.addMustQueryClauses(
			_createSLATaskResultsBooleanQuery(
				completed, dateEnd, dateStart, processId, taskKeys, userIds));

		BooleanQuery tokensBooleanQuery = _queries.booleanQuery();

		tokensBooleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-tokens"));
		tokensBooleanQuery.addMustQueryClauses(
			_createTokensBooleanQuery(
				completed, dateEnd, dateStart, processId, taskKeys, userIds));

		return booleanQuery.addShouldQueryClauses(
			slaTaskResultsBooleanQuery, tokensBooleanQuery);
	}

	private BooleanQuery _createCountFilterBooleanQuery() {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addFilterQueryClauses(
			_queries.term("_index", "workflow-metrics-tokens"));

		return booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", 0));
	}

	private BooleanQuery _createSLATaskResultsBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		String[] taskKeys, Set<Long> userIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", 0));

		if (completed) {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(
					"status", WorkflowMetricsSLAStatus.RUNNING.name()));

			if ((dateEnd != null) && (dateStart != null)) {
				booleanQuery.addMustQueryClauses(
					_queries.rangeTerm(
						"completionDate", true, true,
						_resourceHelper.formatDate(dateStart),
						_resourceHelper.formatDate(dateEnd)));
			}
		}
		else {
			booleanQuery.addMustNotQueryClauses(
				_queries.term(
					"status", WorkflowMetricsSLAStatus.COMPLETED.name()),
				_queries.term(
					"status", WorkflowMetricsSLAStatus.EXPIRED.name()));
		}

		if (taskKeys.length > 0) {
			TermsQuery termsQuery = _queries.terms("taskName");

			termsQuery.addValues(taskKeys);

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		if (!userIds.isEmpty()) {
			booleanQuery.addMustQueryClauses(
				_createAssigneeIdTermsQuery(userIds));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createTokensBooleanQuery(
		boolean completed, Date dateEnd, Date dateStart, long processId,
		String[] taskKeys, Set<Long> userIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("tokenId", "0"));

		if (completed && (dateEnd != null) && (dateStart != null)) {
			booleanQuery.addMustQueryClauses(
				_queries.rangeTerm(
					"completionDate", true, true,
					_resourceHelper.formatDate(dateStart),
					_resourceHelper.formatDate(dateEnd)));
		}

		if (taskKeys.length > 0) {
			TermsQuery termsQuery = _queries.terms("taskName");

			termsQuery.addValues(taskKeys);

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		if (!userIds.isEmpty()) {
			booleanQuery.addMustQueryClauses(
				_createAssigneeIdTermsQuery(userIds));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", completed),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private List<AssigneeUser> _getAssigneeUsers(
			boolean completed, Date dateEnd, Date dateStart,
			FieldSort fieldSort, Pagination pagination, Long processId,
			String[] taskKeys, Set<Long> userIds)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"assigneeId", "assigneeId");

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

		if (fieldSort != null) {
			termsAggregation.addPipelineAggregation(
				_resourceHelper.createBucketSortPipelineAggregation(
					fieldSort, pagination));
		}

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			"workflow-metrics-sla-task-results", "workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createBooleanQuery(
				completed, dateEnd, dateStart, processId, taskKeys, userIds));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("assigneeId")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> {
				AssigneeUser assigneeUser = _toAssigneeUser(bucket);

				_populateAssigneeUserWithSLAMetrics(assigneeUser, bucket);
				_setDurationTaskAvg(assigneeUser, bucket);
				_setTaskCount(assigneeUser, bucket);

				return assigneeUser;
			}
		).limit(
			pagination.getPageSize()
		).collect(
			Collectors.toList()
		);
	}

	private List<AssigneeUser> _getAssigneeUsers(
			boolean completed, Date dateEnd, Date dateStart, long processId,
			String[] taskKeys, Set<Long> userIds)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"assigneeId", "assigneeId");

		termsAggregation.setSize(10000);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames("workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createTokensBooleanQuery(
				completed, dateEnd, dateStart, processId, taskKeys, userIds));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("assigneeId")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			this::_toAssigneeUser
		).filter(
			Objects::nonNull
		).sorted(
			Comparator.comparing(
				AssigneeUser::getName, String::compareToIgnoreCase)
		).collect(
			Collectors.toList()
		);
	}

	private long _getAssigneeUsersCount(
			boolean completed, Date dateEnd, Date dateStart, long processId,
			String[] taskKeys, Set<Long> userIds)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addAggregation(
			_aggregations.cardinality("assigneeId", "assigneeId"));
		searchSearchRequest.setIndexNames("workflow-metrics-tokens");
		searchSearchRequest.setQuery(
			_createTokensBooleanQuery(
				completed, dateEnd, dateStart, processId, taskKeys, userIds));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(CardinalityAggregationResult)aggregationResultsMap.get(
					"assigneeId")
		).map(
			CardinalityAggregationResult::getValue
		).findFirst(
		).orElseGet(
			() -> 0L
		);
	}

	private Set<Long> _getUserIds(String keywords, Long[] roleIds) {
		LinkedHashMap<String, Object> params = new LinkedHashMap<>(1);

		if (ArrayUtil.isNotEmpty(roleIds)) {
			params.put("usersRoles", roleIds);
		}

		return Stream.of(
			_userLocalService.search(
				contextCompany.getCompanyId(), keywords,
				WorkflowConstants.STATUS_APPROVED, params, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, (OrderByComparator<User>)null)
		).flatMap(
			List::parallelStream
		).map(
			User::getUserId
		).collect(
			Collectors.toSet()
		);
	}

	private boolean _isOrderByDurationTaskAvg(String fieldName) {
		if (StringUtil.equals(fieldName, "durationTaskAvg") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"countFilter", StringPool.GREATER_THAN,
					"durationTaskAvg"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOnTimeTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "onTimeTaskCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"onTime", StringPool.GREATER_THAN, "taskCount.value"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByOverdueTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "overdueTaskCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"overdue", StringPool.GREATER_THAN, "taskCount.value"))) {

			return true;
		}

		return false;
	}

	private boolean _isOrderByTaskCount(String fieldName) {
		if (StringUtil.equals(fieldName, "taskCount") ||
			StringUtil.equals(
				fieldName,
				StringBundler.concat(
					"countFilter", StringPool.GREATER_THAN, "taskCount"))) {

			return true;
		}

		return false;
	}

	private void _populateAssigneeUserWithSLAMetrics(
		AssigneeUser assigneeUser, Bucket bucket) {

		_setOnTimeTaskCount(assigneeUser, bucket);
		_setOverdueTaskCount(assigneeUser, bucket);
	}

	private void _setDurationTaskAvg(AssigneeUser assigneeUser, Bucket bucket) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		AvgAggregationResult avgAggregationResult =
			(AvgAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"durationTaskAvg");

		assigneeUser.setDurationTaskAvg(
			GetterUtil.getLong(avgAggregationResult.getValue()));
	}

	private void _setOnTimeTaskCount(AssigneeUser assigneeUser, Bucket bucket) {
		if (bucket == null) {
			return;
		}

		assigneeUser.setOnTimeTaskCount(
			_resourceHelper.getOnTimeTaskCount(bucket));
	}

	private void _setOverdueTaskCount(
		AssigneeUser assigneeUser, Bucket bucket) {

		if (bucket == null) {
			return;
		}

		assigneeUser.setOverdueTaskCount(
			_resourceHelper.getOverdueTaskCount(bucket));
	}

	private void _setTaskCount(AssigneeUser assigneeUser, Bucket bucket) {
		if (bucket == null) {
			return;
		}

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"countFilter");

		ValueCountAggregationResult valueCountAggregationResult =
			(ValueCountAggregationResult)
				filterAggregationResult.getChildAggregationResult("taskCount");

		assigneeUser.setTaskCount(
			GetterUtil.getLong(valueCountAggregationResult.getValue()));
	}

	private AssigneeUser _toAssigneeUser(Bucket bucket) {
		try {
			User user = _userLocalService.getUserById(
				GetterUtil.getLong(bucket.getKey()));

			return new AssigneeUser() {
				{
					durationTaskAvg = 0L;
					id = user.getUserId();
					name = user.getFullName();
					onTimeTaskCount = 0L;
					overdueTaskCount = 0L;
					taskCount = 0L;

					setImage(
						() -> {
							if (user.getPortraitId() == 0) {
								return null;
							}

							ThemeDisplay themeDisplay = new ThemeDisplay() {
								{
									setPathImage(_portal.getPathImage());
								}
							};

							return user.getPortraitURL(themeDisplay);
						});
				}
			};
		}
		catch (PortalException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe, pe);
			}

			return null;
		}
	}

	private FieldSort _toFieldSort(Sort[] sorts) {
		Sort sort = new Sort("taskCount", true);

		if (sorts != null) {
			sort = sorts[0];
		}

		String fieldName = sort.getFieldName();

		if (_isOrderByDurationTaskAvg(fieldName)) {
			fieldName = StringBundler.concat(
				"countFilter", StringPool.GREATER_THAN, "durationTaskAvg");
		}
		else if (_isOrderByTaskCount(fieldName)) {
			fieldName = StringBundler.concat(
				"countFilter", StringPool.GREATER_THAN, "taskCount");
		}
		else if (_isOrderByOnTimeTaskCount(fieldName) ||
				 _isOrderByOverdueTaskCount(fieldName)) {

			fieldName = StringBundler.concat(
				StringUtil.extractFirst(fieldName, "TaskCount"),
				StringPool.GREATER_THAN, "taskCount.value");
		}

		FieldSort fieldSort = _sorts.field(fieldName);

		fieldSort.setSortOrder(
			sort.isReverse() ? SortOrder.DESC : SortOrder.ASC);

		return fieldSort;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		AssigneeUserResourceImpl.class);

	private static final EntityModel _entityModel =
		new AssigneeUserEntityModel();

	@Reference
	private Aggregations _aggregations;

	@Reference
	private Portal _portal;

	@Reference
	private Queries _queries;

	@Reference
	private ResourceHelper _resourceHelper;

	@Reference
	private SearchRequestExecutor _searchRequestExecutor;

	@Reference
	private Sorts _sorts;

	@Reference
	private UserLocalService _userLocalService;

}