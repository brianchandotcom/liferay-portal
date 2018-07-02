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

package com.liferay.portal.kernel.search.suggest;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;

import java.io.InputStream;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author     Michael C. Han
 * @deprecated As of Judson (7.1.x), moved to {@link
 *             com.liferay.portal.search.suggest.BaseGenericSpellCheckIndexWriter}
 */
@Deprecated
public abstract class BaseGenericSpellCheckIndexWriter
	extends BaseSpellCheckIndexWriter {

	public void setBatchSize(int batchSize) {
		_batchSize = batchSize;
	}

	public void setDocumentPrototype(Document documentPrototype) {
		_documentPrototype = documentPrototype;
	}

	protected abstract void addDocument(
			String documentType, SearchContext searchContext, Document document)
		throws SearchException;

	protected abstract void addDocuments(
			String documentType, SearchContext searchContext,
			Collection<Document> documents)
		throws SearchException;

	protected void addNGramFields(
		Document document, Map<String, String> nGrams) {

		for (Map.Entry<String, String> nGramEntry : nGrams.entrySet()) {
			document.addKeyword(nGramEntry.getKey(), nGramEntry.getValue());
		}
	}

	protected Document createDocument() {
		return (Document)_documentPrototype.clone();
	}

	protected Document createDocument(
			long companyId, long groupId, String languageId, String keywords,
			float weight, String keywordFieldName, String typeFieldValue,
			int maxNGramLength)
		throws SearchException {

		Document document = createDocument();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.LANGUAGE_ID, languageId);
		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
		document.addKeyword(Field.SPELL_CHECK_WORD, true);
		document.addKeyword(keywordFieldName, keywords);
		document.addKeyword(Field.TYPE, typeFieldValue);
		document.addKeyword(Field.UID, getUID(companyId, languageId, keywords));

		NGramHolder nGramHolder = NGramHolderBuilderUtil.buildNGramHolder(
			keywords, maxNGramLength);

		addNGramFields(document, nGramHolder.getNGramEnds());

		Map<String, List<String>> nGrams = nGramHolder.getNGrams();

		for (Map.Entry<String, List<String>> entry : nGrams.entrySet()) {
			String fieldName = entry.getKey();

			for (String nGram : entry.getValue()) {
				document.addKeyword(fieldName, nGram);
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

		Document document = createDocument(
			searchContext.getCompanyId(), groupId, languageId, keyword, weight,
			keywordFieldName, typeFieldValue, maxNGramLength);

		addDocument(typeFieldValue, searchContext, document);
	}

	@Override
	protected void indexKeywords(
			SearchContext searchContext, long groupId, String languageId,
			InputStream inputStream, String keywordFieldName,
			String typeFieldValue, int maxNGramLength)
		throws Exception {

		Set<Document> documents = new HashSet<>();

		try {
			DictionaryReader dictionaryReader = new DictionaryReader(
				inputStream, StringPool.UTF8);

			Iterator<DictionaryEntry> iterator =
				dictionaryReader.getDictionaryEntriesIterator();

			int counter = 0;

			while (iterator.hasNext()) {
				counter++;

				DictionaryEntry dictionaryEntry = iterator.next();

				Document document = createDocument(
					searchContext.getCompanyId(), groupId, languageId,
					dictionaryEntry.getWord(), dictionaryEntry.getWeight(),
					keywordFieldName, typeFieldValue, maxNGramLength);

				documents.add(document);

				if ((counter == _batchSize) || !iterator.hasNext()) {
					addDocuments(typeFieldValue, searchContext, documents);

					documents.clear();

					counter = 0;
				}
			}
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index dictionaries", e);
			}

			throw new SearchException(e.getMessage(), e);
		}
	}

	private static final int _DEFAULT_BATCH_SIZE = 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseGenericSpellCheckIndexWriter.class);

	private int _batchSize = _DEFAULT_BATCH_SIZE;
	private Document _documentPrototype = new DocumentImpl();

}