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

package com.liferay.batch.engine.internal.strategy;

import com.liferay.batch.engine.internal.util.BatchEngineImportTaskErrorUtil;
import com.liferay.batch.engine.strategy.BatchEngineImportStrategy;
import com.liferay.petra.function.UnsafeConsumer;

import java.util.Collection;

/**
 * @author Matija Petanjek
 */
public class OnErrorFailBatchEngineImportStrategy implements BatchEngineImportStrategy {

	public OnErrorFailBatchEngineImportStrategy(
		long batchEngineImportTaskId, long companyId, int processedItemsCount,
		long userId) {

		_batchEngineImportTaskId = batchEngineImportTaskId;
		_companyId = companyId;
		_processedItemsCount = processedItemsCount;
		_userId = userId;
	}

	@Override
	public <T> void apply(
			Collection<T> collection,
			UnsafeConsumer<T, Exception> unsafeConsumer)
		throws Exception {

		int index = 0;

		for (T item : collection) {
			try {
				index++;

				unsafeConsumer.accept(item);
			}
			catch (Exception exception) {
				BatchEngineImportTaskErrorUtil.addBatchEngineImportTaskError(
					_batchEngineImportTaskId, _companyId,
					exception.getMessage(), _processedItemsCount + index, item,
					_userId);

				throw exception;
			}
		}
	}

	private final long _batchEngineImportTaskId;
	private final long _companyId;
	private final int _processedItemsCount;
	private final long _userId;

}