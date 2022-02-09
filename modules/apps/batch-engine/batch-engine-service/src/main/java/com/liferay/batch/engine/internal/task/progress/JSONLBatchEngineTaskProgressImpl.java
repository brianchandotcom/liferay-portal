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
public class JSONLBatchEngineTaskProgressImpl
	implements BatchEngineTaskProgress {

	@Override
	public int getTotalItemsCount(InputStream inputStream) {
		int totalItemsCount = 0;

		UnsyncBufferedReader unsyncBufferedReader = null;

		try {
			unsyncBufferedReader = new UnsyncBufferedReader(
				new InputStreamReader(
					ZipInputStreamUtil.asZipInputStream(inputStream)));

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
		JSONLBatchEngineTaskProgressImpl.class);

}