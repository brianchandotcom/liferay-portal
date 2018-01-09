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

package com.liferay.consumer.talend.runtime.reader;

import com.fasterxml.jackson.databind.JsonNode;

import com.liferay.consumer.talend.avro.ResourceEntityConverter;
import com.liferay.consumer.talend.runtime.LiferaySource;
import com.liferay.consumer.talend.runtime.client.ApioJsonLDUtils;
import com.liferay.consumer.talend.tliferayinput.TLiferayInputProperties;

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
		RuntimeContainer container, LiferaySource source,
		TLiferayInputProperties props) {

		super(container, source);
		properties = props;
	}

	@Override
	public boolean advance() throws IOException {
		if (!_started) {
			throw new IllegalStateException("Reader wasn't started");
		}

		_inputRecordsIndex++;

		// Fast return conditions.

		if (_inputRecordsIndex < _inputRecords.size()) {
			dataCount++;
			_hasMore = true;

			return true;
		}
		else {
			JsonNode view = ApioJsonLDUtils.getCollectionViewNode(_resource);

			String actual = ApioJsonLDUtils.getResourceActualPage(view);
			String last = ApioJsonLDUtils.getResourceLastPage(view);

			if (actual.equals(last)) {
				_hasMore = false;

				return false;
			}

			_hasMore = true;
		}

		LiferaySource source = (LiferaySource)getCurrentSource();

		JsonNode view = ApioJsonLDUtils.getCollectionViewNode(_resource);

		String next = ApioJsonLDUtils.getResourceNextPage(view);

		_resource = source.getResourceCollection(next);

		_inputRecords = ApioJsonLDUtils.getCollectionMemberNode(_resource);

		_inputRecordsIndex = 0;

		_hasMore = _inputRecords.size() > 0;

		if (_hasMore) {

			// New result set available to retrieve

			dataCount++;
		}

		return _hasMore;
	}

	@Override
	public IndexedRecord getCurrent() throws NoSuchElementException {
		if (!_started) {
			throw new NoSuchElementException("Reader wasn't started");
		}

		if (!_hasMore) {
			throw new NoSuchElementException(
				"Resource doesn't have more elements");
		}

		try {
			return getConverter().convertToAvro(getCurrentObject());
		}
		catch (IOException ioe) {
			throw new ComponentException(ioe);
		}
	}

	public List getCurrentObject() throws NoSuchElementException {
		JsonNode resourceNode = _inputRecords.get(_inputRecordsIndex);

		if (resourceNode == null) {
			_log.error("Index: {}", _inputRecordsIndex);

			throw new NoSuchElementException("Index: " + _inputRecordsIndex);
		}

		List<String> resourceValues = new ArrayList<>();

		Iterator<JsonNode> elements = resourceNode.elements();

		while (elements.hasNext()) {
			JsonNode node = elements.next();

			resourceValues.add(node.toString());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Resource has been processed");
		}

		return resourceValues;
	}

	@Override
	public boolean start() throws IOException {
		LiferaySource source = (LiferaySource)getCurrentSource();
		String resourceURL = properties.resource.resourceURL.getValue();

		_resource = source.getResourceCollection(resourceURL);

		_inputRecords = ApioJsonLDUtils.getCollectionMemberNode(_resource);

		boolean start = false;

		if (_inputRecords.size() > 0) {
			start = true;
		}

		if (start == false) {
			return false;
		}

		_inputRecordsIndex = 0;
		dataCount++;
		_started = true;
		_hasMore = true;

		return start;
	}

	/**
	 * Returns implementation of {@link AvroConverter}, creates it if it doesn't
	 * exist.
	 *
	 * @return converter
	 * @throws IOException
	 */
	protected ResourceEntityConverter getConverter() throws IOException {
		if (_converter == null) {
			_converter = new ResourceEntityConverter(getSchema());
		}

		return _converter;
	}

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayInputReader.class);

	/**
	 * Converts row retrieved from data source to Avro format
	 * {@link IndexedRecord}
	 */
	private ResourceEntityConverter _converter;

	/**
	 * Represents state of this Reader: whether it has more records
	 */
	private boolean _hasMore;

	/**
	 * Resource collection members field
	 */
	private transient JsonNode _inputRecords;

	private transient int _inputRecordsIndex;
	private transient JsonNode _resource;

	/**
	 * Represents state of this Reader: whether it was started or not
	 */
	private boolean _started;

}