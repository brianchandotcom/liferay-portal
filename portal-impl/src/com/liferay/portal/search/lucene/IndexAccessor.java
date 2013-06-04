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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Collection;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;

/**
 * @author Bruno Farache
 * @author Shuyang Zhou
 */
public interface IndexAccessor {

	public static final long DEFAULT_LAST_GENERATION = -1;

	public void addDocument(Document document) throws IOException;

	public void addDocuments(Collection<Document> documents) throws IOException;

	public void close();

	public void delete();

	public void deleteDocuments(Term term) throws IOException;

	public void dumpIndex(OutputStream outputStream) throws IOException;

	public long getCompanyId();

	public long getLastGeneration();

	public Directory getLuceneDir();

	public void loadIndex(InputStream inputStream) throws IOException;

	public void updateDocument(Term term, Document document) throws IOException;

}