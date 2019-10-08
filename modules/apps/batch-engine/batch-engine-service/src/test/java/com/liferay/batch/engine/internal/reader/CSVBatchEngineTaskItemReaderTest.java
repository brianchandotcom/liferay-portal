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
import com.liferay.petra.string.StringPool;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.HashMap;

import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineTaskItemReaderTest
	extends BaseBatchEngineTaskItemReaderTestCase {

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void testReadInvalidRow() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					new Object[][] {
						{
							"", "sample description", 1, "sample name", "naziv",
							"unknown column"
						}
					},
					StringPool.COMMA)) {

			csvBatchEngineTaskItemReader.read();
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					new Object[][] {
						{
							createDateString, "sample description 1", 1,
							"sample name 1", "naziv 1"
						},
						{
							createDateString, "sample description 2", 2,
							"sample name 2", "naziv 2"
						}
					},
					StringPool.COMMA)) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount,
					new HashMap<String, String>() {
						{
							put("en", "sample name " + rowCount);
							put("hr", "naziv " + rowCount);
						}
					},
					(Item)csvBatchEngineTaskItemReader.read());
			}
		}
	}

	@Test
	public void testReadRowsWithCommaInsideQuotes() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					new Object[][] {
						{
							createDateString, "hey, here is comma inside", 1,
							"sample name", "naziv"
						}
					},
					StringPool.SEMICOLON)) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				new HashMap<String, String>() {
					{
						put("en", "sample name");
						put("hr", "naziv");
					}
				},
				(Item)csvBatchEngineTaskItemReader.read());
		}
	}

	@Test
	public void testReadRowsWithLessValues() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					new Object[][] {{"", "", 1}}, StringPool.COMMA)) {

			validate(
				null, null, 1L, null,
				(Item)csvBatchEngineTaskItemReader.read());
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					new Object[][] {
						{createDateString, "", 1, "", "naziv 1"},
						{
							createDateString, "sample description 2", 2,
							"sample name 2", "naziv 2"
						}
					},
					StringPool.COMMA)) {

			validate(
				createDateString, null, 1L,
				new HashMap<String, String>() {
					{
						put("en", null);
						put("hr", "naziv 1");
					}
				},
				(Item)csvBatchEngineTaskItemReader.read());

			validate(
				createDateString, "sample description 2", 2L,
				new HashMap<String, String>() {
					{
						put("en", "sample name 2");
						put("hr", "naziv 2");
					}
				},
				(Item)csvBatchEngineTaskItemReader.read());
		}
	}

	private byte[] _getContent(Object[][] rowValues, String delimiter) {
		StringBundler sb = new StringBundler();

		for (String cellName : CELL_NAMES) {
			sb.append(cellName);
			sb.append(delimiter);
		}

		sb.setIndex(sb.index() - 1);
		sb.append("\n");

		for (Object[] cellValues : rowValues) {
			for (Object cellValue : cellValues) {
				sb.append(cellValue);
				sb.append(delimiter);
			}

			sb.setIndex(sb.index() - 1);
			sb.append("\n");
		}

		String content = sb.toString();

		return content.getBytes();
	}

	private CSVBatchEngineTaskItemReader _getCSVBatchEngineTaskItemReader(
			Object[][] rowValues, String delimiter)
		throws IOException {

		return new CSVBatchEngineTaskItemReader(
			delimiter,
			new ByteArrayInputStream(_getContent(rowValues, delimiter)),
			Item.class);
	}

}