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
import com.liferay.portal.vulcan.pagination.Page;
import com.liferay.portal.vulcan.pagination.Pagination;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SearchUtil {

	public static <T> Page<T> search(
			UnsafeConsumer<BooleanQuery, Exception> booleanQueryUnsafeConsumer,
			Filter filter, Class<?> indexerClass, Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			UnsafeConsumer<SearchContext, Exception>
				searchContextUnsafeConsumer,
			UnsafeFunction<Document, T, Exception> transformUnsafeFunction,
			Sort[] sorts)
		throws Exception {

		if (sorts == null) {
			sorts = new Sort[] {
				new Sort(Field.ENTRY_CLASS_PK, Sort.LONG_TYPE, false)
			};
		}

		List<T> items = new ArrayList<>();

		Indexer<?> indexer = IndexerRegistryUtil.getIndexer(indexerClass);

		SearchContext searchContext = _createSearchContext(
			_getBooleanClause(booleanQueryUnsafeConsumer, filter), pagination,
			queryConfigUnsafeConsumer, sorts);

		searchContextUnsafeConsumer.accept(searchContext);

		Hits hits = indexer.search(searchContext);

		for (Document document : hits.getDocs()) {
			T item = transformUnsafeFunction.apply(document);

			if (item != null) {
				items.add(item);
			}
		}

		return Page.of(items, pagination, indexer.searchCount(searchContext));
	}

	private static SearchContext _createSearchContext(
			BooleanClause<?> booleanClause, Pagination pagination,
			UnsafeConsumer<QueryConfig, Exception> queryConfigUnsafeConsumer,
			Sort[] sorts)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setBooleanClauses(new BooleanClause[] {booleanClause});
		searchContext.setEnd(pagination.getEndPosition());
		searchContext.setSorts(sorts);
		searchContext.setStart(pagination.getStartPosition());

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

}