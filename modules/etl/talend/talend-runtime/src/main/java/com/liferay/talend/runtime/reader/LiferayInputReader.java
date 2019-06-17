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

import com.liferay.talend.avro.ResourceNodeConverter;
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
import org.talend.daikon.avro.converter.AvroConverter;

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
	}

	@Override
	public boolean advance() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader was not started");
		}

		_inputRecordsIndex++;

		// Fast return conditions

		if (_inputRecordsIndex < _inputRecordsJsonArray.size()) {
			dataCount++;
			_hasMore = true;

			return true;
		}

		int actual = _endpointJsonObject.getInt("page");
		int last = _endpointJsonObject.getInt("lastPage");

		if (actual >= last) {
			_hasMore = false;

			return false;
		}

		_hasMore = true;

		URI endpointURI =
			liferayConnectionResourceBaseProperties.resource.getEndpointURI();

		_endpointJsonObject = _getEndpointJsonNode(endpointURI, ++actual, -1);

		_inputRecordsJsonArray = _endpointJsonObject.getJsonArray("items");

		_inputRecordsIndex = 0;

		_hasMore = _inputRecordsJsonArray.size() > 0;

		if (_hasMore) {

			// New result set available to retrieve

			dataCount++;
		}

		return _hasMore;
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
			AvroConverter<Object, IndexedRecord> avroConverter = getConverter();

			return avroConverter.convertToAvro(getCurrentJsonNode());
		}
		catch (IOException ioe) {
			throw new ComponentException(ioe);
		}
	}

	public JsonValue getCurrentJsonNode() throws NoSuchElementException {
		return _inputRecordsJsonArray.get(_inputRecordsIndex);
	}

	@Override
	public boolean start() throws IOException {
		URI endpointURI =
			liferayConnectionResourceBaseProperties.resource.getEndpointURI();

		_endpointJsonObject = _getEndpointJsonNode(endpointURI, 1, -1);

		if (_endpointJsonObject.containsKey("items")) {
			_inputRecordsJsonArray = _endpointJsonObject.getJsonArray("items");

			boolean start = false;

			if (_inputRecordsJsonArray.size() > 0) {
				start = true;
			}

			if (!start) {
				return false;
			}
		}
		else {
			JsonArrayBuilder jsonArrayBuilder = Json.createArrayBuilder();

			jsonArrayBuilder.add(_endpointJsonObject);

			_inputRecordsJsonArray = jsonArrayBuilder.build();
		}

		dataCount++;
		_inputRecordsIndex = 0;
		_started = true;
		_hasMore = true;

		return true;
	}

	/**
	 * Returns implementation of AvroConverter, creates it if it does not exist.
	 *
	 * @return converter
	 * @throws IOException
	 * @review
	 */
	protected AvroConverter<Object, IndexedRecord> getConverter()
		throws IOException {

		if (_resourceEntityAvroConverter == null) {
			_resourceEntityAvroConverter = new ResourceNodeConverter(
				getSchema());
		}

		return _resourceEntityAvroConverter;
	}

	private JsonObject _getEndpointJsonNode(
		URI endpointURI, int page, int pageSize) {

		if (page <= 0) {
			page = 1;
		}

		if (pageSize == -1) {
			pageSize =
				liferayConnectionResourceBaseProperties.connection.itemsPerPage.
					getValue();
		}

		UriBuilder uriBuilder = UriBuilder.fromUri(endpointURI);

		URI resourceURI = uriBuilder.queryParam(
			"page", page
		).queryParam(
			"pageSize", pageSize
		).build();

		LiferaySource liferaySource = (LiferaySource)getCurrentSource();

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Started to process resources at entry point: " +
					resourceURI.toString());
		}

		return liferaySource.doGetRequest(resourceURI.toString());
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayInputReader.class);

	private transient JsonObject _endpointJsonObject;

	/**
	 * Represents state of this Reader: whether it has more records
	 *
	 * @review
	 */
	private boolean _hasMore;

	private transient int _inputRecordsIndex;

	/**
	 * Resource collection members field
	 *
	 * @review
	 */
	private transient JsonArray _inputRecordsJsonArray;

	/**
	 * Converts row retrieved from data source to Avro format {@link
	 * IndexedRecord}
	 *
	 * @review
	 */
	private AvroConverter _resourceEntityAvroConverter;

	/**
	 * Represents state of this Reader: whether it was started or not
	 *
	 * @review
	 */
	private boolean _started;

}