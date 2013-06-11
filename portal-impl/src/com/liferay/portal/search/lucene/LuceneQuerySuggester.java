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

import com.liferay.portal.kernel.search.CollatorUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilderUtil;
import com.liferay.portal.kernel.search.QuerySuggester;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.TokenizerUtil;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.search.spell.SuggestWord;
import org.apache.lucene.search.spell.SuggestWordQueue;
import org.apache.lucene.util.ReaderUtil;

/**
 * @author Michael C. Han
 */
public class LuceneQuerySuggester implements QuerySuggester {

	public void setBoostEnd(float boostEnd) {
		_boostEnd = boostEnd;
	}

	public void setBoostStart(float boostStart) {
		_boostStart = boostStart;
	}

	public void setStringDistance(StringDistance stringDistance) {
		_stringDistance = stringDistance;
	}

	public void setSuggestWordComparator(
		Comparator<SuggestWord> suggestWordComparator) {

		_suggestWordComparator = suggestWordComparator;
	}

	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		String languageId = searchContext.getLanguageId();

		String localizedFieldName = DocumentImpl.getLocalizedName(
			languageId, Field.SPELL_CHECK_WORD);

		List<String> keywords = TokenizerUtil.tokenize(
			localizedFieldName, searchContext.getKeywords(), languageId);

		Map<String, List<String>> results = spellCheckKeywords(
			keywords, localizedFieldName, searchContext, languageId, 1);

		return CollatorUtil.collate(results, keywords);
	}

	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int maxResults)
		throws SearchException {

		String languageId = searchContext.getLanguageId();

		String localizedFieldName = DocumentImpl.getLocalizedName(
			languageId, Field.SPELL_CHECK_WORD);

		List<String> keywords = TokenizerUtil.tokenize(
			localizedFieldName, searchContext.getKeywords(), languageId);

		return spellCheckKeywords(
			keywords, localizedFieldName, searchContext, languageId,
			maxResults);
	}

	public String[] suggestKeywordQueries(
			SearchContext searchContext, int maxResults)
		throws SearchException {

		try {
			String keywords = searchContext.getKeywords();

			Analyzer analyzer = LuceneHelperUtil.getAnalyzer();

			BooleanQuery suggestKeywordQuery = new BooleanQuery();

			addTermQuery(
				suggestKeywordQuery, Field.COMPANY_ID,
				String.valueOf(searchContext.getCompanyId()), null,
				BooleanClause.Occur.MUST);

			String localizedKeywordFieldName = DocumentImpl.getLocalizedName(
				searchContext.getLanguageId(), Field.KEYWORD_SEARCH);

			QueryParser queryParser = new QueryParser(
				LuceneHelperUtil.getVersion(), localizedKeywordFieldName,
				analyzer);

			Query query = queryParser.parse(keywords);

			BooleanClause keywordTermQuery = new BooleanClause(
				query, BooleanClause.Occur.MUST);

			suggestKeywordQuery.add(keywordTermQuery);

			String languageId = searchContext.getLanguageId();

			addTermQuery(
				suggestKeywordQuery, Field.LANGUAGE_ID, languageId, null,
				BooleanClause.Occur.MUST);

			addTermQuery(
				suggestKeywordQuery, Field.PORTLET_ID, PortletKeys.SEARCH, null,
				BooleanClause.Occur.MUST);

			IndexSearcher indexSearcher = LuceneHelperUtil.getSearcher(
				searchContext.getCompanyId(), true);

			return performSearch(
				indexSearcher, suggestKeywordQuery, localizedKeywordFieldName,
				_defaultRelevancyChecker, maxResults);
		}
		catch (Exception e) {
			throw new SearchException("Unable to suggest query", e);
		}
	}

	protected void addNGramTermQuery(
		BooleanQuery booleanQuery, Map<String, String> nGrams, Float boost,
		BooleanClause.Occur occur) {

		for (Map.Entry<String, String> nGramEntry : nGrams.entrySet()) {
			String name = nGramEntry.getKey();
			String value = nGramEntry.getValue();

			addTermQuery(booleanQuery, name, value, boost, occur);
		}
	}

	protected void addTermQuery(
		BooleanQuery booleanQuery, String termName, String termValue,
		Float boost, BooleanClause.Occur occur) {

		Query query = new TermQuery(new Term(termName, termValue));

		if (boost != null) {
			query.setBoost(boost);
		}

		BooleanClause booleanClause = new BooleanClause(query, occur);

		booleanQuery.add(booleanClause);
	}

	protected BooleanQuery buildNGramQuery(String word) throws SearchException {

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(word);

		BooleanQuery booleanQuery = new BooleanQuery();

		if (_boostEnd > 0) {
			Map<String, String> nGramEnds = nGramHolder.getNGramEnds();

			addNGramTermQuery(
				booleanQuery, nGramEnds, _boostEnd, BooleanClause.Occur.SHOULD);
		}

		Map<String, List<String>> nGrams = nGramHolder.getNGrams();

		for (Map.Entry<String, List<String>> nGramsEntry : nGrams.entrySet()) {
			String fieldName = nGramsEntry.getKey();

			for (String nGram : nGramsEntry.getValue()) {
				addTermQuery(
					booleanQuery, fieldName, nGram, null,
					BooleanClause.Occur.SHOULD);
			}
		}

		if (_boostStart > 0) {
			Map<String, String> nGramStarts = nGramHolder.getNGramStarts();

			addNGramTermQuery(
				booleanQuery, nGramStarts, _boostStart,
				BooleanClause.Occur.SHOULD);
		}

		return booleanQuery;
	}

	protected BooleanQuery buildSpellCheckQuery(String word, String languageId)
		throws SearchException {

		BooleanQuery suggestWordQuery = new BooleanQuery();

		BooleanQuery nGramQuery = buildNGramQuery(word);

		BooleanClause booleanNGramQueryClause = new BooleanClause(
			nGramQuery, BooleanClause.Occur.MUST);

		suggestWordQuery.add(booleanNGramQueryClause);

		addTermQuery(
			suggestWordQuery, Field.LANGUAGE_ID, languageId, null,
			BooleanClause.Occur.MUST);

		addTermQuery(
			suggestWordQuery, Field.PORTLET_ID, PortletKeys.SEARCH, null,
			BooleanClause.Occur.MUST);

		return suggestWordQuery;
	}

	protected String[] performSearch(
			IndexSearcher indexSearcher, Query query, String fieldName,
			RelevancyChecker relevancyChecker, int maxResults)
		throws IOException {

		int maxHits = 10 * maxResults;

		TopDocs topDocs = indexSearcher.search(query, null, maxHits);

		ScoreDoc[] hits = topDocs.scoreDocs;

		SuggestWordQueue suggestWordQueue = new SuggestWordQueue(
			maxResults, _suggestWordComparator);

		int stop = Math.min(hits.length, maxHits);

		for (int i = 0; i < stop; i++) {
			SuggestWord suggestWord = new SuggestWord();

			Document document = indexSearcher.doc(hits[i].doc);

			Fieldable fieldable = document.getFieldable(fieldName);

			suggestWord.string = fieldable.stringValue();

			boolean relevant = relevancyChecker.isRelevant(suggestWord);

			if (relevant) {
				suggestWordQueue.insertWithOverflow(suggestWord);
			}
		}

		String[] list = new String[suggestWordQueue.size()];

		for (int i = suggestWordQueue.size() - 1; i >= 0; i--) {
			list[i] = suggestWordQueue.pop().string;
		}

		return list;
	}

	protected Map<String, List<String>> spellCheckKeywords(
			List<String> keywords, String localizedFieldName,
			SearchContext searchContext, String languageId, int maxResults)
		throws SearchException {

		try {
			Map<String, List<String>> spellCheckResults =
				new LinkedHashMap<String, List<String>>();

			long companyId = searchContext.getCompanyId();

			float scoringThreshold = searchContext.getScoringThreshold();

			if (scoringThreshold == 0) {
				scoringThreshold = _DEFAULT_SCORING_THRESHOLD;
			}

			IndexSearcher indexSearcher = LuceneHelperUtil.getSearcher(
				companyId, true);

			List<IndexReader> indexReaders = new ArrayList<IndexReader>();

			if (indexSearcher.maxDoc() > 0) {
				ReaderUtil.gatherSubReaders(
					indexReaders, indexSearcher.getIndexReader());
			}

			for (String keyword : keywords) {
				List<String> spellCheckSuggestions = Collections.emptyList();

				if (!SpellCheckerUtil.isValidWord(
						localizedFieldName, keyword, indexReaders)) {

					int frequency = indexSearcher.docFreq(
						new Term(localizedFieldName, keyword));

					String[] suggestionsArray = null;

					if (frequency > 0) {
						suggestionsArray = new String[]{ keyword };
					}
					else {
						BooleanQuery suggestWordQuery = buildSpellCheckQuery(
							keyword, languageId);

						RelevancyChecker relevancyChecker =
							new StringDistanceRelevancyChecker(
								keyword, scoringThreshold, _stringDistance);

						suggestionsArray = performSearch(
							indexSearcher, suggestWordQuery, localizedFieldName,
							relevancyChecker, maxResults);
					}

					spellCheckSuggestions = Arrays.asList(suggestionsArray);
				}

				spellCheckResults.put(keyword, spellCheckSuggestions);
			}

			return spellCheckResults;
		}
		catch (IOException e) {
			throw new SearchException("Unable to find alternative queries", e);
		}
	}

	private static final float _DEFAULT_SCORING_THRESHOLD = 0.5f;

	private float _boostEnd = 1.0f;
	private float _boostStart = 2.0f;
	private RelevancyChecker _defaultRelevancyChecker =
		new DefaultRelevancyChecker();
	private StringDistance _stringDistance;
	private Comparator<SuggestWord> _suggestWordComparator =
		SuggestWordQueue.DEFAULT_COMPARATOR;

}