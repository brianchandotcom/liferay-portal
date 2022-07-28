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

package com.liferay.batch.engine.internal.writer;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public CSVBatchEngineExportTaskItemWriterImpl(
			String delimiter, Map<String, Field> fieldMap,
			List<String> fieldNames, OutputStream outputStream,
			Map<String, Serializable> parameters)
		throws IOException {

		if (fieldNames.isEmpty()) {
			throw new IllegalArgumentException("Field names are not set");
		}

		_csvPrinter = new CSVPrinter(
			new BufferedWriter(new OutputStreamWriter(outputStream)),
			_getCSVFormat(delimiter));

		fieldNames = ListUtil.sort(
			fieldNames, (value1, value2) -> value1.compareToIgnoreCase(value2));

		_columnValuesExtractor = new ColumnValuesExtractor(
			fieldMap, fieldNames);

		if (Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE))) {

			_csvPrinter.printRecord(fieldNames);
		}
	}

	@Override
	public void close() throws IOException {
		_csvPrinter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

		for (Object item : items) {
			_write(dateFormat, _columnValuesExtractor.extractValues(item));
		}
	}

	private CSVFormat _getCSVFormat(String delimiter) {
		CSVFormat.Builder builder = CSVFormat.Builder.create();

		builder.setDelimiter(delimiter);

		return builder.build();
	}

	private void _write(DateFormat dateFormat, Collection<?> values)
		throws Exception {

		for (Object value : values) {
			if (value instanceof Date) {
				value = dateFormat.format((Date)value);
			}

			_csvPrinter.print(value);
		}

		_csvPrinter.println();
	}

	private final ColumnValuesExtractor _columnValuesExtractor;
	private final CSVPrinter _csvPrinter;

}