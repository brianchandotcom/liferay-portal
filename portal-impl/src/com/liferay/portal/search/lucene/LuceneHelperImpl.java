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

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.SearchEngineInitializer;
import com.liferay.portal.search.lucene.highlight.QueryTermExtractor;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang.time.StopWatch;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.WeightedTerm;
import org.apache.lucene.util.Version;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
public class LuceneHelperImpl implements LuceneHelper {

	public LuceneHelperImpl() {
		if (PropsValues.INDEX_ON_STARTUP && PropsValues.INDEX_WITH_THREAD) {
			_luceneIndexThreadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					LuceneHelperImpl.class.getName());
		}

		BooleanQuery.setMaxClauseCount(_LUCENE_BOOLEAN_QUERY_CLAUSE_MAX_SIZE);

		IndexAccessorImpl.luceneHelper = this;
	}

	@Override
	public void addDate(Document doc, String field, Date value) {
		doc.add(LuceneFields.getDate(field, value));
	}

	@Override
	public void addDocument(long companyId, Document document)
		throws IOException {

		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		indexAccessor.addDocument(document);
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #releaseIndexSearcher(long,
	 *             IndexSearcher)}
	 */
	@Deprecated
	@Override
	public void cleanUp(IndexSearcher indexSearcher) {
		if (indexSearcher == null) {
			return;
		}

		try {
			indexSearcher.close();

			IndexReader indexReader = indexSearcher.getIndexReader();

			if (indexReader != null) {
				indexReader.close();
			}
		}
		catch (IOException ioe) {
			_log.error(ioe, ioe);
		}
	}

	@Override
	public int countScoredFieldNames(Query query, String[] filedNames) {
		int count = 0;

		for (String fieldName : filedNames) {
			WeightedTerm[] weightedTerms = QueryTermExtractor.getTerms(
				query, false, fieldName);

			if ((weightedTerms.length > 0) &&
				!ArrayUtil.contains(Field.UNSCORED_FIELD_NAMES, fieldName)) {

				count++;
			}
		}

		return count;
	}

	@Override
	public void delete(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		indexAccessor.delete();
	}

	@Override
	public void deleteDocuments(long companyId, Term term) throws IOException {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		indexAccessor.deleteDocuments(term);
	}

	@Override
	public void dumpIndex(long companyId, OutputStream outputStream)
		throws IOException {

		long lastGeneration = getLastGeneration(companyId);

		if (lastGeneration == IndexAccessor.DEFAULT_LAST_GENERATION) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Dump index from cluster is not enabled for " + companyId);
			}

			return;
		}

		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		indexAccessor.dumpIndex(outputStream);
	}

	@Override
	public Analyzer getAnalyzer() {
		return _analyzer;
	}

	@Override
	public IndexAccessor getIndexAccessor(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor != null) {
			return indexAccessor;
		}

		synchronized (this) {
			indexAccessor = _indexAccessors.get(companyId);

			if (indexAccessor == null) {
				indexAccessor = new IndexAccessorImpl(companyId);

				_indexAccessors.put(companyId, indexAccessor);
			}
		}

		return indexAccessor;
	}

	@Override
	public IndexSearcher getIndexSearcher(long companyId) throws IOException {
		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		return indexAccessor.acquireIndexSearcher();
	}

	@Override
	public long getLastGeneration(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return IndexAccessor.DEFAULT_LAST_GENERATION;
		}

		return indexAccessor.getLastGeneration();
	}

	@Override
	public Set<String> getQueryTerms(Query query) {
		String queryString = StringUtil.replace(
			query.toString(), StringPool.STAR, StringPool.BLANK);

		Query tempQuery = null;

		try {
			QueryParser queryParser = new QueryParser(
				getVersion(), StringPool.BLANK, getAnalyzer());

			tempQuery = queryParser.parse(queryString);
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to parse " + queryString);
			}

			tempQuery = query;
		}

		WeightedTerm[] weightedTerms = null;

		for (String fieldName : Field.KEYWORDS) {
			weightedTerms = QueryTermExtractor.getTerms(
				tempQuery, false, fieldName);

			if (weightedTerms.length > 0) {
				break;
			}
		}

		Set<String> queryTerms = new HashSet<>();

		for (WeightedTerm weightedTerm : weightedTerms) {
			queryTerms.add(weightedTerm.getTerm());
		}

		return queryTerms;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #getIndexSearcher(long)}
	 */
	@Deprecated
	@Override
	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		IndexReader indexReader = IndexReader.open(
			indexAccessor.getLuceneDir(), readOnly);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		indexSearcher.setDefaultFieldSortScoring(true, false);
		indexSearcher.setSimilarity(new FieldWeightSimilarity());

		return indexSearcher;
	}

	@Override
	public String getSnippet(Query query, String field, String s)
		throws IOException {

		Formatter formatter = new SimpleHTMLFormatter(
			StringPool.BLANK, StringPool.BLANK);

		return getSnippet(query, field, s, formatter);
	}

	@Override
	public String getSnippet(
			Query query, String field, String s, Formatter formatter)
		throws IOException {

		return getSnippet(query, field, s, 3, 80, "...", formatter);
	}

	@Override
	public String getSnippet(
			Query query, String field, String s, int maxNumFragments,
			int fragmentLength, String fragmentSuffix, Formatter formatter)
		throws IOException {

		QueryScorer queryScorer = new QueryScorer(query, field);

		Highlighter highlighter = new Highlighter(formatter, queryScorer);

		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));

		TokenStream tokenStream = getAnalyzer().tokenStream(
			field, new UnsyncStringReader(s));

		try {
			String snippet = highlighter.getBestFragments(
				tokenStream, s, maxNumFragments, fragmentSuffix);

			if (Validator.isNotNull(snippet) &&
				!StringUtil.endsWith(snippet, fragmentSuffix) &&
				!s.equals(snippet)) {

				snippet = snippet.concat(fragmentSuffix);
			}

			return snippet;
		}
		catch (InvalidTokenOffsetsException itoe) {
			throw new IOException(itoe);
		}
	}

	@Override
	public Version getVersion() {
		return _version;
	}

	@Override
	public void loadIndex(long companyId, InputStream inputStream)
		throws IOException {

		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip loading Lucene index files for company " + companyId +
						" in favor of lazy loading");
			}

			return;
		}

		StopWatch stopWatch = new StopWatch();

		stopWatch.start();

		if (_log.isInfoEnabled()) {
			_log.info(
				"Start loading Lucene index files for company " + companyId);
		}

		indexAccessor.loadIndex(inputStream);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Finished loading index files for company " + companyId +
					" in " + stopWatch.getTime() + " ms");
		}
	}

	@Override
	public void releaseIndexSearcher(
			long companyId, IndexSearcher indexSearcher)
		throws IOException {

		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		indexAccessor.releaseIndexSearcher(indexSearcher);
	}

	public void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	public void setVersion(Version version) {
		_version = version;
	}

	@Override
	public void shutdown() {
		if (_luceneIndexThreadPoolExecutor != null) {
			_luceneIndexThreadPoolExecutor.shutdownNow();

			try {
				_luceneIndexThreadPoolExecutor.awaitTermination(
					60, TimeUnit.SECONDS);
			}
			catch (InterruptedException ie) {
				_log.error("Lucene indexer shutdown interrupted", ie);
			}
		}

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		for (String searchEngineId : SearchEngineUtil.getSearchEngineIds()) {
			String searchWriterDestinationName =
				SearchEngineUtil.getSearchWriterDestinationName(searchEngineId);

			Destination searchWriteDestination = messageBus.getDestination(
				searchWriterDestinationName);

			if (searchWriteDestination != null) {
				ThreadPoolExecutor threadPoolExecutor =
					PortalExecutorManagerUtil.getPortalExecutor(
						searchWriterDestinationName);

				int maxPoolSize = threadPoolExecutor.getMaxPoolSize();

				CountDownLatch countDownLatch = new CountDownLatch(maxPoolSize);

				ShutdownSyncJob shutdownSyncJob = new ShutdownSyncJob(
					countDownLatch);

				for (int i = 0; i < maxPoolSize; i++) {
					threadPoolExecutor.submit(shutdownSyncJob);
				}

				try {
					countDownLatch.await();
				}
				catch (InterruptedException ie) {
					_log.error("Shutdown waiting interrupted", ie);
				}

				List<Runnable> runnables = threadPoolExecutor.shutdownNow();

				if (_log.isDebugEnabled()) {
					_log.debug(
						"Cancelled appending indexing jobs: " + runnables);
				}

				searchWriteDestination.close(true);
			}
		}

		for (IndexAccessor indexAccessor : _indexAccessors.values()) {
			indexAccessor.close();
		}
	}

	@Override
	public void shutdown(long companyId) {
		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		_indexAccessors.remove(companyId);

		indexAccessor.close();
	}

	@Override
	public void startup(long companyId) {
		if (!PropsValues.INDEX_ON_STARTUP) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Indexing Lucene on startup");
		}

		SearchEngineInitializer searchEngineInitializer =
			new SearchEngineInitializer(companyId);

		if (PropsValues.INDEX_WITH_THREAD) {
			if (_luceneIndexThreadPoolExecutor == null) {

				// This should never be null except for the case where
				// VerifyProcessUtil#_verifyProcess(boolean) sets
				// PropsValues#INDEX_ON_STARTUP to true.

				_luceneIndexThreadPoolExecutor =
					PortalExecutorManagerUtil.getPortalExecutor(
						LuceneHelperImpl.class.getName());
			}

			_luceneIndexThreadPoolExecutor.execute(searchEngineInitializer);
		}
		else {
			searchEngineInitializer.reindex();
		}
	}

	@Override
	public void updateDocument(long companyId, Term term, Document document)
		throws IOException {

		IndexAccessor indexAccessor = getIndexAccessor(companyId);

		indexAccessor.updateDocument(term, document);
	}

	private static final int _LUCENE_BOOLEAN_QUERY_CLAUSE_MAX_SIZE =
		GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.LUCENE_BOOLEAN_QUERY_CLAUSE_MAX_SIZE),
			BooleanQuery.getMaxClauseCount());

	private Analyzer _analyzer;
	private final Map<Long, IndexAccessor> _indexAccessors =
		new ConcurrentHashMap<>();
	private final Log _log = LogFactoryUtil.getLog(LuceneHelperImpl.class);
	private ThreadPoolExecutor _luceneIndexThreadPoolExecutor;
	private Version _version;

	private class ShutdownSyncJob implements Runnable {

		public ShutdownSyncJob(CountDownLatch countDownLatch) {
			_countDownLatch = countDownLatch;
		}

		@Override
		public void run() {
			_countDownLatch.countDown();

			try {
				synchronized (this) {
					wait();
				}
			}
			catch (InterruptedException ie) {
			}
		}

		private final CountDownLatch _countDownLatch;

	}

}