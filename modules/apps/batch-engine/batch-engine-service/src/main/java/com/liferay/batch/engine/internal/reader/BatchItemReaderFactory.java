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

import com.liferay.batch.engine.BatchContentType;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Shuyang Zhou
 */
public class BatchItemReaderFactory {

	public static <T> BatchItemReader<T> create(
			Class<T> domainClass, BatchContentType batchContentType,
			InputStream inputStream)
		throws IOException {

		if (batchContentType == BatchContentType.CSV) {
			return new FlatFileBatchItemReader<>(domainClass, inputStream);
		}

		if (batchContentType == BatchContentType.JSON) {
			return new JSONBatchItemReader<>(domainClass, inputStream);
		}

		if ((batchContentType == BatchContentType.XLS) ||
			(batchContentType == BatchContentType.XLSX)) {

			return new XLSBatchItemReader<>(domainClass, inputStream);
		}

		throw new IllegalArgumentException(
			"Unknown item type : " + batchContentType);
	}

}