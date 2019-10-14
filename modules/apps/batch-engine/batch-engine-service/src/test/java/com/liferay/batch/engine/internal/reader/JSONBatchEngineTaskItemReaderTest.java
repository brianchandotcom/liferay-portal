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

package com.liferay.batch.engine.internal.reader;

import com.liferay.petra.string.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class JSONBatchEngineTaskItemReaderTest
	extends BaseBatchEngineTaskItemReaderTestCase {

	@Test
	public void testReadInvalidRow() throws Exception {
		try (JSONBatchEngineTaskItemReader jsonBatchEngineTaskItemReader =
				_getJSONBatchEngineTaskItemReader(
					new Object[][] {
						{
							null, "\"sample description\"", 1,
							"{\"en\": \"sample name\", \"hr\": \"naziv\"}",
							"\"unknown column\""
						}
					})) {

			try {
				jsonBatchEngineTaskItemReader.read();

				Assert.fail();
			}
			catch (NoSuchFieldException nsfe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (JSONBatchEngineTaskItemReader jsonBatchEngineTaskItemReader =
				_getJSONBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"",
							"\"sample description 1\"", 1,
							"{\"en\": \"sample name 1\", \"hr\": \"naziv 1\"}"
						},
						{
							"\"" + createDateString + "\"",
							"\"sample description 2\"", 2,
							"{\"en\": \"sample name 2\", \"hr\": \"naziv 2\"}"
						}
					})) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount, (Item)jsonBatchEngineTaskItemReader.read(),
					new HashMap<String, String>() {
						{
							put("en", "sample name " + rowCount);
							put("hr", "naziv " + rowCount);
						}
					});
			}
		}
	}

	@Test
	public void testReadRowsWithCommaInsideQuotes() throws Exception {
		try (JSONBatchEngineTaskItemReader jsonBatchEngineTaskItemReader =
				_getJSONBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"",
							"\"hey, here is comma inside\"", 1,
							"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
						}
					})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				(Item)jsonBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name");
						put("hr", "naziv");
					}
				});
		}
	}

	@Test
	public void testReadRowsWithLessValues() throws Exception {
		try (JSONBatchEngineTaskItemReader jsonBatchEngineTaskItemReader =
				_getJSONBatchEngineTaskItemReader(
					new Object[][] {{"null", "null", 1}})) {

			validate(
				null, null, 1L, (Item)jsonBatchEngineTaskItemReader.read(),
				null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (JSONBatchEngineTaskItemReader jsonBatchEngineTaskItemReader =
				_getJSONBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"", "null", 1,
							"{\"hr\": \"naziv 1\"}"
						},
						{
							"\"" + createDateString + "\"",
							"\"sample description 2\"", 2,
							"{\"en\": \"sample name 2\", \"hr\": \"naziv 2\"}"
						}
					})) {

			validate(
				createDateString, null, 1L,
				(Item)jsonBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("hr", "naziv 1");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				(Item)jsonBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name 2");
						put("hr", "naziv 2");
					}
				});
		}
	}

	private byte[] _getContent(Object[][] rowValues) {
		StringBundler sb = new StringBundler();

		sb.append("[");

		for (Object[] singleRowValues : rowValues) {
			sb.append("{");

			for (int j = 0; j < singleRowValues.length; j++) {
				if (singleRowValues[j] != null) {
					sb.append("\"");
					sb.append(_CELL_NAMES[j]);
					sb.append("\": ");
					sb.append(singleRowValues[j]);
					sb.append(",");
				}
			}

			sb.setIndex(sb.index() - 1);
			sb.append("},");
		}

		sb.setIndex(sb.index() - 1);
		sb.append("}]");

		String content = sb.toString();

		return content.getBytes();
	}

	private JSONBatchEngineTaskItemReader _getJSONBatchEngineTaskItemReader(
			Object[][] rowValues)
		throws IOException {

		return new JSONBatchEngineTaskItemReader(
			new ByteArrayInputStream(_getContent(rowValues)), Item.class);
	}

	private static final String[] _CELL_NAMES = {
		"createDate", "description", "id", "name", "unknownColumn"
	};

}