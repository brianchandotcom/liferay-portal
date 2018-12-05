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

package com.liferay.segments.internal.asah.client.data.binding;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

import com.liferay.segments.internal.asah.client.model.IndividualSegment;
import com.liferay.segments.internal.asah.client.model.PageMetadata;
import com.liferay.segments.internal.asah.client.model.Rels;
import com.liferay.segments.internal.asah.client.model.Results;

import java.io.IOException;

import java.util.List;

/**
 * @author David Arques
 */
public class IndividualSegmentJSONObjectMapper {

	public IndividualSegmentJSONObjectMapper() {
		_objectMapper = new ObjectMapper();

		_objectMapper.configure(
			DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	public IndividualSegment map(String json) throws IOException {
		return _objectMapper.readValue(json, IndividualSegment.class);
	}

	public Results<IndividualSegment> mapToResults(String json)
		throws IOException {

		JsonNode responseJsonNode = _objectMapper.readTree(json);

		JsonNode embeddedJsonNode = responseJsonNode.get("_embedded");

		JsonNode individualSegmentsJsonNode = embeddedJsonNode.get(
			Rels.INDIVIDUAL_SEGMENTS);

		ObjectReader individualSegmentsReader = _objectMapper.readerFor(
			new TypeReference<List<IndividualSegment>>() {});

		List<IndividualSegment> individualSegments =
			individualSegmentsReader.readValue(individualSegmentsJsonNode);

		JsonNode pageJsonNode = responseJsonNode.get("page");

		PageMetadata pageMetadata = _objectMapper.treeToValue(
			pageJsonNode, PageMetadata.class);

		return new Results<>(
			individualSegments, (int)pageMetadata.getTotalElements());
	}

	private final ObjectMapper _objectMapper;

}