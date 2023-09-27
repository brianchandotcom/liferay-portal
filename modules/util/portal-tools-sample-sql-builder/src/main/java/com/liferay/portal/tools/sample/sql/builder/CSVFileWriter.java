/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public CSVFileWriter(File outputDir) throws FileNotFoundException {
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

	private final Map<String, Writer> _csvWriters = new HashMap<>();

}