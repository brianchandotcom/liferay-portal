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

package com.liferay.portal.search.lucene.dump;

import com.liferay.portal.search.lucene.IndexAccessorImpl;
import com.liferay.portal.util.BaseTestCase;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

/**
 * @author Shuyang Zhou
 */
public class IndexAccessorImplTest extends BaseTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_documentsCount = PropsValues.LUCENE_COMMIT_BATCH_SIZE;

		if (_documentsCount == 0) {
			_documentsCount = 1;
		}

		_indexAccessorImpl = new IndexAccessorImpl(_TEST_COMPANY_ID);
	}

	@Override
	public void tearDown() throws Exception {
		super.tearDown();

		_indexAccessorImpl.delete();
		_indexAccessorImpl.close();
	}

	public void testEmptyDump() throws Exception {
		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
	}

	public void testOneCommitDump() throws Exception {
		_addDocuments("test");

		_assertHits("test", true);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_assertHits("test", true);
	}

	public void testThreeCommitsDump() throws Exception {
		_addDocuments("test1");
		_addDocuments("test2");
		_addDocuments("test3");

		_assertHits("test1", true);
		_assertHits("test2", true);
		_assertHits("test3", true);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_assertHits("test1", true);
		_assertHits("test2", true);
		_assertHits("test3", true);
	}

	public void testThreeCommitsOneDeletionDump() throws Exception {
		_addDocuments("test1");
		_addDocuments("test2");
		_addDocuments("test3");

		_deleteDocuments("test2");

		_assertHits("test1", true);
		_assertHits("test2", false);
		_assertHits("test3", true);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_assertHits("test1", true);
		_assertHits("test2", false);
		_assertHits("test3", true);
	}

	public void testThreeCommitsTwoDeletionsDump() throws Exception {
		_addDocuments("test1");
		_addDocuments("test2");
		_addDocuments("test3");

		_deleteDocuments("test2");
		_deleteDocuments("test3");

		_assertHits("test1", true);
		_assertHits("test2", false);
		_assertHits("test3", false);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_assertHits("test1", true);
		_assertHits("test2", false);
		_assertHits("test3", false);
	}

	public void testTwoCommitsDump() throws Exception {
		_addDocuments("test1");
		_addDocuments("test2");

		_assertHits("test1", true);
		_assertHits("test2", true);

		ByteArrayOutputStream byteArrayOutputStream =
			new ByteArrayOutputStream();

		_indexAccessorImpl.dumpIndex(byteArrayOutputStream);

		_indexAccessorImpl.loadIndex(
			new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));

		_assertHits("test1", true);
		_assertHits("test2", true);
	}

	private void _addDocuments(String key) throws Exception {
		for (int i = 0; i < _documentsCount; i++) {
			Document document = new Document();

			Field field = new Field(
				"name", key + i, Field.Store.YES, Field.Index.ANALYZED);

			document.add(field);

			_indexAccessorImpl.addDocument(document);
		}
	}

	private void _assertHits(String key, boolean expectHit) throws Exception {
		IndexSearcher indexSearcher = new IndexSearcher(
			_indexAccessorImpl.getLuceneDir());

		for (int i = 0; i < _documentsCount * 2; i++) {
			Term term = new Term("name", key + i);

			TermQuery termQuery = new TermQuery(term);

			TopDocs topDocs = indexSearcher.search(termQuery, 1);

			if (i < _documentsCount) {
				if (expectHit) {
					assertEquals(1, topDocs.totalHits);
				}
				else {
					assertEquals(0, topDocs.totalHits);
				}
			}
			else {
				assertEquals(0, topDocs.totalHits);
			}
		}

		indexSearcher.close();
	}

	private void _deleteDocuments(String key) throws Exception {
		for (int i = 0; i < _documentsCount; i++) {
			Term term = new Term("name", key + i);

			_indexAccessorImpl.deleteDocuments(term);
		}
	}

	private static final long _TEST_COMPANY_ID = 1000;

	private int _documentsCount;
	private IndexAccessorImpl _indexAccessorImpl;

}