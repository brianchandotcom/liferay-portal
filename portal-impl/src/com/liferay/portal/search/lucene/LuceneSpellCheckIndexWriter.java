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
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StreamUtil;
import com.liferay.portal.kernel.util.StringUtil;
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
import java.util.Locale;
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

		long companyId = searchContext.getCompanyId();

		Term term = new Term(
			com.liferay.portal.kernel.search.Field.PORTLET_ID,
			PortletKeys.SEARCH);

		try {
			LuceneHelperUtil.deleteDocuments(companyId, term);
		}
		catch (IOException e) {
			throw new SearchException(e);
		}
	}

	public void indexDictionaries(SearchContext searchContext)
		throws SearchException {

		long companyId = searchContext.getCompanyId();

		String[] supportedLocales =
			PropsValues.LUCENE_SPELL_CHECKER_SUPPORTED_LOCALES;

		try {
			for (String supportedLocale : supportedLocales) {
				Locale locale = LocaleUtil.fromLanguageId(supportedLocale);

				doIndexDictionary(companyId, locale);
			}
		}
		catch (Exception e) {
			throw new SearchException(e);
		}
	}

	public void indexDictionary(SearchContext searchContext)
		throws SearchException {

		try {
			long companyId = searchContext.getCompanyId();

			Locale locale = searchContext.getLocale();

			doIndexDictionary(companyId, locale);
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

		for (Map.Entry<String, String> nGramsEntry : nGrams.entrySet()) {
			String fieldName = nGramsEntry.getKey();
			String fieldValue = nGramsEntry.getValue();

			addField(
				document, fieldName, fieldValue, Field.Store.NO,
				FieldInfo.IndexOptions.DOCS_ONLY, true);
		}
	}

	protected Document createDocument(
			String localizedFieldName, String word, Locale locale)
		throws SearchException {

		Document document = new Document();

		addField(
			document, com.liferay.portal.kernel.search.Field.PORTLET_ID,
			PortletKeys.SEARCH, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		addField(
			document, localizedFieldName, word, Field.Store.YES,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		addField(
			document, com.liferay.portal.kernel.search.Field.LOCALE,
			locale.toString(), Field.Store.NO,
			FieldInfo.IndexOptions.DOCS_ONLY, true);

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(word);

		Map<String, String> nGramEnds = nGramHolder.getNGramEnds();
		addNGramFields(document, nGramEnds);

		Map<String, List<String>> nGrams = nGramHolder.getNGrams();

		for (Map.Entry<String, List<String>> nGramsEntry : nGrams.entrySet()) {
			String fieldName = nGramsEntry.getKey();

			for (String nGram : nGramsEntry.getValue()) {
				addField(
					document, fieldName, nGram, Field.Store.NO,
					FieldInfo.IndexOptions.DOCS_AND_FREQS, false);
			}
		}

		Map<String, String> nGramStarts = nGramHolder.getNGramStarts();
		addNGramFields(document, nGramStarts);

		return document;
	}

	protected void doIndexDictionary(long companyId, Locale locale)
		throws Exception {

		String dictionaryFileNameList = PropsUtil.get(
			PropsKeys.LUCENE_SPELL_CHECKER_DICTIONARY,
			new Filter(locale.toString()));

		String[] dictionaryFileNames = StringUtil.split(dictionaryFileNameList);

		IndexAccessor indexAccessor = LuceneHelperUtil.getIndexAccessor(
			companyId);

		for (String dictionaryFileName : dictionaryFileNames) {
			if (_log.isInfoEnabled()) {
				_log.info(
					"Commencing dictionary indexing for: " + locale + " - " +
					dictionaryFileName);
			}

			URL dictionaryURL = getResource(dictionaryFileName);

			InputStream dictionaryInputStream = null;

			try {
				dictionaryInputStream = dictionaryURL.openStream();

				if (dictionaryInputStream == null) {
					if (_log.isWarnEnabled()) {
						_log.warn("No dictionary defined for: " + locale);
					}

					continue;
				}

				IndexSearcher indexSearcher = LuceneHelperUtil.getSearcher(
					indexAccessor.getCompanyId(), true);

				List<IndexReader> indexReaders = new ArrayList<IndexReader>();

				if (indexSearcher.maxDoc() > 0) {
					ReaderUtil.gatherSubReaders(
						indexReaders, indexSearcher.getIndexReader());
				}

				String localizedFieldName = DocumentImpl.getLocalizedName(
					locale,
					com.liferay.portal.kernel.search.Field.SPELL_CHECK_WORD);

				Collection<Document> documents = new ArrayList<Document>();

				Dictionary dictionary = new PlainTextDictionary(
					dictionaryInputStream);

				Iterator<String> wordsIterator = dictionary.getWordsIterator();

				while (wordsIterator.hasNext()) {
					String word = wordsIterator.next();

					boolean validWord = validateWord(
						localizedFieldName, word, indexReaders);

					if (!validWord) {
						continue;
					}

					Document document = createDocument(
						localizedFieldName, word, locale);

					documents.add(document);
				}

				indexAccessor.addDocuments(documents);
			}
			finally {
				StreamUtil.cleanUp(dictionaryInputStream);
			}
		}

		if (_log.isInfoEnabled()) {
			_log.info("Completing text file based dictionary: " + locale);
		}
	}

	protected URL getResource(String name) {
		Class<?> clazz = getClass();

		ClassLoader classLoader = clazz.getClassLoader();

		return classLoader.getResource(name);
	}

	protected boolean validateWord(
			String localizedFieldName, String word,
			List<IndexReader> indexReaders)
		throws IOException {

		int length = word.length();

		if (length < _MINIMUM_WORD_LENGTH) {
			return false;
		}

		if (SpellCheckerUtil.validateWordExists(
				localizedFieldName, word, indexReaders)) {

			return false;
		}

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		LuceneSpellCheckIndexWriter.class);
	private static final int _MINIMUM_WORD_LENGTH = 3;

}