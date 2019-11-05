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

import java.util.Collections;
import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineImportTaskItemReaderTest
	extends BaseBatchEngineImportTaskItemReaderTestCase {

	@Test
	public void testColumnMapping() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1_en",
							"name1_hr"
						},
						StringPool.SEMICOLON,
						new Object[][] {
							{
								createDateString, "sample description", 1,
								"sample name", "naziv"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
						put("name1", "name");
					}
				},
				csvBatchEngineImportTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name");
						put("hr", "naziv");
					}
				});
		}
	}

	@Test
	public void testColumnMappingWithUndefinedColumn() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1_en",
							"name1_hr"
						},
						StringPool.SEMICOLON,
						new Object[][] {
							{
								createDateString, "sample description", 1,
								"sample name", "naziv"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
					}
				},
				csvBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testColumnMappingWithUndefinedTargetColumn() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1_en",
							"name1_hr"
						},
						StringPool.SEMICOLON,
						new Object[][] {
							{
								createDateString, "sample description", 1,
								"sample name", "naziv"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				new HashMap<String, String>() {
					{
						put("createDate1", "createDate");
						put("description1", "description");
						put("id1", "id");
						put("name1", null);
					}
				},
				csvBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testInvalidColumnMapping() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1_en",
							"name1_hr"
						},
						StringPool.SEMICOLON,
						new Object[][] {
							{
								createDateString, "sample description", 1,
								"sample name", "naziv"
							}
						})) {

			try {
				validate(
					createDateString, "sample description", null,
					new HashMap<String, String>() {
						{
							put("createDate1", "description");
							put("description1", "createDate");
							put("id1", "id");
							put("name1", "name");
						}
					},
					csvBatchEngineImportTaskItemReader.read(),
					new HashMap<String, String>() {
						{
							put("en", "sample name");
							put("hr", "naziv");
						}
					});

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
			}
		}
	}

	@Test
	public void testReadInvalidRow() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, StringPool.COMMA,
						new Object[][] {
							{
								"", "sample description", 1, "sample name",
								"naziv", "unknown column"
							}
						})) {

			try {
				csvBatchEngineImportTaskItemReader.read();

				Assert.fail();
			}
			catch (ArrayIndexOutOfBoundsException aioobe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, StringPool.COMMA,
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
					rowCount, Collections.emptyMap(),
					csvBatchEngineImportTaskItemReader.read(),
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
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, StringPool.SEMICOLON,
						new Object[][] {
							{
								createDateString, "hey, here is comma inside",
								1, "sample name", "naziv"
							}
						})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				Collections.emptyMap(),
				csvBatchEngineImportTaskItemReader.read(),
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
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, StringPool.COMMA,
						new Object[][] {{"", "", 1}})) {

			validate(
				null, null, 1L, Collections.emptyMap(),
				csvBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (CSVBatchEngineImportTaskItemReader
				csvBatchEngineImportTaskItemReader =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, StringPool.COMMA,
						new Object[][] {
							{createDateString, "", 1, "", "naziv 1"},
							{
								createDateString, "sample description 2", 2,
								"sample name 2", "naziv 2"
							}
						})) {

			validate(
				createDateString, null, 1L, Collections.emptyMap(),
				csvBatchEngineImportTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", null);
						put("hr", "naziv 1");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				Collections.emptyMap(),
				csvBatchEngineImportTaskItemReader.read(),
				new HashMap<String, String>() {
					{
						put("en", "sample name 2");
						put("hr", "naziv 2");
					}
				});
		}
	}

	private byte[] _getContent(
		String[] cellNames, String delimiter, Object[][] rowValues) {

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.merge(cellNames, delimiter));
		sb.append("\n");

		for (Object[] cellValues : rowValues) {
			sb.append(StringUtil.merge(cellValues, delimiter));
			sb.append("\n");
		}

		String content = sb.toString();

		return content.getBytes();
	}

	private CSVBatchEngineImportTaskItemReader
			_getCSVBatchEngineImportTaskItemReader(
				String[] cellNames, String delimiter, Object[][] rowValues)
		throws IOException {

		return new CSVBatchEngineImportTaskItemReader(
			delimiter,
			new ByteArrayInputStream(
				_getContent(cellNames, delimiter, rowValues)));
	}

}