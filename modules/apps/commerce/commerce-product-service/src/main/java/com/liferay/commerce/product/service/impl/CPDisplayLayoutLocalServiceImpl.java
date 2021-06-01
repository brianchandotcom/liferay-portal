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

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPDisplayLayoutEntryException;
import com.liferay.commerce.product.exception.CPDisplayLayoutLayoutUuidException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDisplayLayout;
import com.liferay.commerce.product.service.base.CPDisplayLayoutLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marco Leo
 * @author Alessio Antonio Rendina
 */
public class CPDisplayLayoutLocalServiceImpl
	extends CPDisplayLayoutLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDisplayLayout addCPDisplayLayout(
			long userId, long groupId, Class<?> clazz, long classPK,
			String layoutUuid)
		throws PortalException {

		validate(classPK, layoutUuid);

		long classNameId = classNameLocalService.getClassNameId(clazz);

		CPDisplayLayout oldCPDisplayLayout =
			cpDisplayLayoutPersistence.fetchByG_C_C(
				groupId, classNameId, classPK);

		if ((clazz == CPDefinition.class) &&
			cpDefinitionLocalService.isVersionable(classPK)) {

			try {
				CPDefinition newCPDefinition =
					cpDefinitionLocalService.copyCPDefinition(classPK);

				classPK = newCPDefinition.getCPDefinitionId();
			}
			catch (PortalException portalException) {
				throw new SystemException(portalException);
			}

			oldCPDisplayLayout = cpDisplayLayoutPersistence.fetchByG_C_C(
				groupId, classNameId, classPK);
		}

		if (oldCPDisplayLayout != null) {
			oldCPDisplayLayout.setLayoutUuid(layoutUuid);

			return cpDisplayLayoutPersistence.update(oldCPDisplayLayout);
		}

		long cpDisplayLayoutId = counterLocalService.increment();

		CPDisplayLayout cpDisplayLayout = createCPDisplayLayout(
			cpDisplayLayoutId);

		cpDisplayLayout.setGroupId(groupId);

		User user = userLocalService.getUser(userId);

		cpDisplayLayout.setCompanyId(user.getCompanyId());

		cpDisplayLayout.setClassNameId(classNameId);
		cpDisplayLayout.setClassPK(classPK);
		cpDisplayLayout.setLayoutUuid(layoutUuid);

		return cpDisplayLayoutPersistence.update(cpDisplayLayout);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	public CPDisplayLayout deleteCPDisplayLayout(Class<?> clazz, long classPK) {
		try {
			if ((clazz == CPDefinition.class) &&
				cpDefinitionLocalService.isVersionable(classPK)) {

				cpDefinitionLocalService.copyCPDefinition(classPK);
			}
		}
		catch (PortalException portalException) {
			throw new SystemException(portalException);
		}

		cpDisplayLayoutLocalService.deleteCPDisplayLayouts(clazz, classPK);

		return null;
	}

	@Override
	public void deleteCPDisplayLayouts(Class<?> clazz, long classPK) {
		List<CPDisplayLayout> cpDisplayLayouts =
			cpDisplayLayoutPersistence.findByC_C(
				classNameLocalService.getClassNameId(clazz), classPK);

		for (CPDisplayLayout cpDisplayLayout : cpDisplayLayouts) {
			cpDisplayLayoutLocalService.deleteCPDisplayLayout(cpDisplayLayout);
		}
	}

	@Override
	public CPDisplayLayout fetchCPDisplayLayout(
		long groupId, Class<?> clazz, long classPK) {

		return cpDisplayLayoutPersistence.fetchByG_C_C(
			groupId, classNameLocalService.getClassNameId(clazz), classPK);
	}

	@Override
	public List<CPDisplayLayout> fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
		long groupId, String layoutUuid) {

		return cpDisplayLayoutPersistence.findByG_L(groupId, layoutUuid);
	}

	@Override
	public List<CPDisplayLayout> fetchCPDisplayLayoutByGroupIdAndLayoutUuid(
		long groupId, String layoutUuid, int start, int end) {

		return cpDisplayLayoutPersistence.findByG_L(
			groupId, layoutUuid, start, end);
	}

	@Override
	public BaseModelSearchResult<CPDisplayLayout> searchCPDisplayLayout(
			long companyId, long groupId, String className, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, className, keywords, start, end, sort);

		return searchCPDisplayLayout(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDisplayLayout updateCPDisplayLayout(
			long cpDisplayLayoutId, long classPK, String layoutUuid)
		throws PortalException {

		CPDisplayLayout cpDisplayLayout =
			cpDisplayLayoutPersistence.findByPrimaryKey(cpDisplayLayoutId);

		validate(cpDisplayLayout.getClassPK(), layoutUuid);

		cpDisplayLayout.setClassPK(classPK);
		cpDisplayLayout.setLayoutUuid(layoutUuid);

		return cpDisplayLayoutPersistence.update(cpDisplayLayout);
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String className, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				"entryModelClassName", className
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).put(
				"searchFilterEnabled", true
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CPDisplayLayout> getCPDisplayLayouts(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPDisplayLayout> cpDisplayLayouts = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long cpDisplayLayoutId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPDisplayLayout cpDisplayLayout = fetchCPDisplayLayout(
				cpDisplayLayoutId);

			if (cpDisplayLayout == null) {
				Indexer<CPDisplayLayout> indexer =
					IndexerRegistryUtil.getIndexer(CPDisplayLayout.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpDisplayLayout != null) {
				cpDisplayLayouts.add(cpDisplayLayout);
			}
		}

		return cpDisplayLayouts;
	}

	protected BaseModelSearchResult<CPDisplayLayout> searchCPDisplayLayout(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPDisplayLayout> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPDisplayLayout.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPDisplayLayout> cpDisplayLayouts = getCPDisplayLayouts(hits);

			if (cpDisplayLayouts != null) {
				return new BaseModelSearchResult<>(
					cpDisplayLayouts, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(long classPK, String layoutUuid)
		throws PortalException {

		if (classPK <= 0) {
			throw new CPDisplayLayoutEntryException();
		}

		if (Validator.isNull(layoutUuid)) {
			throw new CPDisplayLayoutLayoutUuidException();
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

}