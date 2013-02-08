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

import com.liferay.portal.kernel.io.unsync.UnsyncStringReader;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.QuerySuggester;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.search.lucene.spellcheck.SpellChecker;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.io.IOException;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.WhitespaceTokenizer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.util.Version;

/**
 * @author Michael C. Han
 */
public class LuceneQuerySuggester implements QuerySuggester {

	public void setVersion(Version version) {
		_version = version;
	}

	public String spellCheckKeywords(SearchContext searchContext)
		throws SearchException {

		Map<String, List<String>> spellCheckResults = spellCheckKeywords(
			searchContext, 1);

		StringBundler stringBundler = new StringBundler(
			spellCheckResults.size());

		Iterator<Map.Entry<String, List<String>>> spellCheckResultsEntryIter =
			spellCheckResults.entrySet().iterator();

		while (spellCheckResultsEntryIter.hasNext()) {

			Map.Entry<String, List<String>> spellCheckResultEntry =
				spellCheckResultsEntryIter.next();

			String originalWord = spellCheckResultEntry.getKey();

			List<String> alternateWords = spellCheckResultEntry.getValue();

			String bestAlternateWord = originalWord;

			if ((alternateWords != null) && !alternateWords.isEmpty()) {
				bestAlternateWord = alternateWords.get(0);
			}

			stringBundler.append(bestAlternateWord);

			if (spellCheckResultsEntryIter.hasNext()) {
				stringBundler.append(StringPool.SPACE);
			}
		}

		return stringBundler.toString();
	}

	public Map<String, List<String>> spellCheckKeywords(
			SearchContext searchContext, int max)
		throws SearchException {

		try {
			SpellCheckerIndexAccessor spellCheckerIndexAccessor =
				LuceneHelperUtil.getSpellCheckerIndexAccessor();

			SpellChecker spellChecker =
				spellCheckerIndexAccessor.getSpellChecker();

			TokenStream tokenStream = new WhitespaceTokenizer(
				_version, new UnsyncStringReader(searchContext.getKeywords()));

			Map<String, List<String>> spellCheckResults =
				new LinkedHashMap<String, List<String>>();

			while (tokenStream.incrementToken()) {

				CharTermAttribute charTermAttribute = tokenStream.getAttribute(
					CharTermAttribute.class);

				String termToken = charTermAttribute.toString();

				List<String> spellCheckSuggestions = null;

				if (!spellChecker.exist(termToken, searchContext.getLocale())) {
					String[] suggestionsArray = spellChecker.suggestSimilar(
						termToken, searchContext.getLocale(), max);

					spellCheckSuggestions = Arrays.asList(suggestionsArray);
				}
				else {
					spellCheckSuggestions = Collections.emptyList();
				}

				spellCheckResults.put(termToken, spellCheckSuggestions);
			}

			return spellCheckResults;
		}
		catch (IOException e) {
			throw new SearchException("Unable to find alternative queries", e);
		}
	}

	public String[] suggestKeywordQueries(
			SearchContext searchContext, int maxNum)
		throws SearchException {

		try {
			String keywords = searchContext.getKeywords();

			Analyzer analyzer = LuceneHelperUtil.getAnalyzer();

			String fieldName = DocumentImpl.getLocalizedName(
				searchContext.getLocale(), Field.KEYWORD_SEARCH);

			QueryParser queryPaser = new QueryParser(
				_version, fieldName, analyzer);

			Query query = queryPaser.parse(keywords);

			IndexSearcher indexSearch = LuceneHelperUtil.getSearcher(
				searchContext.getCompanyId(), true);

			TopFieldDocs topFieldDocs = indexSearch.search(
				query, null, maxNum, new Sort());

			int totalHits = topFieldDocs.totalHits;

			String[] suggestedKeywordQueries = new String[totalHits];

			for (int i = 0; i < topFieldDocs.totalHits; i++) {
				int docId = topFieldDocs.scoreDocs[i].doc;

				Document document = indexSearch.doc(docId);
				Field keywordSearchField = (Field)document.getFieldable(
					fieldName);

				suggestedKeywordQueries[i] = keywordSearchField.getValue();
			}

			return suggestedKeywordQueries;
		}
		catch (Exception e) {
			throw new SearchException("Unable to suggest query", e);
		}
	}

	private Version _version = Version.LUCENE_35;

}