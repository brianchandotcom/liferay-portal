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

package com.liferay.portal.kernel.search;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.PortletKeys;

import java.io.InputStream;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public abstract class BaseGenericSpellCheckIndexWriter
	extends BaseSpellCheckIndexWriter {

	public void setBatchSize(int batchSize) {
		_batchSize = batchSize;
	}

	public void setDocumentPrototype(Document documentPrototype) {
		_documentPrototype = documentPrototype;
	}

	public void setIndexWriter(IndexWriter indexWriter) {
		_indexWriter = indexWriter;
	}

	protected void addNGramFields(
		Document document, Map<String, String> nGrams) {

		for (Map.Entry<String, String> nGramEntry : nGrams.entrySet()) {
			document.addKeyword(nGramEntry.getKey(), nGramEntry.getValue());
		}
	}

	protected Document createDocument(
			long companyId, long groupId, String languageId, String keywords,
			float weight, String keywordFieldName, String typeFieldValue,
			int maxNGramLength)
		throws SearchException {

		Document document = (Document)_documentPrototype.clone();

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.LANGUAGE_ID, languageId);
		document.addKeyword(Field.PORTLET_ID, PortletKeys.SEARCH);
		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
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

	protected void indexKeyword(
			long companyId, long groupId, String languageId, String keyword,
			float weight, String keywordFieldName, String typeFieldValue,
			int maxNGramLength)
		throws Exception {

		Document document = createDocument(
			companyId, groupId, languageId, keyword, weight, keywordFieldName,
			typeFieldValue, maxNGramLength);

		_indexWriter.addDocument(null, document);
	}

	@Override
	protected void indexKeywords(
			long companyId, long groupId, String languageId,
			InputStream inputStream, String keywordFieldName,
			String typeFieldValue, int maxNGramLength)
		throws Exception {

		Set<Document> documents = new HashSet<Document>();

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
					companyId, groupId, languageId, dictionaryEntry.getWord(),
					dictionaryEntry.getWeight(), keywordFieldName,
					typeFieldValue, maxNGramLength);

				documents.add(document);

				if ((counter == _batchSize) || !iterator.hasNext()) {
					_indexWriter.addDocuments(null, documents);

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

	private static Log _log = LogFactoryUtil.getLog(
		BaseGenericSpellCheckIndexWriter.class);

	private int _batchSize = _DEFAULT_BATCH_SIZE;
	private Document _documentPrototype = new DocumentImpl();
	private IndexWriter _indexWriter;

}