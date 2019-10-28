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

import com.liferay.batch.engine.BatchEngineTaskContentType;

import java.io.OutputStream;

/**
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemWriterFactory {

	public BatchEngineTaskItemWriterFactory(String csvFileColumnDelimiter) {
		_csvFileColumnDelimiter = csvFileColumnDelimiter;
	}

	public BatchEngineTaskItemWriter create(
			BatchEngineTaskContentType batchEngineTaskContentType,
			Class<?> itemClass, OutputStream outputStream)
		throws Exception {

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineTaskItemWriter(
				_csvFileColumnDelimiter, ItemClassIndexUtil.index(itemClass),
				outputStream);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineTaskItemWriter(outputStream);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineTaskItemWriter(outputStream);
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineTaskItemWriter(
				ItemClassIndexUtil.index(itemClass), outputStream);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				batchEngineTaskContentType);
	}

	private final String _csvFileColumnDelimiter;

}