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
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.RepositoryLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Adolfo Pérez
 */
@RunWith(Enclosed.class)
public class PortletFileRepositoryTest {

	public static class WhenAddingAFileEntry {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_portletId = RandomTestUtil.randomString();

			_folder = _addPortletFolder(_portletId);
		}

		@Test
		public void shouldCreateApprovedFileEntry() throws Exception {
			FileEntry fileEntry = _addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateName() throws Exception {
			String name = RandomTestUtil.randomString();

			_addPortletFileEntry(_portletId, _folder.getFolderId(), name);

			_addPortletFileEntry(_portletId, _folder.getFolderId(), name);
		}

		@Test
		public void shouldHaveDefaultVersion() throws Exception {
			FileEntry fileEntry = _addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			Assert.assertEquals(
				fileEntry.getVersion(), DLFileEntryConstants.VERSION_DEFAULT);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			_addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			_addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(count, 2);
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			_addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(count, 1);
		}

		@After
		public void tearDown()throws Exception {
			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			RepositoryLocalServiceUtil.deleteRepository(
				_folder.getRepositoryId());
		}

		private Folder _folder;
		private String _portletId;

	}

	public static class WhenAddingAFolder {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_portletId = RandomTestUtil.randomString();

			_folder = _addPortletFolder(_portletId);
		}

		@Test
		public void shouldReturnExistingFolderIfDuplicateName()
			throws Exception {

			String name = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(
				_portletId, _folder.getFolderId(), name);

			Folder newFolder = _addPortletFolder(
				_portletId, _folder.getFolderId(), name);

			Assert.assertEquals(newFolder, folder);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			_addPortletFolder(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			_addPortletFolder(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			_addPortletFolder(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());
		}

		@After
		public void tearDown()throws Exception {
			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			RepositoryLocalServiceUtil.deleteRepository(
				_folder.getRepositoryId());
		}

		private Folder _folder;
		private String _portletId;

	}

	public static class WhenDeletingAFolder {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_portletId = RandomTestUtil.randomString();

			_folder = _addPortletFolder(_portletId);
		}

		@Test
		public void shouldDeleteAllFileEntries() throws Exception {
			int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

			for (int i = 0; i < fileEntriesToAdd; i++) {
				_addPortletFileEntry(
					_portletId, _folder.getFolderId(),
					RandomTestUtil.randomString());
			}

			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test(expected = NoSuchFolderException.class)
		public void shouldSucceedIfFolderExists() throws Exception {
			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			PortletFileRepositoryUtil.getPortletFolder(_folder.getFolderId());
		}

		@After
		public void tearDown()throws Exception {
			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			RepositoryLocalServiceUtil.deleteRepository(
				_folder.getRepositoryId());
		}

		private Folder _folder;
		private String _portletId;

	}

	public static class WhenDeletingFileEntries {

		@ClassRule
		@Rule
		public static final AggregateTestRule aggregateTestRule =
			new AggregateTestRule(
				new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

		@Before
		public void setUp() throws Exception {
			_portletId = RandomTestUtil.randomString();

			_folder = _addPortletFolder(_portletId);
		}

		@Test
		public void shouldDeleteAllFileEntries() throws Exception {
			int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

			for (int i = 0; i < fileEntriesToAdd; i++) {
				_addPortletFileEntry(
					_portletId, _folder.getFolderId(),
					RandomTestUtil.randomString());
			}

			PortletFileRepositoryUtil.deletePortletFileEntries(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test
		public void shouldIgnoreErorsIfFileDoesNotExist() throws Exception {
			FileEntry fileEntry = _addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test
		public void shouldSucceedIfFileEntryExists() throws Exception {
			FileEntry fileEntry = _addPortletFileEntry(
				_portletId, _folder.getFolderId(),
				RandomTestUtil.randomString());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), _folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@After
		public void tearDown()throws Exception {
			PortletFileRepositoryUtil.deletePortletFolder(
				_folder.getFolderId());

			RepositoryLocalServiceUtil.deleteRepository(
				_folder.getRepositoryId());
		}

		private Folder _folder;
		private String _portletId;

	}

	private static FileEntry _addPortletFileEntry(
			String portletId, long folderId, String name)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFileEntry(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			User.class.getName(), TestPropsValues.getUserId(), portletId,
			folderId, RandomTestUtil.randomInputStream(), name,
			ContentTypes.APPLICATION_OCTET_STREAM, false);
	}

	private static Folder _addPortletFolder(String portletId)
		throws PortalException {

		return _addPortletFolder(
			portletId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString());
	}

	private static Folder _addPortletFolder(
			String portletId, long parentFolderId, String name)
		throws PortalException {

		return PortletFileRepositoryUtil.addPortletFolder(
			TestPropsValues.getGroupId(), TestPropsValues.getUserId(),
			portletId, parentFolderId, name,
			ServiceContextTestUtil.getServiceContext());
	}

}