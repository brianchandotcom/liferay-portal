/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		if (batchEngineTaskContentType == BatchEngineTaskContentType.CSV) {
			return new CSVBatchEngineTaskProgressImpl();
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSON) {
			return new JSONBatchEngineTaskProgressImpl();
		}

		if (batchEngineTaskContentType == BatchEngineTaskContentType.JSONL) {
			return new JSONLBatchEngineTaskProgressImpl();
		}

		if ((batchEngineTaskContentType == BatchEngineTaskContentType.XLS) ||
			(batchEngineTaskContentType == BatchEngineTaskContentType.XLSX)) {

			return new XLSBatchEngineTaskProgressImpl();
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