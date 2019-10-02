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

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;

import com.liferay.petra.string.StringBundler;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.Date;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class JSONLBatchEngineTaskItemReaderTest
	extends BaseBatchEngineTaskItemReaderTestCase {

	@Test
	public void testReadInvalidRow() throws Exception {
		try (JSONLBatchEngineTaskItemReader jsonlBatchEngineTaskItemReader =
				_getJSONLBatchEngineTaskItemReader(
					new Object[][] {
						{
							null, "\"sample description\"", 1,
							"{\"en\":\"sample name\",\"hr\":\"naziv\"}",
							"\"unknown column\""
						}
					})) {

			try {
				jsonlBatchEngineTaskItemReader.read();

				Assert.fail();
			}
			catch (UnrecognizedPropertyException upe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		Date createDate = new Date();

		try (JSONLBatchEngineTaskItemReader jsonlBatchEngineTaskItemReader =
				_getJSONLBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"",
							"\"sample description 1\"", 1,
							"{\"en\":\"sample name 1\",\"hr\":\"naziv 1\"}"
						},
						{
							"\"" + createDateString + "\"",
							"\"sample description 2\"", 2,
							"{\"en\":\"sample name 2\",\"hr\":\"naziv 2\"}"
						}
					})) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount, (Item)jsonlBatchEngineTaskItemReader.read(),
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
		try (JSONLBatchEngineTaskItemReader jsonlBatchEngineTaskItemReader =
				_getJSONLBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"",
							"\"hey, here is comma inside\"", 1,
							"{\"en\":\"sample name\",\"hr\":\"naziv\"}"
						}
					})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				(Item)jsonlBatchEngineTaskItemReader.read(),
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
		try (JSONLBatchEngineTaskItemReader jsonlBatchEngineTaskItemReader =
				_getJSONLBatchEngineTaskItemReader(
					new Object[][] {{"null", "null", 1}})) {

			validate(
				null, null, 1L, (Item)jsonlBatchEngineTaskItemReader.read(),
				null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (JSONLBatchEngineTaskItemReader jsonlBatchEngineTaskItemReader =
				_getJSONLBatchEngineTaskItemReader(
					new Object[][] {
						{
							"\"" + createDateString + "\"", "null", 1,
							"{\"hr\":\"naziv 1\"}"
						},
						{
							"\"" + createDateString + "\"",
							"\"sample description 2\"", 2,
							"{\"en\":\"sample name 2\",\"hr\":\"naziv 2\"}"
						}
					})) {

			validate(
				createDateString, null, 1L,
				(Item)jsonlBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("hr", "naziv 1");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				(Item)jsonlBatchEngineTaskItemReader.read(),
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

		for (int i = 0; i < rowValues.length; i++) {
			sb.append("{");

			for (int j = 0; j < rowValues[i].length; j++) {
				if (rowValues[i][j] != null) {
					sb.append("\"");
					sb.append(_CELL_NAMES[j]);
					sb.append("\":");
					sb.append(rowValues[i][j]);

					if (j < (rowValues[i].length - 1)) {
						sb.append(",");
					}
				}
			}

			sb.append("}");

			if (i < (rowValues.length - 1)) {
				sb.append("\n");
			}
		}

		String content = sb.toString();

		return content.getBytes();
	}

	private JSONLBatchEngineTaskItemReader _getJSONLBatchEngineTaskItemReader(
			Object[][] rowValues)
		throws IOException {

		return new JSONLBatchEngineTaskItemReader(
			new ByteArrayInputStream(_getContent(rowValues)), Item.class);
	}

	private static final String[] _CELL_NAMES = {
		"createDate", "description", "id", "name", "unknownColumn"
	};

}