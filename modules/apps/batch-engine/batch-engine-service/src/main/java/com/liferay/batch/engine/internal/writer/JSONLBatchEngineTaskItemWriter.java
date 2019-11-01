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

import com.fasterxml.jackson.databind.ObjectWriter;

import com.liferay.petra.io.unsync.UnsyncPrintWriter;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author Ivica cardic
 */
public class JSONLBatchEngineTaskItemWriter
	implements BatchEngineTaskItemWriter {

	public JSONLBatchEngineTaskItemWriter(
		Set<String> allFieldNames, List<String> includeFieldNames,
		OutputStream outputStream) {

		_objectWriter = ObjectWriterFactory.getObjectWriter(
			allFieldNames, includeFieldNames);
		_unsyncPrintWriter = new UnsyncPrintWriter(outputStream);
	}

	@Override
	public void close() throws IOException {
		_unsyncPrintWriter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		for (Object item : items) {
			_unsyncPrintWriter.write(_objectWriter.writeValueAsString(item));
			_unsyncPrintWriter.write(StringPool.NEW_LINE);
		}
	}

	private final ObjectWriter _objectWriter;
	private final UnsyncPrintWriter _unsyncPrintWriter;

}