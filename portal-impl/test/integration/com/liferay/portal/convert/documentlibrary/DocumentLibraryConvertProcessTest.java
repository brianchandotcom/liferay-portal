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

package com.liferay.portal.convert.documentlibrary;

import com.liferay.counter.kernel.service.CounterLocalServiceUtil;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLContentLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.store.Store;
import com.liferay.document.library.kernel.util.DLPreviewableProcessor;
import com.liferay.document.library.kernel.util.ImageProcessorUtil;
import com.liferay.message.boards.kernel.model.MBCategoryConstants;
import com.liferay.message.boards.kernel.model.MBMessage;
import com.liferay.message.boards.kernel.model.MBMessageConstants;
import com.liferay.message.boards.kernel.service.MBMessageLocalServiceUtil;
import com.liferay.portal.convert.ConvertProcess;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.test.randomizerbumpers.TikaSafeRandomizerBumper;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Roberto Díaz
 * @author Sergio González
 * @author Manuel de la Peña
 */
public class DocumentLibraryConvertProcessTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@BeforeClass
	public static void setUpClass() throws Exception {
		_storeFactory = StoreFactory.getInstance();
	}

	@Before
	public void setUp() throws Exception {
		_sourceStore = _storeFactory.getStore(_CLASS_NAME_FILE_SYSTEM_STORE);

		_storeFactory.setStore(_CLASS_NAME_FILE_SYSTEM_STORE);

		_group = GroupTestUtil.addGroup();

		_convertProcess = (ConvertProcess)InstancePool.get(
			DocumentLibraryConvertProcess.class.getName());

		_convertProcess.setParameterValues(
			new String[] {_CLASS_NAME_DB_STORE, Boolean.TRUE.toString()});
	}

	@After
	public void tearDown() throws Exception {
		_storeFactory.setStore(_CLASS_NAME_DB_STORE);

		_convertProcess.setParameterValues(
			new String[] {
				_CLASS_NAME_FILE_SYSTEM_STORE, Boolean.TRUE.toString()
			});

		_convertProcess.convert();

		PropsValues.DL_STORE_IMPL = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		_storeFactory.setStore(PropsValues.DL_STORE_IMPL);
	}

	@Test
	public void testMigrateDLAndDeleteFilesInSourceStore() throws Exception {
		testMigrateAndCheckOldRepositoryFiles(Boolean.TRUE);
	}

	@Test
	public void testMigrateDLAndKeepFilesInSourceStore() throws Exception {
		testMigrateAndCheckOldRepositoryFiles(Boolean.FALSE);
	}

	@Test
	public void testMigrateDLWhenFileEntryInFolder() throws Exception {
		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		testMigrateDL(folder.getFolderId());
	}

	@Test
	public void testMigrateDLWhenFileEntryInRootFolder() throws Exception {
		testMigrateDL(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID);
	}

	@Test
	public void testMigrateImages() throws Exception {
		Image image = addImage();

		try {
			_convertProcess.convert();

			DLContentLocalServiceUtil.getContent(
				0, 0, image.getImageId() + ".jpg");
		}
		finally {
			ImageLocalServiceUtil.deleteImage(image);
		}
	}

	@Test
	public void testMigrateMB() throws Exception {
		MBMessage mbMessage = addMBMessageAttachment();

		_convertProcess.convert();

		DLFileEntry dlFileEntry = getDLFileEntry(mbMessage);

		String title = dlFileEntry.getTitle();

		Assert.assertTrue(title.endsWith(".docx"));

		DLContentLocalServiceUtil.getContent(
			dlFileEntry.getCompanyId(),
			DLFolderConstants.getDataRepositoryId(
				dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
			dlFileEntry.getName());
	}

	@Test
	public void testStoreUpdatedAfterConversion() throws Exception {
		_convertProcess.convert();

		Assert.assertEquals(_CLASS_NAME_DB_STORE, PropsValues.DL_STORE_IMPL);
	}

	protected FileEntry addFileEntry(
			long folderId, String fileName, String mimeType, byte[] bytes)
		throws Exception {

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		return DLAppLocalServiceUtil.addFileEntry(
			TestPropsValues.getUserId(), _group.getGroupId(), folderId,
			fileName, mimeType, bytes, serviceContext);
	}

	protected Image addImage() throws Exception {
		return ImageLocalServiceUtil.updateImage(
			CounterLocalServiceUtil.increment(),
			FileUtil.getBytes(getClass(), "dependencies/liferay.jpg"));
	}

	protected MBMessage addMBMessageAttachment() throws Exception {
		List<ObjectValuePair<String, InputStream>> objectValuePairs =
			MBTestUtil.getInputStreamOVPs(
				"OSX_Test.docx", getClass(), StringPool.BLANK);

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(_group.getGroupId());

		User user = TestPropsValues.getUser();

		return MBMessageLocalServiceUtil.addMessage(
			user.getUserId(), user.getFullName(), _group.getGroupId(),
			MBCategoryConstants.DEFAULT_PARENT_CATEGORY_ID, "Subject", "Body",
			MBMessageConstants.DEFAULT_FORMAT, objectValuePairs, false, 0,
			false, serviceContext);
	}

	protected DLFileEntry getDLFileEntry(Object object) throws Exception {
		List<FileEntry> fileEntries = new ArrayList<>();

		if (object instanceof MBMessage) {
			fileEntries = ((MBMessage)object).getAttachmentsFileEntries(0, 1);
		}

		Assert.assertFalse(fileEntries.isEmpty());

		FileEntry fileEntry = fileEntries.get(0);

		return DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());
	}

	protected void testMigrateAndCheckOldRepositoryFiles(Boolean delete)
		throws Exception {

		_convertProcess.setParameterValues(
			new String[] {_CLASS_NAME_DB_STORE, delete.toString()});

		FileEntry rootFileEntry = addFileEntry(
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString() + ".txt", ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE));

		ServiceContext serviceContext =
			ServiceContextTestUtil.getServiceContext(
				_group.getGroupId(), TestPropsValues.getUserId());

		Folder folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			serviceContext);

		FileEntry folderFileEntry = addFileEntry(
			folder.getFolderId(), "liferay.jpg", ContentTypes.IMAGE_JPEG,
			FileUtil.getBytes(getClass(), "dependencies/liferay.jpg"));

		ImageProcessorUtil.generateImages(
			null, folderFileEntry.getFileVersion());

		_convertProcess.convert();

		DLFileEntry rootDLFileEntry = (DLFileEntry)rootFileEntry.getModel();

		Assert.assertNotEquals(
			delete,
			_sourceStore.hasFile(
				rootDLFileEntry.getCompanyId(),
				rootDLFileEntry.getDataRepositoryId(),
				rootDLFileEntry.getName()));

		DLFileEntry folderDLFileEntry = (DLFileEntry)folderFileEntry.getModel();

		Assert.assertNotEquals(
			delete,
			_sourceStore.hasDirectory(
				folderDLFileEntry.getCompanyId(),
				DLPreviewableProcessor.REPOSITORY_ID,
				DLPreviewableProcessor.THUMBNAIL_PATH));
		Assert.assertNotEquals(
			delete,
			_sourceStore.hasFile(
				folderDLFileEntry.getCompanyId(),
				folderDLFileEntry.getDataRepositoryId(),
				folderDLFileEntry.getName()));

		DLContentLocalServiceUtil.getContent(
			folderDLFileEntry.getCompanyId(),
			DLFolderConstants.getDataRepositoryId(
				folderDLFileEntry.getRepositoryId(),
				folderDLFileEntry.getFolderId()),
			folderDLFileEntry.getName());

		DLContentLocalServiceUtil.getContent(
			rootDLFileEntry.getCompanyId(),
			DLFolderConstants.getDataRepositoryId(
				rootDLFileEntry.getRepositoryId(),
				rootDLFileEntry.getFolderId()),
			rootDLFileEntry.getName());
	}

	protected void testMigrateDL(long folderId) throws Exception {
		FileEntry fileEntry = addFileEntry(
			folderId, RandomTestUtil.randomString() + ".txt",
			ContentTypes.TEXT_PLAIN,
			RandomTestUtil.randomBytes(TikaSafeRandomizerBumper.INSTANCE));

		_convertProcess.convert();

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		DLContentLocalServiceUtil.getContent(
			dlFileEntry.getCompanyId(),
			DLFolderConstants.getDataRepositoryId(
				dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
			dlFileEntry.getName());
	}

	private static final String _CLASS_NAME_DB_STORE =
		"com.liferay.portal.store.db.DBStore";

	private static final String _CLASS_NAME_FILE_SYSTEM_STORE =
		"com.liferay.portal.store.file.system.FileSystemStore";

	private static StoreFactory _storeFactory;

	private ConvertProcess _convertProcess;

	@DeleteAfterTestRun
	private Group _group;

	private Store _sourceStore;

}