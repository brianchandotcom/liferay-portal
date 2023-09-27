/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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