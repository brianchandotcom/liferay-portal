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

import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.Directory;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class IndexSearcherManager {

	public IndexSearcherManager(Directory directory) throws IOException {
		_indexSearcher = _createIndexSearcher(
			IndexReader.open(directory, true));
	}

	public IndexSearcherManager(IndexWriter writer) throws IOException {
		_indexSearcher = _createIndexSearcher(IndexReader.open(writer, true));
	}

	public IndexSearcher acquire() throws IOException {
		if (_invalid) {
			synchronized (this) {
				if (_invalid) {
					IndexSearcher indexSearcher = _indexSearcher;

					if (indexSearcher == null) {
						throw new AlreadyClosedException(
							"Index searcher manager is closed");
					}

					IndexReader newIndexReader = IndexReader.openIfChanged(
						indexSearcher.getIndexReader());

					if (newIndexReader != null) {
						_indexSearcher = _createIndexSearcher(newIndexReader);

						release(indexSearcher);
					}

					_invalid = false;
				}
			}
		}

		IndexSearcher indexSearcher = null;

		while ((indexSearcher = _indexSearcher) != null) {
			IndexReader indexReader = indexSearcher.getIndexReader();

			if (indexReader.tryIncRef()) {
				return indexSearcher;
			}

			if (indexSearcher == _indexSearcher) {
				throw new IllegalStateException(
					"Index reader was closed externally");
			}
		}

		throw new AlreadyClosedException("Index searcher manager is closed");
	}

	public synchronized void close() throws IOException {
		IndexSearcher indexSearcher = _indexSearcher;

		_indexSearcher = null;

		release(indexSearcher);
	}

	public synchronized void invalidate() {
		_invalid = true;
	}

	public void release(IndexSearcher indexSearcher) throws IOException {
		if (indexSearcher == null) {
			return;
		}

		IndexReader indexReader = indexSearcher.getIndexReader();

		indexReader.decRef();
	}

	private IndexSearcher _createIndexSearcher(IndexReader indexReader) {
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		indexSearcher.setDefaultFieldSortScoring(true, false);
		indexSearcher.setSimilarity(new FieldWeightSimilarity());

		return indexSearcher;
	}

	private volatile IndexSearcher _indexSearcher;
	private volatile boolean _invalid;

}