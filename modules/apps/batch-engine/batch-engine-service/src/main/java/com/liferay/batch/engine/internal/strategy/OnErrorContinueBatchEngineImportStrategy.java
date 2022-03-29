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

import com.liferay.batch.engine.internal.util.ItemIndexThreadLocal;
import com.liferay.petra.function.UnsafeConsumer;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.util.Collection;

/**
 * @author Matija Petanjek
 */
public class OnErrorContinueBatchEngineImportStrategy
	extends BaseBatchEngineImportStrategy {

	public OnErrorContinueBatchEngineImportStrategy(
		long batchEngineImportTaskId, long companyId, long userId) {

		_batchEngineImportTaskId = batchEngineImportTaskId;
		_companyId = companyId;
		_userId = userId;
	}

	@Override
	public <T> void apply(
			Collection<T> collection,
			UnsafeConsumer<T, Exception> unsafeConsumer)
		throws Exception {

		for (T item : collection) {
			try {
				unsafeConsumer.accept(item);
			}
			catch (Exception exception) {
				if (_log.isDebugEnabled()) {
					_log.debug(exception);
				}

				addBatchEngineImportTaskError(
					_companyId, _userId, _batchEngineImportTaskId,
					item.toString(), ItemIndexThreadLocal.get(item),
					exception.getMessage());
			}
		}
	}

	private static final Log _log = LogFactoryUtil.getLog(
		OnErrorContinueBatchEngineImportStrategy.class);

	private final long _batchEngineImportTaskId;
	private final long _companyId;
	private final long _userId;

}