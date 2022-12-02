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

package com.liferay.portal.search.buffer;

import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Michael C. Han
 * @deprecated As of Athanasius (7.3.x)
 */
@Deprecated
public class NoAutoCommitIndexer<T> implements Indexer<T> {

	public NoAutoCommitIndexer(Indexer<T> indexer) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(long companyId, String uid) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void delete(T object) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object object) {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getClassName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Document getDocument(T object) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public BooleanFilter getFacetBooleanFilter(
			String className, SearchContext searchContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String[] getSearchClassNames() {
		throw new UnsupportedOperationException();
	}

	@Override
	public String getSortField(String orderByCol) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Summary getSummary(
			Document document, String snippet, PortletRequest portletRequest,
			PortletResponse portletResponse)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean hasPermission(
			PermissionChecker permissionChecker, String entryClassName,
			long entryClassPK, String actionId)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isCommitImmediately() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isFilterSearch() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isIndexerEnabled() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isPermissionAware() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isStagingAware() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		throw new UnsupportedOperationException();
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessContextBooleanFilter(BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessContextQuery(
			BooleanQuery contextQuery, SearchContext searchContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
			SearchContext searchContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessSearchQuery(BooleanQuery, BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessSearchQuery(
			BooleanQuery searchQuery, SearchContext searchContext)
		throws Exception {

		throw new UnsupportedOperationException();
	}

	@Override
	public void reindex(Collection<T> objects) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reindex(String className, long classPK) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reindex(String[] ids) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reindex(T object) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(SearchContext searchContext) throws SearchException {
		throw new UnsupportedOperationException();
	}

	@Override
	public Hits search(
			SearchContext searchContext, String... selectedFieldNames)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	@Override
	public long searchCount(SearchContext searchContext)
		throws SearchException {

		throw new UnsupportedOperationException();
	}

	@Override
	public void setIndexerEnabled(boolean indexerEnabled) {
		throw new UnsupportedOperationException();
	}

}