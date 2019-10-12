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
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

/**
 * @author Ivica cardic
 */
public class JSONBatchEngineTaskItemWriter
	implements BatchEngineTaskItemWriter {

	public JSONBatchEngineTaskItemWriter(OutputStream outputStream)
		throws IOException {

		_outputStream = outputStream;

		ObjectWriter objectWriter = _objectMapper.writer();

		_sequenceWriter = objectWriter.writeValuesAsArray(_outputStream);
	}

	@Override
	public void close() throws IOException {
		_sequenceWriter.close();

		_outputStream.close();
	}

	@Override
	public void write(List<?> items) throws Exception {
		_sequenceWriter.writeAll(items);
	}

	private static final ObjectMapper _objectMapper = new ObjectMapper() {
		{
			disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		}
	};

	private final OutputStream _outputStream;
	private final SequenceWriter _sequenceWriter;

}