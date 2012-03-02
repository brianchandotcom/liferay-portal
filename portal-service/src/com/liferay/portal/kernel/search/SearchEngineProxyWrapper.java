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

import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Michael C. Han
 */
public class SearchEngineProxyWrapper implements SearchEngine {

	public SearchEngineProxyWrapper(
		SearchEngine searchEngine, IndexSearcher indexSearcher,
		IndexWriter indexWriter) {

		_indexSearcher = indexSearcher;
		_indexWriter = indexWriter;
		_searchEngine = searchEngine;
	}

	public BooleanClauseFactory getBooleanClauseFactory() {
		return _searchEngine.getBooleanClauseFactory();
	}

	public BooleanQueryFactory getBooleanQueryFactory() {
		return _searchEngine.getBooleanQueryFactory();
	}

	public Priority getClusteredWritePriority() {
		return _searchEngine.getClusteredWritePriority();
	}

	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	public SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	public TermQueryFactory getTermQueryFactory() {
		return _searchEngine.getTermQueryFactory();
	}

	public TermRangeQueryFactory getTermRangeQueryFactory() {
		return _searchEngine.getTermRangeQueryFactory();
	}

	public String getVendor() {
		return _searchEngine.getVendor();
	}

	public boolean isClusteredWrite() {
		return _searchEngine.isClusteredWrite();
	}

	public boolean isLuceneBased() {
		return _searchEngine.isLuceneBased();
	}

	private IndexSearcher _indexSearcher;
	private IndexWriter _indexWriter;
	private SearchEngine _searchEngine;

}