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

package com.liferay.document.library.util.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.model.DLFileEntry;
import com.liferay.document.library.kernel.model.DLFileShortcut;
import com.liferay.document.library.kernel.model.DLFileVersion;
import com.liferay.document.library.kernel.model.DLFolder;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.service.DLAppLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLAppServiceUtil;
import com.liferay.document.library.kernel.service.DLFileEntryLocalServiceUtil;
import com.liferay.document.library.kernel.service.DLFolderLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryDefinition;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.FileShortcut;
import com.liferay.portal.kernel.repository.model.FileVersion;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.repository.model.RepositoryEntry;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portlet.documentlibrary.util.RepositoryModelUtil;
import com.liferay.portlet.documentlibrary.util.test.DLTestUtil;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Roberto Díaz
 */
@RunWith(Arquillian.class)
public class RepositoryModelUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_serviceContext = ServiceContextTestUtil.getServiceContext(
			_group.getGroupId(), TestPropsValues.getUserId());

		_folder = DLAppServiceUtil.addFolder(
			_group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			_serviceContext);
	}

	@Test
	public void testToFileEntries() throws Exception {
		populateFolderWithDLFileEntries();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				_group.getGroupId(), _folder.getFolderId());

		List<FileEntry> fileEntries = RepositoryModelUtil.toFileEntries(
			dlFileEntries);

		Assert.assertEquals(
			fileEntries.toString(), dlFileEntries.size(), fileEntries.size());

		for (int i = 0; i < dlFileEntries.size(); i++) {
			DLFileEntry dlFileEntry = dlFileEntries.get(i);

			FileEntry fileEntry = fileEntries.get(i);

			Assert.assertEquals(
				dlFileEntry.getFileEntryId(), fileEntry.getFileEntryId());
		}
	}

	@Test
	public void testToFileShortcuts() throws Exception {
		populateFolderWithDLFileShortcuts();

		List<DLFileEntry> dlFileEntries =
			DLFileEntryLocalServiceUtil.getFileEntries(
				_group.getGroupId(), _folder.getFolderId());

		DLFileEntry dlFileEntry = dlFileEntries.get(0);

		List<DLFileShortcut> dlFileShortcuts = dlFileEntry.getFileShortcuts();

		List<FileShortcut> fileShortcuts = RepositoryModelUtil.toFileShortcuts(
			dlFileShortcuts);

		Assert.assertEquals(
			fileShortcuts.toString(), dlFileShortcuts.size(),
			fileShortcuts.size());

		for (int i = 0; i < dlFileShortcuts.size(); i++) {
			DLFileShortcut dlFileShortcut = dlFileShortcuts.get(i);

			FileShortcut fileShortcut = fileShortcuts.get(i);

			Assert.assertEquals(
				dlFileShortcut.getFileShortcutId(),
				fileShortcut.getFileShortcutId());
		}
	}

	@Test
	public void testToFileVersions() throws Exception {
		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			_folder.getFolderId());

		for (int i = 0; i < 5; i++) {
			dlFileEntry.setTitle(RandomTestUtil.randomString());

			DLFileEntryLocalServiceUtil.updateDLFileEntry(dlFileEntry);
		}

		dlFileEntry = DLFileEntryLocalServiceUtil.getFileEntry(
			dlFileEntry.getFileEntryId());

		List<DLFileVersion> dlFileVersions = dlFileEntry.getFileVersions(
			WorkflowConstants.STATUS_APPROVED);

		List<FileVersion> fileVersions = RepositoryModelUtil.toFileVersions(
			dlFileVersions);

		Assert.assertEquals(
			fileVersions.toString(), dlFileVersions.size(),
			fileVersions.size());

		for (int i = 0; i < dlFileVersions.size(); i++) {
			DLFileVersion dlFileVersion = dlFileVersions.get(i);

			FileVersion fileVersion = fileVersions.get(i);

			Assert.assertEquals(
				dlFileVersion.getFileVersionId(),
				fileVersion.getFileVersionId());
		}
	}

	@Test
	public void testToFolders() throws Exception {
		populateFolderWithDLFolders();

		List<DLFolder> dlFolders = DLFolderLocalServiceUtil.getFolders(
			_group.getGroupId(), _folder.getFolderId());

		List<Folder> folders = RepositoryModelUtil.toFolders(dlFolders);

		Assert.assertEquals(
			folders.toString(), dlFolders.size(), folders.size());

		for (int i = 0; i < dlFolders.size(); i++) {
			DLFolder dlFolder = dlFolders.get(i);

			Folder folder = folders.get(i);

			Assert.assertEquals(dlFolder.getFolderId(), folder.getFolderId());
		}
	}

	@Test
	public void testToRepositoryEntries() throws Exception {
		populateFolderWithDLFileEntries();
		populateFolderWithDLFileShortcuts();
		populateFolderWithDLFolders();

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		List<Object> dlFoldersAndDLFileEntriesAndDLFileShortcuts =
			DLFolderLocalServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), _folder.getFolderId(), new String[0], true,
				queryDefinition);

		List<RepositoryEntry> repositoryEntries =
			RepositoryModelUtil.toRepositoryEntries(
				dlFoldersAndDLFileEntriesAndDLFileShortcuts);

		Assert.assertEquals(
			repositoryEntries.toString(),
			dlFoldersAndDLFileEntriesAndDLFileShortcuts.size(),
			repositoryEntries.size());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testToRepositoryEntriesWithIllegalArgument() throws Exception {
		populateFolderWithDLFileEntries();
		populateFolderWithDLFileShortcuts();
		populateFolderWithDLFolders();

		QueryDefinition<?> queryDefinition = new QueryDefinition<>(
			WorkflowConstants.STATUS_APPROVED, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);

		List<Object> dlFoldersAndDLFileEntriesAndDLFileShortcuts =
			DLFolderLocalServiceUtil.getFoldersAndFileEntriesAndFileShortcuts(
				_group.getGroupId(), _folder.getFolderId(), new String[0], true,
				queryDefinition);

		dlFoldersAndDLFileEntriesAndDLFileShortcuts.add(new Object());

		RepositoryModelUtil.toRepositoryEntries(
			dlFoldersAndDLFileEntriesAndDLFileShortcuts);
	}

	protected void populateFolderWithDLFileEntries() throws Exception {
		for (int i = 0; i < 5; i++) {
			DLTestUtil.addDLFileEntry(_folder.getFolderId());
		}
	}

	protected void populateFolderWithDLFileShortcuts() throws Exception {
		DLFileEntry dlFileEntry = DLTestUtil.addDLFileEntry(
			_folder.getFolderId());

		for (int i = 0; i < 5; i++) {
			DLAppLocalServiceUtil.addFileShortcut(
				TestPropsValues.getUserId(), _group.getGroupId(),
				_folder.getFolderId(), dlFileEntry.getFileEntryId(),
				_serviceContext);
		}
	}

	protected void populateFolderWithDLFolders() throws Exception {
		for (int i = 0; i < 5; i++) {
			DLTestUtil.addDLFolder(
				_group.getGroupId(), _folder.getFolderId(), true,
				_serviceContext);
		}
	}

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private ServiceContext _serviceContext;

}