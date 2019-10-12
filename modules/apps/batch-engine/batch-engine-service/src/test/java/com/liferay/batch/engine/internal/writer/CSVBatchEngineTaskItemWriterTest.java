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
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (CSVBatchEngineTaskItemWriter csvBatchEngineTaskItemWriter =
				new CSVBatchEngineTaskItemWriter(
					StringPool.COMMA, unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				csvBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		Assert.assertEquals(_getContent(StringPool.COMMA, getItems()), content);
	}

	private Object _formatValue(Object value) {
		if (value == null) {
			return "";
		}

		if (value instanceof Date) {
			return dateFormat.format(value);
		}

		return value;
	}

	private String _getContent(String delimiter, List<Item> items) {
		StringBundler sb = new StringBundler();

		sb.append(StringUtil.merge(CELL_NAMES, delimiter));
		sb.append(StringPool.NEW_LINE);

		for (Item item : items) {
			Map<String, String> name = item.getName();

			sb.append(
				StringUtil.merge(
					Arrays.asList(
						_formatValue(item.getCreateDate()),
						_formatValue(item.getDescription()), item.getId(),
						_formatValue(name.get("en")),
						_formatValue(name.get("hr"))),
					delimiter));

			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

}