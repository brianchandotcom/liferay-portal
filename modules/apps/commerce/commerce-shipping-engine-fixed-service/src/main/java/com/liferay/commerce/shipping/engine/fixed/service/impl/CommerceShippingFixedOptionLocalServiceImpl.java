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

package com.liferay.commerce.shipping.engine.fixed.service.impl;

import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionLocalServiceBaseImpl;
import com.liferay.portal.kernel.exception.PortalException;
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
import com.liferay.portal.kernel.search.SortFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.systemevent.SystemEvent;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionLocalServiceImpl
	extends CommerceShippingFixedOptionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long userId, long groupId, long commerceShippingMethodId,
			Map<Locale, String> nameMap, Map<Locale, String> descriptionMap,
			BigDecimal amount, double priority)
		throws PortalException {

		User user = userLocalService.getUser(userId);

		long commerceShippingFixedOptionId = counterLocalService.increment();

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionPersistence.create(
				commerceShippingFixedOptionId);

		commerceShippingFixedOption.setGroupId(groupId);
		commerceShippingFixedOption.setCompanyId(user.getCompanyId());
		commerceShippingFixedOption.setUserId(user.getUserId());
		commerceShippingFixedOption.setUserName(user.getFullName());
		commerceShippingFixedOption.setCommerceShippingMethodId(
			commerceShippingMethodId);
		commerceShippingFixedOption.setNameMap(nameMap);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setPriority(priority);

		return commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x)
	 */
	@Deprecated
	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long commerceShippingMethodId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, BigDecimal amount,
			double priority, ServiceContext serviceContext)
		throws PortalException {

		return commerceShippingFixedOptionLocalService.
			addCommerceShippingFixedOption(
				serviceContext.getUserId(), serviceContext.getScopeGroupId(),
				commerceShippingMethodId, nameMap, descriptionMap, amount,
				priority);
	}

	@Indexable(type = IndexableType.DELETE)
	@Override
	@SystemEvent(type = SystemEventConstants.TYPE_DELETE)
	public CommerceShippingFixedOption deleteCommerceShippingFixedOption(
		CommerceShippingFixedOption commerceShippingFixedOption) {

		// Commerce shipping fixed option

		commerceShippingFixedOptionPersistence.remove(
			commerceShippingFixedOption);

		// Commerce shipping fixed option rels

		commerceShippingFixedOptionRelLocalService.
			deleteCommerceShippingFixedOptionRels(
				commerceShippingFixedOption.getCommerceShippingFixedOptionId());

		return commerceShippingFixedOption;
	}

	@Override
	public void deleteCommerceShippingFixedOptions(
		long commerceShippingMethodId) {

		commerceShippingFixedOptionPersistence.removeByCommerceShippingMethodId(
			commerceShippingMethodId);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end) {

		return commerceShippingFixedOptionPersistence.
			findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
		long commerceShippingMethodId, int start, int end,
		OrderByComparator<CommerceShippingFixedOption> orderByComparator) {

		return commerceShippingFixedOptionPersistence.
			findByCommerceShippingMethodId(
				commerceShippingMethodId, start, end, orderByComparator);
	}

	@Override
	public List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords, int start, int end)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commerceShippingMethodId, keywords, start, end);

		BaseModelSearchResult<CommerceShippingFixedOption>
			baseModelSearchResult = searchCommerceShippingFixedOption(
				searchContext);

		return baseModelSearchResult.getBaseModels();
	}

	@Override
	public int getCommerceShippingFixedOptionsCount(
		long commerceShippingMethodId) {

		return commerceShippingFixedOptionPersistence.
			countByCommerceShippingMethodId(commerceShippingMethodId);
	}

	@Override
	public long getCommerceShippingFixedOptionsCount(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords)
		throws PortalException {

		SearchContext searchContext = buildSearchContext(
			companyId, groupId, commerceShippingMethodId, keywords, 0, 0);

		Indexer<CommerceShippingFixedOption> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceShippingFixedOption.class.getName());

		return indexer.searchCount(searchContext);
	}

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public BaseModelSearchResult<CommerceShippingFixedOption>
			searchCommerceShippingFixedOption(SearchContext searchContext)
		throws PortalException {

		Indexer<CommerceShippingFixedOption> indexer =
			IndexerRegistryUtil.nullSafeGetIndexer(
				CommerceShippingFixedOption.class.getName());

		for (int i = 0; i < 10; i++) {
			Hits hits = indexer.search(searchContext);

			List<CommerceShippingFixedOption> commerceShippingFixedOptions =
				getCommerceShippingFixedOptions(hits);

			if (commerceShippingFixedOptions != null) {
				return new BaseModelSearchResult<>(
					commerceShippingFixedOptions, hits.getLength());
			}
		}

		throw new SearchException(
			"Unable to fix the search index after 10 attempts");
	}

	@Override
	public CommerceShippingFixedOption updateCommerceShippingFixedOption(
			long commerceShippingFixedOptionId, Map<Locale, String> nameMap,
			Map<Locale, String> descriptionMap, BigDecimal amount,
			double priority)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionPersistence.findByPrimaryKey(
				commerceShippingFixedOptionId);

		commerceShippingFixedOption.setNameMap(nameMap);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setPriority(priority);

		return commerceShippingFixedOptionPersistence.update(
			commerceShippingFixedOption);
	}

	protected SearchContext buildSearchContext(
			long companyId, long groupId, long commerceShippingMethodId,
			String keywords, int start, int end)
		throws PortalException {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute(
			"commerceShippingMethodId", commerceShippingMethodId);
		searchContext.setCompanyId(companyId);
		searchContext.setEnd(end);
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setKeywords(keywords);

		Sort sort = SortFactoryUtil.getSort(
			CommerceShippingFixedOption.class, Sort.LONG_TYPE,
			Field.CREATE_DATE, "DESC");

		searchContext.setSorts(sort);

		searchContext.setStart(start);

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		return searchContext;
	}

	protected List<CommerceShippingFixedOption> getCommerceShippingFixedOptions(
			Hits hits)
		throws PortalException {

		List<Document> documents = hits.toList();

		List<CommerceShippingFixedOption> commerceShippingFixedOptions =
			new ArrayList<>(documents.size());

		for (Document document : documents) {
			long commerceShippingFixedOptionId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			CommerceShippingFixedOption commerceShippingFixedOption =
				fetchCommerceShippingFixedOption(commerceShippingFixedOptionId);

			if (commerceShippingFixedOption == null) {
				commerceShippingFixedOption = null;

				Indexer<CommerceShippingFixedOption> indexer =
					IndexerRegistryUtil.getIndexer(
						CommerceShippingFixedOption.class);

				long companyId = GetterUtil.getLong(
					document.get(Field.COMPANY_ID));

				indexer.delete(companyId, document.getUID());
			}
			else if (commerceShippingFixedOptions != null) {
				commerceShippingFixedOptions.add(commerceShippingFixedOption);
			}
		}

		return commerceShippingFixedOptions;
	}

}