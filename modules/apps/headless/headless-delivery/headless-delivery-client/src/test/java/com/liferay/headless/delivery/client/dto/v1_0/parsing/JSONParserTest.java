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
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.headless.delivery.client.dto.v1_0.DocumentFolder;
import com.liferay.headless.delivery.client.serdes.v1_0.DocumentFolderSerDes;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

import java.text.SimpleDateFormat;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Rubén Pulido
 */
public class JSONParserTest {

	@Test
	public void testDeserializeDocumentFolder() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

		DocumentFolder documentFolder = _randomDocumentFolder();

		String json = _objectMapper.writeValueAsString(documentFolder);

		DocumentFolder outputDocumentFolder = DocumentFolderSerDes.toDTO(json);

		Assert.assertEquals(
			sdf.format(documentFolder.getDateCreated()),
			sdf.format(outputDocumentFolder.getDateCreated()));
		Assert.assertEquals(
			sdf.format(documentFolder.getDateModified()),
			sdf.format(outputDocumentFolder.getDateModified()));
		Assert.assertEquals(
			documentFolder.getDescription(),
			outputDocumentFolder.getDescription());
		Assert.assertEquals(
			documentFolder.getDescription(),
			outputDocumentFolder.getDescription());
		Assert.assertEquals(
			documentFolder.getDescription(),
			outputDocumentFolder.getDescription());
		Assert.assertEquals(
			documentFolder.getId(), outputDocumentFolder.getId());
		Assert.assertEquals(
			documentFolder.getName(), outputDocumentFolder.getName());
		Assert.assertEquals(
			documentFolder.getSiteId(), outputDocumentFolder.getSiteId());
	}

	private DocumentFolder _randomDocumentFolder() {
		return new DocumentFolder() {
			{
				dateCreated = RandomTestUtil.nextDate();
				dateModified = RandomTestUtil.nextDate();
				description = RandomTestUtil.randomString();
				id = RandomTestUtil.randomLong();
				name = RandomTestUtil.randomString();
				siteId = RandomTestUtil.randomLong();
			}
		};
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
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

}