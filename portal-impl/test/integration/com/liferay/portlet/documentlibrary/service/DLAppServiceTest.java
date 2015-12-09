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

import com.liferay.portal.kernel.comment.CommentManagerUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.Indexer;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.QueryConfig;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.test.WorkflowHandlerInvocationCounter;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.DoAsUserThread;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.test.ServiceTestUtil;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.ExpectedLog;
import com.liferay.portal.test.rule.ExpectedLogs;
import com.liferay.portal.test.rule.ExpectedType;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.PrefsPropsTemporarySwapper;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.DuplicateFileEntryException;
import com.liferay.portlet.documentlibrary.FileExtensionException;
import com.liferay.portlet.documentlibrary.FileNameException;
import com.liferay.portlet.documentlibrary.FileSizeException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;

import java.io.File;
import java.io.InputStream;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.hibernate.util.JDBCExceptionReporter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@RunWith(Enclosed.class)
public class DLAppServiceTest extends BaseDLAppTestCase {

	@Sync
	public static class WhenAddingAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void assetTagsShouldBeOrdered() throws Exception {
			String fileName = RandomTestUtil.randomString();

			String[] assetTagNames = new String[] {"hello", "world"};

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				fileName, assetTagNames);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			AssertUtils.assertEqualsSorted(
				assetTagNames, assetEntry.getTagNames());
		}

		@Test
		public void shouldCallWorkflowHandler() throws Exception {
			try (WorkflowHandlerInvocationCounter<DLFileEntry>
					workflowHandlerInvocationCounter =
						new WorkflowHandlerInvocationCounter<>(
							DLFileEntryConstants.getClassName())) {

				addFileEntry(group.getGroupId(), parentFolder.getFolderId());

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));
			}
		}

		@Test
		public void shouldCreateDiscussion() throws Exception {
			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			boolean hasDiscussion = CommentManagerUtil.hasDiscussion(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			Assert.assertEquals(
				PropsValues.DL_FILE_ENTRY_COMMENTS_ENABLED, hasDiscussion);
		}

		@Test(expected = DuplicateFileEntryException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder1()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME, null);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_FILE_NAME, null);
		}

		@Test(expected = DuplicateFileEntryException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder2()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_FILE_NAME, null);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME, null);
		}

		@Test(expected = DuplicateFileEntryException.class)
		public void shouldFailIfDuplicateNameAndExtensionInFolder3()
			throws Exception {

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME, null);
			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(),
				_STRIPPED_FILE_NAME, _FILE_NAME, null);
		}

		@Test(expected = DuplicateFileEntryException.class)
		public void shouldFailIfDuplicateNameInFolder() throws Exception {
			addFileEntry(group.getGroupId(), parentFolder.getFolderId());
			addFileEntry(group.getGroupId(), parentFolder.getFolderId());
		}

		@Test(expected = FileSizeException.class)
		public void shouldFailIfSizeLimitExceeded() throws Exception {
			try (PrefsPropsTemporarySwapper prefsPropsReplacement =
					new PrefsPropsTemporarySwapper(
						PropsKeys.DL_FILE_MAX_SIZE, 1L)) {

				String fileName = RandomTestUtil.randomString();

				addFileEntry(
					group.getGroupId(), parentFolder.getFolderId(), fileName);
			}
		}

		@Test(expected = FileNameException.class)
		public void shouldFailIfSourceFileNameContainsBlacklistedChar()
			throws Exception {

			int i =
				RandomTestUtil.randomInt() %
					PropsValues.DL_CHAR_BLACKLIST.length;

			String blackListedChar = PropsValues.DL_CHAR_BLACKLIST[i];

			String sourceFileName =
				RandomTestUtil.randomString() + blackListedChar +
					RandomTestUtil.randomString();

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), sourceFileName);
		}

		@Test(expected = FileNameException.class)
		public void shouldFailIfSourceFileNameEndsWithBlacklistedChar()
			throws Exception {

			int i =
				RandomTestUtil.randomInt() %
					PropsValues.DL_CHAR_LAST_BLACKLIST.length;

			String blackListedChar = PropsValues.DL_CHAR_LAST_BLACKLIST[i];

			String sourceFileName =
				RandomTestUtil.randomString() + blackListedChar;

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), sourceFileName);
		}

		@Test(expected = FileExtensionException.class)
		public void shouldFailIfSourceFileNameExtensionNotSupported()
			throws Exception {

			try (PrefsPropsTemporarySwapper prefsPropsTemporarySwapper =
					new PrefsPropsTemporarySwapper(
						PropsKeys.DL_FILE_EXTENSIONS, "")) {

				String sourceFileName = "file.jpg";

				addFileEntry(
					group.getGroupId(), parentFolder.getFolderId(),
					sourceFileName);
			}
		}

		@Test(expected = FileNameException.class)
		public void shouldFailIfSourceFileNameIsBlacklisted() throws Exception {
			int i =
				RandomTestUtil.randomInt() %
					PropsValues.DL_NAME_BLACKLIST.length;

			String blackListedName = PropsValues.DL_NAME_BLACKLIST[i];

			addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(),
				blackListedName);
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

			FileEntry fileEntry = addFileEntry(
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
					expectedType = ExpectedType.EXACT
				),
				@ExpectedLog(
					expectedLog = "Duplicate entry ",
					expectedType = ExpectedType.PREFIX
				)
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
					FileEntry fileEntry = addFileEntry(
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

	@Sync
	public static class WhenAddingAFolder extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

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

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			Assert.assertEquals(1, counter.get());
		}

	}

	@Sync
	public static class WhenCheckingInAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldCallWorkflowHandler() throws Exception {
			try (WorkflowHandlerInvocationCounter<FileEntry>
					workflowHandlerInvocationCounter =
						new WorkflowHandlerInvocationCounter<>(
							DLFileEntryConstants.getClassName())) {

				FileEntry fileEntry = addFileEntry(
					group.getGroupId(), parentFolder.getFolderId());

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId());

				DLAppServiceUtil.checkOutFileEntry(
					fileEntry.getFileEntryId(), serviceContext);

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				updateFileEntry(
					group.getGroupId(), fileEntry.getFileEntryId(),
					RandomTestUtil.randomString(), true);

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				DLAppServiceUtil.checkInFileEntry(
					fileEntry.getFileEntryId(), false,
					RandomTestUtil.randomString(), serviceContext);

				Assert.assertEquals(
					2,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));
			}
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

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

	@Sync
	public static class WhenCheckingOutAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.checkOutFileEntry(
				fileEntry.getFileEntryId(), serviceContext);

			Assert.assertEquals(1, counter.get());
		}

	}

	@Sync
	public static class WhenCopyingAFolder extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldCallWorkflowHandler() throws Exception {
			try (WorkflowHandlerInvocationCounter<DLFileEntry>
					workflowHandlerInvocationCounter =
						new WorkflowHandlerInvocationCounter<>(
							DLFileEntryConstants.getClassName())) {

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId());

				Folder folder = DLAppServiceUtil.addFolder(
					group.getGroupId(), parentFolder.getFolderId(),
					RandomTestUtil.randomString(), StringPool.BLANK,
					serviceContext);

				addFileEntry(group.getGroupId(), folder.getFolderId());

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				DLAppServiceUtil.copyFolder(
					folder.getRepositoryId(), folder.getFolderId(),
					parentFolder.getParentFolderId(), folder.getName(),
					folder.getDescription(), serviceContext);

				Assert.assertEquals(
					2,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));
			}
		}

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

	@Sync
	public static class WhenDeletingAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldDeleteDiscussion() throws Exception {
			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			DLAppServiceUtil.deleteFileEntry(fileEntry.getFileEntryId());

			Assert.assertFalse(
				CommentManagerUtil.hasDiscussion(
					DLFileEntryConstants.getClassName(),
					fileEntry.getFileEntryId()));
		}

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

	@Sync
	public static class WhenDeletingAFolder extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldDeleteImplicitlyTrashedChildFolder()
			throws Exception {

			int initialFoldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLAppServiceUtil.addFolder(
				group.getGroupId(), folder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLTrashServiceUtil.moveFolderToTrash(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			int foldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			Assert.assertEquals(initialFoldersCount, foldersCount);
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_DELETE);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			Assert.assertEquals(1, counter.get());
		}

		@Test
		public void shouldSkipExplicitlyTrashedChildFolder() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			Folder subfolder = DLAppServiceUtil.addFolder(
				group.getGroupId(), folder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLTrashServiceUtil.moveFolderToTrash(subfolder.getFolderId());

			DLTrashServiceUtil.moveFolderToTrash(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(folder.getFolderId());

			DLAppServiceUtil.getFolder(subfolder.getFolderId());
		}

	}

	@Sync
	public static class WhenDeletingAFolderByName extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldDeleteImplicitlyTrashedChildFolder()
			throws Exception {

			int initialFoldersCount = DLAppServiceUtil.getFoldersCount(
				group.getGroupId(), parentFolder.getFolderId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLAppServiceUtil.addFolder(
				group.getGroupId(), folder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLTrashServiceUtil.moveFolderToTrash(folder.getFolderId());

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
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			Folder subfolder = DLAppServiceUtil.addFolder(
				group.getGroupId(), folder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLTrashServiceUtil.moveFolderToTrash(subfolder.getFolderId());

			DLTrashServiceUtil.moveFolderToTrash(folder.getFolderId());

			folder = DLAppServiceUtil.getFolder(folder.getFolderId());

			DLAppServiceUtil.deleteFolder(
				folder.getRepositoryId(), folder.getParentFolderId(),
				folder.getName());

			DLAppServiceUtil.getFolder(subfolder.getFolderId());
		}

	}

	@Sync
	public static class WhenMovingAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger moveCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_MOVE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			DLAppServiceUtil.moveFileEntry(
				fileEntry.getFileEntryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

			Assert.assertEquals(1, moveCounter.get());
		}

		@Test
		public void shouldHaveSameFileExtension() throws Exception {
			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), _FILE_NAME,
				_STRIPPED_FILE_NAME, null);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(
					targetGroup.getGroupId());

			FileEntry copiedFileEntry = DLAppServiceUtil.moveFileEntry(
				fileEntry.getFileEntryId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

			Assert.assertEquals(
				fileEntry.getExtension(), copiedFileEntry.getExtension());
		}

	}

	@Sync
	public static class WhenMovingAFileEntryToTrash extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		@Override
		public void setUp() throws Exception {
			super.setUp();

			_fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());
		}

		@Test
		public void shouldCancelCheckout() throws Exception {
			DLAppServiceUtil.checkOutFileEntry(
				_fileEntry.getFileEntryId(),
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			Assert.assertTrue(_fileEntry.isCheckedOut());

			DLTrashServiceUtil.moveFileEntryToTrash(
				_fileEntry.getFileEntryId());

			_fileEntry = DLAppServiceUtil.getFileEntry(
				_fileEntry.getFileEntryId());

			Assert.assertFalse(_fileEntry.isCheckedOut());
		}

		@Test
		public void shouldDeletePWCAssetEntry() throws Exception {
			DLAppServiceUtil.checkOutFileEntry(
				_fileEntry.getFileEntryId(),
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			FileVersion fileVersion = _fileEntry.getLatestFileVersion(false);

			Assert.assertNotNull(
				AssetEntryLocalServiceUtil.fetchEntry(
					DLFileEntryConstants.getClassName(),
					fileVersion.getFileVersionId()));

			DLTrashServiceUtil.moveFileEntryToTrash(
				_fileEntry.getFileEntryId());

			Assert.assertNull(
				AssetEntryLocalServiceUtil.fetchEntry(
					DLFileEntryConstants.getClassName(),
					fileVersion.getFileVersionId()));
		}

		@After
		@Override
		public void tearDown() throws Exception {
			DLAppServiceUtil.deleteFileEntry(_fileEntry.getFileEntryId());

			super.tearDown();
		}

		private FileEntry _fileEntry;

	}

	@Sync
	public static class WhenMovingAFolder extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger moveCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_MOVE);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLAppServiceUtil.moveFolder(
				folder.getFolderId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, serviceContext);

			Assert.assertEquals(1, moveCounter.get());
		}

	}

	@Sync
	public static class WhenRevertingAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldCallWorkflowHandler() throws Exception {
			try (WorkflowHandlerInvocationCounter<FileEntry>
					workflowHandlerInvocationCounter =
						new WorkflowHandlerInvocationCounter<>(
							DLFileEntryConstants.getClassName())) {

				FileEntry fileEntry = addFileEntry(
					group.getGroupId(), parentFolder.getFolderId());

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				String version = fileEntry.getVersion();

				updateFileEntry(
					group.getGroupId(), fileEntry.getFileEntryId(),
					RandomTestUtil.randomString(), true);

				Assert.assertEquals(
					2,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				ServiceContext serviceContext =
					ServiceContextTestUtil.getServiceContext(
						group.getGroupId());

				DLAppServiceUtil.revertFileEntry(
					fileEntry.getFileEntryId(), version, serviceContext);

				Assert.assertEquals(
					3,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));
			}
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger updateCounter =
				registerDLSyncEventProcessorMessageListener(
					DLSyncConstants.EVENT_UPDATE);

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId());

			String version = fileEntry.getVersion();

			Assert.assertEquals(0, updateCounter.get());

			updateFileEntry(
				group.getGroupId(), fileEntry.getFileEntryId(),
				RandomTestUtil.randomString(), true);

			Assert.assertEquals(2, updateCounter.get());

			DLAppServiceUtil.revertFileEntry(
				fileEntry.getFileEntryId(), version,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			Assert.assertEquals(4, updateCounter.get());
		}

	}

	@Sync
	public static class WhenSearchingFileEntries extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldFindFileEntryByAssetTagName() throws Exception {
			String fileName = RandomTestUtil.randomString();

			String[] assetTagNames = new String[] {"hello", "world"};

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				fileName, assetTagNames);

			search(fileEntry, false, "hello", true);
			search(fileEntry, false, "world", true);
			search(fileEntry, false, "liferay", false);
		}

		@Ignore
		@Test
		public void shouldFindFileEntryByAssetTagNameAfterUpdate()
			throws Exception {

			String fileName = RandomTestUtil.randomString();
			String description = StringPool.BLANK;
			String changeLog = StringPool.BLANK;
			byte[] bytes = CONTENT.getBytes();

			String[] assetTagNames = new String[] {"hello", "world"};

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				fileName, assetTagNames);

			assetTagNames = new String[] {"hello", "world", "liferay"};

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

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

	@Sync
	public static class WhenUpdatingAFileEntry extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void assetTagsShouldBeOrdered() throws Exception {
			String fileName = RandomTestUtil.randomString();
			byte[] bytes = CONTENT.getBytes();

			FileEntry fileEntry = addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName);

			String[] assetTagNames = new String[] {"hello", "world", "liferay"};

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

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
		public void shouldCallWorkflowHandler() throws Exception {
			try (WorkflowHandlerInvocationCounter<DLFileEntry>
					workflowHandlerInvocationCounter =
						new WorkflowHandlerInvocationCounter<>(
							DLFileEntryConstants.getClassName())) {

				FileEntry fileEntry = addFileEntry(
					group.getGroupId(), parentFolder.getFolderId());

				Assert.assertEquals(
					1,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));

				updateFileEntry(
					group.getGroupId(), fileEntry.getFileEntryId(),
					RandomTestUtil.randomString(), true);

				Assert.assertEquals(
					2,
					workflowHandlerInvocationCounter.getCount(
						"updateStatus", int.class, Map.class));
			}
		}

		@Test(expected = FileSizeException.class)
		public void shouldFailIfSizeLimitExceeded() throws Exception {
			String fileName = RandomTestUtil.randomString();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			FileEntry fileEntry = DLAppServiceUtil.addFileEntry(
				group.getGroupId(), parentFolder.getFolderId(), fileName,
				ContentTypes.TEXT_PLAIN, fileName, StringPool.BLANK,
				StringPool.BLANK, null, 0, serviceContext);

			try (PrefsPropsTemporarySwapper prefsPropsReplacement =
					new PrefsPropsTemporarySwapper(
						PropsKeys.DL_FILE_MAX_SIZE, 1L)) {

				byte[] bytes = RandomTestUtil.randomBytes(
					TikaSafeRandomizerBumper.INSTANCE);

				DLAppServiceUtil.updateFileEntry(
					fileEntry.getFileEntryId(), fileName,
					ContentTypes.TEXT_PLAIN, StringPool.BLANK, StringPool.BLANK,
					StringPool.BLANK, true, bytes, serviceContext);
			}
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

			FileEntry fileEntry = addFileEntry(
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

			FileEntry fileEntry = addFileEntry(
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

	@Sync
	public static class WhenUpdatingAFolder extends BaseDLAppTestCase {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE,
				SynchronousDestinationTestRule.INSTANCE);

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(group.getGroupId());

			Folder folder = DLAppServiceUtil.addFolder(
				group.getGroupId(), parentFolder.getFolderId(),
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);

			DLAppServiceUtil.updateFolder(
				folder.getFolderId(), folder.getName(), folder.getDescription(),
				serviceContext);

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

		return addFileEntry(groupId, folderId, fileName, fileName, null);
	}

	protected static FileEntry addFileEntry(
			long groupId, long folderId, String fileName, String title,
			String[] assetTagNames)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		serviceContext.setAssetTagNames(assetTagNames);

		return DLAppServiceUtil.addFileEntry(
			groupId, folderId, fileName, ContentTypes.TEXT_PLAIN, title,
			StringPool.BLANK, StringPool.BLANK, CONTENT.getBytes(),
			serviceContext);
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

		Indexer<DLFileEntry> indexer = IndexerRegistryUtil.getIndexer(
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

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		return DLAppServiceUtil.updateFileEntry(
			fileEntryId, fileName, ContentTypes.TEXT_PLAIN, fileName,
			StringPool.BLANK, StringPool.BLANK, majorVersion,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

	private static final String _FILE_NAME = "Title.txt";

	private static final String _STRIPPED_FILE_NAME = "Title";

	private static final Log _log = LogFactoryUtil.getLog(
		DLAppServiceTest.class);

}