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
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.test.MainServletTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.util.test.RandomTestUtil;
import com.liferay.portal.util.test.ServiceContextTestUtil;
import com.liferay.portal.util.test.TestPropsValues;
import com.liferay.portlet.documentlibrary.DuplicateFileException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryConstants;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import org.testng.Assert;

/**
 * @author Adolfo Pérez
 */
@RunWith(Enclosed.class)
public class PortletFileRepositoryTest {

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenAddingAFileEntry {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test
		public void shouldCreateApprovedFileEntry() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			DLFileEntry dlFileEntry = (DLFileEntry)fileEntry.getModel();

			Assert.assertEquals(
				WorkflowConstants.STATUS_APPROVED, dlFileEntry.getStatus());
		}

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			String name = RandomTestUtil.randomString();

			_addPortletFileEntry(portletId, folder.getFolderId(), name);

			_addPortletFileEntry(portletId, folder.getFolderId(), name);
		}

		@Test
		public void shouldHaveDefaultVersion() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			Assert.assertEquals(
				fileEntry.getVersion(), DLFileEntryConstants.VERSION_DEFAULT);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 2);
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(count, 1);
		}

	}

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenAddingAFolder {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test(expected = DuplicateFileException.class)
		public void shouldFailIfDuplicateName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			String name = RandomTestUtil.randomString();

			_addPortletFolder(portletId, folder.getFolderId(), name);

			_addPortletFolder(portletId, folder.getFolderId(), name);
		}

		@Test
		public void shouldSucceedIfUniqueName() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFolder(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			_addPortletFolder(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());
		}

		@Test
		public void shouldSucceedOnEmptyFolder() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			_addPortletFolder(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());
		}

	}

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenDeletingAFolder {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test
		public void shouldDeleteAllFileEntries() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

			for (int i = 0; i < fileEntriesToAdd; i++) {
				_addPortletFileEntry(
					portletId, folder.getFolderId(),
					RandomTestUtil.randomString());
			}

			PortletFileRepositoryUtil.deletePortletFolder(folder.getFolderId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test
		public void shouldIgnoreErorsIfFolderDoesNotExist() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			PortletFileRepositoryUtil.deletePortletFolder(folder.getFolderId());

			PortletFileRepositoryUtil.deletePortletFolder(folder.getFolderId());
		}

		@Test(expected = NoSuchFolderException.class)
		public void shouldSucceedIfFolderExists() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			PortletFileRepositoryUtil.deletePortletFolder(folder.getFolderId());

			PortletFileRepositoryUtil.getPortletFolder(folder.getFolderId());
		}

	}

	@RunWith(LiferayIntegrationJUnitTestRunner.class)
	public static class WhenDeletingFileEntries {

		@ClassRule
		public static final MainServletTestRule mainServletTestRule =
			MainServletTestRule.INSTANCE;

		@Test
		public void shouldDeleteAllFileEntries() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			int fileEntriesToAdd = Math.abs(RandomTestUtil.randomInt()) % 10;

			for (int i = 0; i < fileEntriesToAdd; i++) {
				_addPortletFileEntry(
					portletId, folder.getFolderId(),
					RandomTestUtil.randomString());
			}

			PortletFileRepositoryUtil.deletePortletFileEntries(
				TestPropsValues.getGroupId(), folder.getFolderId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test
		public void shouldIgnoreErorsIfFileDoesNotExist() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(0, count);
		}

		@Test
		public void shouldSucceedIfFileEntryExists() throws Exception {
			String portletId = RandomTestUtil.randomString();

			Folder folder = _addPortletFolder(portletId);

			FileEntry fileEntry = _addPortletFileEntry(
				portletId, folder.getFolderId(), RandomTestUtil.randomString());

			PortletFileRepositoryUtil.deletePortletFileEntry(
				fileEntry.getFileEntryId());

			int count = PortletFileRepositoryUtil.getPortletFileEntriesCount(
				TestPropsValues.getGroupId(), folder.getFolderId());

			Assert.assertEquals(0, count);
		}

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