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

import com.liferay.commerce.model.CommerceOrderType;
import com.liferay.commerce.model.CommerceShippingMethodTable;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOption;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifierTable;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionTable;
import com.liferay.commerce.shipping.engine.fixed.service.base.CommerceShippingFixedOptionLocalServiceBaseImpl;
import com.liferay.petra.sql.dsl.Column;
import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.FromStep;
import com.liferay.petra.sql.dsl.query.GroupByStep;
import com.liferay.petra.sql.dsl.query.JoinStep;
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
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleThreadLocal;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.math.BigDecimal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * @author Alessio Antonio Rendina
 */
public class CommerceShippingFixedOptionLocalServiceImpl
	extends CommerceShippingFixedOptionLocalServiceBaseImpl {

	@Indexable(type = IndexableType.REINDEX)
	@Override
	public CommerceShippingFixedOption addCommerceShippingFixedOption(
			long userId, long groupId, long commerceShippingMethodId,
			BigDecimal amount, Map<Locale, String> descriptionMap,
			Map<Locale, String> nameMap, double priority)
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
		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setKey(
			_getKey(user.getCompanyId(), nameMap));
		commerceShippingFixedOption.setNameMap(nameMap);
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
				commerceShippingMethodId, amount, descriptionMap, nameMap,
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
	public CommerceShippingFixedOption fetchCommerceShippingFixedOption(
		long companyId, String key) {

		return commerceShippingFixedOptionPersistence.fetchByC_K(
			companyId, key);
	}

	@Override
	public List<CommerceShippingFixedOption>
		getCommerceOrderTypeCommerceShippingFixedOptions(
			long companyId, long commerceOrderTypeId,
			long commerceShippingMethodId) {

		return dslQuery(
			_getGroupByStep(
				companyId, commerceOrderTypeId, commerceShippingMethodId,
				DSLQueryFactoryUtil.selectDistinct(
					CommerceShippingFixedOptionTable.INSTANCE)));
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
			long commerceShippingFixedOptionId, BigDecimal amount,
			Map<Locale, String> descriptionMap, Map<Locale, String> nameMap,
			double priority)
		throws PortalException {

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionPersistence.findByPrimaryKey(
				commerceShippingFixedOptionId);

		commerceShippingFixedOption.setAmount(amount);
		commerceShippingFixedOption.setDescriptionMap(descriptionMap);
		commerceShippingFixedOption.setNameMap(nameMap);
		commerceShippingFixedOption.setPriority(priority);

		if (Validator.isNull(commerceShippingFixedOption.getKey())) {
			commerceShippingFixedOption.setKey(
				_getKey(commerceShippingFixedOption.getCompanyId(), nameMap));
		}

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

	private GroupByStep _getGroupByStep(
		Long companyId, Long commerceOrderTypeId, Long commerceShippingMethodId,
		FromStep fromStep) {

		CommerceShippingFixedOptionQualifierTable
			commerceOrderTypeCommerceShippingFixedQualifier =
				CommerceShippingFixedOptionQualifierTable.INSTANCE.as(
					"commerceOrderTypeCommerceShippingFixedQualifier");

		JoinStep joinStep = fromStep.from(
			CommerceShippingFixedOptionTable.INSTANCE
		).innerJoinON(
			CommerceShippingMethodTable.INSTANCE,
			CommerceShippingMethodTable.INSTANCE.commerceShippingMethodId.eq(
				CommerceShippingFixedOptionTable.INSTANCE.
					commerceShippingMethodId)
		).leftJoinOn(
			commerceOrderTypeCommerceShippingFixedQualifier,
			_getPredicate(
				CommerceOrderType.class.getName(),
				commerceOrderTypeCommerceShippingFixedQualifier.classNameId,
				commerceOrderTypeCommerceShippingFixedQualifier.
					commerceShippingFixedOptionId)
		);

		Column<CommerceShippingFixedOptionQualifierTable, Long> classPKColumn =
			commerceOrderTypeCommerceShippingFixedQualifier.classPK;

		return joinStep.where(
			CommerceShippingFixedOptionTable.INSTANCE.companyId.eq(
				companyId
			).and(
				CommerceShippingFixedOptionTable.INSTANCE.
					commerceShippingMethodId.eq(commerceShippingMethodId)
			).and(
				Predicate.withParentheses(
					classPKColumn.eq(
						commerceOrderTypeId
					).or(
						commerceOrderTypeCommerceShippingFixedQualifier.
							commerceShippingFixedOptionId.isNull()
					))
			));
	}

	private String _getKey(long companyId, Map<Locale, String> nameMap) {
		String key = FriendlyURLNormalizerUtil.normalize(
			nameMap.get(LocaleThreadLocal.getDefaultLocale()));

		if (Validator.isNull(key)) {
			Collection<String> values = nameMap.values();

			Stream<String> stream = values.stream();

			Optional<String> firstOptional = stream.filter(
				value -> Validator.isNotNull(value)
			).findFirst();

			key = FriendlyURLNormalizerUtil.normalize(firstOptional.get());
		}

		CommerceShippingFixedOption commerceShippingFixedOption =
			commerceShippingFixedOptionLocalService.
				fetchCommerceShippingFixedOption(companyId, key);

		if (commerceShippingFixedOption != null) {
			key = key + StringUtil.randomString(5);
		}

		return key;
	}

	private Predicate _getPredicate(
		String className,
		Column<CommerceShippingFixedOptionQualifierTable, Long>
			classNameIdColumn,
		Column<CommerceShippingFixedOptionQualifierTable, Long>
			commerceShippingFixedOptionIdColumn) {

		return classNameIdColumn.eq(
			classNameLocalService.getClassNameId(className)
		).and(
			commerceShippingFixedOptionIdColumn.eq(
				CommerceShippingFixedOptionTable.INSTANCE.
					commerceShippingFixedOptionId)
		);
	}

}