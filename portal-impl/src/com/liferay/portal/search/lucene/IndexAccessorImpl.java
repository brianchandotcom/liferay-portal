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
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.dump.DumpIndexDeletionPolicy;
import com.liferay.portal.search.lucene.dump.IndexCommitSerializationUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.LimitTokenCountAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.MMapDirectory;
import org.apache.lucene.store.RAMDirectory;

/**
 * @author Harry Mark
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Mate Thurzo
 */
public class IndexAccessorImpl implements IndexAccessor {

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

		close();

		if (PropsValues.INDEX_FORCE_GC_BEFORE_DELETE) {
			System.gc();
		}

		_deleteDirectory();

		_initIndexWriter();
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
		_dumpIndexDeletionPolicy.dump(outputStream, _indexWriter, _commitLock);
	}

	public void enableDumpIndex() {
		_countDownLatch.countDown();
	}

	public long getCompanyId() {
		return _companyId;
	}

	public long getLastGeneration() {
		if (_countDownLatch.getCount() > 0) {
			return DEFAULT_LAST_GENERATION;
		}

		return _dumpIndexDeletionPolicy.getLastGeneration();
	}

	public Directory getLuceneDir() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			return _getLuceneDirFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			return _getLuceneDirRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	public void loadIndex(InputStream inputStream) throws IOException {
		File tempFile = FileUtil.createTempFile();

		Directory tempDirectory = FSDirectory.open(tempFile);

		IndexCommitSerializationUtil.deserializeIndex(
			inputStream, tempDirectory);

		close();

		if (PropsValues.INDEX_FORCE_GC_BEFORE_DELETE) {
			System.gc();
		}

		_deleteDirectory();

		for (String file : tempDirectory.listAll()) {
			tempDirectory.copy(getLuceneDir(), file, file);
		}

		_initIndexWriter();

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

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			_deleteFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			_deleteRam();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	private void _deleteFile() {
		String path = _getPath();

		try {
			Directory directory = _getDirectory(path);

			directory.close();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Could not close directory " + path);
			}
		}

		FileUtil.deltree(path);
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

	private FSDirectory _getDirectory(String path) throws IOException {
		if (PropsValues.LUCENE_STORE_TYPE_FILE_FORCE_MMAP) {
			return new MMapDirectory(new File(path));
		}
		else {
			return FSDirectory.open(new File(path));
		}
	}

	private Directory _getLuceneDirFile() {
		Directory directory = null;

		String path = _getPath();

		try {
			directory = _getDirectory(path);
		}
		catch (IOException ioe1) {
			if (directory != null) {
				try {
					directory.close();
				}
				catch (Exception e) {
				}
			}
		}

		return directory;
	}

	private Directory _getLuceneDirRam() {
		String path = _getPath();

		Directory directory = _ramDirectories.get(path);

		if (directory == null) {
			directory = new RAMDirectory();

			_ramDirectories.put(path, directory);
		}

		return directory;
	}

	private String _getPath() {
		return PropsValues.LUCENE_DIR.concat(String.valueOf(_companyId)).concat(
			StringPool.SLASH);
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
			LimitTokenCountAnalyzer analyzer = new LimitTokenCountAnalyzer(
				LuceneHelperUtil.getAnalyzer(), _DEFAULT_MAX_FIELD_LENGTH);

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				LuceneHelperUtil.getVersion(), analyzer);

			// Merge Policy

			String mergePolicyClassName = PropsUtil.get(
				PropsKeys.LUCENE_MERGE_POLICY);

			ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

			MergePolicy mergePolicy = (MergePolicy)InstanceFactory.newInstance(
				classLoader, mergePolicyClassName);

			if (mergePolicy instanceof LogMergePolicy) {
				LogMergePolicy logMergePolicy = (LogMergePolicy)mergePolicy;

				logMergePolicy.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
			}

			indexWriterConfig.setMergePolicy(mergePolicy);

			// Buffer Size

			indexWriterConfig.setRAMBufferSizeMB(
				PropsValues.LUCENE_BUFFER_SIZE);

			// Index Deletion Policy

			indexWriterConfig.setIndexDeletionPolicy(_dumpIndexDeletionPolicy);

			_indexWriter = new IndexWriter(getLuceneDir(), indexWriterConfig);

			// LUCENE-2386

			IndexReader indexReader = IndexReader.open(_indexWriter, false);

			if (!indexReader.indexExists(getLuceneDir())) {
				if (_log.isDebugEnabled()) {
					_log.debug("Index file does not exists, creating empty" +
						" index, please reindex to make it up-to-date.");
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

	private static final int _DEFAULT_MAX_FIELD_LENGTH = 10000;

	private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_STORE_TYPE_RAM = "ram";

	private static Log _log = LogFactoryUtil.getLog(IndexAccessorImpl.class);

	private volatile int _batchCount;
	private Lock _commitLock = new ReentrantLock();
	private long _companyId;
	private CountDownLatch _countDownLatch = new CountDownLatch(1);
	private DumpIndexDeletionPolicy _dumpIndexDeletionPolicy =
		new DumpIndexDeletionPolicy();
	private IndexWriter _indexWriter;
	private Map<String, Directory> _ramDirectories =
		new ConcurrentHashMap<String, Directory>();

}