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

package com.liferay.batch.engine.internal.writer;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.lang.reflect.Field;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineTaskItemWriterTest
	extends BaseBatchEngineTaskItemWriterTestCase {

	@Test
	public void testWriteRowsWithDefinedFieldNames1() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "description", "id"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames2() throws Exception {
		_testWriteRows(
			Arrays.asList(
				"createDate", "description", "id", "name_en", "name_hr"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames3() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "id", "name_en"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames4() throws Exception {
		_testWriteRows(
			Arrays.asList(
				"id", "name_hr", "name_en", "description", "createDate"));
	}

	@Test
	public void testWriteRowsWithEmptyFieldNames() throws Exception {
		try {
			_testWriteRows(Collections.emptyList());

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
		}
	}

	private String _formatValue(Object value) {
		if (value == null) {
			return StringPool.BLANK;
		}

		if (value instanceof Date) {
			return dateFormat.format(value);
		}

		return value.toString();
	}

	private String _getExpectedContent(
			List<String> fieldNames, List<Item> items)
		throws IllegalAccessException {

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.merge(fieldNames, StringPool.COMMA));
		sb.append(StringPool.NEW_LINE);

		for (Item item : items) {
			for (String fieldName : fieldNames) {
				if (fieldName.contains(StringPool.UNDERLINE)) {
					List<String> names = StringUtil.split(
						fieldName, CharPool.UNDERLINE);

					Field field = fieldMap.get(names.get(0));

					Map<?, ?> valueMap = (Map<?, ?>)field.get(item);

					sb.append(_formatValue(valueMap.get(names.get(1))));
				}
				else {
					Field field = fieldMap.get(fieldName);

					sb.append(_formatValue(field.get(item)));
				}

				sb.append(StringPool.COMMA);
			}

			sb.setIndex(sb.index() - 1);

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private void _testWriteRows(List<String> fieldNames) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (CSVBatchEngineTaskItemWriter csvBatchEngineTaskItemWriter =
				new CSVBatchEngineTaskItemWriter(
					StringPool.COMMA, fieldMap, fieldNames,
					unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				csvBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		Assert.assertEquals(
			_getExpectedContent(fieldNames, getItems()), content);
	}

}