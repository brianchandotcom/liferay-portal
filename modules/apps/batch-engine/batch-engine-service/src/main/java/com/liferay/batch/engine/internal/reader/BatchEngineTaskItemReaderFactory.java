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

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.batch.engine.internal.BatchEngineTaskMethodRegistry;
import com.liferay.batch.engine.model.BatchEngineTask;
import com.liferay.petra.string.StringPool;

import java.sql.Blob;

/**
 * @author Shuyang Zhou
 * @author Ivica Cardic
 */
public class BatchEngineTaskItemReaderFactory {

	public BatchEngineTaskItemReaderFactory(
		BatchEngineTaskMethodRegistry batchEngineTaskMethodRegistry) {

		_batchEngineTaskMethodRegistry = batchEngineTaskMethodRegistry;
	}

	public BatchEngineTaskItemReader create(BatchEngineTask batchEngineTask)
		throws Exception {

		BatchEngineTaskContentType batchEngineTaskContentType =
			BatchEngineTaskContentType.valueOf(
				batchEngineTask.getContentType());
		Blob content = batchEngineTask.getContent();

		Class<?> itemClass = _batchEngineTaskMethodRegistry.getItemClass(
			batchEngineTask.getClassName());

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineTaskItemReader(
				StringPool.COMMA, content.getBinaryStream(), itemClass);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineTaskItemReader(
				content.getBinaryStream(), itemClass);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineTaskItemReader(
				content.getBinaryStream(), itemClass);
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineTaskItemReader(
				content.getBinaryStream(), itemClass);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				batchEngineTaskContentType);
	}

	private final BatchEngineTaskMethodRegistry _batchEngineTaskMethodRegistry;

}