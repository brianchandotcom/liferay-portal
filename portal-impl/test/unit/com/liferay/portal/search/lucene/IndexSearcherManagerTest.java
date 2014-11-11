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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.test.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.NewEnv;
import com.liferay.portal.test.AdviseWith;
import com.liferay.portal.test.AspectJNewEnvMethodRule;

import java.io.IOException;

import java.lang.Thread.State;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;

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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class IndexSearcherManagerTest {

	@ClassRule
	public static CodeCoverageAssertor codeCoverageAssertor =
		new CodeCoverageAssertor();

	@Before
	public void setUp() throws Exception {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
			Version.LUCENE_35, new StandardAnalyzer(Version.LUCENE_35));

		_directory = new RAMDirectory();

		_indexWriter = new IndexWriter(_directory, indexWriterConfig);

		// Workaround for LUCENE-2386

		_indexWriter.commit();

		_indexSearcherManager = new IndexSearcherManager(_indexWriter);
	}

	@AdviseWith(adviceClasses = {IndexReaderAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testAcquire() throws Exception {
		IndexSearcher indexSearcher = _indexSearcherManager.acquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCount = indexReader.getRefCount();

		Assert.assertSame(indexSearcher, _indexSearcherManager.acquire());
		Assert.assertEquals(referenceCount + 1, indexReader.getRefCount());

		// Concurrent aquire, double check locking

		final String fieldValue = "testvalue";

		_addDocument(fieldValue);

		_indexSearcherManager.invalidate();

		Thread thread = new Thread("Double Check Locking") {

			@Override
			public void run() {
				try {
					_assertHits(fieldValue, 1);
				}
				catch (Exception e) {
					Assert.fail();
				}
			}

		};

		synchronized (_indexSearcherManager) {
			thread.start();

			while (thread.getState() != State.BLOCKED);

			_assertHits(fieldValue, 1);
		}

		thread.join();

		// Concurrent aquire, reopen

		_drainRefs(1);

		IndexReaderAdvice.block();

		FutureTask<IndexSearcher> futureTask1 = new FutureTask<IndexSearcher>(
			new Callable<IndexSearcher>() {

				@Override
				public IndexSearcher call() throws Exception {
					return _indexSearcherManager.acquire();
				}

			});

		thread = new Thread(futureTask1, "Concurrent Reopen 1");

		thread.start();

		IndexReaderAdvice.waitUntilBlock(1);

		_addDocument(fieldValue);

		_indexSearcherManager.invalidate();

		FutureTask<IndexSearcher> futureTask2 = new FutureTask<IndexSearcher>(
			new Callable<IndexSearcher>() {

				@Override
				public IndexSearcher call() throws Exception {
					return _indexSearcherManager.acquire();
				}

			});

		thread = new Thread(futureTask2, "Concurrent Reopen 2");

		thread.start();

		IndexReaderAdvice.waitUntilBlock(2);

		IndexReaderAdvice.unblock(2);

		Assert.assertSame(futureTask1.get(), futureTask2.get());
		Assert.assertNotSame(indexSearcher, futureTask1.get());

		// Externally closed

		_drainRefs(0);

		try {
			_indexSearcherManager.acquire();

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Index reader was closed externally", ise.getMessage());
		}
	}

	@Test
	public void testClose() throws Exception {
		_indexSearcherManager = new IndexSearcherManager(_directory);

		IndexSearcher indexSearcher = _indexSearcherManager.acquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCount = indexReader.getRefCount();

		_indexSearcherManager.close();

		Assert.assertEquals(referenceCount - 1, indexReader.getRefCount());

		try {
			_indexSearcherManager.acquire();

			Assert.fail();
		}
		catch (AlreadyClosedException ace) {
			Assert.assertEquals(
				"Index searcher manager is closed", ace.getMessage());
		}

		_indexSearcherManager.invalidate();

		try {
			_indexSearcherManager.acquire();

			Assert.fail();
		}
		catch (AlreadyClosedException ace) {
			Assert.assertEquals(
				"Index searcher manager is closed", ace.getMessage());
		}
	}

	@Test
	public void testInvalidate() throws Exception {
		String fieldValue = "test";

		_addDocument(fieldValue);

		_assertHits(fieldValue, 0);

		_indexSearcherManager.invalidate();

		IndexSearcher indexSearcher = _assertHits(fieldValue, 1);

		_indexSearcherManager.invalidate();

		Assert.assertSame(indexSearcher, _indexSearcherManager.acquire());
	}

	@Test
	public void testRelase() throws Exception {
		_indexSearcherManager.release(null);

		IndexSearcher indexSearcher = _indexSearcherManager.acquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCount = indexReader.getRefCount();

		_indexSearcherManager.release(indexSearcher);

		Assert.assertEquals(referenceCount - 1, indexReader.getRefCount());
	}

	@Rule
	public final AspectJNewEnvMethodRule aspectJNewEnvMethodRule =
		new AspectJNewEnvMethodRule();

	@Aspect
	public static class IndexReaderAdvice {

		public static void block() {
			_semaphore = new Semaphore(0);
		}

		public static void unblock(int permits) {
			Semaphore semaphore = _semaphore;

			_semaphore = null;

			semaphore.release(permits);
		}

		public static void waitUntilBlock(int threadCount) {
			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				while (semaphore.getQueueLength() < threadCount);
			}
		}

		@Around(
			"execution(public boolean org.apache.lucene.index.IndexReader." +
				"tryIncRef())")
		public Object tryIncRef(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			Semaphore semaphore = _semaphore;

			if (semaphore != null) {
				semaphore.acquire();
			}

			return proceedingJoinPoint.proceed();
		}

		private static volatile Semaphore _semaphore;

	}

	private void _addDocument(String fieldValue) throws Exception {
		Document document = new Document();

		Field field = new Field(
			_FIELD_NAME, fieldValue, Field.Store.YES, Field.Index.ANALYZED);

		document.add(field);

		_indexWriter.addDocument(document);

		_indexWriter.commit();
	}

	private IndexSearcher _assertHits(String fieldValue, int totalHits)
		throws Exception {

		IndexSearcher indexSearcher = _indexSearcherManager.acquire();

		Term term = new Term(_FIELD_NAME, fieldValue);

		TermQuery termQuery = new TermQuery(term);

		TopDocs topDocs = indexSearcher.search(termQuery, 1);

		Assert.assertEquals(totalHits, topDocs.totalHits);

		return indexSearcher;
	}

	private void _drainRefs(int leftCount) throws IOException {
		IndexSearcher indexSearcher = _indexSearcherManager.acquire();

		IndexReader indexReader = indexSearcher.getIndexReader();

		int referenceCount = indexReader.getRefCount();

		while (referenceCount-- > leftCount) {
			indexReader.decRef();
		}
	}

	private static final String _FIELD_NAME = "fieldName";

	private Directory _directory;
	private IndexSearcherManager _indexSearcherManager;
	private IndexWriter _indexWriter;

}