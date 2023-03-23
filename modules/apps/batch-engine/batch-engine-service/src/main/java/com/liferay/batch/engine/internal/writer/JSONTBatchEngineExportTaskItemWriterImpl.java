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

import com.liferay.batch.engine.unit.BatchEngineUnitConfiguration;
import com.liferay.petra.io.unsync.UnsyncPrintWriter;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.OutputStream;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * @author Igor Beslic
 */
public class JSONTBatchEngineExportTaskItemWriterImpl
	implements BatchEngineExportTaskItemWriter {

	public JSONTBatchEngineExportTaskItemWriterImpl(
			BatchEngineUnitConfiguration batchEngineUnitConfiguration,
			List<String> includeFieldNames, OutputStream outputStream)
		throws IOException {

		_objectWriter = ObjectWriterFactory.getObjectWriter(includeFieldNames);

		_unsyncPrintWriter = new UnsyncPrintWriter(outputStream);

		String batchEndpoint =
			"/o/headless-batch-engine/v1.0/import-task/" +
				batchEngineUnitConfiguration.getClassName();

		_unsyncPrintWriter.write(
			"{\"actions\":\n{\"createBatch\": {\"href\": \"");
		_unsyncPrintWriter.write(batchEndpoint);
		_unsyncPrintWriter.write(
			"\", \"method\": \"POST\"}, \"deleteBatch\": {\"href\": \"");
		_unsyncPrintWriter.write(batchEndpoint);
		_unsyncPrintWriter.write(
			"\", \"method\": \"DELETE\"}, \"updateBatch\": {\"href\": \"");
		_unsyncPrintWriter.write(batchEndpoint);
		_unsyncPrintWriter.write(
			"\", \"method\": \"PUT\"}},\n\"configuration\":\n");
		_unsyncPrintWriter.write(
			_objectWriter.writeValueAsString(batchEngineUnitConfiguration));
		_unsyncPrintWriter.write(",\n\"items\": [");
	}

	@Override
	public void close() throws IOException {
		_unsyncPrintWriter.write("]\n}");

		_unsyncPrintWriter.flush();

		_unsyncPrintWriter.close();
	}

	@Override
	public void write(Collection<?> items) throws Exception {
		if (_itemsStarted) {
			_unsyncPrintWriter.write(StringPool.COMMA);
		}

		Iterator<?> iterator = items.iterator();

		while (iterator.hasNext()) {
			Object item = iterator.next();

			_unsyncPrintWriter.write(_objectWriter.writeValueAsString(item));

			if (iterator.hasNext()) {
				_unsyncPrintWriter.write(StringPool.COMMA);
			}
		}

		if (!_itemsStarted) {
			_itemsStarted = true;
		}
	}

	private boolean _itemsStarted;
	private final ObjectWriter _objectWriter;
	private final UnsyncPrintWriter _unsyncPrintWriter;

}