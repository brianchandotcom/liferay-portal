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
import com.liferay.petra.io.unsync.UnsyncBufferedReader;
import com.liferay.petra.io.unsync.UnsyncBufferedWriter;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.tools.ToolDependencies;
import com.liferay.portal.tools.sample.sql.builder.io.CharPipe;
import com.liferay.portal.tools.sample.sql.builder.io.UnsyncTeeWriter;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.channels.FileChannel;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SampleSQLBuilder {

	public SampleSQLBuilder() {
		ToolDependencies.wireBasic();

		// Generic

		File tempDir = new File(BenchmarksPropsValues.OUTPUT_DIR, "temp");

		tempDir.mkdirs();

		Reader reader = generateSQL();

		try {

			// Specific

			compressSQL(reader, tempDir);

			// Merge

			if (BenchmarksPropsValues.OUTPUT_MERGE) {
				File sqlFile = new File(
					BenchmarksPropsValues.OUTPUT_DIR,
					"sample-" + BenchmarksPropsValues.DB_TYPE + ".sql");

				FileUtil.delete(sqlFile);

				mergeSQL(tempDir, sqlFile);
			}
			else {
				File outputDir = new File(
					BenchmarksPropsValues.OUTPUT_DIR, "output");

				FileUtil.deltree(outputDir);

				if (!tempDir.renameTo(outputDir)) {

					// This will only happen when temp and output directories
					// are on different file systems

					FileUtil.copyDirectory(tempDir, outputDir);
				}
			}

			FileUtil.write(
				new File(
					BenchmarksPropsValues.OUTPUT_DIR,
					"benchmarks-actual.properties"),
				BenchmarksPropsValues.ACTUAL_PROPERTIES_CONTENT);
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
		finally {
			FileUtil.deltree(tempDir);
		}
	}

	protected void compressSQL(
			DB db, File directory, Map<String, Writer> insertSQLWriters,
			Map<String, StringBundler> sqls, String insertSQL)
		throws IOException, SQLException {

		String tableName = insertSQL.substring(0, insertSQL.indexOf(' '));

		int index = insertSQL.indexOf(" values ") + 8;

		StringBundler sb = sqls.get(tableName);

		if ((sb == null) || (sb.index() == 0)) {
			sb = new StringBundler();

			sqls.put(tableName, sb);

			sb.append("insert into ");
			sb.append(insertSQL.substring(0, index));
			sb.append("\n");
		}
		else {
			sb.append(",\n");
		}

		String values = insertSQL.substring(index, insertSQL.length() - 1);

		sb.append(values);

		if (sb.index() >= BenchmarksPropsValues.OPTIMIZE_BUFFER_SIZE) {
			sb.append(";\n");

			insertSQL = db.buildSQL(sb.toString());

			sb.setIndex(0);

			writeToInsertSQLFile(
				directory, tableName, insertSQLWriters, insertSQL);
		}
	}

	protected void compressSQL(Reader reader, File dir) throws Exception {
		DB db = DBManagerUtil.getDB(BenchmarksPropsValues.DB_TYPE, null);

		if ((BenchmarksPropsValues.DB_TYPE == DBType.MARIADB) ||
			(BenchmarksPropsValues.DB_TYPE == DBType.MYSQL)) {

			db = new SampleMySQLDB(db.getMajorVersion(), db.getMinorVersion());
		}

		Map<String, Writer> insertSQLWriters = new HashMap<>();
		Map<String, StringBundler> insertSQLs = new HashMap<>();
		List<String> miscSQLs = new ArrayList<>();

		try (UnsyncBufferedReader unsyncBufferedReader =
				new UnsyncBufferedReader(reader)) {

			String s = null;

			while ((_freeMarkerThrowable == null) &&
				   ((s = unsyncBufferedReader.readLine()) != null)) {

				s = s.trim();

				if (s.length() > 0) {
					if (s.startsWith("insert into ")) {
						if (!s.endsWith(");")) {
							StringBundler sb = new StringBundler();

							while (!s.endsWith(");")) {
								sb.append(s);
								sb.append(StringPool.NEW_LINE);

								s = unsyncBufferedReader.readLine();
							}

							sb.append(s);

							s = sb.toString();
						}

						compressSQL(
							db, dir, insertSQLWriters, insertSQLs,
							s.substring(12));
					}
					else {
						miscSQLs.add(s);
					}
				}
			}
		}

		if (_freeMarkerThrowable != null) {
			throw new Exception(
				"Unable to process FreeMarker template ", _freeMarkerThrowable);
		}

		for (Map.Entry<String, StringBundler> entry : insertSQLs.entrySet()) {
			String tableName = entry.getKey();
			StringBundler sb = entry.getValue();

			if (sb.index() > 0) {
				String insertSQL = db.buildSQL(sb.toString());

				writeToInsertSQLFile(
					dir, tableName, insertSQLWriters, insertSQL);
			}

			try (Writer insertSQLWriter = insertSQLWriters.remove(tableName)) {
				insertSQLWriter.write(";\n");
			}
		}

		try (Writer miscSQLWriter = new FileWriter(new File(dir, "misc.sql"))) {
			for (String miscSQL : miscSQLs) {
				miscSQL = db.buildSQL(miscSQL);

				miscSQLWriter.write(miscSQL);

				miscSQLWriter.write(StringPool.NEW_LINE);
			}
		}
	}

	protected Writer createFileWriter(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		Writer writer = new OutputStreamWriter(fileOutputStream);

		return new UnsyncBufferedWriter(writer, _WRITER_BUFFER_SIZE);
	}

	protected Reader generateSQL() {
		final CharPipe charPipe = new CharPipe(_PIPE_BUFFER_SIZE);

		Thread thread = new Thread(
			() -> {
				try (CSVFileWriter csvFileWriter = new CSVFileWriter();
					Writer sampleSQLWriter = new UnsyncTeeWriter(
						new UnsyncBufferedWriter(
							charPipe.getWriter(), _WRITER_BUFFER_SIZE),
						createFileWriter(
							new File(
								BenchmarksPropsValues.OUTPUT_DIR,
								"sample.sql")))) {

					FreeMarkerUtil.process(
						BenchmarksPropsValues.SCRIPT,
						HashMapBuilder.<String, Object>put(
							"csvFileWriter", csvFileWriter
						).put(
							"dataFactory", new DataFactory()
						).build(),
						sampleSQLWriter);
				}
				catch (Throwable throwable) {
					_freeMarkerThrowable = throwable;
				}
				finally {
					charPipe.close();
				}
			});

		thread.start();

		return charPipe.getReader();
	}

	protected void mergeSQL(File inputDir, File outputSQLFile)
		throws IOException {

		FileOutputStream outputSQLFileOutputStream = new FileOutputStream(
			outputSQLFile);

		try (FileChannel outputFileChannel =
				outputSQLFileOutputStream.getChannel()) {

			File miscSQLFile = null;

			for (File inputFile : inputDir.listFiles()) {
				String inputFileName = inputFile.getName();

				if (inputFileName.equals("misc.sql")) {
					miscSQLFile = inputFile;

					continue;
				}

				mergeSQL(inputFile, outputFileChannel);
			}

			if (miscSQLFile != null) {
				mergeSQL(miscSQLFile, outputFileChannel);
			}
		}
	}

	protected void mergeSQL(File inputFile, FileChannel outputFileChannel)
		throws IOException {

		FileInputStream inputFileInputStream = new FileInputStream(inputFile);

		try (FileChannel inputFileChannel = inputFileInputStream.getChannel()) {
			inputFileChannel.transferTo(
				0, inputFileChannel.size(), outputFileChannel);
		}

		inputFile.delete();
	}

	protected void writeToInsertSQLFile(
			File dir, String tableName, Map<String, Writer> insertSQLWriters,
			String insertSQL)
		throws IOException {

		Writer insertSQLWriter = insertSQLWriters.get(tableName);

		if (insertSQLWriter == null) {
			File file = new File(dir, tableName + ".sql");

			insertSQLWriter = createFileWriter(file);

			insertSQLWriters.put(tableName, insertSQLWriter);
		}

		insertSQLWriter.write(insertSQL);
	}

	private static final int _PIPE_BUFFER_SIZE = 16 * 1024 * 1024;

	private static final int _WRITER_BUFFER_SIZE = 16 * 1024;

	private volatile Throwable _freeMarkerThrowable;

}