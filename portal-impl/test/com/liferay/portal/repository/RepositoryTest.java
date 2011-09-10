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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.model.Layout;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.RepositoryServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLFolderServiceUtil;

import java.io.InputStream;

import junit.framework.TestCase;

/**
 * @author Alexander Chow
 */
public class RepositoryTest extends TestCase {

	public void testBasic() throws Exception {

		// Create repositories

		long[] repositoryIds = new long[2];

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		repositoryIds[0] = RepositoryServiceUtil.addRepository(
			getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(),
			new ServiceContext());

		DLFolder dlFolder = DLFolderServiceUtil.addFolder(
			getGroupId(), getGroupId(), false,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Folder", "Folder",
			new ServiceContext());

		repositoryIds[1] = RepositoryServiceUtil.addRepository(
			getGroupId(), classNameId, dlFolder.getFolderId(), "Test 2",
			"Test 2", PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(),
			new ServiceContext());

		// Delete repositories

		RepositoryServiceUtil.unmountRepositories(getGroupId());

		for (int i = 0; i < repositoryIds.length; i++) {
			long repositoryId = repositoryIds[i];

			try {
				RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);

				fail("Should not be able to access repository " + repositoryId);
			}
			catch (Exception e) {
			}
		}
	}

	public void testCreateAndDeleteFileEntries() throws Exception {

		// One default and one mapped repository

		long defaultRepositoryId = getGroupId();

		long classNameId = PortalUtil.getClassNameId(LiferayRepository.class);

		long dlRepositoryId = RepositoryServiceUtil.addRepository(
			getGroupId(), classNameId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, "Test 1", "Test 1",
			PortletKeys.DOCUMENT_LIBRARY, new UnicodeProperties(),
			new ServiceContext());

		long[] repositoryIds = {defaultRepositoryId, dlRepositoryId};

		long[] fileEntryIds = new long[4];

		long[] folderIds = new long[2];

		InputStream inputStream = new UnsyncByteArrayInputStream(
			_TEST_CONTENT.getBytes());

		// Add folders and files

		for (int i = 0; i < repositoryIds.length; i++) {
			long repositoryId = repositoryIds[i];

			LocalRepository localRepository =
				RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);

			String name1 =
				String.valueOf(DLFolderConstants.DEFAULT_PARENT_FOLDER_ID) +
					".txt";

			FileEntry fileEntry1 = localRepository.addFileEntry(
				TestPropsValues.USER_ID,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID, name1,
				ContentTypes.TEXT_PLAIN, name1, StringPool.BLANK,
				StringPool.BLANK, inputStream, _TEST_CONTENT.length(),
				new ServiceContext());

			fileEntryIds[i] = fileEntry1.getFileEntryId();

			Folder folder = localRepository.addFolder(
				TestPropsValues.USER_ID,
				DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(repositoryId), String.valueOf(repositoryId),
				new ServiceContext());

			folderIds[i] = folder.getFolderId();

			String name2 = String.valueOf(folderIds[i]) + ".txt";

			FileEntry fileEntry2 = localRepository.addFileEntry(
				TestPropsValues.USER_ID, folderIds[i], name2,
				ContentTypes.TEXT_PLAIN, name2, StringPool.BLANK,
				StringPool.BLANK, inputStream, _TEST_CONTENT.length(),
				new ServiceContext());

			fileEntryIds[i + 2] = fileEntry2.getFileEntryId();
		}

		// Verify mapped folder

		// TODO

		/*DLFolder dlFolder = DLRepositoryLocalServiceUtil.getFolder(
			folderIds[1]);

		DLFolder parentDLFolder = dlFolder.getParentFolder();

		assertEquals(
			parentDLFolder.getParentFolderId(),
			DLFolderConstants.MAPPED_PARENT_FOLDER_ID);*/

		// Delete repositories

		RepositoryServiceUtil.unmountRepositories(getGroupId());

		for (int i = 0; i < repositoryIds.length; i++) {
			long repositoryId = repositoryIds[i];

			long fileEntryId = fileEntryIds[i];

			try {
				LocalRepository localRepository =
					RepositoryServiceUtil.getLocalRepositoryImpl(repositoryId);

				localRepository.getFileEntry(fileEntryId);

				fail(
					"Should not be able to get file entry " + fileEntryId +
						" from repository " + repositoryId);
			}
			catch (Exception e) {
			}
		}
	}

	protected static long getGroupId() throws Exception {
		if (_groupId == 0) {
			Layout layout = LayoutLocalServiceUtil.getLayout(
				TestPropsValues.LAYOUT_PLID);

			_groupId = layout.getGroupId();
		}

		return _groupId;
	}

	private static final String _TEST_CONTENT =
		"LIFERAY\nEnterprise. Open Source. For Life.";

	private static long _groupId;

}