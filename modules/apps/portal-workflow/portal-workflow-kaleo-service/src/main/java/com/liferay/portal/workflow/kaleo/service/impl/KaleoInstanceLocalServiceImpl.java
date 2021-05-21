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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.exception.NoSuchInstanceException;
import com.liferay.portal.workflow.kaleo.internal.search.KaleoInstanceTokenField;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.runtime.util.WorkflowContextUtil;
import com.liferay.portal.workflow.kaleo.service.KaleoInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoLogLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTaskInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.KaleoTimerInstanceTokenLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoInstanceLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceQuery;

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
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 * @author Marcellus Tavares
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstance",
	service = AopService.class
)
public class KaleoInstanceLocalServiceImpl
	extends KaleoInstanceLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstance addKaleoInstance(
			long kaleoDefinitionId, long kaleoDefinitionVersionId,
			String kaleoDefinitionName, int kaleoDefinitionVersion,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.fetchUser(serviceContext.getUserId());

		if (user == null) {
			user = userLocalService.getDefaultUser(
				serviceContext.getCompanyId());
		}

		Date now = new Date();

		long kaleoInstanceId = counterLocalService.increment();

		KaleoInstance kaleoInstance = kaleoInstancePersistence.create(
			kaleoInstanceId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoInstance.setGroupId(groupId);

		kaleoInstance.setCompanyId(user.getCompanyId());
		kaleoInstance.setUserId(user.getUserId());
		kaleoInstance.setUserName(user.getFullName());
		kaleoInstance.setCreateDate(now);
		kaleoInstance.setModifiedDate(now);
		kaleoInstance.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoInstance.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoInstance.setKaleoDefinitionName(kaleoDefinitionName);
		kaleoInstance.setKaleoDefinitionVersion(kaleoDefinitionVersion);
		kaleoInstance.setClassName(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		if (workflowContext.containsKey(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)) {

			kaleoInstance.setClassPK(
				GetterUtil.getLong(
					(String)workflowContext.get(
						WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)));
		}

		kaleoInstance.setCompleted(false);
		kaleoInstance.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		return kaleoInstancePersistence.update(kaleoInstance);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstance completeKaleoInstance(long kaleoInstanceId)
		throws PortalException {

		KaleoInstance kaleoInstance = kaleoInstancePersistence.findByPrimaryKey(
			kaleoInstanceId);

		kaleoInstance.setCompleted(true);
		kaleoInstance.setCompletionDate(new Date());

		return kaleoInstancePersistence.update(kaleoInstance);
	}

	@Override
	public void deleteCompanyKaleoInstances(long companyId) {

		// Kaleo instances

		for (KaleoInstance kaleoInstance :
				kaleoInstancePersistence.findByCompanyId(companyId)) {

			kaleoInstanceLocalService.deleteKaleoInstance(kaleoInstance);
		}

		// Kaleo instance tokens

		_kaleoInstanceTokenLocalService.deleteCompanyKaleoInstanceTokens(
			companyId);

		// Kaleo logs

		_kaleoLogLocalService.deleteCompanyKaleoLogs(companyId);

		// Kaleo task instance tokens

		_kaleoTaskInstanceTokenLocalService.
			deleteCompanyKaleoTaskInstanceTokens(companyId);
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoInstances(
		long kaleoDefinitionVersionId) {

		// Kaleo instances

		for (KaleoInstance kaleoInstance :
				kaleoInstancePersistence.findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId)) {

			kaleoInstanceLocalService.deleteKaleoInstance(kaleoInstance);
		}

		// Kaleo instance tokens

		_kaleoInstanceTokenLocalService.
			deleteKaleoDefinitionVersionKaleoInstanceTokens(
				kaleoDefinitionVersionId);

		// Kaleo logs

		_kaleoLogLocalService.deleteKaleoDefinitionVersionKaleoLogs(
			kaleoDefinitionVersionId);

		// Kaleo task instance tokens

		_kaleoTaskInstanceTokenLocalService.
			deleteKaleoDefinitionVersionKaleoTaskInstanceTokens(
				kaleoDefinitionVersionId);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public KaleoInstance deleteKaleoInstance(long kaleoInstanceId) {
		KaleoInstance kaleoInstance = null;

		try {
			kaleoInstance = kaleoInstancePersistence.remove(kaleoInstanceId);
		}
		catch (NoSuchInstanceException noSuchInstanceException) {
			if (_log.isDebugEnabled()) {
				_log.debug(noSuchInstanceException, noSuchInstanceException);
			}

			return null;
		}

		// Kaleo instance tokens

		_kaleoInstanceTokenLocalService.deleteKaleoInstanceKaleoInstanceTokens(
			kaleoInstanceId);

		// Kaleo logs

		_kaleoLogLocalService.deleteKaleoInstanceKaleoLogs(kaleoInstanceId);

		// Kaleo task instance tokens

		_kaleoTaskInstanceTokenLocalService.
			deleteKaleoInstanceKaleoTaskInstanceTokens(kaleoInstanceId);

		// Kaleo timer instance tokens

		_kaleoTimerInstanceTokenLocalService.deleteKaleoTimerInstanceTokens(
			kaleoInstanceId);

		return kaleoInstance;
	}

	@Override
	public KaleoInstance fetchKaleoInstance(
		long kaleoInstanceId, long companyId, long userId) {

		return kaleoInstancePersistence.fetchByKII_C_U(
			kaleoInstanceId, companyId, userId);
	}

	@Override
	public int getKaleoDefinitionKaleoInstancesCount(
		long kaleoDefinitionId, boolean completed) {

		return kaleoInstancePersistence.countByKDI_C(
			kaleoDefinitionId, completed);
	}

	@Override
	public List<KaleoInstance> getKaleoInstances(
		Long userId, String assetClassName, Long assetClassPK,
		Boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		return doSearch(
			userId, null, null, getClassNames(assetClassName), assetClassPK,
			completed, start, end, orderByComparator, serviceContext);
	}

	@Override
	public List<KaleoInstance> getKaleoInstances(
		Long userId, String[] assetClassNames, Boolean completed, int start,
		int end, OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		return doSearch(
			userId, null, null, assetClassNames, null, completed, start, end,
			orderByComparator, serviceContext);
	}

	@Override
	public List<KaleoInstance> getKaleoInstances(
		String kaleoDefinitionName, int kaleoDefinitionVersion,
		boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		return doSearch(
			null, kaleoDefinitionName, kaleoDefinitionVersion, null, null,
			completed, start, end, orderByComparator, serviceContext);
	}

	@Override
	public int getKaleoInstancesCount(
		long kaleoDefinitionVersionId, boolean completed) {

		return kaleoInstancePersistence.countByKDVI_C(
			kaleoDefinitionVersionId, completed);
	}

	@Override
	public int getKaleoInstancesCount(
		Long userId, String assetClassName, Long assetClassPK,
		Boolean completed, ServiceContext serviceContext) {

		return doSearchCount(
			userId, null, null, getClassNames(assetClassName), assetClassPK,
			completed, serviceContext);
	}

	@Override
	public int getKaleoInstancesCount(
		Long userId, String[] assetClassNames, Boolean completed,
		ServiceContext serviceContext) {

		return doSearchCount(
			userId, null, null, assetClassNames, null, completed,
			serviceContext);
	}

	@Override
	public int getKaleoInstancesCount(
		String kaleoDefinitionName, int kaleoDefinitionVersion,
		boolean completed, ServiceContext serviceContext) {

		return doSearchCount(
			null, kaleoDefinitionName, kaleoDefinitionVersion, null, null,
			completed, serviceContext);
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #search(Long,
	 *             String, String, String, String, String, Boolean, int, int,
	 *             OrderByComparator, ServiceContext)}
	 */
	@Deprecated
	@Override
	public List<KaleoInstance> search(
		Long userId, String assetClassName, String nodeName,
		String kaleoDefinitionName, Boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		return search(
			userId, assetClassName, null, null, nodeName, kaleoDefinitionName,
			completed, start, end, orderByComparator, serviceContext);
	}

	@Override
	public List<KaleoInstance> search(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String nodeName, String kaleoDefinitionName,
		Boolean completed, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		try {
			BaseModelSearchResult<KaleoInstance> baseModelSearchResult =
				searchKaleoInstances(
					userId, assetClassName, assetTitle, assetDescription,
					nodeName, kaleoDefinitionName, completed, start, end,
					orderByComparator, serviceContext);

			return baseModelSearchResult.getBaseModels();
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return Collections.emptyList();
	}

	/**
	 * @deprecated As of Mueller (7.2.x), replaced by {@link #searchCount(Long,
	 *             String, String, String, String, String, Boolean,
	 *             ServiceContext)}
	 */
	@Deprecated
	@Override
	public int searchCount(
		Long userId, String assetClassName, String nodeName,
		String kaleoDefinitionName, Boolean completed,
		ServiceContext serviceContext) {

		return searchCount(
			userId, assetClassName, null, null, nodeName, kaleoDefinitionName,
			completed, serviceContext);
	}

	@Override
	public int searchCount(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String nodeName, String kaleoDefinitionName,
		Boolean completed, ServiceContext serviceContext) {

		return _kaleoInstanceTokenLocalService.searchCount(
			userId, assetClassName, assetTitle, assetDescription, nodeName,
			kaleoDefinitionName, completed, serviceContext);
	}

	@Override
	public BaseModelSearchResult<KaleoInstance> searchKaleoInstances(
			Long userId, String assetClassName, String assetTitle,
			String assetDescription, String nodeName,
			String kaleoDefinitionName, Boolean completed, int start, int end,
			OrderByComparator<KaleoInstance> orderByComparator,
			ServiceContext serviceContext)
		throws PortalException {

		List<KaleoInstance> kaleoInstances = new ArrayList<>();

		Hits hits = _kaleoInstanceTokenLocalService.search(
			userId, assetClassName, assetTitle, assetDescription, nodeName,
			kaleoDefinitionName, completed, start, end,
			getSortsFromComparator(orderByComparator), serviceContext);

		for (Document document : hits.getDocs()) {
			long kaleoInstanceId = GetterUtil.getLong(
				document.get(KaleoInstanceTokenField.KALEO_INSTANCE_ID));

			KaleoInstance kaleoInstance =
				kaleoInstancePersistence.fetchByPrimaryKey(kaleoInstanceId);

			if (kaleoInstance != null) {
				kaleoInstances.add(kaleoInstance);
			}
		}

		return new BaseModelSearchResult<>(kaleoInstances, hits.getLength());
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstance updateKaleoInstance(
			long kaleoInstanceId, long rootKaleoInstanceTokenId)
		throws PortalException {

		KaleoInstance kaleoInstance = kaleoInstancePersistence.findByPrimaryKey(
			kaleoInstanceId);

		kaleoInstance.setRootKaleoInstanceTokenId(rootKaleoInstanceTokenId);

		return kaleoInstancePersistence.update(kaleoInstance);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstance updateKaleoInstance(
			long kaleoInstanceId, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstance kaleoInstance = kaleoInstancePersistence.findByPrimaryKey(
			kaleoInstanceId);

		kaleoInstance.setWorkflowContext(
			WorkflowContextUtil.convert(workflowContext));

		return kaleoInstancePersistence.update(kaleoInstance);
	}

	protected SearchContext buildSearchContext(
		Map<String, Serializable> searchAttributes, int start, int end,
		OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(searchAttributes);
		searchContext.setCompanyId(serviceContext.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {-1L});
		searchContext.setStart(start);

		if (orderByComparator != null) {
			searchContext.setSorts(getSortsFromComparator(orderByComparator));
		}

		searchContext.setUserId(serviceContext.getUserId());

		return searchContext;
	}

	protected List<KaleoInstance> doSearch(
		Long userId, String kaleoDefinitionName, Integer kaleoDefinitionVersion,
		String[] classNames, Long classPK, Boolean completed, int start,
		int end, OrderByComparator<KaleoInstance> orderByComparator,
		ServiceContext serviceContext) {

		KaleoInstanceQuery kaleoInstanceQuery = new KaleoInstanceQuery(
			serviceContext);

		kaleoInstanceQuery.setClassNames(classNames);
		kaleoInstanceQuery.setClassPK(classPK);
		kaleoInstanceQuery.setCompleted(completed);
		kaleoInstanceQuery.setKaleoDefinitionName(kaleoDefinitionName);
		kaleoInstanceQuery.setKaleoDefinitionVersion(kaleoDefinitionVersion);
		kaleoInstanceQuery.setUserId(userId);

		try {
			Indexer<KaleoInstance> indexer = IndexerRegistryUtil.getIndexer(
				KaleoInstance.class.getName());

			Hits hits = indexer.search(
				buildSearchContext(
					HashMapBuilder.<String, Serializable>put(
						"kaleoInstanceQuery", kaleoInstanceQuery
					).build(),
					start, end, orderByComparator, serviceContext));

			return Stream.of(
				hits.getDocs()
			).map(
				document -> GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK))
			).map(
				kaleoInstancePersistence::fetchByPrimaryKey
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
		Long userId, String kaleoDefinitionName, Integer kaleoDefinitionVersion,
		String[] classNames, Long classPK, boolean completed,
		ServiceContext serviceContext) {

		KaleoInstanceQuery kaleoInstanceQuery = new KaleoInstanceQuery(
			serviceContext);

		kaleoInstanceQuery.setClassNames(classNames);
		kaleoInstanceQuery.setClassPK(classPK);
		kaleoInstanceQuery.setCompleted(completed);
		kaleoInstanceQuery.setKaleoDefinitionName(kaleoDefinitionName);
		kaleoInstanceQuery.setKaleoDefinitionVersion(kaleoDefinitionVersion);
		kaleoInstanceQuery.setUserId(userId);

		try {
			Indexer<KaleoInstance> indexer = IndexerRegistryUtil.getIndexer(
				KaleoInstance.class.getName());

			return (int)indexer.searchCount(
				buildSearchContext(
					HashMapBuilder.<String, Serializable>put(
						"kaleoInstanceQuery", kaleoInstanceQuery
					).build(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null,
					serviceContext));
		}
		catch (PortalException portalException) {
			if (_log.isDebugEnabled()) {
				_log.debug(portalException, portalException);
			}
		}

		return 0;
	}

	protected String[] getClassNames(String className) {
		if (Validator.isNull(className)) {
			return null;
		}

		return new String[] {className};
	}

	protected Sort[] getSortsFromComparator(
		OrderByComparator<KaleoInstance> orderByComparator) {

		if (orderByComparator == null) {
			return null;
		}

		return Stream.of(
			orderByComparator.getOrderByFields()
		).map(
			orderByFieldName -> {
				String fieldName = _fieldNameOrderByCols.getOrDefault(
					orderByFieldName, orderByFieldName);

				int sortType = _fieldNameSortTypes.getOrDefault(
					fieldName, Sort.STRING_TYPE);

				boolean ascending = orderByComparator.isAscending();

				if (Objects.equals(
						orderByFieldName, KaleoInstanceTokenField.COMPLETED)) {

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
		KaleoInstanceLocalServiceImpl.class);

	private static final Map<String, String> _fieldNameOrderByCols =
		HashMapBuilder.put(
			"completed",
			_getSortableFieldName(KaleoInstanceTokenField.COMPLETED, "String")
		).put(
			"completionDate",
			_getSortableFieldName(
				KaleoInstanceTokenField.COMPLETION_DATE, "Number")
		).put(
			"createDate", _getSortableFieldName(Field.CREATE_DATE, "Number")
		).put(
			"kaleoInstanceId",
			_getSortableFieldName(
				KaleoInstanceTokenField.KALEO_INSTANCE_ID, "Number")
		).put(
			"modifiedDate", _getSortableFieldName(Field.MODIFIED_DATE, "Number")
		).put(
			"state",
			_getSortableFieldName(
				KaleoInstanceTokenField.CURRENT_KALEO_NODE_NAME, "String")
		).build();
	private static final Map<String, Integer> _fieldNameSortTypes =
		HashMapBuilder.put(
			Field.CREATE_DATE, Sort.LONG_TYPE
		).put(
			Field.MODIFIED_DATE, Sort.LONG_TYPE
		).put(
			KaleoInstanceTokenField.COMPLETION_DATE, Sort.LONG_TYPE
		).build();

	@Reference
	private KaleoInstanceTokenLocalService _kaleoInstanceTokenLocalService;

	@Reference
	private KaleoLogLocalService _kaleoLogLocalService;

	@Reference
	private KaleoTaskInstanceTokenLocalService
		_kaleoTaskInstanceTokenLocalService;

	@Reference
	private KaleoTimerInstanceTokenLocalService
		_kaleoTimerInstanceTokenLocalService;

	@Reference
	private Staging _staging;

}