/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.workflow.kaleo.service.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Junction;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.BaseMapBuilder;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.workflow.kaleo.definition.LogType;
import com.liferay.portal.workflow.kaleo.definition.util.KaleoLogUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchLogException;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoLog;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignmentInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.base.KaleoLogLocalServiceBaseImpl;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoLog",
	service = AopService.class
)
public class KaleoLogLocalServiceImpl extends KaleoLogLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addActionExecutionKaleoLog(
			KaleoInstanceToken kaleoInstanceToken, KaleoAction kaleoAction,
			long startTime, long endTime, String comment,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.ACTION_EXECUTION, serviceContext);

		kaleoLog.setKaleoClassName(kaleoAction.getKaleoClassName());
		kaleoLog.setKaleoClassPK(kaleoAction.getKaleoClassPK());
		kaleoLog.setKaleoDefinitionId(kaleoAction.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			kaleoAction.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoNodeName(kaleoAction.getKaleoNodeName());
		kaleoLog.setComment(comment);
		kaleoLog.setStartDate(new Date(startTime));
		kaleoLog.setEndDate(new Date(endTime));
		kaleoLog.setDuration(endTime - startTime);

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addNodeEntryKaleoLog(
			KaleoInstanceToken kaleoInstanceToken, KaleoNode sourceKaleoNode,
			KaleoNode targetKaleoNode, ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.NODE_ENTRY, serviceContext);

		kaleoLog.setKaleoClassName(KaleoNode.class.getName());
		kaleoLog.setKaleoClassPK(targetKaleoNode.getKaleoNodeId());
		kaleoLog.setKaleoDefinitionId(targetKaleoNode.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			targetKaleoNode.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoNodeName(targetKaleoNode.getName());
		kaleoLog.setTerminalKaleoNode(targetKaleoNode.isTerminal());

		if (sourceKaleoNode != null) {
			kaleoLog.setPreviousKaleoNodeId(sourceKaleoNode.getKaleoNodeId());
			kaleoLog.setPreviousKaleoNodeName(sourceKaleoNode.getName());
		}

		kaleoLog.setStartDate(kaleoLog.getCreateDate());

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addNodeExitKaleoLog(
			KaleoInstanceToken kaleoInstanceToken, KaleoNode departingKaleoNode,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.NODE_EXIT, serviceContext);

		kaleoLog.setKaleoClassName(KaleoNode.class.getName());
		kaleoLog.setKaleoClassPK(departingKaleoNode.getKaleoNodeId());
		kaleoLog.setKaleoDefinitionId(
			departingKaleoNode.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			departingKaleoNode.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoNodeName(departingKaleoNode.getName());
		kaleoLog.setEndDate(kaleoLog.getCreateDate());

		try {
			KaleoLog previousKaleoLog = getPreviousLog(
				kaleoLog.getKaleoInstanceTokenId(), kaleoLog.getKaleoClassPK(),
				LogType.WORKFLOW_INSTANCE_START);

			Date startDate = previousKaleoLog.getStartDate();

			Date endDate = kaleoLog.getEndDate();

			kaleoLog.setDuration(endDate.getTime() - startDate.getTime());
		}
		catch (NoSuchLogException noSuchLogException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLogException, noSuchLogException);
			}
		}

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addTaskAssignmentKaleoLog(
			List<KaleoTaskAssignmentInstance>
				previousKaleoTaskAssignmentInstances,
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoTaskInstanceToken.getKaleoInstanceToken();

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.TASK_ASSIGNMENT, serviceContext);

		kaleoLog.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		KaleoNode currentKaleoNode = kaleoInstanceToken.getCurrentKaleoNode();

		kaleoLog.setKaleoClassName(KaleoNode.class.getName());
		kaleoLog.setKaleoClassPK(currentKaleoNode.getKaleoNodeId());
		kaleoLog.setKaleoDefinitionId(currentKaleoNode.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			currentKaleoNode.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoNodeName(currentKaleoNode.getName());

		if ((previousKaleoTaskAssignmentInstances != null) &&
			(previousKaleoTaskAssignmentInstances.size() == 1)) {

			KaleoTaskAssignmentInstance previousKaleoTaskAssignmentInstance =
				previousKaleoTaskAssignmentInstances.get(0);

			kaleoLog.setPreviousAssigneeClassName(
				previousKaleoTaskAssignmentInstance.getAssigneeClassName());
			kaleoLog.setPreviousAssigneeClassPK(
				previousKaleoTaskAssignmentInstance.getAssigneeClassPK());
		}

		if (kaleoTaskAssignmentInstance != null) {
			kaleoLog.setCurrentAssigneeClassName(
				kaleoTaskAssignmentInstance.getAssigneeClassName());
			kaleoLog.setCurrentAssigneeClassPK(
				kaleoTaskAssignmentInstance.getAssigneeClassPK());
		}

		kaleoLog.setComment(comment);
		kaleoLog.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		return kaleoLogPersistence.update(kaleoLog);
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #addTaskAssignmentKaleoLog(List, KaleoTaskAssignmentInstance,
	 *             KaleoTaskInstanceToken, String, Map, ServiceContext)}}
	 */
	@Deprecated
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addTaskAssignmentKaleoLog(
			List<KaleoTaskAssignmentInstance>
				previousKaleoTaskAssignmentInstances,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance = null;

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		if (ListUtil.isNotEmpty(kaleoTaskAssignmentInstances)) {
			kaleoTaskAssignmentInstance = kaleoTaskAssignmentInstances.get(0);
		}

		return addTaskAssignmentKaleoLog(
			previousKaleoTaskAssignmentInstances, kaleoTaskAssignmentInstance,
			kaleoTaskInstanceToken, comment, workflowContext, serviceContext);
	}

	@Override
	public List<KaleoLog> addTaskAssignmentKaleoLogs(
			List<KaleoTaskAssignmentInstance> previousTaskAssignmentInstances,
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		List<KaleoLog> kaleoLogs = new ArrayList<>();

		for (KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance :
				kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances()) {

			kaleoLogs.add(
				kaleoLogLocalService.addTaskAssignmentKaleoLog(
					previousTaskAssignmentInstances,
					kaleoTaskAssignmentInstance, kaleoTaskInstanceToken,
					comment, workflowContext, serviceContext));
		}

		if (ListUtil.isEmpty(kaleoLogs)) {
			kaleoLogs.add(
				kaleoLogLocalService.addTaskAssignmentKaleoLog(
					previousTaskAssignmentInstances, null,
					kaleoTaskInstanceToken, comment, workflowContext,
					serviceContext));
		}

		return kaleoLogs;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addTaskCompletionKaleoLog(
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoTaskInstanceToken.getKaleoInstanceToken();

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.TASK_COMPLETION, serviceContext);

		kaleoLog.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		KaleoNode currentKaleoNode = kaleoInstanceToken.getCurrentKaleoNode();

		kaleoLog.setKaleoClassName(KaleoNode.class.getName());
		kaleoLog.setKaleoClassPK(currentKaleoNode.getKaleoNodeId());
		kaleoLog.setKaleoDefinitionId(currentKaleoNode.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			currentKaleoNode.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoNodeName(currentKaleoNode.getName());

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		if (!kaleoTaskAssignmentInstances.isEmpty()) {
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
				kaleoTaskAssignmentInstances.get(0);

			kaleoLog.setCurrentAssigneeClassName(
				kaleoTaskAssignmentInstance.getAssigneeClassName());
			kaleoLog.setCurrentAssigneeClassPK(
				kaleoTaskAssignmentInstance.getAssigneeClassPK());
		}

		kaleoLog.setComment(comment);
		kaleoLog.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addTaskUpdateKaleoLog(
			KaleoTaskInstanceToken kaleoTaskInstanceToken, String comment,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoTaskInstanceToken.getKaleoInstanceToken(), LogType.TASK_UPDATE,
			serviceContext);

		kaleoLog.setKaleoTaskInstanceTokenId(
			kaleoTaskInstanceToken.getKaleoTaskInstanceTokenId());

		List<KaleoTaskAssignmentInstance> kaleoTaskAssignmentInstances =
			kaleoTaskInstanceToken.getKaleoTaskAssignmentInstances();

		if (!kaleoTaskAssignmentInstances.isEmpty()) {
			KaleoTaskAssignmentInstance kaleoTaskAssignmentInstance =
				kaleoTaskAssignmentInstances.get(0);

			kaleoLog.setCurrentAssigneeClassName(
				kaleoTaskAssignmentInstance.getAssigneeClassName());
			kaleoLog.setCurrentAssigneeClassPK(
				kaleoTaskAssignmentInstance.getAssigneeClassPK());
		}

		kaleoLog.setComment(comment);
		kaleoLog.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addWorkflowInstanceEndKaleoLog(
			KaleoInstanceToken kaleoInstanceToken,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.WORKFLOW_INSTANCE_END, serviceContext);

		kaleoLog.setEndDate(kaleoLog.getCreateDate());

		try {
			KaleoLog previousKaleoLog = getPreviousLog(
				kaleoLog.getKaleoInstanceTokenId(), 0,
				LogType.WORKFLOW_INSTANCE_START);

			Date startDate = previousKaleoLog.getStartDate();

			Date endDate = kaleoLog.getEndDate();

			kaleoLog.setDuration(endDate.getTime() - startDate.getTime());
		}
		catch (NoSuchLogException noSuchLogException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchLogException, noSuchLogException);
			}
		}

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoLog addWorkflowInstanceStartKaleoLog(
			KaleoInstanceToken kaleoInstanceToken,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoLog kaleoLog = createKaleoLog(
			kaleoInstanceToken, LogType.WORKFLOW_INSTANCE_START,
			serviceContext);

		kaleoLog.setStartDate(kaleoLog.getCreateDate());

		KaleoInstance kaleoInstance = kaleoInstanceToken.getKaleoInstance();

		kaleoLog.setWorkflowContext(kaleoInstance.getWorkflowContext());

		return kaleoLogPersistence.update(kaleoLog);
	}

	@Override
	public void deleteCompanyKaleoLogs(long companyId) {
		for (KaleoLog kaleoLog :
				kaleoLogPersistence.findByCompanyId(companyId)) {

			kaleoLogLocalService.deleteKaleoLog(kaleoLog);
		}
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoLogs(
		long kaleoDefinitionVersionId) {

		for (KaleoLog kaleoLog :
				kaleoLogPersistence.findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId)) {

			kaleoLogLocalService.deleteKaleoLog(kaleoLog);
		}
	}

	@Override
	public void deleteKaleoInstanceKaleoLogs(long kaleoInstanceId) {
		for (KaleoLog kaleoLog :
				kaleoLogPersistence.findByKaleoInstanceId(kaleoInstanceId)) {

			kaleoLogLocalService.deleteKaleoLog(kaleoLog);
		}
	}

	@Override
	public List<KaleoLog> getKaleoInstanceKaleoLogs(
		long companyId, long kaleoInstanceId, List<Integer> logTypes, int start,
		int end, OrderByComparator<KaleoLog> orderByComparator) {

		return doSearch(
			companyId,
			HashMapBuilder.<String, Serializable>put(
				"kaleoInstanceId", kaleoInstanceId
			).put(
				"logTypes", _toIntegerArraySupplier(logTypes)
			).build(),
			start, end, orderByComparator);
	}

	@Override
	public int getKaleoInstanceKaleoLogsCount(
		long companyId, long kaleoInstanceId, List<Integer> logTypes) {

		return doSearchCount(
			companyId,
			HashMapBuilder.put(
				"kaleoInstanceId", (Serializable)kaleoInstanceId
			).put(
				"logTypes", _toIntegerArraySupplier(logTypes)
			).build());
	}

	@Override
	public List<KaleoLog> getKaleoTaskInstanceTokenKaleoLogs(
		long companyId, long kaleoTaskInstanceTokenId, List<Integer> logTypes,
		int start, int end, OrderByComparator<KaleoLog> orderByComparator) {

		return doSearch(
			companyId,
			HashMapBuilder.put(
				"kaleoTaskInstanceTokenId",
				(Serializable)kaleoTaskInstanceTokenId
			).put(
				"logTypes", _toIntegerArraySupplier(logTypes)
			).build(),
			start, end, orderByComparator);
	}

	@Override
	public int getKaleoTaskInstanceTokenKaleoLogsCount(
		long companyId, long kaleoTaskInstanceTokenId, List<Integer> logTypes) {

		return doSearchCount(
			companyId,
			HashMapBuilder.put(
				"kaleoTaskInstanceTokenId",
				(Serializable)kaleoTaskInstanceTokenId
			).put(
				"logTypes", _toIntegerArraySupplier(logTypes)
			).build());
	}

	protected void addLogTypesJunction(
		DynamicQuery dynamicQuery, List<Integer> logTypes) {

		Junction junction = RestrictionsFactoryUtil.disjunction();

		for (Integer logType : logTypes) {
			String logTypeString = KaleoLogUtil.convert(logType);

			if (Validator.isNull(logTypeString)) {
				continue;
			}

			Property property = PropertyFactoryUtil.forName("type");

			junction.add(property.eq(logTypeString));
		}

		dynamicQuery.add(junction);
	}

	protected DynamicQuery buildKaleoInstanceDynamicQuery(
		long kaleoInstanceId, List<Integer> logTypes) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoLog.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("kaleoInstanceId");

		dynamicQuery.add(property.eq(kaleoInstanceId));

		addLogTypesJunction(dynamicQuery, logTypes);

		return dynamicQuery;
	}

	protected DynamicQuery buildKaleoTaskInstanceTokenDynamicQuery(
		long kaleoTaskId, List<Integer> logTypes) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoLog.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName(
			"kaleoTaskInstanceTokenId");

		dynamicQuery.add(property.eq(kaleoTaskId));

		addLogTypesJunction(dynamicQuery, logTypes);

		return dynamicQuery;
	}

	protected SearchContext buildSearchContext(
		long companyId, Map<String, Serializable> searchAttributes, int start,
		int end, OrderByComparator<KaleoLog> orderByComparator) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(searchAttributes);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {-1L});
		searchContext.setStart(start);

		if (orderByComparator != null) {
			searchContext.setSorts(getSortsFromComparator(orderByComparator));
		}

		return searchContext;
	}

	protected KaleoLog createKaleoLog(
			KaleoInstanceToken kaleoInstanceToken, LogType logType,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date date = new Date();

		long kaleoLogId = counterLocalService.increment();

		KaleoLog kaleoLog = kaleoLogPersistence.create(kaleoLogId);

		kaleoLog.setCompanyId(user.getCompanyId());
		kaleoLog.setUserId(user.getUserId());
		kaleoLog.setUserName(user.getFullName());
		kaleoLog.setCreateDate(date);
		kaleoLog.setModifiedDate(date);
		kaleoLog.setKaleoDefinitionId(
			kaleoInstanceToken.getKaleoDefinitionId());
		kaleoLog.setKaleoDefinitionVersionId(
			kaleoInstanceToken.getKaleoDefinitionVersionId());
		kaleoLog.setKaleoInstanceId(kaleoInstanceToken.getKaleoInstanceId());
		kaleoLog.setKaleoInstanceTokenId(
			kaleoInstanceToken.getKaleoInstanceTokenId());
		kaleoLog.setType(logType.name());

		return kaleoLog;
	}

	protected List<KaleoLog> doSearch(
		long companyId, Map<String, Serializable> searchAttributes, int start,
		int end, OrderByComparator<KaleoLog> orderByComparator) {

		try {
			Indexer<KaleoLog> indexer = IndexerRegistryUtil.getIndexer(
				KaleoLog.class.getName());

			Hits hits = indexer.search(
				buildSearchContext(
					companyId, searchAttributes, start, end,
					orderByComparator));

			return Stream.of(
				hits.getDocs()
			).map(
				document -> GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK))
			).map(
				kaleoLogPersistence::fetchByPrimaryKey
			).filter(
				Objects::nonNull
			).collect(
				Collectors.toList()
			);
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return Collections.emptyList();
	}

	protected int doSearchCount(
		long companyId, Map<String, Serializable> searchAttributes) {

		try {
			Indexer<KaleoLog> indexer = IndexerRegistryUtil.getIndexer(
				KaleoLog.class.getName());

			return (int)indexer.searchCount(
				buildSearchContext(
					companyId, searchAttributes, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return 0;
	}

	protected KaleoLog getPreviousLog(
			long kaleoInstanceTokenId, long kaleoNodeId, LogType logType)
		throws PortalException {

		List<KaleoLog> kaleoLogEntries = null;

		if (kaleoNodeId > 0) {
			kaleoLogEntries = kaleoLogPersistence.findByKCN_KCPK_KITI_T(
				KaleoNode.class.getName(), kaleoNodeId, kaleoInstanceTokenId,
				logType.name());
		}
		else {
			kaleoLogEntries = kaleoLogPersistence.findByKITI_T(
				kaleoInstanceTokenId, logType.name());
		}

		if (!kaleoLogEntries.isEmpty()) {
			return kaleoLogEntries.get(0);
		}

		throw new NoSuchLogException();
	}

	protected Sort[] getSortsFromComparator(
		OrderByComparator<KaleoLog> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return Stream.of(
			orderByComparator.getOrderByFields()
		).map(
			orderByFieldName -> {
				String fieldName = _fieldNameOrderByCols.getOrDefault(
					orderByFieldName, orderByFieldName);

				return new Sort(
					fieldName,
					_fieldNameSortTypes.getOrDefault(
						fieldName, Sort.STRING_TYPE),
					!orderByComparator.isAscending());
			}
		).toArray(
			Sort[]::new
		);
	}

	private static String _getSortableFieldName(String name, String type) {
		return Field.getSortableFieldName(
			StringBundler.concat(name, StringPool.UNDERLINE, type));
	}

	private BaseMapBuilder.UnsafeSupplier<Serializable, Exception>
		_toIntegerArraySupplier(List<Integer> logTypes) {

		return () -> {
			if (ListUtil.isEmpty(logTypes)) {
				return null;
			}

			return logTypes.toArray(new Integer[0]);
		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoLogLocalServiceImpl.class);

	private static final Map<String, String> _fieldNameOrderByCols =
		HashMapBuilder.put(
			"createDate", _getSortableFieldName(Field.CREATE_DATE, "Number")
		).put(
			"kaleoLogId", _getSortableFieldName("kaleoLogId", "Number")
		).put(
			"modifiedDate", _getSortableFieldName(Field.MODIFIED_DATE, "Number")
		).put(
			"userId", _getSortableFieldName(Field.USER_ID, "Number")
		).build();
	private static final Map<String, Integer> _fieldNameSortTypes =
		HashMapBuilder.put(
			Field.CREATE_DATE, Sort.LONG_TYPE
		).put(
			Field.MODIFIED_DATE, Sort.LONG_TYPE
		).build();

}