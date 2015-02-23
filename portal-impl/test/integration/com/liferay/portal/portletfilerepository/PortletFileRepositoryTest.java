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

package com.liferay.portal.portletfilerepository;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.testng.Assert;

/**
 * @author Adolfo Pérez
 */
public class PortletFileRepositoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		_portletId = RandomTestUtil.randomString();

		_group = GroupTestUtil.addGroup();

		_folder = _addPortletFolder(_group.getGroupId(), _portletId);
	}

	@Test
	public void testFileEntryAddShouldCreateApprovedFileEntry()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
	}

	@Test(expected = DuplicateFileException.class)
	public void testFileEntryAddShouldFailIfDuplicateName() throws Exception {
		String name = RandomTestUtil.randomString();

		_addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(), name);

		_addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(), name);
	}

	@Test
	public void testFileEntryAddShouldHaveDefaultVersion() throws Exception {
		FileEntry fileEntry = _addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		Assert.assertEquals(
			fileEntry.getVersion(), DLFileEntryConstants.VERSION_DEFAULT);
	}

	@Test
	public void testFileEntryAddShouldSucceedIfUniqueName() throws Exception {
		_addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		_addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(count, 2);
	}

	@Test
	public void testFileEntryAddShouldSucceedOnEmptyFolder() throws Exception {
		_addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(count, 1);
	}

	@Test
	public void testFileEntryDeleteShouldDeleteAllFileEntries()
		throws Exception {

		int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

		for (int i = 0; i < fileEntriesToAdd; i++) {
			_addPortletFileEntry(
				_group.getGroupId(), _portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());
		}

		PortletFileRepositoryUtil.deletePortletFileEntries(
			_group.getGroupId(), _folder.getFolderId());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(0, count);
	}

	@Test
	public void testFileEntryDeleteShouldIgnoreErorsIfFileDoesNotExist()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(0, count);
	}

	@Test
	public void testFileEntryDeleteShouldSucceedIfFileEntryExistsTest()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		PortletFileRepositoryUtil.deletePortletFileEntry(
			fileEntry.getFileEntryId());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(0, count);
	}

	@Test
	public void testFolderAddShouldReturnExistingFolderIfDuplicateName()
		throws Exception {

		String name = RandomTestUtil.randomString();

		Folder folder = _addPortletFolder(
			_group.getGroupId(), _portletId, _folder.getFolderId(), name);

		Folder newFolder = _addPortletFolder(
			_group.getGroupId(), _portletId, _folder.getFolderId(), name);

		Assert.assertEquals(newFolder, folder);
	}

	@Test
	public void testFolderAddShouldSucceedIfUniqueName() throws Exception {
		_addPortletFolder(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());

		_addPortletFolder(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testFolderAddShouldSucceedOnEmptyFolder() throws Exception {
		_addPortletFolder(
			_group.getGroupId(), _portletId, _folder.getFolderId(),
			RandomTestUtil.randomString());
	}

	@Test
	public void testFolderDeleteShouldDeleteAllFileEntries() throws Exception {
		int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

		for (int i = 0; i < fileEntriesToAdd; i++) {
			_addPortletFileEntry(
				_group.getGroupId(), _portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());
		}

		PortletFileRepositoryUtil.deletePortletFolder(_folder.getFolderId());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(0, count);
	}

	@Test(expected = NoSuchFolderException.class)
	public void testFolderDeleteShouldSucceedIfFolderExists() throws Exception {
		PortletFileRepositoryUtil.deletePortletFolder(_folder.getFolderId());

		PortletFileRepositoryUtil.getPortletFolder(_folder.getFolderId());
	}

	private static FileEntry _addPortletFileEntry(
			long groupId, String portletId, long folderId, String name)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFileEntry(
			groupId, TestPropsValues.getUserId(), User.class.getName(),
			TestPropsValues.getUserId(), portletId, folderId,
			RandomTestUtil.randomInputStream(), name,
			ContentTypes.APPLICATION_OCTET_STREAM, false);
	}

	private static Folder _addPortletFolder(long groupId, String portletId)
		throws PortalException {

		return _addPortletFolder(
			groupId, portletId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
	}

	private static Folder _addPortletFolder(
			long groupId, String portletId, long parentFolderId, String name)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFolder(
			groupId, TestPropsValues.getUserId(), portletId, parentFolderId,
			name, ServiceContextTestUtil.getServiceContext());
	}

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private String _portletId;

}