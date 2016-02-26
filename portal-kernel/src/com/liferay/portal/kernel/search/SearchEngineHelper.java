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

import java.util.Collection;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public interface SearchEngineHelper {

	public static final String GENERIC_ENGINE_ID = "GENERIC_ENGINE";

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	public void flushQueuedSearchEngine();

	public void flushQueuedSearchEngine(String searchEngineId);

	public Collection<Long> getCompanyIds();

	public String getDefaultSearchEngineId();

	public String[] getEntryClassNames();

	public SearchEngine getSearchEngine(String searchEngineId);

	public String getSearchEngineId(Collection<Document> documents);

	public String getSearchEngineId(Document document);

	public Set<String> getSearchEngineIds();

	public Collection<SearchEngine> getSearchEngines();

	public SearchEngine getSearchEngineSilent(String searchEngineId);

	public String getSearchReaderDestinationName(String searchEngineId);

	public String getSearchWriterDestinationName(String searchEngineId);

	public void initialize(long companyId);

	public void removeCompany(long companyId);

	public SearchEngine removeSearchEngine(String searchEngineId);

	public void setDefaultSearchEngineId(String defaultSearchEngineId);

	public void setQueueCapacity(int queueCapacity);

	public void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine);

}