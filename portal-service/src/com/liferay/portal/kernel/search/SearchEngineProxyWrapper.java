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

import com.liferay.portal.kernel.cluster.Priority;

/**
 * @author Michael C. Han
 */
public class SearchEngineProxyWrapper implements SearchEngine {

	public SearchEngineProxyWrapper(
		SearchEngine searchEngine, IndexSearcher indexSearcher,
		IndexWriter indexWriter) {

		_searchEngine = searchEngine;
		_indexSearcher = indexSearcher;
		_indexWriter = indexWriter;
	}

	@Override
	public String backup(long companyId, String backupName)
		throws SearchException {

		return _searchEngine.backup(companyId, backupName);
	}

	@Override
	public BooleanClauseFactory getBooleanClauseFactory() {
		return _searchEngine.getBooleanClauseFactory();
	}

	@Override
	public BooleanQueryFactory getBooleanQueryFactory() {
		return _searchEngine.getBooleanQueryFactory();
	}

	@Override
	public Priority getClusteredWritePriority() {
		return _searchEngine.getClusteredWritePriority();
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	public SearchEngine getSearchEngine() {
		return _searchEngine;
	}

	@Override
	public TermQueryFactory getTermQueryFactory() {
		return _searchEngine.getTermQueryFactory();
	}

	@Override
	public TermRangeQueryFactory getTermRangeQueryFactory() {
		return _searchEngine.getTermRangeQueryFactory();
	}

	@Override
	public String getVendor() {
		return _searchEngine.getVendor();
	}

	@Override
	public void initialize(long companyId) {
		_searchEngine.initialize(companyId);
	}

	@Override
	public boolean isClusteredWrite() {
		return _searchEngine.isClusteredWrite();
	}

	@Override
	public boolean isLuceneBased() {
		return _searchEngine.isLuceneBased();
	}

	@Override
	public void removeBackup(long companyId, String backupName)
		throws SearchException {

		_searchEngine.removeBackup(companyId, backupName);
	}

	@Override
	public void removeCompany(long companyId) {
		_searchEngine.removeCompany(companyId);
	}

	@Override
	public void restore(long companyId, String backupName)
		throws SearchException {

		_searchEngine.restore(companyId, backupName);
	}

	private final IndexSearcher _indexSearcher;
	private final IndexWriter _indexWriter;
	private final SearchEngine _searchEngine;

}