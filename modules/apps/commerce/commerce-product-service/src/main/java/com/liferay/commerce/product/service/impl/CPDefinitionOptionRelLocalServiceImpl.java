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

import com.liferay.commerce.product.configuration.CPOptionConfiguration;
import com.liferay.commerce.product.constants.CPConstants;
import com.liferay.commerce.product.exception.CPDefinitionOptionRelPriceTypeException;
import com.liferay.commerce.product.exception.CPDefinitionOptionSKUContributorException;
import com.liferay.commerce.product.exception.DuplicateCPDefinitionOptionRelKeyException;
import com.liferay.commerce.product.model.CPDefinition;
import com.liferay.commerce.product.model.CPDefinitionOptionRel;
import com.liferay.commerce.product.model.CPDefinitionOptionValueRel;
import com.liferay.commerce.product.model.CPInstance;
import com.liferay.commerce.product.model.CPInstanceOptionValueRel;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPDefinitionOptionRelLocalServiceBaseImpl;
import com.liferay.commerce.product.util.JsonHelper;
import com.liferay.expando.kernel.service.ExpandoRowLocalService;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.model.SystemEventConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.module.configuration.ConfigurationException;
import com.liferay.portal.kernel.module.configuration.ConfigurationProvider;
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
import com.liferay.portal.kernel.security.auth.PrincipalThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.LinkedHashMapBuilder;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Marco Leo
 * @author Igor Beslic
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
			cpDefinitionId, cpOptionId, cpOption.getNameMap(),
			cpOption.getDescriptionMap(), cpOption.getDDMFormFieldTypeName(), 0,
			cpOption.isFacetable(), cpOption.isRequired(),
			cpOption.isSkuContributor(), importOptionValue, serviceContext);
	}

	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			double priority, boolean facetable, boolean required,
			boolean skuContributor, boolean importOptionValue,
			ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionRelLocalService.addCPDefinitionOptionRel(
			cpDefinitionId, cpOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, required, skuContributor,
			importOptionValue, null, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionRel addCPDefinitionOptionRel(
			long cpDefinitionId, long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			double priority, boolean facetable, boolean required,
			boolean skuContributor, boolean importOptionValue, String priceType,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product definition option rel

		_validateDDMFormFieldTypeName(ddmFormFieldTypeName, skuContributor);

		CPOption cpOption = cpOptionLocalService.getCPOption(cpOptionId);

		_validateCPDefinitionOptionKey(cpDefinitionId, cpOption.getKey());

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		long cpDefinitionOptionRelId = counterLocalService.increment();

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.create(cpDefinitionOptionRelId);

		_validatePriceType(cpDefinitionOptionRel, priceType);

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionId, serviceContext.getRequest())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(cpDefinitionId);

			cpDefinitionId = newCPDefinition.getCPDefinitionId();

			HttpServletRequest httpServletRequest = serviceContext.getRequest();

			httpServletRequest.setAttribute(
				"versionable#" + cpDefinitionId, Boolean.FALSE);
		}

		cpDefinitionOptionRel.setGroupId(groupId);
		cpDefinitionOptionRel.setCompanyId(user.getCompanyId());
		cpDefinitionOptionRel.setUserId(user.getUserId());
		cpDefinitionOptionRel.setUserName(user.getFullName());
		cpDefinitionOptionRel.setCPDefinitionId(cpDefinitionId);
		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setNameMap(nameMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setRequired(required);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setKey(cpOption.getKey());
		cpDefinitionOptionRel.setPriceType(priceType);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.update(
			cpDefinitionOptionRel);

		// Commerce product definition option value rels

		if (importOptionValue) {
			cpDefinitionOptionValueRelLocalService.importCPDefinitionOptionRels(
				cpDefinitionOptionRelId, serviceContext);
		}

		// Commerce product instances

		cpInstanceLocalService.inactivateIncompatibleCPInstances(
			user.getUserId(), cpDefinitionId);

		_updateCPDefinitionIgnoreSKUCombinations(
			cpDefinitionId, serviceContext);

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

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionOptionRel.getCPDefinitionId())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPOptionId());
		}

		// Commerce product definition option value rels

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
					QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			cpDefinitionOptionValueRelPersistence.remove(
				cpDefinitionOptionValueRel);

			_expandoRowLocalService.deleteRows(
				cpDefinitionOptionValueRel.getCPDefinitionOptionValueRelId());
		}

		// Commerce product definition option rel

		cpDefinitionOptionRelPersistence.remove(cpDefinitionOptionRel);

		// Expando

		_expandoRowLocalService.deleteRows(
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		// Commerce product instances

		cpInstanceLocalService.inactivateCPDefinitionOptionRelCPInstances(
			PrincipalThreadLocal.getUserId(),
			cpDefinitionOptionRel.getCPDefinitionId(),
			cpDefinitionOptionRel.getCPDefinitionOptionRelId());

		_updateCPDefinitionIgnoreSKUCombinations(
			cpDefinitionOptionRel.getCPDefinitionId(), new ServiceContext());

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
	public CPDefinitionOptionRel fetchCPDefinitionOptionRel(
		long cpDefinitionId, long cpOptionId) {

		return cpDefinitionOptionRelPersistence.fetchByC_C(
			cpDefinitionId, cpOptionId);
	}

	@Override
	public CPDefinitionOptionRel fetchCPDefinitionOptionRelByKey(
		long cpDefinitionId, String key) {

		return cpDefinitionOptionRelPersistence.fetchByC_K(cpDefinitionId, key);
	}

	@Override
	public Map<Long, List<Long>>
			getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
				long cpDefinitionId, boolean skuContributorsOnly, String json)
		throws PortalException {

		if (_jsonHelper.isEmpty(json)) {
			return Collections.emptyMap();
		}

		Map<Long, List<Long>>
			cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds =
				new HashMap<>();

		JSONArray jsonArray = _jsonFactory.createJSONArray();

		if (_jsonHelper.isArray(json)) {
			jsonArray = _jsonFactory.createJSONArray(json);
		}
		else {
			jsonArray.put(_jsonFactory.createJSONObject(json));
		}

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject jsonObject = jsonArray.getJSONObject(i);

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelLocalService.
					fetchCPDefinitionOptionRelByKey(
						cpDefinitionId, jsonObject.getString("key"));

			if ((cpDefinitionOptionRel == null) ||
				(skuContributorsOnly &&
				 !cpDefinitionOptionRel.isSkuContributor())) {

				continue;
			}

			JSONArray valueJSONArray = _jsonHelper.getValueAsJSONArray(
				"value", jsonObject);

			for (int j = 0; j < valueJSONArray.length(); j++) {
				CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
					cpDefinitionOptionValueRelLocalService.
						fetchCPDefinitionOptionValueRel(
							cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
							valueJSONArray.getString(j));

				if (cpDefinitionOptionValueRel == null) {
					continue;
				}

				List<Long> cpDefinitionOptionValueRelIds =
					cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds.get(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId());

				if (cpDefinitionOptionValueRelIds == null) {
					cpDefinitionOptionValueRelIds = new ArrayList<>();

					cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds.put(
						cpDefinitionOptionRel.getCPDefinitionOptionRelId(),
						cpDefinitionOptionValueRelIds);
				}

				cpDefinitionOptionValueRelIds.add(
					cpDefinitionOptionValueRel.
						getCPDefinitionOptionValueRelId());
			}
		}

		return cpDefinitionOptionRelIdCPDefinitionOptionValueRelIds;
	}

	@Override
	public Map<Long, List<Long>>
			getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
				long cpDefinitionId, String json)
		throws PortalException {

		return cpDefinitionOptionRelLocalService.
			getCPDefinitionOptionRelCPDefinitionOptionValueRelIds(
				cpDefinitionId, false, json);
	}

	@Override
	public Map<String, List<String>>
			getCPDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys(
				long cpInstanceId)
		throws PortalException {

		CPInstance cpInstance = cpInstanceLocalService.getCPInstance(
			cpInstanceId);

		if (cpInstance.isInactive()) {
			return Collections.emptyMap();
		}

		List<CPInstanceOptionValueRel> cpInstanceOptionValueRels =
			cpInstanceOptionValueRelPersistence.findByCPInstanceId(
				cpInstanceId);

		Map<String, List<String>>
			cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys =
				new HashMap<>();

		for (CPInstanceOptionValueRel cpInstanceOptionValueRel :
				cpInstanceOptionValueRels) {

			CPDefinitionOptionRel cpDefinitionOptionRel =
				cpDefinitionOptionRelPersistence.fetchByPrimaryKey(
					cpInstanceOptionValueRel.getCPDefinitionOptionRelId());

			if (cpDefinitionOptionRel == null) {
				continue;
			}

			List<String> cpDefinitionOptionValueRelKeys =
				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.get(
					cpDefinitionOptionRel.getKey());

			if (cpDefinitionOptionValueRelKeys == null) {
				cpDefinitionOptionValueRelKeys = new ArrayList<>();

				cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys.put(
					cpDefinitionOptionRel.getKey(),
					cpDefinitionOptionValueRelKeys);
			}

			if (cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId() ==
					0) {

				continue;
			}

			CPDefinitionOptionValueRel cpDefinitionOptionValueRel =
				cpDefinitionOptionValueRelPersistence.findByPrimaryKey(
					cpInstanceOptionValueRel.getCPDefinitionOptionValueRelId());

			cpDefinitionOptionValueRelKeys.add(
				cpDefinitionOptionValueRel.getKey());
		}

		return cpDefinitionOptionRelKeysCPDefinitionOptionValueRelKeys;
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
	public int getCPDefinitionOptionRelsCount(
		long cpDefinitionId, boolean skuContributor) {

		return cpDefinitionOptionRelPersistence.countByC_SC(
			cpDefinitionId, skuContributor);
	}

	@Override
	public boolean hasCPDefinitionPriceContributorCPDefinitionOptionRels(
		long cpDefinitionId) {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelPersistence.findByCPDefinitionId(
				cpDefinitionId);

		if (cpDefinitionOptionRels.isEmpty()) {
			return false;
		}

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			if (cpDefinitionOptionRel.isPriceContributor()) {
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean hasCPDefinitionRequiredCPDefinitionOptionRels(
		long cpDefinitionId) {

		long count = cpDefinitionOptionRelPersistence.countByCPDI_R(
			cpDefinitionId, true);

		if (count == 0) {
			return false;
		}

		return true;
	}

	@Override
	public boolean hasLinkedCPInstanceCPDefinitionOptionRels(
		long cpDefinitionId) {

		List<CPDefinitionOptionRel> cpDefinitionOptionRels =
			cpDefinitionOptionRelPersistence.findByCPDefinitionId(
				cpDefinitionId);

		if (cpDefinitionOptionRels.isEmpty()) {
			return false;
		}

		for (CPDefinitionOptionRel cpDefinitionOptionRel :
				cpDefinitionOptionRels) {

			if (!cpDefinitionOptionRel.isPriceContributor()) {
				continue;
			}

			for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
					cpDefinitionOptionRel.getCPDefinitionOptionValueRels()) {

				if (Validator.isNull(
						cpDefinitionOptionValueRel.getCPInstanceUuid())) {

					continue;
				}

				CPInstance cpInstance =
					cpDefinitionOptionValueRel.fetchCPInstance();

				if (cpInstance == null) {
					continue;
				}

				return true;
			}
		}

		return false;
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPDefinitionOptionRel> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CPDefinitionOptionRel.class);

			return indexer.search(searchContext);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	/**
	 * @param      companyId
	 * @param      groupId
	 * @param      cpDefinitionId
	 * @param      keywords
	 * @param      start
	 * @param      end
	 * @param      sort
	 * @return
	 *
	 * @throws     PortalException
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link
	 * 			   #searchCPDefinitionOptionRels(long, long, long, String, int,
	 * 			   int, Sort[])}
	 */
	@Deprecated
	@Override
	public BaseModelSearchResult<CPDefinitionOptionRel>
			searchCPDefinitionOptionRels(
				long companyId, long groupId, long cpDefinitionId,
				String keywords, int start, int end, Sort sort)
		throws PortalException {

		return searchCPDefinitionOptionRels(
			companyId, groupId, cpDefinitionId, keywords, start, end,
			new Sort[] {sort});
	}

	@Override
	public BaseModelSearchResult<CPDefinitionOptionRel>
			searchCPDefinitionOptionRels(
				long companyId, long groupId, long cpDefinitionId,
				String keywords, int start, int end, Sort[] sorts)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionId, keywords, start, end, sorts);

		return searchCPOptions(searchContext);
	}

	@Override
	public int searchCPDefinitionOptionRelsCount(
			long companyId, long groupId, long cpDefinitionId, String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, cpDefinitionId, keywords, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		return searchCPDefinitionOptionRelsCount(searchContext);
	}

	@Override
	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
			long cpDefinitionOptionRelId, long cpOptionId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, double priority, boolean facetable,
			boolean required, boolean skuContributor,
			ServiceContext serviceContext)
		throws PortalException {

		return cpDefinitionOptionRelLocalService.updateCPDefinitionOptionRel(
			cpDefinitionOptionRelId, cpOptionId, nameMap, descriptionMap,
			ddmFormFieldTypeName, priority, facetable, required, skuContributor,
			null, serviceContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPDefinitionOptionRel updateCPDefinitionOptionRel(
			long cpDefinitionOptionRelId, long cpOptionId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			String ddmFormFieldTypeName, double priority, boolean facetable,
			boolean required, boolean skuContributor, String priceType,
			ServiceContext serviceContext)
		throws PortalException {

		_validateDDMFormFieldTypeName(ddmFormFieldTypeName, skuContributor);

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.findByPrimaryKey(
				cpDefinitionOptionRelId);

		_validatePriceType(cpDefinitionOptionRel, priceType);

		if (cpDefinitionLocalService.isVersionable(
				cpDefinitionOptionRel.getCPDefinitionId(),
				serviceContext.getRequest())) {

			CPDefinition newCPDefinition =
				cpDefinitionLocalService.copyCPDefinition(
					cpDefinitionOptionRel.getCPDefinitionId());

			cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.findByC_C(
				newCPDefinition.getCPDefinitionId(),
				cpDefinitionOptionRel.getCPOptionId());
		}

		cpDefinitionOptionRel.setCPOptionId(cpOptionId);
		cpDefinitionOptionRel.setNameMap(nameMap);
		cpDefinitionOptionRel.setDescriptionMap(descriptionMap);
		cpDefinitionOptionRel.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpDefinitionOptionRel.setPriority(priority);
		cpDefinitionOptionRel.setFacetable(facetable);
		cpDefinitionOptionRel.setRequired(required);
		cpDefinitionOptionRel.setSkuContributor(skuContributor);
		cpDefinitionOptionRel.setPriceType(priceType);
		cpDefinitionOptionRel.setExpandoBridgeAttributes(serviceContext);

		cpDefinitionOptionRel = cpDefinitionOptionRelPersistence.update(
			cpDefinitionOptionRel);

		_updateCPDefinitionOptionValueRels(cpDefinitionOptionRelId, priceType);

		// Commerce product instances

		cpInstanceLocalService.inactivateIncompatibleCPInstances(
			serviceContext.getUserId(),
			cpDefinitionOptionRel.getCPDefinitionId());

		_updateCPDefinitionIgnoreSKUCombinations(
			cpDefinitionOptionRel.getCPDefinitionId(), serviceContext);

		// Commerce product definition

		reindexCPDefinition(cpDefinitionOptionRel.getCPDefinitionId());

		return cpDefinitionOptionRel;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, long cpDefinitionId, String keywords,
		int start, int end, Sort[] sorts) {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttributes(
			HashMapBuilder.<String, Serializable>put(
				Field.CONTENT, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"CPDefinitionId", cpDefinitionId
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build());
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});

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

	protected int searchCPDefinitionOptionRelsCount(SearchContext searchContext)
		throws PortalException {

		Indexer<CPDefinitionOptionRel> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPDefinitionOptionRel.class);

		return GetterUtil.getInteger(indexer.searchCount(searchContext));
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

	private CPOptionConfiguration _getCPOptionConfiguration()
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			CPOptionConfiguration.class,
			new SystemSettingsLocator(CPConstants.SERVICE_NAME_CP_OPTION));
	}

	private boolean _hasCPDefinitionSKUContributorCPDefinitionOptionRel(
		long cpDefinitionId) {

		int cpDefinitionOptionRelsCount =
			cpDefinitionOptionRelPersistence.countByC_SC(cpDefinitionId, true);

		if (cpDefinitionOptionRelsCount > 0) {
			return true;
		}

		return false;
	}

	private void _updateCPDefinitionIgnoreSKUCombinations(
			long cpDefintionId, ServiceContext serviceContext)
		throws PortalException {

		if (_hasCPDefinitionSKUContributorCPDefinitionOptionRel(
				cpDefintionId)) {

			cpDefinitionLocalService.updateCPDefinitionIgnoreSKUCombinations(
				cpDefintionId, false, serviceContext);

			return;
		}

		cpDefinitionLocalService.updateCPDefinitionIgnoreSKUCombinations(
			cpDefintionId, true, serviceContext);
	}

	private void _updateCPDefinitionOptionValueRels(
		long cpDefinitionOptionRelId, String priceType) {

		if (!Objects.equals(
				priceType, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {

			return;
		}

		List<CPDefinitionOptionValueRel> cpDefinitionOptionValueRels =
			cpDefinitionOptionValueRelLocalService.
				getCPDefinitionOptionValueRels(cpDefinitionOptionRelId);

		if (ListUtil.isEmpty(cpDefinitionOptionValueRels)) {
			return;
		}

		for (CPDefinitionOptionValueRel cpDefinitionOptionValueRel :
				cpDefinitionOptionValueRels) {

			if (cpDefinitionOptionValueRel.getPrice() == null) {
				cpDefinitionOptionValueRel.setPrice(BigDecimal.ZERO);

				cpDefinitionOptionValueRelLocalService.
					updateCPDefinitionOptionValueRel(
						cpDefinitionOptionValueRel);
			}
		}
	}

	private void _validateCPDefinitionOptionKey(long cpDefinitionId, String key)
		throws PortalException {

		CPDefinitionOptionRel cpDefinitionOptionRel =
			cpDefinitionOptionRelPersistence.fetchByC_K(cpDefinitionId, key);

		if (cpDefinitionOptionRel != null) {
			throw new DuplicateCPDefinitionOptionRelKeyException();
		}
	}

	private void _validateDDMFormFieldTypeName(
			String ddmFormFieldTypeName, boolean skuContributor)
		throws PortalException {

		if (Validator.isNull(ddmFormFieldTypeName)) {
			throw new CPDefinitionOptionSKUContributorException();
		}

		CPOptionConfiguration cpOptionConfiguration =
			_getCPOptionConfiguration();

		String[] ddmFormFieldTypesAllowed =
			cpOptionConfiguration.ddmFormFieldTypesAllowed();

		if (skuContributor) {
			ddmFormFieldTypesAllowed =
				CPConstants.PRODUCT_OPTION_SKU_CONTRIBUTOR_FIELD_TYPES;
		}

		if (ArrayUtil.contains(
				ddmFormFieldTypesAllowed, ddmFormFieldTypeName)) {

			return;
		}

		throw new CPDefinitionOptionSKUContributorException();
	}

	private void _validatePriceType(
			CPDefinitionOptionRel cpDefinitionOptionRel, String priceType)
		throws PortalException {

		if (cpDefinitionOptionRel.isNew() ||
			!cpDefinitionOptionRel.isPriceContributor() ||
			Objects.equals(cpDefinitionOptionRel.getPriceType(), priceType) ||
			!cpDefinitionOptionValueRelLocalService.
				hasCPDefinitionOptionValueRels(
					cpDefinitionOptionRel.getCPDefinitionOptionRelId()) ||
			Objects.equals(
				priceType, CPConstants.PRODUCT_OPTION_PRICE_TYPE_STATIC)) {

			return;
		}

		throw new CPDefinitionOptionRelPriceTypeException();
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID
	};

	@ServiceReference(type = ConfigurationProvider.class)
	private ConfigurationProvider _configurationProvider;

	@ServiceReference(type = ExpandoRowLocalService.class)
	private ExpandoRowLocalService _expandoRowLocalService;

	@ServiceReference(type = JSONFactory.class)
	private JSONFactory _jsonFactory;

	@ServiceReference(type = JsonHelper.class)
	private JsonHelper _jsonHelper;

}