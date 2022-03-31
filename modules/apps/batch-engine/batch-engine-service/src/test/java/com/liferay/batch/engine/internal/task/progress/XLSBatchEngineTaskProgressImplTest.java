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