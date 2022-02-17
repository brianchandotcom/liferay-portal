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

import com.liferay.commerce.product.exception.CPOptionValueKeyException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.model.CPOptionValue;
import com.liferay.commerce.product.service.base.CPOptionValueLocalServiceBaseImpl;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.SystemEventConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.FriendlyURLNormalizer;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Marco Leo
 */
public class CPOptionValueLocalServiceImpl
	extends CPOptionValueLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue addCPOptionValue(
			long cpOptionId, Map<Locale, String> nameMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		return cpOptionValueLocalService.addCPOptionValue(
			StringPool.BLANK, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue addCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			Map<Locale, String> nameMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		User user = userLocalService.getUser(serviceContext.getUserId());

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		key = _friendlyURLNormalizer.normalize(key);

		validate(0, cpOptionId, key);

		long cpOptionValueId = counterLocalService.increment();

		CPOptionValue cpOptionValue = cpOptionValuePersistence.create(
			cpOptionValueId);

		cpOptionValue.setExternalReferenceCode(externalReferenceCode);
		cpOptionValue.setCompanyId(user.getCompanyId());
		cpOptionValue.setUserId(user.getUserId());
		cpOptionValue.setUserName(user.getFullName());
		cpOptionValue.setCPOptionId(cpOptionId);
		cpOptionValue.setNameMap(nameMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setKey(key);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValue = cpOptionValuePersistence.update(cpOptionValue);

		reindexCPOption(cpOptionId);

		return cpOptionValue;
	}

	@Override
	public CPOptionValue addOrUpdateCPOptionValue(
			String externalReferenceCode, long cpOptionId,
			Map<Locale, String> nameMap, double priority, String key,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CPOptionValue cpOptionValue = cpOptionValuePersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);

			if (cpOptionValue != null) {
				return cpOptionValueLocalService.updateCPOptionValue(
					cpOptionValue.getCPOptionValueId(), nameMap, priority, key,
					serviceContext);
			}
		}

		return cpOptionValueLocalService.addCPOptionValue(
			externalReferenceCode, cpOptionId, nameMap, priority, key,
			serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOptionValue deleteCPOptionValue(CPOptionValue cpOptionValue)
		throws PortalException {

		// Commerce product option value

		cpOptionValuePersistence.remove(cpOptionValue);

		// Expando

		_expandoRowLocalService.deleteRows(cpOptionValue.getCPOptionValueId());

		reindexCPOption(cpOptionValue.getCPOptionId());

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
	public CPOptionValue fetchByExternalReferenceCode(
		String externalReferenceCode, long companyId) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return cpOptionValuePersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CPOptionValue getCPOptionValue(long cpOptionId, String key)
		throws PortalException {

		return cpOptionValuePersistence.findByC_K(cpOptionId, key);
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
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	/**
	 * @param      companyId
	 * @param      groupId
	 * @param      cpOptionId
	 * @param      keywords
	 * @param      start
	 * @param      end
	 * @param      sort
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 *             #searchCPOptionValues(long, long, String, int, int, Sort[])}
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			long companyId, long groupId, long cpOptionId, String keywords,
			int start, int end, Sort sort)
		throws PortalException {

		return cpOptionValueLocalService.searchCPOptionValues(
			companyId, cpOptionId, keywords, start, end, new Sort[] {sort});
	}

	@Override
	public BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			long companyId, long cpOptionId, String keywords, int start,
			int end, Sort[] sorts)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, cpOptionId, keywords, start, end, sorts);

		return searchCPOptionValues(searchContext);
	}

	@Override
	public int searchCPOptionValuesCount(
			long companyId, long cpOptionId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, cpOptionId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return searchCPOptionValuesCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOptionValue updateCPOptionValue(
			long cpOptionValueId, Map<Locale, String> nameMap, double priority,
			String key, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option value

		CPOptionValue cpOptionValue = cpOptionValuePersistence.findByPrimaryKey(
			cpOptionValueId);

		key = _friendlyURLNormalizer.normalize(key);

		validate(
			cpOptionValue.getCPOptionValueId(), cpOptionValue.getCPOptionId(),
			key);

		cpOptionValue.setNameMap(nameMap);
		cpOptionValue.setPriority(priority);
		cpOptionValue.setKey(key);
		cpOptionValue.setExpandoBridgeAttributes(serviceContext);

		cpOptionValue = cpOptionValuePersistence.update(cpOptionValue);

		reindexCPOption(cpOptionValue.getCPOptionId());

		return cpOptionValue;
	}

	protected SearchContext buildSearchContext(
		long companyId, long cpOptionId, String keywords, int start, int end,
		Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				_FIELD_KEY, keywords
			).put(
				Field.CONTENT, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"CPOptionId", cpOptionId
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		if (sorts != null) {
			searchContext.setSorts(sorts);
		}

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CPOptionValue> getCPOptionValues(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPOptionValue> cpOptionValues = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpOptionValueId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPOptionValue cpOptionValue = fetchCPOptionValue(cpOptionValueId);

			if (cpOptionValue == null) {
				cpOptionValues = null;

				Indexer<CPOptionValue> indexer = IndexerRegistryUtil.getIndexer(
					CPOptionValue.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpOptionValues != null) {
				cpOptionValues.add(cpOptionValue);
			}
		}

		return cpOptionValues;
	}

	protected void reindexCPOption(long cpOptionId) throws PortalException {
		Indexer<CPOption> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOption.class);

		indexer.reindex(CPOption.class.getName(), cpOptionId);
	}

	protected BaseModelSearchResult<CPOptionValue> searchCPOptionValues(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPOptionValue> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOptionValue.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPOptionValue> cpOptionValues = getCPOptionValues(hits);

			if (cpOptionValues != null) {
				return new BaseModelSearchResult<>(
					cpOptionValues, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected int searchCPOptionValuesCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CPOptionValue> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOptionValue.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
	}

	protected void validate(long cpOptionValueId, long cpOptionId, String key)
		throws PortalException {

		CPOptionValue cpOptionValue = cpOptionValuePersistence.fetchByC_K(
			cpOptionId, key);

		if ((cpOptionValue != null) &&
			(cpOptionValue.getCPOptionValueId() != cpOptionValueId)) {

			throw new CPOptionValueKeyException();
		}
	}

	private static final String _FIELD_KEY = "key";

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

	@ServiceReference(type = FriendlyURLNormalizer.class)
	private FriendlyURLNormalizer _friendlyURLNormalizer;

}