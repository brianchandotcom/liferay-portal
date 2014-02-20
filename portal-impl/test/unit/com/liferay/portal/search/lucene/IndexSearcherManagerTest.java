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

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;

import java.lang.Thread.State;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.AlreadyClosedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class IndexSearcherManagerTest {

	@Before
	public void setUp() throws Exception {
		PortalExecutorManagerUtil portalExecutorManagerUtil =
			new PortalExecutorManagerUtil();

		portalExecutorManagerUtil.setPortalExecutorManager(
			new MockPortalExecutorManager());

		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
			Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

		_indexWriter = new IndexWriter(new RAMDirectory(), indexWriterConfig);

		_indexSearcherManager = new IndexSearcherManager(_indexWriter);
	}

	@Test
	public void testAquire() throws Exception {
		IndexSearcher indexSearcher = _indexSearcherManager.aquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCountBeforeAquire = indexReader.getRefCount();

		_indexSearcherManager.aquire();

		int referenceCountAfterAquire = indexReader.getRefCount();

		Assert.assertEquals(
			referenceCountBeforeAquire + 1, referenceCountAfterAquire);

		// concurrent aquire

		final String fieldValue = "testvalue";

		_addDocument(fieldValue);

		_indexSearcherManager.invalid();

		Thread thread = new Thread() {

			public void run() {
				try {
					IndexSearcher indexSearcher1 =
						_indexSearcherManager.aquire();

					_assertHits(indexSearcher1, fieldValue, 1);
				}
				catch (Exception ex) {
					Assert.fail();
				}
			}

		};

		synchronized (_indexSearcherManager) {
			thread.start();

			while (thread.getState() != State.BLOCKED);

			indexSearcher = _indexSearcherManager.aquire();

			_assertHits(indexSearcher, fieldValue, 1);
		}

		thread.join();
	}

	@Test
	public void testClose() throws Exception {
		IndexSearcher indexSearcher = _indexSearcherManager.aquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCountBeforeClose = indexReader.getRefCount();

		_indexSearcherManager.close();

		int referenceCountAfterClose = indexReader.getRefCount();

		Assert.assertEquals(
			referenceCountBeforeClose - 1, referenceCountAfterClose);

		try {
			_indexSearcherManager.aquire();

			Assert.fail();
		}
		catch (AlreadyClosedException ace) {
			Assert.assertEquals(
				"IndexSearcherManager is closed", ace.getMessage());
		}

		_indexSearcherManager.invalid();

		try {
			_indexSearcherManager.aquire();

			Assert.fail();
		}
		catch (AlreadyClosedException ace) {
			Assert.assertEquals(
				"IndexSearcherManager is closed", ace.getMessage());
		}

		// close after close

		_indexSearcherManager.close();
	}

	@Test
	public void testInvalid() throws Exception {
		String fieldValue = "testvalue";

		_addDocument(fieldValue);

		IndexSearcher indexSearcher = _indexSearcherManager.aquire();

		_assertHits(indexSearcher, fieldValue, 0);

		_indexSearcherManager.invalid();

		indexSearcher = _indexSearcherManager.aquire();

		_assertHits(indexSearcher, fieldValue, 1);

		_indexSearcherManager.invalid();

		Assert.assertEquals(indexSearcher, _indexSearcherManager.aquire());
	}

	@Test
	public void testRelase() throws Exception {
		IndexSearcher indexSearcher = _indexSearcherManager.aquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCountBeforeRelease = indexReader.getRefCount();

		_indexSearcherManager.release(indexSearcher);

		int referenceCountAfterRelease = indexReader.getRefCount();

		Assert.assertEquals(
			referenceCountBeforeRelease - 1, referenceCountAfterRelease);
	}

	private void _addDocument(String fieldValue) throws Exception {
		Document document = new Document();

		Field field = new Field(
			_FIELD_NAME, fieldValue, Field.Store.YES, Field.Index.ANALYZED);

		document.add(field);

		_indexWriter.addDocument(document);

		_indexWriter.commit();
	}

	private void _assertHits(
			IndexSearcher indexSearcher, String fieldValue, int totalHits)
		throws Exception {

		Term term = new Term(_FIELD_NAME, fieldValue);

		TermQuery termQuery = new TermQuery(term);

		TopDocs topDocs = indexSearcher.search(termQuery, 1);

		Assert.assertEquals(totalHits, topDocs.totalHits);
	}

	private static final String _FIELD_NAME = "fieldName";

	private IndexSearcherManager _indexSearcherManager;
	private IndexWriter _indexWriter;

	private class MockPortalExecutorManager implements PortalExecutorManager {

		@Override
		public <T> Future<T> execute(String name, Callable<T> callable) {
			return null;
		}

		@Override
		public <T> T execute(
			String name, Callable<T> callable, long timeout,
			TimeUnit timeUnit) {

			return null;
		}

		@Override
		public ThreadPoolExecutor getPortalExecutor(String name) {
			return null;
		}

		@Override
		public ThreadPoolExecutor getPortalExecutor(
			String name, boolean createIfAbsent) {

			return null;
		}

		@Override
		public ThreadPoolExecutor registerPortalExecutor(
			String name, ThreadPoolExecutor threadPoolExecutor) {

			return null;
		}

		@Override
		public void shutdown() {
		}

		@Override
		public void shutdown(boolean interrupt) {
		}

		@Override
		public void shutdown(String name) {
		}

		@Override
		public void shutdown(String name, boolean interrupt) {
		}

	}

}