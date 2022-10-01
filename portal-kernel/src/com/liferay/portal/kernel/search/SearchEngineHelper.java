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

	public static final String SYSTEM_ENGINE_ID = "SYSTEM_ENGINE";

	public String[] getEntryClassNames();

	public SearchEngine getSearchEngine(String searchEngineId);

	public Set<String> getSearchEngineIds();

	public Collection<SearchEngine> getSearchEngines();

	public void initialize(long companyId);

	public void removeCompany(long companyId);

	public SearchEngine removeSearchEngine(String searchEngineId);

	public void setSearchEngine(
		String searchEngineId, SearchEngine searchEngine);

}