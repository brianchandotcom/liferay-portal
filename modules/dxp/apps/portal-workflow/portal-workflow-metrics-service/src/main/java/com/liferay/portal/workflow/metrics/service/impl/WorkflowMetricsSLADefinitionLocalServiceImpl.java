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

package com.liferay.portal.workflow.metrics.service.impl;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.aggregation.AggregationResult;
import com.liferay.portal.search.aggregation.Aggregations;
import com.liferay.portal.search.aggregation.bucket.Bucket;
import com.liferay.portal.search.aggregation.bucket.FilterAggregation;
import com.liferay.portal.search.aggregation.bucket.FilterAggregationResult;
import com.liferay.portal.search.aggregation.bucket.TermsAggregationResult;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.TermsQuery;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStartNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStopNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionTimeframeException;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAProcessResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinitionVersion;
import com.liferay.portal.workflow.metrics.service.base.WorkflowMetricsSLADefinitionLocalServiceBaseImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionLocalServiceImpl
	extends WorkflowMetricsSLADefinitionLocalServiceBaseImpl {

	@Override
	public WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition(
			String calendarKey, String description, long duration, String name,
			String[] pauseNodeKeys, long processId, String[] startNodeKeys,
			String[] stopNodeKeys, ServiceContext serviceContext)
		throws PortalException {

		String latestProcessVersion = _getLatestProcessVersion(
			serviceContext.getCompanyId(), processId);

		validate(
			0, serviceContext.getCompanyId(), processId, latestProcessVersion,
			name, duration, pauseNodeKeys, startNodeKeys, stopNodeKeys);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.create(
				counterLocalService.increment());

		workflowMetricsSLADefinition.setGroupId(
			serviceContext.getScopeGroupId());
		workflowMetricsSLADefinition.setCompanyId(
			serviceContext.getCompanyId());

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());

		workflowMetricsSLADefinition.setUserId(user.getUserId());
		workflowMetricsSLADefinition.setUserName(user.getFullName());

		Date now = new Date();

		workflowMetricsSLADefinition.setCreateDate(now);
		workflowMetricsSLADefinition.setModifiedDate(now);

		workflowMetricsSLADefinition.setActive(true);
		workflowMetricsSLADefinition.setCalendarKey(calendarKey);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setPauseNodeKeys(
			StringUtil.merge(pauseNodeKeys));
		workflowMetricsSLADefinition.setProcessId(processId);
		workflowMetricsSLADefinition.setProcessVersion(latestProcessVersion);
		workflowMetricsSLADefinition.setStartNodeKeys(
			StringUtil.merge(startNodeKeys));
		workflowMetricsSLADefinition.setStopNodeKeys(
			StringUtil.merge(stopNodeKeys));
		workflowMetricsSLADefinition.setVersion(_VERSION_DEFAULT);
		workflowMetricsSLADefinition.setStatus(
			WorkflowConstants.STATUS_APPROVED);

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

		addWorkflowMetricsSLADefinitionVersion(
			user, workflowMetricsSLADefinition);

		return workflowMetricsSLADefinition;
	}

	@Override
	public void deactivateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, ServiceContext serviceContext)
		throws PortalException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.findByPrimaryKey(
				workflowMetricsSLADefinitionId);

		workflowMetricsSLADefinition.setActive(false);

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());

		addWorkflowMetricsSLADefinitionVersion(
			user, workflowMetricsSLADefinition);

		_workflowMetricsPortalExecutor.execute(
			() -> _slaProcessResultWorkflowMetricsIndexer.deleteDocuments(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId(),
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId()));

		_workflowMetricsPortalExecutor.execute(
			() -> _slaTaskResultWorkflowMetricsIndexer.deleteDocuments(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId(),
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId()));
	}

	@Override
	public WorkflowMetricsSLADefinition getWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, boolean active)
		throws PortalException {

		return workflowMetricsSLADefinitionPersistence.findByWMSLAD_A(
			workflowMetricsSLADefinitionId, active);
	}

	@Override
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, boolean active, long processId, int status, int start,
		int end, OrderByComparator<WorkflowMetricsSLADefinition> obc) {

		return workflowMetricsSLADefinitionPersistence.findByC_A_P_S(
			companyId, active, processId, status, start, end, obc);
	}

	@Override
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, boolean active, long processId, String processVersion,
		int status) {

		return workflowMetricsSLADefinitionPersistence.findByC_A_P_NotPV_S(
			companyId, active, processId, processVersion, status);
	}

	@Override
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, int status) {

		return workflowMetricsSLADefinitionPersistence.findByC_S(
			companyId, status);
	}

	@Override
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId) {

		return workflowMetricsSLADefinitionPersistence.countByC_A_P(
			companyId, active, processId);
	}

	@Override
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, boolean active, long processId, int status) {

		return workflowMetricsSLADefinitionPersistence.countByC_A_P_S(
			companyId, active, processId, status);
	}

	@Override
	public WorkflowMetricsSLADefinition updateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, String calendarKey,
			String description, long duration, String name,
			String[] pauseNodeKeys, String[] startNodeKeys,
			String[] stopNodeKeys, int status, ServiceContext serviceContext)
		throws PortalException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.findByPrimaryKey(
				workflowMetricsSLADefinitionId);

		String latestProcessVersion = _getLatestProcessVersion(
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId());

		validate(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId(),
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId(), latestProcessVersion,
			name, duration, pauseNodeKeys, startNodeKeys, stopNodeKeys);

		Date now = new Date();

		workflowMetricsSLADefinition.setModifiedDate(
			serviceContext.getModifiedDate(now));

		workflowMetricsSLADefinition.setCalendarKey(calendarKey);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setPauseNodeKeys(
			StringUtil.merge(pauseNodeKeys));
		workflowMetricsSLADefinition.setProcessVersion(latestProcessVersion);
		workflowMetricsSLADefinition.setStartNodeKeys(
			StringUtil.merge(startNodeKeys));
		workflowMetricsSLADefinition.setStopNodeKeys(
			StringUtil.merge(stopNodeKeys));
		workflowMetricsSLADefinition.setVersion(
			getNextVersion(workflowMetricsSLADefinition.getVersion()));
		workflowMetricsSLADefinition.setStatus(status);

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());

		workflowMetricsSLADefinition.setStatusByUserId(user.getUserId());
		workflowMetricsSLADefinition.setStatusByUserName(user.getFullName());

		workflowMetricsSLADefinition.setStatusDate(
			serviceContext.getModifiedDate(now));

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

		addWorkflowMetricsSLADefinitionVersion(
			user, workflowMetricsSLADefinition);

		_workflowMetricsPortalExecutor.execute(
			() -> _slaProcessResultWorkflowMetricsIndexer.deleteDocuments(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId(),
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId()));

		_workflowMetricsPortalExecutor.execute(
			() -> _slaTaskResultWorkflowMetricsIndexer.deleteDocuments(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId(),
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId()));

		return workflowMetricsSLADefinition;
	}

	protected WorkflowMetricsSLADefinitionVersion
		addWorkflowMetricsSLADefinitionVersion(
			User user,
			WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

		long workflowMetricsSLADefinitionVersionId =
			counterLocalService.increment();

		WorkflowMetricsSLADefinitionVersion
			workflowMetricsSLADefinitionVersion =
				workflowMetricsSLADefinitionVersionPersistence.create(
					workflowMetricsSLADefinitionVersionId);

		workflowMetricsSLADefinitionVersion.setGroupId(
			workflowMetricsSLADefinition.getGroupId());
		workflowMetricsSLADefinitionVersion.setCompanyId(
			workflowMetricsSLADefinition.getCompanyId());

		workflowMetricsSLADefinitionVersion.setUserId(user.getUserId());
		workflowMetricsSLADefinitionVersion.setUserName(user.getFullName());

		Date now = new Date();

		workflowMetricsSLADefinitionVersion.setCreateDate(now);
		workflowMetricsSLADefinitionVersion.setModifiedDate(now);

		workflowMetricsSLADefinitionVersion.setActive(
			workflowMetricsSLADefinition.getActive());
		workflowMetricsSLADefinitionVersion.setCalendarKey(
			workflowMetricsSLADefinition.getCalendarKey());
		workflowMetricsSLADefinitionVersion.setDescription(
			workflowMetricsSLADefinition.getDescription());
		workflowMetricsSLADefinitionVersion.setDuration(
			workflowMetricsSLADefinition.getDuration());
		workflowMetricsSLADefinitionVersion.setName(
			workflowMetricsSLADefinition.getName());
		workflowMetricsSLADefinitionVersion.setPauseNodeKeys(
			workflowMetricsSLADefinition.getPauseNodeKeys());
		workflowMetricsSLADefinitionVersion.setProcessId(
			workflowMetricsSLADefinition.getProcessId());
		workflowMetricsSLADefinitionVersion.setProcessVersion(
			workflowMetricsSLADefinition.getProcessVersion());
		workflowMetricsSLADefinitionVersion.setStartNodeKeys(
			workflowMetricsSLADefinition.getStartNodeKeys());
		workflowMetricsSLADefinitionVersion.setStopNodeKeys(
			workflowMetricsSLADefinition.getStopNodeKeys());
		workflowMetricsSLADefinitionVersion.setVersion(
			workflowMetricsSLADefinition.getVersion());
		workflowMetricsSLADefinitionVersion.setWorkflowMetricsSLADefinitionId(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId());
		workflowMetricsSLADefinitionVersion.setStatus(
			workflowMetricsSLADefinition.getStatus());
		workflowMetricsSLADefinition.setStatusByUserId(user.getUserId());
		workflowMetricsSLADefinition.setStatusByUserName(user.getFullName());
		workflowMetricsSLADefinition.setStatusDate(now);

		workflowMetricsSLADefinitionVersionPersistence.update(
			workflowMetricsSLADefinitionVersion);

		return workflowMetricsSLADefinitionVersion;
	}

	protected String getNextVersion(String version) {
		int[] versionParts = StringUtil.split(version, StringPool.PERIOD, 0);

		return StringBundler.concat(
			++versionParts[0], StringPool.PERIOD, versionParts[1]);
	}

	protected void validate(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String processVersion, String name, long duration,
			String[] pauseNodeKeys, String[] startNodeKeys,
			String[] stopNodeKeys)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new WorkflowMetricsSLADefinitionNameException();
		}

		if (duration <= 0) {
			throw new WorkflowMetricsSLADefinitionDurationException();
		}

		List<WorkflowMetricsSLADefinition> workflowMetricsSLADefinitions =
			workflowMetricsSLADefinitionPersistence.findByC_A_N_P(
				companyId, true, name, processId);

		if (ListUtil.isNotEmpty(workflowMetricsSLADefinitions) &&
			((workflowMetricsSLADefinitionId == 0) ||
			 ListUtil.isNotEmpty(
				 ListUtil.filter(
					 workflowMetricsSLADefinitions,
					 workflowMetricsSLADefinition -> {
						long filteredWorkflowMetricsSLADefinitionId =
							workflowMetricsSLADefinition.
								getWorkflowMetricsSLADefinitionId();

						if (filteredWorkflowMetricsSLADefinitionId ==
								workflowMetricsSLADefinitionId) {

							return false;
						}

						return true;
					 })))) {

			throw new WorkflowMetricsSLADefinitionDuplicateNameException();
		}

		validateTimeframe(
			companyId, pauseNodeKeys, processId, processVersion, startNodeKeys,
			stopNodeKeys);
	}

	protected void validateTimeframe(
			long companyId, String[] pauseNodeKeys, long processId,
			String processVersion, String[] startNodeKeys,
			String[] stopNodeKeys)
		throws PortalException {

		if (ArrayUtil.isEmpty(startNodeKeys)) {
			throw new WorkflowMetricsSLADefinitionStartNodeKeysException();
		}

		if (ArrayUtil.isEmpty(stopNodeKeys)) {
			throw new WorkflowMetricsSLADefinitionStopNodeKeysException();
		}

		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		Set<String> pauseNodeIds = _getNodeIds(pauseNodeKeys);

		searchSearchRequest.addAggregation(
			_createNodeIdAggregation("pause", pauseNodeIds));

		Set<String> startNodeIds = _getNodeIds(startNodeKeys);

		searchSearchRequest.addAggregation(
			_createNodeIdAggregation("start", startNodeIds));

		Set<String> stopNodeIds = _getNodeIds(stopNodeKeys);

		searchSearchRequest.addAggregation(
			_createNodeIdAggregation("stop", stopNodeIds));

		searchSearchRequest.setIndexNames("workflow-metrics-nodes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		searchSearchRequest.setQuery(
			booleanQuery.addMustQueryClauses(
				_queries.term("companyId", companyId),
				_queries.term("processId", processId),
				_queries.term("version", processVersion)));

		SearchSearchResponse searchSearchResponse =
			_searchRequestExecutor.executeSearchRequest(searchSearchRequest);

		Map<String, AggregationResult> aggregationResultsMap =
			searchSearchResponse.getAggregationResultsMap();

		List<String> fieldNames = new ArrayList<>();

		long pauseNodeIdsCount = _getNodeIdsCount(
			(FilterAggregationResult)aggregationResultsMap.get("pause"));

		if (pauseNodeIdsCount != pauseNodeIds.size()) {
			fieldNames.add("pauseNodeKeys");
		}

		long startNodeIdsCount = _getNodeIdsCount(
			(FilterAggregationResult)aggregationResultsMap.get("start"));

		if (startNodeIdsCount != startNodeIds.size()) {
			fieldNames.add("startNodeKeys");
		}

		long stopNodeIdsCount = _getNodeIdsCount(
			(FilterAggregationResult)aggregationResultsMap.get("stop"));

		if (stopNodeIdsCount != stopNodeIds.size()) {
			fieldNames.add("stopNodeKeys");
		}

		if (!fieldNames.isEmpty()) {
			throw new WorkflowMetricsSLADefinitionTimeframeException(
				fieldNames);
		}
	}

	private FilterAggregation _createNodeIdAggregation(
		String aggregationName, Set<String> nodeIds) {

		TermsQuery termsQuery = _queries.terms("nodeId");

		termsQuery.addValues(nodeIds.toArray());

		FilterAggregation filterAggregation = _aggregations.filter(
			aggregationName, termsQuery);

		filterAggregation.addChildAggregation(
			_aggregations.terms("nodeId", "nodeId"));

		return filterAggregation;
	}

	private String _getLatestProcessVersion(long companyId, long processId) {
		SearchSearchRequest searchSearchRequest = new SearchSearchRequest();

		searchSearchRequest.setIndexNames("workflow-metrics-processes");

		BooleanQuery booleanQuery = _queries.booleanQuery();

		booleanQuery.addMustQueryClauses(
			_queries.term("companyId", companyId),
			_queries.term("processId", processId));

		searchSearchRequest.setQuery(booleanQuery);

		searchSearchRequest.setSelectedFieldNames("version");

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
			document -> document.getString("version")
		).orElseGet(
			() -> StringPool.BLANK
		);
	}

	private Set<String> _getNodeIds(String[] nodeKeys) {
		if (nodeKeys == null) {
			return Collections.emptySet();
		}

		return Stream.of(
			nodeKeys
		).map(
			nodeKey -> StringUtil.split(nodeKey, CharPool.COLON)
		).map(
			nodeKeyParts -> nodeKeyParts[0]
		).collect(
			Collectors.toSet()
		);
	}

	private long _getNodeIdsCount(
		FilterAggregationResult filterAggregationResult) {

		TermsAggregationResult termsAggregationResult =
			(TermsAggregationResult)
				filterAggregationResult.getChildAggregationResult("nodeId");

		Collection<Bucket> buckets = termsAggregationResult.getBuckets();

		return buckets.size();
	}

	private static final String _VERSION_DEFAULT = "1.0";

	@ServiceReference(type = Aggregations.class)
	private Aggregations _aggregations;

	@ServiceReference(type = Queries.class)
	private Queries _queries;

	@ServiceReference(type = SearchRequestExecutor.class)
	private SearchRequestExecutor _searchRequestExecutor;

	@ServiceReference(type = SLAProcessResultWorkflowMetricsIndexer.class)
	private SLAProcessResultWorkflowMetricsIndexer
		_slaProcessResultWorkflowMetricsIndexer;

	@ServiceReference(type = SLATaskResultWorkflowMetricsIndexer.class)
	private SLATaskResultWorkflowMetricsIndexer
		_slaTaskResultWorkflowMetricsIndexer;

	@ServiceReference(type = WorkflowMetricsPortalExecutor.class)
	private WorkflowMetricsPortalExecutor _workflowMetricsPortalExecutor;

}