/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.liferay.batch.engine.csv.ColumnDescriptorProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.ObjectValuePair;

import java.io.IOException;
import java.io.OutputStream;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * @author Ivica Cardic
 */
public class XLSBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public XLSBatchEngineExportTaskItemWriterImpl(
			ColumnDescriptorProvider columnDescriptorProvider, long companyId,
			Map<String, ObjectValuePair<Field, Method>>
				fieldNameObjectValuePairs,
			List<String> fieldNames, OutputStream outputStream,
			String taskItemDelegateName)
		throws PortalException {

		if (fieldNames.isEmpty()) {
			throw new IllegalArgumentException("Field names are not set");
		}

		_outputStream = outputStream;

		_columnValuesExtractor = new ColumnValuesExtractor(
			columnDescriptorProvider, companyId, fieldNameObjectValuePairs,
			fieldNames, taskItemDelegateName);

		_sheet = _workbook.createSheet();

		_write(_columnValuesExtractor.getHeaders());
	}

	@Override
	public void close() throws IOException {
		_workbook.write(_outputStream);

		_workbook.close();

		_outputStream.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		for (Object item : items) {
			for (Object[] values : _columnValuesExtractor.extractValues(item)) {
				_write(values);
			}
		}
	}

	private void _write(Object[] values) {
		Row row = _sheet.createRow(_rowNum++);

		int column = 0;

		for (Object value : values) {
			Cell cell = row.createCell(column++);

			if (value instanceof Boolean) {
				cell.setCellValue((Boolean)value);
			}
			else if (value instanceof Number) {
				Number number = (Number)value;

				cell.setCellValue(number.doubleValue());
			}
			else {
				cell.setCellValue((String)value);
			}
		}
	}

	private final ColumnValuesExtractor _columnValuesExtractor;
	private final OutputStream _outputStream;
	private int _rowNum;
	private final Sheet _sheet;
	private final Workbook _workbook = new XSSFWorkbook();

}