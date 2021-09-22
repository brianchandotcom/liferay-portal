/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.search.experiences.internal.search.searcher;

import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.search.experiences.rest.dto.v1_0.Document;
import com.liferay.search.experiences.rest.dto.v1_0.DocumentField;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;

import java.beans.ExceptionListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchResponseToDTOTranslator {

	public SearchResponseToDTOTranslator(
		com.liferay.portal.search.searcher.SearchResponse
			portalSearchResponse) {

		_portalSearchResponse = portalSearchResponse;
	}

	public SearchResponse translate() {
		SearchRequest searchRequest = _portalSearchResponse.getRequest();

		if (searchRequest != null) {
			_searchResponse.setPage(searchRequest.getFrom());
			_searchResponse.setPageSize(searchRequest.getSize());
		}

		_searchResponse.setRequestString(
			_portalSearchResponse.getRequestString());
		_searchResponse.setResponseString(
			_portalSearchResponse.getResponseString());

		_searchResponse.setTotalHits(_portalSearchResponse.getTotalHits());

		_processDocuments(_portalSearchResponse.getDocumentsStream());

		return _searchResponse;
	}

	private <K, V> void _forEach(
		Map<K, V> map, BiConsumer<K, V> biConsumer,
		ExceptionListener exceptionListener) {

		map.forEach(
			(key, value) -> {
				try {
					biConsumer.accept(key, value);
				}
				catch (Exception exception) {
					exceptionListener.exceptionThrown(exception);
				}
			});
	}

	private <V> void _forEach(
		Stream<V> stream, Consumer<V> consumer,
		ExceptionListener exceptionListener) {

		stream.forEach(
			value -> {
				try {
					consumer.accept(value);
				}
				catch (Exception exception) {
					exceptionListener.exceptionThrown(exception);
				}
			});
	}

	private void _processDocuments(
		Stream<com.liferay.portal.search.document.Document> stream) {

		List<Document> list = new ArrayList<>();

		_forEach(
			stream,
			portalSearchDocument -> list.add(_toDocument(portalSearchDocument)),
			_runtimeException::addSuppressed);

		_searchResponse.setDocuments(list.toArray(new Document[0]));
	}

	private Document _toDocument(
		com.liferay.portal.search.document.Document portalSearchDocument) {

		Document document = new Document();

		List<DocumentField> list = new ArrayList<>();

		Map<String, Field> map = portalSearchDocument.getFields();

		_forEach(
			map,
			(name, portalSearchField) -> list.add(
				_toDocumentField(name, portalSearchField)),
			_runtimeException::addSuppressed);

		document.setDocumentFields(list.toArray(new DocumentField[0]));

		return document;
	}

	private DocumentField _toDocumentField(String name, Field field) {
		DocumentField documentField = new DocumentField();

		documentField.setName(name);

		documentField.setValueString(String.valueOf(field.getValue()));

		return documentField;
	}

	private final com.liferay.portal.search.searcher.SearchResponse
		_portalSearchResponse;
	private final RuntimeException _runtimeException = new RuntimeException();
	private final SearchResponse _searchResponse = new SearchResponse();

}