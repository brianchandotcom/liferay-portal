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

import com.fasterxml.jackson.core.JsonProcessingException;

import com.liferay.petra.io.unsync.UnsyncByteArrayOutputStream;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class JSONLBatchEngineTaskItemWriterTest
	extends BaseBatchEngineTaskItemWriterTestCase {

	@Test
	public void testWriteRows() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (JSONLBatchEngineTaskItemWriter jsonlBatchEngineTaskItemWriter =
				new JSONLBatchEngineTaskItemWriter(
					unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				jsonlBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		Assert.assertEquals(_getContent(getItems()), content);
	}

	private String _getContent(List<Item> items)
		throws JsonProcessingException {

		StringBundler sb = new StringBundler();

		for (Item item : items) {
			sb.append(objectMapper.writeValueAsString(item));
			sb.append(StringPool.NEW_LINE);
		}

		return sb.toString();
	}

}