/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.task.progress;

import com.liferay.batch.engine.internal.util.ZipInputStreamUtil;
import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Matija Petanjek
 */
public class CSVBatchEngineTaskProgressImpl implements BatchEngineTaskProgress {

	@Override
	public int getTotalItemsCount(InputStream inputStream) {
		int totalItemsCount = 0;

		UnsyncBufferedReader unsyncBufferedReader = null;

		try {
			unsyncBufferedReader = new UnsyncBufferedReader(
				new InputStreamReader(
					ZipInputStreamUtil.asZipInputStream(inputStream)));

			if (unsyncBufferedReader.readLine() == null) {
				return totalItemsCount;
			}

			while (unsyncBufferedReader.readLine() != null) {
				totalItemsCount++;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to get total items count", ioException);

			totalItemsCount = 0;
		}
		finally {
			try {
				if (unsyncBufferedReader != null) {
					unsyncBufferedReader.close();
				}
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return totalItemsCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		CSVBatchEngineTaskProgressImpl.class);

}