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
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.io.ByteArrayInputStream;

import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class JSONLBatchEngineImportTaskItemReaderTest
	extends BaseBatchEngineImportTaskItemReaderTestCase {

	@Test
	public void testColumnMapping() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1"
						},
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"sample description\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				HashMapBuilder.put(
					"createDate1", "createDate"
				).put(
					"description1", "description"
				).put(
					"id1", "id"
				).put(
					"name1", "name"
				).build(),
				jsonlBatchEngineImportTaskItemReader.read(),
				HashMapBuilder.put(
					"en", "sample name"
				).put(
					"hr", "naziv"
				).build());
		}
	}

	@Test
	public void testColumnMappingWithUndefinedColumn() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1"
						},
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"sample description\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				HashMapBuilder.put(
					"createDate1", "createDate"
				).put(
					"description1", "description"
				).put(
					"id1", "id"
				).build(),
				jsonlBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testColumnMappingWithUndefinedTargetColumn() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1"
						},
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"sample description\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
							}
						})) {

			validate(
				createDateString, "sample description", 1L,
				HashMapBuilder.put(
					"createDate1", "createDate"
				).put(
					"description1", "description"
				).put(
					"id1", "id"
				).put(
					"name1", null
				).build(),
				jsonlBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testInvalidColumnMapping() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						new String[] {
							"createDate1", "description1", "id1", "name1"
						},
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"sample description\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
							}
						})) {

			try {
				validate(
					createDateString, "sample description", null,
					HashMapBuilder.put(
						"createDate1", "description"
					).put(
						"description1", "createDate"
					).put(
						"id1", "id"
					).put(
						"name1", "name"
					).build(),
					jsonlBatchEngineImportTaskItemReader.read(),
					HashMapBuilder.put(
						"en", "sample name"
					).put(
						"hr", "naziv"
					).build());

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
			}
		}
	}

	@Test
	public void testReadInvalidRow() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						_FIELD_NAMES,
						new Object[][] {
							{
								null, "\"sample description\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}",
								"\"unknown column\""
							}
						})) {

			try {
				validate(
					createDateString, "sample description", null,
					Collections.emptyMap(),
					jsonlBatchEngineImportTaskItemReader.read(),
					HashMapBuilder.put(
						"en", "sample name"
					).put(
						"hr", "naziv"
					).build());

				Assert.fail();
			}
			catch (NoSuchFieldException nsfe) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						_FIELD_NAMES,
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"sample description 1\"", 1,
								"{\"en\": \"sample name 1\", \"hr\": \"naziv " +
									"1\"}"
							},
							{
								"\"" + createDateString + "\"",
								"\"sample description 2\"", 2,
								"{\"en\": \"sample name 2\", \"hr\": \"naziv " +
									"2\"}"
							}
						})) {

			for (int i = 1; i < 3; i++) {
				long rowCount = i;

				validate(
					createDateString, "sample description " + rowCount,
					rowCount, Collections.emptyMap(),
					jsonlBatchEngineImportTaskItemReader.read(),
					HashMapBuilder.put(
						"en", "sample name " + rowCount
					).put(
						"hr", "naziv " + rowCount
					).build());
			}
		}
	}

	@Test
	public void testReadRowsWithCommaInsideQuotes() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						_FIELD_NAMES,
						new Object[][] {
							{
								"\"" + createDateString + "\"",
								"\"hey, here is comma inside\"", 1,
								"{\"en\": \"sample name\", \"hr\": \"naziv\"}"
							}
						})) {

			validate(
				createDateString, "hey, here is comma inside", 1L,
				Collections.emptyMap(),
				jsonlBatchEngineImportTaskItemReader.read(),
				HashMapBuilder.put(
					"en", "sample name"
				).put(
					"hr", "naziv"
				).build());
		}
	}

	@Test
	public void testReadRowsWithLessValues() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						_FIELD_NAMES, new Object[][] {{"null", "null", 1}})) {

			validate(
				null, null, 1L, Collections.emptyMap(),
				jsonlBatchEngineImportTaskItemReader.read(), null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (JSONLBatchEngineImportTaskItemReader
				jsonlBatchEngineImportTaskItemReader =
					_getJSONLBatchEngineImportTaskItemReader(
						_FIELD_NAMES,
						new Object[][] {
							{
								"\"" + createDateString + "\"", "null", 1,
								"{\"hr\": \"naziv 1\"}"
							},
							{
								"\"" + createDateString + "\"",
								"\"sample description 2\"", 2,
								"{\"en\": \"sample name 2\", \"hr\": \"naziv " +
									"2\"}"
							}
						})) {

			validate(
				createDateString, null, 1L, Collections.emptyMap(),
				jsonlBatchEngineImportTaskItemReader.read(),
				HashMapBuilder.put(
					"hr", "naziv 1"
				).build());

			validate(
				createDateString, "sample description 2", 2L,
				Collections.emptyMap(),
				jsonlBatchEngineImportTaskItemReader.read(),
				HashMapBuilder.put(
					"en", "sample name 2"
				).put(
					"hr", "naziv 2"
				).build());
		}
	}

	private byte[] _getContent(String[] cellNames, Object[][] rowValues) {
		StringBundler sb = new StringBundler();

		for (Object[] singleRowValues : rowValues) {
			sb.append("{");

			for (int j = 0; j < singleRowValues.length; j++) {
				if (singleRowValues[j] != null) {
					sb.append("\"");
					sb.append(cellNames[j]);
					sb.append("\": ");
					sb.append(singleRowValues[j]);
					sb.append(",");
				}
			}

			sb.setIndex(sb.index() - 1);
			sb.append("}\n");
		}

		sb.setIndex(sb.index() - 1);
		sb.append("}");

		String content = sb.toString();

		return content.getBytes();
	}

	private JSONLBatchEngineImportTaskItemReader
		_getJSONLBatchEngineImportTaskItemReader(
			String[] cellNames, Object[][] rowValues) {

		return new JSONLBatchEngineImportTaskItemReader(
			new ByteArrayInputStream(_getContent(cellNames, rowValues)));
	}

	private static final String[] _FIELD_NAMES = {
		"createDate", "description", "id", "name", "unknownColumn"
	};

}