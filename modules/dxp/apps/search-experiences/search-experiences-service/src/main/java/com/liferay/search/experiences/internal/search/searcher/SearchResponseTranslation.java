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

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.search.document.Field;
import com.liferay.portal.search.searcher.SearchRequest;
import com.liferay.search.experiences.rest.dto.v1_0.Document;
import com.liferay.search.experiences.rest.dto.v1_0.DocumentField;
import com.liferay.search.experiences.rest.dto.v1_0.SearchResponse;

import java.beans.ExceptionListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author André de Oliveira
 */
public class SearchResponseTranslation {

	public SearchResponseTranslation(
		JSONFactory jsonFactory,
		com.liferay.portal.search.searcher.SearchResponse
			portalSearchResponse) {

		_jsonFactory = jsonFactory;
		_portalSearchResponse = portalSearchResponse;
	}

	public SearchResponse translate() {
		SearchRequest searchRequest = _portalSearchResponse.getRequest();

		if (searchRequest != null) {
			_searchResponse.setPage(searchRequest.getFrom());
			_searchResponse.setPageSize(searchRequest.getSize());
		}

		String requestString = _portalSearchResponse.getRequestString();

		_searchResponse.setRequest(_toJSONObject(requestString));
		_searchResponse.setRequestString(requestString);

		String responseString = _portalSearchResponse.getResponseString();

		_searchResponse.setResponse(_toJSONObject(responseString));
		_searchResponse.setResponseString(responseString);

		_searchResponse.setTotalHits(_portalSearchResponse.getTotalHits());

		_searchResponse.setDocuments(
			_toDocuments(_portalSearchResponse.getDocumentsStream()));

		if (ArrayUtil.isNotEmpty(_runtimeException.getSuppressed())) {
			throw _runtimeException;
		}

		return _searchResponse;
	}

	private <T, U> BiConsumer<T, U> _safely(
		BiConsumer<T, U> biConsumer, ExceptionListener exceptionListener) {

		return (t, u) -> {
			try {
				biConsumer.accept(t, u);
			}
			catch (Exception exception) {
				exceptionListener.exceptionThrown(exception);
			}
		};
	}

	private <V> Consumer<V> _safely(
		Consumer<V> consumer, ExceptionListener exceptionListener) {

		return value -> {
			try {
				consumer.accept(value);
			}
			catch (Exception exception) {
				exceptionListener.exceptionThrown(exception);
			}
		};
	}

	private Document _toDocument(
		com.liferay.portal.search.document.Document portalSearchDocument) {

		Map<String, DocumentField> map = new LinkedHashMap<>();

		MapUtil.isNotEmptyForEach(
			portalSearchDocument.getFields(),
			_safely(
				(name, portalSearchField) -> map.put(
					name, _toDocumentField(portalSearchField)),
				_runtimeException::addSuppressed));

		Document document = new Document();

		document.setDocumentFields(map);

		return document;
	}

	private DocumentField _toDocumentField(Field field) {
		DocumentField documentField = new DocumentField();

		List<Object> values = field.getValues();

		documentField.setValues(values.toArray());

		return documentField;
	}

	private Document[] _toDocuments(
		Stream<com.liferay.portal.search.document.Document> stream) {

		List<Document> list = new ArrayList<>();

		stream.forEach(
			_safely(
				portalSearchDocument -> list.add(
					_toDocument(portalSearchDocument)),
				_runtimeException::addSuppressed));

		return list.toArray(new Document[0]);
	}

	private JSONObject _toJSONObject(String string) {
		try {
			return _jsonFactory.createJSONObject(string);
		}
		catch (JSONException jsonException) {

			// No need to log. This means the search engine is not JSON based

			return null;
		}
	}

	private final JSONFactory _jsonFactory;
	private final com.liferay.portal.search.searcher.SearchResponse
		_portalSearchResponse;
	private final RuntimeException _runtimeException = new RuntimeException();
	private final SearchResponse _searchResponse = new SearchResponse();

}