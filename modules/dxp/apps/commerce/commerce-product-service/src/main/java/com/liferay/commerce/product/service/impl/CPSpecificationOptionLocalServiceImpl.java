/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.product.service.impl;

import com.liferay.commerce.product.exception.CPSpecificationOptionKeyException;
import com.liferay.commerce.product.exception.CPSpecificationOptionTitleException;
import com.liferay.commerce.product.model.CPSpecificationOption;
import com.liferay.commerce.product.search.CPSpecificationOptionIndexer;
import com.liferay.commerce.product.service.base.CPSpecificationOptionLocalServiceBaseImpl;
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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Andrea Di Giorgi
 * @author Alessio Antonio Rendina
 */
public class CPSpecificationOptionLocalServiceImpl
	extends CPSpecificationOptionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPSpecificationOption addCPSpecificationOption(
			long cpOptionCategoryId, Map<Locale, String> titleMap,
			Map<Locale, String> descriptionMap, boolean facetable, String key,
			ServiceContext serviceContext)
		throws PortalException {

		// Commerce product specification option

		User user = userLocalService.getUser(serviceContext.getUserId());
		long groupId = serviceContext.getScopeGroupId();

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(0, groupId, titleMap, key);

		long cpSpecificationOptionId = counterLocalService.increment();

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.create(cpSpecificationOptionId);

		cpSpecificationOption.setUuid(serviceContext.getUuid());
		cpSpecificationOption.setGroupId(groupId);
		cpSpecificationOption.setCompanyId(user.getCompanyId());
		cpSpecificationOption.setUserId(user.getUserId());
		cpSpecificationOption.setUserName(user.getFullName());
		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);
		cpSpecificationOption.setTitleMap(titleMap);
		cpSpecificationOption.setDescriptionMap(descriptionMap);
		cpSpecificationOption.setFacetable(facetable);
		cpSpecificationOption.setKey(key);
		cpSpecificationOption.setExpandoBridgeAttributes(serviceContext);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		// Resources

		resourceLocalService.addModelResources(
			cpSpecificationOption, serviceContext);

		return cpSpecificationOption;
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CPSpecificationOption deleteCPSpecificationOption(
			CPSpecificationOption cpSpecificationOption)
		throws PortalException {

		// Commerce product specification option

		cpSpecificationOptionPersistence.remove(cpSpecificationOption);

		// Commerce product definition specification option values

		cpDefinitionSpecificationOptionValueLocalService.
			deleteCPSpecificationOptionDefinitionValues(
				cpSpecificationOption.getCPSpecificationOptionId());

		// Resources

		resourceLocalService.deleteResource(
			cpSpecificationOption.getCompanyId(),
			CPSpecificationOption.class.getName(),
			ResourceConstants.SCOPE_INDIVIDUAL,
			cpSpecificationOption.getCPSpecificationOptionId());

		// Expando

		expandoRowLocalService.deleteRows(
			cpSpecificationOption.getCPSpecificationOptionId());

		return cpSpecificationOption;
	}

	@Override
	public CPSpecificationOption deleteCPSpecificationOption(
			long cpSpecificationOptionId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		return cpSpecificationOptionLocalService.deleteCPSpecificationOption(
			cpSpecificationOption);
	}

	@Override
	public void deleteCPSpecificationOptions(long groupId)
		throws PortalException {

		List<CPSpecificationOption> cpSpecificationOptions =
			cpSpecificationOptionPersistence.findByGroupId(groupId);

		for (CPSpecificationOption cpSpecificationOption :
				cpSpecificationOptions) {

			cpSpecificationOptionLocalService.deleteCPSpecificationOption(
				cpSpecificationOption);
		}
	}

	@Override
	public CPSpecificationOption fetchCPSpecificationOption(
		long groupId, String key) {

		return cpSpecificationOptionPersistence.fetchByG_K(groupId, key);
	}

	@Override
	public Hits search(SearchContext searchContext) {
		try {
			Indexer<CPSpecificationOption> indexer =
				IndexerRegistryUtil.nullSafeGetIndexer(
					CPSpecificationOption.class);

			return indexer.search(searchContext);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@Override
	public BaseModelSearchResult<CPSpecificationOption>
			searchCPSpecificationOptions(
				long companyId, long groupId, String keywords, int start,
				int end, Sort sort)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, keywords, start, end, sort);

		return searchCPSpecificationOptions(searchContext);
	}

	@Override
	public CPSpecificationOption updateCPOptionCategoryId(
			long cpSpecificationOptionId, long cpOptionCategoryId)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		return cpSpecificationOption;
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CPSpecificationOption updateCPSpecificationOption(
			long cpSpecificationOptionId, long cpOptionCategoryId,
			Map<Locale, String> titleMap, Map<Locale, String> descriptionMap,
			boolean facetable, String key, ServiceContext serviceContext)
		throws PortalException {

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.findByPrimaryKey(
				cpSpecificationOptionId);

		key = FriendlyURLNormalizerUtil.normalize(key);

		validate(
			cpSpecificationOption.getCPSpecificationOptionId(),
			cpSpecificationOption.getGroupId(), titleMap, key);

		cpSpecificationOption.setCPOptionCategoryId(cpOptionCategoryId);
		cpSpecificationOption.setTitleMap(titleMap);
		cpSpecificationOption.setDescriptionMap(descriptionMap);
		cpSpecificationOption.setFacetable(facetable);
		cpSpecificationOption.setKey(key);
		cpSpecificationOption.setExpandoBridgeAttributes(serviceContext);

		cpSpecificationOptionPersistence.update(cpSpecificationOption);

		return cpSpecificationOption;
	}

	protected SearchContext buildSearchContext(
		long companyId, long groupId, String keywords, int start, int end,
		Sort sort) {

		SearchContext searchContext = new SearchContext();

		LinkedHashMap<String, Object> params = new LinkedHashMap<>();

		params.put("keywords", keywords);

		Map<String, Serializable> attributes = new HashMap<>();

		attributes.put(
			CPSpecificationOptionIndexer.FIELD_CP_OPTION_CATEGORY_ID, keywords);
		attributes.put(
			CPSpecificationOptionIndexer.FIELD_CP_OPTION_CATEGORY_TITLE,
			keywords);
		attributes.put(Field.ENTRY_CLASS_PK, keywords);
		attributes.put(Field.TITLE, keywords);
		attributes.put(Field.DESCRIPTION, keywords);
		attributes.put(Field.CONTENT, keywords);
		attributes.put(CPSpecificationOptionIndexer.FIELD_KEY, keywords);
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

	protected List<CPSpecificationOption> getCPSpecificationOptions(Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CPSpecificationOption> cpSpecificationOptions = new ArrayList<>(
			documents.size());

		for (Document document : documents) {
			long cpSpecificationOptionId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CPSpecificationOption cpSpecificationOption =
				fetchCPSpecificationOption(cpSpecificationOptionId);

			if (cpSpecificationOption == null) {
				cpSpecificationOptions = null;

				Indexer<CPSpecificationOption> indexer =
					IndexerRegistryUtil.getIndexer(CPSpecificationOption.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (cpSpecificationOptions != null) {
				cpSpecificationOptions.add(cpSpecificationOption);
			}
		}

		return cpSpecificationOptions;
	}

	protected BaseModelSearchResult<CPSpecificationOption>
			searchCPSpecificationOptions(SearchContext searchContext)
		throws PortalException {

		Indexer<CPSpecificationOption> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(CPSpecificationOption.class);

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext, _SELECTED_FIELD_NAMES);

			List<CPSpecificationOption> cpSpecificationOptions =
				getCPSpecificationOptions(hits);

			if (cpSpecificationOptions != null) {
				return new BaseModelSearchResult<>(
					cpSpecificationOptions, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	protected void validate(
			long cpSpecificationOptionId, long groupId,
			Map<Locale, String> titleMap, String key)
		throws PortalException {

		Locale locale = LocaleUtil.getSiteDefault();

		String title = titleMap.get(locale);

		if (Validator.isNull(title)) {
			throw new CPSpecificationOptionTitleException();
		}

		if (Validator.isNull(key)) {
			throw new CPSpecificationOptionKeyException.MustNotBeNull();
		}

		CPSpecificationOption cpSpecificationOption =
			cpSpecificationOptionPersistence.fetchByG_K(groupId, key);

		if ((cpSpecificationOption != null) &&
			(cpSpecificationOption.getCPSpecificationOptionId() !=
				cpSpecificationOptionId)) {

			throw new CPSpecificationOptionKeyException.MustNotBeDuplicate(key);
		}
	}

	private static final String[] _SELECTED_FIELD_NAMES =
		{Field.ENTRY_CLASS_PK, Field.COMPANY_ID, Field.GROUP_ID, Field.UID};

}