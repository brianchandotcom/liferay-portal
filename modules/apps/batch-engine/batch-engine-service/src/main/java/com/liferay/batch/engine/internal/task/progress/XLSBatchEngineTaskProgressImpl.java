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
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.IOException;
import java.io.InputStream;

import java.util.Iterator;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Matija Petanjek
 */
public class XLSBatchEngineTaskProgressImpl implements BatchEngineTaskProgress {

	@Override
	public int getTotalItemsCount(InputStream inputStream) {
		int totalItemsCount = 0;

		try {
			Workbook workbook = new XSSFWorkbook(
				ZipInputStreamUtil.asZipInputStream(inputStream));

			Sheet sheet = workbook.getSheetAt(0);

			Iterator<Row> iterator = sheet.rowIterator();

			if (iterator.hasNext()) {
				iterator.next();
			}

			while (iterator.hasNext()) {
				iterator.next();
				totalItemsCount++;
			}
		}
		catch (IOException ioException) {
			_log.error("Unable to get total items count", ioException);

			totalItemsCount = 0;
		}
		finally {
			try {
				inputStream.close();
			}
			catch (IOException ioException) {
				_log.error(ioException);
			}
		}

		return totalItemsCount;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		XLSBatchEngineTaskProgressImpl.class);

}