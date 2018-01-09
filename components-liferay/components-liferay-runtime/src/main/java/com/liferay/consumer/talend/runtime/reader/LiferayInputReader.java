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

import com.liferay.consumer.talend.avro.ResourceEntityConverter;
import com.liferay.consumer.talend.runtime.LiferaySource;
import com.liferay.consumer.talend.runtime.client.ApioJsonLDUtils;
import com.liferay.consumer.talend.tliferayinput.TLiferayInputProperties;

import com.fasterxml.jackson.databind.JsonNode;

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
import org.talend.daikon.avro.converter.AvroConverter;

/**
 * @author Zoltán Takács
 */
public class LiferayInputReader extends LiferayBaseReader<IndexedRecord> {

	public LiferayInputReader(RuntimeContainer container, LiferaySource source,
		TLiferayInputProperties props) {

		super(container, source);
		properties = props;
	}

	@Override
	public boolean start() throws IOException {
		LiferaySource source = (LiferaySource)getCurrentSource();
		String resourceURL = properties.resource.resourceURL.getValue();

		resource = source.getResourceCollection(resourceURL);

		inputRecords = ApioJsonLDUtils.getCollectionMemberNode(resource);

		boolean start = (inputRecords.size() > 0);

		if (start == false) {
			return false;
		}

		inputRecordsIndex = 0;
		dataCount++;
		started = true;
		hasMore = true;

		return start;
	}

	@Override
	public boolean advance() throws IOException {
		if (!started) {
			throw new IllegalStateException("Reader wasn't started");
		}

		inputRecordsIndex++;

		// Fast return conditions.
		if (inputRecordsIndex < inputRecords.size()) {
			dataCount++;
			hasMore = true;

			return true;
		}
		else {
			JsonNode view = ApioJsonLDUtils.getCollectionViewNode(resource);

			String actual = ApioJsonLDUtils.getResourceActualPage(view);
			String last = ApioJsonLDUtils.getResourceLastPage(view);

			if (actual.equals(last)) {
				hasMore = false;

				return false;
			}

			hasMore = true;
		}

		LiferaySource source = (LiferaySource)getCurrentSource();

		JsonNode view = ApioJsonLDUtils.getCollectionViewNode(resource);
		String next = ApioJsonLDUtils.getResourceNextPage(view);

		resource = source.getResourceCollection(next);

		inputRecords = ApioJsonLDUtils.getCollectionMemberNode(resource);
		inputRecordsIndex = 0;

		hasMore = (inputRecords.size() > 0);

		if (hasMore) {
			// New result set available to retrieve
			dataCount++;
		}

		return hasMore;
	}

	public List getCurrentObject() throws NoSuchElementException {
		JsonNode resourceNode = inputRecords.get(inputRecordsIndex);

		if (resourceNode == null) {
			_log.error("Index: {}", inputRecordsIndex);

			throw new NoSuchElementException("Index: " + inputRecordsIndex);
		}

		List<String> resourceValues = new ArrayList<>();

		Iterator<JsonNode> elements = resourceNode.elements();
		while(elements.hasNext()){
			JsonNode node = elements.next();
			resourceValues.add(node.toString());
		}

		if (_log.isDebugEnabled()) {
			_log.debug("Resource has been processed");
		}

		return resourceValues;
	}

	@Override
	public IndexedRecord getCurrent() throws NoSuchElementException {
		if (!started) {
			throw new NoSuchElementException("Reader wasn't started");
		}
		if (!hasMore) {
			throw new NoSuchElementException("Resource doesn't have more elements");
		}

		try {
			return getConverter().convertToAvro(getCurrentObject());
		}
		catch (IOException ioe) {
			throw new ComponentException(ioe);
		}
	}

	/**
	 * Returns implementation of {@link AvroConverter}, creates it if it doesn't
	 * exist.
	 *
	 * @return converter
	 * @throws IOException
	 */
	protected ResourceEntityConverter getConverter() throws IOException {
		if (converter == null) {
			converter = new ResourceEntityConverter(getSchema());
		}

		return converter;
	}

	private IndexedRecord currentRecord;

	/**
	 * Converts row retrieved from data source to Avro format
	 * {@link IndexedRecord}
	 */
	private ResourceEntityConverter converter;
	/*
	 * Represents state of this Reader: whether it has more records
	 */
	private boolean hasMore = false;

	/**
	 * Resource collection members field
	 */
	private transient JsonNode inputRecords;

	private transient int inputRecordsIndex;

	private transient JsonNode resource;

	/**
	 * Represents state of this Reader: whether it was started or not
	 */
	private boolean started = false;

	private static final Logger _log =
		LoggerFactory.getLogger(LiferayInputReader.class);

}
