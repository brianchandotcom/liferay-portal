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

package com.liferay.document.library.repository.portlet.file.repository.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.document.library.kernel.exception.DuplicateFileEntryException;
import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFileEntryConstants;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.io.unsync.UnsyncByteArrayInputStream;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.capabilities.WorkflowCapability;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestDataConstants;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.InputStream;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Adolfo Pérez
 */
@RunWith(Arquillian.class)
public class PortletFileRepositoryTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_group = GroupTestUtil.addGroup();

		_portletId = RandomTestUtil.randomString();

		_folder = PortletFileRepositoryUtil.addPortletFolder(
			_group.getGroupId(), TestPropsValues.getUserId(), _portletId,
			DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(),
			ServiceContextTestUtil.getServiceContext());
	}

	@Test
	public void testFileEntryAddShouldCreateApprovedFileEntry()
		throws Exception {

		FileEntry fileEntry = _addPortletFileEntry(
			RandomTestUtil.randomString());

		WorkflowCapability workflowCapability =
			fileEntry.getRepositoryCapability(WorkflowCapability.class);

		Assert.assertEquals(
			WorkflowConstants.STATUS_APPROVED,
			workflowCapability.getStatus(fileEntry));
	}

	@Test(expected = DuplicateFileEntryException.class)
	public void testFileEntryAddShouldFailIfDuplicateName() throws Exception {
		String name = RandomTestUtil.randomString();

		_addPortletFileEntry(name);
		_addPortletFileEntry(name);
	}

	@Test
	public void testFileEntryAddShouldHaveDefaultVersion() throws Exception {
		FileEntry fileEntry = _addPortletFileEntry(
			RandomTestUtil.randomString());

		Assert.assertEquals(
			DLFileEntryConstants.VERSION_DEFAULT, fileEntry.getVersion());
	}

	@Test
	public void testFileEntryAddShouldSucceedIfUniqueName() throws Exception {
		_addPortletFileEntry(RandomTestUtil.randomString());
		_addPortletFileEntry(RandomTestUtil.randomString());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(2, count);
	}

	@Test
	public void testFileEntryAddShouldSucceedOnEmptyFolder() throws Exception {
		_addPortletFileEntry(RandomTestUtil.randomString());

		int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
			_group.getGroupId(), _folder.getFolderId());

		Assert.assertEquals(1, count);
	}

	@Test
	public void testFileEntryDeleteShouldDeleteAllFileEntries()
		throws Exception {

		int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

		for (int i = 0; i < fileEntriesToAdd; i++) {
			_addPortletFileEntry(RandomTestUtil.randomString());
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

		Assert.assertEquals(_addPortletFolder(name), _addPortletFolder(name));
	}

	@Test
	public void testFolderAddShouldSucceedIfUniqueName() throws Exception {
		_addPortletFolder(RandomTestUtil.randomString());
		_addPortletFolder(RandomTestUtil.randomString());
	}

	@Test
	public void testFolderAddShouldSucceedOnEmptyFolder() throws Exception {
		_addPortletFolder(RandomTestUtil.randomString());
	}

	@Test
	public void testFolderDeleteShouldDeleteAllFileEntries() throws Exception {
		int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

		for (int i = 0; i < fileEntriesToAdd; i++) {
			_addPortletFileEntry(RandomTestUtil.randomString());
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

	@Test
	public void testGetPortletFileEntryURLWithAmpersand() throws Exception {
		FileEntry fileEntry = _addPortletFileEntry(
			RandomTestUtil.randomString());

		String queryString = "param=value";

		StringBundler sb = new StringBundler(8);

		sb.append("/documents/portlet_file_entry/");
		sb.append(_group.getGroupId());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getTitle());
		sb.append(StringPool.SLASH);
		sb.append(fileEntry.getUuid());
		sb.append(StringPool.QUESTION);
		sb.append(queryString);

		Assert.assertEquals(
			sb.toString(),
			PortletFileRepositoryUtil.getPortletFileEntryURL(
				null, fileEntry, StringPool.AMPERSAND + queryString));
	}

	private FileEntry _addPortletFileEntry(String name) throws Exception {
		try (InputStream inputStream = new UnsyncByteArrayInputStream(
				TestDataConstants.TEST_BYTE_ARRAY)) {

			return PortletFileRepositoryUtil.addPortletFileEntry(
				_group.getGroupId(), TestPropsValues.getUserId(),
				User.class.getName(), TestPropsValues.getUserId(), _portletId,
				_folder.getFolderId(), inputStream, name,
				ContentTypes.APPLICATION_OCTET_STREAM, false);
		}
	}

	private Folder _addPortletFolder(String name) throws PortalException {
		return PortletFileRepositoryUtil.addPortletFolder(
			_group.getGroupId(), TestPropsValues.getUserId(), _portletId,
			_folder.getFolderId(), name,
			ServiceContextTestUtil.getServiceContext());
	}

	private Folder _folder;

	@DeleteAfterTestRun
	private Group _group;

	private String _portletId;

}