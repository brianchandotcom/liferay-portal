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

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.talend.avro.ResourceEntityConverter;
import com.liferay.talend.runtime.LiferaySource;
import com.liferay.talend.runtime.apio.jsonld.ApioResourceCollection;
import com.liferay.talend.tliferayinput.TLiferayInputProperties;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.apache.avro.generic.IndexedRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.container.RuntimeContainer;
import org.talend.components.api.exception.ComponentException;

/**
 * @author Zoltán Takács
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

		if (_inputRecordsIndex < _inputRecordsJsonNode.size()) {
			dataCount++;
			_hasMore = true;

			return true;
		}
		else {
			String actual = _apioResourceCollection.getResourceActualPage();
			String last = _apioResourceCollection.getResourceLastPage();

			if (actual.equals(last)) {
				_hasMore = false;

				return false;
			}

			_hasMore = true;
		}

		LiferaySource liferaySource = (LiferaySource)getCurrentSource();

		String next = _apioResourceCollection.getResourceNextPage();

		_apioResourceCollection = new ApioResourceCollection(
			liferaySource.doApioGetRequest(next));

		_inputRecordsJsonNode = _apioResourceCollection.getMemberJsonNode();

		_inputRecordsIndex = 0;

		_hasMore = _inputRecordsJsonNode.size() > 0;

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
			ResourceEntityConverter resourceEntityConverter = getConverter();

			return resourceEntityConverter.convertToAvro(getCurrentObject());
		}
		catch (IOException ioe) {
			throw new ComponentException(ioe);
		}
	}

	public JsonNode getCurrentJsonNode() throws NoSuchElementException {
		JsonNode resourceJsonNode = _inputRecordsJsonNode.get(
			_inputRecordsIndex);

		return resourceJsonNode;
	}

	public List<String> getCurrentObject() throws NoSuchElementException {
		JsonNode resourceJsonNode = _inputRecordsJsonNode.get(
			_inputRecordsIndex);

		if (resourceJsonNode == null) {
			_log.error("Index: {}", _inputRecordsIndex);

			throw new NoSuchElementException("Index: " + _inputRecordsIndex);
		}

		List<String> resourceValues = new ArrayList<>();

		Iterator<JsonNode> iterator = resourceJsonNode.elements();

		while (iterator.hasNext()) {
			JsonNode jsonNode = iterator.next();

			resourceValues.add(jsonNode.toString());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Resource has been processed");
		}

		return resourceValues;
	}

	@Override
	public boolean start() throws IOException {
		LiferaySource liferaySource = (LiferaySource)getCurrentSource();

		String resourceURL =
			liferayConnectionResourceBaseProperties.resource.resourceURL.
				getValue();

		_apioResourceCollection = new ApioResourceCollection(
			liferaySource.doApioGetRequest(resourceURL));

		_inputRecordsJsonNode = _apioResourceCollection.getMemberJsonNode();

		boolean start = false;

		if (_inputRecordsJsonNode.size() > 0) {
			start = true;
		}

		if (start == false) {
			return false;
		}

		dataCount++;
		_inputRecordsIndex = 0;
		_started = true;
		_hasMore = true;

		return start;
	}

	/**
	 * Returns implementation of {@link AvroConverter}, creates it if it does
	 * not exist.
	 *
	 * @return converter
	 * @throws IOException
	 */
	protected ResourceEntityConverter getConverter() throws IOException {
		if (_resourceEntityConverter == null) {
			_resourceEntityConverter = new ResourceEntityConverter(getSchema());
		}

		return _resourceEntityConverter;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayInputReader.class);

	private transient ApioResourceCollection _apioResourceCollection;

	/**
	 * Represents state of this Reader: whether it has more records
	 */
	private boolean _hasMore;

	private transient int _inputRecordsIndex;

	/**
	 * Resource collection members field
	 */
	private transient JsonNode _inputRecordsJsonNode;

	/**
	 * Converts row retrieved from data source to Avro format {@link
	 * IndexedRecord}
	 */
	private ResourceEntityConverter _resourceEntityConverter;

	/**
	 * Represents state of this Reader: whether it was started or not
	 */
	private boolean _started;

}