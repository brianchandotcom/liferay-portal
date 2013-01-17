/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import java.util.Locale;

import javax.portlet.PortletURL;

/**
 * @author Brian Wing Shun Chan
 */
public class DummyIndexer implements Indexer {

	public void addRelatedEntityFields(Document document, Object obj)
		throws Exception {
	}

	public void delete(long companyId, String uid) {
	}

	public void delete(Object obj) {
	}

	public String[] getClassNames() {
		return new String[0];
	}

	public Document getDocument(Object obj) {
		return null;
	}

	public BooleanQuery getFacetQuery(
		String className, SearchContext searchContext) {

		return null;
	}

	public BooleanQuery getFullQuery(SearchContext searchContext) {
		return null;
	}

	public IndexerPostProcessor[] getIndexerPostProcessors() {
		return new IndexerPostProcessor[0];
	}

	public String getPortletId() {
		return StringPool.BLANK;
	}

	public String getSearchEngineId() {
		return StringPool.BLANK;
	}

	public String getSortField(String orderByCol) {
		return StringPool.BLANK;
	}

	public Summary getSummary(
		Document document, Locale locale, String snippet,
		PortletURL portletURL) {

		return null;
	}

	public boolean hasPermission(
		PermissionChecker permissionChecker, String entryClassName,
		long entryClassPK, String actionId) {

		return false;
	}

	public boolean isFilterSearch() {
		return false;
	}

	public boolean isPermissionAware() {
		return false;
	}

	public boolean isStagingAware() {
		return false;
	}

	public void postProcessContextQuery(
		BooleanQuery contextQuery, SearchContext searchContext) {
	}

	public void postProcessSearchQuery(
		BooleanQuery searchQuery, SearchContext searchContext) {
	}

	public void registerIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
	}

	public void reindex(Object obj) {
	}

	public void reindex(String className, long classPK) {
	}

	public void reindex(String[] ids) {
	}

	public Hits search(SearchContext searchContext) {
		return null;
	}

	public void unregisterIndexerPostProcessor(
		IndexerPostProcessor indexerPostProcessor) {
	}

}