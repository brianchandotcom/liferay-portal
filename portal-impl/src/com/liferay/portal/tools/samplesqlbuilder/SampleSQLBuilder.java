/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.freemarker.FreeMarkerUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.io.CharPipe;
import com.liferay.portal.kernel.io.OutputStreamWriter;
import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.io.unsync.UnsyncTeeWriter;
import com.liferay.portal.kernel.util.DateUtil_IW;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil_IW;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.tools.ArgumentsUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.blogs.model.BlogsStatsUser;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.social.model.SocialActivity;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.SimpleCounter;

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

		String baseDir = arguments.get("sample.sql.base.dir");
		String outputDir = arguments.get("sample.sql.output.dir");
		String dbType = arguments.get("sample.sql.db.type");
		int maxBlogsEntryCommentCount = GetterUtil.getInteger(
			arguments.get("sample.sql.blogs.entry.comment.count"));
		int maxBlogsEntryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.blogs.entry.count"));
		int maxDLFileEntryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.dl.file.entry.count"));
		int dlFileEntrySize = GetterUtil.getInteger(
			arguments.get("sample.sql.dl.file.entry.size"));
		int maxDLFolderCount = GetterUtil.getInteger(
			arguments.get("sample.sql.dl.folder.count"));
		int maxDLFolderDepth = GetterUtil.getInteger(
			arguments.get("sample.sql.dl.folder.depth"));
		int maxGroupCount = GetterUtil.getInteger(
			arguments.get("sample.sql.group.count"));
		int maxMBCategoryCount = GetterUtil.getInteger(
			arguments.get("sample.sql.mb.category.count"));
		int maxMBMessageCount = GetterUtil.getInteger(
			arguments.get("sample.sql.mb.message.count"));
		int maxMBThreadCount = GetterUtil.getInteger(
			arguments.get("sample.sql.mb.thread.count"));
		int maxUserCount = GetterUtil.getInteger(
			arguments.get("sample.sql.user.count"));
		int maxUserToGroupCount = GetterUtil.getInteger(
			arguments.get("sample.sql.user.to.group.count"));
		int maxWikiNodeCount = GetterUtil.getInteger(
			arguments.get("sample.sql.wiki.node.count"));
		int maxWikiPageCommentCount = GetterUtil.getInteger(
			arguments.get("sample.sql.wiki.page.comment.count"));
		int maxWikiPageCount = GetterUtil.getInteger(
			arguments.get("sample.sql.wiki.page.count"));
		boolean securityEnabled = GetterUtil.getBoolean(
			arguments.get("sample.sql.security.enabled"));

		new SampleSQLBuilder(
			arguments, baseDir, outputDir, dbType, maxBlogsEntryCommentCount,
			maxBlogsEntryCount, maxDLFileEntryCount, dlFileEntrySize,
			maxDLFolderCount, maxDLFolderDepth, maxGroupCount,
			maxMBCategoryCount, maxMBMessageCount, maxMBThreadCount,
			maxUserCount, maxUserToGroupCount, maxWikiNodeCount,
			maxWikiPageCommentCount, maxWikiPageCount, securityEnabled);
	}

	public SampleSQLBuilder(
		Map<String, String> arguments, String baseDir, String outputDir,
		String dbType, int maxBlogsEntryCommentCount, int maxBlogsEntryCount,
		int maxDLFileEntryCount, int dlFileEntrySize, int maxDLFolderCount,
		int maxDLFolderDepth, int maxGroupCount, int maxMBCategoryCount,
		int maxMBMessageCount, int maxMBThreadCount, int maxUserCount,
		int maxUserToGroupCount, int maxWikiNodeCount,
		int maxWikiPageCommentCount, int maxWikiPageCount,
		boolean securityEnabled) {

		try {
			_outputDir = outputDir;
			_dbType = dbType;
			_maxBlogsEntryCommentCount = maxBlogsEntryCommentCount;
			_maxBlogsEntryCount = maxBlogsEntryCount;
			_maxDLFileEntryCount = maxDLFileEntryCount;
			_dlFileEntrySize = dlFileEntrySize;
			_maxDLFolderCount = maxDLFolderCount;
			_maxDLFolderDepth = maxDLFolderDepth;
			_maxGroupCount = maxGroupCount;
			_maxMBCategoryCount = maxMBCategoryCount;
			_maxMBMessageCount = maxMBMessageCount;
			_maxMBThreadCount = maxMBThreadCount;
			_maxUserCount = maxUserCount;
			_maxUserToGroupCount = maxUserToGroupCount;
			_maxWikiNodeCount = maxWikiNodeCount;
			_maxWikiPageCommentCount = maxWikiPageCommentCount;
			_maxWikiPageCount = maxWikiPageCount;
			_securityEnabled = securityEnabled;

			int totalMThreadCount = maxMBCategoryCount * maxMBThreadCount;
			int totalMBMessageCount = totalMThreadCount * maxMBMessageCount;

			int counterOffset =
				_maxGroupCount +
				(_maxGroupCount *
					(maxMBCategoryCount + totalMThreadCount +
						totalMBMessageCount)
				) + 1;

			_counter = new SimpleCounter(counterOffset);
			_permissionCounter = new SimpleCounter();
			_resourceCounter = new SimpleCounter();
			_resourceCodeCounter = new SimpleCounter();
			_socialActivityCounter = new SimpleCounter();

			_userScreenNameIncrementer = new SimpleCounter();

			_dataFactory = new DataFactory(
				baseDir, _maxGroupCount, _maxUserToGroupCount, _counter,
				_permissionCounter, _resourceCounter, _resourceCodeCounter,
				_socialActivityCounter);

			_db = DBFactoryUtil.getDB(_dbType);

			// Generic

			_tempDir = new File(_outputDir, "temp");

			_tempDir.mkdirs();

			final CharPipe charPipe = new CharPipe(1024 * 1024 * 10);

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
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertAssetEntry(AssetEntry assetEntry) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "assetEntry", assetEntry);

		processTemplate(_tplAssetEntry, context);
	}

	public void insertBlogsEntry(BlogsEntry blogsEntry) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "blogsEntry", blogsEntry);

		processTemplate(_tplBlogsEntry, context);
	}

	public void insertBlogsStatsUser(BlogsStatsUser blogsStatsUser)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "blogsStatsUser", blogsStatsUser);

		processTemplate(_tplBlogsStatsUser, context);
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
			long parentDLFolderId, int dlFolderDepth, DDMStructure ddmStructure)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "ddmStructure", ddmStructure);
		put(context, "dlFolderDepth", dlFolderDepth);
		put(context, "parentDLFolderId", parentDLFolderId);

		processTemplate(_tplDLFolders, context);
	}

	public void insertGroup(
			Group group, List<Layout> privateLayouts,
			List<Layout> publicLayouts)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "group", group);
		put(context, "privateLayouts", privateLayouts);
		put(context, "publicLayouts", publicLayouts);

		processTemplate(_tplGroup, context);
	}

	public void insertMBCategory(MBCategory mbCategory) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbCategory", mbCategory);

		processTemplate(_tplMBCategory, context);
	}

	public void insertMBDiscussion(MBDiscussion mbDiscussion) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbDiscussion", mbDiscussion);

		processTemplate(_tplMBDiscussion, context);
	}

	public void insertMBMessage(MBMessage mbMessage) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbMessage", mbMessage);

		processTemplate(_tplMBMessage, context);
	}

	public void insertMBStatsUser(MBStatsUser mbStatsUser) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbStatsUser", mbStatsUser);

		processTemplate(_tplMBStatsUser, context);
	}

	public void insertMBThread(MBThread mbThread) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "mbThread", mbThread);

		processTemplate(_tplMBThread, context);
	}

	public void insertSecurity(String name, long primKey) throws Exception {
		insertSecurity(name, String.valueOf(primKey));
	}

	public void insertSecurity(String name, String primKey) throws Exception {
		if (!_securityEnabled) {
			return;
		}

		Map<String, Object> context = getContext();

		Resource resource = _dataFactory.addResource(name, primKey);

		put(context, "resource", resource);

		processTemplate(_tplSecurity, context);
	}

	public void insertSocialActivity(SocialActivity socialActivity)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "socialActivity", socialActivity);

		processTemplate(_tplSocialActivity, context);
	}

	public void insertUser(
			Contact contact, Group group, List<Long> groupIds,
			List<Long> organizationIds, List<Layout> privateLayouts,
			List<Layout> publicLayouts, List<Role> roleIds, User user)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "contact", contact);
		put(context, "group", group);
		put(context, "groupIds", groupIds);
		put(context, "organizationIds", organizationIds);
		put(context, "privateLayouts", privateLayouts);
		put(context, "publicLayouts", publicLayouts);
		put(context, "roleIds", roleIds);
		put(context, "user", user);

		processTemplate(_tplUser, context);
	}

	public void insertWikiNode(WikiNode wikiNode) throws Exception {
		Map<String, Object> context = getContext();

		put(context, "wikiNode", wikiNode);

		processTemplate(_tplWikiNode, context);
	}

	public void insertWikiPage(WikiNode wikiNode, WikiPage wikiPage)
		throws Exception {

		Map<String, Object> context = getContext();

		put(context, "wikiNode", wikiNode);
		put(context, "wikiPage", wikiPage);

		processTemplate(_tplWikiPage, context);
	}

	protected void compressInsertSQL(String insertSQL) throws IOException {
		String tableName = insertSQL.substring(0, insertSQL.indexOf(' '));

		int pos = insertSQL.indexOf(" values ") + 8;

		String values = insertSQL.substring(pos, insertSQL.length() - 1);

		StringBundler sb = _insertSQLs.get(tableName);

		if (sb == null) {
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

		if (sb.index() >= _OPTIMIZE_BUFFER_SIZE) {
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

	protected void generateSQL(final CharPipe charPipe) {
		final Writer writer = charPipe.getWriter();

		Thread thread = new Thread() {

			@Override
			public void run() {
				try {
					_writerSampleSQL = new UnsyncTeeWriter(
						writer, new FileWriter(_outputDir +  "/sample.sql"));

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
				_writerMessageBoardsCSV = getWriter("message_boards.csv");
				_writerUsersCSV = getWriter("users.csv");
				_writerWikiCSV = getWriter("wiki.csv");

				Map<String, Object> context = getContext();

				processTemplate(_tplSample, context);

				_writerBlogsCSV.flush();
				_writerCompanyCSV.flush();
				_writerDocumentLibraryCSV.flush();
				_writerMessageBoardsCSV.flush();
				_writerUsersCSV.flush();
				_writerWikiCSV.flush();
			}

			protected Writer getWriter(String fileName) throws Exception {
				return new FileWriter(new File(_outputDir + "/" + fileName));
			}

		};

		thread.start();
	}

	protected Map<String, Object> getContext() {
		Map<String, Object> context = new HashMap<String, Object>();

		Company company = _dataFactory.getCompany();
		User defaultUser = _dataFactory.getDefaultUser();

		put(context, "companyId", company.getCompanyId());
		put(context, "counter", _counter);
		put(context, "dataFactory", _dataFactory);
		put(context, "dateUtil", DateUtil_IW.getInstance());
		put(context, "defaultUserId", defaultUser.getCompanyId());
		put(context, "dlFileEntrySize", _dlFileEntrySize);
		put(context, "maxBlogsEntryCommentCount", _maxBlogsEntryCommentCount);
		put(context, "maxBlogsEntryCount", _maxBlogsEntryCount);
		put(context, "maxDLFileEntryCount", _maxDLFileEntryCount);
		put(context, "maxDLFolderCount", _maxDLFolderCount);
		put(context, "maxDLFolderDepth", _maxDLFolderDepth);
		put(context, "maxGroupCount", _maxGroupCount);
		put(context, "maxMBCategoryCount", _maxMBCategoryCount);
		put(context, "maxMBMessageCount", _maxMBMessageCount);
		put(context, "maxMBThreadCount", _maxMBThreadCount);
		put(context, "maxUserCount", _maxUserCount);
		put(context, "maxUserToGroupCount", _maxUserToGroupCount);
		put(context, "maxWikiNodeCount", _maxWikiNodeCount);
		put(context, "maxWikiPageCommentCount", _maxWikiPageCommentCount);
		put(context, "maxWikiPageCount", _maxWikiPageCount);
		put(context, "portalUUIDUtil", PortalUUIDUtil.getPortalUUID());
		put(context, "sampleSQLBuilder", this);
		put(context, "stringUtil", StringUtil_IW.getInstance());
		put(context, "userScreenNameIncrementer", _userScreenNameIncrementer);
		put(context, "writerBlogsCSV", _writerBlogsCSV);
		put(context, "writerCompanyCSV", _writerCompanyCSV);
		put(context, "writerDocumentLibraryCSV", _writerDocumentLibraryCSV);
		put(context, "writerMessageBoardsCSV", _writerMessageBoardsCSV);
		put(context, "writerUsersCSV", _writerUsersCSV);
		put(context, "writerWikiCSV", _writerWikiCSV);

		return context;
	}

	protected File getInsertSQLFile(String tableName) {
		return new File(_tempDir, tableName);
	}

	protected void mergeSQL() throws IOException {
		FileOutputStream fileOutputStream = new FileOutputStream(
			_outputDir + "/sample-" + _dbType + ".sql");

		FileChannel fileChannel = fileOutputStream.getChannel();

		Set<Map.Entry<String, StringBundler>> insertSQLs =
			_insertSQLs.entrySet();

		for (Map.Entry<String, StringBundler> entry : insertSQLs) {
			String tableName = entry.getKey();

			String sql = _db.buildSQL(entry.getValue().toString());

			writeToInsertSQLFile(tableName, sql);

			Writer insertSQLWriter = _insertSQLWriters.remove(tableName);

			insertSQLWriter.write(";\n");

			insertSQLWriter.close();

			File insertSQLFile = getInsertSQLFile(tableName);

			FileInputStream insertSQLFileInputStream = new FileInputStream(
				insertSQLFile);

			FileChannel insertSQLFileChannel =
				insertSQLFileInputStream.getChannel();

			insertSQLFileChannel.transferTo(
				0, insertSQLFileChannel.size(), fileChannel);

			insertSQLFileChannel.close();
		}

		Writer writer = new OutputStreamWriter(fileOutputStream);

		for (String sql : _otherSQLs) {
			sql = _db.buildSQL(sql);

			writer.write(sql);
			writer.write(StringPool.NEW_LINE);
		}

		writer.close();
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

			writer = new FileWriter(file);

			_insertSQLWriters.put(tableName, writer);
		}

		writer.write(sql);
	}

	private static final int _OPTIMIZE_BUFFER_SIZE = 8192;

	private static final String _TPL_ROOT =
		"com/liferay/portal/tools/samplesqlbuilder/dependencies/";

	private SimpleCounter _counter;
	private DataFactory _dataFactory;
	private DB _db;
	private String _dbType;
	private int _dlFileEntrySize;
	private Map<String, StringBundler> _insertSQLs =
		new ConcurrentHashMap<String, StringBundler>();
	private Map<String, Writer> _insertSQLWriters =
		new ConcurrentHashMap<String, Writer>();
	private int _maxBlogsEntryCommentCount;
	private int _maxBlogsEntryCount;
	private int _maxDLFileEntryCount;
	private int _maxDLFolderCount;
	private int _maxDLFolderDepth;
	private int _maxGroupCount;
	private int _maxMBCategoryCount;
	private int _maxMBMessageCount;
	private int _maxMBThreadCount;
	private int _maxUserCount;
	private int _maxUserToGroupCount;
	private int _maxWikiNodeCount;
	private int _maxWikiPageCommentCount;
	private int _maxWikiPageCount;
	private List<String> _otherSQLs = new ArrayList<String>();
	private String _outputDir;
	private SimpleCounter _permissionCounter;
	private SimpleCounter _resourceCodeCounter;
	private SimpleCounter _resourceCounter;
	private boolean _securityEnabled;
	private SimpleCounter _socialActivityCounter;
	private File _tempDir;
	private String _tplAssetEntry = _TPL_ROOT + "asset_entry.ftl";
	private String _tplBlogsEntry = _TPL_ROOT + "blogs_entry.ftl";
	private String _tplBlogsStatsUser = _TPL_ROOT + "blogs_stats_user.ftl";
	private String _tplDLFileEntry = _TPL_ROOT + "dl_file_entry.ftl";
	private String _tplDLFolder = _TPL_ROOT + "dl_folder.ftl";
	private String _tplDLFolders = _TPL_ROOT + "dl_folders.ftl";
	private String _tplGroup = _TPL_ROOT + "group.ftl";
	private String _tplMBCategory = _TPL_ROOT + "mb_category.ftl";
	private String _tplMBDiscussion = _TPL_ROOT + "mb_discussion.ftl";
	private String _tplMBMessage = _TPL_ROOT + "mb_message.ftl";
	private String _tplMBStatsUser = _TPL_ROOT + "mb_stats_user.ftl";
	private String _tplMBThread = _TPL_ROOT + "mb_thread.ftl";
	private String _tplSample = _TPL_ROOT + "sample.ftl";
	private String _tplSecurity = _TPL_ROOT + "security.ftl";
	private String _tplSocialActivity = _TPL_ROOT + "social_activity.ftl";
	private String _tplUser = _TPL_ROOT + "user.ftl";
	private String _tplWikiNode = _TPL_ROOT + "wiki_node.ftl";
	private String _tplWikiPage = _TPL_ROOT + "wiki_page.ftl";
	private SimpleCounter _userScreenNameIncrementer;
	private Writer _writerBlogsCSV;
	private Writer _writerCompanyCSV;
	private Writer _writerDocumentLibraryCSV;
	private Writer _writerMessageBoardsCSV;
	private Writer _writerSampleSQL;
	private Writer _writerUsersCSV;
	private Writer _writerWikiCSV;

}