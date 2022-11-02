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

package com.liferay.portal.search.solr8.internal;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.IndexSearcher;
import com.liferay.portal.kernel.search.IndexWriter;
import com.liferay.portal.kernel.search.SearchEngine;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Michael C. Han
 */
@Component(property = "search.engine.impl=Solr", service = SearchEngine.class)
public class SolrSearchEngine implements SearchEngine {

	@Override
	public synchronized String backup(long companyId, String backupName) {
		return StringPool.BLANK;
	}

	@Override
	public IndexSearcher getIndexSearcher() {
		return _indexSearcher;
	}

	@Override
	public IndexWriter getIndexWriter() {
		return _indexWriter;
	}

	@Override
	public String getVendor() {
		return "Solr";
	}

	@Override
	public void initialize(long companyId) {
	}

	@Override
	public synchronized void removeBackup(long companyId, String backupName) {
	}

	@Override
	public void removeCompany(long companyId) {
	}

	@Override
	public synchronized void restore(long companyId, String backupName) {
	}

	@Reference(target = "(search.engine.impl=Solr)")
	private IndexSearcher _indexSearcher;

	@Reference(target = "(search.engine.impl=Solr)")
	private IndexWriter _indexWriter;

}