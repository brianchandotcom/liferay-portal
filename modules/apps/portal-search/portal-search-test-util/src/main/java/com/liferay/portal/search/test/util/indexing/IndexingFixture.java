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

package com.liferay.portal.search.test.util.indexing;

import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.search.engine.adapter.SearchEngineAdapter;

/**
 * @author Miguel Angelo Caldas Gallindo
 * @author André de Oliveira
 */
public interface IndexingFixture {

	public long getCompanyId();

	public IndexSearcher getIndexSearcher();

	public IndexWriter getIndexWriter();

	public SearchEngineAdapter getSearchEngineAdapter();

	public boolean isSearchEngineAvailable();

	public void setUp() throws Exception;

	public void tearDown() throws Exception;

}