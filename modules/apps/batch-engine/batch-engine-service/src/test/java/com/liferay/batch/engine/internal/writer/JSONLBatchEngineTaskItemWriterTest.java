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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.skyscreamer.jsonassert.JSONAssert;

/**
 * @author Ivica Cardic
 */
public class JSONLBatchEngineTaskItemWriterTest
	extends BaseBatchEngineTaskItemWriterTestCase {

	@Test
	public void testWriteRowsWithDefinedFieldNames1() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "description", "id"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames2() throws Exception {
		_testWriteRows(
			Arrays.asList("createDate", "description", "id", "name"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames3() throws Exception {
		_testWriteRows(Arrays.asList("createDate", "id", "name"));
	}

	@Test
	public void testWriteRowsWithDefinedFieldNames4() throws Exception {
		_testWriteRows(
			Arrays.asList("id", "name", "description", "createDate"));
	}

	@Test
	public void testWriteRowsWithEmptyFieldNames() throws Exception {
		_testWriteRows(Collections.emptyList());
	}

	private String _getExpectedContent(
		List<String> fieldNames, List<Item> items) {

		StringBundler sb = new StringBundler();

		if (fieldNames.isEmpty()) {
			fieldNames = jsonFieldNames;
		}

		for (Item item : items) {
			sb.append(getItemJSONContent(fieldNames, item));
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

	private void _testWriteRows(List<String> fieldNames) throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (JSONLBatchEngineTaskItemWriter jsonlBatchEngineTaskItemWriter =
				new JSONLBatchEngineTaskItemWriter(
					fieldMap.keySet(), fieldNames,
					unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				jsonlBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		JSONAssert.assertEquals(
			_getExpectedContent(fieldNames, getItems()), content, true);
	}

}