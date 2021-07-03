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

package com.liferay.batch.engine.internal.item.counter;

import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Matija Petanjek
 */
public class JSONLBatchEngineImportTaskTotalItemsCounter
	implements BatchEngineImportTaskTotalItemsCounter {

	@Override
	public int getTotalItemsCount(InputStream inputStream) throws IOException {
		int totalItemsCount = 0;

		try {
			UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(new InputStreamReader(inputStream));

			while (unsyncBufferedReader.readLine() != null) {
				totalItemsCount++;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to calculate total items", ioException);
		}
		finally {
			inputStream.close();
		}

		return totalItemsCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		JSONLBatchEngineImportTaskTotalItemsCounter.class);

}