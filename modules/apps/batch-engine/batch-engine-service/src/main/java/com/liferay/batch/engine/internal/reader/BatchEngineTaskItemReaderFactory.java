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
import com.liferay.batch.engine.BatchEngineTaskItemClassRegistry;
import com.liferay.batch.engine.model.BatchEngineTask;

import java.sql.Blob;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Shuyang Zhou
 */
@Component(service = BatchEngineTaskItemReaderFactory.class)
public class BatchEngineTaskItemReaderFactory {

	public <T> BatchEngineTaskItemReader<T> create(
			BatchEngineTask batchEngineTask)
		throws Exception {

		BatchEngineTaskContentType batchEngineTaskContentType =
			BatchEngineTaskContentType.valueOf(
				batchEngineTask.getContentType());
		Blob content = batchEngineTask.getContent();

		@SuppressWarnings("unchecked")
		Class<T> itemClass = (Class<T>)_batchEngineTaskItemClassRegistry.get(
			batchEngineTask.getClassName());

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineTaskItemReader<>(
				content.getBinaryStream(), itemClass);
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineTaskItemReader<>(
				content.getBinaryStream(), itemClass);
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineTaskItemReader<>(
				content.getBinaryStream(), itemClass);
		}

		throw new IllegalArgumentException(
			"Unknown batch engine task content type " +
				batchEngineTaskContentType);
	}

	@Reference
	private BatchEngineTaskItemClassRegistry _batchEngineTaskItemClassRegistry;

}