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
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineImportTaskItemReaderImplTest
	extends BaseBatchEngineImportTaskItemReaderImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testColumnMapping() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
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
				HashMapBuilder.put(
					"createDate1", "createDate"
				).put(
					"description1", "description"
				).put(
					"id1", "id"
				).put(
					"name1", "name"
				).build(),
				csvBatchEngineImportTaskItemReaderImpl.read(),
				HashMapBuilder.put(
					"en", "sample name"
				).put(
					"hr", "naziv"
				).build());
		}
	}

	@Test
	public void testColumnMappingWithUndefinedColumn() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
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
				HashMapBuilder.put(
					"createDate1", "createDate"
				).put(
					"description1", "description"
				).put(
					"id1", "id"
				).build(),
				csvBatchEngineImportTaskItemReaderImpl.read(), null);
		}
	}

	@Test
	public void testColumnMappingWithUndefinedTargetColumn() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
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
				csvBatchEngineImportTaskItemReaderImpl.read(), null);
		}
	}

	@Test
	public void testInvalidColumnMapping() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
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
					HashMapBuilder.put(
						"createDate1", "description"
					).put(
						"description1", "createDate"
					).put(
						"id1", "id"
					).put(
						"name1", "name"
					).build(),
					csvBatchEngineImportTaskItemReaderImpl.read(),
					HashMapBuilder.put(
						"en", "sample name"
					).put(
						"hr", "naziv"
					).build());

				Assert.fail();
			}
			catch (IllegalArgumentException illegalArgumentException) {
			}
		}
	}

	@Test
	public void testReadInvalidRow() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, null,
						new Object[][] {
							{
								"", "sample description", 1, "sample name",
								"naziv", "unknown column"
							}
						})) {

			try {
				csvBatchEngineImportTaskItemReaderImpl.read();

				Assert.fail();
			}
			catch (ArrayIndexOutOfBoundsException
						arrayIndexOutOfBoundsException) {
			}
		}
	}

	@Test
	public void testReadMultipleRows() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, null,
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
					csvBatchEngineImportTaskItemReaderImpl.read(),
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
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
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
				csvBatchEngineImportTaskItemReaderImpl.read(),
				HashMapBuilder.put(
					"en", "sample name"
				).put(
					"hr", "naziv"
				).build());
		}
	}

	@Test
	public void testReadRowsWithLessValues() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, null, new Object[][] {{"", "", 1}})) {

			validate(
				null, null, 1L, Collections.emptyMap(),
				csvBatchEngineImportTaskItemReaderImpl.read(), null);
		}
	}

	@Test
	public void testReadRowsWithNullValues() throws Exception {
		try (CSVBatchEngineImportTaskItemReaderImpl
				csvBatchEngineImportTaskItemReaderImpl =
					_getCSVBatchEngineImportTaskItemReader(
						FIELD_NAMES, null,
						new Object[][] {
							{createDateString, "", 1, "", "naziv 1"},
							{
								createDateString, "sample description 2", 2,
								"sample name 2", "naziv 2"
							}
						})) {

			validate(
				createDateString, null, 1L, Collections.emptyMap(),
				csvBatchEngineImportTaskItemReaderImpl.read(),
				new HashMap<String, String>() {
					{
						put("en", null);
						put("hr", "naziv 1");
					}
				});

			validate(
				createDateString, "sample description 2", 2L,
				Collections.emptyMap(),
				csvBatchEngineImportTaskItemReaderImpl.read(),
				HashMapBuilder.put(
					"en", "sample name 2"
				).put(
					"hr", "naziv 2"
				).build());
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

	private CSVBatchEngineImportTaskItemReaderImpl
			_getCSVBatchEngineImportTaskItemReader(
				String[] cellNames, String delimiter, Object[][] rowValues)
		throws IOException {

		return new CSVBatchEngineImportTaskItemReaderImpl(
			StringPool.COMMA, _getProperties(delimiter),
			new ByteArrayInputStream(
				_getContent(cellNames, delimiter, rowValues)));
	}

	private Map<String, Serializable> _getProperties(String delimiter) {
		if (Validator.isNull(delimiter)) {
			return Collections.emptyMap();
		}

		return HashMapBuilder.<String, Serializable>put(
			"delimiter", delimiter
		).build();
	}

}