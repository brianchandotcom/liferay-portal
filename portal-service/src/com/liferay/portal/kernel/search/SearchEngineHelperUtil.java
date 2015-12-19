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

import com.liferay.portal.kernel.util.ProxyFactory;

import java.util.Collection;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class SearchEngineHelperUtil {

	public static void flushQueuedSearchEngine() {
		_searchEngineHelper.flushQueuedSearchEngine();
	}

	public static void flushQueuedSearchEngine(String searchEngineId) {
		_searchEngineHelper.flushQueuedSearchEngine(searchEngineId);
	}

	public static String getDefaultSearchEngineId() {
		return _searchEngineHelper.getDefaultSearchEngineId();
	}

	public static String[] getEntryClassNames() {
		return _searchEngineHelper.getEntryClassNames();
	}

	public static SearchEngine getSearchEngine(String searchEngineId) {
		return _searchEngineHelper.getSearchEngine(searchEngineId);
	}

	public static String getSearchEngineId(Collection<Document> documents) {
		return _searchEngineHelper.getSearchEngineId(documents);
	}

	public static String getSearchEngineId(Document document) {
		return _searchEngineHelper.getSearchEngineId(document);
	}

	public static Set<String> getSearchEngineIds() {
		return _searchEngineHelper.getSearchEngineIds();
	}

	public static SearchEngine getSearchEngineSilent(String searchEngineId) {
		return _searchEngineHelper.getSearchEngineSilent(searchEngineId);
	}

	public static SearchPermissionChecker getSearchPermissionChecker() {
		return _searchPermissionChecker;
	}

	public static String getSearchReaderDestinationName(String searchEngineId) {
		return _searchEngineHelper.getSearchReaderDestinationName(
			searchEngineId);
	}

	public static String getSearchWriterDestinationName(String searchEngineId) {
		return _searchEngineHelper.getSearchWriterDestinationName(
			searchEngineId);
	}

	public static void initialize(long companyId) {
		_searchEngineHelper.initialize(companyId);
	}

	public static void removeCompany(long companyId) {
		_searchEngineHelper.removeCompany(companyId);
	}

	public static SearchEngine removeSearchEngine(String searchEngineId) {
		return _searchEngineHelper.removeSearchEngine(searchEngineId);
	}

	public static void setDefaultSearchEngineId(String defaultSearchEngineId) {
		_searchEngineHelper.setDefaultSearchEngineId(defaultSearchEngineId);
	}

	public static void setQueueCapacity(int queueCapacity) {
		_searchEngineHelper.setQueueCapacity(queueCapacity);
	}

	public static void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine) {

		_searchEngineHelper.setSearchEngine(searchEngineId, searchEngine);
	}

	private static final SearchEngineHelper _searchEngineHelper =
		ProxyFactory.newServiceTrackedInstance(SearchEngineHelper.class);
	private static final SearchPermissionChecker _searchPermissionChecker =
		ProxyFactory.newServiceTrackedInstance(SearchPermissionChecker.class);

}