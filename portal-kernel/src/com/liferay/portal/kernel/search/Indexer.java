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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 * @author Raymond Augé
 * @author Ryan Park
 */
public interface Indexer<T> {

	public static final int DEFAULT_INTERVAL = 10000;

	public void delete(long companyId, String uid) throws SearchException;

	@Bufferable
	public void delete(T object) throws SearchException;

	public String getClassName();

	public Document getDocument(T object) throws SearchException;

	public BooleanFilter getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception;

	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException;

	public IndexerPostProcessor[] getIndexerPostProcessors();

	public String[] getSearchClassNames();

	/**
	 * @deprecated As of Judson (7.1.x), replaced by {@link
	 *             com.liferay.portal.sort.SortFieldBuilder}
	 */
	@Deprecated
	public String getSortField(String orderByCol);

	public Summary getSummary(
			Document document, String snippet, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws SearchException;

	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception;

	public boolean isCommitImmediately();

	/**
	 * Return <code>true</code> if the indexer participates in post-search
	 * result filtering.
	 *
	 * @return <code>true</code> if the indexer participates in post-search
	 *         result filtering; <code>false</code> otherwise
	 * @see    SearchResultPermissionFilter
	 */
	public boolean isFilterSearch();

	public boolean isIndexerEnabled();

	/**
	 * Returns <code>true</code> if the indexer adds permission related filters
	 * to the search query prior to execution
	 *
	 * @return <code>true</code> if the indexer adds permission related filters
	 *         to the search query prior to execution; <code>false</code>
	 *         otherwise
	 * @see    SearchPermissionChecker
	 */
	public boolean isPermissionAware();

	public boolean isStagingAware();

	public boolean isVisible(long classPK, int status) throws Exception;

	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessContextBooleanFilter(BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception;

	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception;

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessSearchQuery(BooleanQuery, BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception;

	@Bufferable
	public void reindex(Collection<T> objects) throws SearchException;

	@Bufferable
	public void reindex(String className, long classPK) throws SearchException;

	public void reindex(String[] ids) throws SearchException;

	@Bufferable
	public void reindex(T object) throws SearchException;

	public Hits search(SearchContext searchContext) throws SearchException;

	public Hits search(
			SearchContext searchContext, String... selectedFieldNames)
		throws SearchException;

	public long searchCount(SearchContext searchContext) throws SearchException;

	public void setIndexerEnabled(boolean indexerEnabled);

}