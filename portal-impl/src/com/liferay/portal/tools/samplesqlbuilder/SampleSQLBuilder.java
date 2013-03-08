/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.DateUtil_IW;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatalists.model.DDLRecord;
import com.liferay.portlet.dynamicdatalists.model.DDLRecordSet;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.wiki.model.WikiPage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import java.nio.channels.FileChannel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class SampleSQLBuilder {

	public static void main(String[] args) {
		Map<String, String> arguments = ArgumentsUtil.parseArguments(args);

		InitUtil.initWithSpring();

		try {
			new SampleSQLBuilder(arguments);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public SampleSQLBuilder(Map<String, String> arguments) throws Exception {
		String baseDir = arguments.get("sample.sql.base.dir");

		_dbType = arguments.get("sample.sql.db.type");
		_maxBlogsEntryCommentCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.blogs.entry.comment.count"));
		_maxBlogsEntryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.blogs.entry.count"));
		_maxDDLRecordCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.ddl.record.count"));
		_maxDDLRecordSetCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.ddl.record.set.count"));
		_maxDLFileEntryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.dl.file.entry.count"));
		_maxDLFileEntrySize = GetterUtil.getInteger(
			arguments.get("sample.sql.max.dl.file.entry.size"));
		_maxDLFolderCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.dl.folder.count"));
		_maxDLFolderDepth = GetterUtil.getInteger(
			arguments.get("sample.sql.max.dl.folder.depth"));
		_maxGroupCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.group.count"));
		_maxJournalArticleCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.journal.article.count"));
		_maxJournalArticleSize = GetterUtil.getInteger(
			arguments.get("sample.sql.max.journal.article.size"));
		_maxMBCategoryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.mb.category.count"));
		_maxMBMessageCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.mb.message.count"));
		_maxMBThreadCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.mb.thread.count"));
		_maxUserCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.user.count"));
		_maxUserToGroupCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.user.to.group.count"));
		_maxWikiNodeCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.wiki.node.count"));
		_maxWikiPageCommentCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.wiki.page.comment.count"));
		_maxWikiPageCount = GetterUtil.getInteger(
			arguments.get("sample.sql.max.wiki.page.count"));
		_optimizeBufferSize = GetterUtil.getInteger(
			arguments.get("sample.sql.optimize.buffer.size"));
		_outputDir = arguments.get("sample.sql.output.dir");
		_outputMerge = GetterUtil.getBoolean(
			arguments.get("sample.sql.output.merge"));

		_dataFactory = new DataFactory(
			baseDir, _maxBlogsEntryCount, _maxGroupCount,
			_maxJournalArticleSize, _maxMBCategoryCount, _maxMBThreadCount,
			_maxMBMessageCount, _maxUserToGroupCount);

		_db = DBFactoryUtil.getDB(_dbType);

		if (_db instanceof MySQLDB) {
			_db = new SampleMySQLDB();
		}

		// Clean up previous output

		FileUtil.delete(_outputDir + "/sample-" + _dbType + ".sql");
		FileUtil.deltree(_outputDir + "/output");

		// Generic

		_tempDir = new File(_outputDir, "temp");

		_tempDir.mkdirs();

		final CharPipe charPipe = new CharPipe(_PIPE_BUFFER_SIZE);

		generateSQL(charPipe);

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

		List<String> keys = ListUtil.fromMapKeys(arguments);

		Collections.sort(keys);

		for (String key : keys) {
			if (!key.startsWith("sample.sql")) {
				continue;
			}

			String value = arguments.get(key);

			sb.append(key);
			sb.append(StringPool.EQUAL);
			sb.append(value);
			sb.append(StringPool.NEW_LINE);
		}

		FileUtil.write(
			new File(_outputDir, "benchmarks-actual.properties"),
			sb.toString());
	}

	public void insertBlogsEntry(BlogsEntry blogsEntry) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "blogsEntry", blogsEntry);

		processTemplate(_tplBlogsEntry, context);
	}

	public void insertDDLRecord(
			DDLRecord ddlRecord, DDLRecordSet ddlRecordSet, int ddlRecordCount)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "ddlRecord", ddlRecord);
		put(context, "ddlRecordCount", ddlRecordCount);
		put(context, "ddlRecordSet", ddlRecordSet);

		processTemplate(_tplDDLRecord, context);
	}

	public void insertDDLRecordSet(DDMStructure ddmStructure) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "ddmStructure", ddmStructure);

		processTemplate(_tplDLFolders, context);
	}

	public void insertDLFileEntry(
			DLFileEntry dlFileEntry, DDMStructure ddmStructure)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "ddmStructure", ddmStructure);
		put(context, "dlFileEntry", dlFileEntry);

		processTemplate(_tplDLFileEntry, context);
	}

	public void insertDLFolder(DLFolder dlFolder, DDMStructure ddmStructure)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "ddmStructure", ddmStructure);
		put(context, "dlFolder", dlFolder);

		processTemplate(_tplDLFolder, context);
	}

	public void insertDLFolders(
			long groupId, long parentDLFolderId, int dlFolderDepth,
			DDMStructure ddmStructure)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "ddmStructure", ddmStructure);
		put(context, "dlFolderDepth", dlFolderDepth);
		put(context, "groupId", groupId);
		put(context, "parentDLFolderId", parentDLFolderId);

		processTemplate(_tplDLFolders, context);
	}

	public void insertGroup(Group group, int publicPageCount) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "group", group);
		put(context, "publicPageCount", publicPageCount);

		processTemplate(_tplGroup, context);
	}

	public void insertLayout(Layout layout) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "layout", layout);

		processTemplate(_tplLayout, context);
	}

	public void insertMBCategory(MBCategory mbCategory) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbCategory", mbCategory);

		processTemplate(_tplMBCategory, context);
	}

	public void insertMBDiscussion(
			long groupId, long classNameId, long classPK, long mbThreadId,
			long mbRootMessageId, int maxCommentCount)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "groupId", groupId);
		put(context, "classNameId", classNameId);
		put(context, "classPK", classPK);
		put(context, "mbThreadId", mbThreadId);
		put(context, "mbRootMessageId", mbRootMessageId);
		put(context, "maxCommentCount", maxCommentCount);

		processTemplate(_tplMBDiscussion, context);
	}

	public void insertMBMessage(MBMessage mbMessage) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbMessage", mbMessage);

		processTemplate(_tplMBMessage, context);
	}

	public void insertResourcePermission(String name, String primKey)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "resourceName", name);
		put(context, "resourcePrimkey", primKey);

		processTemplate(_tplResourcePermission, context);
	}

	public void insertUser(List<Long> groupIds, List<Role> roleIds, User user)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "groupIds", groupIds);
		put(context, "roleIds", roleIds);
		put(context, "user", user);

		processTemplate(_tplUser, context);
	}

	public void insertWikiPage(WikiPage wikiPage) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "wikiPage", wikiPage);

		processTemplate(_tplWikiPage, context);
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
	}

	protected Writer createFileWriter(File file) throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(file);

		Writer writer = new OutputStreamWriter(fileOutputStream);

		return createUnsyncBufferedWriter(writer);
	}

	protected Writer createFileWriter(String fileName) throws IOException {
		File file = new File(fileName);

		return createFileWriter(file);
	}

	protected Writer createUnsyncBufferedWriter(Writer writer) {
		return new UnsyncBufferedWriter(writer, _WRITER_BUFFER_SIZE) {

			@Override
			public void flush() {

				// Disable FreeMarker from flushing

			}

		};
	}

	protected void generateSQL(final CharPipe charPipe) {
		final Writer writer = createUnsyncBufferedWriter(charPipe.getWriter());

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					_writerSampleSQL = new UnsyncTeeWriter(
						writer, createFileWriter(_outputDir + "/sample.sql"));

					createSample();

					_writerSampleSQL.close();

					charPipe.close();
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}

			protected void createSample() throws Exception {
				_writerBlogsCSV = getWriter("blogs.csv");
				_writerCompanyCSV = getWriter("company.csv");
				_writerDocumentLibraryCSV = getWriter("document_library.csv");
				_writerDynamicDataListsCSV = getWriter(
					"dynamic_data_lists.csv");
				_writerLayoutCSV = getWriter("layout.csv");
				_writerMessageBoardsCSV = getWriter("message_boards.csv");
				_writerRepositoryCSV = getWriter("repository.csv");
				_writerWikiCSV = getWriter("wiki.csv");

				Map<String, Object> context = getContext();

				processTemplate(_tplSample, context);

				_writerBlogsCSV.close();
				_writerCompanyCSV.close();
				_writerDocumentLibraryCSV.close();
				_writerDynamicDataListsCSV.close();
				_writerLayoutCSV.close();
				_writerMessageBoardsCSV.close();
				_writerRepositoryCSV.close();
				_writerWikiCSV.close();
			}

			protected Writer getWriter(String fileName) throws Exception {
				return createFileWriter(new File(_outputDir + "/" + fileName));
			}

		};

		thread.start();
	}

	protected Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<String, Object>();

		put(context, "companyId", _dataFactory.getCompanyId());
		put(context, "counter", _dataFactory.getCounter());
		put(context, "dataFactory", _dataFactory);
		put(context, "dateUtil", DateUtil_IW.getInstance());
		put(context, "maxDLFileEntrySize", _maxDLFileEntrySize);
		put(context, "maxBlogsEntryCommentCount", _maxBlogsEntryCommentCount);
		put(context, "maxBlogsEntryCount", _maxBlogsEntryCount);
		put(context, "maxDDLRecordCount", _maxDDLRecordCount);
		put(context, "maxDDLRecordSetCount", _maxDDLRecordSetCount);
		put(context, "maxDLFileEntryCount", _maxDLFileEntryCount);
		put(context, "maxDLFolderCount", _maxDLFolderCount);
		put(context, "maxDLFolderDepth", _maxDLFolderDepth);
		put(context, "maxGroupCount", _maxGroupCount);
		put(context, "maxJournalArticleCount", _maxJournalArticleCount);
		put(context, "maxMBCategoryCount", _maxMBCategoryCount);
		put(context, "maxMBMessageCount", _maxMBMessageCount);
		put(context, "maxMBThreadCount", _maxMBThreadCount);
		put(context, "maxUserCount", _maxUserCount);
		put(context, "maxUserToGroupCount", _maxUserToGroupCount);
		put(context, "maxWikiNodeCount", _maxWikiNodeCount);
		put(context, "maxWikiPageCommentCount", _maxWikiPageCommentCount);
		put(context, "maxWikiPageCount", _maxWikiPageCount);
		put(context, "portalUUIDUtil", SequentialUUID.getSequentialUUID());
		put(context, "sampleSQLBuilder", this);
		put(context, "stringUtil", StringUtil_IW.getInstance());
		put(context, "writerBlogsCSV", _writerBlogsCSV);
		put(context, "writerCompanyCSV", _writerCompanyCSV);
		put(context, "writerDocumentLibraryCSV", _writerDocumentLibraryCSV);
		put(context, "writerDynamicDataListsCSV", _writerDynamicDataListsCSV);
		put(context, "writerLayoutCSV", _writerLayoutCSV);
		put(context, "writerMessageBoardsCSV", _writerMessageBoardsCSV);
		put(context, "writerRepositoryCSV", _writerRepositoryCSV);
		put(context, "writerWikiCSV", _writerWikiCSV);

		return context;
	}

	protected File getInsertSQLFile(String tableName) {
		return new File(_tempDir, tableName + ".sql");
	}

	protected void mergeSQL() throws IOException {
		File outputFile = new File(_outputDir + "/sample-" + _dbType + ".sql");

		FileOutputStream fileOutputStream = null;
		FileChannel fileChannel = null;

		if (_outputMerge) {
			fileOutputStream = new FileOutputStream(outputFile);
			fileChannel = fileOutputStream.getChannel();
		}

		Set<Map.Entry<String, StringBundler>> insertSQLs =
			_insertSQLs.entrySet();

		for (Map.Entry<String, StringBundler> entry : insertSQLs) {
			String tableName = entry.getKey();

			String sql = _db.buildSQL(entry.getValue().toString());

			writeToInsertSQLFile(tableName, sql);

			Writer insertSQLWriter = _insertSQLWriters.remove(tableName);

			insertSQLWriter.write(";\n");

			insertSQLWriter.close();

			if (_outputMerge) {
				File insertSQLFile = getInsertSQLFile(tableName);

				FileInputStream insertSQLFileInputStream = new FileInputStream(
					insertSQLFile);

				FileChannel insertSQLFileChannel =
					insertSQLFileInputStream.getChannel();

				insertSQLFileChannel.transferTo(
					0, insertSQLFileChannel.size(), fileChannel);

				insertSQLFileChannel.close();

				insertSQLFile.delete();
			}
		}

		Writer writer = null;

		if (_outputMerge) {
			writer = new OutputStreamWriter(fileOutputStream);
		}
		else {
			writer = new FileWriter(getInsertSQLFile("others"));
		}

		for (String sql : _otherSQLs) {
			sql = _db.buildSQL(sql);

			writer.write(sql);
			writer.write(StringPool.NEW_LINE);
		}

		writer.close();

		File outputFolder = new File(_outputDir, "output");

		if (!_outputMerge && !_tempDir.renameTo(outputFolder)) {

			// This will only happen when temp and output folders are on
			// different file systems

			FileUtil.copyDirectory(_tempDir, outputFolder);
		}
	}

	protected void processTemplate(String name, Map<String, Object> context)
		throws Exception {

		FreeMarkerUtil.process(name, context, _writerSampleSQL);
	}

	protected void put(Map<String, Object> context, String key, Object value) {
		context.put(key, value);
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

	private static final int _PIPE_BUFFER_SIZE = 16 * 1024 * 1024;

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/";

	private static final int _WRITER_BUFFER_SIZE = 16 * 1024;

	private DataFactory _dataFactory;
	private DB _db;
	private String _dbType;
	private Map<String, StringBundler> _insertSQLs =
		new ConcurrentHashMap<String, StringBundler>();
	private Map<String, Writer> _insertSQLWriters =
		new ConcurrentHashMap<String, Writer>();
	private int _maxBlogsEntryCommentCount;
	private int _maxBlogsEntryCount;
	private int _maxDDLRecordCount;
	private int _maxDDLRecordSetCount;
	private int _maxDLFileEntryCount;
	private int _maxDLFileEntrySize;
	private int _maxDLFolderCount;
	private int _maxDLFolderDepth;
	private int _maxGroupCount;
	private int _maxJournalArticleCount;
	private int _maxJournalArticleSize;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserCount;
	private int _maxUserToGroupCount;
	private int _maxWikiNodeCount;
	private int _maxWikiPageCommentCount;
	private int _maxWikiPageCount;
	private int _optimizeBufferSize;
	private List<String> _otherSQLs = new ArrayList<String>();
	private String _outputDir;
	private boolean _outputMerge;
	private File _tempDir;
	private String _tplBlogsEntry = _TPL_ROOT + "blogs_entry.ftl";
	private String _tplDDLRecord = _TPL_ROOT + "ddl_record.ftl";
	private String _tplDLFileEntry = _TPL_ROOT + "dl_file_entry.ftl";
	private String _tplDLFolder = _TPL_ROOT + "dl_folder.ftl";
	private String _tplDLFolders = _TPL_ROOT + "dl_folders.ftl";
	private String _tplGroup = _TPL_ROOT + "group.ftl";
	private String _tplLayout = _TPL_ROOT + "layout.ftl";
	private String _tplMBCategory = _TPL_ROOT + "mb_category.ftl";
	private String _tplMBDiscussion = _TPL_ROOT + "mb_discussion.ftl";
	private String _tplMBMessage = _TPL_ROOT + "mb_message.ftl";;
	private String _tplResourcePermission =
		_TPL_ROOT + "resource_permission.ftl";
	private String _tplSample = _TPL_ROOT + "sample.ftl";
	private String _tplUser = _TPL_ROOT + "user.ftl";
	private String _tplWikiPage = _TPL_ROOT + "wiki_page.ftl";
	private Writer _writerBlogsCSV;
	private Writer _writerCompanyCSV;
	private Writer _writerDocumentLibraryCSV;
	private Writer _writerDynamicDataListsCSV;
	private Writer _writerLayoutCSV;
	private Writer _writerMessageBoardsCSV;
	private Writer _writerRepositoryCSV;
	private Writer _writerSampleSQL;
	private Writer _writerWikiCSV;

}