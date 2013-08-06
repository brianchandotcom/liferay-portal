/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.tools.samplesqlbuilder;

import com.liferay.portal.dao.db.MySQLDB;
import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.io.CharPipe;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncTeeWriter;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.SortedProperties;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.util.InitUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SampleSQLBuilder {

	public static void main(String[] args) {
		List<String> extraConfigLocations = new ArrayList<String>();

		extraConfigLocations.add("META-INF/portlet-container-spring.xml");

		InitUtil.initWithSpring(false, extraConfigLocations);

		Reader reader = null;

		try {
			Properties properties = new SortedProperties();

			reader = new FileReader(args[0]);

			properties.load(reader);

			new SampleSQLBuilder(properties);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (reader != null) {
				try {
					reader.close();
				}
				catch (IOException ioe) {
					ioe.printStackTrace();
				}
			}
		}
	}

	public SampleSQLBuilder(Properties properties) throws Exception {
		_dbType = properties.getProperty("sample.sql.db.type");

		_optimizeBufferSize = GetterUtil.getInteger(
			properties.getProperty("sample.sql.optimize.buffer.size"));
		_outputDir = properties.getProperty("sample.sql.output.dir");
		boolean outputMerge = GetterUtil.getBoolean(
			properties.getProperty("sample.sql.output.merge"));

		_dataFactory = new DataFactory(properties);

		// Generic

		Reader reader = generateSQL();

		String endSQLFileName = "others.sql";
		File tempDir = new File(_outputDir, "temp");

		tempDir.mkdirs();

		try {

			// Specific

			compressSQL(reader, tempDir, endSQLFileName);

			// Merge

			if (outputMerge) {
				File mergedSQLFile = new File(
					_outputDir, "sample-" + _dbType + ".sql");

				FileUtil.delete(mergedSQLFile);

				mergeSQL(mergedSQLFile, tempDir, endSQLFileName);
			}
			else {
				File dividedSQLDir = new File(_outputDir, "output");

				FileUtil.deltree(dividedSQLDir);

				if (!tempDir.renameTo(dividedSQLDir)) {

					// This will only happen when temp and output folders are on
					// different file systems

					FileUtil.copyDirectory(tempDir, dividedSQLDir);
				}
			}
		}
		finally {
			FileUtil.deltree(tempDir);
		}

		StringBundler sb = new StringBundler();

		for (String key : properties.stringPropertyNames()) {
			if (!key.startsWith("sample.sql")) {
				continue;
			}

			String value = properties.getProperty(key);

			sb.append(key);
			sb.append(StringPool.EQUAL);
			sb.append(value);
			sb.append(StringPool.NEW_LINE);
		}

		FileUtil.write(
			new File(_outputDir, "benchmarks-actual.properties"),
			sb.toString());
	}

	protected void compressInsertSQL(
			DB db, File directory, String insertSQL,
			Map<String, StringBundler> insertSQLs,
			Map<String, Writer> insertSQLWriters)
		throws IOException {

		String tableName = insertSQL.substring(0, insertSQL.indexOf(' '));

		int pos = insertSQL.indexOf(" values ") + 8;

		String values = insertSQL.substring(pos, insertSQL.length() - 1);

		StringBundler sb = insertSQLs.get(tableName);

		if ((sb == null) || (sb.index() == 0)) {
			sb = new StringBundler();

			insertSQLs.put(tableName, sb);

			sb.append("insert into ");
			sb.append(insertSQL.substring(0, pos));
			sb.append("\n");
		}
		else {
			sb.append(",\n");
		}

		sb.append(values);

		if (sb.index() >= _optimizeBufferSize) {
			sb.append(";\n");

			String sql = db.buildSQL(sb.toString());

			sb.setIndex(0);

			writeToInsertSQLFile(directory, insertSQLWriters, tableName, sql);
		}
	}

	protected void compressSQL(
			Reader reader, File directory, String endSQLFileName)
		throws IOException {

		DB db = DBFactoryUtil.getDB(_dbType);

		if (db instanceof MySQLDB) {
			db = new SampleMySQLDB();
		}

		Map<String, StringBundler> insertSQLs =
			new HashMap<String, StringBundler>();
		Map<String, Writer> insertSQLWriters = new HashMap<String, Writer>();
		List<String> otherSQLs = new ArrayList<String>();

		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			reader);

		String s = null;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			s = s.trim();

			if (s.length() > 0) {
				if (s.startsWith("insert into ")) {
					compressInsertSQL(
						db, directory, s.substring(12), insertSQLs,
						insertSQLWriters);
				}
				else {
					otherSQLs.add(s);
				}
			}
		}

		unsyncBufferedReader.close();

		for (Map.Entry<String, StringBundler> entry : insertSQLs.entrySet()) {
			String tableName = entry.getKey();
			StringBundler sb = entry.getValue();

			String sql = db.buildSQL(sb.toString());

			writeToInsertSQLFile(directory, insertSQLWriters, tableName, sql);

			Writer insertSQLWriter = insertSQLWriters.remove(tableName);

			insertSQLWriter.write(";\n");

			insertSQLWriter.close();
		}

		Writer endSQLFileWriter = new FileWriter(
			new File(directory, endSQLFileName));

		for (String sql : otherSQLs) {
			sql = db.buildSQL(sql);

			endSQLFileWriter.write(sql);
			endSQLFileWriter.write(StringPool.NEW_LINE);
		}

		endSQLFileWriter.close();
	}

	protected Writer createFileWriter(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		Writer writer = new OutputStreamWriter(fileOutputStream);

		return createUnsyncBufferedWriter(writer);
	}

	protected Writer createUnsyncBufferedWriter(Writer writer) {
		return new UnsyncBufferedWriter(writer, _WRITER_BUFFER_SIZE) {

			@Override
			public void flush() {

				// Disable FreeMarker from flushing

			}

		};
	}

	protected void doMergeSQL(File sqlFile, FileChannel outputFileChannel)
		throws IOException {

		FileInputStream fileInputStream = new FileInputStream(sqlFile);

		FileChannel inputFileChannel = fileInputStream.getChannel();

		inputFileChannel.transferTo(
			0, inputFileChannel.size(), outputFileChannel);

		inputFileChannel.close();

		sqlFile.delete();
	}

	protected Reader generateSQL() {
		final CharPipe charPipe = new CharPipe(_PIPE_BUFFER_SIZE);

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					Writer sampleSQLWriter = new UnsyncTeeWriter(
						createUnsyncBufferedWriter(charPipe.getWriter()),
						createFileWriter(new File(_outputDir, "sample.sql")));

					Map<String, Object> context = getContext();

					FreeMarkerUtil.process(_SCRIPT, context, sampleSQLWriter);

					for (String fileName : _CSV_FILE_NAMES) {
						Writer writer = (Writer)context.get(
							fileName + "CSVWriter");

						writer.close();
					}

					sampleSQLWriter.close();

					charPipe.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		thread.start();

		return charPipe.getReader();
	}

	protected Map<String, Object> getContext() throws Exception {
		Map<String, Object> context = new HashMap<String, Object>();

		context.put("dataFactory", _dataFactory);

		for (String fileName : _CSV_FILE_NAMES) {
			Writer writer = createFileWriter(
				new File(_outputDir, fileName + ".csv"));

			context.put(fileName + "CSVWriter", writer);
		}

		return context;
	}

	protected void mergeSQL(
			File mergedSQLFile, File directory, String endSQLFileName)
		throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(mergedSQLFile);
		FileChannel fileChannel = fileOutputStream.getChannel();

		File endSQLFile = null;

		for (File sqlFile : directory.listFiles()) {
			String fileName = sqlFile.getName();

			if (fileName.equals(endSQLFileName)) {
				endSQLFile = sqlFile;

				continue;
			}

			doMergeSQL(sqlFile, fileChannel);
		}

		if (endSQLFile != null) {
			doMergeSQL(endSQLFile, fileChannel);
		}

		fileChannel.close();
	}

	protected void writeToInsertSQLFile(
			File directory, Map<String, Writer> writers, String tableName,
			String sql)
		throws IOException {

		Writer writer = writers.get(tableName);

		if (writer == null) {
			File file = new File(directory, tableName + ".sql");

			writer = createFileWriter(file);

			writers.put(tableName, writer);
		}

		writer.write(sql);
	}

	private static final String[] _CSV_FILE_NAMES = {
		"assetPublisher", "blog", "company", "documentLibrary",
		"dynamicDataList", "layout", "messageBoard", "repository", "wiki"
	};

	private static final int _PIPE_BUFFER_SIZE = 16 * 1024 * 1024;

	private static final String _SCRIPT =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/sample.ftl";

	private static final int _WRITER_BUFFER_SIZE = 16 * 1024;

	private DataFactory _dataFactory;
	private String _dbType;
	private int _optimizeBufferSize;
	private String _outputDir;

}