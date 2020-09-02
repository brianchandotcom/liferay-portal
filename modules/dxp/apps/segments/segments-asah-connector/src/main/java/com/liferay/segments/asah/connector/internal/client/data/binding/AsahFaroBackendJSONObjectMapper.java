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

package com.liferay.segments.asah.connector.internal.client.data.binding;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.type.TypeFactory;

import com.liferay.segments.asah.connector.internal.client.model.PageMetadata;
import com.liferay.segments.asah.connector.internal.client.model.Results;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author David Arques
 */
public class AsahFaroBackendJSONObjectMapper {

	public static <T> T map(String json, Class<T> clazz) throws IOException {
		return ObjectMapperHolder._objectMapper.readValue(json, clazz);
	}

	public static <T> Results<T> mapToResults(
			String json, String embeddedRelName, Class<T> clazz)
		throws IOException {

		JsonNode responseJsonNode = ObjectMapperHolder._objectMapper.readTree(
			json);

		JsonNode embeddedJsonNode = responseJsonNode.get("_embedded");

		List<T> items = Collections.emptyList();

		if (embeddedJsonNode != null) {
			TypeFactory typeFactory = TypeFactory.defaultInstance();

			ObjectReader objectReader =
				ObjectMapperHolder._objectMapper.readerFor(
					typeFactory.constructCollectionType(
						ArrayList.class, clazz));

			JsonNode embeddedRelJsonNode = embeddedJsonNode.get(
				embeddedRelName);

			items = objectReader.readValue(embeddedRelJsonNode);
		}

		JsonNode pageJsonNode = responseJsonNode.get("page");

		if (pageJsonNode != null) {
			PageMetadata pageMetadata =
				ObjectMapperHolder._objectMapper.treeToValue(
					pageJsonNode, PageMetadata.class);

			return new Results<>(items, (int)pageMetadata.getTotalElements());
		}

		return new Results<>(items, items.size());
	}

	private AsahFaroBackendJSONObjectMapper() {
	}

	private static class ObjectMapperHolder {

		private static final ObjectMapper _objectMapper = new ObjectMapper() {
			{
				configure(
					DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			}
		};

	}

}