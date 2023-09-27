/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.batch.engine.internal.writer;

import com.fasterxml.jackson.databind.util.ISO8601DateFormat;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ListUtil;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.text.DateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;

/**
 * @author Ivica Cardic
 * @author Igor Beslic
 * @author Matija Petanjek
 */
public class CSVBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public CSVBatchEngineExportTaskItemWriterImpl(
			String delimiter, Map<String, Field> fieldsMap,
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
			fieldsMap, fieldNames);

		if (Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE))) {

			_csvPrinter.printRecord(_columnValuesExtractor.getHeaders());
		}
	}

	@Override
	public void close() throws IOException {
		_csvPrinter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		DateFormat dateFormat = new ISO8601DateFormat();

		for (Object item : items) {
			for (Object[] values : _columnValuesExtractor.extractValues(item)) {
				_write(dateFormat, values);
			}
		}
	}

	private CSVFormat _getCSVFormat(String delimiter) {
		CSVFormat.Builder builder = CSVFormat.Builder.create();

		builder.setDelimiter(delimiter);

		return builder.build();
	}

	private void _write(DateFormat dateFormat, Object[] values)
		throws Exception {

		for (Object value : values) {
			if (value instanceof Date) {
				value = dateFormat.format((Date)value);
			}
			else if (value instanceof Map) {
				Map<String, Object> map = (Map<String, Object>)value;

				StringBundler sb = new StringBundler();

				for (Map.Entry<String, Object> entry : map.entrySet()) {
					sb.append(entry.getKey());
					sb.append(StringPool.COLON);
					sb.append(entry.getValue());
					sb.append(StringPool.RETURN_NEW_LINE);
				}

				value = sb.toString();
			}

			_csvPrinter.print(value);
		}

		_csvPrinter.println();
	}

	private final ColumnValuesExtractor _columnValuesExtractor;
	private final CSVPrinter _csvPrinter;

}