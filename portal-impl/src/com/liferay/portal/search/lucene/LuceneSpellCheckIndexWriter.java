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

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.NGramHolder;
import com.liferay.portal.kernel.search.NGramHolderBuilderUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.SpellCheckIndexWriter;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.IOException;
import java.io.InputStream;

import java.net.URL;

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
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.PlainTextDictionary;
import org.apache.lucene.util.ReaderUtil;

/**
 * @author Michael C. Han
 */
public class LuceneSpellCheckIndexWriter implements SpellCheckIndexWriter {

	@Override
	public void clearDictionaryIndexes(SearchContext searchContext)
		throws SearchException {

		Term term = new Term(
			com.liferay.portal.kernel.search.Field.PORTLET_ID,
			PortletKeys.SEARCH);

		try {
			LuceneHelperUtil.deleteDocuments(
				searchContext.getCompanyId(), term);
		}
		catch (IOException e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void indexDictionaries(SearchContext searchContext)
		throws SearchException {

		try {
			for (String languageId :
					PropsValues.LUCENE_SPELL_CHECKER_SUPPORTED_LOCALES) {

				indexDictionary(searchContext.getCompanyId(), languageId);
			}
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	@Override
	public void indexDictionary(SearchContext searchContext)
		throws SearchException {

		try {
			indexDictionary(
				searchContext.getCompanyId(), searchContext.getLanguageId());
		}
		catch (Exception e) {
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
			String localizedFieldName, String word, String languageId)
		throws SearchException {

		Document document = new Document();

		addField(
			document, com.liferay.portal.kernel.search.Field.LANGUAGE_ID,
			languageId, Field.Store.NO, FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, com.liferay.portal.kernel.search.Field.PORTLET_ID,
			PortletKeys.SEARCH, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);
		addField(
			document, localizedFieldName, word, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(word);

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

	protected URL getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResource(name);
	}

	protected void indexDictionary(long companyId, String languageId)
		throws Exception {

		if (_log.isInfoEnabled()) {
			_log.info("Start indexing dictionaries for " + languageId);
		}

		IndexAccessor indexAccessor = LuceneHelperUtil.getIndexAccessor(
			companyId);

		String[] dictionaryFileNames = PropsUtil.getArray(
			PropsKeys.LUCENE_SPELL_CHECKER_DICTIONARY, new Filter(languageId));

		for (String dictionaryFileName : dictionaryFileNames) {
			if (_log.isInfoEnabled()) {
				_log.info("Indexing dictionary " + dictionaryFileName);
			}

			InputStream inputStream = null;
			IndexSearcher indexSearcher = null;

			try {
				URL url = getResource(dictionaryFileName);

				inputStream = url.openStream();

				if (inputStream == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("Unable to read " + dictionaryFileName);
					}

					continue;
				}

				String localizedFieldName = DocumentImpl.getLocalizedName(
					languageId,
					com.liferay.portal.kernel.search.Field.SPELL_CHECK_WORD);

				indexSearcher = LuceneHelperUtil.getSearcher(
					indexAccessor.getCompanyId(), true);

				List<IndexReader> indexReaders = new ArrayList<IndexReader>();

				if (indexSearcher.maxDoc() > 0) {
					ReaderUtil.gatherSubReaders(
						indexReaders, indexSearcher.getIndexReader());
				}

				Collection<Document> documents = new ArrayList<Document>();

				Dictionary dictionary = new PlainTextDictionary(inputStream);

				Iterator<String> iterator = dictionary.getWordsIterator();

				while (iterator.hasNext()) {
					String word = iterator.next();

					boolean validWord = isValidWord(
						localizedFieldName, word, indexReaders);

					if (!validWord) {
						continue;
					}

					Document document = createDocument(
						localizedFieldName, word, languageId);

					documents.add(document);
				}

				indexAccessor.addDocuments(documents);
			}
			finally {
				StreamUtil.cleanUp(inputStream);

				LuceneHelperUtil.closeSearcher(indexSearcher);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Finished indexing dictionaries for " + languageId);
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

	private static Log _log = LogFactoryUtil.getLog(
		LuceneSpellCheckIndexWriter.class);

}