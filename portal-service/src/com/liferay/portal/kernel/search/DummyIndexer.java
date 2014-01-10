/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.security.permission.PermissionChecker;

import java.util.List;
import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyIndexer implements Indexer {

	@Override
	public void addRelatedEntryFields(Document document, Object obj) {
	}

	@Override
	public void delete(long companyId, String uid) {
	}

	@Override
	public void delete(Object obj) {
	}

	@Override
	public String[] getClassNames() {
		return new String[0];
	}

	@Override
	public Document getDocument(Object obj) {
		return null;
	}

	@Override
	public BooleanQuery getFacetQuery(
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
	public String getPortletId() {
		return StringPool.BLANK;
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
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		return null;
	}

	@Override
	public boolean hasPermission(
		PermissionChecker permissionChecker, String entryClassName,
		long entryClassPK, String actionId) {

		return false;
	}

	@Override
	public boolean isFilterSearch() {
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
	public void postProcessContextQuery(
		BooleanQuery contextQuery, SearchContext searchContext) {
	}

	@Override
	public void postProcessSearchQuery(
		BooleanQuery searchQuery, SearchContext searchContext) {
	}

	@Override
	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
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
	public void reindexDDMStructures(List<Long> ddmStructureIds) {
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
	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
	}

}