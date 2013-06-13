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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.UserTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLAppServiceTest extends BaseDLAppTestCase {

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		_fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false,
			"Test DLAppService.txt");

		_userIds = new long[ServiceTestUtil.THREAD_COUNT];

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			User user = UserTestUtil.addUser(
				"DLAppServiceTest" + (i + 1), group.getGroupId());

			_userIds[i] = user.getUserId();
		}
	}

	@After
	@Override
	public void tearDown() throws Exception {
		if (_fileEntry != null) {
			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}

		super.tearDown();

		for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
			UserLocalServiceUtil.deleteUser(_userIds[i]);
		}
	}

	@Test
	public void testAddFileEntriesConcurrently() throws Exception {
		DoAsUserThread[] doAsUserThreads = new DoAsUserThread[_userIds.length];

		_fileEntryIds = new long[_userIds.length];

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

			String message =
				"Only " + successCount + " out of " + _userIds.length;

			if (i == 0) {
				message += " threads added file entries successfully";
			}
			else {
				message += " threads retrieved file entries successfully";
			}

			Assert.assertTrue(message, successCount == _userIds.length);
		}
	}

	@Test
	public void testAddFileEntryWithDuplicateName() throws Exception {
		addFileEntry(false);

		try {
			addFileEntry(false);

			Assert.fail("Able to add two files of the same name");
		}
		catch (DuplicateFileException dfe) {
		}

		try {
			addFileEntry(true);

			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());
		}
		catch (DuplicateFileException dfe) {
			Assert.fail(
				"Unable to add two files of the same name in different " +
					"folders");
		}

		_fileEntry = null;
	}

	@Test
	public void testAddFileEntryWithInvalidMimeType() throws Exception {
		long folderId = parentFolder.getFolderId();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		try {
			String name = "InvalidMime.txt";
			byte[] bytes = CONTENT.getBytes();

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name,
				ContentTypes.APPLICATION_OCTET_STREAM, name, description,
				changeLog, bytes, serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());

			name = "InvalidMime";

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, null, name, description,
				changeLog, true, bytes, serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to add file with invalid mime type " +
					StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testAddNullFileEntry() throws Exception {
		long folderId = parentFolder.getFolderId();
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		try {
			String name = "Bytes-null.txt";
			byte[] bytes = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, bytes, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, bytes, serviceContext);

			String newName = "Bytes-changed.txt";

			bytes = CONTENT.getBytes();

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), newName, ContentTypes.TEXT_PLAIN,
				newName, description, changeLog, true, bytes, serviceContext);
		}
		catch (Exception e) {
			Assert.fail(
				"Unable to pass null byte[] " +
					StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "File-null.txt";
			File file = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, file, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, file, serviceContext);

			try {
				String newName = "File-changed.txt";

				file = FileUtil.createTempFile(CONTENT.getBytes());

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
			Assert.fail(
				"Unable to pass null File " + StackTraceUtil.getStackTrace(e));
		}

		try {
			String name = "IS-null.txt";
			InputStream is = null;

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN,
				name, description, changeLog, is, 0, serviceContext);

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
				description, changeLog, true, is, 0, serviceContext);

			try {
				String newName = "IS-changed.txt";

				is = new ByteArrayInputStream(CONTENT.getBytes());

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
			Assert.fail(
				"Unable to pass null InputStream " +
					StackTraceUtil.getStackTrace(e));
		}
	}

	@Test
	public void testAssetTags() throws Exception {
		long folderId = parentFolder.getFolderId();
		String name = "TestTags.txt";
		String description = StringPool.BLANK;
		String changeLog = StringPool.BLANK;
		byte[] bytes = CONTENT.getBytes();

		ServiceContext serviceContext = ServiceTestUtil.getServiceContext(
			group.getGroupId());

		String[] assetTagNames = new String[] {"hello", "world"};

		serviceContext.setAssetTagNames(assetTagNames);

		FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
			group.getGroupId(), folderId, name, ContentTypes.TEXT_PLAIN, name,
			description, changeLog, bytes, serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());

		_fileEntry = fileEntry;

		search(_fileEntry, false, "hello", true);
		search(_fileEntry, false, "world", true);
		search(_fileEntry, false, "liferay", false);

		assetTagNames = new String[] {"hello", "world", "liferay"};

		serviceContext.setAssetTagNames(assetTagNames);

		fileEntry = DLAppServiceUtil.updateFileEntry(
			fileEntry.getFileEntryId(), name, ContentTypes.TEXT_PLAIN, name,
			description, changeLog, false, bytes, serviceContext);

		assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		AssertUtils.assertEqualsSorted(assetTagNames, assetEntry.getTagNames());

		_fileEntry = fileEntry;

		search(_fileEntry, false, "hello", true);
		search(_fileEntry, false, "world", true);
		search(_fileEntry, false, "liferay", true);

		DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());

		_fileEntry = null;
	}

	@Test
	public void testSearchFileInRootFolder() throws Exception {
		searchFile(true);
	}

	@Test
	public void testSearchFileInSubFolder() throws Exception {
		searchFile(false);
	}

	@Test
	public void testVersionLabel() throws Exception {
		String fileName = "TestVersion.txt";

		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), false, fileName);

		Assert.assertEquals(
			"Version label incorrect after add", "1.0", fileEntry.getVersion());

		fileEntry = updateFileEntry(
			fileEntry.getFileEntryId(), fileName, false);

		Assert.assertEquals(
			"Version label incorrect after minor update", "1.1",
			fileEntry.getVersion());

		fileEntry = updateFileEntry(fileEntry.getFileEntryId(), fileName, true);

		Assert.assertEquals(
			"Version label incorrect after major update", "2.0",
			fileEntry.getVersion());
	}

	protected FileEntry addFileEntry(boolean rootFolder) throws Exception {
		return DLAppTestUtil.addFileEntry(
			group.getGroupId(), parentFolder.getFolderId(), rootFolder,
			"Title.txt");
	}

	protected void search(
			FileEntry fileEntry, boolean rootFolder, String keywords,
			boolean assertTrue)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setAttribute("paginationType", "regular");
		searchContext.setCompanyId(fileEntry.getCompanyId());
		searchContext.setFolderIds(new long[] {fileEntry.getFolderId()});
		searchContext.setGroupIds(new long[] {fileEntry.getRepositoryId()});
		searchContext.setKeywords(keywords);

		QueryConfig queryConfig = new QueryConfig();

		queryConfig.setHighlightEnabled(false);
		queryConfig.setScoreEnabled(false);

		searchContext.setQueryConfig(queryConfig);

		Indexer indexer = IndexerRegistryUtil.getIndexer(
			DLFileEntryConstants.getClassName());

		Hits hits = indexer.search(searchContext);

		List<Document> documents = hits.toList();

		boolean found = false;

		for (Document document : documents) {
			long fileEntryId = GetterUtil.getLong(
				document.get(Field.ENTRY_CLASS_PK));

			if (fileEntryId == fileEntry.getFileEntryId()) {
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

		if (assertTrue) {
			Assert.assertTrue(message, found);
		}
		else {
			Assert.assertFalse(message, found);
		}
	}

	protected void searchFile(boolean rootFolder) throws Exception {
		FileEntry fileEntry = addFileEntry(rootFolder);

		search(fileEntry, rootFolder, "title", true);
		search(fileEntry, rootFolder, "content", true);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected FileEntry updateFileEntry(
			long fileEntryId, String fileName, boolean majorVersion)
		throws Exception {

		return DLAppTestUtil.updateFileEntry(
			group.getGroupId(), fileEntryId, fileName, fileName, majorVersion);
	}

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceTest.class);

	private FileEntry _fileEntry;
	private long[] _fileEntryIds;
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
				FileEntry fileEntry = DLAppTestUtil.addFileEntry(
					group.getGroupId(), parentFolder.getFolderId(), false,
					"Test-" + _index + ".txt");

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

				if (CONTENT.equals(content)) {
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