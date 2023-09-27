/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.suggest;

import com.liferay.osgi.util.service.Snapshot;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.DocumentImpl;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.suggest.NGramHolder;
import com.liferay.portal.kernel.search.suggest.NGramHolderBuilder;

import java.io.InputStream;

import java.util.Collection;
import java.util.HashSet;
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

		document.addKeyword(keywordFieldName, keywords);

		document.addKeyword(Field.COMPANY_ID, companyId);
		document.addKeyword(Field.GROUP_ID, groupId);
		document.addKeyword(Field.LANGUAGE_ID, languageId);
		document.addKeyword(Field.PRIORITY, String.valueOf(weight));
		document.addKeyword(Field.TYPE, typeFieldValue);
		document.addKeyword(
			Field.UID,
			getUID(companyId, keywordFieldName, languageId, keywords));

		NGramHolderBuilder nGramHolderBuilder = getNGramHolderBuilder();

		NGramHolder nGramHolder = nGramHolderBuilder.buildNGramHolder(
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

	protected NGramHolderBuilder getNGramHolderBuilder() {
		NGramHolderBuilder nGramHolderBuilder =
			_nGramHolderBuilderSnapshot.get();

		if (nGramHolderBuilder != null) {
			return nGramHolderBuilder;
		}

		return _defaultNGramHolderBuilder;
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

			try {
				dictionaryReader.accept(
					dictionaryEntry -> {
						try {
							Document document = createDocument(
								searchContext.getCompanyId(), groupId,
								languageId, dictionaryEntry.getWord(),
								dictionaryEntry.getWeight(), keywordFieldName,
								typeFieldValue, maxNGramLength);

							documents.add(document);

							if (documents.size() == _batchSize) {
								addDocuments(
									typeFieldValue, searchContext, documents);

								documents.clear();
							}
						}
						catch (SearchException searchException) {
							throw new RuntimeException(searchException);
						}
					});
			}
			catch (RuntimeException runtimeException) {
				Throwable throwable = runtimeException.getCause();

				if (throwable instanceof SearchException) {
					throw (SearchException)throwable;
				}

				throw runtimeException;
			}

			addDocuments(typeFieldValue, searchContext, documents);
		}
		catch (Exception exception) {
			if (_log.isWarnEnabled()) {
				_log.warn("Unable to index dictionaries", exception);
			}

			throw exception;
		}
	}

	private static final int _DEFAULT_BATCH_SIZE = 1000;

	private static final Log _log = LogFactoryUtil.getLog(
		BaseGenericSpellCheckIndexWriter.class);

	private static final NGramHolderBuilder _defaultNGramHolderBuilder =
		new NullNGramHolderBuilder();
	private static final Snapshot<NGramHolderBuilder>
		_nGramHolderBuilderSnapshot = new Snapshot<>(
			BaseGenericSpellCheckIndexWriter.class, NGramHolderBuilder.class,
			null, true);

	private int _batchSize = _DEFAULT_BATCH_SIZE;
	private Document _documentPrototype = new DocumentImpl();

}