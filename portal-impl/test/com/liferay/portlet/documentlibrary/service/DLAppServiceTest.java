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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLAppServiceTest extends BaseServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_userIds = new long[THREAD_COUNT];

		for (int i = 0 ; i < THREAD_COUNT; i++) {
			User user = addUser(
				"DLAppServiceTest" + (i + 1), false,
				new long[] {TestPropsValues.getGroupId()});

			_userIds[i] = user.getUserId();
		}

		String name = "Test Folder";
		String description = "This is a test folder.";

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			DLAppServiceUtil.deleteFolder(
				TestPropsValues.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name);
		}
		catch (NoSuchFolderException nsfe) {
		}

		_folder = DLAppServiceUtil.addFolder(
			TestPropsValues.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name, description,
			serviceContext);
	}

	@Override
	public void tearDown() throws Exception {
		if (_fileEntry != null) {
			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		if (_folder != null) {
			DLAppServiceUtil.deleteFolder(_folder.getFolderId());
		}

		super.tearDown();
	}

	public void testAddFileEntriesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[THREAD_COUNT];

		_fileEntryIds = new long[THREAD_COUNT];

		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < doAsUserThreads.length; j++) {
				if (i == 0) {
					doAsUserThreads[j] = new AddFileEntryThread(_userIds[j], j);
				}
				else {
					doAsUserThreads[j] = new GetFileEntryThread(_userIds[j], j);
				}
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.start();
			}

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				doAsUserThread.join();
			}

			int successCount = 0;

			for (DoAsUserThread doAsUserThread : doAsUserThreads) {
				if (doAsUserThread.isSuccess()) {
					successCount++;
				}
			}

			String message = "Only " + successCount + " out of " + THREAD_COUNT;

			if (i == 0) {
				message += " threads added file entries successfully";
			}
			else {
				message += " threads retrieved file entries successfully";
			}

			assertTrue(message, successCount == THREAD_COUNT);
		}
	}

	public void testAddFileEntryWithDuplicateName() throws Exception {
		addFileEntry(false);

		try {
			addFileEntry(false);

			fail("Able to add two files of the same name");
		}
		catch (DuplicateFileException dfe) {
		}

		try {
			addFileEntry(true);
		}
		catch (DuplicateFileException dfe) {
			fail(
				"Unable to add two files of the same name in different " +
					"folders");
		}
	}

	public void testAddNullFileEntry() throws Exception {
		long folderId = _folder.getFolderId();

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		try {
			String name = "Bytes-null.txt";
			byte[] bytes = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				TestPropsValues.getGroupId(), folderId, name,
				ContentTypes.TEXT_PLAIN, name, description, changeLog, bytes,
				serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, bytes, serviceContext);

			String newName = "Bytes-changed.txt";

			bytes = _CONTENT.getBytes();

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), newName, ContentTypes.TEXT_PLAIN,
				newName, description, changeLog, true, bytes, serviceContext);
		}
		catch (Exception e) {
			fail(
				"Unable to pass null byte[] " +
					StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "File-null.txt";
			File file = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				TestPropsValues.getGroupId(), folderId, name,
				ContentTypes.TEXT_PLAIN, name, description, changeLog, file,
				serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, file, serviceContext);

			try {
				String newName = "File-changed.txt";

				file = FileUtil.createTempFile(_CONTENT.getBytes());

				DLAppServiceUtil.updateFileEntry(
					fileEntry.getFileEntryId(), newName,
					ContentTypes.TEXT_PLAIN, newName, description, changeLog,
					true, file, serviceContext);
			}
			finally {
				FileUtil.delete(file);
			}
		}
		catch (Exception e) {
			fail(
				"Unable to pass null File " +
					StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "IS-null.txt";
			InputStream is = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				TestPropsValues.getGroupId(), folderId, name,
				ContentTypes.TEXT_PLAIN, name, description, changeLog, is, 0,
				serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, is, 0, serviceContext);

			try {
				String newName = "IS-changed.txt";

				is = new ByteArrayInputStream(_CONTENT.getBytes());

				DLAppServiceUtil.updateFileEntry(
					fileEntry.getFileEntryId(), newName,
					ContentTypes.TEXT_PLAIN, newName, description, changeLog,
					true, is, 0, serviceContext);
			}
			finally {
				if (is != null) {
					is.close();
				}
			}
		}
		catch (Exception e) {
			fail(
				"Unable to pass null InputStream " +
					StackTraceUtil.getStackTrace(e));
		}
	}

	public void testSearchFileInRootFolder() throws Exception {
		testSearchFile(true);
	}

	public void testSearchFileInSubFolder() throws Exception {
		testSearchFile(false);
	}

	protected void addFileEntry(boolean rootFolder) throws Exception {
		_fileEntry = addFileEntry(rootFolder, "Title.txt");
	}

	protected FileEntry addFileEntry(boolean rootFolder, String fileName)
		throws Exception {

		long folderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			folderId = _folder.getFolderId();
		}

		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		byte[] bytes = _CONTENT.getBytes();

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		return DLAppServiceUtil.addFileEntry(
			TestPropsValues.getGroupId(), folderId, fileName,
			ContentTypes.TEXT_PLAIN, fileName, description, changeLog, bytes,
			serviceContext);
	}

	protected void search(boolean rootFolder, String keywords)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(_fileEntry.getCompanyId());
		searchContext.setFolderIds(new long[] {_fileEntry.getFolderId()});
		searchContext.setGroupIds(new long[] {_fileEntry.getRepositoryId()});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(DLFileEntry.class);

		Hits hits = indexer.search(searchContext);

		List<Document> documents = hits.toList();

		boolean found = false;

		for (Document document : documents) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (fileEntryId == _fileEntry.getFileEntryId()) {
				found = true;

				break;
			}
		}

		String message = "Search engine could not find ";

		if (rootFolder) {
			message += "root file entry by " + keywords;
		}
		else {
			message += "file entry by " + keywords;
		}

		message += " using query " + hits.getQuery();

		assertTrue(message, found);
	}

	protected void testSearchFile(boolean rootFolder) throws Exception {
		addFileEntry(rootFolder);

		Thread.sleep(1000);

		search(rootFolder, "title");
		search(rootFolder, "content");
	}

	private static final String _CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceTest.class);

	private FileEntry _fileEntry;
	private long[] _fileEntryIds;
	private Folder _folder;
	private long[] _userIds;

	private class AddFileEntryThread extends DoAsUserThread {

		public AddFileEntryThread(long userId, int index) {
			super(userId);

			_index = index;
		}

		@Override
		public boolean isSuccess() {
			return _success;
		}

		@Override
		protected void doRun() throws Exception {
			try {
				FileEntry fileEntry = addFileEntry(
					false, "Test-" + _index + ".txt");

				_fileEntryIds[_index] = fileEntry.getFileEntryId();

				_log.debug("Added file " + _index);

				_success = true;
			}
			catch (Exception e) {
				_log.error("Unable to add file " + _index, e);
			}
		}

		private int _index;
		private boolean _success;

	}

	private class GetFileEntryThread extends DoAsUserThread {

		public GetFileEntryThread(long userId, int index) {
			super(userId);

			_index = index;
		}

		@Override
		public boolean isSuccess() {
			return _success;
		}

		@Override
		protected void doRun() throws Exception {
			try {
				FileEntry fileEntry = DLAppServiceUtil.getFileEntry(
					_fileEntryIds[_index]);

				InputStream is = fileEntry.getContentStream();

				String content = StringUtil.read(is);

				if (_CONTENT.equals(content)) {
					_log.debug("Retrieved file " + _index);

					_success = true;
				}
			}
			catch (Exception e) {
				_log.error("Unable to add file " + _index, e);
			}
		}

		private int _index;
		private boolean _success;

	}

}