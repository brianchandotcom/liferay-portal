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

package com.liferay.portal.kernel.search.dummy;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.BooleanQuery;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerPostProcessor;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.Summary;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.security.permission.PermissionChecker;

import java.util.Collection;

import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyIndexer implements Indexer<Object> {

	@Override
	public void delete(long companyId, String uid) {
	}

	@Override
	public void delete(Object object) {
	}

	@Override
	public String getClassName() {
		return StringPool.BLANK;
	}

	@Override
	public Document getDocument(Object object) {
		return null;
	}

	@Override
	public BooleanFilter getFacetBooleanFilter(
		String className, SearchContext searchContext) {

		return null;
	}

	@Override
	public BooleanQuery getFullQuery(SearchContext searchContext) {
		return null;
	}

	@Override
	public IndexerPostProcessor[] getIndexerPostProcessors() {
		return new IndexerPostProcessor[0];
	}

	@Override
	public String[] getSearchClassNames() {
		return new String[0];
	}

	@Override
	public String getSearchEngineId() {
		return StringPool.BLANK;
	}

	@Override
	public String getSortField(String orderByCol) {
		return StringPool.BLANK;
	}

	@Override
	public String getSortField(String orderByCol, int sortType) {
		return StringPool.BLANK;
	}

	@Override
	public Summary getSummary(
		Document document, String snippet, PortletRequest portletRequest,
		PortletResponse portletResponse) {

		return null;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, String entryClassName,
		long entryClassPK, String actionId) {

		return false;
	}

	@Override
	public boolean isCommitImmediately() {
		return false;
	}

	@Override
	public boolean isFilterSearch() {
		return false;
	}

	@Override
	public boolean isIndexerEnabled() {
		return false;
	}

	@Override
	public boolean isPermissionAware() {
		return false;
	}

	@Override
	public boolean isStagingAware() {
		return false;
	}

	@Override
	public boolean isVisible(long classPK, int status) throws Exception {
		return true;
	}

	@Override
	public boolean isVisibleRelatedEntry(long classPK, int status)
		throws Exception {

		return true;
	}

	@Override
	public void postProcessContextBooleanFilter(
			BooleanFilter contextBooleanFilter, SearchContext searchContext)
		throws Exception {
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessContextBooleanFilter(BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessContextQuery(
		BooleanQuery contextQuery, SearchContext searchContext) {
	}

	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, BooleanFilter fullQueryBooleanFilter,
		SearchContext searchContext) {
	}

	/**
	 * @deprecated As of Wilberforce (7.0.x), replaced by {@link
	 *             #postProcessSearchQuery(BooleanQuery, BooleanFilter,
	 *             SearchContext)}
	 */
	@Deprecated
	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, SearchContext searchContext) {

		throw new UnsupportedOperationException();
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
	}

	@Override
	public void reindex(Collection<Object> objects) {
	}

	@Override
	public void reindex(Object obj) {
	}

	@Override
	public void reindex(String className, long classPK) {
	}

	@Override
	public void reindex(String[] ids) {
	}

	@Override
	public Hits search(SearchContext searchContext) {
		return null;
	}

	@Override
	public Hits search(
		SearchContext searchContext, String... selectedFieldNames) {

		return null;
	}

	@Override
	public long searchCount(SearchContext searchContext) {
		return 0;
	}

	@Override
	public void setIndexerEnabled(boolean indexerEnabled) {
	}

	@Override
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
	}

}