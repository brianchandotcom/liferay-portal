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
import java.util.concurrent.ConcurrentHashMap;

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
		_outputMerge = GetterUtil.getBoolean(
			properties.getProperty("sample.sql.output.merge"));

		_dataFactory = new DataFactory(properties);

		_db = DBFactoryUtil.getDB(_dbType);

		if (_db instanceof MySQLDB) {
			_db = new SampleMySQLDB();
		}

		// Clean up previous output

		FileUtil.delete(_outputDir + "/sample-" + _dbType + ".sql");
		FileUtil.deltree(_outputDir + "/output");

		// Generic

		_tempDir = new File(_outputDir, "temp");
		_endSQLFileName = "others.sql";

		_tempDir.mkdirs();

		CharPipe charPipe = generateSQL();

		try {

			// Specific

			compressSQL(charPipe.getReader());

			// Merge

			mergeSQL();
		}
		finally {
			FileUtil.deltree(_tempDir);
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

	protected void compressInsertSQL(String insertSQL) throws IOException {
		String tableName = insertSQL.substring(0, insertSQL.indexOf(' '));

		int pos = insertSQL.indexOf(" values ") + 8;

		String values = insertSQL.substring(pos, insertSQL.length() - 1);

		StringBundler sb = _insertSQLs.get(tableName);

		if ((sb == null) || (sb.index() == 0)) {
			sb = new StringBundler();

			_insertSQLs.put(tableName, sb);

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

			String sql = _db.buildSQL(sb.toString());

			sb.setIndex(0);

			writeToInsertSQLFile(tableName, sql);
		}
	}

	protected void compressSQL(Reader reader) throws IOException {
		UnsyncBufferedReader unsyncBufferedReader = new UnsyncBufferedReader(
			reader);

		String s = null;

		while ((s = unsyncBufferedReader.readLine()) != null) {
			s = s.trim();

			if (s.length() > 0) {
				if (s.startsWith("insert into ")) {
					compressInsertSQL(s.substring(12));
				}
				else if (s.length() > 0) {
					_otherSQLs.add(s);
				}
			}
		}

		unsyncBufferedReader.close();

		for (Map.Entry<String, StringBundler> entry : _insertSQLs.entrySet()) {
			String tableName = entry.getKey();
			StringBundler sb = entry.getValue();

			String sql = _db.buildSQL(sb.toString());

			writeToInsertSQLFile(tableName, sql);

			Writer insertSQLWriter = _insertSQLWriters.remove(tableName);

			insertSQLWriter.write(";\n");

			insertSQLWriter.close();
		}

		File endSQLFile = new File(_tempDir, _endSQLFileName);

		Writer endSQLFileWriter = new FileWriter(endSQLFile);

		for (String sql : _otherSQLs) {
			sql = _db.buildSQL(sql);

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
	}

	protected CharPipe generateSQL() {
		final CharPipe charPipe = new CharPipe(_PIPE_BUFFER_SIZE);

		final Writer writer = createUnsyncBufferedWriter(charPipe.getWriter());

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					Writer writerSampleSQL = new UnsyncTeeWriter(
						writer,
						createFileWriter(new File(_outputDir, "sample.sql")));

					Map<String, Object> context = getContext();

					FreeMarkerUtil.process(_SCRIPT, context, writerSampleSQL);

					for (String fileName : _CSV_FILE_NAMES) {
						Writer writer = (Writer)context.get(
							fileName + "CSVWriter");

						writer.close();
					}

					writerSampleSQL.close();

					charPipe.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

		};

		thread.start();

		return charPipe;
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

	protected File getInsertSQLFile(String tableName) {
		return new File(_tempDir, tableName + ".sql");
	}

	protected void mergeSQL() throws IOException {
		if (!_outputMerge) {
			File outputFolder = new File(_outputDir, "output");

			if (!_tempDir.renameTo(outputFolder)) {

				// This will only happen when temp and output folders are on
				// different file systems

				FileUtil.copyDirectory(_tempDir, outputFolder);
			}

			return;
		}

		File outputFile = new File(_outputDir, "sample-" + _dbType + ".sql");

		FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
		FileChannel fileChannel = fileOutputStream.getChannel();

		File lastSQLFile = null;

		for (File tableFile : _tempDir.listFiles()) {
			String fileName = tableFile.getName();

			if (fileName.equals(_endSQLFileName)) {
				lastSQLFile = tableFile;

				continue;
			}

			doMergeSQL(tableFile, fileChannel);

			tableFile.delete();
		}

		if (lastSQLFile != null) {
			doMergeSQL(lastSQLFile, fileChannel);

			lastSQLFile.delete();
		}

		fileChannel.close();
	}

	protected void writeToInsertSQLFile(String tableName, String sql)
		throws IOException {

		Writer writer = _insertSQLWriters.get(tableName);

		if (writer == null) {
			File file = getInsertSQLFile(tableName);

			writer = createFileWriter(file);

			_insertSQLWriters.put(tableName, writer);
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
	private DB _db;
	private String _dbType;
	private String _endSQLFileName;
	private Map<String, StringBundler> _insertSQLs =
		new ConcurrentHashMap<String, StringBundler>();
	private Map<String, Writer> _insertSQLWriters =
		new ConcurrentHashMap<String, Writer>();
	private int _optimizeBufferSize;
	private List<String> _otherSQLs = new ArrayList<String>();
	private String _outputDir;
	private boolean _outputMerge;
	private File _tempDir;

}