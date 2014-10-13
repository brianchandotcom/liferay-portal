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
import com.liferay.portal.kernel.search.BaseSpellCheckIndexWriter;
import com.liferay.portal.kernel.search.DictionaryEntry;
import com.liferay.portal.kernel.search.DictionaryReader;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilderUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SuggestionConstants;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.FieldInfo;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.util.ReaderUtil;

/**
 * @author Michael C. Han
 */
public class LuceneSpellCheckIndexWriter extends BaseSpellCheckIndexWriter {

	@Override
	public void clearQuerySuggestionDictionaryIndexes(
			SearchContext searchContext)
		throws SearchException {

		Term term = new Term(
			com.liferay.portal.kernel.search.Field.TYPE,
			SuggestionConstants.TYPE_QUERY_SUGGESTION);

		try {
			LuceneHelperUtil.deleteDocuments(
				searchContext.getCompanyId(), term);
		}
		catch (IOException e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void clearSpellCheckerDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		Term term = new Term(
			com.liferay.portal.kernel.search.Field.TYPE,
			SuggestionConstants.TYPE_SPELL_CHECKER);

		try {
			LuceneHelperUtil.deleteDocuments(
				searchContext.getCompanyId(), term);
		}
		catch (IOException e) {
			throw new SearchException(e);
		}
	}

	protected void addField(
		Document document, String fieldName, String fieldValue,
		Field.Store fieldStore, FieldInfo.IndexOptions indexOptions,
		boolean omitNorms) {

		Field field = new Field(
			fieldName, fieldValue, fieldStore, Field.Index.NOT_ANALYZED);

		field.setIndexOptions(indexOptions);
		field.setOmitNorms(omitNorms);

		document.add(field);
	}

	protected void addNGramFields(
		Document document, Map<String, String> nGrams) {

		for (Map.Entry<String, String> entry : nGrams.entrySet()) {
			String fieldName = entry.getKey();
			String fieldValue = entry.getValue();

			addField(
				document, fieldName, fieldValue, Field.Store.NO,
				FieldInfo.IndexOptions.DOCS_ONLY, true);
		}
	}

	protected Document createDocument(
			long companyId, long groupId, String languageId,
			String localizedFieldName, String word, float weight,
			String typeFieldValue, int maxNGramLength)
		throws SearchException {

		Document document = new Document();

		addField(
			document, com.liferay.portal.kernel.search.Field.GROUP_ID,
			String.valueOf(groupId), Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, com.liferay.portal.kernel.search.Field.LANGUAGE_ID,
			languageId, Field.Store.YES, FieldInfo.IndexOptions.DOCS_ONLY,
			true);
		addField(
			document, com.liferay.portal.kernel.search.Field.PORTLET_ID,
			PortletKeys.SEARCH, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, com.liferay.portal.kernel.search.Field.PRIORITY,
			String.valueOf(weight), Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, com.liferay.portal.kernel.search.Field.TYPE,
			typeFieldValue, Field.Store.YES, FieldInfo.IndexOptions.DOCS_ONLY,
			true);
		addField(
			document, com.liferay.portal.kernel.search.Field.UID,
			getUID(companyId, languageId, word), Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, localizedFieldName, word, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(
			word, maxNGramLength);

		addNGramFields(document, nGramHolder.getNGramEnds());

		Map<String, List<String>> nGrams = nGramHolder.getNGrams();

		for (Map.Entry<String, List<String>> entry : nGrams.entrySet()) {
			String fieldName = entry.getKey();

			for (String nGram : entry.getValue()) {
				addField(
					document, fieldName, nGram, Field.Store.NO,
					FieldInfo.IndexOptions.DOCS_AND_FREQS, false);
			}
		}

		addNGramFields(document, nGramHolder.getNGramStarts());

		return document;
	}

	@Override
	protected void indexKeyword(
			SearchContext searchContext, long groupId, String languageId,
			String keyword, float weight, String keywordFieldName,
			String typeFieldValue, int maxNGramLength)
		throws Exception {

		IndexAccessor indexAccessor = LuceneHelperUtil.getIndexAccessor(
			searchContext.getCompanyId());

		IndexSearcher indexSearcher = null;

		try {
			List<IndexReader> indexReaders = new ArrayList<IndexReader>();

			indexSearcher = LuceneHelperUtil.getIndexSearcher(
				searchContext.getCompanyId());

			if (indexSearcher.maxDoc() > 0) {
				ReaderUtil.gatherSubReaders(
					indexReaders, indexSearcher.getIndexReader());
			}

			String localizedFieldName = DocumentImpl.getLocalizedName(
				languageId, keywordFieldName);

			boolean validWord = isValidWord(
				localizedFieldName, keyword, indexReaders);

			if (!validWord) {
				if (_log.isInfoEnabled()) {
					_log.info(
						"Not indexing because keyword " + keyword +
							" is invalid");
				}

				return;
			}

			Document document = createDocument(
				searchContext.getCompanyId(), groupId, languageId,
				localizedFieldName, keyword, weight, typeFieldValue,
				maxNGramLength);

			indexAccessor.addDocument(document);
		}
		finally {
			try {
				LuceneHelperUtil.releaseIndexSearcher(
					searchContext.getCompanyId(), indexSearcher);
			}
			catch (IOException ioe) {
				_log.error("Unable to release searcher", ioe);
			}
		}
	}

	@Override
	protected void indexKeywords(
			SearchContext searchContext, long groupId, String languageId,
			InputStream inputStream, String keywordFieldName,
			String typeFieldValue, int maxNGramLength)
		throws Exception {

		IndexAccessor indexAccessor = LuceneHelperUtil.getIndexAccessor(
			searchContext.getCompanyId());

		IndexSearcher indexSearcher = null;

		try {
			String localizedFieldName = DocumentImpl.getLocalizedName(
				languageId, keywordFieldName);

			indexSearcher = LuceneHelperUtil.getIndexSearcher(
				searchContext.getCompanyId());

			List<IndexReader> indexReaders = new ArrayList<IndexReader>();

			if (indexSearcher.maxDoc() > 0) {
				ReaderUtil.gatherSubReaders(
					indexReaders, indexSearcher.getIndexReader());
			}

			Collection<Document> documents = new ArrayList<Document>();

			DictionaryReader dictionaryReader = new DictionaryReader(
				inputStream, StringPool.UTF8);

			Iterator<DictionaryEntry> iterator =
				dictionaryReader.getDictionaryEntriesIterator();

			while (iterator.hasNext()) {
				DictionaryEntry dictionaryEntry = iterator.next();

				String word = dictionaryEntry.getWord();

				boolean validWord = isValidWord(
					localizedFieldName, word, indexReaders);

				if (!validWord) {
					if (_log.isInfoEnabled()) {
						_log.info(
							"Not indexing because word " + word +
								" is invalid");
					}

					continue;
				}

				Document document = createDocument(
					searchContext.getCompanyId(), groupId, languageId,
					localizedFieldName, word, dictionaryEntry.getWeight(),
					typeFieldValue, maxNGramLength);

				documents.add(document);
			}

			indexAccessor.addDocuments(documents);
		}
		finally {
			try {
				LuceneHelperUtil.releaseIndexSearcher(
					searchContext.getCompanyId(), indexSearcher);
			}
			catch (IOException ioe) {
				_log.error("Unable to release searcher", ioe);
			}
		}
	}

	protected boolean isValidWord(
			String localizedFieldName, String word,
			List<IndexReader> indexReaders)
		throws IOException {

		if (word.length() < _MINIMUM_WORD_LENGTH) {
			return false;
		}

		if (SpellCheckerUtil.isValidWord(
				localizedFieldName, word, indexReaders)) {

			return false;
		}

		return true;
	}

	private static final int _MINIMUM_WORD_LENGTH = 3;

	private static final Log _log = LogFactoryUtil.getLog(
		LuceneSpellCheckIndexWriter.class);

}