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
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

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
	public void testWriteRows() throws Exception {
		_testWriteRows(Collections.emptyList());
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "description", "id"));
		_testWriteRows(
			Arrays.asList(
				"createDate", "description", "id", "name_en", "name_hr"));
		_testWriteRows(Arrays.asList("createDate", "id", "name_en"));
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
		List<String> fieldNames, List<Item> items) {

		if (fieldNames.isEmpty()) {
			return StringPool.NEW_LINE;
		}

		StringBundler sb = new StringBundler();

		sb.append(StringUtil.merge(fieldNames, StringPool.COMMA));
		sb.append(StringPool.NEW_LINE);

		for (Item item : items) {
			if (fieldNames.contains("createDate")) {
				sb.append(_formatValue(item.getCreateDate()));
				sb.append(StringPool.COMMA);
			}

			if (fieldNames.contains("description")) {
				sb.append(_formatValue(item.getDescription()));
				sb.append(StringPool.COMMA);
			}

			if (fieldNames.contains("id")) {
				sb.append(_formatValue(item.getId()));
				sb.append(StringPool.COMMA);
			}

			Map<String, String> name = item.getName();

			if (fieldNames.contains("name_en")) {
				sb.append(_formatValue(name.get("en")));
				sb.append(StringPool.COMMA);
			}

			if (fieldNames.contains("name_hr")) {
				sb.append(_formatValue(name.get("hr")));
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