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
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
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
import com.liferay.portal.vulcan.util.LocalizedMapUtil;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Assignee;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Creator;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Instance;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.SLAResult;
import com.liferay.portal.workflow.metrics.rest.dto.v1_0.Transition;
import com.liferay.portal.workflow.metrics.rest.internal.dto.v1_0.util.AssigneeUtil;
import com.liferay.portal.workflow.metrics.rest.internal.resource.exception.NoSuchInstanceException;
import com.liferay.portal.workflow.metrics.rest.internal.resource.helper.ResourceHelper;
import com.liferay.portal.workflow.metrics.rest.resource.v1_0.InstanceResource;
import com.liferay.portal.workflow.metrics.search.index.InstanceWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.search.index.name.WorkflowMetricsIndexNameBuilder;
import com.liferay.portal.workflow.metrics.service.WorkflowMetricsSLADefinitionLocalService;
import com.liferay.portal.workflow.metrics.sla.processor.WorkflowMetricsSLAStatus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	public void deleteProcessInstance(Long processId, Long instanceId)
		throws Exception {

		_instanceWorkflowMetricsIndexer.deleteInstance(
			contextCompany.getCompanyId(), instanceId);
	}

	@Override
	public Instance getProcessInstance(Long processId, Long instanceId)
		throws Exception {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation indexFilterAggregation = _aggregations.filter(
			"tasksIndex",
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));

		TermsAggregation assigneeTypeTermsAggregation = _aggregations.terms(
			"assigneeType", "assigneeType");

		TermsAggregation assigneeIdTermsAggregation = _aggregations.terms(
			"assigneeId", "assigneeIds");

		assigneeIdTermsAggregation.setSize(10000);

		assigneeTypeTermsAggregation.addChildAggregation(
			assigneeIdTermsAggregation);

		indexFilterAggregation.addChildAggregation(
			assigneeTypeTermsAggregation);

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
			"name", "name");

		taskNameTermsAggregation.setSize(10000);

		termsAggregation.addChildrenAggregations(
			indexFilterAggregation, onTimeFilterAggregation,
			overdueFilterAggregation, slaDefinitionIdTermsAggregation,
			taskNameTermsAggregation);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		BooleanQuery booleanQuery = _createBooleanQuery(
			new Long[0], new Long[0], null, processId);

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("instanceId", instanceId)));

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
						_setAssignees(bucket, instance);
						_setSLAResults(bucket, instance);
						_setSLAStatus(bucket, instance);
						_setTaskNames(bucket, instance);
						_setTransitions(instance);
					}
				);

				return instance;
			}
		).orElseThrow(
			() -> new NoSuchInstanceException(
				"No instance exists with the instance ID " + instanceId)
		);
	}

	@Override
	public Page<Instance> getProcessInstancesPage(
			Long processId, Long[] assigneeIds, Long[] classPKs,
			Boolean completed, Date dateEnd, Date dateStart,
			String[] slaStatuses, String[] taskNames, Pagination pagination)
		throws Exception {

		SearchSearchResponse searchSearchResponse = _getSearchSearchResponse(
			assigneeIds, classPKs, completed, dateEnd, dateStart, processId,
			slaStatuses, taskNames);

		int instanceCount = _getInstanceCount(searchSearchResponse);

		if (instanceCount > 0) {
			return Page.of(
				_getInstances(
					assigneeIds, classPKs, completed, dateEnd, dateStart,
					searchSearchResponse.getCount(), pagination, processId,
					slaStatuses, taskNames),
				pagination, instanceCount);
		}

		return Page.of(Collections.emptyList());
	}

	@Override
	public void patchProcessInstance(
			Long processId, Long instanceId, Instance instance)
		throws Exception {

		getProcessInstance(processId, instanceId);

		_instanceWorkflowMetricsIndexer.updateInstance(
			LocalizedMapUtil.getLocalizedMap(instance.getAssetTitle_i18n()),
			LocalizedMapUtil.getLocalizedMap(instance.getAssetType_i18n()),
			contextCompany.getCompanyId(), instanceId,
			instance.getDateModified());
	}

	@Override
	public void patchProcessInstanceComplete(
			Long processId, Long instanceId, Instance instance)
		throws Exception {

		getProcessInstance(processId, instanceId);

		_instanceWorkflowMetricsIndexer.completeInstance(
			contextCompany.getCompanyId(), instance.getDateCompletion(),
			instance.getDuration(), instanceId, instance.getDateModified());
	}

	@Override
	public Instance postProcessInstance(Long processId, Instance instance)
		throws Exception {

		Creator creator = instance.getCreator();

		return _createInstance(
			_instanceWorkflowMetricsIndexer.addInstance(
				LocalizedMapUtil.getLocalizedMap(instance.getAssetTitle_i18n()),
				LocalizedMapUtil.getLocalizedMap(instance.getAssetType_i18n()),
				instance.getClassName(), instance.getClassPK(),
				contextCompany.getCompanyId(), null, instance.getDateCreated(),
				instance.getId(), instance.getDateModified(), processId,
				instance.getProcessVersion(), creator.getId(),
				creator.getName()));
	}

	private Assignee _createAssignee(boolean reviewer) {
		Assignee assignee = new Assignee();

		assignee.setId(-1L);
		assignee.setName(
			_language.get(
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					InstanceResourceImpl.class),
				"unassigned"));
		assignee.setReviewer(reviewer);

		return assignee;
	}

	private BooleanQuery _createAssigneeIdsTermsBooleanQuery(
		Long[] assigneeIds) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("assigneeIds");

		termsQuery.addValues(
			Stream.of(
				assigneeIds
			).filter(
				assigneeId -> assigneeId > 0
			).map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		return booleanQuery.addMustQueryClauses(termsQuery);
	}

	private BooleanQuery _createAssigneeTypeBooleanQuery(Long[] assigneeIds) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		if (ArrayUtil.contains(assigneeIds, -1L)) {
			booleanQuery.addMustQueryClauses(
				_queries.term("assigneeType", Role.class.getName()));
		}

		return booleanQuery;
	}

	private BooleanQuery _createBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createBooleanQuery(long processId, long instanceId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		BooleanQuery tasksBooleanQuery = _queries.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(processId, instanceId));

		BooleanQuery transitionsBooleanQuery = _queries.booleanQuery();

		transitionsBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_transitionWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		transitionsBooleanQuery.addMustQueryClauses(
			_createBooleanQuery(processId));

		return booleanQuery.addShouldQueryClauses(
			tasksBooleanQuery, transitionsBooleanQuery);
	}

	private BooleanQuery _createBooleanQuery(
		Long[] assigneeIds, Long[] classPKs, Boolean completed,
		long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.setMinimumShouldMatch(1);

		BooleanQuery instancesBooleanQuery = _queries.booleanQuery();

		instancesBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		instancesBooleanQuery.addMustQueryClauses(
			_createInstancesBooleanQuery(classPKs, completed, processId));

		BooleanQuery slaInstanceResultsBooleanQuery = _queries.booleanQuery();

		slaInstanceResultsBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		slaInstanceResultsBooleanQuery.addMustQueryClauses(
			_createSLAInstanceResultsBooleanQuery(processId));

		BooleanQuery tasksBooleanQuery = _queries.booleanQuery();

		tasksBooleanQuery.addFilterQueryClauses(
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));
		tasksBooleanQuery.addMustQueryClauses(
			_createTasksBooleanQuery(assigneeIds, processId));

		return booleanQuery.addShouldQueryClauses(
			instancesBooleanQuery, slaInstanceResultsBooleanQuery,
			tasksBooleanQuery);
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

		FieldSort keyFieldSort = _sorts.field("_key");

		keyFieldSort.setSortOrder(SortOrder.ASC);

		bucketSortPipelineAggregation.addSortFields(keyFieldSort);

		bucketSortPipelineAggregation.setFrom(pagination.getStartPosition());
		bucketSortPipelineAggregation.setSize(pagination.getPageSize());

		return bucketSortPipelineAggregation;
	}

	private Instance _createInstance(Document document) {
		return new Instance() {
			{
				assetTitle = document.getString(
					_getLocalizedName("assetTitle"));
				assetType = document.getString(_getLocalizedName("assetType"));
				classPK = document.getLong("classPK");
				completed = document.getBoolean("completed");
				creator = _toCreator(document.getLong("userId"));
				dateCompletion = _parseDate(document.getDate("completionDate"));
				dateCreated = _parseDate(document.getDate("createDate"));
				dateModified = _parseDate(document.getDate("modifiedDate"));
				id = document.getLong("instanceId");
				processId = document.getLong("processId");
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
				classPK = GetterUtil.getLong(sourcesMap.get("classPK"));
				completed = GetterUtil.getBoolean(sourcesMap.get("completed"));
				creator = _toCreator(
					GetterUtil.getLong(sourcesMap.get("userId")));
				dateCompletion = _parseDate(
					GetterUtil.getString(sourcesMap.get("completionDate")));
				dateCreated = _parseDate(
					GetterUtil.getString(sourcesMap.get("createDate")));
				dateModified = _parseDate(
					GetterUtil.getString(sourcesMap.get("modifiedDate")));
				id = GetterUtil.getLong(sourcesMap.get("instanceId"));
				processId = GetterUtil.getLong(sourcesMap.get("processId"));
			}
		};
	}

	private BooleanQuery _createInstancesBooleanQuery(
		Long[] classPKs, Boolean completed, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("instanceId", 0));

		if (ArrayUtil.isNotEmpty(classPKs)) {
			TermsQuery termsQuery = _queries.terms("classPK");

			termsQuery.addValues(
				Stream.of(
					classPKs
				).map(
					String::valueOf
				).toArray(
					Object[]::new
				));

			booleanQuery.addMustQueryClauses(termsQuery);
		}

		if (completed != null) {
			booleanQuery.addMustQueryClauses(
				_queries.term("completed", completed));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createSLAInstanceResultsBooleanQuery(long processId) {
		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(
			_queries.term("slaDefinitionId", 0),
			_queries.term("status", WorkflowMetricsSLAStatus.NEW.name()));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private SLAResult _createSLAResult(Map<String, Object> sourcesMap) {
		return new SLAResult() {
			{
				dateOverdue = _parseDate(
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

	private BooleanQuery _createTasksBooleanQuery(
		long processId, long instanceId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("instanceId", instanceId),
			_queries.term("processId", processId));
	}

	private BooleanQuery _createTasksBooleanQuery(
		Long[] assigneeIds, long processId) {

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustNotQueryClauses(_queries.term("taskId", 0));

		if (assigneeIds.length > 0) {
			booleanQuery.addShouldQueryClauses(
				_createAssigneeIdsTermsBooleanQuery(assigneeIds),
				_createAssigneeTypeBooleanQuery(assigneeIds));
		}

		return booleanQuery.addMustQueryClauses(
			_queries.term("companyId", contextCompany.getCompanyId()),
			_queries.term("completed", Boolean.FALSE),
			_queries.term("deleted", Boolean.FALSE),
			_queries.term("processId", processId));
	}

	private List<Assignee> _getAssignees(Bucket bucket) {
		List<Assignee> assignees = new ArrayList<>();

		FilterAggregationResult filterAggregationResult =
			(FilterAggregationResult)bucket.getChildAggregationResult(
				"tasksIndex");

		TermsAggregationResult assigneeTypeAggregationResult =
			(TermsAggregationResult)
				filterAggregationResult.getChildAggregationResult(
					"assigneeType");

		Bucket userBucket = assigneeTypeAggregationResult.getBucket(
			User.class.getName());

		if (userBucket != null) {
			TermsAggregationResult termsAggregationResult =
				(TermsAggregationResult)userBucket.getChildAggregationResult(
					"assigneeId");

			Collection<Bucket> buckets = termsAggregationResult.getBuckets();

			Stream<Bucket> stream = buckets.stream();

			stream.map(
				Bucket::getKey
			).map(
				GetterUtil::getLong
			).map(
				userId -> AssigneeUtil.toAssignee(
					_language, _portal,
					ResourceBundleUtil.getModuleAndPortalResourceBundle(
						contextAcceptLanguage.getPreferredLocale(),
						InstanceResourceImpl.class),
					userId, _userLocalService::fetchUser)
			).sorted(
				Comparator.comparing(
					Assignee::getName,
					Comparator.nullsLast(String::compareToIgnoreCase))
			).filter(
				Objects::nonNull
			).forEachOrdered(
				assignees::add
			);
		}

		Bucket roleBucket = assigneeTypeAggregationResult.getBucket(
			Role.class.getName());

		if (roleBucket != null) {
			boolean reviewer = false;

			TermsAggregationResult termsAggregationResult =
				(TermsAggregationResult)roleBucket.getChildAggregationResult(
					"assigneeId");

			for (Bucket assigneeIdBucket :
					termsAggregationResult.getBuckets()) {

				long roleId = GetterUtil.getLong(assigneeIdBucket.getKey());

				if (ArrayUtil.contains(contextUser.getRoleIds(), roleId)) {
					reviewer = true;

					break;
				}
			}

			assignees.add(_createAssignee(reviewer));
		}

		return assignees;
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

	private Collection<Instance> _getInstances(
		Long[] assigneeIds, Long[] classPKs, Boolean completed, Date dateEnd,
		Date dateStart, long instanceCount, Pagination pagination,
		long processId, String[] slaStatuses, String[] taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation instancesIndexFilterAggregation =
			_aggregations.filter(
				"instanceIndex",
				_queries.term(
					"_index",
					_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
						contextCompany.getCompanyId())));

		instancesIndexFilterAggregation.addChildAggregation(
			_aggregations.topHits("topHits"));

		FilterAggregation onTimeFilterAggregation = _aggregations.filter(
			"onTime", _resourceHelper.createMustNotBooleanQuery());

		onTimeFilterAggregation.addChildAggregation(
			_resourceHelper.createOnTimeScriptedMetricAggregation());

		FilterAggregation overdueFilterAggregation = _aggregations.filter(
			"overdue", _resourceHelper.createMustNotBooleanQuery());

		overdueFilterAggregation.addChildAggregation(
			_resourceHelper.createOverdueScriptedMetricAggregation());

		TermsAggregation taskNameTermsAggregation = _aggregations.terms(
			"name", "name");

		taskNameTermsAggregation.setSize(10000);

		termsAggregation.addChildrenAggregations(
			instancesIndexFilterAggregation, onTimeFilterAggregation,
			overdueFilterAggregation, taskNameTermsAggregation,
			_aggregations.topHits("topHits"),
			_resourceHelper.creatInstanceCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds), completed, dateEnd, dateStart,
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));

		termsAggregation.addOrders(Order.key(true));
		termsAggregation.addPipelineAggregations(
			_createBucketSelectorPipelineAggregation(),
			_createBucketSortPipelineAggregation(pagination));

		termsAggregation.setSize(GetterUtil.getInteger(instanceCount));

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, classPKs, completed, processId));

		Map<Long, Instance> instances = Stream.of(
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
				(FilterAggregationResult)bucket.getChildAggregationResult(
					"instanceIndex")
			).map(
				filterAggregationResult ->
					(TopHitsAggregationResult)
						filterAggregationResult.getChildAggregationResult(
							"topHits")
			).map(
				TopHitsAggregationResult::getSearchHits
			).map(
				SearchHits::getSearchHits
			).flatMap(
				List::stream
			).map(
				SearchHit::getSourcesMap
			).findFirst(
			).map(
				this::_createInstance
			).map(
				instance -> {
					_setSLAStatus(bucket, instance);
					_setTaskNames(bucket, instance);

					return instance;
				}
			).orElseGet(
				Instance::new
			)
		).collect(
			LinkedHashMap::new,
			(map, instance) -> map.put(instance.getId(), instance), Map::putAll
		);

		_populateAssignees(assigneeIds, instances, processId);

		return instances.values();
	}

	private String _getLocalizedName(String name) {
		return Field.getLocalizedName(
			contextAcceptLanguage.getPreferredLocale(), name);
	}

	private List<String> _getNextTransitionNames(
		long processId, long instanceId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"nodeId", "nodeId");

		termsAggregation.setSize(100000);

		FilterAggregation countFilterAggregation = _aggregations.filter(
			"countFilter",
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));

		countFilterAggregation.addChildrenAggregations(
			_aggregations.valueCount("nodeCount", "nodeId"));

		FilterAggregation nameFilterAggregation = _aggregations.filter(
			"nameFilter",
			_queries.term(
				"_index",
				_transitionWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));

		TermsAggregation nameTermsAggregation = _aggregations.terms(
			"name", "name");

		nameTermsAggregation.setSize(100000);

		nameFilterAggregation.addChildAggregation(nameTermsAggregation);

		termsAggregation.addChildrenAggregations(
			countFilterAggregation, nameFilterAggregation);

		BucketSelectorPipelineAggregation bucketSelectorPipelineAggregation =
			_aggregations.bucketSelector(
				"bucketSelector", _scripts.script("params.nodeCount > 0"));

		bucketSelectorPipelineAggregation.addBucketPath(
			"nodeCount", "countFilter>nodeCount.value");

		termsAggregation.addPipelineAggregations(
			bucketSelectorPipelineAggregation);

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_transitionWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addFilterQueryClauses(
				_createBooleanQuery(processId, instanceId)));

		return Stream.of(
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest)
		).map(
			SearchSearchResponse::getAggregationResultsMap
		).map(
			aggregationResultsMap ->
				(TermsAggregationResult)aggregationResultsMap.get("nodeId")
		).map(
			TermsAggregationResult::getBuckets
		).flatMap(
			Collection::stream
		).map(
			bucket -> Stream.of(
				(FilterAggregationResult)bucket.getChildAggregationResult(
					"nameFilter")
			).map(
				filterAggregationResult ->
					(TermsAggregationResult)
						filterAggregationResult.getChildAggregationResult(
							"name")
			).map(
				TermsAggregationResult::getBuckets
			).flatMap(
				Collection::stream
			).map(
				Bucket::getKey
			).collect(
				Collectors.toCollection(ArrayList::new)
			)
		).flatMap(
			Collection::stream
		).sorted(
		).collect(
			Collectors.toCollection(ArrayList::new)
		);
	}

	private SearchSearchResponse _getSearchSearchResponse(
		Long[] assigneeIds, Long[] classPKs, Boolean completed, Date dateEnd,
		Date dateStart, long processId, String[] slaStatuses,
		String[] taskNames) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.addAggregation(
			_resourceHelper.creatInstanceCountScriptedMetricAggregation(
				ListUtil.fromArray(assigneeIds), completed, dateEnd, dateStart,
				ListUtil.fromArray(slaStatuses),
				ListUtil.fromArray(taskNames)));
		searchSearchRequest.setIndexNames(
			_instanceWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_slaInstanceResultWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()),
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));
		searchSearchRequest.setQuery(
			_createBooleanQuery(assigneeIds, classPKs, completed, processId));

		return _searchRequestExecutor.executeSearchRequest(searchSearchRequest);
	}

	private String _getSLAName(long slaDefinitionId) {
		try {
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
				_workflowMetricsSLADefinitionLocalService.
					getWorkflowMetricsSLADefinition(slaDefinitionId);

			return workflowMetricsSLADefinition.getName();
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	private SLAResult.Status _getSLAResultStatus(String status) {
		if (Objects.equals(status, WorkflowMetricsSLAStatus.COMPLETED.name()) ||
			Objects.equals(status, WorkflowMetricsSLAStatus.STOPPED.name())) {

			return SLAResult.Status.STOPPED;
		}
		else if (Objects.equals(
					status, WorkflowMetricsSLAStatus.PAUSED.name())) {

			return SLAResult.Status.PAUSED;
		}

		return SLAResult.Status.RUNNING;
	}

	private List<String> _getTaskNames(Bucket bucket) {
		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)bucket.getChildAggregationResult("name");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		Stream<Bucket> stream = buckets.stream();

		return stream.map(
			Bucket::getKey
		).map(
			taskName -> _language.get(
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					InstanceResourceImpl.class),
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

	private Date _parseDate(String dateString) {
		try {
			return DateUtil.parseDate(
				"yyyyMMddHHmmss", dateString, LocaleUtil.getDefault());
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn(exception, exception);
			}

			return null;
		}
	}

	private void _populateAssignees(
		Long[] assigneeIds, Map<Long, Instance> instances, Long processId) {

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		TermsAggregation termsAggregation = _aggregations.terms(
			"instanceId", "instanceId");

		FilterAggregation tasksIndexFilterAggregation = _aggregations.filter(
			"tasksIndex",
			_queries.term(
				"_index",
				_taskWorkflowMetricsIndexNameBuilder.getIndexName(
					contextCompany.getCompanyId())));

		TermsAggregation assigneeTypeTermsAggregation = _aggregations.terms(
			"assigneeType", "assigneeType");

		TermsAggregation assigneeIdTermsAggregation = _aggregations.terms(
			"assigneeId", "assigneeIds");

		assigneeIdTermsAggregation.setSize(10000);

		assigneeTypeTermsAggregation.addChildAggregation(
			assigneeIdTermsAggregation);

		tasksIndexFilterAggregation.addChildAggregation(
			assigneeTypeTermsAggregation);

		termsAggregation.addChildrenAggregations(tasksIndexFilterAggregation);

		termsAggregation.setSize(instances.size());

		searchSearchRequest.addAggregation(termsAggregation);

		searchSearchRequest.setIndexNames(
			_taskWorkflowMetricsIndexNameBuilder.getIndexName(
				contextCompany.getCompanyId()));

		BooleanQuery booleanQuery = _queries.booleanQuery();

		TermsQuery termsQuery = _queries.terms("instanceId");

		termsQuery.addValues(
			Stream.of(
				instances.keySet()
			).flatMap(
				Collection::stream
			).map(
				String::valueOf
			).toArray(
				Object[]::new
			));

		booleanQuery.addMustQueryClauses(
			termsQuery, _createTasksBooleanQuery(assigneeIds, processId));

		searchSearchRequest.setQuery(booleanQuery);

		Stream.of(
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
		).forEach(
			bucket -> {
				Instance instance = instances.get(
					GetterUtil.getLong(bucket.getKey()));

				_setAssignees(bucket, instance);
				_setTransitions(instance);
			}
		);
	}

	private void _setAssignees(Bucket bucket, Instance instance) {
		List<Assignee> assignees = _getAssignees(bucket);

		if (ListUtil.isNull(assignees)) {
			return;
		}

		instance.setAssignees(assignees.toArray(new Assignee[0]));
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

	private void _setTaskNames(Bucket bucket, Instance instance) {
		List<String> taskNames = _getTaskNames(bucket);

		if (ListUtil.isNull(taskNames)) {
			return;
		}

		instance.setTaskNames(taskNames.toArray(new String[0]));
	}

	private void _setTransitions(Instance instance) {
		if (ArrayUtil.isEmpty(instance.getAssignees()) ||
			(ArrayUtil.getLength(instance.getTaskNames()) != 1)) {

			return;
		}

		Assignee[] assignees = instance.getAssignees();

		if (!Objects.equals(assignees[0].getId(), contextUser.getUserId())) {
			return;
		}

		instance.setTransitions(_toTransitions(instance));
	}

	private Creator _toCreator(Long userId) {
		if (Objects.isNull(userId)) {
			return null;
		}

		User user = _userLocalService.fetchUser(userId);

		return new Creator() {
			{
				id = userId;

				setName(
					() -> {
						if (user == null) {
							return String.valueOf(userId);
						}

						return user.getFullName();
					});
			}
		};
	}

	private Transition _toTransition(String name) {
		Transition transition = new Transition();

		transition.setLabel(
			_language.get(
				ResourceBundleUtil.getModuleAndPortalResourceBundle(
					contextAcceptLanguage.getPreferredLocale(),
					InstanceResourceImpl.class),
				name));
		transition.setName(name);

		return transition;
	}

	private Transition[] _toTransitions(Instance instance) {
		return transformToArray(
			_getNextTransitionNames(instance.getProcessId(), instance.getId()),
			this::_toTransition, Transition.class);
	}

	private static final Log _log = LogFactoryUtil.getLog(
		InstanceResourceImpl.class);

	@Reference
	private Aggregations _aggregations;

	@Reference
	private InstanceWorkflowMetricsIndexer _instanceWorkflowMetricsIndexer;

	@Reference(target = "(workflow.metrics.index.entity.name=instance)")
	private WorkflowMetricsIndexNameBuilder
		_instanceWorkflowMetricsIndexNameBuilder;

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

	@Reference(
		target = "(workflow.metrics.index.entity.name=sla-instance-result)"
	)
	private WorkflowMetricsIndexNameBuilder
		_slaInstanceResultWorkflowMetricsIndexNameBuilder;

	@Reference
	private Sorts _sorts;

	@Reference(target = "(workflow.metrics.index.entity.name=task)")
	private WorkflowMetricsIndexNameBuilder
		_taskWorkflowMetricsIndexNameBuilder;

	@Reference(target = "(workflow.metrics.index.entity.name=transition)")
	private WorkflowMetricsIndexNameBuilder
		_transitionWorkflowMetricsIndexNameBuilder;

	@Reference
	private UserLocalService _userLocalService;

	@Reference
	private WorkflowMetricsSLADefinitionLocalService
		_workflowMetricsSLADefinitionLocalService;

}