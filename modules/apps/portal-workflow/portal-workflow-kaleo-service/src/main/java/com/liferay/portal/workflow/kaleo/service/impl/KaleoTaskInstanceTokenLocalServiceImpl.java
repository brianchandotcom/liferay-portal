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

import com.liferay.exportimport.kernel.staging.Staging;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Property;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoTaskInstanceTokenField;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskAssignment;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskAssignmentInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskFormInstanceLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoTaskInstanceTokenLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenQuery;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken",
	service = AopService.class
)
public class KaleoTaskInstanceTokenLocalServiceImpl
	extends KaleoTaskInstanceTokenLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoTaskInstanceToken addKaleoTaskInstanceToken(
			long kaleoInstanceTokenId, long kaleoTaskId, String kaleoTaskName,
			Collection<KaleoTaskAssignment> kaleoTaskAssignments, Date dueDate,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoInstanceTokenPersistence.findByPrimaryKey(
				kaleoInstanceTokenId);

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoTaskInstanceTokenId = counterLocalService.increment();

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.create(kaleoTaskInstanceTokenId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoTaskInstanceToken.setGroupId(groupId);

		kaleoTaskInstanceToken.setCompanyId(user.getCompanyId());
		kaleoTaskInstanceToken.setUserId(user.getUserId());
		kaleoTaskInstanceToken.setUserName(user.getFullName());
		kaleoTaskInstanceToken.setCreateDate(now);
		kaleoTaskInstanceToken.setModifiedDate(now);
		kaleoTaskInstanceToken.setKaleoDefinitionVersionId(
			kaleoInstanceToken.getKaleoDefinitionVersionId());
		kaleoTaskInstanceToken.setKaleoInstanceId(
			kaleoInstanceToken.getKaleoInstanceId());
		kaleoTaskInstanceToken.setKaleoInstanceTokenId(kaleoInstanceTokenId);
		kaleoTaskInstanceToken.setDueDate(dueDate);

		kaleoTaskInstanceToken.setKaleoTaskId(kaleoTaskId);
		kaleoTaskInstanceToken.setKaleoTaskName(kaleoTaskName);

		if (workflowContext != null) {
			kaleoTaskInstanceToken.setClassName(
				(String)workflowContext.get(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

			if (workflowContext.containsKey(
					WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)) {

				kaleoTaskInstanceToken.setClassPK(
					GetterUtil.getLong(
						(String)workflowContext.get(
							WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)));
			}
		}

		kaleoTaskInstanceToken.setCompleted(false);
		kaleoTaskInstanceToken.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		kaleoTaskInstanceTokenPersistence.update(kaleoTaskInstanceToken);

		_kaleoTaskAssignmentInstanceLocalService.addTaskAssignmentInstances(
			kaleoTaskInstanceToken, kaleoTaskAssignments, workflowContext,
			serviceContext);

		return kaleoTaskInstanceToken;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoTaskInstanceToken assignKaleoTaskInstanceToken(
			long kaleoTaskInstanceTokenId, String assigneeClassName,
			long assigneeClassPK, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.findByPrimaryKey(
				kaleoTaskInstanceTokenId);

		kaleoTaskInstanceToken.setModifiedDate(new Date());
		kaleoTaskInstanceToken.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		kaleoTaskInstanceTokenPersistence.update(kaleoTaskInstanceToken);

		_kaleoTaskAssignmentInstanceLocalService.
			assignKaleoTaskAssignmentInstance(
				kaleoTaskInstanceToken, assigneeClassName, assigneeClassPK,
				serviceContext);

		return kaleoTaskInstanceToken;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoTaskInstanceToken completeKaleoTaskInstanceToken(
			long kaleoTaskInstanceTokenId, ServiceContext serviceContext)
		throws PortalException {

		// Kaleo task instance token

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.findByPrimaryKey(
				kaleoTaskInstanceTokenId);

		kaleoTaskInstanceToken.setCompletionUserId(serviceContext.getUserId());
		kaleoTaskInstanceToken.setCompleted(true);
		kaleoTaskInstanceToken.setCompletionDate(new Date());

		kaleoTaskInstanceTokenPersistence.update(kaleoTaskInstanceToken);

		// Kaleo task assignment instance

		_kaleoTaskAssignmentInstanceLocalService.completeKaleoTaskInstanceToken(
			kaleoTaskInstanceTokenId, serviceContext);

		// Kaleo timers

		_kaleoTimerInstanceTokenLocalService.completeKaleoTimerInstanceTokens(
			kaleoTaskInstanceToken.getKaleoInstanceTokenId(), serviceContext);

		return kaleoTaskInstanceToken;
	}

	@Override
	public void deleteCompanyKaleoTaskInstanceTokens(long companyId) {

		// Kaleo task instance tokens

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokenPersistence.findByCompanyId(companyId)) {

			kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
				kaleoTaskInstanceToken);
		}

		// Kaleo task assignment instances

		_kaleoTaskAssignmentInstanceLocalService.
			deleteCompanyKaleoTaskAssignmentInstances(companyId);

		// Kaleo task form instances

		_kaleoTaskFormInstanceLocalService.deleteCompanyKaleoTaskFormInstances(
			companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoTaskInstanceTokens(
		long kaleoDefinitionVersionId) {

		// Kaleo task instance tokens

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokenPersistence.
					findByKaleoDefinitionVersionId(kaleoDefinitionVersionId)) {

			kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
				kaleoTaskInstanceToken);
		}

		// Kaleo task assignment instances

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoDefinitionVersionKaleoTaskAssignmentInstances(
				kaleoDefinitionVersionId);

		// Kaleo task form instances

		_kaleoTaskFormInstanceLocalService.
			deleteKaleoDefinitionVersionKaleoTaskFormInstances(
				kaleoDefinitionVersionId);
	}

	@Override
	public void deleteKaleoInstanceKaleoTaskInstanceTokens(
		long kaleoInstanceId) {

		// Kaleo task instance tokens

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokenPersistence.findByKaleoInstanceId(
					kaleoInstanceId)) {

			kaleoTaskInstanceTokenLocalService.deleteKaleoTaskInstanceToken(
				kaleoTaskInstanceToken);
		}

		// Kaleo task assignment instances

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoInstanceKaleoTaskAssignmentInstances(kaleoInstanceId);

		// Kaleo task form instances

		_kaleoTaskFormInstanceLocalService.
			deleteKaleoInstanceKaleoTaskFormInstances(kaleoInstanceId);
	}

	@Override
	public KaleoTaskInstanceToken deleteKaleoTaskInstanceToken(
			long kaleoTaskInstanceTokenId)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.remove(kaleoTaskInstanceTokenId);

		_kaleoTaskAssignmentInstanceLocalService.
			deleteKaleoTaskAssignmentInstances(kaleoTaskInstanceToken);

		_kaleoTaskFormInstanceLocalService.
			deleteKaleoTaskInstanceTokenKaleoTaskFormInstances(
				kaleoTaskInstanceTokenId);

		return kaleoTaskInstanceToken;
	}

	@Override
	public List<KaleoTaskInstanceToken> getCompanyKaleoTaskInstanceTokens(
		long companyId, int start, int end) {

		return kaleoTaskInstanceTokenPersistence.findByCompanyId(
			companyId, start, end);
	}

	@Override
	public int getCompanyKaleoTaskInstanceTokensCount(long companyId) {
		return kaleoTaskInstanceTokenPersistence.countByCompanyId(companyId);
	}

	@Override
	public List<KaleoTaskInstanceToken> getKaleoTaskInstanceTokens(
		Boolean completed, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = buildDynamicQuery(
			completed, serviceContext);

		return dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public List<KaleoTaskInstanceToken> getKaleoTaskInstanceTokens(
		List<Long> roleIds, Boolean completed, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setEnd(end);
		kaleoTaskInstanceTokenQuery.setOrderByComparator(orderByComparator);
		kaleoTaskInstanceTokenQuery.setRoleIds(roleIds);
		kaleoTaskInstanceTokenQuery.setStart(start);

		return kaleoTaskInstanceTokenFinder.findKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public List<KaleoTaskInstanceToken> getKaleoTaskInstanceTokens(
		long kaleoInstanceId, Boolean completed, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		long userId = serviceContext.getUserId();

		if (userId == 0) {
			DynamicQuery dynamicQuery = buildDynamicQuery(
				kaleoInstanceId, completed, serviceContext);

			return dynamicQuery(dynamicQuery, start, end, orderByComparator);
		}

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAssigneeClassName(User.class.getName());
		kaleoTaskInstanceTokenQuery.setAssigneeClassPK(
			serviceContext.getUserId());
		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setEnd(end);
		kaleoTaskInstanceTokenQuery.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskInstanceTokenQuery.setOrderByComparator(orderByComparator);
		kaleoTaskInstanceTokenQuery.setStart(start);

		return kaleoTaskInstanceTokenFinder.findKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public KaleoTaskInstanceToken getKaleoTaskInstanceTokens(
			long kaleoInstanceId, long kaleoTaskId)
		throws PortalException {

		return kaleoTaskInstanceTokenPersistence.findByKII_KTI(
			kaleoInstanceId, kaleoTaskId);
	}

	@Override
	public List<KaleoTaskInstanceToken> getKaleoTaskInstanceTokens(
		String assigneeClassName, long assigneeClassPK, Boolean completed,
		int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAssigneeClassName(assigneeClassName);
		kaleoTaskInstanceTokenQuery.setAssigneeClassPK(assigneeClassPK);
		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setEnd(end);
		kaleoTaskInstanceTokenQuery.setOrderByComparator(orderByComparator);
		kaleoTaskInstanceTokenQuery.setStart(start);

		return kaleoTaskInstanceTokenFinder.findKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		Boolean completed, ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = buildDynamicQuery(
			completed, serviceContext);

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		List<Long> roleIds, Boolean completed, ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setAssigneeClassName(Role.class.getName());

		kaleoTaskInstanceTokenQuery.setRoleIds(roleIds);

		return kaleoTaskInstanceTokenFinder.countKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		long kaleoInstanceId, Boolean completed,
		ServiceContext serviceContext) {

		long userId = serviceContext.getUserId();

		if (userId == 0) {
			DynamicQuery dynamicQuery = buildDynamicQuery(
				kaleoInstanceId, completed, serviceContext);

			return (int)dynamicQueryCount(dynamicQuery);
		}

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAssigneeClassName(User.class.getName());
		kaleoTaskInstanceTokenQuery.setAssigneeClassPK(
			serviceContext.getUserId());
		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setKaleoInstanceId(kaleoInstanceId);

		return kaleoTaskInstanceTokenFinder.countKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public int getKaleoTaskInstanceTokensCount(
		String assigneeClassName, long assigneeClassPK, Boolean completed,
		ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAssigneeClassName(assigneeClassName);
		kaleoTaskInstanceTokenQuery.setAssigneeClassPK(assigneeClassPK);
		kaleoTaskInstanceTokenQuery.setCompleted(completed);

		return kaleoTaskInstanceTokenFinder.countKaleoTaskInstanceTokens(
			kaleoTaskInstanceTokenQuery);
	}

	@Override
	public List<KaleoTaskInstanceToken>
		getSubmittingUserKaleoTaskInstanceTokens(
			long userId, Boolean completed, int start, int end,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
			ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoTaskInstanceToken.class, getClassLoader());

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(serviceContext.getCompanyId()));

		Property workflowContextProperty = PropertyFactoryUtil.forName(
			"workflowContext");

		dynamicQuery.add(
			workflowContextProperty.like("%\"userId\":\"" + userId + "\"%"));

		addCompletedCriterion(dynamicQuery, completed);

		return dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	@Override
	public int getSubmittingUserKaleoTaskInstanceTokensCount(
		long userId, Boolean completed, ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoTaskInstanceToken.class, getClassLoader());

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(serviceContext.getCompanyId()));

		Property workflowContextProperty = PropertyFactoryUtil.forName(
			"workflowContext");

		dynamicQuery.add(
			workflowContextProperty.like("%\"userId\":\"" + userId + "\"%"));

		addCompletedCriterion(dynamicQuery, completed);

		return (int)dynamicQueryCount(dynamicQuery);
	}

	@Override
	public boolean hasPendingKaleoTaskForms(long kaleoTaskInstanceTokenId)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.findByPrimaryKey(
				kaleoTaskInstanceTokenId);

		int kaleoTaskFormsCount = kaleoTaskFormPersistence.countByKaleoTaskId(
			kaleoTaskInstanceToken.getKaleoTaskId());

		int kaleoTaskFormInstancesCount =
			kaleoTaskFormInstancePersistence.countByKaleoTaskInstanceTokenId(
				kaleoTaskInstanceTokenId);

		if (kaleoTaskFormsCount > kaleoTaskFormInstancesCount) {
			return true;
		}

		return false;
	}

	@Override
	public List<KaleoTaskInstanceToken> search(
		String keywords, Boolean completed, Boolean searchByUserRoles,
		int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		return search(
			keywords, keywords, null, null, null, completed, searchByUserRoles,
			false, start, end, orderByComparator, serviceContext);
	}

	@Override
	public List<KaleoTaskInstanceToken> search(
		String taskName, String assetType, Long[] assetPrimaryKeys,
		Date dueDateGT, Date dueDateLT, Boolean completed,
		Boolean searchByUserRoles, boolean andOperator, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		return search(
			null, taskName, getAssetTypes(assetType), assetPrimaryKeys,
			dueDateGT, dueDateLT, completed, searchByUserRoles, andOperator,
			start, end, orderByComparator, serviceContext);
	}

	@Override
	public List<KaleoTaskInstanceToken> search(
		String assetTitle, String taskName, String[] assetTypes,
		Long[] assetPrimaryKeys, Date dueDateGT, Date dueDateLT,
		Boolean completed, Boolean searchByUserRoles, boolean andOperator,
		int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAndOperator(andOperator);
		kaleoTaskInstanceTokenQuery.setAssetTitle(assetTitle);
		kaleoTaskInstanceTokenQuery.setAssetTypes(assetTypes);
		kaleoTaskInstanceTokenQuery.setAssetPrimaryKeys(assetPrimaryKeys);
		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setDueDateGT(dueDateGT);
		kaleoTaskInstanceTokenQuery.setDueDateLT(dueDateLT);
		kaleoTaskInstanceTokenQuery.setEnd(end);
		kaleoTaskInstanceTokenQuery.setOrderByComparator(orderByComparator);
		kaleoTaskInstanceTokenQuery.setSearchByUserRoles(searchByUserRoles);
		kaleoTaskInstanceTokenQuery.setStart(start);
		kaleoTaskInstanceTokenQuery.setTaskName(taskName);

		try {
			Indexer<KaleoTaskInstanceToken> indexer =
				IndexerRegistryUtil.getIndexer(
					KaleoTaskInstanceToken.class.getName());

			SearchContext searchContext = buildSearchContext(
				kaleoTaskInstanceTokenQuery, start, end, orderByComparator);

			List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens =
				new ArrayList<>();

			Hits hits = indexer.search(searchContext);

			for (Document document : hits.getDocs()) {
				long kaleoTaskInstanceTokenId = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				Optional.ofNullable(
					kaleoTaskInstanceTokenPersistence.fetchByPrimaryKey(
						kaleoTaskInstanceTokenId)
				).ifPresent(
					kaleoTaskInstanceTokens::add
				);
			}

			return kaleoTaskInstanceTokens;
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return Collections.emptyList();
	}

	@Override
	public List<KaleoTaskInstanceToken> search(
		String keywords, String[] assetTypes, Boolean completed,
		Boolean searchByUserRoles, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		ServiceContext serviceContext) {

		return search(
			keywords, keywords, assetTypes, null, null, null, completed,
			searchByUserRoles, false, start, end, orderByComparator,
			serviceContext);
	}

	@Override
	public int searchCount(
		long kaleoInstanceId, Boolean completed, Boolean searchByUserRoles,
		ServiceContext serviceContext) {

		return searchCount(
			kaleoInstanceId, null, null, null, null, null, null, completed,
			searchByUserRoles, false, serviceContext);
	}

	@Override
	public int searchCount(
		Long kaleoInstanceId, String assetTitle, String taskName,
		String[] assetTypes, Long[] assetPrimaryKeys, Date dueDateGT,
		Date dueDateLT, Boolean completed, Boolean searchByUserRoles,
		boolean andOperator, ServiceContext serviceContext) {

		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery =
			new KaleoTaskInstanceTokenQuery(serviceContext);

		kaleoTaskInstanceTokenQuery.setAndOperator(andOperator);
		kaleoTaskInstanceTokenQuery.setAssetTitle(assetTitle);
		kaleoTaskInstanceTokenQuery.setAssetTypes(assetTypes);
		kaleoTaskInstanceTokenQuery.setAssetPrimaryKeys(assetPrimaryKeys);
		kaleoTaskInstanceTokenQuery.setCompleted(completed);
		kaleoTaskInstanceTokenQuery.setDueDateGT(dueDateGT);
		kaleoTaskInstanceTokenQuery.setDueDateLT(dueDateLT);
		kaleoTaskInstanceTokenQuery.setKaleoInstanceId(kaleoInstanceId);
		kaleoTaskInstanceTokenQuery.setSearchByUserRoles(searchByUserRoles);
		kaleoTaskInstanceTokenQuery.setTaskName(taskName);

		try {
			Indexer<KaleoTaskInstanceToken> indexer =
				IndexerRegistryUtil.getIndexer(
					KaleoTaskInstanceToken.class.getName());

			SearchContext searchContext = buildSearchContext(
				kaleoTaskInstanceTokenQuery, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null);

			return (int)indexer.searchCount(searchContext);
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe, pe);
			}
		}

		return 0;
	}

	@Override
	public int searchCount(
		String keywords, Boolean completed, Boolean searchByUserRoles,
		ServiceContext serviceContext) {

		return searchCount(
			keywords, keywords, null, null, null, completed, searchByUserRoles,
			false, serviceContext);
	}

	@Override
	public int searchCount(
		String taskName, String assetType, Long[] assetPrimaryKeys,
		Date dueDateGT, Date dueDateLT, Boolean completed,
		Boolean searchByUserRoles, boolean andOperator,
		ServiceContext serviceContext) {

		return searchCount(
			null, null, taskName, getAssetTypes(assetType), assetPrimaryKeys,
			dueDateGT, dueDateLT, completed, searchByUserRoles, andOperator,
			serviceContext);
	}

	@Override
	public int searchCount(
		String keywords, String[] assetTypes, Boolean completed,
		Boolean searchByUserRoles, ServiceContext serviceContext) {

		return searchCount(
			null, keywords, keywords, assetTypes, null, null, null, completed,
			searchByUserRoles, false, serviceContext);
	}

	@Override
	public KaleoTaskInstanceToken updateDueDate(
			long kaleoTaskInstanceTokenId, Date dueDate,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			kaleoTaskInstanceTokenPersistence.findByPrimaryKey(
				kaleoTaskInstanceTokenId);

		kaleoTaskInstanceToken.setModifiedDate(new Date());

		if (dueDate != null) {
			Calendar cal = CalendarFactoryUtil.getCalendar(
				LocaleUtil.getDefault());

			cal.setTime(dueDate);

			kaleoTaskInstanceToken.setDueDate(cal.getTime());
		}

		return kaleoTaskInstanceTokenLocalService.updateKaleoTaskInstanceToken(
			kaleoTaskInstanceToken);
	}

	protected void addCompletedCriterion(
		DynamicQuery dynamicQuery, Boolean completed) {

		if (completed == null) {
			return;
		}

		Property property = PropertyFactoryUtil.forName("completed");

		dynamicQuery.add(property.eq(completed));
	}

	protected DynamicQuery buildDynamicQuery(
		Boolean completed, ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoTaskInstanceToken.class, getClassLoader());

		Property property = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(property.eq(serviceContext.getCompanyId()));

		addCompletedCriterion(dynamicQuery, completed);

		return dynamicQuery;
	}

	protected DynamicQuery buildDynamicQuery(
		long kaleoInstanceId, Boolean completed,
		ServiceContext serviceContext) {

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(
			KaleoTaskInstanceToken.class, getClassLoader());

		Property companyIdProperty = PropertyFactoryUtil.forName("companyId");

		dynamicQuery.add(companyIdProperty.eq(serviceContext.getCompanyId()));

		Property kaleoInstanceIdProperty = PropertyFactoryUtil.forName(
			"kaleoInstanceId");

		dynamicQuery.add(kaleoInstanceIdProperty.eq(kaleoInstanceId));

		addCompletedCriterion(dynamicQuery, completed);

		return dynamicQuery;
	}

	protected SearchContext buildSearchContext(
		KaleoTaskInstanceTokenQuery kaleoTaskInstanceTokenQuery, int start,
		int end, OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			"kaleoTaskInstanceTokenQuery", kaleoTaskInstanceTokenQuery);
		searchContext.setCompanyId(kaleoTaskInstanceTokenQuery.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setStart(start);

		if (orderByComparator != null) {
			searchContext.setSorts(getSortsFromComparator(orderByComparator));
		}

		searchContext.setUserId(kaleoTaskInstanceTokenQuery.getUserId());

		return searchContext;
	}

	protected String[] getAssetTypes(String assetType) {
		if (Validator.isNull(assetType)) {
			return null;
		}

		return new String[] {assetType};
	}

	protected Sort[] getSortsFromComparator(
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		Stream<String> stream = Arrays.stream(
			orderByComparator.getOrderByFields());

		return stream.map(
			orderByFieldName -> {
				String fieldName = _fieldNameOrderByCols.getOrDefault(
					orderByFieldName, orderByFieldName);

				int sortType = _fieldNameSortTypes.getOrDefault(
					fieldName, Sort.STRING_TYPE);

				boolean ascending = orderByComparator.isAscending();

				if (Objects.equals(
						orderByFieldName,
						KaleoTaskInstanceTokenField.COMPLETED)) {

					ascending = true;
				}

				return new Sort(fieldName, sortType, !ascending);
			}
		).toArray(
			Sort[]::new
		);
	}

	private static String _getSortableFieldName(String name, String type) {
		return Field.getSortableFieldName(
			StringBundler.concat(name, StringPool.UNDERLINE, type));
	}

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenLocalServiceImpl.class);

	private static final Map<String, String> _fieldNameOrderByCols =
		HashMapBuilder.<String, String>put(
			"completed",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.COMPLETED, "String")
		).put(
			"completionDate",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.COMPLETION_DATE, "Number")
		).put(
			"createDate", _getSortableFieldName(Field.CREATE_DATE, "Number")
		).put(
			"dueDate",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.DUE_DATE, "Number")
		).put(
			"kaleoTaskId",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.KALEO_TASK_ID, "Number")
		).put(
			"kaleoTaskInstanceTokenId",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.KALEO_TASK_INSTANCE_TOKEN_ID,
				"Number")
		).put(
			"modifiedDate", _getSortableFieldName(Field.MODIFIED_DATE, "Number")
		).put(
			"name",
			_getSortableFieldName(
				KaleoTaskInstanceTokenField.TASK_NAME, "String")
		).put(
			"userId", _getSortableFieldName(Field.USER_ID, "Number")
		).build();
	private static final Map<String, Integer> _fieldNameSortTypes =
		HashMapBuilder.<String, Integer>put(
			Field.CREATE_DATE, Sort.LONG_TYPE
		).put(
			Field.MODIFIED_DATE, Sort.LONG_TYPE
		).put(
			KaleoTaskInstanceTokenField.COMPLETION_DATE, Sort.LONG_TYPE
		).put(
			KaleoTaskInstanceTokenField.DUE_DATE, Sort.LONG_TYPE
		).build();

	@Reference
	private KaleoTaskAssignmentInstanceLocalService
		_kaleoTaskAssignmentInstanceLocalService;

	@Reference
	private KaleoTaskFormInstanceLocalService
		_kaleoTaskFormInstanceLocalService;

	@Reference
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Reference
	private Staging _staging;

}