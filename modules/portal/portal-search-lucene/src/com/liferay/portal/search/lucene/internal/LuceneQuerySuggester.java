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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.BaseQuerySuggester;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilderUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SuggestionConstants;
import com.liferay.portal.kernel.search.TokenizerUtil;
import com.liferay.portal.kernel.util.ArrayUtil;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Fieldable;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.spell.LevensteinDistance;
import org.apache.lucene.search.spell.StringDistance;
import org.apache.lucene.search.spell.SuggestWord;
import org.apache.lucene.search.spell.SuggestWordQueue;
import org.apache.lucene.util.ReaderUtil;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = LuceneQuerySuggester.class)
public class LuceneQuerySuggester extends BaseQuerySuggester {

	public void setBoostEnd(float boostEnd) {
		_boostEnd = boostEnd;
	}

	public void setBoostStart(float boostStart) {
		_boostStart = boostStart;
	}

	public void setQuerySuggestionMaxNGramLength(
		int querySuggestionMaxNGramLength) {

		_querySuggestionMaxNGramLength = querySuggestionMaxNGramLength;
	}

	@Override
	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		String languageId = searchContext.getLanguageId();

		String localizedFieldName = DocumentImpl.getLocalizedName(
			languageId, Field.SPELL_CHECK_WORD);

		List<String> keywords = TokenizerUtil.tokenize(
			localizedFieldName, searchContext.getKeywords(), languageId);

		return spellCheckKeywords(
			keywords, localizedFieldName, searchContext, languageId, max);
	}

	@Override
	public String[] suggestKeywordQueries(SearchContext searchContext, int max)
		throws SearchException {

		IndexSearcher indexSearcher = null;

		try {
			indexSearcher = _luceneHelper.getIndexSearcher(
				searchContext.getCompanyId());

			String localizedKeywordFieldName = DocumentImpl.getLocalizedName(
				searchContext.getLanguageId(), Field.KEYWORD_SEARCH);

			BooleanQuery suggestKeywordQuery = buildSpellCheckQuery(
				searchContext.getGroupIds(), searchContext.getKeywords(),
				searchContext.getLanguageId(),
				SuggestionConstants.TYPE_QUERY_SUGGESTION,
				_querySuggestionMaxNGramLength);

			return search(
				indexSearcher, suggestKeywordQuery, localizedKeywordFieldName,
				_relevancyChecker, max);
		}
		catch (Exception e) {
			throw new SearchException("Unable to suggest query", e);
		}
		finally {
			try {
				_luceneHelper.releaseIndexSearcher(
					searchContext.getCompanyId(), indexSearcher);
			}
			catch (IOException ioe) {
				_log.error("Unable to release searcher", ioe);
			}
		}
	}

	@Activate
	protected void activate(Map<String, Object> properties) {
		if (_stringDistance == null) {
			_stringDistance = new LevensteinDistance();
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

	protected BooleanQuery buildGroupIdQuery(long[] groupIds) {
		BooleanQuery booleanQuery = new BooleanQuery();

		addTermQuery(
			booleanQuery, Field.GROUP_ID, String.valueOf(0), null,
			BooleanClause.Occur.SHOULD);

		if (ArrayUtil.isNotEmpty(groupIds)) {
			for (long groupId : groupIds) {
				addTermQuery(
					booleanQuery, Field.GROUP_ID, String.valueOf(groupId), null,
					BooleanClause.Occur.SHOULD);
			}
		}

		return booleanQuery;
	}

	protected BooleanQuery buildNGramQuery(String word, int maxNGramLength)
		throws SearchException {

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(
			word, maxNGramLength);

		BooleanQuery booleanQuery = new BooleanQuery();

		if (_boostEnd > 0) {
			Map<String, String> nGramEnds = nGramHolder.getNGramEnds();

			addNGramTermQuery(
				booleanQuery, nGramEnds, _boostEnd, BooleanClause.Occur.SHOULD);
		}

		Map<String, List<String>> nGrams = nGramHolder.getNGrams();

		for (Map.Entry<String, List<String>> entry : nGrams.entrySet()) {
			String fieldName = entry.getKey();

			for (String nGram : entry.getValue()) {
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

	protected BooleanQuery buildSpellCheckQuery(
			long groupIds[], String word, String languageId,
			String typeFieldValue, int maxNGramLength)
		throws SearchException {

		BooleanQuery suggestWordQuery = new BooleanQuery();

		BooleanQuery nGramQuery = buildNGramQuery(word, maxNGramLength);

		BooleanClause booleanNGramQueryClause = new BooleanClause(
			nGramQuery, BooleanClause.Occur.MUST);

		suggestWordQuery.add(booleanNGramQueryClause);

		BooleanQuery groupIdQuery = buildGroupIdQuery(groupIds);

		BooleanClause groupIdQueryClause = new BooleanClause(
			groupIdQuery, BooleanClause.Occur.MUST);

		suggestWordQuery.add(groupIdQueryClause);

		addTermQuery(
			suggestWordQuery, Field.LANGUAGE_ID, languageId, null,
			BooleanClause.Occur.MUST);
		addTermQuery(
			suggestWordQuery, Field.SPELL_CHECK_WORD, Boolean.TRUE.toString(),
			null, BooleanClause.Occur.MUST);
		addTermQuery(
			suggestWordQuery, Field.TYPE, typeFieldValue, null,
			BooleanClause.Occur.MUST);

		return suggestWordQuery;
	}

	protected String[] search(
			IndexSearcher indexSearcher, Query query, String fieldName,
			RelevancyChecker relevancyChecker, int max)
		throws IOException {

		int maxScoreDocs = max * 10;

		TopDocs topDocs = indexSearcher.search(query, null, maxScoreDocs);

		ScoreDoc[] scoreDocs = topDocs.scoreDocs;

		SuggestWordQueue suggestWordQueue = new SuggestWordQueue(
			max, _suggestWordComparator);

		int stop = Math.min(scoreDocs.length, maxScoreDocs);

		for (int i = 0; i < stop; i++) {
			SuggestWord suggestWord = new SuggestWord();

			Document document = indexSearcher.doc(scoreDocs[i].doc);

			Fieldable fieldable = document.getFieldable(fieldName);

			suggestWord.string = fieldable.stringValue();

			boolean relevant = relevancyChecker.isRelevant(suggestWord);

			if (relevant) {
				suggestWordQueue.insertWithOverflow(suggestWord);
			}
		}

		String[] words = new String[suggestWordQueue.size()];

		for (int i = suggestWordQueue.size() - 1; i >= 0; i--) {
			SuggestWord suggestWord = suggestWordQueue.pop();

			words[i] = suggestWord.string;
		}

		return words;
	}

	@Reference(unbind = "-")
	protected void setLuceneHelper(LuceneHelper luceneHelper) {
		_luceneHelper = luceneHelper;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setStringDistance(StringDistance stringDistance) {
		_stringDistance = stringDistance;
	}

	@Reference(
		cardinality = ReferenceCardinality.OPTIONAL,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void setSuggestWordComparator(
		Comparator<SuggestWord> suggestWordComparator) {

		_suggestWordComparator = suggestWordComparator;
	}

	protected Map<String, List<String>> spellCheckKeywords(
			List<String> keywords, String localizedFieldName,
			SearchContext searchContext, String languageId, int max)
		throws SearchException {

		IndexSearcher indexSearcher = null;

		try {
			Map<String, List<String>> suggestions = new LinkedHashMap<>();

			float scoresThreshold = searchContext.getScoresThreshold();

			if (scoresThreshold == 0) {
				scoresThreshold = _SCORES_THRESHOLD_DEFAULT;
			}

			indexSearcher = _luceneHelper.getIndexSearcher(
				searchContext.getCompanyId());

			List<IndexReader> indexReaders = new ArrayList<>();

			if (indexSearcher.maxDoc() > 0) {
				ReaderUtil.gatherSubReaders(
					indexReaders, indexSearcher.getIndexReader());
			}

			for (String keyword : keywords) {
				List<String> suggestionsList = Collections.emptyList();

				if (!SpellCheckerUtil.isValidWord(
						localizedFieldName, keyword, indexReaders)) {

					int frequency = indexSearcher.docFreq(
						new Term(localizedFieldName, keyword));

					String[] suggestionsArray = null;

					if (frequency > 0) {
						suggestionsArray = new String[] {keyword};
					}
					else {
						BooleanQuery suggestWordQuery = buildSpellCheckQuery(
							searchContext.getGroupIds(), keyword, languageId,
							SuggestionConstants.TYPE_SPELL_CHECKER, 0);

						RelevancyChecker relevancyChecker =
							new StringDistanceRelevancyChecker(
								keyword, scoresThreshold, _stringDistance);

						suggestionsArray = search(
							indexSearcher, suggestWordQuery, localizedFieldName,
							relevancyChecker, max);
					}

					suggestionsList = Arrays.asList(suggestionsArray);
				}

				suggestions.put(keyword, suggestionsList);
			}

			return suggestions;
		}
		catch (IOException ioe) {
			throw new SearchException("Unable to find suggestions", ioe);
		}
		finally {
			try {
				_luceneHelper.releaseIndexSearcher(
					searchContext.getCompanyId(), indexSearcher);
			}
			catch (IOException ioe) {
				_log.error("Unable to release searcher", ioe);
			}
		}
	}

	protected void unsetStringDistance(StringDistance stringDistance) {
		_stringDistance = null;
	}

	protected void unsetSuggestWordComparator(
		Comparator<SuggestWord> suggestWordComparator) {

		_suggestWordComparator = null;
	}

	private static final float _SCORES_THRESHOLD_DEFAULT = 0.5f;

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneQuerySuggester.class);

	private float _boostEnd = 1.0f;
	private float _boostStart = 2.0f;
	private LuceneHelper _luceneHelper;
	private int _querySuggestionMaxNGramLength = 50;
	private final RelevancyChecker _relevancyChecker =
		new DefaultRelevancyChecker();
	private StringDistance _stringDistance;
	private Comparator<SuggestWord> _suggestWordComparator =
		SuggestWordQueue.DEFAULT_COMPARATOR;

}