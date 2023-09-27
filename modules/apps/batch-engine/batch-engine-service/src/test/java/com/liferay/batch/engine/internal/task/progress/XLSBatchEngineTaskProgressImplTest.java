/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.task.progress;

import com.liferay.portal.test.rule.LiferayUnitTestRule;

import java.io.ByteArrayOutputStream;

import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Matija Petanjek
 */
public class XLSBatchEngineTaskProgressImplTest
	extends BaseBatchEngineTaskProgressImplTestCase {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Test
	public void testGetTotalItemsCount() throws Exception {
		_testGetTotalItemsCount(0);
		_testGetTotalItemsCount(PRODUCTS_COUNT);
	}

	private void _createXLSRow(Row row, Object... values) {
		for (int i = 0; i < values.length; i++) {
			Cell cell = row.createCell(i);

			cell.setCellValue((String)values[i]);
		}
	}

	private XSSFWorkbook _createXSSFWorkbook(int expectedTotalItemsCount) {
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook();

		Sheet sheet = xssfWorkbook.createSheet();

		Row row = sheet.createRow(0);

		Set<String> keys = productJSONObject.keySet();

		_createXLSRow(row, keys.toArray());

		for (int i = 0; i < expectedTotalItemsCount; i++) {
			_createXLSRow(sheet.createRow(i + 1), keys.toArray());
		}

		return xssfWorkbook;
	}

	private void _testGetTotalItemsCount(int expectedTotalItemsCount)
		throws Exception {

		try (ByteArrayOutputStream byteArrayOutputStream =
				new ByteArrayOutputStream()) {

			XSSFWorkbook xssfWorkbook = _createXSSFWorkbook(
				expectedTotalItemsCount);

			xssfWorkbook.write(byteArrayOutputStream);

			xssfWorkbook.close();

			Assert.assertEquals(
				expectedTotalItemsCount,
				_batchEngineTaskProgress.getTotalItemsCount(
					compress(byteArrayOutputStream.toByteArray(), "XLS")));
		}
	}

	private static final BatchEngineTaskProgress _batchEngineTaskProgress =
		new XLSBatchEngineTaskProgressImpl();

}