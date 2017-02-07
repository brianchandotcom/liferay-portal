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

import com.liferay.asset.kernel.model.AssetEntry;
import com.liferay.asset.kernel.service.AssetEntryLocalServiceUtil;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.model.DLSyncConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLTrashServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.model.Subscription;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryProviderUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ClassNameLocalServiceUtil;
import com.liferay.portal.kernel.service.RepositoryLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class DLAppLocalServiceTest {

	@Sync
	public static class WhenAddingAFolder {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();
		}

		@Test
		public void shouldAddAssetEntry() throws Exception {
			Folder folder = addFolder(_group.getGroupId(), false);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
				DLFolderConstants.getClassName(), folder.getFolderId());

			Assert.assertNotNull(assetEntry);
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_ADD);

			addFolder(_group.getGroupId(), true);

			Assert.assertEquals(1, counter.get());
		}

		@Test
		public void shouldSucceedForNonRootFolders() throws Exception {
			Folder folder = addFolder(_group.getGroupId(), false);

			Assert.assertTrue(folder != null);
		}

		@Test
		public void shouldSucceedForRootFolder() throws Exception {
			Folder folder = addFolder(_group.getGroupId(), true);

			Assert.assertTrue(folder != null);
		}

		@DeleteAfterTestRun
		private Group _group;

	}

	@Sync
	public static class WhenDeletingAFolder {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();

			_folder = addFolder(_group.getGroupId(), true);
		}

		@Test
		public void shouldDeleteSubscriptions() throws Exception {
			DLAppLocalServiceUtil.subscribeFolder(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_folder.getFolderId());

			Subscription subscription =
				SubscriptionLocalServiceUtil.fetchSubscription(
					_group.getCompanyId(), TestPropsValues.getUserId(),
					DLFolderConstants.getClassName(), _folder.getFolderId());

			Assert.assertNotNull(subscription);

			DLAppLocalServiceUtil.deleteFolder(_folder.getFolderId());

			subscription = SubscriptionLocalServiceUtil.fetchSubscription(
				_group.getCompanyId(), TestPropsValues.getUserId(),
				DLFolderConstants.getClassName(), _folder.getFolderId());

			Assert.assertNull(subscription);
		}

		private Folder _folder;

		@DeleteAfterTestRun
		private Group _group;

	}

	@Sync
	public static class WhenDeletingAllRepositoriesInAGroup {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();
		}

		@Test
		public void shouldDeleteAllGroupRepositoryFileEntries()
			throws Exception {

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			addFileEntry(serviceContext);

			Folder folder = addFolder(_group.getGroupId(), true);

			DLAppLocalServiceUtil.addFileEntry(
				serviceContext.getUserId(), _group.getGroupId(),
				folder.getFolderId(), StringUtil.randomString(),
				ContentTypes.APPLICATION_OCTET_STREAM, new byte[0],
				serviceContext);

			DLAppLocalServiceUtil.deleteAllRepositories(_group.getGroupId());

			LocalRepository localRepository =
				RepositoryProviderUtil.getLocalRepository(_group.getGroupId());

			int rootFolderFileEntriesCount =
				localRepository.getFileEntriesCount(
					DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);

			Assert.assertEquals(0, rootFolderFileEntriesCount);

			int subfolderFileEntriesCount = localRepository.getFileEntriesCount(
				folder.getFolderId());

			Assert.assertEquals(0, subfolderFileEntriesCount);
		}

		@Test
		public void shouldDeleteAllGroupRepositoryFolders() throws Exception {
			Folder folder = addFolder(_group.getGroupId(), true);

			Folder subfolder = addFolder(
				_group.getGroupId(), folder.getFolderId(),
				StringUtil.randomString());

			DLAppLocalServiceUtil.deleteAllRepositories(_group.getGroupId());

			try {
				DLAppLocalServiceUtil.getFolder(folder.getFolderId());

				Assert.fail();
			}
			catch (NoSuchFolderException nsfe) {
			}

			try {
				DLAppLocalServiceUtil.getFolder(subfolder.getFolderId());

				Assert.fail();
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		@Test
		public void shouldDeleteTrashedFolders() throws Exception {
			Folder folder = addFolder(_group.getGroupId(), true);

			DLTrashServiceUtil.moveFolderToTrash(folder.getFolderId());

			DLAppLocalServiceUtil.deleteAllRepositories(_group.getGroupId());

			try {
				DLAppLocalServiceUtil.getFolder(folder.getFolderId());

				Assert.fail();
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		@DeleteAfterTestRun
		private Group _group;

	}

	@Sync
	public static class WhenDeletingALocalRepository {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			_repository = RepositoryLocalServiceUtil.addRepository(
				serviceContext.getUserId(), _group.getGroupId(),
				ClassNameLocalServiceUtil.getClassNameId(
					LiferayRepository.class),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), StringPool.BLANK,
				RandomTestUtil.randomString(), new UnicodeProperties(), true,
				serviceContext);
		}

		@Test
		public void shouldFireSyncEvent() throws Exception {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_DELETE);

			addFolder(_group.getGroupId(), false);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			addFileEntry(serviceContext);

			DLAppLocalServiceUtil.deleteAll(_group.getGroupId());

			Assert.assertEquals(3, counter.get());
		}

		@Test
		public void shouldOnlyDeleteRequestedRepository()
			throws PortalException {

			LocalRepository localRepository =
				RepositoryProviderUtil.getLocalRepository(
					_repository.getRepositoryId());

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			FileEntry fileEntry = localRepository.addFileEntry(
				serviceContext.getUserId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), ContentTypes.APPLICATION_TEXT,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				RandomTestUtil.randomString(),
				new ByteArrayInputStream(new byte[0]), 0, serviceContext);

			DLAppLocalServiceUtil.deleteAll(_group.getGroupId());

			Assert.assertNotNull(
				localRepository.getFileEntry(fileEntry.getFileEntryId()));
		}

		@DeleteAfterTestRun
		private Group _group;

		private Repository _repository;

	}

	@Sync
	public static class WhenUpdatingAFileEntry {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();
		}

		@Test
		public void shouldFireSyncEvent() throws Throwable {
			AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
				DLSyncConstants.EVENT_UPDATE);

			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			FileEntry fileEntry = addFileEntry(serviceContext);

			updateFileEntry(serviceContext, fileEntry);

			Assert.assertEquals(2, counter.get());
		}

		@Test
		public void shouldUpdateAssetEntry() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			FileEntry fileEntry = addFileEntry(serviceContext);

			updateFileEntry(serviceContext, fileEntry);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				DLFileEntryConstants.getClassName(),
				fileEntry.getFileEntryId());

			Assert.assertEquals("New Title", assetEntry.getTitle());
		}

		@DeleteAfterTestRun
		private Group _group;

	}

	@Sync
	public static class WhenUpdatingAFolder {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(),
				SynchronousDestinationTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_group = GroupTestUtil.addGroup();
		}

		@Test(expected = NoSuchFolderException.class)
		public void shouldFailForDefaultParentFolder() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			DLAppLocalServiceUtil.updateFolder(
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), StringPool.BLANK,
				serviceContext);
		}

		@Test
		public void shouldUpdateAssetEntry() throws Exception {
			ServiceContext serviceContext =
				ServiceContextTestUtil.getServiceContext(_group.getGroupId());

			Folder folder = addFolder(_group.getGroupId(), false, "Old Name");

			DLAppLocalServiceUtil.updateFolder(
				folder.getFolderId(), folder.getParentFolderId(), "New Name",
				RandomTestUtil.randomString(), serviceContext);

			AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
				DLFolderConstants.getClassName(), folder.getFolderId());

			Assert.assertEquals("New Name", assetEntry.getTitle());
		}

		@DeleteAfterTestRun
		private Group _group;

	}

	protected static FileEntry addFileEntry(ServiceContext serviceContext)
		throws Exception {

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), serviceContext.getScopeGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, "Old Title",
			RandomTestUtil.randomString(), null,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

	protected static Folder addFolder(long groupId, boolean rootFolder)
		throws Exception {

		return addFolder(groupId, rootFolder, RandomTestUtil.randomString());
	}

	protected static Folder addFolder(
			long groupId, boolean rootFolder, String name)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			Folder parentFolder = addFolder(
				groupId, parentFolderId, "Test Folder", true);

			parentFolderId = parentFolder.getFolderId();
		}

		return addFolder(groupId, parentFolderId, name);
	}

	protected static Folder addFolder(
			long groupId, long parentFolderId, String name)
		throws Exception {

		return addFolder(groupId, parentFolderId, name, false);
	}

	protected static Folder addFolder(
			long groupId, long parentFolderId, String name,
			boolean deleteExisting)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(groupId);

		if (deleteExisting) {
			try {
				Folder folder = DLAppLocalServiceUtil.getFolder(
					groupId, parentFolderId, name);

				DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), groupId, parentFolderId, name,
			StringPool.BLANK, serviceContext);
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

	protected static FileEntry updateFileEntry(
			ServiceContext serviceContext, FileEntry fileEntry)
		throws Exception {

		return DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, "New Title",
			RandomTestUtil.randomString(), null, true,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE),
			serviceContext);
	}

}