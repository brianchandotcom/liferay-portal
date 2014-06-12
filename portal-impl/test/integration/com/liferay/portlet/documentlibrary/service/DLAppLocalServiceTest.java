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

import com.liferay.portal.kernel.messaging.DestinationNames;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
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

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Manuel de la Peña
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public class DLAppLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddAssetEntryWhenAddingFolder() throws Exception {
		Folder folder = addFolder(false);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.fetchEntry(
			DLFolderConstants.getClassName(), folder.getFolderId());

		Assert.assertNotNull(assetEntry);
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
	public void testFireSyncEventWhenAddingFolder() throws Exception {
		AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
			DLSyncConstants.EVENT_ADD);

		addFolder(true);

		Assert.assertEquals(1, counter.get());
	}

	@Test
	public void testFireSyncEventWhenUpdatingFileEntry() throws Throwable {
		AtomicInteger counter = registerDLSyncEventProcessorMessageListener(
			DLSyncConstants.EVENT_UPDATE);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry = addFileEntry(serviceContext);

		updateFileEntry(serviceContext, fileEntry);

		Assert.assertEquals(2, counter.get());
	}

	@Test
	public void testUpdateAssetEntryWhenUpdatingFileEntry() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		FileEntry fileEntry = addFileEntry(serviceContext);

		updateFileEntry(serviceContext, fileEntry);

		AssetEntry assetEntry = AssetEntryLocalServiceUtil.getEntry(
			DLFileEntryConstants.getClassName(), fileEntry.getFileEntryId());

		Assert.assertEquals("New Title", assetEntry.getTitle());
	}

	@Test
	public void testUpdateAssetEntryWhenUpdatingFolder() throws Exception {
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

	@Test(expected = NoSuchFolderException.class)
	public void testUpdateDefaultParentFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		DLAppLocalServiceUtil.updateFolder(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), StringPool.BLANK, serviceContext);
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

	protected AtomicInteger registerDLSyncEventProcessorMessageListener(
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

	protected FileEntry updateFileEntry(
			ServiceContext serviceContext, FileEntry fileEntry)
		throws Exception {

		return DLAppLocalServiceUtil.updateFileEntry(
			TestPropsValues.getUserId(), fileEntry.getFileEntryId(),
			RandomTestUtil.randomString(), ContentTypes.TEXT_PLAIN, "New Title",
			RandomTestUtil.randomString(), null, true,
			RandomTestUtil.randomBytes(), serviceContext);
	}

	@DeleteAfterTestRun
	private Group _group;

}