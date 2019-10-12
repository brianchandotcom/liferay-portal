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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import com.liferay.petra.io.unsync.UnsyncPrintWriter;
import com.liferay.petra.string.StringPool;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

/**
 * @author Ivica cardic
 */
public class JSONLBatchEngineTaskItemWriter
	implements BatchEngineTaskItemWriter {

	public JSONLBatchEngineTaskItemWriter(OutputStream outputStream) {
		_unsyncPrintWriter = new UnsyncPrintWriter(outputStream);
	}

	@Override
	public void close() throws IOException {
		_unsyncPrintWriter.close();
	}

	@Override
	public void write(List<?> items) throws Exception {
		for (Object item : items) {
			_unsyncPrintWriter.write(_objectMapper.writeValueAsString(item));
			_unsyncPrintWriter.write(StringPool.NEW_LINE);
		}
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		}
	};

	private final UnsyncPrintWriter _unsyncPrintWriter;

}