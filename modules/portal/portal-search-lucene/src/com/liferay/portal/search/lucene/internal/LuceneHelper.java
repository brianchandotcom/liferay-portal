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

package com.liferay.portal.search.lucene.internal;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;
import java.util.Set;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Andrea Di Giorgi
 */
public interface LuceneHelper {

	public void addDate(Document document, String field, Date value);

	public void addDocument(long companyId, Document document)
		throws IOException;

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #releaseIndexSearcher(long,
	 *             IndexSearcher)}
	 */
	@Deprecated
	public void cleanUp(IndexSearcher indexSearcher);

	public int countScoredFieldNames(Query query, String[] fieldNames);

	public void delete(long companyId);

	public void deleteDocuments(long companyId, Term term) throws IOException;

	public void dumpIndex(long companyId, OutputStream outputStream)
		throws IOException;

	public IndexAccessor getIndexAccessor(long companyId);

	public IndexSearcher getIndexSearcher(long companyId) throws IOException;

	public long getLastGeneration(long companyId);

	public Set<String> getQueryTerms(Query query);

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getIndexSearcher(long)}
	 */
	@Deprecated
	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException;

	public String getSnippet(Query query, String field, String s)
		throws IOException;

	public String getSnippet(
			Query query, String field, String s, Formatter formatter)
		throws IOException;

	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, Formatter formatter)
		throws IOException;

	public void loadIndex(long companyId, InputStream inputStream)
		throws IOException;

	public void releaseIndexSearcher(
			long companyId, IndexSearcher indexSearcher)
		throws IOException;

	public void shutdown(long companyId);

	public void startup(long companyId);

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException;

}