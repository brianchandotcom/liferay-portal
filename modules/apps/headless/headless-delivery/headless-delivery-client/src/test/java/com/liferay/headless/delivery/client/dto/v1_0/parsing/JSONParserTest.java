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

package com.liferay.headless.delivery.client.dto.v1_0.parsing;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentPageSerDes;
import com.liferay.headless.delivery.client.serdes.v1_0.StructuredContentSerDes;
import com.liferay.headless.delivery.dto.v1_0.StructuredContent;
import com.liferay.portal.vulcan.pagination.Page;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class JSONParserTest extends BaseJSONParserTestCase {

	@Test
	public void testDeserializeSerializeObjectStructuredContent()
		throws Exception {

		_testDeserializeSerializeStructuredContent(
			"object-structured-content.json");
	}

	@Test
	public void testDeserializeSerializePageStructuredContents()
		throws Exception {

		_testDeserializeSerializeStructuredContentPage(
			"page-structured-contents.json");
	}

	@Test
	public void testDeserializeSerializePageStructuredContentsEmptyValue()
		throws Exception {

		_testDeserializeSerializeStructuredContentPage(
			"page-structured-contents-empty-value.json");
	}

	@Test
	public void testDeserializeSerializePageStructuredContentsNullValue()
		throws Exception {

		_testDeserializeSerializeStructuredContentPage(
			"page-structured-contents-null-value.json");
	}

	private void _testDeserializeSerializeStructuredContent(String fileName)
		throws Exception {

		String expectedJSON = getFileContent(fileName);

		String expectedJSONFormatted = _toStructuredContentToJSON(expectedJSON);

		String actualJSON = StructuredContentSerDes.toJSON(
			StructuredContentSerDes.toDTO(expectedJSONFormatted));

		String actualJSONFormatted = _toStructuredContentToJSON(actualJSON);

		Assert.assertEquals(expectedJSONFormatted, actualJSONFormatted);
	}

	private void _testDeserializeSerializeStructuredContentPage(String fileName)
		throws Exception {

		String expectedJSON = getFileContent(fileName);

		String expectedJSONFormatted = _toStructuredContentPageToJSON(
			expectedJSON);

		String actualJSON = StructuredContentPageSerDes.toJSON(
			StructuredContentPageSerDes.toDTO(expectedJSONFormatted));

		String actualJSONFormatted = _toStructuredContentPageToJSON(actualJSON);

		Assert.assertEquals(expectedJSONFormatted, actualJSONFormatted);
	}

	private String _toStructuredContentPageToJSON(String json)
		throws Exception {

		Page<StructuredContent> page = _outputObjectMapper.readValue(
			json,
			new TypeReference<Page<StructuredContent>>() {
			});

		return _outputObjectMapper.writeValueAsString(page);
	}

	private String _toStructuredContentToJSON(String json) throws Exception {
		StructuredContent structuredContent = _inputObjectMapper.readValue(
			json, StructuredContent.class);

		return _outputObjectMapper.writeValueAsString(structuredContent);
	}

	private static final ObjectMapper _inputObjectMapper = new ObjectMapper() {
		{
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			setDateFormat(new ISO8601DateFormat());
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};
	private static final ObjectMapper _outputObjectMapper = new ObjectMapper() {
		{
			configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
			enable(SerializationFeature.INDENT_OUTPUT);
			setDateFormat(new ISO8601DateFormat());
			setFilterProvider(
				new SimpleFilterProvider() {
					{
						addFilter(
							"Liferay.Vulcan",
							SimpleBeanPropertyFilter.serializeAll());
					}
				});
			setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
			setSerializationInclusion(JsonInclude.Include.NON_NULL);
		}
	};

}