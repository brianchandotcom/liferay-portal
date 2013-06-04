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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.search.BooleanClauseOccur;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Set;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.util.Version;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public interface LuceneHelper {

	public void addDocument(long companyId, Document document)
		throws IOException;

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value);

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue);

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String[] values, boolean like);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur);

	public void addTerm(
		BooleanQuery booleanQuery, String field, String[] values, boolean like);

	public int countScoredFieldNames(Query query, String[] fieldNames);

	public void delete(long companyId);

	public void deleteDocuments(long companyId, Term term) throws IOException;

	public void dumpIndex(long companyId, OutputStream outputStream)
		throws IOException;

	public Analyzer getAnalyzer();

	public IndexAccessor getIndexAccessor(long companyId);

	public long getLastGeneration(long companyId);

	public InputStream getLoadIndexesInputStreamFromCluster(
			long companyId, Address bootupAddress)
		throws SystemException;

	public Set<String> getQueryTerms(Query query);

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException;

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, Formatter formatter)
		throws IOException;

	public Version getVersion();

	public boolean isLoadIndexFromClusterEnabled();

	public void loadIndex(long companyId, InputStream inputStream)
		throws IOException;

	public void loadIndexesFromCluster(long companyId) throws SystemException;

	public void shutdown();

	public void startup(long companyId);

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException;

}