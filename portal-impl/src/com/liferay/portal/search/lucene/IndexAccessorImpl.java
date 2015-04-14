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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.rpc.IntrabandRPCUtil;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.resiliency.mpi.MPIHelperUtil;
import com.liferay.portal.kernel.resiliency.spi.SPI;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.dump.DumpIndexDeletionPolicy;
import com.liferay.portal.search.lucene.dump.IndexCommitSerializationUtil;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;

import java.util.Collection;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.LimitTokenCountAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.LogMergePolicy;
import org.apache.lucene.index.MergePolicy;
import org.apache.lucene.index.MergeScheduler;
import org.apache.lucene.index.NoMergePolicy;
import org.apache.lucene.index.NoMergeScheduler;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
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

		_path = PropsValues.LUCENE_DIR + _companyId + StringPool.SLASH;

		IndexSearcherManager indexSearcherManager = null;

		try {
			_checkLuceneDir();
			_initIndexWriter();
			_initCommitScheduler();

			indexSearcherManager = new IndexSearcherManager(_indexWriter);
		}
		catch (IOException ioe) {
			_log.error(
				"Unable to initialize index searcher manager for company " +
					_companyId,
				ioe);
		}

		_indexSearcherManager = indexSearcherManager;
	}

	@Override
	public IndexSearcher acquireIndexSearcher() throws IOException {
		return _indexSearcherManager.acquire();
	}

	@Override
	public void addDocument(Document document) throws IOException {
		_write(null, document);
	}

	@Override
	public void addDocuments(Collection<Document> documents)
		throws IOException {

		try {
			for (Document document : documents) {
				_indexWriter.addDocument(document);
			}

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	@Override
	public void close() {
		try {
			_indexSearcherManager.close();

			_indexWriter.close();

			_directory.close();
		}
		catch (Exception e) {
			_log.error("Closing Lucene writer failed for " + _companyId, e);
		}

		if (_scheduledExecutorService != null) {
			_scheduledExecutorService.shutdownNow();
		}
	}

	@Override
	public void delete() {
		_deleteDirectory();
	}

	@Override
	public void deleteDocuments(Term term) throws IOException {
		try {
			_indexWriter.deleteDocuments(term);

			_batchCount++;
		}
		finally {
			_commit();
		}
	}

	@Override
	public void dumpIndex(OutputStream outputStream) throws IOException {
		try {
			_dumpIndexDeletionPolicy.dump(
				outputStream, _indexWriter, _commitLock);
		}
		finally {
			_indexSearcherManager.invalidate();
		}
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public long getLastGeneration() {
		return _dumpIndexDeletionPolicy.getLastGeneration();
	}

	@Override
	public Directory getLuceneDir() {
		if (_directory != null) {
			return _directory;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE)) {
			_directory = _getLuceneDirFile();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {
			_directory = new RAMDirectory();
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		return _directory;
	}

	@Override
	public void invalidate() {
		_indexSearcherManager.invalidate();
	}

	@Override
	public void loadIndex(InputStream inputStream) throws IOException {
		File tempFile = FileUtil.createTempFile();

		try (Directory tempDirectory = FSDirectory.open(tempFile)) {
			IndexCommitSerializationUtil.deserializeIndex(
				inputStream, tempDirectory);

			_deleteDirectory();

			try (IndexReader indexReader = IndexReader.open(
					tempDirectory, false)) {

				if (indexReader.numDocs() > 0) {
					try (IndexSearcher indexSearcher = new IndexSearcher(
							indexReader)) {

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
				}

				indexReader.flush();
			}
		}

		FileUtil.deltree(tempFile);
	}

	@Override
	public void releaseIndexSearcher(IndexSearcher indexSearcher)
		throws IOException {

		_indexSearcherManager.release(indexSearcher);
	}

	@Override
	public void updateDocument(Term term, Document document)
		throws IOException {

		if (_log.isDebugEnabled()) {
			_log.debug("Indexing " + document);
		}

		_write(term, document);
	}

	protected static LuceneHelper luceneHelper;

	private static void _invalidate(long companyId) {
		for (SPI spi : MPIHelperUtil.getSPIs()) {
			try {
				RegistrationReference registrationReference =
					spi.getRegistrationReference();

				IntrabandRPCUtil.execute(
					registrationReference,
					new InvalidateProcessCallable(companyId));
			}
			catch (Exception e) {
				_log.error(
					"Unable to invalidate SPI " + spi + " for company " +
						companyId,
					e);
			}
		}
	}

	private void _checkLuceneDir() {
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

	private void _deleteAll() {
		try {
			_indexWriter.deleteAll();

			_doCommit();
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to delete index in directory " + _path);
			}
		}
	}

	private void _deleteDirectory() {
		if (_log.isDebugEnabled()) {
			_log.debug("Lucene store type " + PropsValues.LUCENE_STORE_TYPE);
		}

		if (PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_FILE) ||
			PropsValues.LUCENE_STORE_TYPE.equals(_LUCENE_STORE_TYPE_RAM)) {

			_deleteAll();
		}
		else if (PropsValues.LUCENE_STORE_TYPE.equals(
					_LUCENE_STORE_TYPE_JDBC)) {

			throw new IllegalArgumentException(
				"Store type JDBC is no longer supported in favor of SOLR");
		}
		else {
			throw new RuntimeException(
				"Invalid store type " + PropsValues.LUCENE_STORE_TYPE);
		}
	}

	private void _doCommit() throws IOException {
		_commitLock.lock();

		try {
			_indexWriter.commit();
		}
		finally {
			_commitLock.unlock();

			_indexSearcherManager.invalidate();

			_invalidate(_companyId);
		}

		_batchCount = 0;
	}

	private Directory _getLuceneDirFile() {
		Directory directory = null;

		try {
			if (PropsValues.LUCENE_STORE_TYPE_FILE_FORCE_MMAP) {
				directory = new MMapDirectory(new File(_path));
			}
			else {
				directory = FSDirectory.open(new File(_path));
			}
		}
		catch (IOException ioe) {
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

	private MergePolicy _getMergePolicy() throws Exception {
		if (PropsValues.LUCENE_MERGE_POLICY.equals(
				NoMergePolicy.class.getName())) {

			return NoMergePolicy.NO_COMPOUND_FILES;
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		MergePolicy mergePolicy = (MergePolicy)InstanceFactory.newInstance(
			classLoader, PropsValues.LUCENE_MERGE_POLICY);

		if (mergePolicy instanceof LogMergePolicy) {
			LogMergePolicy logMergePolicy = (LogMergePolicy)mergePolicy;

			logMergePolicy.setMergeFactor(PropsValues.LUCENE_MERGE_FACTOR);
		}

		return mergePolicy;
	}

	private MergeScheduler _getMergeScheduler() throws Exception {
		if (PropsValues.LUCENE_MERGE_SCHEDULER.equals(
				NoMergeScheduler.class.getName())) {

			return NoMergeScheduler.INSTANCE;
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		return (MergeScheduler)InstanceFactory.newInstance(
			classLoader, PropsValues.LUCENE_MERGE_SCHEDULER);
	}

	private void _initCommitScheduler() {
		if ((PropsValues.LUCENE_COMMIT_BATCH_SIZE <= 0) ||
			(PropsValues.LUCENE_COMMIT_TIME_INTERVAL <= 0)) {

			return;
		}

		_scheduledExecutorService =
			Executors.newSingleThreadScheduledExecutor();

		Runnable runnable = new Runnable() {

			@Override
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

		_scheduledExecutorService.scheduleWithFixedDelay(
			runnable, 0, PropsValues.LUCENE_COMMIT_TIME_INTERVAL,
			TimeUnit.MILLISECONDS);
	}

	private void _initIndexWriter() {
		try {
			Analyzer analyzer = new LimitTokenCountAnalyzer(
				luceneHelper.getAnalyzer(),
				PropsValues.LUCENE_ANALYZER_MAX_TOKENS);

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(
				luceneHelper.getVersion(), analyzer);

			indexWriterConfig.setIndexDeletionPolicy(_dumpIndexDeletionPolicy);
			indexWriterConfig.setMergePolicy(_getMergePolicy());
			indexWriterConfig.setMergeScheduler(_getMergeScheduler());
			indexWriterConfig.setRAMBufferSizeMB(
				PropsValues.LUCENE_BUFFER_SIZE);

			_indexWriter = new IndexWriter(getLuceneDir(), indexWriterConfig);

			if (!IndexReader.indexExists(getLuceneDir())) {

				// Workaround for LUCENE-2386

				if (_log.isDebugEnabled()) {
					_log.debug("Creating missing index");
				}

				_indexWriter.commit();
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

	private static final String _LUCENE_STORE_TYPE_FILE = "file";

	private static final String _LUCENE_STORE_TYPE_JDBC = "jdbc";

	private static final String _LUCENE_STORE_TYPE_RAM = "ram";

	private static final Log _log = LogFactoryUtil.getLog(
		IndexAccessorImpl.class);

	private volatile int _batchCount;
	private final Lock _commitLock = new ReentrantLock();
	private long _companyId;
	private Directory _directory;
	private final DumpIndexDeletionPolicy _dumpIndexDeletionPolicy =
		new DumpIndexDeletionPolicy();
	private final IndexSearcherManager _indexSearcherManager;
	private IndexWriter _indexWriter;
	private final String _path;
	private ScheduledExecutorService _scheduledExecutorService;

	private static class InvalidateProcessCallable
		implements ProcessCallable<Serializable> {

		public InvalidateProcessCallable(long companyId) {
			_companyId = companyId;
		}

		@Override
		public Serializable call() {
			IndexAccessor indexAccessor = luceneHelper.getIndexAccessor(
				_companyId);

			indexAccessor.invalidate();

			_invalidate(_companyId);

			return null;
		}

		private static final long serialVersionUID = 1L;

		private final long _companyId;

	}

}