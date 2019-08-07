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
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoInstance;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTokenConstants;
import com.liferay.portal.workflow.kaleo.model.KaleoNode;
import com.liferay.portal.workflow.kaleo.service.KaleoNodeLocalService;
import com.liferay.portal.workflow.kaleo.service.base.KaleoInstanceTokenLocalServiceBaseImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceTokenQuery;

import java.io.Serializable;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Brian Wing Shun Chan
 */
@Component(
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken",
	service = AopService.class
)
public class KaleoInstanceTokenLocalServiceImpl
	extends KaleoInstanceTokenLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstanceToken addKaleoInstanceToken(
			long currentKaleoNodeId, long kaleoDefinitionVersionId,
			long kaleoInstanceId, long parentKaleoInstanceTokenId,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoInstanceTokenId = counterLocalService.increment();

		KaleoInstanceToken kaleoInstanceToken =
			kaleoInstanceTokenPersistence.create(kaleoInstanceTokenId);

		long groupId = _staging.getLiveGroupId(
			serviceContext.getScopeGroupId());

		kaleoInstanceToken.setGroupId(groupId);

		kaleoInstanceToken.setCompanyId(user.getCompanyId());
		kaleoInstanceToken.setUserId(user.getUserId());
		kaleoInstanceToken.setUserName(user.getFullName());
		kaleoInstanceToken.setCreateDate(now);
		kaleoInstanceToken.setModifiedDate(now);
		kaleoInstanceToken.setKaleoDefinitionVersionId(
			kaleoDefinitionVersionId);
		kaleoInstanceToken.setKaleoInstanceId(kaleoInstanceId);
		kaleoInstanceToken.setParentKaleoInstanceTokenId(
			parentKaleoInstanceTokenId);

		if (currentKaleoNodeId > 0) {
			setCurrentKaleoNode(kaleoInstanceToken, currentKaleoNodeId);
		}

		kaleoInstanceToken.setClassName(
			(String)workflowContext.get(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_NAME));

		if (workflowContext.containsKey(
				WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)) {

			kaleoInstanceToken.setClassPK(
				GetterUtil.getLong(
					(String)workflowContext.get(
						WorkflowConstants.CONTEXT_ENTRY_CLASS_PK)));
		}

		kaleoInstanceToken.setCompleted(false);

		kaleoInstanceTokenPersistence.update(kaleoInstanceToken);

		return kaleoInstanceToken;
	}

	@Override
	public KaleoInstanceToken addKaleoInstanceToken(
			long parentKaleoInstanceTokenId,
			Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstanceToken parentKaleoInstanceToken =
			kaleoInstanceTokenPersistence.findByPrimaryKey(
				parentKaleoInstanceTokenId);

		return kaleoInstanceTokenLocalService.addKaleoInstanceToken(
			parentKaleoInstanceToken.getCurrentKaleoNodeId(),
			parentKaleoInstanceToken.getKaleoDefinitionVersionId(),
			parentKaleoInstanceToken.getKaleoInstanceId(),
			parentKaleoInstanceToken.getKaleoInstanceTokenId(), workflowContext,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstanceToken completeKaleoInstanceToken(
			long kaleoInstanceTokenId)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoInstanceTokenPersistence.findByPrimaryKey(
				kaleoInstanceTokenId);

		kaleoInstanceToken.setCompleted(true);
		kaleoInstanceToken.setCompletionDate(new Date());

		kaleoInstanceTokenPersistence.update(kaleoInstanceToken);

		return kaleoInstanceToken;
	}

	@Override
	public void deleteCompanyKaleoInstanceTokens(long companyId) {
		for (KaleoInstanceToken kaleoInstanceToken :
				kaleoInstanceTokenPersistence.findByCompanyId(companyId)) {

			kaleoInstanceTokenLocalService.deleteKaleoInstanceToken(
				kaleoInstanceToken);
		}
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoInstanceTokens(
		long kaleoDefinitionVersionId) {

		for (KaleoInstanceToken kaleoInstanceToken :
				kaleoInstanceTokenPersistence.findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId)) {

			kaleoInstanceTokenLocalService.deleteKaleoInstanceToken(
				kaleoInstanceToken);
		}
	}

	@Override
	public void deleteKaleoInstanceKaleoInstanceTokens(long kaleoInstanceId) {
		for (KaleoInstanceToken kaleoInstanceToken :
				kaleoInstanceTokenPersistence.findByKaleoInstanceId(
					kaleoInstanceId)) {

			kaleoInstanceTokenLocalService.deleteKaleoInstanceToken(
				kaleoInstanceToken);
		}
	}

	@Override
	public List<KaleoInstanceToken> getKaleoInstanceTokens(
		long parentKaleoInstanceTokenId, Date completionDate,
		ServiceContext serviceContext) {

		return kaleoInstanceTokenPersistence.findByC_PKITI_CD(
			serviceContext.getCompanyId(), parentKaleoInstanceTokenId,
			completionDate);
	}

	@Override
	public List<KaleoInstanceToken> getKaleoInstanceTokens(
		long parentKaleoInstanceTokenId, ServiceContext serviceContext) {

		return kaleoInstanceTokenPersistence.findByC_PKITI(
			serviceContext.getCompanyId(), parentKaleoInstanceTokenId);
	}

	@Override
	public int getKaleoInstanceTokensCount(
		long parentKaleoInstanceTokenId, Date completionDate,
		ServiceContext serviceContext) {

		return kaleoInstanceTokenPersistence.countByC_PKITI_CD(
			serviceContext.getCompanyId(), parentKaleoInstanceTokenId,
			completionDate);
	}

	@Override
	public int getKaleoInstanceTokensCount(
		long parentKaleoInstanceTokenId, ServiceContext serviceContext) {

		return kaleoInstanceTokenPersistence.countByC_PKITI(
			serviceContext.getCompanyId(), parentKaleoInstanceTokenId);
	}

	@Override
	public KaleoInstanceToken getRootKaleoInstanceToken(
			long kaleoInstanceId, Map<String, Serializable> workflowContext,
			ServiceContext serviceContext)
		throws PortalException {

		KaleoInstance kaleoInstance = kaleoInstancePersistence.findByPrimaryKey(
			kaleoInstanceId);

		long rootKaleoInstanceTokenId =
			kaleoInstance.getRootKaleoInstanceTokenId();

		if (rootKaleoInstanceTokenId > 0) {
			return kaleoInstanceTokenPersistence.findByPrimaryKey(
				rootKaleoInstanceTokenId);
		}

		// Kaleo instance token

		KaleoInstanceToken kaleoInstanceToken =
			kaleoInstanceTokenLocalService.addKaleoInstanceToken(
				0, kaleoInstance.getKaleoDefinitionVersionId(),
				kaleoInstance.getKaleoInstanceId(),
				KaleoInstanceTokenConstants.
					PARENT_KALEO_INSTANCE_TOKEN_ID_DEFAULT,
				workflowContext, serviceContext);

		// Kaleo instance

		kaleoInstance.setRootKaleoInstanceTokenId(
			kaleoInstanceToken.getKaleoInstanceTokenId());

		kaleoInstancePersistence.update(kaleoInstance);

		return kaleoInstanceToken;
	}

	@Override
	public Hits search(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String currentKaleoNodeName,
		String kaleoDefinitionName, Boolean completed, int start, int end,
		Sort[] sorts, ServiceContext serviceContext) {

		try {
			KaleoInstanceTokenQuery kaleoInstanceTokenQuery =
				new KaleoInstanceTokenQuery(serviceContext);

			kaleoInstanceTokenQuery.setAssetDescription(assetDescription);
			kaleoInstanceTokenQuery.setAssetTitle(assetTitle);
			kaleoInstanceTokenQuery.setClassName(assetClassName);
			kaleoInstanceTokenQuery.setCompleted(completed);
			kaleoInstanceTokenQuery.setCurrentKaleoNodeName(
				currentKaleoNodeName);
			kaleoInstanceTokenQuery.setKaleoDefinitionName(kaleoDefinitionName);
			kaleoInstanceTokenQuery.setUserId(userId);

			Indexer<KaleoInstanceToken> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					KaleoInstanceToken.class);

			SearchContext searchContext = buildSearchContext(
				kaleoInstanceTokenQuery, start, end, sorts, serviceContext);

			return indexer.search(searchContext);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	@Override
	public int searchCount(
		Long userId, String assetClassName, String assetTitle,
		String assetDescription, String currentKaleoNodeName,
		String kaleoDefinitionName, Boolean completed,
		ServiceContext serviceContext) {

		KaleoInstanceTokenQuery kaleoInstanceTokenQuery =
			new KaleoInstanceTokenQuery(serviceContext);

		kaleoInstanceTokenQuery.setAssetDescription(assetDescription);
		kaleoInstanceTokenQuery.setAssetTitle(assetTitle);
		kaleoInstanceTokenQuery.setClassName(assetClassName);
		kaleoInstanceTokenQuery.setCurrentKaleoNodeName(currentKaleoNodeName);
		kaleoInstanceTokenQuery.setCompleted(completed);
		kaleoInstanceTokenQuery.setKaleoDefinitionName(kaleoDefinitionName);
		kaleoInstanceTokenQuery.setUserId(userId);

		try {
			Indexer<KaleoInstanceToken> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					KaleoInstanceToken.class);

			SearchContext searchContext = buildSearchContext(
				kaleoInstanceTokenQuery, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null, serviceContext);

			return (int)indexer.searchCount(searchContext);
		}
		catch (SearchException se) {
			throw new SystemException(se);
		}
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoInstanceToken updateKaleoInstanceToken(
			long kaleoInstanceTokenId, long currentKaleoNodeId)
		throws PortalException {

		KaleoInstanceToken kaleoInstanceToken =
			kaleoInstanceTokenPersistence.findByPrimaryKey(
				kaleoInstanceTokenId);

		setCurrentKaleoNode(kaleoInstanceToken, currentKaleoNodeId);

		return kaleoInstanceTokenPersistence.update(kaleoInstanceToken);
	}

	protected SearchContext buildSearchContext(
		KaleoInstanceTokenQuery kaleoInstanceTokenQuery, int start, int end,
		Sort[] sorts, ServiceContext serviceContext) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			"kaleoInstanceTokenQuery", kaleoInstanceTokenQuery);
		searchContext.setCompanyId(kaleoInstanceTokenQuery.getCompanyId());
		searchContext.setEnd(end);
		searchContext.setStart(start);

		if (sorts != null) {
			searchContext.setSorts(sorts);
		}

		searchContext.setUserId(serviceContext.getUserId());

		return searchContext;
	}

	protected void setCurrentKaleoNode(
			KaleoInstanceToken kaleoInstanceToken, long currentKaleoNodeId)
		throws PortalException {

		kaleoInstanceToken.setCurrentKaleoNodeId(currentKaleoNodeId);

		KaleoNode currentKaleoNode = _kaleoNodeLocalService.getKaleoNode(
			currentKaleoNodeId);

		kaleoInstanceToken.setCurrentKaleoNodeName(currentKaleoNode.getName());
	}

	@Reference
	private KaleoNodeLocalService _kaleoNodeLocalService;

	@Reference
	private Staging _staging;

}