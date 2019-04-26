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

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.engine.adapter.search.SearchRequestExecutor;
import com.liferay.portal.search.engine.adapter.search.SearchSearchRequest;
import com.liferay.portal.search.engine.adapter.search.SearchSearchResponse;
import com.liferay.portal.search.hits.SearchHit;
import com.liferay.portal.search.hits.SearchHits;
import com.liferay.portal.search.query.BooleanQuery;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDuplicateNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionDurationException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionNameException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStartNodeKeysException;
import com.liferay.portal.workflow.metrics.exception.WorkflowMetricsSLADefinitionStopNodeKeysException;
import com.liferay.portal.workflow.metrics.internal.petra.executor.WorkflowMetricsPortalExecutor;
import com.liferay.portal.workflow.metrics.internal.search.index.SLAProcessResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.internal.search.index.SLATaskResultWorkflowMetricsIndexer;
import com.liferay.portal.workflow.metrics.model.WorkflowMetricsSLADefinition;
import com.liferay.portal.workflow.metrics.service.base.WorkflowMetricsSLADefinitionLocalServiceBaseImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
public class WorkflowMetricsSLADefinitionLocalServiceImpl
	extends WorkflowMetricsSLADefinitionLocalServiceBaseImpl {

	@Override
	public WorkflowMetricsSLADefinition addWorkflowMetricsSLADefinition(
			String name, String description, long duration, long processId,
			String[] pauseNodeKeys, String[] startNodeKeys,
			String[] stopNodeKeys, ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		validate(
			0, serviceContext.getCompanyId(), processId, name, duration,
			pauseNodeKeys, startNodeKeys, stopNodeKeys);

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.create(
				counterLocalService.increment());

		workflowMetricsSLADefinition.setGroupId(
			serviceContext.getScopeGroupId());
		workflowMetricsSLADefinition.setCompanyId(
			serviceContext.getCompanyId());
		workflowMetricsSLADefinition.setUserId(user.getUserId());
		workflowMetricsSLADefinition.setUserName(user.getFullName());
		workflowMetricsSLADefinition.setCreateDate(now);
		workflowMetricsSLADefinition.setModifiedDate(now);
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setProcessId(processId);
		workflowMetricsSLADefinition.setProcessVersion(
			_getLatestProcessVersion(serviceContext.getCompanyId(), processId));
		workflowMetricsSLADefinition.setPauseNodeKeys(
			StringUtil.merge(pauseNodeKeys));
		workflowMetricsSLADefinition.setStartNodeKeys(
			StringUtil.merge(startNodeKeys));
		workflowMetricsSLADefinition.setStopNodeKeys(
			StringUtil.merge(stopNodeKeys));
		workflowMetricsSLADefinition.setStatus(
			WorkflowConstants.STATUS_APPROVED);

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

		return workflowMetricsSLADefinition;
	}

	public int countWorkflowMetricsSLADefinitions(
		long companyId, long processId, int status) {

		return workflowMetricsSLADefinitionPersistence.countByC_P_S(
			companyId, processId, status);
	}

	@Override
	public WorkflowMetricsSLADefinition deleteWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId)
		throws PortalException {

		return deleteWorkflowMetricsSLADefinition(
			workflowMetricsSLADefinitionPersistence.findByPrimaryKey(
				workflowMetricsSLADefinitionId));
	}

	@Override
	public WorkflowMetricsSLADefinition deleteWorkflowMetricsSLADefinition(
		WorkflowMetricsSLADefinition workflowMetricsSLADefinition) {

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

		return workflowMetricsSLADefinitionPersistence.remove(
			workflowMetricsSLADefinition);
	}

	@Override
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId) {

		return workflowMetricsSLADefinitionPersistence.findByC_P(
			companyId, processId);
	}

	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId, int status, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> obc) {

		return workflowMetricsSLADefinitionPersistence.findByC_P_S(
			companyId, processId, status, start, end, obc);
	}

	@Override
	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId, int start, int end,
		OrderByComparator<WorkflowMetricsSLADefinition> orderByComparator) {

		return workflowMetricsSLADefinitionPersistence.findByC_P(
			companyId, processId, start, end, orderByComparator);
	}

	public List<WorkflowMetricsSLADefinition> getWorkflowMetricsSLADefinitions(
		long companyId, long processId, String processVersion, int status) {

		return workflowMetricsSLADefinitionPersistence.findByC_P_NotPV_S(
			companyId, processId, processVersion, status);
	}

	@Override
	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, long processId) {

		return workflowMetricsSLADefinitionPersistence.countByC_P(
			companyId, processId);
	}

	public int getWorkflowMetricsSLADefinitionsCount(
		long companyId, long processId, int status) {

		return workflowMetricsSLADefinitionPersistence.countByC_P_S(
			companyId, processId, status);
	}

	@Override
	public WorkflowMetricsSLADefinition updateWorkflowMetricsSLADefinition(
			long workflowMetricsSLADefinitionId, String name,
			String description, long duration, String[] pauseNodeKeys,
			String[] startNodeKeys, String[] stopNodeKeys,
			ServiceContext serviceContext)
		throws PortalException {

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.findByPrimaryKey(
				workflowMetricsSLADefinitionId);

		validate(
			workflowMetricsSLADefinition.getWorkflowMetricsSLADefinitionId(),
			workflowMetricsSLADefinition.getCompanyId(),
			workflowMetricsSLADefinition.getProcessId(), name, duration,
			pauseNodeKeys, startNodeKeys, stopNodeKeys);

		workflowMetricsSLADefinition.setModifiedDate(new Date());
		workflowMetricsSLADefinition.setName(name);
		workflowMetricsSLADefinition.setDescription(description);
		workflowMetricsSLADefinition.setDuration(duration);
		workflowMetricsSLADefinition.setProcessVersion(
			_getLatestProcessVersion(
				workflowMetricsSLADefinition.getCompanyId(),
				workflowMetricsSLADefinition.getProcessId()));
		workflowMetricsSLADefinition.setPauseNodeKeys(
			StringUtil.merge(pauseNodeKeys));
		workflowMetricsSLADefinition.setStartNodeKeys(
			StringUtil.merge(startNodeKeys));
		workflowMetricsSLADefinition.setStopNodeKeys(
			StringUtil.merge(stopNodeKeys));

		workflowMetricsSLADefinitionPersistence.update(
			workflowMetricsSLADefinition);

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

	protected void validate(
			long workflowMetricsSLADefinitionId, long companyId, long processId,
			String name, long duration, String[] pauseNodeKeys,
			String[] startNodeKeys, String[] stopNodeKeys)
		throws PortalException {

		if (Validator.isNull(name)) {
			throw new WorkflowMetricsSLADefinitionNameException();
		}

		if (duration <= 0) {
			throw new WorkflowMetricsSLADefinitionDurationException();
		}

		if (ArrayUtil.isEmpty(startNodeKeys)) {
			throw new WorkflowMetricsSLADefinitionStartNodeKeysException();
		}

		if (ArrayUtil.isEmpty(stopNodeKeys)) {
			throw new WorkflowMetricsSLADefinitionStopNodeKeysException();
		}

		WorkflowMetricsSLADefinition workflowMetricsSLADefinition =
			workflowMetricsSLADefinitionPersistence.fetchByC_N_P(
				companyId, name, processId);

		if ((workflowMetricsSLADefinition != null) &&
			(workflowMetricsSLADefinitionId !=
				workflowMetricsSLADefinition.
					getWorkflowMetricsSLADefinitionId())) {

			throw new WorkflowMetricsSLADefinitionDuplicateNameException();
		}
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
		).orElse(
			StringPool.BLANK
		);
	}

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