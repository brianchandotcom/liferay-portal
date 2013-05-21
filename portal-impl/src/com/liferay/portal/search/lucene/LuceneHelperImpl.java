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

import com.liferay.portal.kernel.cluster.Address;
import com.liferay.portal.kernel.cluster.BaseClusterResponseCallback;
import com.liferay.portal.kernel.cluster.ClusterEvent;
import com.liferay.portal.kernel.cluster.ClusterEventListener;
import com.liferay.portal.kernel.cluster.ClusterEventType;
import com.liferay.portal.kernel.cluster.ClusterExecutorUtil;
import com.liferay.portal.kernel.cluster.ClusterLink;
import com.liferay.portal.kernel.cluster.ClusterNode;
import com.liferay.portal.kernel.cluster.ClusterNodeResponse;
import com.liferay.portal.kernel.cluster.ClusterRequest;
import com.liferay.portal.kernel.cluster.FutureClusterResponses;
import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.executor.PortalExecutorManagerUtil;
import com.liferay.portal.kernel.io.unsync.UnsyncPrintWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.proxy.MessageValuesThreadLocal;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnsyncPrintWriterPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.search.lucene.cluster.LuceneClusterUtil;
import com.liferay.portal.search.lucene.highlight.QueryTermExtractor;
import com.liferay.portal.security.auth.TransientTokenUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.lucene.KeywordsUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang.time.StopWatch;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.WeightedTerm;
import org.apache.lucene.util.Version;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 * @author Hugo Huijser
 */
public class LuceneHelperImpl implements LuceneHelper {

	public void addDocument(long companyId, Document document)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.addDocument(document);
	}

	public void addExactTerm(
		BooleanQuery booleanQuery, String field, String value) {

		addTerm(booleanQuery, field, value, false);
	}

	public void addNumericRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		NumericRangeQuery<?> numericRangeQuery = NumericRangeQuery.newLongRange(
			field, GetterUtil.getLong(startValue), GetterUtil.getLong(endValue),
			true, true);

		booleanQuery.add(numericRangeQuery, BooleanClause.Occur.SHOULD);
	}

	public void addRangeTerm(
		BooleanQuery booleanQuery, String field, String startValue,
		String endValue) {

		boolean includesLower = true;

		if ((startValue != null) && startValue.equals(StringPool.STAR)) {
			includesLower = false;
		}

		boolean includesUpper = true;

		if ((endValue != null) && endValue.equals(StringPool.STAR)) {
			includesUpper = false;
		}

		TermRangeQuery termRangeQuery = new TermRangeQuery(
			field, startValue, endValue, includesLower, includesUpper);

		booleanQuery.add(termRangeQuery, BooleanClause.Occur.SHOULD);
	}

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		addRequiredTerm(booleanQuery, field, new String[] {value}, like);
	}

	public void addRequiredTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		if (values == null) {
			return;
		}

		BooleanQuery query = new BooleanQuery();

		for (String value : values) {
			addTerm(query, field, value, like);
		}

		booleanQuery.add(query, BooleanClause.Occur.MUST);
	}

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like) {

		addTerm(booleanQuery, field, value, like, BooleanClauseOccur.SHOULD);
	}

	public void addTerm(
		BooleanQuery booleanQuery, String field, String value, boolean like,
		BooleanClauseOccur booleanClauseOccur) {

		if (Validator.isNull(value)) {
			return;
		}

		Analyzer analyzer = getAnalyzer();

		if (analyzer instanceof PerFieldAnalyzer) {
			PerFieldAnalyzer perFieldAnalyzer = (PerFieldAnalyzer)analyzer;

			Analyzer fieldAnalyzer = perFieldAnalyzer.getAnalyzer(field);

			if (fieldAnalyzer instanceof LikeKeywordAnalyzer) {
				like = true;
			}
		}

		if (like) {
			value = StringUtil.replace(
				value, StringPool.PERCENT, StringPool.BLANK);
		}

		try {
			QueryParser queryParser = new QueryParser(
				getVersion(), field, analyzer);

			Query query = null;

			try {
				if (like) {

					// LUCENE-89

					value = value.toLowerCase(queryParser.getLocale());
				}

				query = queryParser.parse(value);
			}
			catch (Exception e) {
				query = queryParser.parse(KeywordsUtil.escape(value));
			}

			BooleanClause.Occur occur = null;

			if (booleanClauseOccur.equals(BooleanClauseOccur.MUST)) {
				occur = BooleanClause.Occur.MUST;
			}
			else if (booleanClauseOccur.equals(BooleanClauseOccur.MUST_NOT)) {
				occur = BooleanClause.Occur.MUST_NOT;
			}
			else {
				occur = BooleanClause.Occur.SHOULD;
			}

			_includeIfUnique(booleanQuery, query, occur, like);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public void addTerm(
		BooleanQuery booleanQuery, String field, String[] values,
		boolean like) {

		for (String value : values) {
			addTerm(booleanQuery, field, value, like);
		}
	}

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

	public void delete(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		indexAccessor.delete();
	}

	public void deleteDocuments(long companyId, Term term) throws IOException {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		indexAccessor.deleteDocuments(term);
	}

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

	public Analyzer getAnalyzer() {
		return _analyzer;
	}

	public long getLastGeneration(long companyId) {
		if (!isLoadIndexFromClusterEnabled()) {
			return IndexAccessor.DEFAULT_LAST_GENERATION;
		}

		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return IndexAccessor.DEFAULT_LAST_GENERATION;
		}

		return indexAccessor.getLastGeneration();
	}

	public InputStream getLoadIndexesInputStreamFromCluster(
			long companyId, Address bootupAddress)
		throws SystemException {

		if (!isLoadIndexFromClusterEnabled()) {
			return null;
		}

		InputStream inputStream = null;

		try {
			ObjectValuePair<String, URL> bootupClusterNodeObjectValuePair =
				_getBootupClusterNodeObjectValuePair(bootupAddress);

			URL url = bootupClusterNodeObjectValuePair.getValue();

			URLConnection urlConnection = url.openConnection();

			urlConnection.setDoOutput(true);

			UnsyncPrintWriter unsyncPrintWriter = UnsyncPrintWriterPool.borrow(
				urlConnection.getOutputStream());

			unsyncPrintWriter.write("transientToken=");
			unsyncPrintWriter.write(bootupClusterNodeObjectValuePair.getKey());
			unsyncPrintWriter.write("&companyId=");
			unsyncPrintWriter.write(String.valueOf(companyId));

			unsyncPrintWriter.close();

			inputStream = urlConnection.getInputStream();

			return inputStream;
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

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

		Set<String> queryTerms = new HashSet<String>();

		for (WeightedTerm weightedTerm : weightedTerms) {
			queryTerms.add(weightedTerm.getTerm());
		}

		return queryTerms;
	}

	public IndexSearcher getSearcher(long companyId, boolean readOnly)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		IndexReader indexReader = IndexReader.open(
			indexAccessor.getLuceneDir(), readOnly);

		IndexSearcher indexSearcher = new IndexSearcher(indexReader);

		indexSearcher.setDefaultFieldSortScoring(true, true);
		indexSearcher.setSimilarity(new FieldWeightSimilarity());

		return indexSearcher;
	}

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
			throw new IOException(itoe.getMessage());
		}
	}

	public Version getVersion() {
		return _version;
	}

	public boolean isLoadIndexFromClusterEnabled() {
		if (PropsValues.CLUSTER_LINK_ENABLED &&
			PropsValues.LUCENE_REPLICATE_WRITE) {

			return true;
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Load index from cluster is not enabled");
		}

		return false;
	}

	public void loadIndex(long companyId, InputStream inputStream)
		throws IOException {

		if (!isLoadIndexFromClusterEnabled()) {
			return;
		}

		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Skip loading Lucene index files for company " + companyId +
						" in favor of lazy loading");
			}

			return;
		}

		StopWatch stopWatch = null;

		if (_log.isInfoEnabled()) {
			_log.info(
				"Start loading Lucene index files for company " + companyId);

			stopWatch = new StopWatch();

			stopWatch.start();
		}

		indexAccessor.loadIndex(inputStream);

		if (_log.isInfoEnabled()) {
			_log.info(
				"Finished loading index files for company " + companyId +
					" in " + stopWatch.getTime() + " ms");
		}
	}

	public void loadIndexesFromCluster(long companyId) throws SystemException {
		if (!isLoadIndexFromClusterEnabled()) {
			return;
		}

		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor == null) {
			return;
		}

		long localLastGeneration = getLastGeneration(companyId);

		_loadIndexFromCluster(indexAccessor, localLastGeneration);
	}

	public void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	public void setVersion(Version version) {
		_version = version;
	}

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

		if (isLoadIndexFromClusterEnabled()) {
			ClusterExecutorUtil.removeClusterEventListener(
				_loadIndexClusterEventListener);
		}

		for (IndexAccessor indexAccessor : _indexAccessors.values()) {
			indexAccessor.close();
		}
	}

	public void startup(long companyId) {
		if (!PropsValues.INDEX_ON_STARTUP) {
			return;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Indexing Lucene on startup");
		}

		LuceneIndexer luceneIndexer = new LuceneIndexer(companyId);

		if (PropsValues.INDEX_WITH_THREAD) {
			if (_luceneIndexThreadPoolExecutor == null) {

				// This should never be null except for the case where
				// VerifyProcessUtil#_verifyProcess(boolean) sets
				// PropsValues#INDEX_ON_STARTUP to true.

				_luceneIndexThreadPoolExecutor =
					PortalExecutorManagerUtil.getPortalExecutor(
						LuceneHelperImpl.class.getName());
			}

			_luceneIndexThreadPoolExecutor.execute(luceneIndexer);
		}
		else {
			luceneIndexer.reindex();
		}
	}

	public void updateDocument(long companyId, Term term, Document document)
		throws IOException {

		IndexAccessor indexAccessor = _getIndexAccessor(companyId);

		indexAccessor.updateDocument(term, document);
	}

	private LuceneHelperImpl() {
		if (PropsValues.INDEX_ON_STARTUP && PropsValues.INDEX_WITH_THREAD) {
			_luceneIndexThreadPoolExecutor =
				PortalExecutorManagerUtil.getPortalExecutor(
					LuceneHelperImpl.class.getName());
		}

		if (isLoadIndexFromClusterEnabled()) {
			_loadIndexClusterEventListener =
				new LoadIndexClusterEventListener();

			ClusterExecutorUtil.addClusterEventListener(
				_loadIndexClusterEventListener);
		}
	}

	private ObjectValuePair<String, URL>
			_getBootupClusterNodeObjectValuePair(Address bootupAddress)
		throws SystemException {

		ClusterRequest clusterRequest = ClusterRequest.createUnicastRequest(
			new MethodHandler(
				_createTokenMethodKey, _TRANSIENT_TOKEN_KEEP_ALIVE_TIME),
			bootupAddress);

		FutureClusterResponses futureClusterResponses =
			ClusterExecutorUtil.execute(clusterRequest);

		BlockingQueue<ClusterNodeResponse> clusterNodeResponses =
			futureClusterResponses.getPartialResults();

		try {
			ClusterNodeResponse clusterNodeResponse = clusterNodeResponses.poll(
				_BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT, TimeUnit.MILLISECONDS);

			String transientToken = (String)clusterNodeResponse.getResult();

			ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

			InetAddress inetAddress = clusterNode.getInetAddress();

			String fileName = PortalUtil.getPathContext();

			if (!fileName.endsWith(StringPool.SLASH)) {
				fileName = fileName.concat(StringPool.SLASH);
			}

			fileName = fileName.concat("lucene/dump");

			URL url = new URL(
				"http", inetAddress.getHostAddress(), clusterNode.getPort(),
				fileName);

			return new ObjectValuePair<String, URL>(transientToken, url);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	private IndexAccessor _getIndexAccessor(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor != null) {
			return indexAccessor;
		}

		synchronized (this) {
			indexAccessor = _indexAccessors.get(companyId);

			if (indexAccessor == null) {
				indexAccessor = new IndexAccessorImpl(companyId);

				if (isLoadIndexFromClusterEnabled()) {
					boolean clusterForwardMessage = GetterUtil.getBoolean(
						MessageValuesThreadLocal.getValue(
							ClusterLink.CLUSTER_FORWARD_MESSAGE));

					if (clusterForwardMessage) {
						if (_log.isInfoEnabled()) {
							_log.info(
								"Skip Luncene index files cluster loading " +
									"since this is a manual reindex request");
						}
					}
					else {
						indexAccessor = new SynchronizedIndexAccessorImpl(
							indexAccessor);

						try {
							_loadIndexFromCluster(
								indexAccessor,
								indexAccessor.getLastGeneration());
						}
						catch (Exception e) {
							_log.error(
								"Unable to load index for company " +
									indexAccessor.getCompanyId(),
								e);
						}
					}
				}

				_indexAccessors.put(companyId, indexAccessor);
			}
		}

		return indexAccessor;
	}

	private void _includeIfUnique(
		BooleanQuery booleanQuery, Query query, BooleanClause.Occur occur,
		boolean like) {

		if (query instanceof TermQuery) {
			Set<Term> terms = new HashSet<Term>();

			query.extractTerms(terms);

			for (Term term : terms) {
				String termValue = term.text();

				if (like) {
					term = term.createTerm(
						StringPool.STAR.concat(termValue).concat(
							StringPool.STAR));

					query = new WildcardQuery(term);
				}
				else {
					query = new TermQuery(term);
				}

				boolean included = false;

				for (BooleanClause booleanClause : booleanQuery.getClauses()) {
					if (query.equals(booleanClause.getQuery())) {
						included = true;
					}
				}

				if (!included) {
					booleanQuery.add(query, occur);
				}
			}
		}
		else if (query instanceof BooleanQuery) {
			BooleanQuery curBooleanQuery = (BooleanQuery)query;

			BooleanQuery containerBooleanQuery = new BooleanQuery();

			for (BooleanClause booleanClause : curBooleanQuery.getClauses()) {
				_includeIfUnique(
					containerBooleanQuery, booleanClause.getQuery(),
					booleanClause.getOccur(), like);
			}

			if (containerBooleanQuery.getClauses().length > 0) {
				booleanQuery.add(containerBooleanQuery, occur);
			}
		}
		else {
			boolean included = false;

			for (BooleanClause booleanClause : booleanQuery.getClauses()) {
				if (query.equals(booleanClause.getQuery())) {
					included = true;
				}
			}

			if (!included) {
				booleanQuery.add(query, occur);
			}
		}
	}

	private void _loadIndexFromCluster(
			IndexAccessor indexAccessor, long localLastGeneration)
		throws SystemException {

		List<Address> clusterNodeAddresses =
			ClusterExecutorUtil.getClusterNodeAddresses();

		int clusterNodeAddressesCount = clusterNodeAddresses.size();

		if (clusterNodeAddressesCount <= 1) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"Do not load indexes because there is either one portal " +
						"instance or no portal instances in the cluster");
			}

			return;
		}

		ClusterRequest clusterRequest = ClusterRequest.createMulticastRequest(
			new MethodHandler(
				_getLastGenerationMethodKey, indexAccessor.getCompanyId()),
			true);

		ClusterExecutorUtil.execute(
			clusterRequest,
			new LoadIndexClusterResponseCallback(
				indexAccessor, clusterNodeAddressesCount, localLastGeneration));
	}

	private static final long _BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT = 10000;

	private static final long _TRANSIENT_TOKEN_KEEP_ALIVE_TIME = 10000;

	private static Log _log = LogFactoryUtil.getLog(LuceneHelperImpl.class);

	private static MethodKey _createTokenMethodKey = new MethodKey(
		TransientTokenUtil.class, "createToken", long.class);
	private static MethodKey _getLastGenerationMethodKey = new MethodKey(
		LuceneHelperUtil.class, "getLastGeneration", long.class);

	private Analyzer _analyzer;
	private Map<Long, IndexAccessor> _indexAccessors =
		new ConcurrentHashMap<Long, IndexAccessor>();
	private LoadIndexClusterEventListener _loadIndexClusterEventListener;
	private ThreadPoolExecutor _luceneIndexThreadPoolExecutor;
	private Version _version;

	private class LoadIndexClusterEventListener
		implements ClusterEventListener {

		public void processClusterEvent(ClusterEvent clusterEvent) {
			ClusterEventType clusterEventType =
				clusterEvent.getClusterEventType();

			if (!clusterEventType.equals(ClusterEventType.JOIN)) {
				return;
			}

			List<Address> clusterNodeAddresses =
				ClusterExecutorUtil.getClusterNodeAddresses();
			List<ClusterNode> clusterNodes = clusterEvent.getClusterNodes();

			if ((clusterNodeAddresses.size() - clusterNodes.size()) > 1) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Number of original cluster members is greater than " +
							"one");
				}

				return;
			}

			long[] companyIds = PortalInstances.getCompanyIds();

			for (long companyId : companyIds) {
				loadIndexes(companyId);
			}

			loadIndexes(CompanyConstants.SYSTEM);
		}

		private void loadIndexes(long companyId) {
			long lastGeneration = getLastGeneration(companyId);

			if (lastGeneration == IndexAccessor.DEFAULT_LAST_GENERATION) {
				return;
			}

			try {
				LuceneClusterUtil.loadIndexesFromCluster(companyId);
			}
			catch (Exception e) {
				_log.error(
					"Unable to load indexes for company " + companyId, e);
			}
		}

	}

	private class LoadIndexClusterResponseCallback
		extends BaseClusterResponseCallback {

		public LoadIndexClusterResponseCallback(
			IndexAccessor indexAccessor, int clusterNodeAddressesCount,
			long localLastGeneration) {

			_indexAccessor = indexAccessor;
			_clusterNodeAddressesCount = clusterNodeAddressesCount;
			_localLastGeneration = localLastGeneration;

			_companyId = _indexAccessor.getCompanyId();
		}

		@Override
		public void callback(BlockingQueue<ClusterNodeResponse> blockingQueue) {
			Address bootupAddress = null;

			do {
				_clusterNodeAddressesCount--;

				ClusterNodeResponse clusterNodeResponse = null;

				try {
					clusterNodeResponse = blockingQueue.poll(
						_BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT,
						TimeUnit.MILLISECONDS);
				}
				catch (Exception e) {
					_log.error("Unable to get cluster node response", e);
				}

				if (clusterNodeResponse == null) {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Unable to get cluster node response in " +
								_BOOTUP_CLUSTER_NODE_RESPONSE_TIMEOUT +
									TimeUnit.MILLISECONDS);
					}

					continue;
				}

				ClusterNode clusterNode = clusterNodeResponse.getClusterNode();

				if (clusterNode.getPort() > 0) {
					try {
						long remoteLastGeneration =
							(Long)clusterNodeResponse.getResult();

						if (remoteLastGeneration > _localLastGeneration) {
							bootupAddress = clusterNodeResponse.getAddress();

							break;
						}
					}
					catch (Exception e) {
						if (_log.isDebugEnabled()) {
							_log.debug(
								"Suppress exception caused by remote method " +
									"invocation",
								e);
						}

						continue;
					}
				}
				else {
					if (_log.isDebugEnabled()) {
						_log.debug(
							"Cluster node " + clusterNode +
								" has invalid port");
					}
				}
			}
			while ((bootupAddress == null) && (_clusterNodeAddressesCount > 1));

			if (bootupAddress == null) {
				return;
			}

			if (_log.isInfoEnabled()) {
				_log.info(
					"Start loading lucene index files from cluster node " +
						bootupAddress);
			}

			InputStream inputStream = null;

			try {
				inputStream = getLoadIndexesInputStreamFromCluster(
					_companyId, bootupAddress);

				_indexAccessor.loadIndex(inputStream);

				if (_log.isInfoEnabled()) {
					_log.info("Lucene index files loaded successfully");
				}
			}
			catch (Exception e) {
				_log.error("Unable to load index for company " + _companyId, e);
			}
			finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					}
					catch (IOException ioe) {
						_log.error(
							"Unable to close input stream for company " +
								_companyId,
							ioe);
					}
				}
			}
		}

		@Override
		public void processTimeoutException(TimeoutException timeoutException) {
			_log.error(
				"Uanble to load index for company " + _companyId,
				timeoutException);
		}

		private int _clusterNodeAddressesCount;
		private long _companyId;
		private IndexAccessor _indexAccessor;
		private long _localLastGeneration;

	}

}