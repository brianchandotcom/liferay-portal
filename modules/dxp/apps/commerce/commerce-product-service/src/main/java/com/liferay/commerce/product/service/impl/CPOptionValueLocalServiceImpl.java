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

import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueLocalServiceBaseImpl;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.BaseModelSearchResult;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionValueLocalServiceImpl
	extends CPOptionValueLocalServiceBaseImpl {

	public static final String[] SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, String name, Map<Locale, String> titleMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpOptionValueId = counterLocalService.increment();

		CPOptionValue cpOptionValue = cpOptionValuePersistence.create(
			cpOptionValueId);

		cpOptionValue.setUuid(serviceContext.getUuid());
		cpOptionValue.setGroupId(groupId);
		cpOptionValue.setCompanyId(user.getCompanyId());
		cpOptionValue.setUserId(user.getUserId());
		cpOptionValue.setUserName(user.getFullName());
		cpOptionValue.setCPOptionId(cpOptionId);
		cpOptionValue.setName(name);
		cpOptionValue.setTitleMap(titleMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValuePersistence.update(cpOptionValue);

		return cpOptionValue;
	}

	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		// Commerce product option value

		cpOptionValuePersistence.remove(cpOptionValue);

		// Expando

		expandoRowLocalService.deleteRows(cpOptionValue.getCPOptionValueId());

		return cpOptionValue;
	}

	@Override
	public CPOptionValue deleteCPOptionValue(long cpOptionValueId)
		throws PortalException {

		CPOptionValue cpOptionValue = cpOptionValuePersistence.findByPrimaryKey(
			cpOptionValueId);

		return cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
	}

	@Override
	public void deleteCPOptionValues(long cpOptionId) throws PortalException {
		List<CPOptionValue> cpOptionValues =
			cpOptionValueLocalService.getCPOptionValues(
				cpOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPOptionValue cpOptionValue : cpOptionValues) {
			cpOptionValueLocalService.deleteCPOptionValue(cpOptionValue);
		}
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end) {

		return cpOptionValuePersistence.findByCPOptionId(
			cpOptionId, start, end);
	}

	@Override
	public List<CPOptionValue> getCPOptionValues(
		long cpOptionId, int start, int end,
		OrderByComparator<CPOptionValue> orderByComparator) {

		return cpOptionValuePersistence.findByCPOptionId(
			cpOptionId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionValuesCount(long cpOptionId) {
		return cpOptionValuePersistence.countByCPOptionId(cpOptionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPOptionValue> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(CPOptionValue.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			long companyId, long groupId, long cpOptionId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpOptionId, keywords, start, end, sort);

		return searchCPOptions(searchContext);
	}

	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, String name, Map<Locale, String> titleMap,
			int priority, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		CPOptionValue cpOptionValue = cpOptionValuePersistence.findByPrimaryKey(
			cpOptionValueId);

		cpOptionValue.setName(name);
		cpOptionValue.setTitleMap(titleMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValuePersistence.update(cpOptionValue);

		return cpOptionValue;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpOptionId, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put(Field.CONTENT, keywords);
		attributes.put("CPOptionId", cpOptionId);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected BaseModelSearchResult<CPOptionValue> searchCPOptions(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPOptionValue> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOptionValue.class);

		List<CPOptionValue> cpOptionValues = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, SELECTED_FIELD_NAMES);

			Document[] documents = hits.getDocs();

			for (Document document : documents) {
				long classPK = GetterUtil.getLong(
					document.get(Field.ENTRY_CLASS_PK));

				CPOptionValue cpOptionValue = getCPOptionValue(classPK);

				cpOptionValues.add(cpOptionValue);
			}

			if (cpOptionValues != null) {
				return new BaseModelSearchResult<>(
					cpOptionValues, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

}