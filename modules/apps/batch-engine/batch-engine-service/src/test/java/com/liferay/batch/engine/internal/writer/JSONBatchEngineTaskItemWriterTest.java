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

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Ivica Cardic
 */
public class JSONBatchEngineTaskItemWriterTest
	extends BaseBatchEngineTaskItemWriterTestCase {

	@Test
	public void testWriteRows() throws Exception {
		UnsyncByteArrayOutputStream unsyncByteArrayOutputStream =
			new UnsyncByteArrayOutputStream();

		try (JSONBatchEngineTaskItemWriter jsonBatchEngineTaskItemWriter =
				new JSONBatchEngineTaskItemWriter(
					unsyncByteArrayOutputStream)) {

			for (Item[] items : getItemGroups()) {
				jsonBatchEngineTaskItemWriter.write(Arrays.asList(items));
			}
		}

		String content = unsyncByteArrayOutputStream.toString();

		Assert.assertEquals(
			objectMapper.writeValueAsString(getItems()), content);
	}

}