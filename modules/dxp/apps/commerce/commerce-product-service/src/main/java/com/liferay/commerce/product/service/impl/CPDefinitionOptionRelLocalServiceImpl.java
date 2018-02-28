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

import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelLocalServiceBaseImpl;
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
public class CPDefinitionOptionRelLocalServiceImpl
	extends CPDefinitionOptionRelLocalServiceBaseImpl {

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, boolean importOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		CPOption cpOption = cpOptionLocalService.getCPOption(cpOptionId);

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, cpOption.getTitleMap(),
			cpOption.getDescriptionMap(), cpOption.getDDMFormFieldTypeName(), 0,
			cpOption.getFacetable(), cpOption.isRequired(),
			cpOption.getSkuContributor(), importOptionValue, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			double priority, boolean facetable, boolean required,
			boolean skuContributor, boolean importOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option rel

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionOptionRelId = counterLocalService.increment();

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.create(cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setUuid(serviceContext.getUuid());
		cpDefinitionOptionRel.setGroupId(groupId);
		cpDefinitionOptionRel.setCompanyId(user.getCompanyId());
		cpDefinitionOptionRel.setUserId(user.getUserId());
		cpDefinitionOptionRel.setUserName(user.getFullName());
		cpDefinitionOptionRel.setCPDefinitionId(cpDefinitionId);
		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setTitleMap(titleMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setRequired(required);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		// Commerce product definition option value rels

		if (importOptionValue) {
			cpDefinitionOptionValueRelLocalService.importCPDefinitionOptionRels(
				cpDefinitionOptionRelId, serviceContext);
		}

		// Commerce product definition

		reindexCPDefinition(cpDefinitionId);

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, true, serviceContext);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
			CPDefinitionOptionRel cpDefinitionOptionRel)
		throws PortalException {

		// Commerce product definition option value rels

		cpDefinitionOptionValueRelLocalService.
			deleteCPDefinitionOptionValueRels(
				cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		// Commerce product definition option rel

		cpDefinitionOptionRelPersistence.remove(cpDefinitionOptionRel);

		// Expando

		expandoRowLocalService.deleteRows(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel.getCPDefinitionId());

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel deleteCPDefinitionOptionRel(
			long cpDefinitionOptionRelId)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		return cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
			cpDefinitionOptionRel);
	}

	@Override
	public void deleteCPDefinitionOptionRels(long cpDefinitionId)
		throws PortalException {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelLocalService.getCPDefinitionOptionRels(
				cpDefinitionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			cpDefinitionOptionRelLocalService.deleteCPDefinitionOptionRel(
				cpDefinitionOptionRel);
		}
	}

	@Override
	public int getCPDefinitionOptionRelCount(
		long cpDefinitionId, boolean skuContributor) {

		return cpDefinitionOptionRelPersistence.countByC_SC(
			cpDefinitionId, skuContributor);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId) {

		return cpDefinitionOptionRelPersistence.findByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, boolean skuContributor) {

		return cpDefinitionOptionRelPersistence.findByC_SC(
			cpDefinitionId, skuContributor);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end) {

		return cpDefinitionOptionRelPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end);
	}

	@Override
	public List<CPDefinitionOptionRel> getCPDefinitionOptionRels(
		long cpDefinitionId, int start, int end,
		OrderByComparator<CPDefinitionOptionRel> orderByComparator) {

		return cpDefinitionOptionRelPersistence.findByCPDefinitionId(
			cpDefinitionId, start, end, orderByComparator);
	}

	@Override
	public int getCPDefinitionOptionRelsCount(long cpDefinitionId) {
		return cpDefinitionOptionRelPersistence.countByCPDefinitionId(
			cpDefinitionId);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPDefinitionOptionRel> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CPDefinitionOptionRel.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionRel>
			searchCPDefinitionOptionRels(
				long companyId, long groupId, long cpDefinitionId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionId, keywords, start, end, sort);

		return searchCPOptions(searchContext);
	}

	@Override
	public CPDefinitionOptionRel setFacetable(
			long cpDefinitionOptionRelId, boolean facetable)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setFacetable(facetable);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel setRequired(
			long cpDefinitionOptionRelId, boolean required)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setRequired(required);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		return cpDefinitionOptionRel;
	}

	@Override
	public CPDefinitionOptionRel setSkuContributor(
			long cpDefinitionOptionRelId, boolean skuContributor)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setSkuContributor(skuContributor);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		return cpDefinitionOptionRel;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
			long cpDefinitionOptionRelId, long cpOptionId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, double priority, boolean facetable,
			boolean required, boolean skuContributor,
			ServiceContext serviceContext)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setTitleMap(titleMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setRequired(required);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRelPersistence.update(cpDefinitionOptionRel);

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel.getCPDefinitionId());

		return cpDefinitionOptionRel;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put(Field.CONTENT, keywords);
		attributes.put("CPDefinitionId", cpDefinitionId);
		attributes.put("params", params);

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setStart(start);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

		if (Validator.isNotNull(keywords)) {
			searchContext.setKeywords(keywords);
		}

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		if (sort != null) {
			searchContext.setSorts(sort);
		}

		return searchContext;
	}

	protected List<CPDefinitionOptionRel> getCPDefinitionOptionRels(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPDefinitionOptionRel> cpDefinitionOptionRels = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long cpDefinitionOptionRelId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPDefinitionOptionRel cpDefinitionOptionRel =
				fetchCPDefinitionOptionRel(cpDefinitionOptionRelId);

			if (cpDefinitionOptionRel == null) {
				cpDefinitionOptionRels = null;

				Indexer<CPDefinitionOptionRel> indexer =
					IndexerRegistryUtil.getIndexer(CPDefinitionOptionRel.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpDefinitionOptionRels != null) {
				cpDefinitionOptionRels.add(cpDefinitionOptionRel);
			}
		}

		return cpDefinitionOptionRels;
	}

	protected void reindexCPDefinition(long cpDefinitionId)
		throws PortalException {

		Indexer<CPDefinition> indexer = IndexerRegistryUtil.nullSafeGetIndexer(
			CPDefinition.class);

		indexer.reindex(CPDefinition.class.getName(), cpDefinitionId);
	}

	protected BaseModelSearchResult<CPDefinitionOptionRel> searchCPOptions(
			SearchContext searchContext)
		throws PortalException {

		Indexer<CPDefinitionOptionRel> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPDefinitionOptionRel.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPDefinitionOptionRel> cpDefinitionOptionRels =
				getCPDefinitionOptionRels(hits);

			if (cpDefinitionOptionRels != null) {
				return new BaseModelSearchResult<>(
					cpDefinitionOptionRels, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}