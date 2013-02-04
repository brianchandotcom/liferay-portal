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

package com.liferay.portal.search.lucene;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.dump.IndexCommitSerializationUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

/**
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Mate Thurzo
 */
public class IndexAccessorImpl extends BaseIndexAccessorAdapter
	implements IndexAccessor {

	public IndexAccessorImpl(long companyId) {

		_companyId = companyId;
		_checkLuceneDir();
		_initIndexWriter();
		_initCommitScheduler();
	}

	public void addDocument(Document document) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		_write(null, document);
	}

	public void close() {
		try {
			_indexWriter.close();
		}
		catch (Exception e) {
			_log.error("Closing Lucene writer failed for " + _companyId, e);
		}
	}

	public void delete() {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		_deleteDirectory();
	}

	public void deleteDocuments(Term term) throws IOException {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		try {
			_indexWriter.deleteDocuments(term);

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	public void dumpIndex(OutputStream outputStream) throws IOException {
		dumpIndexDeletionPolicy.dump(outputStream, _indexWriter, _commitLock);
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getLastGeneration() {
		return dumpIndexDeletionPolicy.getLastGeneration();
	}

	public Directory getLuceneDir() {
		return getIndexDirectory();
	}

	public void loadIndex(InputStream inputStream) throws IOException {
		File tempFile = FileUtil.createTempFile();

		Directory tempDirectory = FSDirectory.open(tempFile);

		IndexCommitSerializationUtil.deserializeIndex(
			inputStream, tempDirectory);

		_deleteDirectory();

		IndexReader indexReader = IndexReader.open(tempDirectory, false);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		try {
			TopDocs topDocs = indexSearcher.search(
				new MatchAllDocsQuery(), indexReader.numDocs());

			ScoreDoc[] scoreDocs = topDocs.scoreDocs;

			for (ScoreDoc scoreDoc : scoreDocs) {
				Document document = indexSearcher.doc(scoreDoc.doc);

				addDocument(document);
			}
		}
		catch (IllegalArgumentException iae) {
			if (_log.isDebugEnabled()) {
				_log.debug(iae.getMessage());
			}
		}

		indexSearcher.close();

		indexReader.flush();
		indexReader.close();

		tempDirectory.close();

		FileUtil.deltree(tempFile);
	}

	public void updateDocument(Term term, Document document)
		throws IOException {

		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing " + document);
		}

		_write(term, document);
	}

	@Override
	protected String doGetPath() {
		return PropsValues.LUCENE_DIR.concat(String.valueOf(_companyId)).concat(
			StringPool.SLASH);
	}

	private void _checkLuceneDir() {
		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		try {
			Directory directory = getLuceneDir();

			if (IndexWriter.isLocked(directory)) {
				IndexWriter.unlock(directory);
			}
		}
		catch (Exception e) {
			_log.error("Check Lucene directory failed for " + _companyId, e);
		}
	}

	private void _commit() throws IOException {
		if ((PropsValues.LUCENE_COMMIT_BATCH_SIZE == 0) ||
			(PropsValues.LUCENE_COMMIT_BATCH_SIZE <= _batchCount)) {

			_doCommit();
		}
	}

	private void _deleteDirectory() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_FILE)) {
			_deleteFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(LUCENE_STORE_TYPE_RAM)) {
			_deleteRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	private void _deleteFile() {
		String path = doGetPath();

		try {
			_indexWriter.deleteAll();

			// Ensuring that all the changes has been applied to the index

			_indexWriter.commit();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not delete index in directory " + path);
			}
		}
	}

	private void _deleteRam() {
	}

	private void _doCommit() throws IOException {
		if (_indexWriter != null) {
			_commitLock.lock();

			try {
				_indexWriter.commit();
			}
			finally {
				_commitLock.unlock();
			}
		}

		_batchCount = 0;
	}

	private void _initCommitScheduler() {
		if ((PropsValues.LUCENE_COMMIT_BATCH_SIZE <= 0) ||
			(PropsValues.LUCENE_COMMIT_TIME_INTERVAL <= 0)) {

			return;
		}

		ScheduledExecutorService scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		Runnable runnable = new Runnable() {

			public void run() {
				try {
					if (_batchCount > 0) {
						_doCommit();
					}
				}
				catch (IOException ioe) {
					_log.error("Could not run scheduled commit", ioe);
				}
			}

		};

		scheduledExecutorService.scheduleWithFixedDelay(
			runnable, 0, PropsValues.LUCENE_COMMIT_TIME_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	private void _initIndexWriter() {
		try {
			IndexWriterConfig indexWriterConfig = getIndexWriterConfig();

			_indexWriter = new IndexWriter(getLuceneDir(), indexWriterConfig);

			if (!IndexReader.indexExists(getLuceneDir())) {

				// Workaround for LUCENE-2386

				if (_log.isDebugEnabled()) {
					_log.debug("Creating missing index");
				}

				_doCommit();
			}
		}
		catch (Exception e) {
			_log.error(
				"Initializing Lucene writer failed for " + _companyId, e);
		}
	}

	private void _write(Term term, Document document) throws IOException {
		try {
			if (term != null) {
				_indexWriter.updateDocument(term, document);
			}
			else {
				_indexWriter.addDocument(document);
			}

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	private static Log _log = LogFactoryUtil.getLog(IndexAccessorImpl.class);

	private volatile int _batchCount;
	private Lock _commitLock = new ReentrantLock();
	private long _companyId;
	private IndexWriter _indexWriter;

}