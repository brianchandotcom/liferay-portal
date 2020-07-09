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

package com.liferay.portal.tools.sample.sql.builder;

import com.liferay.petra.io.OutputStreamWriter;
import com.liferay.petra.io.unsync.UnsyncBufferedWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Writer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Tina Tian
 */
public class CSVFileWriter implements AutoCloseable {

	public CSVFileWriter() throws FileNotFoundException {
		File outputDir = new File(BenchmarksPropsValues.OUTPUT_DIR);

		outputDir.mkdirs();

		for (String csvFileName : BenchmarksPropsValues.OUTPUT_CSV_FILE_NAMES) {
			_csvWriters.put(
				csvFileName,
				new UnsyncBufferedWriter(
					new OutputStreamWriter(
						new FileOutputStream(
							new File(outputDir, csvFileName.concat(".csv")))),
					_WRITER_BUFFER_SIZE));
		}
	}

	@Override
	public void close() throws IOException {
		for (Writer writer : _csvWriters.values()) {
			writer.close();
		}
	}

	public void write(String csvFileName, String content) throws IOException {
		Writer writer = _csvWriters.get(csvFileName);

		if (writer == null) {
			throw new IllegalArgumentException(
				"Unknown CSV file name: " + csvFileName);
		}

		writer.write(content);
	}

	private static final int _WRITER_BUFFER_SIZE = 16 * 1024;

	private Map<String, Writer> _csvWriters = new HashMap<>();

}