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

import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.messaging.MessageListenerException;
import com.liferay.portal.kernel.messaging.SynchronousDestination;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.service.AssetEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.model.DLSyncConstants;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLAppLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		MessageBus messageBus = MessageBusUtil.getMessageBus();

		_originalSyncDestination = messageBus.getDestination(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);

		SynchronousDestination destination = new SynchronousDestination();

		destination.setName(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);

		messageBus.addDestination(destination);
	}

	@After
	public void tearDown() throws Exception {
		MessageBusUtil.removeDestination(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR);

		if (_originalSyncDestination != null) {
			MessageBusUtil.addDestination(_originalSyncDestination);
		}

		GroupLocalServiceUtil.deleteGroup(_group);
	}

	@Test
	public void testAddFolder() throws Exception {
		Folder folder = addFolder(true);

		Assert.assertTrue(folder != null);
	}

	@Test
	public void testAddRootFolder() throws Exception {
		Folder folder = addFolder(false);

		Assert.assertTrue(folder != null);
	}

	@Test
	public void testWhenAddingAFolderASyncEventIsFired() throws Exception {
		int[] messagesReceived = registerStubSyncMessageListener(
			DLSyncConstants.EVENT_ADD);

		addFolder(true);

		Assert.assertEquals(1, messagesReceived[0]);
	}

	@Test
	public void testWhenAddingAFolderItsAssetIsCreated() throws Exception {
		Folder folder = addFolder(false);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());

		Assert.assertNotNull(assetEntry);
	}

	@Test
	public void testWhenUpdatingAFileEntryASyncEventIsFired() throws Throwable {
		int[] messagesReceived = registerStubSyncMessageListener(
			DLSyncConstants.EVENT_UPDATE);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry = addFileEntry(serviceContext);

		updateFileEntry(serviceContext, fileEntry);

		Assert.assertEquals(2, messagesReceived[0]);
	}

	@Test
	public void testWhenUpdatingAFileEntryItsAssetIsUpdated() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry = addFileEntry(serviceContext);

		updateFileEntry(serviceContext, fileEntry);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		Assert.assertEquals("New Title", assetEntry.getTitle());
	}

	@Test
	public void testWhenUpdatingFolderItsAssetIsUpdated() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		Folder folder = addFolder(false, "Old Name");

		DLAppLocalServiceUtil.updateFolder(
			folder.getFolderId(), folder.getParentFolderId(), "New Name",
			RandomTestUtil.randomString(), serviceContext);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());

		Assert.assertEquals("New Name", assetEntry.getTitle());
	}

	protected FileEntry addFileEntry(ServiceContext serviceContext)
		throws Exception {

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, "Old Title",
			RandomTestUtil.randomString(), null, RandomTestUtil.randomBytes(),
			serviceContext);
	}

	protected Folder addFolder(boolean rootFolder) throws Exception {
		return addFolder(rootFolder, RandomTestUtil.randomString());
	}

	protected Folder addFolder(boolean rootFolder, String name)
		throws Exception {

		long parentFolderId = DLFolderConstants.DEFAULT_PARENT_FOLDER_ID;

		if (!rootFolder) {
			Folder parentFolder = addFolder(
				parentFolderId, "Test Folder", true);

			parentFolderId = parentFolder.getFolderId();
		}

		return addFolder(parentFolderId, name);
	}

	protected Folder addFolder(long parentFolderId, String name)
		throws Exception {

		return addFolder(parentFolderId, name, false);
	}

	protected Folder addFolder(
			long parentFolderId, String name, boolean deleteExisting)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		if (deleteExisting) {
			try {
				Folder folder = DLAppLocalServiceUtil.getFolder(
					_group.getGroupId(), parentFolderId, name);

				DLAppLocalServiceUtil.deleteFolder(folder.getFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}
		}

		return DLAppLocalServiceUtil.addFolder(
			TestPropsValues.getUserId(), _group.getGroupId(), parentFolderId,
			name, StringPool.BLANK, serviceContext);
	}

	protected int[] registerStubSyncMessageListener(final String targetEvent) {
		final int[] messagesReceived = {0};

		MessageBusUtil.registerMessageListener(
			DestinationNames.DOCUMENT_LIBRARY_SYNC_EVENT_PROCESSOR,
			new MessageListener() {

				@Override
				public void receive(Message message)
					throws MessageListenerException {

					Object event = message.get("event");

					if (targetEvent.equals(event)) {
						messagesReceived[0]++;
					}
				}

			}
		);

		return messagesReceived;
	}

	protected FileEntry updateFileEntry(
			ServiceContext serviceContext, FileEntry fileEntry)
		throws Exception {

		return DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, "New Title",
			RandomTestUtil.randomString(), null, true,
			RandomTestUtil.randomBytes(), serviceContext);
	}

	private Group _group;
	private Destination _originalSyncDestination;

}