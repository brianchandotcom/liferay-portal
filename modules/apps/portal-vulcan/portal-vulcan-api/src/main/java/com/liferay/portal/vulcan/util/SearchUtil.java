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

package com.liferay.portal.vulcan.util;

import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.petra.function.UnsafeFunction;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseFactoryUtil;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.Filter;
import com.liferay.portal.kernel.search.generic.BooleanQueryImpl;
import com.liferay.portal.kernel.search.generic.MatchAllQuery;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUtil {

	public static <T extends BaseModel<T>> QueryDefinition<T>
		getQueryDefinition(
			Class<T> clazz, Pagination pagination, Sort[] sorts) {

		QueryDefinition<T> queryDefinition = new QueryDefinition<>();

		queryDefinition.setEnd(pagination.getEndPosition());

		Object[] orderByComparatorColumns = _getOrderByComparatorColumns(sorts);

		if (orderByComparatorColumns != null) {
			OrderByComparator<T> orderByComparator =
				OrderByComparatorFactoryUtil.create(
					clazz.getSimpleName(), orderByComparatorColumns);

			queryDefinition.setOrderByComparator(orderByComparator);
		}

		queryDefinition.setStart(pagination.getStartPosition());

		return queryDefinition;
	}

	public static <T> Page<T> search(
			Map<String, Map<String, String>> actions,
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, Class<?> indexerClass, String keywords,
			Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			UnsafeConsumer<SearchContext, Exception>
				searchContextUnsafeConsumer,
			Sort[] sorts,
			UnsafeFunction<Document, T, Exception> transformUnsafeFunction)
		throws Exception {

		if (actions == null) {
			actions = Collections.emptyMap();
		}

		if (sorts == null) {
			sorts = new Sort[] {
				new Sort(Field.ENTRY_CLASS_PK, Sort.LONG_TYPE, false)
			};
		}

		List<T> items = new ArrayList<>();

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(indexerClass);

		SearchContext searchContext = _createSearchContext(
			_getBooleanClause(booleanQueryUnsafeConsumer, filter), keywords,
			pagination, queryConfigUnsafeConsumer, sorts);

		searchContextUnsafeConsumer.accept(searchContext);

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			T item = transformUnsafeFunction.apply(document);

			if (item != null) {
				items.add(item);
			}
		}

		return Page.of(
			(Map)actions, items, pagination,
			indexer.searchCount(searchContext));
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(Map,
	 *             UnsafeConsumer, Filter, Class, String, Pagination,
	 *             UnsafeConsumer, UnsafeConsumer, Sort[], UnsafeFunction)}
	 */
	@Deprecated
	public static <T> Page<T> search(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, Class<?> indexerClass, String keywords,
			Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			UnsafeConsumer<SearchContext, Exception>
				searchContextUnsafeConsumer,
			UnsafeFunction<Document, T, Exception> transformUnsafeFunction,
			Sort[] sorts)
		throws Exception {

		return search(
			Collections.emptyMap(), booleanQueryUnsafeConsumer, filter,
			indexerClass, keywords, pagination, queryConfigUnsafeConsumer,
			searchContextUnsafeConsumer, sorts, transformUnsafeFunction);
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), replaced by {@link #search(Map,
	 *             UnsafeConsumer, Filter, Class, String, Pagination,
	 *             UnsafeConsumer, UnsafeConsumer, Sort[], UnsafeFunction)}
	 */
	@Deprecated
	public static <T> Page<T> search(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, Class<?> indexerClass, String keywords,
			Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			UnsafeConsumer<SearchContext, Exception>
				searchContextUnsafeConsumer,
			UnsafeFunction<Document, T, Exception> transformUnsafeFunction,
			Sort[] sorts, Map<String, Map> actions)
		throws Exception {

		Set<Map.Entry<String, Map>> entries = actions.entrySet();

		Stream<Map.Entry<String, Map>> stream = entries.stream();

		Map<String, Map<String, String>> actionsTypedMap = stream.collect(
			Collectors.toMap(
				Map.Entry::getKey,
				entry -> (Map<String, String>)entry.getValue()));

		return search(
			actionsTypedMap, booleanQueryUnsafeConsumer, filter, indexerClass,
			keywords, pagination, queryConfigUnsafeConsumer,
			searchContextUnsafeConsumer, sorts, transformUnsafeFunction);
	}

	private static SearchContext _createSearchContext(
			BooleanClause<?> booleanClause, String keywords,
			Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			Sort[] sorts)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setBooleanClauses(new BooleanClause[] {booleanClause});

		if (pagination != null) {
			searchContext.setEnd(pagination.getEndPosition());
		}

		searchContext.setKeywords(keywords);
		searchContext.setSorts(sorts);

		if (pagination != null) {
			searchContext.setStart(pagination.getStartPosition());
		}

		PermissionChecker permissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		searchContext.setUserId(permissionChecker.getUserId());

		QueryConfig queryConfig = searchContext.getQueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		queryConfigUnsafeConsumer.accept(queryConfig);

		return searchContext;
	}

	private static BooleanClause<?> _getBooleanClause(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter)
		throws Exception {

		BooleanQuery booleanQuery = new BooleanQueryImpl() {
			{
				add(new MatchAllQuery(), BooleanClauseOccur.MUST);

				BooleanFilter booleanFilter = new BooleanFilter();

				if (filter != null) {
					booleanFilter.add(filter, BooleanClauseOccur.MUST);
				}

				setPreBooleanFilter(booleanFilter);
			}
		};

		booleanQueryUnsafeConsumer.accept(booleanQuery);

		return BooleanClauseFactoryUtil.create(
			booleanQuery, BooleanClauseOccur.MUST.getName());
	}

	private static Object[] _getOrderByComparatorColumns(Sort[] sorts) {
		if (ArrayUtil.isEmpty(sorts)) {
			return null;
		}

		return Stream.of(
			sorts
		).flatMap(
			sort -> Stream.of(sort.getFieldName(), !sort.isReverse())
		).toArray();
	}

}