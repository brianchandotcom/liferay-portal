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
import com.liferay.petra.string.StringUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineTaskItemReaderTest
	extends BaseBatchEngineTaskItemReaderTestCase {

	@Test
	public void testReadInvalidRow() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					StringPool.COMMA,
					new Object[][] {
						{
							"", "sample description", 1, "sample name", "naziv",
							"unknown column"
						}
					})) {

			try {
				csvBatchEngineTaskItemReader.read();

				Assert.fail();
			}
			catch (ArrayIndexOutOfBoundsException aioobe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					StringPool.COMMA,
					new Object[][] {
						{
							createDateString, "sample description 1", 1,
							"sample name 1", "naziv 1"
						},
						{
							createDateString, "sample description 2", 2,
							"sample name 2", "naziv 2"
						}
					})) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount, (Item)csvBatchEngineTaskItemReader.read(),
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
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					StringPool.SEMICOLON,
					new Object[][] {
						{
							createDateString, "hey, here is comma inside", 1,
							"sample name", "naziv"
						}
					})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				(Item)csvBatchEngineTaskItemReader.read(),
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
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					StringPool.COMMA, new Object[][] {{"", "", 1}})) {

			validate(
				null, null, 1L, (Item)csvBatchEngineTaskItemReader.read(),
				null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (CSVBatchEngineTaskItemReader csvBatchEngineTaskItemReader =
				_getCSVBatchEngineTaskItemReader(
					StringPool.COMMA,
					new Object[][] {
						{createDateString, "", 1, "", "naziv 1"},
						{
							createDateString, "sample description 2", 2,
							"sample name 2", "naziv 2"
						}
					})) {

			validate(
				createDateString, null, 1L,
				(Item)csvBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", null);
						put("hr", "naziv 1");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				(Item)csvBatchEngineTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name 2");
						put("hr", "naziv 2");
					}
				});
		}
	}

	private byte[] _getContent(String delimiter, Object[][] rowValues) {
		StringBundler sb = new StringBundler();

		sb.append(StringUtil.merge(CELL_NAMES, delimiter));
		sb.append("\n");

		for (Object[] cellValues : rowValues) {
			sb.append(StringUtil.merge(cellValues, delimiter));
			sb.append("\n");
		}

		String content = sb.toString();

		return content.getBytes();
	}

	private CSVBatchEngineTaskItemReader _getCSVBatchEngineTaskItemReader(
			String delimiter, Object[][] rowValues)
		throws IOException {

		return new CSVBatchEngineTaskItemReader(
			delimiter,
			new ByteArrayInputStream(_getContent(delimiter, rowValues)),
			Item.class);
	}

}