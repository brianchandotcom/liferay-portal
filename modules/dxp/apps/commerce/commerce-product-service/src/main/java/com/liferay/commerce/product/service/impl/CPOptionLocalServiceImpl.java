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

import com.liferay.commerce.product.exception.CPOptionKeyException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.ResourceConstants;
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
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
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
public class CPOptionLocalServiceImpl extends CPOptionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOption addCPOption(
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, boolean facetable, boolean required,
			boolean skuContributor, String key, ServiceContext serviceContext)
		throws PortalException {

		// Commerce product option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, key);

		long cpOptionId = counterLocalService.increment();

		CPOption cpOption = cpOptionPersistence.create(cpOptionId);

		cpOption.setUuid(serviceContext.getUuid());
		cpOption.setGroupId(groupId);
		cpOption.setCompanyId(user.getCompanyId());
		cpOption.setUserId(user.getUserId());
		cpOption.setUserName(user.getFullName());
		cpOption.setTitleMap(titleMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setFacetable(facetable);
		cpOption.setRequired(required);
		cpOption.setSkuContributor(skuContributor);
		cpOption.setKey(key);
		cpOption.setExpandoBridgeAttributes(serviceContext);

		cpOptionPersistence.update(cpOption);

		// Resources

		resourceLocalService.addModelResources(cpOption, serviceContext);

		return cpOption;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPOption deleteCPOption(CPOption cpOption) throws PortalException {

		// Commerce product option

		cpOptionPersistence.remove(cpOption);

		// Commerce product option values

		cpOptionValueLocalService.deleteCPOptionValues(
			cpOption.getCPOptionId());

		// Resources

		resourceLocalService.deleteResource(
			cpOption.getCompanyId(), CPOption.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL, cpOption.getCPOptionId());

		// Expando

		expandoRowLocalService.deleteRows(cpOption.getCPOptionId());

		return cpOption;
	}

	@Override
	public CPOption deleteCPOption(long cpOptionId) throws PortalException {
		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		return cpOptionLocalService.deleteCPOption(cpOption);
	}

	@Override
	public void deleteCPOptions(long groupId) throws PortalException {
		List<CPOption> cpOptions = cpOptionPersistence.findByGroupId(groupId);

		for (CPOption cpOption : cpOptions) {
			cpOptionLocalService.deleteCPOption(cpOption);
		}
	}

	@Override
	public CPOption fetchCPOption(long groupId, String key)
		throws PortalException {

		return cpOptionPersistence.fetchByG_K(groupId, key);
	}

	@Override
	public CPOption getCPOption(long groupId, String key)
		throws PortalException {

		return cpOptionPersistence.findByG_K(groupId, key);
	}

	@Override
	public List<CPOption> getCPOptions(long groupId, int start, int end) {
		return cpOptionPersistence.findByGroupId(groupId, start, end);
	}

	@Override
	public List<CPOption> getCPOptions(
		long groupId, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {

		return cpOptionPersistence.findByGroupId(
			groupId, start, end, orderByComparator);
	}

	@Override
	public int getCPOptionsCount(long groupId) {
		return cpOptionPersistence.countByGroupId(groupId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPOption> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
				CPOption.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPOption> searchCPOptions(
			long companyId, long groupId, String keywords, int start, int end,
			Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return searchCPOptions(searchContext);
	}

	@Override
	public CPOption setFacetable(long cpOptionId, boolean facetable)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		cpOption.setFacetable(facetable);

		cpOptionPersistence.update(cpOption);

		return cpOption;
	}

	@Override
	public CPOption setRequired(long cpOptionId, boolean required)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		cpOption.setRequired(required);

		cpOptionPersistence.update(cpOption);

		return cpOption;
	}

	@Override
	public CPOption setSkuContributor(long cpOptionId, boolean skuContributor)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		cpOption.setSkuContributor(skuContributor);

		cpOptionPersistence.update(cpOption);

		return cpOption;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOption updateCPOption(
			long cpOptionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, ServiceContext serviceContext)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(cpOption.getCPOptionId(), cpOption.getGroupId(), key);

		cpOption.setTitleMap(titleMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setFacetable(facetable);
		cpOption.setRequired(required);
		cpOption.setSkuContributor(skuContributor);
		cpOption.setKey(key);
		cpOption.setExpandoBridgeAttributes(serviceContext);

		cpOptionPersistence.update(cpOption);

		return cpOption;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put(Field.DESCRIPTION, keywords);
		attributes.put(Field.CONTENT, keywords);
		attributes.put(_FIELD_KEY, keywords);
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

	protected List<CPOption> getCPOptions(Hits hits) throws PortalException {
		List<Document> documents = hits.toList();

		List<CPOption> cpOptions = new ArrayList<>(documents.size());

		for (Document document : documents) {
			long cpOptionId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPOption cpOption = fetchCPOption(cpOptionId);

			if (cpOption == null) {
				cpOptions = null;

				Indexer<CPOption> indexer = IndexerRegistryUtil.getIndexer(
					CPOption.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpOptions != null) {
				cpOptions.add(cpOption);
			}
		}

		return cpOptions;
	}

	protected BaseModelSearchResult<CPOption> searchCPOptions(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPOption> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPOption.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPOption> cpOptions = getCPOptions(hits);

			if (cpOptions != null) {
				return new BaseModelSearchResult<>(cpOptions, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(long cpOptionId, long groupId, String key)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.fetchByG_K(groupId, key);

		if ((cpOption != null) && (cpOption.getCPOptionId() != cpOptionId)) {
			throw new CPOptionKeyException();
		}
	}

	private static final String _FIELD_KEY = "key";

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}