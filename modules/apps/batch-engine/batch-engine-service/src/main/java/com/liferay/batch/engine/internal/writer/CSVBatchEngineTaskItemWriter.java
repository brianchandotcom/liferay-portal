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

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author Ivica cardic
 */
public class CSVBatchEngineTaskItemWriter implements BatchEngineTaskItemWriter {

	public CSVBatchEngineTaskItemWriter(
		String delimiter, OutputStream outputStream) {

		_delimiter = delimiter;
		_columnValueWriter = new ColumnValueWriter();
		_unsyncPrintWriter = new UnsyncPrintWriter(outputStream);
	}

	@Override
	public void close() throws IOException {
		_unsyncPrintWriter.close();
	}

	@Override
	public void write(List<?> items) throws Exception {
		DateFormat dateFormat = new SimpleDateFormat(
			"yyyy-MM-dd'T'HH:mm:ss.SSSZ");

		for (Object item : items) {
			_columnValueWriter.write(
				item, values -> _write(dateFormat, values));
		}
	}

	@SuppressWarnings("unchecked")
	private void _write(DateFormat dateFormat, Collection<?> values) {
		_unsyncPrintWriter.write(
			StringUtil.merge(
				values,
				value -> {
					if (value instanceof Date) {
						return dateFormat.format(value);
					}

					return String.valueOf(value);
				},
				_delimiter));

		_unsyncPrintWriter.write(StringPool.NEW_LINE);
	}

	private final ColumnValueWriter _columnValueWriter;
	private final String _delimiter;
	private final UnsyncPrintWriter _unsyncPrintWriter;

}