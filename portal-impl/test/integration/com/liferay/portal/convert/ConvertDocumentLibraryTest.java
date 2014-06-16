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

package com.liferay.portal.convert;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.InstancePool;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.NoSuchContentException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLContentLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.store.DBStore;
import com.liferay.portlet.documentlibrary.store.FileSystemStore;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;
import com.liferay.portlet.documentlibrary.util.test.DLAppTestUtil;
import com.liferay.portlet.messageboards.model.MBCategoryConstants;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageConstants;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.util.test.MBTestUtil;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.portlet.wiki.util.test.WikiTestUtil;

import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		MainServletExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ConvertDocumentLibraryTest {

	@Before
	public void setUp() throws Exception {
		PropsValues.DL_STORE_IMPL = FileSystemStore.class.getName();

		Store store = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(),
			FileSystemStore.class.getName());

		StoreFactory.setInstance(store);

		_group = GroupTestUtil.addGroup();

		_convertProcess = (ConvertProcess)InstancePool.get(
			ConvertDocumentLibrary.class.getName());

		_convertProcess.setParameterValues(
			new String[] {DBStore.class.getName()});
	}

	@After
	public void tearDown() throws Exception {
		PropsValues.DL_STORE_IMPL = PropsUtil.get(PropsKeys.DL_STORE_IMPL);

		Store store = (Store)InstanceFactory.newInstance(
			ClassLoaderUtil.getPortalClassLoader(), PropsValues.DL_STORE_IMPL);

		StoreFactory.setInstance(store);
	}

	@Test
	public void testMigrateDLWhenFileEntryInFolder() throws Exception {
		Folder folder = DLAppTestUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());

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

			try {
				DLContentLocalServiceUtil.getContent(
					0, 0, image.getImageId() + ".jpg");
			}
			catch (NoSuchContentException nsce) {
				Assert.fail();
			}
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

		try {
			DLContentLocalServiceUtil.getContent(
				dlFileEntry.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
				dlFileEntry.getName());
		}
		catch (NoSuchContentException nsce) {
			Assert.fail();
		}
	}

	@Test
	public void testMigrateWiki() throws Exception {
		WikiPage wikiPage = addWikiPage();

		addWikiPageAttachment(wikiPage);

		_convertProcess.convert();

		DLFileEntry dlFileEntry = getDLFileEntry(wikiPage);

		String title = dlFileEntry.getTitle();

		Assert.assertTrue(title.endsWith(".docx"));

		try {
			DLContentLocalServiceUtil.getContent(
				dlFileEntry.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
				dlFileEntry.getName());
		}
		catch (NoSuchContentException nsce) {
			Assert.fail();
		}
	}

	@Test
	public void testStoreUpdatedAfterConversion() throws Exception {
		_convertProcess.convert();

		Store store = StoreFactory.getInstance();

		Assert.assertEquals(
			DBStore.class.getName(), store.getClass().getName());
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

	protected WikiPage addWikiPage() throws Exception {
		WikiNode wikiNode = WikiTestUtil.addNode(_group.getGroupId());

		return WikiTestUtil.addPage(
			wikiNode.getUserId(), _group.getGroupId(), wikiNode.getNodeId(),
			RandomTestUtil.randomString(), true);
	}

	protected void addWikiPageAttachment(WikiPage wikiPage) throws Exception {
		WikiTestUtil.addWikiAttachment(
			wikiPage.getUserId(), wikiPage.getNodeId(), wikiPage.getTitle(),
			getClass());
	}

	protected DLFileEntry getDLFileEntry(Object object) throws Exception {
		List<FileEntry> fileEntries = new ArrayList<FileEntry>();

		if (object instanceof MBMessage) {
			fileEntries = ((MBMessage)object).getAttachmentsFileEntries(0, 1);
		}
		else if (object instanceof WikiPage) {
			fileEntries = ((WikiPage)object).getAttachmentsFileEntries(0, 1);
		}

		if (fileEntries.isEmpty()) {
			Assert.fail();
		}

		FileEntry fileEntry = fileEntries.get(0);

		return DLFileEntryLocalServiceUtil.getDLFileEntry(
			fileEntry.getFileEntryId());
	}

	protected void testMigrateDL(long folderId) throws Exception {
		FileEntry fileEntry = DLAppTestUtil.addFileEntry(
			_group.getGroupId(), folderId,
			RandomTestUtil.randomString() + ".txt");

		_convertProcess.convert();

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		try {
			DLContentLocalServiceUtil.getContent(
				dlFileEntry.getCompanyId(),
				DLFolderConstants.getDataRepositoryId(
					dlFileEntry.getRepositoryId(), dlFileEntry.getFolderId()),
				dlFileEntry.getName());
		}
		catch (NoSuchContentException nsce) {
			Assert.fail();
		}
	}

	private ConvertProcess _convertProcess;

	@DeleteAfterTestRun
	private Group _group;

}