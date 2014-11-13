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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.test.DeleteAfterTestRun;
import com.liferay.portal.test.listeners.MainServletExecutionTestListener;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.test.GroupTestUtil;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLAppServiceUtil;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class RepositoryTest {

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();
	}

	@Test
	public void testAddFileEntryInRepository() throws Exception {
		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		Repository dlRepository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), true,
			new ServiceContext());

		long repositoryId = dlRepository.getRepositoryId();

		long[] entryIds = populateRepository(repositoryId);

		Assert.assertEquals(
			1,
			DLAppServiceUtil.getFoldersCount(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));

		Assert.assertEquals(
			1,
			DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				WorkflowConstants.STATUS_ANY));

		Assert.assertEquals(
			1,
			DLAppServiceUtil.getFileEntriesAndFileShortcutsCount(
				repositoryId, entryIds[1], WorkflowConstants.STATUS_ANY));
	}

	@Test
	public void testDeleteAllRepositories() throws Exception {
		long[] repositoryIds = new long[2];

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		Repository repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), true,
			new ServiceContext());

		repositoryIds[0] = repository.getRepositoryId();

		DLFolder dlFolder = DLFolderServiceUtil.addFolder(
			_group.getGroupId(), _group.getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder", "Folder",
			new ServiceContext());

		repository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			dlFolder.getFolderId(), "Test 2", "Test 2",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), true,
			new ServiceContext());

		repositoryIds[1] = repository.getRepositoryId();

		// Delete repositories

		DLAppLocalServiceUtil.deleteAllRepositories(_group.getGroupId());

		for (long repositoryId : repositoryIds) {
			try {
				RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);

				Assert.fail(
					"Should not be able to access repository " + repositoryId);
			}
			catch (Exception e) {
			}
		}
	}

	@Test
	public void testFileEntriesAreDeletedWhenDeletingAllRepositories()
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		Repository dlRepository = RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), true,
			new ServiceContext());

		long[] fileEntryIds = new long[2];

		long[] folderIds = new long[1];

		// Add folders and files

		long[] entryIds = populateRepository(dlRepository.getRepositoryId());

		fileEntryIds[0] = entryIds[0];
		fileEntryIds[1] = entryIds[2];
		folderIds[0] = entryIds[1];

		// Delete repositories

		DLAppLocalServiceUtil.deleteAllRepositories(_group.getGroupId());

		for (int i = 0; i < fileEntryIds.length; i++) {
			try {
				LocalRepository localRepository =
					RepositoryServiceUtil.getLocalRepositoryImpl(
						dlRepository.getRepositoryId());

				localRepository.getFileEntry(fileEntryIds[i]);

				Assert.fail(
					"Should not be able to get file entry " + fileEntryIds[i] +
						" from repository " + dlRepository.getRepositoryId());
			}
			catch (Exception e) {
			}
		}
	}

	@Test
	public void testGetMountFoldersCountWithHiddenRepository()
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), true,
			new ServiceContext());

		Assert.assertEquals(
			0,
			DLFolderServiceUtil.getMountFoldersCount(
				_group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
	}

	@Test
	public void testGetMountFoldersCountWithNotHiddenRepository()
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		RepositoryLocalServiceUtil.addRepository(
			TestPropsValues.getUserId(), _group.getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(), false,
			new ServiceContext());

		Assert.assertEquals(
			1,
			DLFolderServiceUtil.getMountFoldersCount(
				_group.getGroupId(),
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID));
	}

	protected long[] populateRepository(long repositoryId) throws Exception {
		InputStream inputStream = new UnsyncByteArrayInputStream(
			_TEST_CONTENT.getBytes());

		LocalRepository localRepository =
			RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);

		String name1 = RandomTestUtil.randomString();

		FileEntry fileEntry = localRepository.addFileEntry(
			TestPropsValues.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name1,
			ContentTypes.TEXT_PLAIN, name1, StringPool.BLANK, StringPool.BLANK,
			inputStream, _TEST_CONTENT.length(), new ServiceContext());

		Folder folder = localRepository.addFolder(
			TestPropsValues.getUserId(),
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(repositoryId), String.valueOf(repositoryId),
			new ServiceContext());

		String name2 = RandomTestUtil.randomString();

		FileEntry folderFileEntry = localRepository.addFileEntry(
			TestPropsValues.getUserId(), folder.getFolderId(), name2,
			ContentTypes.TEXT_PLAIN, name2, StringPool.BLANK, StringPool.BLANK,
			inputStream, _TEST_CONTENT.length(), new ServiceContext());

		return new long[] {
			fileEntry.getFileEntryId(), folder.getFolderId(),
			folderFileEntry.getFileEntryId()
		};
	}

	private static final String _TEST_CONTENT =
		"LIFERAY\nEnterprise. Open Source. For Life.";

	@DeleteAfterTestRun
	private Group _group;

}