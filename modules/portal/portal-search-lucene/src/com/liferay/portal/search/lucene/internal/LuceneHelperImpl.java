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

import aQute.bnd.annotation.metatype.Configurable;

import com.liferay.portal.kernel.concurrent.ThreadPoolExecutor;
import com.liferay.portal.kernel.executor.PortalExecutorManager;
import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.SearchEngineInitializer;
import com.liferay.portal.search.lucene.internal.configuration.LuceneConfiguration;
import com.liferay.portal.search.lucene.internal.dump.IndexCommitSerializationUtil;
import com.liferay.portal.search.lucene.internal.highlight.QueryTermExtractor;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
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

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Brian Wing Shun Chan
 * @author Harry Mark
 * @author Bruno Farache
 * @author Shuyang Zhou
 * @author Tina Tian
 * @author Hugo Huijser
 * @author Andrea Di Giorgi
 */
@Component(
	configurationPid = "com.liferay.portal.sso.ntlm.configuration.NtlmConfiguration",
	immediate = true, service = LuceneHelper.class
)
public class LuceneHelperImpl implements LuceneHelper {

	@Override
	public void addDate(Document document, String field, Date value) {
		document.add(LuceneFields.getDate(field, value));
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
	public IndexAccessor getIndexAccessor(long companyId) {
		IndexAccessor indexAccessor = _indexAccessors.get(companyId);

		if (indexAccessor != null) {
			return indexAccessor;
		}

		synchronized (this) {
			indexAccessor = _indexAccessors.get(companyId);

			if (indexAccessor == null) {
				indexAccessor = new IndexAccessorImpl(
					_analyzer, _version, companyId);

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
				_version, StringPool.BLANK, _analyzer);

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

		TokenStream tokenStream = _analyzer.tokenStream(
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
					_portalExecutorManager.getPortalExecutor(
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

	@Activate
	protected void activate(Map<String, Object> properties) {
		_luceneConfiguration = Configurable.createConfigurable(
			LuceneConfiguration.class, properties);

		if (PropsValues.INDEX_ON_STARTUP && PropsValues.INDEX_WITH_THREAD) {
			_luceneIndexThreadPoolExecutor =
				_portalExecutorManager.getPortalExecutor(
					LuceneHelperImpl.class.getName());
		}

		BooleanQuery.setMaxClauseCount(BooleanQuery.getMaxClauseCount());

		IndexAccessorImpl.luceneConfiguration = _luceneConfiguration;
		IndexAccessorImpl.luceneHelper = this;
		IndexCommitSerializationUtil.luceneConfiguration = _luceneConfiguration;
	}

	@Deactivate
	protected void deactivate() {
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

		for (IndexAccessor indexAccessor : _indexAccessors.values()) {
			indexAccessor.close();
		}
	}

	@Modified
	protected void modified(Map<String, Object> properties) {
		_luceneConfiguration = Configurable.createConfigurable(
			LuceneConfiguration.class, properties);

		IndexAccessorImpl.luceneConfiguration = _luceneConfiguration;
		IndexCommitSerializationUtil.luceneConfiguration = _luceneConfiguration;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setAnalyzer(Analyzer analyzer) {
		_analyzer = analyzer;
	}

	@Reference(unbind = "-")
	protected void setPortalExecutorManager(
		PortalExecutorManager portalExecutorManager) {

		_portalExecutorManager = portalExecutorManager;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setVersion(Version version) {
		_version = version;
	}

	protected void unsetAnalyzer(Analyzer analyzer) {
		_analyzer = null;
	}

	protected void unsetVersion(Version version) {
		_version = null;
	}

	private Analyzer _analyzer;
	private final Map<Long, IndexAccessor> _indexAccessors =
		new ConcurrentHashMap<>();
	private final Log _log = LogFactoryUtil.getLog(LuceneHelperImpl.class);
	private volatile LuceneConfiguration _luceneConfiguration;
	private ThreadPoolExecutor _luceneIndexThreadPoolExecutor;
	private PortalExecutorManager _portalExecutorManager;
	private Version _version;

}