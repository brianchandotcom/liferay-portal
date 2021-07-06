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

package com.liferay.batch.engine.internal.task.progress;

import com.liferay.batch.engine.BatchEngineTaskContentType;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author Matija Petanjek
 */
public class BatchEngineTaskProgressFactory {

	public BatchEngineTaskProgress create(
		BatchEngineTaskContentType batchEngineTaskContentType) {

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineTaskProgressImpl();
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineTaskProgressImpl();
		}

		return new DefaultBatchEngineTaskProgressImpl();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BatchEngineTaskProgressFactory.class);

	private class DefaultBatchEngineTaskProgressImpl
		implements BatchEngineTaskProgress {

		@Override
		public int getTotalItemsCount(InputStream inputStream) {
			try {
				return 0;
			}
			finally {
				try {
					inputStream.close();
				}
				catch (IOException ioException) {
					_log.error("Unable to close input stream", ioException);
				}
			}
		}

	}

}