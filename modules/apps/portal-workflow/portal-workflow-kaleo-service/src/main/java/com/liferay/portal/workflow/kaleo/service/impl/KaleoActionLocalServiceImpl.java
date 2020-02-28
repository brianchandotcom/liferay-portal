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

import com.liferay.portal.aop.AopService;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.workflow.kaleo.definition.Action;
import com.liferay.portal.workflow.kaleo.definition.ExecutionType;
import com.liferay.portal.workflow.kaleo.definition.ScriptLanguage;
import com.liferay.portal.workflow.kaleo.model.KaleoAction;
import com.liferay.portal.workflow.kaleo.service.base.KaleoActionLocalServiceBaseImpl;

import java.io.Serializable;

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
	property = "model.class.name=com.liferay.portal.workflow.kaleo.model.KaleoAction",
	service = AopService.class
)
public class KaleoActionLocalServiceImpl
	extends KaleoActionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public KaleoAction addKaleoAction(
			String kaleoClassName, long kaleoClassPK, long kaleoDefinitionId,
			long kaleoDefinitionVersionId, String kaleoNodeName, Action action,
			ServiceContext serviceContext)
		throws PortalException {

		User user = userLocalService.getUser(serviceContext.getGuestOrUserId());
		Date now = new Date();

		long kaleoActionId = counterLocalService.increment();

		KaleoAction kaleoAction = kaleoActionPersistence.create(kaleoActionId);

		kaleoAction.setCompanyId(user.getCompanyId());
		kaleoAction.setUserId(user.getUserId());
		kaleoAction.setUserName(user.getFullName());
		kaleoAction.setCreateDate(now);
		kaleoAction.setModifiedDate(now);
		kaleoAction.setKaleoClassName(kaleoClassName);
		kaleoAction.setKaleoClassPK(kaleoClassPK);
		kaleoAction.setKaleoDefinitionId(kaleoDefinitionId);
		kaleoAction.setKaleoDefinitionVersionId(kaleoDefinitionVersionId);
		kaleoAction.setKaleoNodeName(kaleoNodeName);
		kaleoAction.setName(action.getName());
		kaleoAction.setDescription(action.getDescription());

		ExecutionType executionType = action.getExecutionType();

		kaleoAction.setExecutionType(executionType.getValue());

		kaleoAction.setScript(action.getScript());

		ScriptLanguage scriptLanguage = action.getScriptLanguage();

		kaleoAction.setScriptLanguage(scriptLanguage.getValue());

		kaleoAction.setScriptRequiredContexts(
			action.getScriptRequiredContexts());
		kaleoAction.setPriority(action.getPriority());

		return kaleoActionPersistence.update(kaleoAction);
	}

	@Override
	public void deleteCompanyKaleoActions(long companyId) {
		for (KaleoAction kaleoAction :
				kaleoActionPersistence.findByCompanyId(companyId)) {

			kaleoActionLocalService.deleteKaleoAction(kaleoAction);
		}
	}

	@Override
	public void deleteKaleoDefinitionVersionKaleoActions(
		long kaleoDefinitionVersionId) {

		for (KaleoAction kaleoAction :
				kaleoActionPersistence.findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId)) {

			kaleoActionLocalService.deleteKaleoAction(kaleoAction);
		}
	}

	@Override
	public List<KaleoAction> getKaleoActions(
		long companyId, String kaleoClassName, long kaleoClassPK) {

		return doSearch(
			companyId,
			HashMapBuilder.put(
				"kaleoClassName", (Serializable)kaleoClassName
			).put(
				"kaleoClassPK", kaleoClassPK
			).build());
	}

	@Override
	public List<KaleoAction> getKaleoActions(
		long companyId, String kaleoClassName, long kaleoClassPK,
		String executionType) {

		return doSearch(
			companyId,
			HashMapBuilder.put(
				"executionType", (Serializable)executionType
			).put(
				"kaleoClassName", kaleoClassName
			).put(
				"kaleoClassPK", kaleoClassPK
			).build());
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getKaleoActions(long, String, long)}
	 */
	@Deprecated
	@Override
	public List<KaleoAction> getKaleoActions(
		String kaleoClassName, long kaleoClassPK) {

		return kaleoActionPersistence.findByKCN_KCPK(
			kaleoClassName, kaleoClassPK);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link
	 *             #getKaleoActions(long, String, long, String)}
	 */
	@Deprecated
	@Override
	public List<KaleoAction> getKaleoActions(
		String kaleoClassName, long kaleoClassPK, String executionType) {

		return kaleoActionPersistence.findByKCN_KCPK_ET(
			kaleoClassName, kaleoClassPK, executionType);
	}

	protected SearchContext buildSearchContext(
		long companyId, Map<String, Serializable> searchAttributes) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(searchAttributes);
		searchContext.setCompanyId(companyId);

		return searchContext;
	}

	protected List<KaleoAction> doSearch(
		long companyId, Map<String, Serializable> searchAttributes) {

		try {
			Indexer<KaleoAction> indexer = IndexerRegistryUtil.getIndexer(
				KaleoAction.class.getName());

			Hits hits = indexer.search(
				buildSearchContext(companyId, searchAttributes));

			return Stream.of(
				hits.getDocs()
			).map(
				document -> GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK))
			).map(
				kaleoActionPersistence::fetchByPrimaryKey
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

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoActionLocalServiceImpl.class);

}