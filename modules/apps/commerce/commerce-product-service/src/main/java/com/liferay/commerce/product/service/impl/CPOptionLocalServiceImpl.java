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
import com.liferay.commerce.product.constants.CPField;
import com.liferay.commerce.product.exception.CPOptionKeyException;
import com.liferay.commerce.product.exception.CPOptionSKUContributorException;
import com.liferay.commerce.product.model.CPOption;
import com.liferay.commerce.product.service.base.CPOptionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.ResourceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.settings.SystemSettingsLocator;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
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
 * @author Igor Beslic
 */
public class CPOptionLocalServiceImpl extends CPOptionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOption addCPOption(
			long userId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		validateDDMFormFieldTypeName(ddmFormFieldTypeName, skuContributor);

		User user = userLocalService.getUser(userId);

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}

		key = FriendlyURLNormalizerUtil.normalize(key);

		validateCPOptionKey(0, user.getCompanyId(), key);

		long cpOptionId = counterLocalService.increment();

		CPOption cpOption = cpOptionPersistence.create(cpOptionId);

		cpOption.setCompanyId(user.getCompanyId());
		cpOption.setUserId(user.getUserId());
		cpOption.setUserName(user.getFullName());
		cpOption.setNameMap(nameMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setFacetable(facetable);
		cpOption.setRequired(required);
		cpOption.setSkuContributor(skuContributor);
		cpOption.setKey(key);
		cpOption.setExpandoBridgeAttributes(serviceContext);
		cpOption.setExternalReferenceCode(externalReferenceCode);

		cpOption = cpOptionPersistence.update(cpOption);

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
			cpOption, ResourceConstants.SCOPE_INDIVIDUAL);

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
	public void deleteCPOptions(long companyId) throws PortalException {
		List<CPOption> cpOptions = cpOptionPersistence.findByCompanyId(
			companyId);

		for (CPOption cpOption : cpOptions) {
			cpOptionLocalService.deleteCPOption(cpOption);
		}
	}

	@Override
	public CPOption fetchByExternalReferenceCode(
		long companyId, String externalReferenceCode) {

		if (Validator.isBlank(externalReferenceCode)) {
			return null;
		}

		return cpOptionPersistence.fetchByC_ERC(
			companyId, externalReferenceCode);
	}

	@Override
	public CPOption fetchCPOption(long companyId, String key)
		throws PortalException {

		return cpOptionPersistence.fetchByC_K(companyId, key);
	}

	@Override
	public List<CPOption> findCPOptionByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<CPOption> orderByComparator) {

		return cpOptionPersistence.filterFindByCompanyId(
			companyId, start, end, orderByComparator);
	}

	@Override
	public CPOption getCPOption(long companyId, String key)
		throws PortalException {

		return cpOptionPersistence.findByC_K(companyId, key);
	}

	@Override
	public int getCPOptionsCount(long companyId) {
		return cpOptionPersistence.countByCompanyId(companyId);
	}

	@Override
	public BaseModelSearchResult<CPOption> searchCPOptions(
			long companyId, String keywords, int start, int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, keywords, start, end, sort);

		return searchCPOptions(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOption updateCPOption(
			long cpOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, ServiceContext serviceContext)
		throws PortalException {

		validateDDMFormFieldTypeName(ddmFormFieldTypeName, skuContributor);

		CPOption cpOption = cpOptionPersistence.findByPrimaryKey(cpOptionId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validateCPOptionKey(
			cpOption.getCPOptionId(), cpOption.getCompanyId(), key);

		cpOption.setNameMap(nameMap);
		cpOption.setDescriptionMap(descriptionMap);
		cpOption.setDDMFormFieldTypeName(ddmFormFieldTypeName);
		cpOption.setFacetable(facetable);
		cpOption.setRequired(required);
		cpOption.setSkuContributor(skuContributor);
		cpOption.setKey(key);
		cpOption.setExpandoBridgeAttributes(serviceContext);

		return cpOptionPersistence.update(cpOption);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPOption updateCPOptionExternalReferenceCode(
			long cpOptionId, String externalReferenceCode)
		throws PortalException {

		CPOption cpOption = cpOptionLocalService.getCPOption(cpOptionId);

		cpOption.setExternalReferenceCode(externalReferenceCode);

		return cpOptionPersistence.update(cpOption);
	}

	@Override
	public CPOption upsertCPOption(
			long userId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, String ddmFormFieldTypeName,
			boolean facetable, boolean required, boolean skuContributor,
			String key, String externalReferenceCode,
			ServiceContext serviceContext)
		throws PortalException {

		if (Validator.isBlank(externalReferenceCode)) {
			externalReferenceCode = null;
		}
		else {
			CPOption cpOption = cpOptionPersistence.fetchByC_ERC(
				serviceContext.getCompanyId(), externalReferenceCode);

			if (cpOption != null) {
				return updateCPOption(
					cpOption.getCPOptionId(), nameMap, descriptionMap,
					ddmFormFieldTypeName, facetable, required, skuContributor,
					key, serviceContext);
			}
		}

		return addCPOption(
			userId, nameMap, descriptionMap, ddmFormFieldTypeName, facetable,
			required, skuContributor, key, externalReferenceCode,
			serviceContext);
	}

	protected SearchContext buildSearchContext(
		long companyId, String keywords, int start, int end, Sort sort) {

		SearchContext searchContext = new SearchContext();

		Map<String, Serializable> attributes =
			HashMapBuilder.<String, Serializable>put(
				CPField.KEY, keywords
			).put(
				Field.CONTENT, keywords
			).put(
				Field.DESCRIPTION, keywords
			).put(
				Field.ENTRY_CLASS_PK, keywords
			).put(
				Field.NAME, keywords
			).put(
				"params",
				LinkedHashMapBuilder.<String, Object>put(
					"keywords", keywords
				).build()
			).build();

		searchContext.setAttributes(attributes);

		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);

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

	protected void validateCPOptionKey(
			long cpOptionId, long companyId, String key)
		throws PortalException {

		CPOption cpOption = cpOptionPersistence.fetchByC_K(companyId, key);

		if ((cpOption != null) && (cpOption.getCPOptionId() != cpOptionId)) {
			throw new CPOptionKeyException();
		}
	}

	protected void validateDDMFormFieldTypeName(
			String ddmFormFieldTypeName, boolean skuContributor)
		throws PortalException {

		if (Validator.isNull(ddmFormFieldTypeName)) {
			throw new CPOptionSKUContributorException();
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

		throw new CPOptionSKUContributorException();
	}

	private CPOptionConfiguration _getCPOptionConfiguration()
		throws ConfigurationException {

		return _configurationProvider.getConfiguration(
			CPOptionConfiguration.class,
			new SystemSettingsLocator(CPConstants.CP_OPTION_SERVICE_NAME));
	}

	private static final String[] _SELECTED_FIELD_NAMES = {
		Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.UID
	};

	@ServiceReference(type = ConfigurationProvider.class)
	private ConfigurationProvider _configurationProvider;

}