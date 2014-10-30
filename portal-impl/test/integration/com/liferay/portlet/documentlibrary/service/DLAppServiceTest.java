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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.log.ExpectedLog;
import com.liferay.portal.test.log.ExpectedLogs;
import com.liferay.portal.test.log.ExpectedType;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.UserTestUtil;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Enclosed.class)
public class DLAppServiceTest extends BaseDLAppTestCase {

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenAddingAFileEntry extends BaseDLAppTestCase {

		@Test
		public void assetTagsShouldBeOrdered() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			String[] assetTagNames = new String[] {"hello", "world"};

			serviceContext.setAssetTagNames(assetTagNames);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, CONTENT.getBytes(), serviceContext);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			AssertUtils.assertEqualsSorted(
				assetTagNames, assetEntry.getTagNames());
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder1()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_FILE_NAME);
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder2()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_FILE_NAME);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder3()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(),
				_STRIPPED_FILE_NAME, _FILE_NAME);
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateNameInFolder() throws Exception {
			addFileEntry(group.getGroupId(), parentFolder.getFolderId());
			addFileEntry(group.getGroupId(), parentFolder.getFolderId());
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_ADD);

			addFileEntry(group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(1, counter.get());
		}

		@Test
		public void shouldHaveDefaultVersion() throws Exception {
			String fileName = RandomTestUtil.randomString();

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName);

			Assert.assertEquals(
				"Version label incorrect after add", "1.0",
				fileEntry.getVersion());
		}

		@Test
		public void shouldInferValidMimeType() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.APPLICATION_OCTET_STREAM, fileName,
				StringPool.BLANK, StringPool.BLANK, CONTENT.getBytes(),
				serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
		}

		@Test
		public void shouldSucceedIfDuplicateNameInOtherFolder()
			throws Exception {

			addFileEntry(group.getGroupId(), parentFolder.getFolderId());
			addFileEntry(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		@ExpectedLogs(
			expectedLogs = {
				@ExpectedLog(
					expectedLog =
						"Deadlock found when trying to get lock; try " +
							"restarting transaction",
					expectedType = ExpectedType.EXACT),
				@ExpectedLog(
					expectedLog = "Duplicate entry ",
					expectedType = ExpectedType.PREFIX)
			},
			level = "ERROR", loggerClass = JDBCExceptionReporter.class
		)
		@Test
		public void shouldSucceedWithConcurrentAccess() throws Exception {
			_users = new User[ServiceTestUtil.THREAD_COUNT];

			for (int i = 0; i < ServiceTestUtil.THREAD_COUNT; i++) {
				User user = UserTestUtil.addUser(
					"DLAppServiceTest" + (i + 1), group.getGroupId());

				_users[i] = user;
			}

			DoAsUserThread[] doAsUserThreads =
				new DoAsUserThread[_users.length];

			_fileEntryIds = new long[_users.length];

			int successCount = 0;

			for (int i = 0; i < doAsUserThreads.length; i++) {
				doAsUserThreads[i] = new AddFileEntryThread(
					_users[i].getUserId(), i);
			}

			successCount = runUserThreads(doAsUserThreads);

			Assert.assertEquals(
				"Only " + successCount + " out of " + _users.length +
					" threads added successfully",
				_users.length, successCount);

			for (int i = 0; i < doAsUserThreads.length; i++) {
				doAsUserThreads[i] = new GetFileEntryThread(
					_users[i].getUserId(), i);
			}

			successCount = runUserThreads(doAsUserThreads);

			Assert.assertEquals(
				"Only " + successCount + " out of " + _users.length +
					" threads retrieved successfully",
				_users.length, successCount);
		}

		@Test
		public void shouldSucceedWithNullBytes() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, (byte[])null, serviceContext);
		}

		@Test
		public void shouldSucceedWithNullFile() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, (File)null, serviceContext);
		}

		@Test
		public void shouldSucceedWithNullInputStream() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, null, 0, serviceContext);
		}

		private long[] _fileEntryIds;

		@DeleteAfterTestRun
		private User[] _users;

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
						group.getGroupId(), parentFolder.getFolderId(),
						"Test-" + _index + ".txt");

					_fileEntryIds[_index] = fileEntry.getFileEntryId();

					if (_log.isDebugEnabled()) {
						_log.debug("Added file " + _index);
					}

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
						if (_log.isDebugEnabled()) {
							_log.debug("Retrieved file " + _index);
						}

						_success = true;
					}
				}
				catch (Exception e) {
					_log.error("Unable to get file " + _index, e);
				}
			}

			private int _index;
			private boolean _success;

		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenAddingAFolder extends BaseDLAppTestCase {

		@Test
		public void shouldAddAssetEntry() throws PortalException {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				DLFolderConstants.getClassName(), folder.getFolderId());

			Assert.assertNotNull(assetEntry);
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_ADD);

			DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(1, counter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenCheckingInAFileEntry extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getRepositoryId(),
				parentFolder.getFolderId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);

			DLAppServiceUtil.checkInFileEntry(
				fileEntry.getFileEntryId(), false,
				RandomTestUtil.randomString(), serviceContext);

			Assert.assertEquals(2, counter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenCheckingOutAFileEntry extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getRepositoryId(),
				parentFolder.getFolderId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);

			Assert.assertEquals(1, counter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenCopyingAFolder extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_ADD);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

			DLAppServiceUtil.addFolder(
				group.getGroupId(), folder.getFolderId(),
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);

			DLAppServiceUtil.copyFolder(
				folder.getRepositoryId(), folder.getFolderId(),
				parentFolder.getParentFolderId(), folder.getName(),
				folder.getDescription(), serviceContext);

			Assert.assertEquals(4, counter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenDeletingAFileEntry extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_DELETE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

			Assert.assertEquals(1, counter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenDeletingAFolder extends BaseDLAppTestCase {

		@Test
		public void shouldDeleteImplicitlyTrashedChildFolder()
			throws Exception {

			int initialFoldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppTestUtil.addFolder(group.getGroupId(), folder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			int foldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(initialFoldersCount, foldersCount);
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_DELETE);

			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			Assert.assertEquals(1, counter.get());
		}

		@Test
		public void shouldSkipExplicitlyTrashedChildFolder() throws Exception {
			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			Folder subfolder = DLAppTestUtil.addFolder(
				group.getGroupId(), folder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(subfolder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			DLAppServiceUtil.getFolder(subfolder.getFolderId());
		}
	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenDeletingAFolderByName extends BaseDLAppTestCase {

		@Test
		public void shouldDeleteImplicitlyTrashedChildFolder()
			throws Exception {

			int initialFoldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppTestUtil.addFolder(group.getGroupId(), folder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

			folder = DLAppServiceUtil.getFolder(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(
				folder.getRepositoryId(), folder.getParentFolderId(),
				folder.getName());

			int foldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(initialFoldersCount, foldersCount);
		}

		@Test
		public void shouldSkipExplicitlyTrashedChildFolder() throws Exception {
			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			Folder subfolder = DLAppTestUtil.addFolder(
				group.getGroupId(), folder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(subfolder.getFolderId());

			DLAppServiceUtil.moveFolderToTrash(folder.getFolderId());

			folder = DLAppServiceUtil.getFolder(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(
				folder.getRepositoryId(), folder.getParentFolderId(),
				folder.getName());

			DLAppServiceUtil.getFolder(subfolder.getFolderId());
		}
	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenMovingAFileEntry extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger addCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_ADD);

			AtomicInteger deleteCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_DELETE);

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString());

			DLAppServiceUtil.moveFileEntry(
				fileEntry.getFileEntryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceContextTestUtil.getServiceContext());

			Assert.assertEquals(2, addCounter.get());
			Assert.assertEquals(1, deleteCounter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenMovingAFolder extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger addCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_ADD);

			AtomicInteger deleteCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_DELETE);

			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.moveFolder(
				folder.getFolderId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				ServiceContextTestUtil.getServiceContext());

			Assert.assertEquals(2, addCounter.get());
			Assert.assertEquals(1, deleteCounter.get());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenSearchingFileEntries extends BaseDLAppTestCase {

		@Test
		public void shouldFindFileEntryByAssetTagName() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			String[] assetTagNames = new String[] {"hello", "world"};

			serviceContext.setAssetTagNames(assetTagNames);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, CONTENT.getBytes(), serviceContext);

			search(fileEntry, false, "hello", true);
			search(fileEntry, false, "world", true);
			search(fileEntry, false, "liferay", false);
		}

		@Ignore
		@Test
		public void shouldFindFileEntryByAssetTagNameAfterUpdate()
			throws Exception {

			long folderId = parentFolder.getFolderId();
			String fileName = RandomTestUtil.randomString();
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;
			byte[] bytes = CONTENT.getBytes();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			String[] assetTagNames = new String[] {"hello", "world"};

			serviceContext.setAssetTagNames(assetTagNames);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), folderId, fileName, ContentTypes.TEXT_PLAIN,
				fileName, description, changeLog, bytes, serviceContext);

			assetTagNames = new String[] {"hello", "world", "liferay"};

			serviceContext.setAssetTagNames(assetTagNames);

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				fileName, description, changeLog, false, bytes, serviceContext);

			search(fileEntry, false, "hello", true);
			search(fileEntry, false, "world", true);
			search(fileEntry, false, "liferay", true);
		}

		@Ignore
		@Test
		public void shouldFindFileEntryInRootFolder() throws Exception {
			searchFile(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
		}

		@Ignore
		@Test
		public void shouldFindFileEntryInSubfolder() throws Exception {
			searchFile(group.getGroupId(), parentFolder.getFolderId());
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenUpdatingAFileEntry extends BaseDLAppTestCase {

		@Test
		public void assetTagsShouldBeOrdered() throws Exception {
			String fileName = RandomTestUtil.randomString();
			byte[] bytes = CONTENT.getBytes();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			String[] assetTagNames = new String[] {"hello", "world"};

			serviceContext.setAssetTagNames(assetTagNames);

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, bytes, serviceContext);

			assetTagNames = new String[] {"hello", "world", "liferay"};

			serviceContext.setAssetTagNames(assetTagNames);

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				fileName, StringPool.BLANK, StringPool.BLANK, false, bytes,
				serviceContext);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			AssertUtils.assertEqualsSorted(
				assetTagNames, assetEntry.getTagNames());
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			updateFileEntry(
				fileEntry.getGroupId(), fileEntry.getFileEntryId(),
				fileEntry.getTitle(), true);

			Assert.assertEquals(2, counter.get());
		}

		@Test
		public void shouldIncrementMajorVersion() throws Exception {
			String fileName = "TestVersion.txt";

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName);

			fileEntry = updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(), fileName, true);

			fileEntry = updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(), fileName, true);

			Assert.assertEquals(
				"Version label incorrect after major update", "3.0",
				fileEntry.getVersion());
		}

		@Test
		public void shouldIncrementMinorVersion() throws Exception {
			String fileName = "TestVersion.txt";

			FileEntry fileEntry = DLAppTestUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName);

			fileEntry = updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(), fileName,
				false);

			fileEntry = updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(), fileName,
				false);

			Assert.assertEquals(
				"Version label incorrect after major update", "1.2",
				fileEntry.getVersion());
		}

		@Test
		public void shouldNotChangeMimeTypeIfNullContent() throws Exception {
			String fileName = RandomTestUtil.randomString();

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			byte[] bytes = CONTENT.getBytes();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			fileEntry = DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, null, fileName,
				StringPool.BLANK, StringPool.BLANK, true, bytes,
				serviceContext);

			Assert.assertEquals(
				ContentTypes.TEXT_PLAIN, fileEntry.getMimeType());
		}

		@Test
		public void shouldSucceedForRootFolder() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.updateFolder(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
		}

		@Test
		public void shouldSucceedWithNullBytes() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				fileName, StringPool.BLANK, StringPool.BLANK, true,
				(byte[])null, serviceContext);
		}

		@Test
		public void shouldSucceedWithNullFile() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				fileName, StringPool.BLANK, StringPool.BLANK, true, (File)null,
				serviceContext);
		}

		@Test
		public void shouldSucceedWithNullInputStream() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.updateFileEntry(
				fileEntry.getFileEntryId(), fileName, ContentTypes.TEXT_PLAIN,
				fileName, StringPool.BLANK, StringPool.BLANK, true, null, 0,
				serviceContext);
		}

	}

	@ExecutionTestListeners(
		listeners = {
			MainServletExecutionTestListener.class,
			SynchronousDestinationExecutionTestListener.class
		})
	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	@Sync
	public static class WhenUpdatingAFolder extends BaseDLAppTestCase {

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			Folder folder = DLAppTestUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.updateFolder(
				folder.getFolderId(), folder.getName(), folder.getDescription(),
				ServiceContextTestUtil.getServiceContext());

			Assert.assertEquals(1, counter.get());
		}

		@Test
		public void shouldSucceedForDefaultParentFolder() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.updateFolder(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
		}

	}

	protected static FileEntry addFileEntry(long groupId, long folderId)
		throws Exception {

		return addFileEntry(groupId, folderId, _FILE_NAME);
	}

	protected static FileEntry addFileEntry(
			long groupId, long folderId, String fileName)
		throws Exception {

		return addFileEntry(groupId, folderId, fileName, fileName);
	}

	protected static FileEntry addFileEntry(
			long groupId, long folderId, String fileName, String title)
		throws Exception {

		return DLAppTestUtil.addFileEntry(groupId, folderId, fileName, title);
	}

	protected static AtomicInteger registerDLSyncEventProcessorMessageListener(
		final String targetEvent) {

		final AtomicInteger counter = new AtomicInteger();

		MessageBusUtil.registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
			new MessageListener() {

				@Override
				public void receive(Message message) {
					Object event = message.get("event");

					if (targetEvent.equals(event)) {
						counter.incrementAndGet();
					}
				}

			});

		return counter;
	}

	protected static int runUserThreads(DoAsUserThread[] doAsUserThreads)
		throws Exception {

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

		return successCount;
	}

	protected static void search(
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

	protected static void searchFile(long groupId, long folderId)
		throws Exception {

		FileEntry fileEntry = addFileEntry(groupId, folderId);

		boolean rootFolder = false;

		if (folderId == DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) {
			rootFolder = true;
		}

		search(fileEntry, rootFolder, "title", true);
		search(fileEntry, rootFolder, "content", true);

		DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());
	}

	protected static FileEntry updateFileEntry(
			long groupId, long fileEntryId, String fileName,
			boolean majorVersion)
		throws Exception {

		return DLAppTestUtil.updateFileEntry(
			groupId, fileEntryId, fileName, fileName, majorVersion, true, true);
	}

	private static final String _FILE_NAME = "Title.txt";

	private static final String _STRIPPED_FILE_NAME = "Title";

	private static Log _log = LogFactoryUtil.getLog(DLAppServiceTest.class);

}