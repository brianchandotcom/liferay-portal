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

import com.liferay.petra.io.unsync.UnsyncPrintWriter;
import com.liferay.petra.string.StringPool;
import com.liferay.petra.string.StringUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;

import java.lang.reflect.Field;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ivica Cardic
 */
public class CSVBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public CSVBatchEngineExportTaskItemWriterImpl(
		String delimiter, Map<String, Field> fieldMap, List<String> fieldNames,
		OutputStream outputStream, Map<String, Serializable> parameters) {

		if (fieldNames.isEmpty()) {
			throw new IllegalArgumentException("Field names are not set");
		}

		_delimiter = delimiter;

		_columnValuesExtractor = new ColumnValuesExtractor(
			fieldMap, fieldNames);

		_unsyncPrintWriter = new UnsyncPrintWriter(outputStream);

		if (Boolean.valueOf(
				(String)parameters.getOrDefault(
					"containsHeaders", StringPool.TRUE))) {

			_unsyncPrintWriter.println(StringUtil.merge(fieldNames, delimiter));
		}
	}

	@Override
	public void close() throws IOException {
		_unsyncPrintWriter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSXXX");

		for (Object item : items) {
			_write(dateFormat, _columnValuesExtractor.extractValues(item));
		}
	}

	private void _write(DateFormat dateFormat, Collection<?> values) {
		_unsyncPrintWriter.println(
			StringUtil.merge(
				values,
				value -> {
					if (value instanceof Date) {
						return dateFormat.format(value);
					}

					return String.valueOf(value);
				},
				_delimiter));
	}

	private final ColumnValuesExtractor _columnValuesExtractor;
	private final String _delimiter;
	private final UnsyncPrintWriter _unsyncPrintWriter;

}