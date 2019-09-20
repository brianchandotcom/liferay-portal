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

package com.liferay.talend.runtime.reader;

import com.liferay.talend.avro.JsonObjectIndexedRecordConverter;
import com.liferay.talend.runtime.LiferaySource;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;

import java.io.IOException;

import java.net.URI;

import java.util.NoSuchElementException;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonValue;

import javax.ws.rs.core.UriBuilder;

import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.exception.ComponentException;

/**
 * @author Zoltán Takács
 * @author Igor Beslic
 */
public class LiferayInputReader extends LiferayBaseReader<IndexedRecord> {

	public LiferayInputReader(
		RuntimeContainer runtimeContainer, LiferaySource liferaySource,
		TLiferayInputProperties tLiferayInputProperties) {

		super(runtimeContainer, liferaySource);

		liferayConnectionResourceBaseProperties = tLiferayInputProperties;

		_jsonObjectIndexedRecordConverter =
			new JsonObjectIndexedRecordConverter(
				tLiferayInputProperties.getSchema());
	}

	@Override
	public boolean advance() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader was not started");
		}

		_currentItemIndex++;

		if (_currentItemIndex < _itemsJsonArray.size()) {
			dataCount++;
			_hasMore = true;

			return true;
		}

		if (_currentPage > _lastPage) {
			_hasMore = false;

			return false;
		}

		_readEndpointJsonObject();

		if (_itemsJsonArray.size() <= 0) {
			_hasMore = false;

			return false;
		}

		_hasMore = true;

		dataCount++;

		return true;
	}

	@Override
	public IndexedRecord getCurrent() throws NoSuchElementException {
		if (!_started) {
			throw new NoSuchElementException("Reader was not started");
		}

		if (!_hasMore) {
			throw new NoSuchElementException(
				"Resource does not have more elements");
		}

		try {
			JsonValue currentJsonValue = getCurrentJsonValue();

			if (!(currentJsonValue instanceof JsonObject)) {
				throw new ComponentException(
					new IllegalArgumentException(
						"Expected json object instead of " +
							currentJsonValue.getClass()));
			}

			return _jsonObjectIndexedRecordConverter.toIndexedRecord(
				currentJsonValue.asJsonObject());
		}
		catch (Exception e) {
			throw new ComponentException(e);
		}
	}

	public JsonValue getCurrentJsonValue() throws NoSuchElementException {
		if (_currentItemIndex < _itemsJsonArray.size()) {
			return _itemsJsonArray.get(_currentItemIndex);
		}

		throw new NoSuchElementException(
			"JSON array does not have more elements");
	}

	@Override
	public boolean start() throws IOException {
		if (_started) {
			throw new IllegalStateException("Reader has already started");
		}

		_currentPage = 1;
		_lastPage = Integer.MIN_VALUE;

		_readEndpointJsonObject();

		if (_itemsJsonArray.isEmpty()) {
			return false;
		}

		dataCount = 0;
		_hasMore = true;
		_started = true;

		return true;
	}

	private void _readEndpointJsonObject() {
		UriBuilder uriBuilder = UriBuilder.fromUri(
			liferayConnectionResourceBaseProperties.resource.getEndpointURI());

		URI resourceURI = uriBuilder.queryParam(
			"page", _currentPage++
		).queryParam(
			"pageSize",
			liferayConnectionResourceBaseProperties.getItemsPerPage()
		).build();

		_currentItemIndex = 0;

		LiferaySource liferaySource = (LiferaySource)getCurrentSource();

		if (_logger.isDebugEnabled()) {
			_logger.debug(
				"Started to process resources at entry point: " +
					resourceURI.toString());
		}

		JsonObject jsonObject = liferaySource.doGetRequest(
			resourceURI.toString());

		if (jsonObject.containsKey("page")) {
			if (jsonObject.containsKey("items")) {
				_itemsJsonArray = jsonObject.getJsonArray("items");

				if (jsonObject.containsKey("lastPage")) {
					_lastPage = jsonObject.getInt("lastPage");
				}

				return;
			}

			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

			_itemsJsonArray = jsonArrayBuilder.build();

			_currentPage = 1;
			_lastPage = 1;

			return;
		}

		JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

		jsonArrayBuilder.add(jsonObject);

		_itemsJsonArray = jsonArrayBuilder.build();

		_currentPage = 1;
		_lastPage = 1;
	}

	private static final Logger _logger = LoggerFactory.getLogger(
		LiferayInputReader.class);

	private transient int _currentItemIndex;
	private int _currentPage;
	private boolean _hasMore;
	private transient JsonArray _itemsJsonArray;
	private final JsonObjectIndexedRecordConverter
		_jsonObjectIndexedRecordConverter;
	private int _lastPage;
	private boolean _started;

}